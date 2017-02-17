/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.quadro;

//import iodegree.lib.reci.ReciDB;
import iodegree.lib.D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.commons.math3.random.Well44497b;

/**
 *
 * @author Libra
 */
public class ConnToIO {
    //[glu uni, gaba uni, glu bi, hyb bi, gaba bi;

    private Well44497b r;
    private int binSize;
    private HashMap<Integer, Integer> slotMap;
    private HashMap<Integer, Integer[]> connMap;
    private HashMap<Integer, Integer> idToKey;
    private HashSet<Integer> gluCells;
    private ArrayList<PotentialSynapse> slots;
    private ArrayList<PotentialSynapse> conns;
    private int maxIO;

    public ConnToIO(String path) {
        GenConnDB db = new GenConnDB(path);
        r = new Well44497b();
        binSize = 50;
        slots = db.getSlots();
        conns = db.getConns();
        maxIO = 0;
    }
//
//    private Integer[] quadList(int id1, int id2, int id3, int id4, HashMap<Integer, Boolean> map) {
//        Integer[] list = new Integer[12];
//        list[0] = getType(id1, id2, map);
//        list[1] = getType(id1, id3, map);
//        list[2] = getType(id1, id4, map);
//        list[3] = getType(id2, id1, map);
//        list[4] = getType(id2, id3, map);
//        list[5] = getType(id2, id4, map);
//        list[6] = getType(id3, id1, map);
//        list[7] = getType(id3, id2, map);
//        list[8] = getType(id3, id4, map);
//        list[9] = getType(id4, id1, map);
//        list[10] = getType(id4, id2, map);
//        list[11] = getType(id4, id3, map);
//        return list;
//    }
//
//    private int getType(int id1, int id2, HashMap<Integer, Boolean> map) {
//        int key = getKey(id1, id2);
//        if (map.containsKey(key)) {
//            return map.get(key) ? 1 : 2;
//        } else {
//            return 0;
//        }
//    }
//
//    public ArrayList<Integer[]> getObsPatt() {
//
//        ArrayList<Integer[]> pattList = new ArrayList<>(300);
//
//        //connSet
//        HashMap<Integer, Boolean> connIdMap = new HashMap<>(1000);
////        ArrayList<PotentialSynapse> conns = db.getConns();
//        for (PotentialSynapse ps : conns) {
//            connIdMap.put(getKey(ps.getId1(), ps.getId2()), ps.getFwdGlu());
//        }
//
//        //Pattern Count
////        ArrayList<PotentialSynapse> slots = db.getSlots();
//        for (int currPs = 0; currPs < slots.size();) {
//            if (getKey(slots.get(currPs).getDate(), slots.get(currPs).getGroup())
//                    == getKey(slots.get(currPs + 11).getDate(), slots.get(currPs + 11).getGroup())) {
//
//                //Quadruplet
//                int id1 = slots.get(currPs).getId1();
//                int id2 = slots.get(currPs + 3).getId1();
//                int id3 = slots.get(currPs + 6).getId1();
//                int id4 = slots.get(currPs + 9).getId1();
//
//                Integer[] list = quadList(id1, id2, id3, id4, connIdMap);
//                pattList.add(list);
//                currPs += 12;
//            } else if (getKey(slots.get(currPs).getDate(), slots.get(currPs).getGroup())
//                    == getKey(slots.get(currPs + 5).getDate(), slots.get(currPs + 5).getGroup())) {
//                //Triplet
//                currPs += 6;
//            } else {
//                //duo
//                currPs += 2;
//            }
//        }
//        return pattList;
//    }

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
//
//    private int getKey(Date date, int n) {
//        int prefix = (int) date.getTime() >>> 23;
//        return (prefix << 4) + n;
//    }

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

//    private HashMap<Integer, Boolean> genConnIdMap() {
//
//        HashMap<Integer, Boolean> map = new HashMap<>(1000);;
//        HashMap<Integer, Integer> usedSlot = new HashMap<>();
//        HashMap<Integer, Integer[]> usedConn = new HashMap<>();
//        for (int currPs = 0; currPs < slots.size(); currPs++) {
//            int id1 = slots.get(currPs).getId1();
//            int id2 = slots.get(currPs).getId2();
//            if (id1 < id2) {
//                boolean fwdGlu = gluCells.contains(id1);
//                boolean revGlu = gluCells.contains(id2);
//
//                int mapKey = getKey(slots.get(currPs).getFwdGlu(),
//                        slots.get(currPs).getRevGlu(),
//                        slots.get(currPs).getDiv(),
//                        slots.get(currPs).getDist());
//
//                //get slot;
//                int slot = slotMap.get(mapKey);
//                if (usedSlot.containsKey(mapKey)) {
//                    int currValue = usedSlot.get(mapKey);
//                    slot -= currValue;
//                    usedSlot.put(mapKey, currValue + 1);
//                } else {
//                    usedSlot.put(mapKey, 1);
//                }
//
//                int dice = r.nextInt(slot);
//                int luck = 0;
//                int type = -1;
//                if (connMap.containsKey(mapKey)) {
//                    for (int i = 0; i < 5; i++) {
//                        int conn = connMap.get(mapKey)[i];
//                        if (usedConn.containsKey(mapKey)) {
//                            int currValue = usedConn.get(mapKey)[i];
//                            conn -= currValue;
//                        }
//                        luck += conn;
//                        if (dice < luck) {
//                            type = i;
//                            if (usedConn.containsKey(mapKey)) {
//                                usedConn.get(mapKey)[i] += 1;
//                            } else {
//                                Integer[] newArray = {0, 0, 0, 0, 0};
//                                newArray[i] = 1;
//                                usedConn.put(mapKey, newArray);
//                            }
//                            break;
//                        }
//                    }
//                }
//                int fwdKey = getKey(id1, id2);
//                int revKey = getKey(id2, id1);
//                switch (type) {
//                    case 0:
//                        map.put(
//                                (fwdGlu && revGlu)
//                                ? ((r.nextDouble() < 0.5) ? fwdKey : revKey)
//                                : (fwdGlu ? fwdKey : revKey), true);
//                        break;
//                    case 1:
//                        map.put(
//                                (!(fwdGlu || revGlu))
//                                ? ((r.nextDouble() < 0.5) ? fwdKey : revKey)
//                                : (fwdGlu ? revKey : fwdKey), false);
//                        break;
//                    case 2:
//                        map.put(fwdKey, true);
//                        map.put(revKey, true);
//                        break;
//                    case 3:
//                        map.put(fwdGlu ? fwdKey : revKey, true);
//                        map.put(fwdGlu ? revKey : fwdKey, false);
//                        break;
//                    case 4:
//                        map.put(fwdKey, false);
//                        map.put(revKey, false);
//                }
//            }
//        }
//        return map;
//    }
    private ArrayList<PotentialSynapse> genConnList() {

//        HashMap<Integer, Boolean> map = new HashMap<>(1000);;
        HashMap<Integer, Integer> usedSlot = new HashMap<>();
        HashMap<Integer, Integer[]> usedConn = new HashMap<>();
        ArrayList<PotentialSynapse> connList = new ArrayList<>();

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
                boolean flag = r.nextDouble() < 0.5;
                PotentialSynapse ps = new PotentialSynapse();
                PotentialSynapse ps2 = new PotentialSynapse();
                switch (type) {
                    case 0:
                        ps.setId1((fwdGlu && revGlu)
                                ? flag ? id1 : id2
                                : fwdGlu ? id1 : id2);
                        ps.setId2(ps.getId1() == id1 ? id2 : id1);
                        ps.setFwdGlu(true);
                        connList.add(ps);
                        break;
                    case 1:
                        ps.setId1(!(fwdGlu || revGlu)
                                ? flag ? id1 : id2
                                : fwdGlu ? id2 : id1);
                        ps.setId2(ps.getId1() == id1 ? id2 : id1);
                        ps.setFwdGlu(false);
                        connList.add(ps);
                        break;
                    case 2:
                        ps.setId1(id1);
                        ps.setId2(id2);
                        ps.setFwdGlu(true);
                        connList.add(ps);
                        ps2.setId1(id2);
                        ps2.setId2(id1);
                        ps2.setFwdGlu(true);
                        connList.add(ps2);
                        break;
                    case 3:
                        ps.setId1(id1);
                        ps.setId2(id2);
                        ps.setFwdGlu(fwdGlu ? true : false);
                        connList.add(ps);
                        ps2.setId1(id2);
                        ps2.setId2(id1);
                        ps2.setFwdGlu(fwdGlu ? false : true);
                        connList.add(ps2);
                        break;
                    case 4:
                        ps.setId1(id1);
                        ps.setId2(id2);
                        ps.setFwdGlu(false);
                        connList.add(ps);
                        ps2.setId1(id2);
                        ps2.setId2(id1);
                        ps2.setFwdGlu(false);
                        connList.add(ps2);
                        break;
                }
            }
        }
        return connList;
    }
//
//    public ArrayList<Integer[]> genExpPatt() {
//
//        ArrayList<Integer[]> pattList = new ArrayList<>(300);
//        HashMap<Integer, Boolean> connIdMap = genConnIdMap();
//
//        for (int currPs = 0; currPs < slots.size();) {
//            if (getKey(slots.get(currPs).getDate(), slots.get(currPs).getGroup())
//                    == getKey(slots.get(currPs + 11).getDate(), slots.get(currPs + 11).getGroup())) {
//
//                //Quadruplet
//                int id1 = slots.get(currPs).getId1();
//                int id2 = slots.get(currPs + 3).getId1();
//                int id3 = slots.get(currPs + 6).getId1();
//                int id4 = slots.get(currPs + 9).getId1();
//
//                Integer[] list = quadList(id1, id2, id3, id4, connIdMap);
//                pattList.add(list);
//                currPs += 12;
//            } else if (getKey(slots.get(currPs).getDate(), slots.get(currPs).getGroup())
//                    == getKey(slots.get(currPs + 5).getDate(), slots.get(currPs + 5).getGroup())) {
//                //Triplet
//                currPs += 6;
//            } else {
//                //duo
//                currPs += 2;
//            }
//        }
//        return pattList;
//    }

    private void mapAdd(HashMap<Integer, Integer> map, Integer key) {
        if (map.containsKey(key)) {
            Integer currValue = map.get(key);
            map.put(key, currValue + 1);
        } else {
            Integer newValue = 1;
            map.put(key, newValue);
        }

    }

    private int mapGet(HashMap<Integer, Integer> map, Integer key) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return 0;
        }
    }

    public Double[] getObsDist(int type) {
        return getDist(type, true);
    }

    public Double[] getExpDist(int type) {
        return getDist(type, false);

        //debug
    }

    private Double[] getDist(int type, boolean OBS) {


        ArrayList<PotentialSynapse> currConnList;

        HashMap<Integer, Integer> outMap;
        HashMap<Integer, Integer> inMap;
        HashMap<Integer, Integer> slotIOMap;
        HashMap<Integer, Integer> connIOMap;

        slotIOMap = new HashMap<>();
        connIOMap = new HashMap<>();
        outMap = new HashMap<>();
        inMap = new HashMap<>();


        currConnList = OBS ? conns : genConnList();

        gluCells = new HashSet<>(500);

        //Gen IO Map

        for (PotentialSynapse ps : currConnList) {
            int id1 = ps.getId1();
            int id2 = ps.getId2();
            mapAdd(outMap, id1);
            mapAdd(inMap, id2);
        }


        //Map Slots
        for (PotentialSynapse ps : slots) {
            int id1 = ps.getId1();
            int id2 = ps.getId2();

            int io1 = mapGet(outMap, id1) + mapGet(inMap, id1);
            int io2 = mapGet(outMap, id2) + mapGet(inMap, id2);

            maxIO = (maxIO > io1 + io2) ? maxIO : io1 + io2;

            mapAdd(slotIOMap, io1 + io2);
//            D.tpi("slot");
//            D.tp(io1 + io2);
        }

        //Map Conns
        for (PotentialSynapse conn : currConnList) {
            int id1 = conn.getId1();
            int id2 = conn.getId2();

            int io1 = mapGet(outMap, id1) + mapGet(inMap, id1);
            int io2 = mapGet(outMap, id2) + mapGet(inMap, id2);
//            D.tpi("conn");
//            D.tp(io1 + io2);

            switch (type) {
                case 0:
                    mapAdd(connIOMap, io1 + io2);
                    break;
                case 1:
                    if (conn.getFwdGlu()) {
                        mapAdd(connIOMap, io1 + io2);
                    }
                    break;
                case 2:
                    if (!conn.getFwdGlu()) {
                        mapAdd(connIOMap, io1 + io2);
                    }
            }
        }

        Double[] dist = new Double[maxIO + 1];
        for (int i = 0; i < maxIO+1; i++) {
            if (slotIOMap.containsKey(i)) {
                dist[i] = connIOMap.containsKey(i) ? new Double((double) connIOMap.get(i) / slotIOMap.get(i)) : new Double(0);
            } else {
                dist[i] = null;
            }
        }
        return dist;
    }

    public int getMaxIO() {
        return maxIO+1;
    }
}
