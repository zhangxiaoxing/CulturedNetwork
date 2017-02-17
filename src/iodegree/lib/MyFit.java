/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 *
 * @author Libra
 */
public class MyFit {

    private SimpleRegression regres;

    public MyFit(double[][] regData) {
        regres = new SimpleRegression();
        regres.addData(regData);
    }

    public double getIntercept() {
        return regres.getIntercept();
    }

    public double getSlope() {
        return regres.getSlope() * 1000;
    }

    public float predict(int dist) {
        float temp = (float) (regres.predict(dist));
        return temp > 0 ? temp : 0;
    }
    
    public double getR(){
        return regres.getR();
    }
}
