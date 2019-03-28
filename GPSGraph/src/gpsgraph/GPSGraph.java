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
        String file_path = args[0];
        
        // initialize graph from file
        DiGraph graph = createGraph(file_path);
        
        int[] shortest_path;
        
        // scanner for user input
        Scanner scan = new Scanner(System.in);
        // users menu choice
        int menu_choice = 0;
        while (menu_choice != 2){
            printMenu(graph.V());
            
            // if user chooses 1, get source and destination info
            menu_choice = scan.nextInt();
            if (menu_choice == 1){
                // array of the form {src, dest}
                int[] path_info = getPathInfo(graph.V());
                int src = path_info[0];
                int dest = path_info[1];
                
                // if the first value in path_info is 0, the user entered an invalid value
                if (path_info[0] == 0){
                    System.out.println("Invalid entry.\n");
                }
                else{
                    // find shortest path
                    long start_time = System.currentTimeMillis();
                    shortest_path = dijkstrasAlg(graph, src, dest);
                    long end_time = System.currentTimeMillis();
                    double time = (double)(end_time-start_time)/1000;
                    
                    output(shortest_path, time);
                }
            }
            else if (menu_choice != 2){
                System.out.println("Invalid entry.\n");
            }
        }
        System.out.println("\nExited.");
    }
    
    /**
     * method to return the revers of the path as an array
     * @param graph
     * @param src
     * @param dest
     * @return 
     */
    private static int[] dijkstrasAlg(DiGraph graph, int src, int dest){
        // array that stores distance from src for each vertex
        int[] distTo = new int[graph.V()+1];
        // array that stores the tail of the edge that connects to the index
        int[] edgeTo = new int[graph.V()+1];
        
        // populate distTo with values outside scope
        for (int i = 0; i < distTo.length; i++){
            distTo[i] = -1;
        }
        distTo[src] = 0;
        edgeTo[src] = -1;
        
        MinPriorityQueue<NewVertex> pq = new MinPriorityQueue<NewVertex>();
        pq.insert(new NewVertex(src, 0));
        
        // loop to find the shortest path
        while (!pq.isEmpty()){
            // variable to store current shortest path form source
            NewVertex current = pq.remove();
            // variable to store list of vertices the terminal vertex of current is connected to
            Iterable<Integer> heads = graph.adj(current.getVertex());
            
            // loop adds all possible paths branching from the terminal vertex to a min PQ
            for (int head : heads){
                int tail = current.getVertex();
                // variable to store the additional cost of adding an edge from tail to head
                int edge_dist = graph.getEdgeCost(tail, head);
                
                // branch off of current vertex
                NewVertex branch = relax(distTo, tail, head, edge_dist);
                if (branch != null){
                    edgeTo[head] = tail;
                    pq.insert(branch);
                }
                
            }
        }
        
        // array that stores the path in reverse
        int[] path = new int[3];
        path[1] = distTo[dest];
        // iter follows the shortest path, starting at the dest
        // i is the first empty index of the path array
        int iter = dest, i = 2;
        // loop to insert vertices into path array in reverse order
        while (iter != -1){
            if (i == path.length){
                path = doubleArraySize(path);
            }
            path[i] = iter;
            iter = edgeTo[iter];
            i++;
        }
        // path[0] is the last filled index in the array
        path[0] = i;
        return path;
    }
    
    private static NewVertex relax(int[] distTo, int tail, int head, int edge_dist){
        // if there is no path to head, then this is the current shortest path to head
        if (distTo[head] == -1){
            distTo[head] = distTo[tail] + edge_dist;
            return new NewVertex(head, edge_dist);
        }
        // otherwise, if this paths cost is less than the current cost to reach head,
        // this becomes the current shortest path
        else if (distTo[head] > distTo[tail] + edge_dist){
            distTo[head] = distTo[tail] + edge_dist;
            return new NewVertex(head, edge_dist);
        }
        // otherwise, the existing path to head is the shortest path
        else{
            return null;
        }
    }
    
    /**
     * Method to read in file and create graph
     * @param file_path
     * @return 
     */
    private static DiGraph createGraph(String file_path){
        DiGraph graph = null;
        
        try{
            // scanner for file input
            Scanner fin = new Scanner(new File(file_path));
            
            String[] line;
            // loop finds the line that defines the number of nodes and edges
            do{
                String input = fin.nextLine();
                line = input.split(" ");
            } while (!line[0].equals("p"));
            
            // create correctly sized graph
            int v = Integer.parseInt(line[2]);
            graph = new DiGraph(v);
            
            // loop populates graph with edges
            while (fin.hasNextLine()){
                String input = fin.nextLine();                                  
                line = input.split(" ");
                
                if (line[0].equals("a")){
                    // lines that begin with the character a create an weigted edge between to vertices
                    // such lines will be of the form {"a", "tail", "head", "weight"}
                    // where all values are strings but "tail", "head", and "wweight" can be converted to ints
                    int tail = Integer.parseInt(line[1]);
                    int head = Integer.parseInt(line[2]);
                    int cost = Integer.parseInt(line[3]);
                    graph.addEdge(tail, head, cost);
                }
            }
            
            fin.close();
        }
        catch (FileNotFoundException e){
            System.err.println("Opening file error");
        }
        
        return graph;
    }
    
    /**
     * method to get path information from user
     * returns the array {0, 0} if user enters invalid entry
     * @param v
     * @return 
     */
    private static int[] getPathInfo(int v){
        // scanner for user input
        Scanner scan = new Scanner(System.in);
        
        System.out.print("Source: ");
        int src = scan.nextInt();

        // ensures src is a valid entry
        if (src < 1 || src > v){
            return new int[] {0, 0};
        }
        
        System.out.print("Destination: ");
        int dest = scan.nextInt();

        // ensures dest is a valid entry
        if (dest < 1 || dest > v){
            return new int[] {0, 0};

        }
        
        return new int[] {src, dest};
    }
    
    private static int[] doubleArraySize(int[] arr){
        int[] temp = new int[2*arr.length];
        for (int i = 0; i < arr.length; i++){
            temp[i] = arr[i];
        }
        return temp;
    }
    
    /**
     * Method to print menu
     * @param max 
     */
    private static void printMenu(int max){
        System.out.println(String.format("The current graph has vertices from 1 to %d.\n"
                + "Would you like to:\n1. Find a new route\n2. Exit", max));
    }
    
    /**
     * method to output path information
     * @param path
     * @param start
     * @param end 
     */
    private static void output(int[] path, double time){
        int len = path[0];
        int cost = path[1];
        int dest = path[2];
        int src = path[len-1];                    

        System.out.println(String.format("\nRESULTS\nShortest path from %d to %d", src, dest));
        String output = "Path: ";
        for (int i = len - 1; i > 1; i--){
            output += Integer.toString(path[i]);
            if (i != 2){
                output += "->";
            }
        }
        System.out.println(output);
        System.out.println(String.format("Total cost: %d", cost));
        System.out.println(String.format("Total time: %.3f sec\n", time));
    }
}
