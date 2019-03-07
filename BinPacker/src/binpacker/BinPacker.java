/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binpacker;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author natha
 */
public class BinPacker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*if (args.length != 2){
            System.out.println("Sufficient input not provided");
        }
        start(args[0], args[1]);
        */
        
        start("50", "input.txt");
    }
    
    public static void start(String max_weight_string, String file_path){
        int max_weight = Integer.parseInt(max_weight_string);
        
        int[] items = readFile(file_path);
        
        int first_fit_bin_count = firstFit(items, max_weight);
        int best_fit_bin_count = bestFitDecreasing(items, max_weight);
        //int worst_fit_bin_count = worstFitDecreasing(items, max_weight);
        
        
    }
    
    /**
     * Method to compute the number of bins required for first fit algorithm
     * @param items
     * @param max_weight
     * @return 
     */
    private static int firstFit(int[] items, int max_weight) {
        LinkedList<Bin> bins = new LinkedList<Bin>();
        
        for (int i = 0; i < items.length && items[i] != 0; i++){                // run for every item in items
            int j = 0;                                                          // current index in linked list
            boolean inserted_item = false;
            while (!inserted_item){
                if (items[i] > max_weight) {                                    // ensure item can fit in empty bin
                    System.out.println("Item is too large for bin.");
                    inserted_item = true;
                }
                else if (j == bins.size()){                                     // if there is no available bin, make a new bin
                    Bin new_bin = new Bin(max_weight);
                    new_bin.add(items[i]);
                    bins.add(new_bin);
                    inserted_item = true;
                }
                else if (max_weight - bins.get(j).getWeight() - items[i] >= 0) {//if item fits, insert item
                    bins.get(j).add(items[i]);
                    inserted_item = true;
                }
                
                j++;                                                            // increment index in linked list
            }
        }
        
        System.out.println("Bins for First Fit Algorithm:");
        for (int i = 0; i < bins.size(); i++){
            System.out.println("Bin " + (i + 1) + ": " + bins.get(i));
        }
        
        return bins.size();                                                     // return the size of the bin
    }

    /**
     * Method to fit items into bins by finding the bin that has the least
     * remaining space that can fit the item
     * 
     * @param items
     * @param max_weight
     * @return 
     */
    private static int bestFitDecreasing(int[] items, int max_weight) {
        BST<Integer, ArrayList<Bin>> bins = new BST<Integer, ArrayList<Bin>>(); // BST where the key is the remaining space in a bin and the value is an array list of bins
        int size = 0;                                                           // number of bins used
        
        for (int i = 0; i < items.length && items[i] != 0; i++){                // run while there are items to insert
            if (items[0] > max_weight){                                         // ensure item is not larger than bin
                System.out.println("Item is too large for bin");
            }
            else{
                if (bins.isEmpty()){                                            // check if ST is empty
                    Bin bin = new Bin(max_weight);                              // create first value in ST
                    bin.add(items[i]);
                    ArrayList<Bin> val = new ArrayList<Bin>();
                    val.add(bin);
                    bins.put(max_weight - items[i], val);  
                    
                    size++;                                                     // increase bin count
                }
                else{
                    ArrayList<Bin> old_val = bins.bestFit(items[i]);            // find bin to fit item
                    if (old_val == null){                                       // if there is no bin large enough, create one
                        old_val = new ArrayList<Bin>();
                    }
                    if (old_val.size() == 0){                                   // if no bins exist in the ArrayList
                        Bin bin = new Bin(max_weight);                          // create a bin
                        bin.add(items[i]);                                      // add item
                        old_val.add(bin);
                        bins.put(max_weight - bin.getWeight(), old_val);        // put to symbol table
                        
                        size++;                                                 // increment size
                    }
                    else{
                        Bin bin = old_val.remove(old_val.size()-1);             // get bin from data structure
                        if (old_val.size() == 0){
                            bins.delete(max_weight - bin.getWeight());          // delete ArrayList if no values remain
                        }
                        else{
                            bins.put(max_weight - bin.getWeight(), old_val);    // otherwise, reinsert remaing bins of equivalent size
                        }
                            
                        bin.add(items[i]);                                      // add item to bin and bin to ArrayList
                        int remaining_weight = max_weight - bin.getWeight();
                        ArrayList<Bin> new_val = bins.get(remaining_weight);
                        if (new_val == null){
                            new_val = new ArrayList<Bin>();
                        }
                        new_val.add(bin);

                        bins.put(remaining_weight, new_val);                    // put item to data structure
                    }
                }
            }
        }
        
        System.out.println("\n\nBins for Best Fit Decreasing Algorithm:");      // print out bins
        int i = 1;
        for (int key : bins.keys()){
            ArrayList<Bin> val = bins.get(key);
            for (int j = 0; j < val.size(); j++){
                System.out.println(String.format("Bin %d: ", i) + val.get(j));
                i++;
            }
        }
        
        return size;
    }

    private static int worstFitDecreasing(int[] items, int max_weight) {
        return 0;
    }
    
    private static int[] readFile(String file_path){
        int[] items = new int[10];                                              // array to store items
        int index = 0;                                                          // first empty spot in array
        
        try{
            Scanner fin = new Scanner(new File(file_path));
            
            while (fin.hasNextLine()){
                String line = fin.nextLine();                                   // take in line as input
                
                if (index == items.length){                                     // resize array if needed
                    items = doubleSize(items);
                }
                
                items[index] = Integer.parseInt(line);                          // insert value into array
                index++;
            }
            
            fin.close();
        }
        catch (FileNotFoundException e){
            System.err.println("Opening file error");
        }
        
        return items;
    }
    
    /**
     * Method to double the size of an integer array
     * @param array
     * @return 
     */
    public static int[] doubleSize(int[] array){
        int[] return_array = new int[2*array.length];
        
        for (int i = 0; i < array.length; i++){                                 // copy array values
            return_array[i] = array[i];
        }
        
        return return_array;
    }

}
