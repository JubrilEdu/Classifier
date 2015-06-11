/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firster;

import static firster.Firster.conditionalProb;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edu
 */
public class Tester implements Serializable{
   static HashMap hashmap=new HashMap<>();static String ray;static int i=0;static Map anotherMap;
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        Scanner scanner;
        ObjectOutputStream objOut;
        ObjectInputStream ois;
        TrainingClass trained = new TrainingClass();
       try (FileInputStream fileInputStream = new FileInputStream("/Users/Edu/NetBeansProjects/Firster/train2/ham1000.txt")) {
           FileOutputStream out = new FileOutputStream("file.ser");
           objOut = new ObjectOutputStream(out);
           scanner = new Scanner(fileInputStream);
           while (scanner.hasNext()) {
               ray = scanner.next();
              double dob= trained.conditionalProb(ray,"ham");
               hashmap.put(ray,dob);
               i++;
           }
            System.out.println(hashmap);
       }
        objOut.writeObject(hashmap);
        objOut.close();
      
        FileInputStream fin = new FileInputStream("file.ser");
        ois = new ObjectInputStream(fin);
       try {
            anotherMap = (Map) ois.readObject();
            System.out.println(anotherMap);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
       }
        ois.close();
       
        }
       
    }

    

