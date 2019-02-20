/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binpacker;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author natha
 */
public class BinPacker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*if (args.length != 2){
            System.out.println("Sufficient input not provided");
        }
        start(args[0], args[1]);
        */
        start("50", "input.txt");
    }
    
    public static void start(String bin_size_string, String file_path){
        int bin_size = Integer.parseInt(bin_size_string);
        
        try{
            Scanner fin = new Scanner(new File(file_path));
            
            while (fin.hasNextLine()){
                
            }
        }
        catch (Exception e){
            System.err.println("Opening file error");
        }
    }

}
