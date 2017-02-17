/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.dataStrucure;

import iodegree.lib.D;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Libra
 */
public class BatchRatio {

    private HashMap<Date, int[]> slots = new HashMap<>();
    private ArrayList<Date> batches = new ArrayList<>();
    private HashMap<Date, int[]> conns = new HashMap<>();
    private ArrayList<Integer> bins;
    private Double[][] ratio;

    public void setBins(ArrayList<Integer> distBin) {
        this.bins = distBin;
    }

    public ArrayList<Integer> getBins() {
        return bins;
    }

    public void addSlot(Date batch, String binMethod, int data) {
        switch (binMethod) {
            case "DIV":
                if (slots.containsKey(batch)) {
                    slots.get(batch)[divBin(data)]++;
                } else {
                    slots.put(batch, new int[bins.size()]);
                    conns.put(batch, new int[bins.size()]);
                    batches.add(batch);
                    slots.get(batch)[divBin(data)]++;
                }
                break;
            case "Dist":
                if (slots.containsKey(batch)) {
                    slots.get(batch)[distBin(data)]++;
                } else {
                    slots.put(batch, new int[bins.size()]);
                    conns.put(batch, new int[bins.size()]);
                    batches.add(batch);
                    slots.get(batch)[distBin(data)]++;
                }
                break;
        }
    }

    public void addConn(Date batch, String binMethod, int data) {
        switch (binMethod) {
            case "DIV":
                conns.get(batch)[divBin(data)]++;
                break;
            case "Dist":
                conns.get(batch)[distBin(data)]++;
                break;
        }
    }

    private int divBin(int div) {
        return div - 5;
    }

    private int distBin(int dist) {
        if (bins.size() > 0) {
            for (int i = 0; i < bins.size();) {
//            System.out.println(dist+","+distTop.get(i));
                if (dist > bins.get(i)) {
                    i++;
                } else {
//                    System.out.println(dist + "," + i);
                    return i;
                }
            }
//        System.out.println(distTop.size());
//        System.out.println(dist + "," + distBin.size());
            return bins.size();
        } else {
            D.p("empty DistBin");
            return 0;
        }
    }

    public ArrayList<Date> getBatches() {
        return batches;
    }

    public void genBatchRatio() {
        ratio = new Double[batches.size()][slots.get(batches.get(0)).length];
        for (int i = 0; i < ratio.length; i++) {
            for (int j = 0; j < ratio[0].length; j++) {
                if (slots.get(batches.get(i))[j] == 0) {
                    ratio[i][j] = null;
                } else if (!conns.containsKey(batches.get(i))) {
                    ratio[i][j] = 0.0d;
                    D.tpi("0", slots.get(batches.get(i))[j]);
                } else {
                    ratio[i][j] = (double) conns.get(batches.get(i))[j] / (double) slots.get(batches.get(i))[j];
                    D.tpi(conns.get(batches.get(i))[j], slots.get(batches.get(i))[j]);
                }
            }
            D.tp();
        }


    }

    public Double[][] getBatchRatio() {
        return ratio;
    }
}
