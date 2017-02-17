/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.dataStrucure;

/**
 *
 * @author Libra
 */
public class ConnProbResult {

    private int binSize;
    private int lowBound;
    private int highBound;
    
    private int nConn;
    private float connProb;
    private int avg;
    private String s;

    public void fill() {
        avg = (highBound + lowBound) / 2;
        connProb = (float) nConn / binSize;
//        s = avg + "," + connProb;
    }
    
    public ConnProbResult(int bin, int low, int high){
        binSize=bin;
        lowBound=low;
        highBound=high;
    }

//    void print() {
//        System.out.println(s);
//    }
    
    public int getHighBound(){
        return highBound;
    }
    
    public void addNConn(){
        nConn++;
    }

    @Override
    public String toString() {
        return s;
    }
    
    public int getAvg(){
        return avg;
    }
    
    public float getConnProb(){
        return connProb;
    }
}
