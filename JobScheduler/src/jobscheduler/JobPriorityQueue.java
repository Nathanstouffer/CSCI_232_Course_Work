/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscheduler;

/**
 * Class that stores and prioritizes the jobs
 * The higher the value of the priority variable, the higher the priority
 * Otherwise, priority is chose arbitrarily
 * @author natha
 * @param <T>
 */
public class JobPriorityQueue <T extends Comparable<T>> {
    private T[] heap;                           // heap to prioritize jobs
    private int heap_size = 0;                  // variable to store the size of the heap
    
    /**
     * Constructor to initialize the size of heap
     * @param size 
     */
    JobPriorityQueue(int size){ heap = (T[]) new Comparable[size+1]; }
    
    /**
     * Method to insert job into heap
     * @param new_job 
     */
    public void insert(T new_job){
        heap_size++;
        heap[heap_size] = new_job;                                          // insert new job at end of heap
        
        int new_job_index = heap_size;                                      // integer to track new jobs location in heap
        boolean sorted = false;
        while (!sorted){                                                    // loop to swim new_job up the heap until sorted
            if (new_job_index == 1){                                        // if job is at top, heap is sorted
                sorted = true;
            }
            else{                                                           // otherwise, sort by priority
                if (compareNodes(new_job_index, new_job_index/2) >= 0){     // if new_job has higher priority than its parent, swim up heap
                    new_job_index = swimUp(new_job_index);                    
                }
                else{                                                       // otherwise, heap is sorted
                    sorted = true;
                }
            }
        }
    }
    
    /**
     * Method to remove job from heap
     * @return
     */
    public T remove(){
        T return_job = heap[1];                     // highest priority value, to be returned
        
        heap[1] = heap[heap_size];                  // move bottom value to top of heap
        boolean changed_size = false;               // variable tells whether heap size has been edited
        if (heap_size % 2 == 0){                    // ensure that every node has either two or zero children
            heap[heap_size] = null;
            heap_size--;
            changed_size = true;
        }
        
        int current_index = 1;                                                  // variable to track location of the node in heap
        int child_index;
        boolean sorted = false;
        while (!sorted){
            if (2*current_index > heap_size){                                   // if node has no children, heap is sorted
                sorted = true;
            }
            else{                                                               // otherwise, compare priorities
                if (compareNodes(2*current_index, 2*current_index +1) >= 0){    // finds greater priority node of two child nodes
                    child_index = 2*current_index;
                }
                else{
                    child_index = 2*current_index+1;
                }
                
                if (!(compareNodes(current_index, child_index) >= 0)){          // if parent node has less priority than its child, sink down
                    current_index = sinkDown(current_index);
                }
                else{
                    sorted = true;                                              // otherwise, heap is sorted
                }
            }
        }
        
        if (!changed_size){                                                     // removes last value from heap if that has not been done
            heap[heap_size] = null;
            heap_size--;
        }    
        return return_job;
    }
    
    /**
     * Method to return top of heap without removing it
     * @return 
     */
    public T peekMax(){ return heap[1]; }
    
    /**
     * Method to return whether heap is empty
     * @return 
     */
    public boolean isEmpty(){ return heap[1] == null; }
    
    /**
     * Method to swim value up if the heap is out of order
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
     * Method to sink value down if the heap is out of order
     * @param parent_index
     * @return 
     */
    private int sinkDown(int parent_index){
        T temp = heap[parent_index];
        
        int child_index = 0;
        if (compareNodes(2*parent_index, 2*parent_index+1) >= 0){                // tests which child has greater priority
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
     * Method to compare the priority of two nodes
     * Returns 1 if value at index_one is larger than value at index_two
     * Returns 0 if values are equivalent
     * Returns -1 if value at index_one is smaller than value at index_two
     * @param index_one
     * @param index_two
     * @return 
     */
    private int compareNodes(final int index_one, final int index_two){        
        return heap[index_one].compareTo(heap[index_two]);
    }
}
