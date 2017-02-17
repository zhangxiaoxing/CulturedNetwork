/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.reci;

import iodegree.lib.D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.math3.random.Well44497b;

/**
 *
 * @author Libra
 */
public class ReciCalc {

    public ReciCalc(String path) {
        pathToFile = path;
        db = new ReciDB(pathToFile);
        r = new Well44497b();
        binSize = 50;
        connMap = new HashMap<>(1000);
        slotMap = new HashMap<>(1000);
        slots = db.getSlots();
        conns = db.getConns();
        init();
    }
    String pathToFile;
    Well44497b r;
    private int binSize;
    private ReciDB db;
    private HashMap<Integer, Integer> connMap;
    private HashMap<Integer, Integer> slotMap;
    private HashSet<Integer> keySet;
    private HashMap<Integer, Integer> connMapMaster;
    private HashMap<Integer, Integer> slotMapMaster;
    ArrayList<PotentialSynapse> slots;
    ArrayList<PotentialSynapse> conns;

//    private boolean connected(boolean fwdGlu, boolean revGlu, int div, double dist) {
    private boolean connected(int key) {
        double prob = r.nextDouble();
//        int key = getKey(fwdGlu, revGlu, div, dist);
        if (connMap.containsKey(key)) {
            int connLeft = connMap.get(key);
            int slotLeft = slotMap.get(key);
            double luck = (double) connLeft / slotLeft;
//            D.tp(luck+","+prob);
            return luck > prob ? true : false;
        } else {
            return false;
        }
    }

    private void init() {
        //Globally defined
        //ArrayList<PotentialSynapse> slots = db.getSlots();
        slotMapMaster = new HashMap<>(100);
        connMapMaster = new HashMap<>(100);
        keySet = new HashSet<>(100);
        for (PotentialSynapse ps : slots) {
            int key = getKey(ps.getFwdGlu(), ps.getRevGlu(), ps.getDiv(), ps.getDist());
            keySet.add(key);
            if (slotMapMaster.containsKey(key)) {
                int newValue = slotMapMaster.get(key) + 1;
                slotMapMaster.put(key, newValue);
            } else {
                slotMapMaster.put(key, 1);
            }
        }


        for (PotentialSynapse conn : conns) {
            int key = getKey(conn.getFwdGlu(), conn.getRevGlu(), conn.getDiv(), conn.getDist());
//            D.tp("in" + key);
            if (connMapMaster.containsKey(key)) {
                int newValue = connMapMaster.get(key) + 1;
                connMapMaster.put(key, newValue);
            } else {
                connMapMaster.put(key, 1);
            }
        }
    }

    private int getKey(boolean fwdGlu, boolean revGlu, int div, double dist) {
        int key = 0;
        int bin = (int) dist / binSize;
        key += (fwdGlu ? 0 : 1) << 14;
        key += (revGlu ? 0 : 1) << 12;
        key += div << 6;
        key += bin;
        return key;
    }

    private int getKey(int id1, int id2) {
        return (id1 << 12) + id2;
    }

    public int[] randReci() {
        for (Integer key : keySet) {
            slotMap.put(key, slotMapMaster.get(key) + 0);
            if (connMapMaster.containsKey(key)) {
                connMap.put(key, connMapMaster.get(key) + 0);
            }
        }

        HashMap<Integer, Integer> typeMap = new HashMap<>(1000);
        HashSet<Integer> fwdConnMap = new HashSet<>(1000);
        HashSet<Integer> revConnMap = new HashSet<>(1000);

        for (PotentialSynapse ps : slots) {
            int mapKey = getKey(ps.getFwdGlu(), ps.getRevGlu(), ps.getDiv(), ps.getDist());
//            D.tpi(mapKey);
            if (connected(mapKey)) {
//                D.tpi(connMap.get(mapKey));
                connMap.put(mapKey, connMap.get(mapKey) - 1);
//                D.tpi(connMap.get(mapKey));
                int setKey = getKey(ps.getId1(), ps.getId2());
//                D.tp(key);
                if (ps.getId1() < ps.getId2()) {
                    fwdConnMap.add(setKey);
                    if (ps.getFwdGlu() && ps.getRevGlu()) {
                        typeMap.put(setKey, 0);
                    } else if (ps.getFwdGlu() || ps.getRevGlu()) {
                        typeMap.put(setKey, 1);
                    } else {
                        typeMap.put(setKey, 2);
                    }
                } else {
                    revConnMap.add(getKey(ps.getId2(), ps.getId1()));
                }
            }
//            D.tpi(slotMap.get(mapKey));
            slotMap.put(mapKey, slotMap.get(mapKey) - 1);
//            D.tp(slotMap.get(mapKey));

        }
        int[] reciCount = new int[3];

        for (int i : fwdConnMap) {
//            D.hit();
            if (revConnMap.contains(i)) {
                reciCount[typeMap.get(i)]++;
            }
        }
        return reciCount;
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
