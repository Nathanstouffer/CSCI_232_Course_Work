/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gpsgraph;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
//import java.util.Iterator;

/**
 *
 * @author natha
 */
public class GPSGraph {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String file_path = "testGraph20.txt";
        // String file_path = args[0];
        
        // initialize graph from file
        DiGraph graph = createGraph(file_path);
        
        Path shortest_path = null;
        
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
                    shortest_path = dijkstrasAlg(graph, src, dest);
                    System.out.println(String.format("\nShortest path from %d to %d", src, dest));
                    System.out.print(shortest_path);
                    System.out.println(String.format("Total cost: %d\n", shortest_path.getCost()));
                }
            }
            else if (menu_choice != 2){
                System.out.println("Invalid entry.\n");
            }
        }
        System.out.println("\nExited.");
    }
    
    private static Path dijkstrasAlg(DiGraph graph, int src, int dest){        
        MinPriorityQueue<Path> pq = new MinPriorityQueue<Path>();
        pq.insert(new Path(src));
        
        while (pq.peek().getTerminal() != dest){
            // variable to store current shortest existing path
            Path current = pq.remove();
            // list of vertexes that the current terminal vertex is connected to
            Iterable<Integer> heads = graph.adj(current.getTerminal());
            
            // loop adds all possible paths branching from the terminal vertex to a min PQ
            for (int head : heads){
                pq.insert(new Path(current, head, graph.getEdgeCost(current.getTerminal(), head)));
            }
        }
        
        // return shortest path from src to dest
        return pq.remove();
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
    
    /**
     * Method to print menu
     * @param max 
     */
    private static void printMenu(int max){
        System.out.println(String.format("The current graph has vertices from 1 to %d.\n"
                + "Would you like to:\n1. Find a new route\n2. Exit", max));
    }
}
