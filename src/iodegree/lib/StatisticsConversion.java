/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.special.Erf;

/**
 * Created by IntelliJ IDEA. User: Niklaas Colaert Date: 6-Nov-2008 Time:
 * 12:23:33
 *
 * Update by Libra Zhang to work with the new apache math3 library
 * Date:2012/07/24
 */
/**
 * This class calculates the p value from a Z score. This is done by an "Error
 * Function".
 */
public class StatisticsConversion {

    // Static normal distribution dist in order to transform z-socres into probabilities.
    //Two-tailed Confidence Level (|Z| < 1)
    private static NormalDistribution dist = new NormalDistribution(0.0, 1.0);

    /**
     * The calculation is done here. Example: From 1.96 (Z-score) to 0.95 %
     * (P-value) From -1.96 (Z-score) to -0.95%
     *
     * @return double a p value
     */
    public static double twoTailConfiLevel(double aZValue) {
        aZValue = aZValue / Math.sqrt(2.0);
        double lPvalue = 0.0;
        try {
            lPvalue = Erf.erf(aZValue);
        } catch (MaxCountExceededException e) {
            System.err.println(e.toString());
        }
        return lPvalue;
    }

    public static double twoTailPvalue(double aZValue) {
        return 1 - twoTailConfiLevel(aZValue);
    }

    public static double oneTailPvalue(double aZValue) {
        return (1 - twoTailConfiLevel(aZValue)) / 2;
    }

    /**
     * The quantile calculation is done here. -1.96 : +1.96 returns 0.95
     *
     * @return double a p value
     */
    public static double cumulativeProbability(double aZValue) {
        double lQuantile = 0.5;
        try {
            lQuantile = dist.probability(aZValue);
        } catch (MaxCountExceededException e) {
            System.err.println(e.toString());
        }
        return lQuantile;
    }

    /**
     * The quantile calcuation is done here. From -1.96 returns 0.025 While 1.96
     * returns 0.975
     *
     * @return double a p value
     */
    public static double cumulativeProbability(double aLowerZValue, double aHigherZValue) {
        double lQuantile = 0.5;
        try {
            lQuantile = dist.probability(aLowerZValue, aHigherZValue);
        } catch (MaxCountExceededException e) {
            System.err.println(e.toString());
        }
        return lQuantile;
    }

    /**
     * The z-score calculation is done here. Example: From 0.95 returns 1.64
     * While 0.05 returns -1.64 And 0.975 returns 1.96
     *
     * @param aProbability
     * @return double aZscore
     */
    public static double inverseCumulativeProbability(final double aProbability) {
        double lZscore = 0;
        try {
            lZscore = dist.inverseCumulativeProbability(aProbability);
        } catch (MaxCountExceededException e) {
            System.err.println(e.toString());
        }
        return lZscore;
    }

    /**
     * Algorithm as241 appl. statist. (1988) 37(3):477-484. produces the normal
     * deviate z corresponding to a given lower tail area of p; z is accurate to
     * about 1 part in 10**7.
     * <p/>
     * The hash sums below are the sums of the mantissas of the coefficients.
     * They are included for use in checking transcription. This method is based
     * on the C code that can be found on the following website:
     * http://download.osgeo.org/grass/grass6_progman/as241_8c-source.html .
     *
     * @param aQuantile the quantile to retrieve. 0.05 returns -1.96 while 0.95
     * returns 1.96.
     * @return double with the calculated Z score
     */
    private static double calculateZscore(double aQuantile) {


        double zero = 0.0, one = 1.0, half = 0.5;
        double split1 = 0.425, split2 = 5.0;
        double const1 = 0.180625, const2 = 1.6;

        /*
         * coefficients for p close to 0.5
         */
        double[] a = {3.3871327179, 5.0434271938e+01, 1.5929113202e+02, 5.9109374720e+01};
        double[] b = {0.0, 1.7895169469e+01, 7.8757757664e+01, 6.72E+01};

        /*
         * hash sum ab 32.3184577772
         */
        /*
         * coefficients for p not close to 0, 0.5 or 1.
         */
        double[] c = {1.4234372777e+00, 2.7568153900e+00, 1.3067284816e+00, 1.7023821103e-01};
        double[] d = {0.0, 7.3700164250e-01, 1.2021132975e-01};

        /*
         * hash sum cd 15.7614929821
         */
        /*
         * coefficients for p near 0 or 1.
         */
        double[] e = {6.6579051150e+00, 3.0812263860e+00, 4.2868294337e-01, 1.7337203997e-02};
        double[] f = {0.0, 2.4197894225e-01, 1.2258202635e-02};

        /*
         * hash sum ef 19.4052910204
         */
        double q, r, ret;

        q = aQuantile - half;
        if (Math.abs(q) <= split1) {
            r = const1 - q * q;
            ret = q * (((a[3] * r + a[2]) * r + a[1]) * r + a[0])
                    / (((b[3] * r + b[2]) * r + b[1]) * r + one);

            return ret;
        }
        /*
         * else
         */

        if (q < zero) {
            r = aQuantile;
        } else {
            r = one - aQuantile;
        }
        if (r <= zero) {
            return zero;
        }
        r = Math.sqrt(-Math.log(r));
        if (r <= split2) {
            r = r - const2;
            ret = (((c[3] * r + c[2]) * r + c[1]) * r + c[0])
                    / ((d[2] * r + d[1]) * r + one);
        } else {
            r = r - split2;
            ret = (((e[3] * r + e[2]) * r + e[1]) * r + e[0])
                    / ((f[2] * r + f[1]) * r + one);
        }

        if (q < zero) {
            ret = -ret;
        }
        return ret;


    }

    /**
     * This method will calculate the Z score in a one-sided test for a specific
     * quantile.
     *
     * @return double with the calculated Z score
     */
    public static double calculateOneSidedZscore(double aQuantile) {
        return calculateZscore(aQuantile);
    }

    /**
     * This method will calculate the Z score in a two-sided test for a specific
     * Quantile. The Quantile will first be transformed Quantile = 0.95 => alpha
     * = O.05 the quantile will be = 1-(alpha/2)
     *
     * @return double with the calculated Z score
     */
    public static double calculateTwoSidedZscore(double aQuantile) {
        double p = aQuantile;
        p = 1 - ((1 - p) / 2);
        return calculateZscore(p);
    }

    public static Object[][] fillTable(Object[] header, Object[][] content) {
        Object[][] table = new Object[content[0].length][content.length + 1];
        if (header.length == content[0].length) {
            table[0] = header;
        } else if (header.length < content[0].length) {
            int delta = content[0].length - header.length;
            System.arraycopy(header, 0, table[0], delta, header.length);
        }
        for (int i = 1; i < table.length; i++) {
            table[i] = content[i - 1];
        }
        return table;
    }

    public static Object[][] fillTable(Object[] header, Object[] rowTitle, Object[][] content) {
        Object[][] table = new Object[content[0].length+1][content.length + 1];
        if (header.length == table[0].length) {
            table[0] = header;
        } else if (header.length < table[0].length) {
            int delta = table[0].length - header.length;
            System.arraycopy(header, 0, table[0], delta, header.length);
        }
        
        for (int i = 1; i < table.length; i++) {
            table[i][0]=rowTitle[i-1];
            System.arraycopy(content[i-1], 0, table[i], 1, content[i-1].length);
        }
        return table;
    }
}
