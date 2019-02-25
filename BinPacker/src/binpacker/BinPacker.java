/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binpacker;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
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
        //int best_fit_bin_count = bestFitDecreasing(items, max_weight);
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
                    new_bin.addItem(items[i]);
                    bins.add(new_bin);
                    inserted_item = true;
                }
                else if (max_weight - bins.get(j).getWeight() - items[i] >= 0) {//if item fits, insert item
                    bins.get(j).addItem(items[i]);
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

    private static int bestFitDecreasing(int[] items, int max_weight) {
        
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
