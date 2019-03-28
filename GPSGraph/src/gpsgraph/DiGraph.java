/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsgraph;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author natha
 */
public class DiGraph {
    
    // number of vertices
    private int V = 0;
    // number of edges
    private int E = 0;
    // adjacency matrix
    // first index is for the tail of an edge
    // second index is for the head of an edge
    // if the edge does not exist, value is 0
    // if the edge exists, the value is the cost of the edge
    private int[][] adjacencymtx;
    
    /**
     * constructor that creates an appropriately sized adjacency matrix
     * @param size 
     */
    DiGraph(int size){
        V = size;
        adjacencymtx = new int[size+1][size+1];
    }
    
    /**
     * method to add edge to a graph
     * @param tail
     * @param head
     * @param weight 
     */
    public void addEdge(int tail, int head, int weight){
        adjacencymtx[tail][head] = weight;
        E++;
    }
    
    /**
     * method to return a list of adjacent vertices to v
     * @param v
     * @return 
     */
    public Iterable<Integer> adj(int v){
        return new AdjIterator(v);
    }
    
    private class AdjIterator implements Iterator<Integer>, Iterable<Integer> {
        // current source vertex
        private int v;
        // vertex that is the head of an edge whose tail is at v
        private int w = 0;

        AdjIterator(int v) {
            this.v = v;
        }
        
        @Override
        public Iterator<Integer> iterator() {
            return this;
        }
        
        @Override
        public boolean hasNext() {
            while (w < V + 1) {
                if (adjacencymtx[v][w] != 0) return true;
                w++;
            }
            return false;
        }
        
        /**
         * method to return the index of the head of an edge whose tail is at v
         * @return 
         */
        @Override
        public Integer next() {
            if (hasNext()) return w++;
            else           throw new NoSuchElementException();
        }
    }
    
    /**
     * method to return the cost of a specific edge
     * @param tail
     * @param head
     * @return 
     */
    public int getEdgeCost(int tail, int head){ return adjacencymtx[tail][head]; }
    
    /**
     * method to return number of vertices in the graph
     * @return 
     */
    public int V(){ return this.V; }
    
    /**
     * method to return the number of edges in the graph
     * @return 
     */
    public int E(){ return this.E; }
}
