/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsgraph;

/**
 *
 * @author natha
 */
public class NewVertex implements Comparable<NewVertex>{
    
    // global variables
    private final int vertex;
    private final int distTo;
    
    /**
     * constructor to initialize global variables
     * @param vertex
     * @param distTo 
     */
    NewVertex(int vertex, int distTo){
        this.vertex = vertex;
        this.distTo = distTo;
    }
    
    /**
     * method to compare distance of vertices from a source
     * @param object
     * @return 
     */
    @Override
    public int compareTo(NewVertex object){
        if (this.distTo > object.getDistTo()){
            return 1;
        }
        else if (this.distTo == object.getDistTo()){
            return 0;
        }
        else{
            return -1;
        }
    }
    
    // getter methods
    public int getVertex(){ return vertex; }
    public int getDistTo(){ return distTo; }
}
