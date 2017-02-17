/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

import org.apache.commons.math3.stat.StatUtils;

/**
 *
 * @author Libra
 */
public class DescStats {

    double mean;
    double variance;
    double std;

    public DescStats(double[] in) {
        mean = StatUtils.mean(in);
        variance = StatUtils.variance(in);
        std = Math.sqrt(variance);
    }

    public DescStats(int[] intin) {
        double[] in = new double[intin.length];
        for (int i = 0; i < intin.length; i++) {
            in[i] = intin[i];
        }

        mean = StatUtils.mean(in);
        variance = StatUtils.variance(in);
        std = Math.sqrt(variance);
    }

    public double getMean() {
        return mean;
    }

    public double getSTD() {
        return std;
    }
}
