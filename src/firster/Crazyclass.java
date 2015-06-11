/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firster;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Edu
 */
public class Crazyclass implements Serializable{
    static HashMap  anothermap;
    public static void main(String[]main) throws FileNotFoundException, IOException, ClassNotFoundException{  
       FileInputStream fin = new FileInputStream("file.ser");
       ObjectInputStream ois = new ObjectInputStream(fin);
            anothermap =  (HashMap) ois.readObject();
            System.out.println(anothermap);
        ois.close();
        System.out.println(anothermap.size());
        }
    }
