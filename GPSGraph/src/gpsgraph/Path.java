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
public class Path implements Comparable<Path>{
    
    private int[] vertices = new int[10];
    private int path_len = 0;
    private int cost = 0;
    
    /**
     * constructor to create new path
     * @param src 
     */
    Path(int src){
        vertices[0] = src;
        path_len = 1;
    }
    
    /**
     * constructor to create a path that adds one additional vertex to an exising path
     * @param past 
     */
    Path(Path past, int v, int edge_cost){
        this.vertices = past.getPath().clone();
        this.path_len = past.getPathLen();
        this.cost = past.getCost();
        addEdge(v, edge_cost);
    }
    
    /**
     * method to add an edge to a path
     * @param v
     * @param edge_cost 
     */
    public void addEdge(int v, int edge_cost){
        // check array is correctly sized
        // if not, double the array size
        if (path_len == vertices.length){
            doubleArrSize();
        }
        
        // add edge to array
        vertices[path_len] = v;
        // increment path length
        path_len++;
        // adjust cost
        cost += edge_cost;
    }
    
    /**
     * method to compare paths to another path
     * returns 1 if current path cost is greater than object path cost
     * returns 0 if the two costs are equal
     * returns -1 if the current path cost is less than object path cost
     * @param other
     * @return 
     */
    @Override
    public int compareTo(final Path object){
        if (this.cost > object.getCost()){
            return 1;
        }
        else if (this.cost == object.getCost()){
            return 0;
        }
        else{
            return -1;
        }
    }
    
    /**
     * method to print out path vertexes in order
     * @return 
     */
    @Override
    public String toString(){
        String val = "";
        for (int i = 0; i < path_len; i++){
            val += Integer.toString(vertices[i]);
            if (i != path_len - 1){
                val += "->";
            }
        }
        return val + "\n";
    }
    
    /**
     * method to double the array size
     */
    public void doubleArrSize(){
        int[] temp = new int[2*vertices.length];
        for (int i = 0; i < vertices.length; i++){
            temp[i] = vertices[i];
        }
        vertices = temp;
    }
    
    public int[] getPath(){ return vertices; }
    public int getPathLen(){ return path_len; }
    public int getCost(){ return cost; }
    
    public int getSource(){ return vertices[0]; }
    public int getTerminal(){ return vertices[path_len - 1]; }    
}
