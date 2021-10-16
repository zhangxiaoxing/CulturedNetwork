/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.reci;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author Libra
 */
public class ReciCalc {

    String pathToFile;
    Random r;
    final private ReciDB db;

    private HashSet<Integer> keySet;
    private HashMap<Integer, Integer> connMapMaster;
    private HashMap<Integer, Integer> slotMapMaster;
    ArrayList<PotentialSynapse> slots;
    ArrayList<PotentialSynapse> conns;

    public ReciCalc(String path) {
        pathToFile = path;
        db = new ReciDB(pathToFile);
        r = new Random();
        slots = db.getSlots();
        conns = db.getConns();
        init();
    }

    private boolean connected(int key, HashMap<Integer, Integer> connMap, HashMap<Integer, Integer> slotMap) {
        double prob = r.nextDouble();
        if (connMap.containsKey(key)) {
            int connLeft = connMap.get(key);
            int slotLeft = slotMap.get(key);
            double luck = (double) connLeft / slotLeft;
            return luck > prob;
        } else {
            return false;
        }
    }

    private void init() {
        //Globally defined
        slotMapMaster = new HashMap<>(100);
        connMapMaster = new HashMap<>(100);

        int[][] slotMap = new int[12][7];
        int[][] connMap = new int[12][7];

        keySet = new HashSet<>(100);
        for (PotentialSynapse ps : slots) {
            int key = getKey(ps.getFwdGlu(), ps.getRevGlu(), ps.getDiv(), ps.getDist());
            keySet.add(key);
            slotMap[yPos(ps.getFwdGlu(), ps.getRevGlu(), ps.getDiv())][getDistBin(ps.getDist())]++;
            if (slotMapMaster.containsKey(key)) {
                slotMapMaster.put(key, slotMapMaster.get(key) + 1);
            } else {
                slotMapMaster.put(key, 1);
            }
        }

        for (PotentialSynapse conn : conns) {
            int key = getKey(conn.getFwdGlu(), conn.getRevGlu(), conn.getDiv(), conn.getDist());
            connMap[yPos(conn.getFwdGlu(), conn.getRevGlu(), conn.getDiv())][getDistBin(conn.getDist())]++;
            if (connMapMaster.containsKey(key)) {
                connMapMaster.put(key, connMapMaster.get(key) + 1);
            } else {
                connMapMaster.put(key, 1);
            }
        }
//        for (int y = slotMap.length - 1; y >= 0; y--) {
//            for (int x = 0; x < slotMap[0].length; x++) {
//                System.out.print(connMap[y][x]);
//                System.out.print("\t");
//            }
//            System.out.print("\n");
//        }
//        System.out.println("===================");
//        for (int y = slotMap.length - 1; y >= 0; y--) {
//            for (int x = 0; x < slotMap[0].length; x++) {
//                System.out.print(slotMap[y][x]);
//                System.out.print("\t");
//            }
//            System.out.print("\n");
//        }

    }

    private int yPos(boolean preGlu, boolean postGlu, int div) {
        //Y:0-11:GluGluD5-D8,GluGABAD5-D8,GABAGABAD5-D8
        
        if (preGlu && postGlu) {
            return (div - 5);
        } else if (preGlu != postGlu) {
            return div - 1;
        } else {
            return div + 3;
        }
    }

    private int yPos(int type, int div) {
        switch (type) {
            case 0:
                return div + 3;
            case 1:
                return div - 1;
            default:
                return div - 5;
        }
    }

    private int getKey(boolean fwdGlu, boolean revGlu, int div, double dist) {
        int key = 0;
        int bin = getDistBin(dist);
        key += (fwdGlu ? 0 : 1) << 14;
        key += (revGlu ? 0 : 1) << 12;
        key += div << 6;
        key += bin;
        return key;
    }

    private int getDistBin(double dist) {
        double[] bins = {50, 100, 150, 200, 250, 350, 550};
        for (int i = 0; i < bins.length; i++) {
            if (dist < bins[i]) {
                return i;
            }
        }
        throw new IllegalArgumentException("Uncatched distance");
    }

    private int getKey(int id1, int id2) {
        return (id1 << 12) + id2;
    }

    public double[][] randReci() {
        HashMap<Integer, Integer> connMap;
        HashMap<Integer, Integer> slotMap;
        connMap = new HashMap<>(1000);
        slotMap = new HashMap<>(1000);
        for (Integer key : keySet) {
            slotMap.put(key, slotMapMaster.get(key));
            if (connMapMaster.containsKey(key)) {
                connMap.put(key, connMapMaster.get(key));
            }
        }

        HashMap<Integer, int[]> fwdConnMap = new HashMap<>(1000);
        HashSet<Integer> revConnMap = new HashSet<>(1000);

        int[][] reciConn = new int[12][7];
        int[][] reciSlot = new int[12][7];
        double[][] reciProb = new double[12][7];

        for (PotentialSynapse ps : slots) {
            if (ps.getId1() < ps.getId2()) {
                reciSlot[yPos(ps.getFwdGlu(), ps.getRevGlu(), ps.getDiv())][getDistBin(ps.getDist())]++;
            }
            int mapKey = getKey(ps.getFwdGlu(), ps.getRevGlu(), ps.getDiv(), ps.getDist());
            if (connected(mapKey, connMap, slotMap)) {
                connMap.put(mapKey, connMap.get(mapKey) - 1);
                int setKey = getKey(ps.getId1(), ps.getId2());
                if (ps.getId1() < ps.getId2()) {
                    fwdConnMap.put(setKey, new int[]{(ps.getFwdGlu() ? 1 : 0) + (ps.getRevGlu() ? 1 : 0), ps.getDiv(), getDistBin(ps.getDist())});
                } else {
                    revConnMap.add(getKey(ps.getId2(), ps.getId1()));
                }
            }
            slotMap.put(mapKey, slotMap.get(mapKey) - 1);
        }

        for (int i : fwdConnMap.keySet()) {
            if (revConnMap.contains(i)) {
                reciConn[yPos(fwdConnMap.get(i)[0], fwdConnMap.get(i)[1])][fwdConnMap.get(i)[2]]++;
            }
        }

        for (int i = 0; i < reciProb.length; i++) {
            for (int j = 0; j < reciProb[0].length; j++) {
                reciProb[i][j] = (double) reciConn[i][j] / reciSlot[i][j];
            }
        }
        return reciProb;

    }

    public int[] countReci() {
        HashMap<Integer, Integer> typeMap = new HashMap<>(1000);
        HashSet<Integer> fwdConnMap = new HashSet<>(1000);
        HashSet<Integer> revConnMap = new HashSet<>(1000);
        for (PotentialSynapse ps : conns) {
            if (ps.getId1() < ps.getId2()) {
                fwdConnMap.add(getKey(ps.getId1(), ps.getId2()));
                if (ps.getFwdGlu() && ps.getRevGlu()) {
                    typeMap.put(getKey(ps.getId1(), ps.getId2()), 0);
                } else if (ps.getFwdGlu() || ps.getRevGlu()) {
                    typeMap.put(getKey(ps.getId1(), ps.getId2()), 1);
                } else {
                    typeMap.put(getKey(ps.getId1(), ps.getId2()), 2);
                }
            } else {
//                D.tp("hit");
                revConnMap.add(getKey(ps.getId2(), ps.getId1()));
            }

        }
        int[] reciCount = new int[3];

        for (int i : fwdConnMap) {
//            D.tp(i>>>12+","+i&4095);
            if (revConnMap.contains(i)) {
                reciCount[typeMap.get(i)]++;
            }
        }
        return reciCount;
    }
}
