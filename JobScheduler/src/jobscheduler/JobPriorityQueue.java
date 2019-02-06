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
 */
public class JobPriorityQueue {
    private Job[] job_heap;             // heap to prioritize jobs
    private int heap_size = 0;          // variable to store the size of the heap

    
    /**
     * Constructor to initialize the size of job_heap
     * @param size 
     */
    JobPriorityQueue(int size){ job_heap = new Job[size+1]; }
    
    /**
     * Method to insert job into heap
     * @param new_job 
     */
    public void insert(Job new_job){
        heap_size++;
        job_heap[heap_size] = new_job;                              // insert new job at end of heap
        
        int new_job_index = heap_size;                              // integer to track new jobs location in heap
        boolean sorted = false;
        while (!sorted){                                            // loop to swim new_job up the heap until sorted
            if (new_job_index == 1){                                // if job is at top, heap is sorted
                sorted = true;
            }
            else{                                                   // otherwise, sort by priority
                if (compareNodes(new_job_index, new_job_index/2)){  // if new_job has higher priority than its parent, swim up heap
                    new_job_index = swimUp(new_job_index);                    
                }
                else{                                               // otherwise, heap is sorted
                    sorted = true;
                }
            }
        }
    }
    
    /**
     * Method to remove job from heap
     * @return
     */
    public Job remove(){
        Job return_job = job_heap[1];               // highest priority value, to be returned
        
        job_heap[1] = job_heap[heap_size];          // move bottom value to top of heap
        boolean changed_size = false;               // variable tells whether heap size has been edited
        if (heap_size % 2 == 0){                    // ensure that every node has either two or zero children
            job_heap[heap_size] = null;
            heap_size--;
            changed_size = true;
        }
        
        int current_index = 1;                                              // variable to track location of the node in heap
        boolean sorted = false;
        while (!sorted){
            if (2*current_index > heap_size){                               // if node has no children, heap is sorted
                sorted = true;
            }
            else{                                                           // otherwise, compare priorities
                int child_index;
                if (job_heap[2*current_index].getPriority()
                        >= job_heap[2*current_index+1].getPriority()){      // finds greater priority node of two child nodes
                    child_index = 2*current_index;
                }
                else{
                    child_index = 2*current_index+1;
                }
                
                if (!compareNodes(current_index, child_index)){             // if parent node has less priority than its child, sink down
                    current_index = sinkDown(current_index);
                }
                else{
                    sorted = true;                                          // otherwise, heap is sorted
                }
            }
        }
        
        if (!changed_size){                                                 // removes last value from heap if that has not been done
            job_heap[heap_size] = null;
            heap_size--;
        }    
        return return_job;
    }
    
    /**
     * Method to return top of heap without removing it
     * @return 
     */
    public Job peekMax(){ return job_heap[1]; }
    
    /**
     * Method to return whether job_heap is empty
     * @return 
     */
    public boolean isEmpty(){ return job_heap[1] == null; }
    
    /**
     * Method to swim value up if the heap is out of order
     * @param child_index
     * @return 
     */
    private int swimUp(int child_index){
        Job temp = job_heap[child_index];
        job_heap[child_index] = job_heap[child_index/2];
        job_heap[child_index/2] = temp;
        return child_index / 2;
    }
    
    /**
     * Method to sink value down if the heap is out of order
     * @param parent_index
     * @return 
     */
    private int sinkDown(int parent_index){
        Job temp = job_heap[parent_index];
        
        int child_index = 0;
        if (compareNodes(2*parent_index, 2*parent_index+1)){                // tests which child has greater priority
            child_index = 2*parent_index;
        }
        else{
            child_index = 2*parent_index+1;
        }
        
        job_heap[parent_index] = job_heap[child_index];
        job_heap[child_index] = temp;
        return child_index;
    }
    
    /**
     * Method to compare the priority of two nodes
     * Returns true if node at index_one is greater than node at index_two
     * @param index_one
     * @param index_two
     * @return 
     */
    private boolean compareNodes(int index_one, int index_two){        
        if (job_heap[index_one].getPriority() >= job_heap[index_two].getPriority()){
            return true;
        }
        else{
            return false;
        }
    }
}
