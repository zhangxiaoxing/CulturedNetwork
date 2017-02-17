/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.interact;

/**
 *
 * @author Libra
 */
public class InteractItem {

    private int id;
    private boolean isGlu;
    private int outSlots;
    private int outConn;
    private int gluInSlots;
    private int gluInConn;
    private int gabaInSlots;
    private int gabaInConn;
    private double gluInRatio;
    private double gabaInRatio;
    private double outRatio;

    public void setId(int id) {
        this.id = id;
    }

    public void setIsGlu(boolean isGlu) {
        this.isGlu = isGlu;
    }

    public void setOutSlots(int outSlots) {
        this.outSlots = outSlots;
    }

    public void setOutConn(int outConn) {
        this.outConn = outConn;
    }

    public void setGluInSlots(int gluInSlots) {
        this.gluInSlots = gluInSlots;
    }

    public void setGabaInSlots(int gabaInSlots) {
        this.gabaInSlots = gabaInSlots;
    }

    public void setGluInConn(int gluInConn) {
        this.gluInConn = gluInConn;
    }

    public void setGabaInConn(int gabaInConn) {
        this.gabaInConn = gabaInConn;
    }

    public void printAll() {
        outRatio = (double) outConn / outSlots;
        gluInRatio = (double) gluInConn / gluInSlots;
        gabaInRatio = (double) gabaInConn / gabaInSlots;
        System.out.println(id + "," + isGlu + "," + outRatio + "," + gluInRatio + "," + gabaInRatio);
    }

    public boolean isGlu() {
        return isGlu;
    }

    public double getOutRatio() {
        return (double) outConn / outSlots;
    }

    public double getGabaInRatio() {
        return gabaInSlots == 0 ? -1 : (double) gabaInConn / gabaInSlots;
    }

    public double getGluInRatio() {
        return gluInSlots == 0 ? -1 : (double) gluInConn / gluInSlots;
    }
}
