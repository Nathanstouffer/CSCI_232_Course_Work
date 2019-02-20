/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binpacker;

/**
 *
 * @author natha
 */
public class Bin {
    
    private int[] bin_values;
    private int value_count;
    private int bin_weight;
    
    /**
     * Constructor to create bin
     * @param bin_size 
     */
    Bin(int bin_size){
        bin_values = new int[bin_size];
    }
    
    /**
     * Method to add value to bin
     * @param value 
     */
    public void addValue(int value){
        value_count++;
        bin_values[value_count] = value;
        bin_weight += value;
    }
    
    public int getBinWeight(){ return bin_weight; }
}
