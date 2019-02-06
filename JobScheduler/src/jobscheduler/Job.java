/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobscheduler;

/**
 * Class to store information about a job
 * Job objects will be nodes in the heap
 * @author natha
 */
public class Job {
    private int job_number = 0;
    private int priority = 0;
    private int arrival_time = 0;
    private int duration = 0;
    private int progress = 0;
    private int start_time = 0;
    
    /**
     * Constructor method to store values in object
     * @param p_job_number
     * @param p_priority
     * @param p_arrival_time
     * @param p_duration 
     */
    Job (int p_job_number, int p_priority, int p_arrival_time, int p_duration){
        job_number = p_job_number;
        priority = p_priority;
        arrival_time = p_arrival_time;
        duration = p_duration;
    }

    /**
     * Method to print out job information
     * @param current_second
     * @return 
     */
    public String toString(){
        String output = String.format("Current job number: "
                + "%-4d | ", job_number);
        if (duration == progress){                                      // add to output string if job is completed
            output += String.format("Completed. Waiting time "
                    + "(sec): %d | Execution time (sec): ", 
                    start_time-arrival_time);
        }
        
        return output;
    }
    
    /**
     * Method to run job for one second
     */
    public void runJob(){ progress++; }
    
    /**
     * Setter method
     */
    public void setStartTime(int p_start_time) { start_time = p_start_time; }
    
    /**
     * Getter methods 
     * @return 
     */
    public int getJobNumber() { return job_number; }
    public int getPriority() { return priority; }
    public int getArrivalTime() { return arrival_time; }
    public int getDuration() { return duration; }
    public int getProgress() { return progress; }
    public int getStartTime() { return start_time; }
}
