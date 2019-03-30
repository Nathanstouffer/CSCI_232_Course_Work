/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsgraph;

/**
 * Class that stores and prioritizes the items in a minimum oriented priority queue
 * @author natha
 * @param <T>
 */
public class MinPriorityQueue <T extends Comparable<T>> {

    // heap to prioritize items
    private T[] heap = (T[]) new Comparable[10];
    // variable to store the position of the first empty slot in the heap
    private int last_item = 0;
    
    /**
     * Constructor to initialize object
     * @param size 
     */
    MinPriorityQueue(){ }
    
    /**
     * Method to insert items into heap
     * @param new_item 
     */
    public void insert(T new_item){
        // double heap size, if too small
        if (++last_item == heap.length){
            doubleHeapSize();
        }
        // insert new item at end of heap
        heap[last_item] = new_item;
        
        sortUp(last_item);
    }
    
    /**
     * Method to remove item from top of heap
     * @return
     */
    public T remove(){
        // highest priority value, to be returned
        T return_job = heap[1];
        
        // move bottom value to top of heap
        heap[1] = heap[last_item];
        
        sortDown(1);
        
        return return_job;
    }
    
    /**
     * method to change the key of an object if it currently exists in the heap
     * NOTE: this method is specific to the GPS Graph lab for CSCI 232, delete otherwise
     * @param object 
     */
    public void decreaseKey(T temp){
        NewVertex object = (NewVertex)temp;
        boolean not_found = true;
        for (int i = 1; i <= last_item && not_found; i++){
            NewVertex current = (NewVertex)heap[i];
            if (current.getVertex() == object.getVertex()){
                not_found = false;
                current.setDistTo(object.getDistTo());
                //heap[i] = (T)current;
                sortUp(i);
            }
        }
        if (not_found){
            insert(temp);
        }
    }
    
    /**
     * Method to return top of heap without removing it
     * @return 
     */
    public T peek(){ return heap[1]; }
    
    /**
     * Method to return whether heap is empty
     * @return 
     */
    public boolean isEmpty(){ return heap[1] == null; }
    
    /**
     * method to sort the priority queue going up starting at any index
     * @param new_item_index 
     */
    private void sortUp(int item_index){
        boolean sorted = false;
        
        // loop to swim new_item up the heap until sorted
        while (!sorted){
            // if item is at top, heap is sorted
            if (item_index == 1){
                sorted = true;
            }
            // otherwise, sort by priority
            else{
                // if new_item has higher priority than its parent, swim up heap
                if (compareItems(item_index, item_index/2) <= 0){
                    item_index = swimUp(item_index);                    
                }
                // otherwise, heap is sorted
                else{
                    sorted = true;
                }
            }
        }
    }
    
    /**
     * Method to swim item up if the heap is out of order
     * @param child_index
     * @return 
     */
    private int swimUp(int child_index){
        T temp = heap[child_index];
        heap[child_index] = heap[child_index/2];
        heap[child_index/2] = temp;
        return child_index / 2;
    }
    
    /**
     * method to sort the priority queue going down starting at any index
     * @param current_index 
     */
    private void sortDown(int current_index){
        // variable tells whether heap size has been changed
        boolean changed_size = false;
        
        // ensure that every item has either two or zero children
        if (last_item % 2 == 0){
            heap[last_item] = null;
            last_item--;
            changed_size = true;
        }
        
        int child_index;
        boolean sorted = false;
        while (!sorted){
            // if item has no children, heap is sorted
            if (2*current_index > last_item){
                sorted = true;
            }
            // otherwise, compare priorities
            else{
                // finds greater priority item of two child items
                if (compareItems(2*current_index, 2*current_index + 1) <= 0){
                    child_index = 2*current_index;
                }
                else{
                    child_index = 2*current_index+1;
                }
                
                // if parent item has less priority than its child, sink down
                if (!(compareItems(current_index, child_index) <= 0)){
                    current_index = sinkDown(current_index);
                }
                // otherwise, heap is sorted
                else{
                    sorted = true;
                }
            }
        }
        
        // removes last value from heap if that has not been done
        if (!changed_size){
            heap[last_item] = null;
            last_item--;
        }
    }
    
    /**
     * Method to sink item down if the heap is out of order
     * @param parent_index
     * @return 
     */
    private int sinkDown(int parent_index){
        T temp = heap[parent_index];
        
        int child_index = 0;
        // tests which child has greater priority
        if (compareItems(2*parent_index, 2*parent_index+1) <= 0){
            child_index = 2*parent_index;
        }
        else{
            child_index = 2*parent_index+1;
        }
        
        heap[parent_index] = heap[child_index];
        heap[child_index] = temp;
        return child_index;
    }
    
    /**
     * Method to compare the priority of two items
     * Returns 1 if value at index_one is larger than value at index_two
     * Returns 0 if values are equivalent
     * Returns -1 if value at index_one is smaller than value at index_two
     * @param index_one
     * @param index_two
     * @return 
     */
    private int compareItems(final int index_one, final int index_two){        
        return heap[index_one].compareTo(heap[index_two]);
    }
    
    /**
     * method to double the size of the heap
     * @return 
     */
    private void doubleHeapSize(){
        T[] temp = (T[]) new Comparable[2*heap.length];
        for (int i = 0; i < heap.length; i++){
            temp[i] = heap[i];
        }
        heap = temp;
    }
}
