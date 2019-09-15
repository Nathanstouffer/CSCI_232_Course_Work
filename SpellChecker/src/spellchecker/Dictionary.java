/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spellchecker;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author natha
 */
public class Dictionary {
    
    // Hashtable to store the dictionary of words
    private final Hashtable<Integer, String> dict;
    
    // ArrayList of replacement options for a misspelled word
    private final ArrayList<String> options = new ArrayList();
    // ArrayList of visited words
    private final ArrayList<String> visited = new ArrayList();
    
    // Queue of words to be checked in dict
    private Queue<String> vals = new Queue();
    // Queue of depths of words to be checked in dict
    private Queue<Integer> depths = new Queue();
    
    // variable to define the maximum number of options displayed
    private final int OPTIONS = 7;
    // variable to set maximum distance from current word
    private final int MAXDEPTH = 2;
    
    private final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z' };
    
    /**
     * constructor to initialize empty hashtable
     * @param size 
     */
    Dictionary(){ dict = new Hashtable<Integer, String>(); }
    
    /**
     * constructor to populate a hashtable with the values in words
     * @param words 
     */
    Dictionary(ArrayList<String> words){
        dict = new Hashtable();
        
        for (String str : words){
            dict.put(str.hashCode(), str);
        }
    }
    
    /**
     * method to find and display options for a misspelled word
     * @param initial misspelled word
     * @return chosen word
     */
    public String options(String initial){
        // clear ArrayLists
        options.clear();
        visited.clear();
        
        // clear queues
        vals = new Queue();
        depths = new Queue();
        
        // populate first items in queue
        vals.enqueue(initial);
        depths.enqueue(0);
        
        // compute possible options for misspelled word
        computeOptions();
        
        String[] output = new String[OPTIONS];
        // number of values in output
        int count = 0;
        // populate output array with possible replacements for misspelled words
        for (int i = 0; count < OPTIONS && i < options.size(); i++){
            // only add to output array if not null
            if (options.get(i) != null){
                output[count] = options.get(i);
                count++;
            }
        }
        
        // get the choice of the new word from the user
        String choice = displayOptions(initial, output, count);
        return choice;
    }
    
    /**
     * method to compute possible replacements for misspelled words
     */
    private void computeOptions(){  
        // generate possible replacements for misspelled word
        while (!vals.isEmpty() && options.size() < OPTIONS){
            // get next val to be tested
            String val = vals.dequeue();
            // get associated depth
            int depth = depths.dequeue();
            
            // if val is in the dictionary, add to ArrayList options
            if (dict.containsKey(val.hashCode())){
                if (dict.containsValue(val)){
                    options.add(val);
                }
            }
            
            // process word if within MAXDETPH
            if (depth < MAXDEPTH)
                processWord(val, depth);
        }
    }
    
    /**
     * method to process a word,
     * performing each operation on each character in the input string
     * @param val
     * @param depth 
     */
    private void processWord(String val, int depth){
        for (int pos = 0; pos < val.length()+1; pos++){
            addLetter(val, pos, depth+1);
            removeLetter(val, pos, depth+1);
            replaceLetter(val, pos, depth+1);
            swapConsecutiveLetters(val, pos, depth+1);
            computeOptions();
        }
    }
    
    /**
     * method to process new value as visited and add to the queues
     * @param newVal
     * @param depth 
     */
    private void processNewVal(String newVal, int depth){
        // mark as visited
        visited.add(newVal);
        // add newVal to queue
        vals.enqueue(newVal);
        // add depth to queue
        depths.enqueue(depth);
    }
    
    /**
     * Method takes in a word and enqueues modifications to the word.
     * The modification is adding a single letter to a fixed position in the word.
     * Every possible modification is enqueued
     * @param val 
     */
    private void addLetter(String val, int pos, int depth){
        if (pos < val.length()+1){
            // iterate through the alphabet
            for (int j = 0; j < alphabet.length; j++){
                String newVal;
                if (pos < val.length())
                    // new string with a letter added inside the word
                    newVal = val.substring(0, pos) + alphabet[j] + val.substring(pos);
                else
                    // new string with a letter added to the end of the word
                    newVal = val + alphabet[j];
                // if newVal has not been visited
                if (!visited.contains(newVal))
                    processNewVal(newVal, depth);
            }
        }
    }

    /**
     * Method takes in a word and enqueues modifications to the word.
     * The modification is removing a single letter at a fixed position in the word.
     * Every possible modification is enqueued
     * @param val 
     */
    private void removeLetter(String val, int pos, int depth){
        if (pos < val.length()){
            // new string missing one letter
            String newVal = val.substring(0, pos) + val.substring(pos+1);
            // if newVal has not been visited
            if (!visited.contains(newVal))
                processNewVal(newVal, depth);
        }
    }
    
    /**
     * Method takes in a word and enqueues modifications to the word.
     * The modification is replacing a single letter at a fixed position in the word.
     * Every possible modification is enqueued.
     * @param val 
     * @param depth
     */
    private void replaceLetter(String val, int pos, int depth){
        if (pos < val.length()){
            // iterate through the alphabet
            for (int j = 0; j < alphabet.length; j++){
                // new string with replaced letter
                String newVal = val.substring(0, pos) + alphabet[j] + val.substring(pos+1);
                // if newVal has not been visited
                if (!visited.contains(newVal))
                    processNewVal(newVal, depth);
            }
        }
    }
    
    /**
     * Method takes in a word and enqueues modifications to the word.
     * The modification is swapping the positions of two consecutive letters at a fixed position
     * Every possible modification is enqueued
     * @param val 
     * @param depth 
     */
    private void swapConsecutiveLetters(String val, int pos, int depth){
        if (pos < val.length()-1){
            // char values of letters to be swapped
            char cur = val.charAt(pos);
            char next = val.charAt(pos+1);
            // new string with swapped letters
            String newVal = val.substring(0, pos) + next + cur + val.substring(pos+2);
            // if newVal has not been visited
            if (!visited.contains(newVal))
                processNewVal(newVal, depth);
        }
    }
    
    /**
     * method to display possible words for user
     * @param word word to be replaced
     * @param output options for misspelled word. Maximum value of 10
     * @param count count of values in output
     * @return 
     */
    private String displayOptions(String word, String[] output, int count){
        System.out.println(word + "\nDid you mean:");
        for (int i = 0; i < output.length; i++){
            if (output[i] != null){
                int index = i + 1;
                System.out.println(index + ". " + output[i]);
            }
        }
        System.out.print("0. Something else\nEnter integer: ");
        
        // check user's choice
        Scanner scnr = new Scanner(System.in);
        int choice = scnr.nextInt();
        switch (choice){
            case 0:
                System.out.print("Enter word: ");
                word = scnr.next();
                break;
            default:
                int index = choice - 1;
                if (index < count)
                    word = output[index];
                else{
                    System.out.println("\nInvalid entry.");
                    word = displayOptions(word, output, count);
                }
        }
        
        System.out.println();
        return word;
    }
    
    public boolean containsKey(Integer key){ return dict.containsKey(key); }
    public boolean containsValue(String val){ return dict.containsValue(val); }
    public void put(String val){ dict.put(val.hashCode(), val); }
    
}
