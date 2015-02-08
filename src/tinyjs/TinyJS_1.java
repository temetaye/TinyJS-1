/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tinyjs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author Alexy
 */
public class TinyJS_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        File file = new File("test.txt");
        try {
            FileReader r = new FileReader(file);
            BufferedReader br = new BufferedReader(r);
            try {
               P1 p = new P1();
            }catch (Exception e){
                System.out.println("File not found:"+file.toString());
            }
            
        }catch (FileNotFoundException e){
            System.out.println("File not found:"+file.toString());
        }
    } 
                
    }

