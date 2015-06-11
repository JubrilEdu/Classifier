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
public class TrainingClass implements Serializable {

    static int numberOfHam, numberOfSpam, totalNumberOfMail, noOfDistinctWords;
    static double spamPrior, HamPrior;
    static Scanner scanner;
    static String word;
    static HashMap hashmap1 = new HashMap<>();
    static HashMap hasher = new HashMap();
    static HashMap<String, Double> hashmap = new HashMap<>();
    static File file = new File("/Users/Edu/NetBeansProjects/Firster/train/");
    static ObjectOutputStream objOut, objOut2, objOut3;
    static FileOutputStream out, out2, out3;
    static HashMap hashed = new HashMap(), hashed1 = new HashMap();

    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        File filer = new File("/Users/Edu/NetBeansProjects/Firster/train/");
        TrainingClass seral = new TrainingClass();
        hashed = (HashMap) seral.countWordOccurence("spam");
        hashed1 = (HashMap) seral.countWordOccurence("ham");
        noOfDistinctWords = hashed.size() + hashed1.size();
        out = new FileOutputStream("file.ser");
        objOut = new ObjectOutputStream(out);
        out2 = new FileOutputStream("file2.ser");
        objOut2 = new ObjectOutputStream(out2);
        spamPrior = seral.prior("spam");
        HamPrior = seral.prior("ham");
        HashMap hasher = new HashMap();
        hasher.put("spamPrior", spamPrior);
        hasher.put("hamPrior", HamPrior);
        out3 = new FileOutputStream("file3.ser");
        objOut3 = new ObjectOutputStream(out3);
        objOut3.writeObject(hasher);
        objOut3.close();
        for (int i = 0; i < filer.listFiles().length; i++) {
            seral.groupCPHam(filer.listFiles()[i]);
            seral.groupCPSpam(filer.listFiles()[i]);
        }
         objOut.writeObject(hashmap);
         objOut2.writeObject(hashmap1);
        System.out.println();
        objOut2.close();
        objOut.close();

    }

    public Map groupCPHam(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        String worded;
        Scanner scanner1;
        if (file.getName().contains("ham")) {
            FileInputStream fileInputStream = new FileInputStream(file);
            scanner1 = new Scanner(fileInputStream);
            while (scanner1.hasNext()) {
                worded = scanner1.next();
                double reck = conditionalProb2(worded, "ham");
                hashmap.put(worded, reck);
            }
        } else {
        }
        // objOut.writeObject(hashmap);
        return hashmap;
    }

    public Map<String, Double> groupCPSpam(File filed) throws IOException, FileNotFoundException, ClassNotFoundException {
        String worded;
        Scanner scanner2;
        if (filed.getName().contains("spam")) {
            FileInputStream fileInputStream = new FileInputStream(filed);
            scanner2 = new Scanner(fileInputStream);
            while (scanner2.hasNext()) {
                worded = scanner2.next();
                double reck = conditionalProb(worded, "spam");
                hashmap1.put(worded, reck);
            }
        } else {
        }
        return hashmap1;
    }

    public int wordCount(String word2) {
        //total number of words in the file
        String word = "";
        int num = 0;
        ArrayList<String> arraylist = new ArrayList<String>();
        scanner = null;
        for (int i = 0; i < file.list().length; i++) {
            if (file.listFiles()[i].getName().contains(word2) == true) {
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

    public double conditionalProb(String string, String string1) throws FileNotFoundException, IOException, ClassNotFoundException {
//calculates conditional probability where string is the word and string1 is the class which the word belongs
//e.g conditionalProb("boy","spam")
        int wordOccurenceCount;
        if (hashed.get(string) == null) {
            wordOccurenceCount = 0;
        } else {
            wordOccurenceCount = (int) hashed.get(string);
        }
        double probability;
        double wordCounted = hashed.size();
        double numberOfDistinctWords = (double) noOfDistinctWords;

        probability = (wordOccurenceCount + 1) / (wordCounted + numberOfDistinctWords);
        return probability;
    }

    public double conditionalProb2(String string, String string1) throws FileNotFoundException, IOException, ClassNotFoundException {
//calculates conditional probability where string is the word and string1 is the class which the word belongs
//e.g conditionalProb("boy","spam")
        int wordOccurenceCount = 0;
        if (hashed1.get(string) == null) {
            wordOccurenceCount = 0;
        } else {
            wordOccurenceCount = (int) hashed1.get(string);
        }
        double probability;
        double wordCounted = hashed1.size();
        double numberOfDistinctWords = (double) noOfDistinctWords;

        probability = (wordOccurenceCount + 1) / (wordCounted + numberOfDistinctWords);
        return probability;
    }

    public int distinctwords(File file) {
        HashSet hashset = new HashSet();
        int num;
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

    public Map countWordOccurence(String word2) {
//outputs the total number of occurence of word in a file.....word2 = class
        HashMap hashmap = new HashMap();
        for (int i = 0; i < file.list().length; i++) {
            if (file.listFiles()[i].getName().contains(word2) == true) {
                try {
                    scanner = new Scanner(file.listFiles()[i]);
                    while (scanner.hasNext()) {
                        String str = scanner.next();
                        if (hashmap.containsKey(str)) {
                            int dobb = (int) hashmap.get(str);
                            hashmap.put(str, dobb + 1);
                        } else {
                            hashmap.put(str, 1);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Firster.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return hashmap;
    }

    public double prior(String string) {
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
        } else if (string.equalsIgnoreCase("spam")) {
            b = Double.valueOf(numberOfSpam);
        }
        double drior = b / a;
        return drior;
    }

}
