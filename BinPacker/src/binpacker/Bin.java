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
public class Bin implements Comparable<Bin> {
    
    private final int[] items;
    private int count;
    private int weight = 0;
    
    /**
     * Constructor to create bin
     * @param size 
     */
    Bin(int max_weight){
        items = new int[max_weight];
        count = 0;
    }
    
    /**
     * Method to add value to bin 
     * @param item
     */
    public void addItem(int item){
        items[count] = item;
        count++;
        weight += item;
    }
    
    /**
     * Method to return items array as output
     * @return 
     */
    @Override
    public String toString(){
        if (count > 0){
            String return_string = "{" + items[0];
            for (int i = 1; i < count; i++){
                return_string += ", " + items[i];
            }
            return return_string + "}";
        }
        else{
            return "No items in bin";
        }
    }
    
    /**
     * Method to compare two bins
     * Returns 1 if current bin has less space than temp
     * Returns 0 if remaining space is equal
     * Returns -1 if current bin has more space than temp
     * @param temp
     * @return 
     */
    @Override
    public int compareTo(final Bin temp){// throws NullPointerException{
        if (weight > temp.weight){
            return 1;
        }
        else if (weight == temp.weight){
            return 0;
        }
        else{
            return -1;
        }
    }
    
    public int getWeight(){ return weight; }
}
