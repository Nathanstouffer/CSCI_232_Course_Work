/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw2stouffer;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author natha
 */
public class HW2Stouffer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        symbolTableExercise();
    }
    
    public static void symbolTableExercise(){
        HashMap<String, Double> gpa_convert = new HashMap<String, Double>();    // create symbol table
        
        // insert keys
        gpa_convert.put("A+", 4.33);
        gpa_convert.put("A", 4.00);
        gpa_convert.put("A-", 3.67);
        gpa_convert.put("B+", 3.33);
        gpa_convert.put("B", 3.00);
        gpa_convert.put("B-", 2.67);
        gpa_convert.put("C+", 2.33);
        gpa_convert.put("C", 2.00);
        gpa_convert.put("C-", 1.67);
        gpa_convert.put("D", 1.00);
        gpa_convert.put("F", 0.00);

        System.out.print("Insert grades: ");
        
        // read input
        Scanner input = new Scanner(System.in);
        String value = input.nextLine();
        input.close();
        
        String[] grades = value.split(" ");
        int grade_count = 0;                        // keeps track of number of grades
        double gpa = 0.00;                          // variable to store gpa
        for (int i = 0; i < grades.length; i++){
            gpa += gpa_convert.get(grades[i]);
            grade_count++;
        }
        
        gpa /= grade_count;
        System.out.println(String.format("GPA: %.2f", gpa));
    }
    
}
