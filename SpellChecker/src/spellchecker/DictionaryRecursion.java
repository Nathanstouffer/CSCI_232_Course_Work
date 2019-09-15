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
public class DictionaryRecursion {
    
    // hashtable to store known words
    private Hashtable<Integer, String> dict;
    
    // ArrayList to store replacement options for a misspelled word
    private ArrayList<String> options = new ArrayList();
    // ArrayList to store the depth of an option
    private ArrayList<Integer> depthOptions = new ArrayList();
    
    // ArrayList to help with efficiency, tells if a word has been visited 
    private ArrayList<String> visited = new ArrayList();
    // ArrayList to store the depth at which a value was visited
    private ArrayList<Integer> depthVisited = new ArrayList();
    
    // variable to set maximum distance from current word
    private final int MAXDEPTH = 2;
    // variable to define the maximum number of options displayed
    private final int OPTIONS = 6;
    
    // variable to store the number of options that have depth 1
    private int depthOneCount = 0;
    
    private final char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z' };
    
    /**
     * constructor to initialize empty hashtable
     * @param size 
     */
    DictionaryRecursion(){ dict = new Hashtable<Integer, String>(); }
    
    /**
     * constructor to populate a hashtable with the values in words
     * @param words 
     */
    DictionaryRecursion(ArrayList<String> words){
        dict = new Hashtable();
        
        for (String str : words){
            dict.put(str.hashCode(), str);
        }
    }
    
    /**
     * method to find and display options for a misspelled word
     * @param val misspelled word
     * @return chosen word
     */
    public String options(String val){
        // clear ArrayLists
        options.clear();
        depthOptions.clear();
        visited.clear();
        depthVisited.clear();
        
        // compute possible options for misspelled word
        computeOptions(val, 0);
        
        String[] output = new String[OPTIONS];
        // number of values in output
        int count = 0;
        // get "close" options for misspelled word with depth as priority
        for (int i = 1; i <= MAXDEPTH && count < output.length; i++){
            // iterate through options ArrayList looking for smallest depths first
            for (int j = 0; count < output.length && j < options.size(); j++){
                if (depthOptions.get(j) == i){
                    // only add to output array if not null
                    if (options.get(j) != null){
                        output[count] = options.get(j);
                        count++;
                    }
                }
            }
        }
        
        // get the choice of the new word from the user
        String choice = displayOptions(val, output, count);
        return choice;
    }
    
    /**
     * method to find options for a misspelled word
     * @param val
     * @param depth 
     */
    private void computeOptions(String val, int depth){
        // check depth of word
        if (depth <= MAXDEPTH && depthOneCount < OPTIONS){
            if (visited.contains(val)){
                return;
                /*int index = visited.indexOf(val);
                // return if current depth of val is greater than depth from another path
                if (depth >= depthVisited.get(index))
                    return;
                // otherwise, set the new depth
                depthVisited.set(index, depth);
                */
            }
            else{
                visited.add(val);
                depthVisited.add(depth);
            }
            
            // check to see if val is a word
            if (dict.containsKey(val.hashCode())){
                if (dict.containsValue(val)){
                    options.add(val);
                    depthOptions.add(depth);
                    if (depth == 1)
                        depthOneCount++;
                }
            }
            
            addLetter(val, depth);
            removeLetter(val, depth);
            replaceLetter(val, depth);
            swapConsecutiveLetters(val, depth);
        }
    }
    
    /**
     * method that adds a letter to a word
     * recursive for computing options for misspelled words
     * @param val
     * @param depth 
     */
    private void addLetter(String val, int depth){
        for (int i = 0; i < val.length()+1; i++){
            for (int j = 0; j < alphabet.length; j++){
                String newVal;
                if (i < val.length())
                    newVal = val.substring(0, i) + alphabet[j] + val.substring(i);
                else{
                    newVal = val + alphabet[j];
                }
                computeOptions(newVal, depth+1);
            }
        }
    }
    
    /**
     * method to remove a letter from a word
     * recursive for computing options for misspelled words
     * @param val
     * @param depth 
     */
    private void removeLetter(String val, int depth){
        for (int i = 0; i < val.length(); i++){
            String newVal = val.substring(0, i) + val.substring(i+1);
            computeOptions(newVal, depth+1);
        }
    }
    
    /**
     * method to switch out every letter in the word
     * recursive for computing options for misspelled words
     * @param val
     * @param depth 
     */
    private void replaceLetter(String val, int depth){
        for (int i = 0; i < val.length(); i++){
            for (int j = 0; j < alphabet.length; j++){
                String newVal = val.substring(0, i) + alphabet[j] + val.substring(i+1);
                computeOptions(newVal, depth+1);
            }
        }
    }
    
    /**
     * method to swap two consecutive letters
     * recursive for computing options for misspelled words
     * @param val
     * @param pos
     * @param depth 
     */
    private void swapConsecutiveLetters(String val, int depth){
        for (int i = 0; i < val.length()-1; i++){
            char cur = val.charAt(i);
            char next = val.charAt(i+1);
            String newVal = val.substring(0, i) + next + cur + val.substring(i+2);
            computeOptions(newVal, depth+1);
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
