/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.pattern;

//import iodegree.lib.reci.ReciDB;
import iodegree.lib.D;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.math3.random.Well44497b;

/**
 *
 * @author Libra
 */
public class PattList {
    //[glu uni, gaba uni, glu bi, hyb bi, gaba bi;

    private String pathToFile;
    private Well44497b r;
    private int binSize;
    private ConnDB db;
    private HashMap<Integer, Integer> slotMap;
    private HashMap<Integer, Integer[]> connMap;
    private HashMap<Integer, Integer> idToKey;
    private HashSet<Integer> gluCells;
    private ArrayList<PotentialSynapse> slots;
    private ArrayList<PotentialSynapse> conns;

    public PattList(String path) {
        pathToFile = path;
        db = new ConnDB(pathToFile);
        r = new Well44497b();
        binSize = 50;
        slots = db.getSlots();
        conns = db.getConns();
    }

    public HashMap<Integer, Boolean> getObsMap() {
        HashMap<Integer, Boolean> connIdMap = new HashMap<>(1000);
        for (PotentialSynapse ps : conns) {
            connIdMap.put(getKey(ps.getId1(), ps.getId2()), ps.getFwdGlu());
        }
        return connIdMap;
    }
    public void init() {
        slotMap = new HashMap<>();
        connMap = new HashMap<>();
        idToKey = new HashMap<>();
        gluCells = new HashSet<>(500);

        //Query Reciprocal
        HashSet<Integer> revConnMap = new HashSet<>(1000);
        for (PotentialSynapse conn : conns) {
            revConnMap.add(getKey(conn.getId1(), conn.getId2()));
        }

        //Map Slots
        for (PotentialSynapse ps : slots) {
            int id1 = ps.getId1();
            int id2 = ps.getId2();

            if (ps.getFwdGlu()) {
                gluCells.add(id1);
            }
            if (id1 < id2) {
                int mapKey = getKey(ps.getFwdGlu(), ps.getRevGlu(), ps.getDiv(), ps.getDist());
                idToKey.put(getKey(id1, id2), mapKey);
                if (slotMap.containsKey(mapKey)) {
                    int newValue = slotMap.get(mapKey) + 1;
                    slotMap.put(mapKey, newValue);
                } else {
                    slotMap.put(mapKey, 1);
                }
            }
        }

        //Map Conns
        HashSet<Integer> recipSet = new HashSet<>(500);
        for (PotentialSynapse conn : conns) {
            int mapKey = getKey(conn.getFwdGlu(), conn.getRevGlu(), conn.getDiv(), conn.getDist());
            int reciKey = getKey(conn.getId2(), conn.getId1());
            boolean recip = revConnMap.contains(reciKey);
            if (recip) {
                recipSet.add(mapKey);
            }
            int typeIdx = getIndex(conn.getFwdGlu(), conn.getRevGlu(), recip);

            if (connMap.containsKey(mapKey)) {
                Integer[] currValue = connMap.get(mapKey);
                currValue[typeIdx] += 1;
                connMap.put(mapKey, currValue);
            } else {
                Integer[] newValue = {0, 0, 0, 0, 0};
                newValue[typeIdx] = 1;
                connMap.put(mapKey, newValue);
            }
        }
        for (Integer mapKey : recipSet) {
            for (int i = 2; i < 5; i++) {
                connMap.get(mapKey)[i] /= 2;
            }
        }
    }

    private int getKey(int id1, int id2) {
        return (id1 << 12) + id2;
    }

    private int getKey(Date date, int n) {
        int prefix = (int) date.getTime() >>> 23;
        return (prefix << 4) + n;
    }

    private int getKey(boolean fwdGlu, boolean revGlu, int div, double dist) {
        int key = 0;
        int bin = (int) dist / binSize;
        int type = (fwdGlu ? 0 : 1) + (revGlu ? 0 : 1);
        key += type << 12;
        key += div << 6;
        key += bin;
        return key;
    }

    private int getIndex(boolean fwdGlu, boolean revGlu, boolean bi) {

        //[glu uni, gaba uni, glu bi, hyb bi, gaba bi;
        int idx = bi
                ? (fwdGlu && revGlu ? 2
                : (fwdGlu || revGlu ? 3 : 4))
                : (fwdGlu ? 0 : 1);
//        D.tp(idx);
        return idx;
    }

    public HashMap<Integer, Boolean> getSimMap() {

        HashMap<Integer, Boolean> map = new HashMap<>(1000);;
        HashMap<Integer, Integer> usedSlot = new HashMap<>();
        HashMap<Integer, Integer[]> usedConn = new HashMap<>();
        for (int currPs = 0; currPs < slots.size(); currPs++) {
            int id1 = slots.get(currPs).getId1();
            int id2 = slots.get(currPs).getId2();
            if (id1 < id2) {
                boolean fwdGlu = gluCells.contains(id1);
                boolean revGlu = gluCells.contains(id2);

                int mapKey = getKey(slots.get(currPs).getFwdGlu(),
                        slots.get(currPs).getRevGlu(),
                        slots.get(currPs).getDiv(),
                        slots.get(currPs).getDist());

                //get slot;
                int slot = slotMap.get(mapKey);
                if (usedSlot.containsKey(mapKey)) {
                    int currValue = usedSlot.get(mapKey);
                    slot -= currValue;
                    usedSlot.put(mapKey, currValue + 1);
                } else {
                    usedSlot.put(mapKey, 1);
                }

                int dice = r.nextInt(slot);
                int luck = 0;
                int type = -1;
                if (connMap.containsKey(mapKey)) {
                    for (int i = 0; i < 5; i++) {
                        int conn = connMap.get(mapKey)[i];
                        if (usedConn.containsKey(mapKey)) {
                            int currValue = usedConn.get(mapKey)[i];
                            conn -= currValue;
                        }
                        luck += conn;
                        if (dice < luck) {
                            type = i;
                            if (usedConn.containsKey(mapKey)) {
                                usedConn.get(mapKey)[i] += 1;
                            } else {
                                Integer[] newArray = {0, 0, 0, 0, 0};
                                newArray[i] = 1;
                                usedConn.put(mapKey, newArray);
                            }
                            break;
                        }
                    }
                }
                int fwdKey = getKey(id1, id2);
                int revKey = getKey(id2, id1);
                switch (type) {
                    case 0:
                        map.put(
                                (fwdGlu && revGlu)
                                ? ((r.nextDouble() < 0.5) ? fwdKey : revKey)
                                : (fwdGlu ? fwdKey : revKey), true);
                        break;
                    case 1:
                        map.put(
                                (!(fwdGlu || revGlu))
                                ? ((r.nextDouble() < 0.5) ? fwdKey : revKey)
                                : (fwdGlu ? revKey : fwdKey), false);
                        break;
                    case 2:
                        map.put(fwdKey, true);
                        map.put(revKey, true);
                        break;
                    case 3:
                        map.put(fwdGlu ? fwdKey : revKey, true);
                        map.put(fwdGlu ? revKey : fwdKey, false);
                        break;
                    case 4:
                        map.put(fwdKey, false);
                        map.put(revKey, false);
                }
            }
        }
        return map;
    }

    public ArrayList<int[]> getIdList() {

        ArrayList<int[]> pattList = new ArrayList<>(300);

        for (int currPs = 0; currPs < slots.size();) {
            if (getKey(slots.get(currPs).getDate(), slots.get(currPs).getGroup())
                    == getKey(slots.get(currPs + 11).getDate(), slots.get(currPs + 11).getGroup())) {

                //Quadruplet
                int id1 = slots.get(currPs).getId1();
                int id2 = slots.get(currPs + 3).getId1();
                int id3 = slots.get(currPs + 6).getId1();
                int id4 = slots.get(currPs + 9).getId1();

                int[][] lists = {{id2, id3, id4},
                    {id1, id3, id4},
                    {id1, id2, id4},
                    {id1, id2, id3}};
                pattList.addAll(Arrays.asList(lists));
                currPs += 12;
            } else if (getKey(slots.get(currPs).getDate(), slots.get(currPs).getGroup())
                    == getKey(slots.get(currPs + 5).getDate(), slots.get(currPs + 5).getGroup())) {
//                D.hit();
                //Triplet
                int id1 = slots.get(currPs).getId1();
                int id2 = slots.get(currPs + 2).getId1();
                int id3 = slots.get(currPs + 4).getId1();

                int[] list = {id1, id2, id3};
                pattList.add(list);
                currPs += 6;
            } else {

                //duo
                currPs += 2;
            }
        }
        return pattList;
    }
}
