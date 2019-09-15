/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1.stouffer;

/**
 *
 * @author natha
 */
public class MinPQ {
    private int heap_length = 1;
    private CubedSumValue[] heap;
    private int queue_size = 0;
    
    /**
     * Constructor method to initialize array
     * @param size
    */
    MinPQ(int size){
        if (size > 1){
            heap_length = size;
            heap = new CubedSumValue[heap_length + 2];
        }
    }
    
    /**
     * Returns whether queue is empty
     * @return 
     */
    public boolean isEmpty(){
        return heap[1] == null;
    }
    
    /**
     * Method to insert value into heap
     * @param number 
     */
    public void insert(CubedSumValue number){
        int index = queue_size + 1;
        heap[index] = number;
        boolean sorted = false;
        while (index / 2 > 0 && !sorted){
            if (heap[index].getCubeSum() < heap[index/2].getCubeSum()){
                CubedSumValue temp = heap[index];
                heap[index] = heap[index/2];
                heap[index/2] = temp;
                index /= 2;
            }
            else{
                sorted = true;
            }
        }
        queue_size++;
    }
    
    /**
     * Method to remove value from the heap
     * @return 
     */
    public CubedSumValue remove(){
        CubedSumValue return_value = heap[1];
        heap[1] = heap[queue_size];
        
        int index = 1;
        boolean sorted = false;
        while (!sorted){
            if (!(2*index + 1 > queue_size)){
                if (heap[index].getCubeSum() > heap[2*index].getCubeSum()
                        || heap[index].getCubeSum() > heap[2*index+1].getCubeSum()){
                    if (heap[2*index].getCubeSum() < heap[2*index+1].getCubeSum()){
                        CubedSumValue temp = heap[2*index];
                        heap[2*index] = heap[index];
                        heap[index] = temp;
                        index *= 2;
                    }
                    else{
                        CubedSumValue temp = heap[2*index+1];
                        heap[2*index+1] = heap[index];
                        heap[index] = temp;
                        index = 2*index + 1;
                    }
                }
                else{
                    sorted = true;
                }
            }
            else{
                sorted = true;
            }
        }
        
        heap[queue_size] = null;
        queue_size--;
        
        return return_value;
    }
    
}
