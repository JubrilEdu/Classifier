/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firster;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edu
 */
public class Firster implements Serializable{

    static int numberOfHam, numberOfSpam, totalNumberOfMail;
    static Scanner scanner;
    static File file = new File("/Users/Edu/NetBeansProjects/Firster/train/");

    public static void main(String[] args) throws FileNotFoundException, IOException {
        File filer = new File("/Users/Edu/NetBeansProjects/Firster/train/");
        System.out.println(classifyFolder(filer));

    }
    public static String classifyFile(File file){
     scanner = null;double result=1.0,result1=1.0;String word ="",classed;
        try {
            scanner= new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Firster.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scanner.hasNext()) {
            word = scanner.next();
            result = result*conditionalProb(word,"spam");
            result1 = result1*conditionalProb(word,"ham");
        }
        result = result*prior("spam");
        result1 = result1*prior("ham");
        if (result>=result1) {
            classed="spam";
        }else{
         classed = "ham";
        }
        
        return classed;
    }
    public static int classifyFolder(File folder){
        //remember to alter the path of the globally declared file as that would affect the method prior
       for (int i = 0; i < folder.list().length; i++) {
            if(classifyFile(folder.listFiles()[i]) == "spam"){
            System.out.println("spam");
            }else{
            System.out.println("Ham");}
        }
       return 1;
    }

    public static double conditionalProb(String string, String string1) {
//calculates conditional probability where string is the word and string1 is the class which the word belongs
//e.g conditionalProb("boy","spam")
        double probability;
        int wordCounted = 0;
        int wordOccurenceCount = 0;
        int numberOfDistinctWords = 0;
        for (int i = 0; i < file.list().length; i++) {
            numberOfDistinctWords = numberOfDistinctWords + distinctwords(file.listFiles()[i]);
            if (file.listFiles()[i].getName().contains(string1) == true) {
                wordCounted = wordCounted + wordCount(file.listFiles()[i]);
                wordOccurenceCount = wordOccurenceCount + countWordOccurence(string, file.listFiles()[i]);

            }
        }
        probability = (Double.valueOf(wordOccurenceCount + 1) / Double.valueOf((wordCounted + numberOfDistinctWords)));
        return probability;
    }

    public static int wordCount(File file) {
        //total number of words in the file
        String word = "";
        ArrayList<String> arraylist = new ArrayList<String>();
        scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Firster.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scanner.hasNext()) {
            word = scanner.next();
            arraylist.add(word);
        }
        return arraylist.size();
    }

    public static int distinctwords(File file) {
        String word = "";
        HashSet hashset = new HashSet();
        scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Firster.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scanner.hasNext()) {
            word = scanner.next();
            hashset.add(word);
        }
        return hashset.size();

    }

    public static int countWordOccurence(String word, File file) {
//outputs the total number of occurence of word in a file
        int count = 0;
        scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Firster.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            if (str.indexOf(word) != -1) {
                count++;
            }
        }
        return count;
    }

    public static double prior(String string) {
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
