/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw3stouffer;

import java.util.Random;
import java.util.Arrays;

/**
 *
 * @author natha
 */
public class HW3Stouffer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        exercise4_2_17();
        boernerTheorem(5);
        boernerTheorem(10);
        boernerTheorem(50);
        boernerTheorem(100);
        boernerTheorem(1000);
    }
    
    /**
     * method to solve Exercise 4.2.17
     * Uses classes StronglyCC.java and In.java
     */
    public static void exercise4_2_17(){
        
        DiGraph graph = new DiGraph(new In("mediumDG.txt"));
        StronglyCC strong = new StronglyCC(graph);
        
        System.out.println("Number of strongly connected components: " + strong.count());
    }
    
    /**
     * method that tests Boerner's Theorem
     * @param arr
     * @return 
     */
    public static boolean boernerTheorem(int size){
        // matrix where mtx[i] accesses the ith column of the matrix
        // mtx[i][j] accesses the item in the jth row of the ith column
        int[][] mtx = new int[size][size];
        Random rand = new Random();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                mtx[i][j] = rand.nextInt();
            }
        }
        
        // initially sort the columns
        for (int i = 0; i < size; i++){
            Arrays.sort(mtx[i]);
        }
        
        // sort the rows
        for (int j = 0; j < size; j++){
            int[] row = new int[size];
            for (int i = 0; i < size; i++){
                row[i] = mtx[i][j];
            }
            Arrays.sort(row);
            for (int i = 0; i < size; i++){
                mtx[i][j] = row[i];
            }
        }
        
        // check if columns are still sorted
        for (int i = 0; i < size; i++){
            for (int j = 1; j < size; j++){
                // return false if the columns are out of order
                if (mtx[i][j] < mtx[i][j-1]){
                    System.out.println("For a randomly populated " + size + "x" + size + " matrix, Boerner's Theorem proved to be false.");
                    return false;
                }
            }
        }
        
        System.out.println("For a randomly populated " + size + "x" + size + " matrix, Boerner's Theorem proved to be true.");
        
        return true;
    }
}
