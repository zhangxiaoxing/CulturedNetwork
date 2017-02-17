/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.pattern;

import java.util.HashMap;

/**
 *
 * @author Libra
 */
public class TriPattLib {

    private int[][] triArr;
    private HashMap<Integer, Integer> tripleMap;
    private int nPatt;

    public TriPattLib() {
        tripleMap = new HashMap<>(1000);
        nPatt=initList();
        for (int i = 0; i < triArr.length; i++) {
            int[] q1 = {triArr[i][0], triArr[i][1], triArr[i][2], triArr[i][3], triArr[i][4], triArr[i][5]};
            int[] q2 = {triArr[i][1], triArr[i][0], triArr[i][5], triArr[i][4], triArr[i][3], triArr[i][2]};
            int[] q3 = {triArr[i][2], triArr[i][3], triArr[i][4], triArr[i][5], triArr[i][0], triArr[i][1]};
            int[] q4 = {triArr[i][3], triArr[i][2], triArr[i][1], triArr[i][0], triArr[i][5], triArr[i][4]};
            int[] q5 = {triArr[i][4], triArr[i][5], triArr[i][0], triArr[i][1], triArr[i][2], triArr[i][3]};
            int[] q6 = {triArr[i][5], triArr[i][4], triArr[i][3], triArr[i][2], triArr[i][1], triArr[i][0]};

            int[] inVar = new int[6];
            inVar[0] = hashArrInt(q1);
            inVar[1] = hashArrInt(q2);
            inVar[2] = hashArrInt(q3);
            inVar[3] = hashArrInt(q4);
            inVar[4] = hashArrInt(q5);
            inVar[5] = hashArrInt(q6);

            for (int j = 0; j < inVar.length; j++) {
                tripleMap.put(inVar[j], i);
            }
        }
    }

    private int hashArrInt(int[] in) {
        int hashCode = 0;
        for (int i = 0; i < in.length; i++) {
            hashCode += in[i] << (i * 2);
        }
        return hashCode;
    }

    final public int initList() {
        String s = "0,0,0,0,0,0,"
                + "0,0,0,0,0,1,"
                + "0,0,0,0,0,2,"
                + "0,0,0,0,1,1,"
                + "0,0,0,0,1,2,"
                + "0,0,0,0,2,2,"
                + "0,0,0,1,0,1,"
                + "0,0,0,1,0,2,"
                + "0,0,0,1,1,0,"
                + "0,0,0,1,1,1,"
                + "0,0,0,1,1,2,"
                + "0,0,0,2,0,1,"
                + "0,0,0,2,0,2,"
                + "0,0,0,2,2,0,"
                + "0,0,0,2,2,1,"
                + "0,0,0,2,2,2,"
                + "0,0,1,0,0,1,"
                + "0,0,1,0,0,2,"
                + "0,0,1,0,1,1,"
                + "0,0,1,0,1,2,"
                + "0,0,1,0,2,1,"
                + "0,0,1,0,2,2,"
                + "0,0,1,1,0,2,"
                + "0,0,1,1,1,1,"
                + "0,0,1,1,1,2,"
                + "0,0,1,2,0,2,"
                + "0,0,1,2,2,1,"
                + "0,0,1,2,2,2,"
                + "0,0,2,0,0,2,"
                + "0,0,2,0,1,2,"
                + "0,0,2,0,2,2,"
                + "0,0,2,1,1,2,"
                + "0,0,2,2,2,2,"
                + "0,1,0,1,0,1,"
                + "0,1,0,1,0,2,"
                + "0,1,0,1,1,0,"
                + "0,1,0,1,1,1,"
                + "0,1,0,1,1,2,"
                + "0,1,0,2,0,2,"
                + "0,1,0,2,2,0,"
                + "0,1,0,2,2,1,"
                + "0,1,0,2,2,2,"
                + "0,1,1,0,0,2,"
                + "0,1,1,0,1,1,"
                + "0,1,1,0,1,2,"
                + "0,1,1,0,2,2,"
                + "0,1,1,1,0,2,"
                + "0,1,1,1,1,0,"
                + "0,1,1,1,1,1,"
                + "0,1,1,1,1,2,"
                + "0,1,1,2,0,2,"
                + "0,1,1,2,2,0,"
                + "0,1,1,2,2,1,"
                + "0,1,1,2,2,2,"
                + "0,2,0,2,0,2,"
                + "0,2,0,2,2,0,"
                + "0,2,0,2,2,1,"
                + "0,2,0,2,2,2,"
                + "0,2,2,0,1,1,"
                + "0,2,2,0,1,2,"
                + "0,2,2,0,2,2,"
                + "0,2,2,1,1,1,"
                + "0,2,2,1,1,2,"
                + "0,2,2,2,2,0,"
                + "0,2,2,2,2,1,"
                + "0,2,2,2,2,2,"
                + "1,1,1,1,1,1,"
                + "1,1,1,2,2,1,"
                + "1,2,2,2,2,1,"
                + "2,2,2,2,2,2,";
        String[] sArr = s.split(",");
        triArr = new int[sArr.length / 6][6];
        for (int i = 0, currS = 0; i < triArr.length; i++) {
            for (int j = 0; j < 6; j++, currS++) {
                triArr[i][j] = Integer.parseInt(sArr[currS]);
            }
        }
        return triArr.length;
    }

    public int[][] getTripleList() {
        return triArr;
    }

    public HashMap<Integer, Integer> getTripleMap() {
        return tripleMap;
    }

    public int getnPatt() {
        return nPatt;
    }
    
}
