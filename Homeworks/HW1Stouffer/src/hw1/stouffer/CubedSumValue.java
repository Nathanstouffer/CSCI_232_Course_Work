/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw1.stouffer;

/**
 * Node objects in priority queue
 * @author natha
 */
public class CubedSumValue {
    private long i = 0;
    private long j = 0;
    private long cubeSum = 0;
    
    /**
     * Constructor
     * @param a
     * @param b 
     */
    CubedSumValue(long a, long b){
        i = a;
        j = b;
        cubeSum = a*a*a + b*b*b;
    }

    /**
     * Setter methods
     */
    public void setI(long i){
        this.i = i;
        cubeSum = this.i*this.i*this.i + j*j*j;
    }
    public void setJ(long j){
        this.j = j;
        cubeSum = i*i*i + this.j*this.j*this.j;
    }
    
    /**
     * getter methods
     * @return 
     */
    public long getI() { return i; }
    public long getJ() { return j; }
    public long getCubeSum() { return cubeSum; }
}
