/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.weight;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

/**
 *
 * @author Libra
 */
public class ClusterWeightResult {
    private int histoCount=0;
    private SummaryStatistics gluStats=new SummaryStatistics();
    private SummaryStatistics gabaStats=new SummaryStatistics();
    private int gluN=0;
    private int gabaN=0;
    
    public void addCount(){
        histoCount++;
    }
    


    public double getGabaMean() {
        return gabaStats.getMean();
    }

    public double getGabaSD() {
        return gabaStats.getStandardDeviation();
    }

    public double getGluMean() {
        return gluStats.getMean();
    }

    public double getGluSD() {
        return gluStats.getStandardDeviation();
    }
    
    public double getGluSEM(){
        return gluStats.getStandardDeviation()/Math.sqrt(gluN);
    }
    public double getGABASEM(){
        return gabaStats.getStandardDeviation()/Math.sqrt(gabaN);
    }

    public int getHistoCount() {
        return histoCount;
    }
    
    public void addGluStats(int in){
        gluStats.addValue(in);
        gluN++;
    }
    public void addGABAStats(int in){
        gabaStats.addValue(in);
        gabaN++;
    }
    
}
