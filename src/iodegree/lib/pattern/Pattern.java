/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.pattern;

import iodegree.lib.D;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Libra
 */
public class Pattern {

    private HashMap<Integer, Integer> tripleMap;

    public Pattern() {
        tripleMap = new TriPattLib().getTripleMap();
    }

//    public int triPatId(int[] in, boolean gluOnly, HashMap<Integer, Boolean> map) {
//        int id1 = in[0];
//        int id2 = in[1];
//        int id3 = in[2];
//        int hashCode;
//        hashCode = getType(id1, id2, gluOnly, map);
//        hashCode += getType(id2, id1, gluOnly, map) << 2;
//        hashCode += getType(id2, id3, gluOnly, map) << 4;
//        hashCode += getType(id3, id2, gluOnly, map) << 6;
//        hashCode += getType(id3, id1, gluOnly, map) << 8;
//        hashCode += getType(id1, id3, gluOnly, map) << 10;
//
//        return tripleMap.get(hashCode);
//    }

    public int triPatId(int[] in, int type, HashMap<Integer, Boolean> map) {
        //type0=all 1=glu 2=gaba
        int id1 = in[0];
        int id2 = in[1];
        int id3 = in[2];
        int hashCode;
        hashCode = getType(id1, id2, type, map);
        hashCode += getType(id2, id1, type, map) << 2;
        hashCode += getType(id2, id3, type, map) << 4;
        hashCode += getType(id3, id2, type, map) << 6;
        hashCode += getType(id3, id1, type, map) << 8;
        hashCode += getType(id1, id3, type, map) << 10;

        return tripleMap.get(hashCode);
    }

//    private int getType(int id1, int id2, boolean gluOnly, HashMap<Integer, Boolean> map) {
//        int key = (id1 << 12) + id2;
//        return map.containsKey(key) ? map.get(key) ? 1 : gluOnly ? 0 : 2 : 0;
//    }

    private int getType(int id1, int id2, int type, HashMap<Integer, Boolean> map) {
        int key = (id1 << 12) + id2;
        if (!map.containsKey(key)) {
            return 0;
        }
        switch (type) {
            case 0:
                return map.get(key) ? 1 : 2;
            case 1:
                return map.get(key) ? 1 : 0;
            case 2:
                return map.get(key) ? 0 : 2;
            default:
                return 0;
        }
    }

    public ArrayList<Integer[]> chr2Split(int[][] sizeNId, int posi) {
        int minimumPositiveI = posi;
        Integer[] thisGrp;
        int currPointer = 0;
        ArrayList<Integer[]> triGrpList = new ArrayList<>(100);
        while (currPointer < sizeNId.length - 2) {
            switch (sizeNId[currPointer][0]) {
                case 2:
                    currPointer += 2;
                    break;
                case 3:
                    thisGrp = new Integer[4];
                    thisGrp[3] = 0;
                    for (int i = 0; i < 3; i++) {
                        thisGrp[i] = sizeNId[currPointer + i][1];
                        thisGrp[3] += sizeNId[currPointer + i][2] > minimumPositiveI ? 1 : 0;
                    }

                    triGrpList.add(thisGrp);
                    currPointer += 3;
                    break;
                case 4:
                    thisGrp = new Integer[4];
                    thisGrp[3] = 0;
                    thisGrp[0] = sizeNId[currPointer][1];
                    thisGrp[1] = sizeNId[currPointer + 1][1];
                    thisGrp[2] = sizeNId[currPointer + 2][1];

                    thisGrp[3] += sizeNId[currPointer][2] > minimumPositiveI ? 1 : 0;
                    thisGrp[3] += sizeNId[currPointer + 1][2] > minimumPositiveI ? 1 : 0;
                    thisGrp[3] += sizeNId[currPointer + 2][2] > minimumPositiveI ? 1 : 0;

                    triGrpList.add(thisGrp);

                    thisGrp = new Integer[4];
                    thisGrp[3] = 0;
                    thisGrp[0] = sizeNId[currPointer][1];
                    thisGrp[1] = sizeNId[currPointer + 1][1];
                    thisGrp[2] = sizeNId[currPointer + 3][1];

                    thisGrp[3] += sizeNId[currPointer][2] > minimumPositiveI ? 1 : 0;
                    thisGrp[3] += sizeNId[currPointer + 1][2] > minimumPositiveI ? 1 : 0;
                    thisGrp[3] += sizeNId[currPointer + 3][2] > minimumPositiveI ? 1 : 0;

                    triGrpList.add(thisGrp);

                    thisGrp = new Integer[4];
                    thisGrp[3] = 0;
                    thisGrp[3] = 0;
                    thisGrp[0] = sizeNId[currPointer][1];
                    thisGrp[1] = sizeNId[currPointer + 2][1];
                    thisGrp[2] = sizeNId[currPointer + 3][1];

                    thisGrp[3] += sizeNId[currPointer][2] > minimumPositiveI ? 1 : 0;
                    thisGrp[3] += sizeNId[currPointer + 2][2] > minimumPositiveI ? 1 : 0;
                    thisGrp[3] += sizeNId[currPointer + 3][2] > minimumPositiveI ? 1 : 0;
                    triGrpList.add(thisGrp);

                    thisGrp = new Integer[4];
                    thisGrp[3] = 0;
                    thisGrp[0] = sizeNId[currPointer + 1][1];
                    thisGrp[1] = sizeNId[currPointer + 2][1];
                    thisGrp[2] = sizeNId[currPointer + 3][1];

                    thisGrp[3] += sizeNId[currPointer + 1][2] > minimumPositiveI ? 1 : 0;
                    thisGrp[3] += sizeNId[currPointer + 2][2] > minimumPositiveI ? 1 : 0;
                    thisGrp[3] += sizeNId[currPointer + 3][2] > minimumPositiveI ? 1 : 0;

                    triGrpList.add(thisGrp);
                    currPointer += 4;
                    break;
            }
        }
        return triGrpList;
    }

    public int[] clusterCount(ArrayList<int[]> idList, int type, HashMap<Integer, Boolean> map) {
        int[] histo = new int[7];
        for (int[] subGroup : idList) {
            int id1 = subGroup[0];
            int id2 = subGroup[1];
            int id3 = subGroup[2];
            int count = 0;
            count += count(type, id1, id2, map) ? 1 : 0;
            count += count(type, id1, id3, map) ? 1 : 0;
            count += count(type, id2, id1, map) ? 1 : 0;
            count += count(type, id2, id3, map) ? 1 : 0;
            count += count(type, id3, id1, map) ? 1 : 0;
            count += count(type, id3, id2, map) ? 1 : 0;
            histo[count]++;
        }
        return histo;
    }

    private boolean count(int countType, int id1, int id2, HashMap<Integer, Boolean> map) {
        int connType = getType(id1, id2, 0, map);
        switch (countType) {
            case 0:
                return connType != 0;
            case 1:
                return connType == 1;
            case 2:
                return connType == 2;
            default:
                return false;
        }
    }

    public int[][] chr2ClusterCount(ArrayList<Integer[]> grp, HashMap<Integer, Integer> map, int type) {
        int[][] histo = new int[4][7];
        for (int i = 0; i < grp.size(); i++) {
            int[] G = new int[6];
            G[0] = getConnType(grp.get(i)[0], grp.get(i)[1], map);
            G[1] = getConnType(grp.get(i)[1], grp.get(i)[0], map);
            G[2] = getConnType(grp.get(i)[1], grp.get(i)[2], map);
            G[3] = getConnType(grp.get(i)[2], grp.get(i)[1], map);
            G[4] = getConnType(grp.get(i)[2], grp.get(i)[0], map);
            G[5] = getConnType(grp.get(i)[0], grp.get(i)[2], map);

            int chr2Count = grp.get(i)[3];

            int count = 0;
            for (int j = 0; j < 6; j++) {
                switch (type) {
                    case 0:
                        count += G[j] == 0 ? 0 : 1;
                        break;
                    case 1:
                        count += G[j] == 1 ? 1 : 0;
                        break;
                    case 2:
                        count += G[j] == 2 ? 1 : 0;
                        break;
                }
            }
            histo[chr2Count][count]++;
        }
//        System.out.println(Arrays.toString(histo));
        return histo;
    }

    public int getConnType(int pre, int post, HashMap<Integer, Integer> connMap) {
        int key = (pre << 12) + post;
        if (connMap.containsKey(key)) {
            return connMap.get(key);
        } else {
            return 0;
        }

    }

    public CommNeiStat commonNeighbor(int[][] sizeNId, HashMap<Integer, Integer> map) {
        CommNeiStat stats = new CommNeiStat();
        Integer[] thisGrp;
        Integer[] thisType;
        int currPointer = 0;
        ArrayList<Integer[]> grpList = new ArrayList<>(100);
        ArrayList<Integer[]> typeList = new ArrayList<>(100);
        while (currPointer < sizeNId.length - 2) {
            switch (sizeNId[currPointer][0]) {
                case 2:
                    currPointer += 2;
                    break;
                case 3:
                    thisGrp = new Integer[3];
                    thisType = new Integer[3];
                    for (int i = 0; i < 3; i++) {
                        thisGrp[i] = sizeNId[currPointer + i][1];
                        thisType[i] = sizeNId[currPointer + i][2];
                    }
                    grpList.add(thisGrp);
                    typeList.add(thisType);
                    currPointer += 3;
                    break;
                case 4:
                    thisGrp = new Integer[4];
                    thisType = new Integer[4];
                    for (int i = 0; i < 4; i++) {
                        thisGrp[i] = sizeNId[currPointer + i][1];
                        thisType[i] = sizeNId[currPointer + i][2];
                    }
                    grpList.add(thisGrp);
                    typeList.add(thisType);
                    currPointer += 4;
                    break;
            }
        }

        ArrayList<int[]> cNC = new ArrayList<>(100);
        for (int i = 0; i < grpList.size(); i++) {
            for (int j = 0; j < grpList.get(i).length; j++) {
                int pre = grpList.get(i)[j];
                for (int k = 0; k < grpList.get(i).length; k++) {
                    int post = grpList.get(i)[k];
                    if (pre != post) {
                        int[] cncCell = new int[5];
                        //[conned type][pre type][post type][comm GluNei][common GABANei]
                        cncCell[0] = getConnType(pre, post, map);
                        cncCell[1] = typeList.get(i)[j];
                        cncCell[2] = typeList.get(i)[k];
                        //glu common neighbor
                        for (int n = 0; n < grpList.get(i).length; n++) {
                            int nei = grpList.get(i)[n];
                            if (nei != pre && nei != post) {
                                if (getConnType(nei, pre, map) == 1 && getConnType(nei, post, map) == 1) {
                                    cncCell[3] += 1;
                                } else if (getConnType(nei, pre, map) == 2 && getConnType(nei, post, map) == 2) {
                                    cncCell[4] += 1;
                                }
                            }
                        }
                        cNC.add(cncCell);
                    }
                }
            }
        }

        for (int[] pair : cNC) {
            if (pair[1] == 1 && pair[2] == 1) {
                stats.addGG(pair[0], pair[3], pair[4]);
            } else if (pair[1] == 1 && pair[2] == 2) {
                stats.addGA(pair[0], pair[3], pair[4]);
            } else if (pair[1] == 2 && pair[2] == 1) {
                stats.addAG(pair[0], pair[3], pair[4]);
            } else if (pair[1] == 2 && pair[2] == 2) {
                stats.addAA(pair[0], pair[3], pair[4]);
            } else {
                D.p("error!");
            }
        }
        return stats;
    }
}