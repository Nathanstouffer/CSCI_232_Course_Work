/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spellchecker;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 *
 * @author natha
 */
public class SpellChecker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            String path = args[0];
            //String path = "words.txt";
            // dictionary of existing words
            System.out.println("Loading...");
            Dictionary dict = new Dictionary(getWords(path));
            
            path = args[1];
            //path = "mydoc.txt";
            spellCheck(dict, path);
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.err.println("Enter file paths as command line arguments.");
        }
    }
    
    /**
     * method to spell check a file
     * @param path path of the file to be spell checked
     */
    private static void spellCheck(Dictionary dict, String path){
        // Set up scanner
        Scanner scnr = null;
        try{ scnr = new Scanner(new File(path)); }
        catch (FileNotFoundException e){
            System.err.println("Input file not found.");
            System.exit(1);
        }
        
        String output = "";
        // iterate through input checking for misspells
        while (scnr.hasNextLine()){
            String[] line = scnr.nextLine().split(" ");
            for (int i = 0; i < line.length; i++){
                String word = line[i];
                
                // check if word is in dict
                // if not, find options
                if (!wordInDict(dict, word)){
                    System.out.println("Loading...");
                    word = dict.options(word);
                }
                
                output += word + " ";
            }
            
            output += "\n";
        }
        
        // print output to a file
        try{ fileOutput(output); }
        catch (IOException e) { System.err.println("Invalid output file path."); }
    }
    
    /**
     * method to check if a word is in the hashtable
     * @param dict hashtable
     * @param word word to be checked
     * @return boolean variable
     */
    public static boolean wordInDict(Dictionary dict, String word){
        if (dict.containsKey(word.hashCode())){
            if (dict.containsValue(word))
                return true;
        }
        return false;
    }
    
    /**
     * method to store words in a Hashtable
     * @param path path of file to be stored
     */
    private static ArrayList<String> getWords(String path){
        // Set up scanner
        Scanner scnr = null;
        try{ scnr = new Scanner(new File(path)); }
        catch (FileNotFoundException e){
            System.err.println("File containing words not found.");
            System.exit(1);
        }
        
        ArrayList<String> words = new ArrayList();
        // store dictionary in arrayList
        while (scnr.hasNextLine()){
            words.add(scnr.nextLine());
        }
        
        return words;
    }
    
    /**
     * method to write output to a file
     * @param output String to be outputted
     * @throws IOException 
     */
    private static void fileOutput(String output) throws IOException{
        String path = "mydoc-checked.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));

        writer.write(output);
        writer.close();
    }
    
}
