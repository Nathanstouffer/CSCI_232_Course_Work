/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsgraph;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author natha
 */
public class GPSGraph {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String file_path = "testGraph.txt";
        // String file_path = args[0];
        
        DiGraph graph = createGraph(file_path);
    }
    
    /**
     * Method to read in file and create graph
     * @param file_path
     * @return 
     */
    private static DiGraph createGraph(String file_path){
        DiGraph graph = null;
        
        try{
            Scanner fin = new Scanner(new File(file_path));
            
            String[] line;
            // loop finds the problem line, which defines the number of nodes and edges
            do{
                String input = fin.nextLine();
                line = input.split(" ");
            } while (!line[0].equals("p"));
            
            // create correctly sized graph
            int v = Integer.parseInt(line[2]);
            graph = new DiGraph(v);
            
            while (fin.hasNextLine()){
                String input = fin.nextLine();                                  
                line = input.split(" ");
                
                // lines that begin with the character a create an weigted edge between to vertices
                // such lines will be of the form {"a", "tail", "head", "weight"}
                // where all values are strings but "tail", "head", and "wweight" can be converted to ints
                int tail = Integer.parseInt(line[1]);
                int head = Integer.parseInt(line[2]);
                int weight = Integer.parseInt(line[3]);
                graph.addEdge(tail, head, weight);
            }
            
            fin.close();
        }
        catch (FileNotFoundException e){
            System.err.println("Opening file error");
        }
        
        return graph;
    }
}
