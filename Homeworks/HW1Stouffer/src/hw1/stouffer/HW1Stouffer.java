/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1.stouffer;

/**
 *
 * @author natha
 */
public class HW1Stouffer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int n = 100000;
        MinPQ heap = new MinPQ(n); // heap containing cubes
        
        long j = 0;
        for (long i = 0; i <= n; i++){ // initial filling of heap
            CubedSumValue temp = new CubedSumValue(i, j);
            heap.insert(temp);
        }
        
        long[] previous_min_data = {1, 0, 1}; // array of the form {cubed_sum, number1, nuumber2}
        while (!heap.isEmpty()){ // running while the heap still has values
            CubedSumValue current_min = heap.remove();
            if (current_min.getCubeSum() == previous_min_data[0]){ // statement to test if value already exists in output, will print out factors if already in output
                if (current_min.getI() != previous_min_data[2] && current_min.getJ() != previous_min_data[1]
                        && current_min.getI() != previous_min_data[1] && current_min.getJ() != previous_min_data[2]){
                    System.out.print(" = " + previous_min_data[1] + "^3 + " + previous_min_data[2] + "^3 = "
                                        + current_min.getI() + "^3 + " + current_min.getJ() + "^3");
                }
            }
            else{
                System.out.print("\n" + current_min.getCubeSum());
            }
            
            previous_min_data[0] = current_min.getCubeSum();
            previous_min_data[1] = current_min.getI();
            previous_min_data[2] = current_min.getJ();
            
            if (current_min.getJ() < n){
                current_min.setJ(current_min.getJ()+1);
                heap.insert(current_min);
            }
        }
        
        System.out.println();
    }
    
}
