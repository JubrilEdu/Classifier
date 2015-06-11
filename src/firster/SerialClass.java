/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edu
 */
public class SerialClass implements Serializable{
    static int numberOfHam, numberOfSpam, totalNumberOfMail;
    static double spamPrior,HamPrior;
    static Scanner scanner;static String word; 
    static HashMap hashmap1 = new HashMap<>();
    HashMap <String,Double> hashmap = new HashMap<>();
    static File file = new File("/Users/Edu/NetBeansProjects/Firster/train2/");
    static ObjectOutputStream objOut;static FileOutputStream out;
    static Map anotherMap;

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        File filer = new File("/Users/Edu/NetBeansProjects/Firster/train2/");
        TrainingClass seral = new TrainingClass();
        spamPrior = seral.prior("spam");
        HamPrior = seral.prior("ham");
         seral.groupCPHam(filer);
         seral.groupCPSpam(filer);
         HashMap hasher = new HashMap();
         hasher.put("spamPrior",spamPrior);
         hasher.put("hamPrior", HamPrior);
         out = new FileOutputStream("file3.ser");
         objOut = new ObjectOutputStream(out);
         objOut.writeObject(hasher);
         objOut.close();     
       
        FileInputStream fin = new FileInputStream("file.ser");
       ObjectInputStream ois = new ObjectInputStream(fin);
       try {
             anotherMap = (Map) ois.readObject();
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
       }
        ois.close();
        System.out.println(anotherMap.size());
       
                
               
         
       
    }
  
    public  Map groupCPHam(File file) throws FileNotFoundException, IOException{
        String worded; Scanner scanner1;out = new FileOutputStream("file.ser");
        objOut = new ObjectOutputStream(out);
        for (int i = 0; i < file.list().length; i++) { 
             if (file.listFiles()[i].getName().contains("ham")) {
                   FileInputStream fileInputStream = new FileInputStream(file.listFiles()[i].toString());
                      scanner1 = new Scanner(fileInputStream);
                      while (scanner1.hasNext()) {
                      worded = scanner1.next();
                      double reck = conditionalProb(worded,"ham");
                          hashmap.put(worded,reck);
                     }
                   
                   
             }
} 
      objOut.writeObject(hashmap);
        objOut.close();        
      return hashmap;
    }
    public  Map<String,Double> groupCPSpam(File file) throws IOException{
             String worded; Scanner scanner2;out = new FileOutputStream("file2.ser");
             objOut = new ObjectOutputStream(out);
        for (int i = 0; i < file.list().length; i++) { 
             if (file.listFiles()[i].getName().contains("spam")) {
                   FileInputStream fileInputStream = new FileInputStream(file.listFiles()[i].toString());
                      scanner2 = new Scanner(fileInputStream);
                      while (scanner2.hasNext()) {
                      worded = scanner2.next();
                      double reck = conditionalProb(worded,"spam");
                          hashmap1.put(worded,reck);
                     }
                   
                   
             }
}
     objOut.writeObject(hashmap1);
        objOut.close();  
     return hashmap1;
    }
    public  int wordCount(File file,String word2) {
        //total number of words in the file
        String word = "";int num=0;
        ArrayList<String> arraylist = new ArrayList<String>();
        scanner = null;
         for (int i = 0; i < file.list().length; i++) {
        if(file.listFiles()[i].getName().contains(word2)== true){  
        try {
            scanner = new Scanner(file.listFiles()[i]);
             while (scanner.hasNext()) {
            word = scanner.next();
            arraylist.add(word);
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Firster.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        }
         num = num + arraylist.size();
         }
       
        arraylist.clear();
  
         return num;
    }
  public  double conditionalProb(String string, String string1) {
//calculates conditional probability where string is the word and string1 is the class which the word belongs
//e.g conditionalProb("boy","spam")
        double probability;
        double wordCounted = Double.valueOf(wordCount(file,string1));
        double wordOccurenceCount =Double.valueOf(countWordOccurence(string,string1));
        double numberOfDistinctWords = Double.valueOf(distinctwords(file));
        
        probability = (wordOccurenceCount + 1)/ (wordCounted + numberOfDistinctWords);
        return probability;
    }
    public  int distinctwords(File file) {
        HashSet hashset = new HashSet();int num;
        for (int i = 0; i < file.list().length; i++) {
          try {
            scanner = new Scanner(file.listFiles()[i]);
             while (scanner.hasNext()) {
              word = scanner.next();
              hashset.add(word);
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Firster.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
         num = hashset.size();
         hashset.clear();
         return num;
    }

    public  int countWordOccurence(String word,String word2) {
//outputs the total number of occurence of word in a file.....word2 = class
        int count = 0;int rob =0;
       for (int i = 0; i < file.list().length; i++) {
    if(file.listFiles()[i].getName().contains(word2)== true){       
        try {
            scanner = new Scanner(file.listFiles()[i]);
             while (scanner.hasNext()) {
            String str = scanner.next();
              if (str.indexOf(word) != -1) {
                count++;
             }

             }
             }catch (FileNotFoundException ex) {
            Logger.getLogger(Firster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       }
     return count;
    }

    public  double prior(String string) {
        //outputs the prior given the arguement above ,string represents the class whose prior is to be calculated
        totalNumberOfMail = file.list().length;
         numberOfHam = 0;
        numberOfSpam = 0;
        double a = Double.valueOf(totalNumberOfMail);
        double b = 0.0;
        for (int i = 0; i < file.list().length; i++) {
            if (file.listFiles()[i].getName().contains("ham") == true) {
                numberOfHam++;
            } else {
                numberOfSpam++;
            }
        }
        if (string.equalsIgnoreCase("ham")) {
          b = Double.valueOf(numberOfHam);
        }else if(string.equalsIgnoreCase("spam")){
          b = Double.valueOf(numberOfSpam);
        }
        double drior = b/a;
        return drior;
    }
   
}
