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
public class Job implements Comparable<Job> {
    private int job_number = 0;
    private int priority = 0;
    private int arrival_time = 0;
    private int duration = 0;
    private int progress = 0;
    private int start_time = 0;
    private int wait_time = 0;
    private int execution_time = 0;
    
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
     * Method to calculate wait and execution time for job
     * @param finish_time
     */
    public void calculateStats(int finish_time){
        wait_time = start_time-arrival_time;
        execution_time = finish_time-start_time+1;
    }
    
    /**
     * Method to compare object to another object
     * @param object
     * @return 
     */
    @Override
    public int compareTo(final Job object){
        if (this.priority > object.getPriority()){
            return 1;
        }
        else if (this.priority == object.getPriority()){
            return 0;
        }
        else{
            return -1;
        }
    }

    /**
     * Method to print out job wait and execution time
     * @return 
     */
    @Override
    public String toString(){
        String output = String.format("Job number: %-2d | "
                + "Wait time: %-4d | Execution time: %-4d |",
                job_number, wait_time, execution_time);

        return output;
    }
    
    /**
     * Method to run job for one second
     * @param current_second
     */
    public void runJob(int current_second){
        if (start_time == 0){
            start_time = current_second;
        }
        progress++;
    }
    
    /**
     * Setter methods
     * @param p_start_time
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
    public int getWaitTime() { return wait_time; }
    public int getExectuionTime() { return execution_time; }
}
