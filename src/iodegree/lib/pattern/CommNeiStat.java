/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.pattern;

import org.apache.commons.math3.random.Well44497b;

/**
 *
 * @author Libra
 */
public class CommNeiStat {

    private Sub GG = new Sub();
    private Sub GA = new Sub();
    private Sub AG = new Sub();
    private Sub AA = new Sub();
    private Sub pairs;

    public void addGG(int conned, int gluNeib, int gabaNeib) {
        GG.add(conned, gluNeib, gabaNeib);
    }

    public void addGA(int conned, int gluNeib, int gabaNeib) {
        GA.add(conned, gluNeib, gabaNeib);
    }

    public void addAG(int conned, int gluNeib, int gabaNeib) {
        AG.add(conned, gluNeib, gabaNeib);
    }

    public void addAA(int conned, int gluNeib, int gabaNeib) {
        AA.add(conned, gluNeib, gabaNeib);
    }

    public void setType(int type) {
        switch (type) {
            case 0:
                pairs = GG;
                break;
            case 1:
                pairs = GA;
                break;
            case 2:
                pairs = AG;
                break;
            case 3:
                pairs = AA;
                break;
            default:
                pairs = GG;
        }
        pairs.calc();
    }

    public void setCycles(int cycles) {
        pairs.setCycles(cycles);
    }

    public Double[] getProb() {
        return pairs.getProb();
    }

    public Double[] getSem() {
        return pairs.getSEM();
    }

    public Double[] getP() {
        return pairs.getP();
    }

    public Integer[] getN() {
        return pairs.getN();
    }

    public String[] getTitle() {
        String[] title = {"NO Common Neighbor", "Common Neighbor", "No Glu Common Neighbor",
            "Glu Common Neighbor", "No GABA Common Neighbor", "GABA Common Neighbor",
            "Only Glu Common Neibor", "Only GABA Common Neighbor"};
        return title;
    }

    class Sub {
        //[Any][G][A]

        private int total = 0;
        private Subsub C = new Subsub();
        private Subsub NC = new Subsub();
        Double[] prob = new Double[9];
        private Double[] sem = new Double[9];
        private Double[] p = new Double[9];
        private Integer[] n = new Integer[9];

        public void setCycles(int cycles) {
            p[1] = monteCarlo(NC.WOC + C.WOC, C.WOC, NC.WC + C.WC, C.WC, cycles);
            p[3] = monteCarlo(NC.WOG + C.WOG, C.WOG, NC.WG + C.WG, C.WG, cycles);
            p[5] = monteCarlo(NC.WOA + C.WOA, C.WOA, NC.WA + C.WA, C.WA, cycles);
            p[6]=monteCarlo(NC.WOC + C.WOC, C.WOC, NC.WGG + C.WGG, C.WGG, cycles);
            p[7]=monteCarlo(NC.WOC + C.WOC, C.WOC, NC.WAA + C.WAA, C.WAA, cycles);
        }

        private double monteCarlo(int s1n, int s1p, int s2n, int s2p, int cycles) {
            Well44497b r = new Well44497b();
            int getP = 0;
            for (int i = 0; i < cycles; i++) {
                int count = 0;
                for (int j = 0; j < s2n; j++) {
                    if (r.nextInt(s1n) < s1p) {
                        count++;
                    }
                }
                getP += count >= s2p ? 1 : 0;
            }
//        D.p(Integer.toString(ratio));
            return (double) getP / cycles;
        }

        void add(int conned, int gluNeib, int gabaNeib) {
            total++;
            Subsub to = (conned != 0) ? C : NC;
            to.add(gluNeib, gabaNeib);
        }

        void calc() {
            n[0] = C.WOC + NC.WOC;
            n[1] = C.WC + NC.WC;
            n[2] = C.WOG + NC.WOG;
            n[3] = C.WG + NC.WG;
            n[4] = C.WOA + NC.WOA;
            n[5] = C.WA + NC.WA;
            n[6] = C.WGG + NC.WGG;
            n[7] = C.WAA + NC.WAA;
//            n[9] = C.WAG + NC.WAG;

            
            prob[0] = (double) C.WOC / n[0];
            prob[1] = (double) C.WC / n[1];
            prob[2] = (double) C.WOG / n[2];
            prob[3] = (double) C.WG / n[3];
            prob[4] = (double) C.WOA / n[4];
            prob[5] = (double) C.WA / n[5];
            prob[6] = (double) C.WGG / n[6];
            prob[7] = (double) C.WAA / n[7];

            sem[0] = (double) Math.sqrt(prob[0] * (1 - prob[0]) / n[0]);
            sem[1] = (double) Math.sqrt(prob[1] * (1 - prob[1]) / n[1]);
            sem[2] = (double) Math.sqrt(prob[2] * (1 - prob[2]) / n[2]);
            sem[3] = (double) Math.sqrt(prob[3] * (1 - prob[3]) / n[3]);
            sem[4] = (double) Math.sqrt(prob[4] * (1 - prob[4]) / n[4]);
            sem[5] = (double) Math.sqrt(prob[5] * (1 - prob[5]) / n[5]);
            sem[6] = (double) Math.sqrt(prob[6] * (1 - prob[6]) / n[6]);
            sem[7] = (double) Math.sqrt(prob[7] * (1 - prob[7]) / n[7]);

        }

        Double[] getProb() {

            return prob;
        }

        Double[] getSEM() {
            return sem;
        }

        Integer[] getN() {
//            D.tp(Arrays.toString(n));
            return n;
        }

        public Double[] getP() {
            return p;
        }

        class Subsub {

            private int WOC = 0;
            private int WC = 0;
            private int WOG = 0;
            private int WG = 0;
            private int WOA = 0;
            private int WA = 0;
            private int WAA = 0;
            private int WGG = 0;
//            private int WAG=0;

            public void add(int gluNei, int gabaNei) {
                if (gluNei + gabaNei == 0) {
                    WOC++;
                } else {
                    WC++;
                }
                if (gluNei == 0) {
                    WOG++;
                } else {
                    WG++;
                }
                if (gabaNei == 0) {
                    WOA++;
                } else {
                    WA++;
                }
//                if (gluNei!=0&&gabaNei!=0){
//                    WAG++;
//                }
                if (gluNei != 0 && gabaNei == 0) {
                    WGG++;
                }
                if (gluNei == 0 && gabaNei != 0) {
                    WAA++;
                }

            }
        }
    }
}
