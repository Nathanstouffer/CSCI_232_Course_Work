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
        if (args.length != 2){
            System.out.println("Sufficient input not provided");
        }
        start(args[0], args[1]);
    }
    
    public static void start(String max_weight_string, String file_path){
        int max_weight = Integer.parseInt(max_weight_string);
        
        int[] items = readFile(file_path);
        
        int first_fit_bin_count = firstFit(items, max_weight);
        int best_fit_bin_count = bestFitDecreasing(items, max_weight);
        int worst_fit_bin_count = worstFitDecreasing(items, max_weight);
        
        
    }
    
    /**
     * Method to compute the number of bins required for first fit algorithm
     * @param items
     * @param max_weight
     * @return 
     */
    private static int firstFit(int[] items, int max_weight) {
        LinkedList<Bin> bins = new LinkedList<Bin>();
        
        int item_large_count = 0;
        for (int i = 0; i < items.length && items[i] != 0; i++){                // run for every item in items
            int j = 0;                                                          // current index in linked list
            boolean inserted_item = false;
            while (!inserted_item){
                if (items[i] > max_weight) {                                    // ensure item can fit in empty bin
                    item_large_count++;
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
        System.out.println(String.format("%d items too large for bin", item_large_count));
        
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
        
        int item_large_count = 0;
        for (int i = 0; i < items.length && items[i] != 0; i++){                // run while there are items to insert
            if (items[i] > max_weight){                                         // ensure item is not larger than bin
                item_large_count++;
            }
            else{
                if (bins.isEmpty()){                                            // check if ST is empty
                    newBin(bins, max_weight, items[i]);
                    size++;                                                     // increment bin size
                }
                else{
                    ArrayList<Bin> bins_at_key = bins.bestFit(items[i]);        // find bin to fit item
                    if (bins_at_key == null){                                   // if there is no bin large enough, create one
                        bins_at_key = new ArrayList<Bin>();
                        newBin(bins, max_weight, items[i]);
                        size++;                                                 // increment bin size
                    }
                    else{
                        insertItem(bins, bins_at_key, max_weight, items[i]);    // insert item
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
        System.out.println(String.format("%d items too large for bin", item_large_count));
        
        return size;
    }

    private static int worstFitDecreasing(int[] items, int max_weight) {
        BST<Integer, ArrayList<Bin>> bins = new BST<Integer, ArrayList<Bin>>(); // BST where the key is the remaining space in a bin and the value is an array list of bins
        int size = 0;                                                           // number of bins used
        
        int item_large_count = 0;
        for (int i = 0; i < items.length && items[i] != 0; i++){                // run while there are items to insert
            if (items[i] > max_weight){                                         // ensure item is not larger than bin
                item_large_count++;
            }
            else{
                if (bins.isEmpty()){                                            // check if ST is empty
                    newBin(bins, max_weight, items[i]);                    
                    size++;                                                     // increase bin count
                }
                else{
                    ArrayList<Bin> bins_at_key = bins.worstFit();               // find bin to fit item
                    if (bins_at_key == null || max_weight - 
                            bins_at_key.get(0).getWeight() - items[i] < 0){     // if there is no bin large enough, create one
                        bins_at_key = new ArrayList<Bin>();
                        newBin(bins, max_weight, items[i]);
                        size++;                                                 // increment bin size
                    }
                    else{
                        insertItem(bins, bins_at_key, max_weight, items[i]);    // insert item
                    }
                }
            }
        }
        
        System.out.println("\n\nBins for Worst Fit Decreasing Algorithm:");      // print out bins
        int i = 1;
        for (int key : bins.keys()){
            ArrayList<Bin> val = bins.get(key);
            for (int j = 0; j < val.size(); j++){
                System.out.println(String.format("Bin %d: ", i) + val.get(j));
                i++;
            }
        }
        System.out.println(String.format("%d items too large for bin", item_large_count));
        
        return size;
    }
    
    /**
     * Method to insert item into BST at correct location
     * 
     * @param tree
     * @param bins_at_key
     * @param max_weight
     * @param item 
     */
    private static void insertItem(BST<Integer, ArrayList<Bin>> tree, ArrayList<Bin> bins_at_key, int max_weight, int item)
    {
        Bin bin = bins_at_key.remove(bins_at_key.size()-1);                     // get bin from data structure
        if (bins_at_key.size() == 0){
            tree.delete(max_weight - bin.getWeight());                          // delete ArrayList if no values remain
        }
        else{
            tree.put(max_weight - bin.getWeight(), bins_at_key);                // otherwise, reinsert remaing bins of equivalent size
        }

        bin.add(item);                                                          // add item to bin and bin to ArrayList
        int remaining_weight = max_weight - bin.getWeight();
        ArrayList<Bin> new_val = tree.get(remaining_weight);
        if (new_val == null){
            new_val = new ArrayList<Bin>();
        }
        new_val.add(bin);

        tree.put(remaining_weight, new_val);                                    // put item to data structure
    }
    
    /**
     * Method to create new bin and add it to symbol table
     * 
     * @param tree
     * @param max_weight
     * @param item 
     */
    private static void newBin(BST<Integer, ArrayList<Bin>> tree, int max_weight, int item){
        Bin bin = new Bin(max_weight);
        bin.add(item);
        ArrayList<Bin> val = new ArrayList<Bin>();
        val.add(bin);
        tree.put(max_weight - item, val);  
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
