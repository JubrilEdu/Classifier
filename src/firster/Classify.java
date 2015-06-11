/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edu
 */
public class Classify implements Serializable{
    FileInputStream fileInputStream;ObjectInputStream objectInputStream;static Map anotherMap;
    //put directory of file to be classified below
       static File file = new File("/Users/Edu/NetBeansProjects/Firster/train/spam285.txt");
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream("file3.ser");
        Classify classify = new Classify();
       ObjectInputStream ois = new ObjectInputStream(fin);
       try {
             anotherMap = (Map) ois.readObject();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
       }
        ois.close();
       double Ham = classify.classifiedHam(file);
       double Spam = classify.classifiedSpam(file);
        if (Ham > Spam) {
            System.out.println("Ham");
        }else{
            System.out.println("spam");
        }
    }
    public double classifiedHam(File filed) throws FileNotFoundException, IOException, ClassNotFoundException{
      Map mapper; Scanner scanner;float product=1;double val;
      FileInputStream fileInputStream = new FileInputStream("file.ser");
      ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
      mapper =  (Map)objectInputStream.readObject();
      objectInputStream.close();
      scanner = new Scanner(filed);
        while (scanner.hasNext()) {  
            String next = scanner.next();
            if (mapper.containsKey(next)) {               
               val = (double)mapper.get(next);
               product = (float) (product*val);
                System.out.println(product);
            }else{
             product = product*1;   
        }
        }
      double prior = (double) anotherMap.get("hamPrior");
      double result = prior*product;
      
      return  result;
    }
    
    public double classifiedSpam(File filed) throws FileNotFoundException, IOException, ClassNotFoundException{
      Map mapper; Scanner scanner;double product=1;
      fileInputStream = new FileInputStream("file2.ser");
      objectInputStream = new ObjectInputStream(fileInputStream);
      mapper = (Map) objectInputStream.readObject();
      objectInputStream.close();
      scanner = new Scanner(filed);
        while (scanner.hasNext()) {
            if (mapper.get(scanner.next())== null) {
               
            }else{
                 double val = (double) mapper.get(scanner.next());
            product = product*val;
        }}
      double prior = (double) anotherMap.get("spamPrior");
      double result = prior*product;
      return  result;
   
}
}
