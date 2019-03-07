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
    
    private final int[] items;
    private int count;
    private final int max_weight;
    private int weight = 0;
    
    /**
     * Constructor to create bin
     * @param size 
     */
    Bin(int max_weight){
        this.max_weight = max_weight;
        items = new int[max_weight];
        count = 0;
    }
    
    /**
     * Method to add value to bin 
     * @param item
     */
    public void add(int item){
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
    
    public int getWeight(){ return weight; }
}
