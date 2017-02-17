/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.quadro;

import iodegree.lib.pattern.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author Libra
 */
public class PatternQuadro extends iodegree.lib.pattern.Pattern {

    private ArrayList<int[]> quadList;
    private ArrayList<int[]> isoList;

    public int[] clusterCount(ArrayList<Integer[]> pattList, int type) {
        int[] histo = new int[13];
        for (Integer[] patt : pattList) {
            int count = 0;
            for (Integer connType : patt) {
                switch (type) {
                    case 0:
                        count += connType == 0 ? 0 : 1;
                        break;
                    case 1:
                        count += connType == 1 ? 1 : 0;
                        break;
                    case 2:
                        count += connType == 2 ? 1 : 0;
                }
            }
            histo[count]++;
        }
        return histo;
    }

    public ArrayList<int[]> quadGrpConn(ArrayList<Integer[]> grp, HashMap<Integer, Integer> map) {
        ArrayList<int[]> grp2Conn = new ArrayList<>(100);
//        sortList(map);
        for (int i = 0; i < grp.size(); i++) {
            int[] G = new int[12];
            G[0] = getConnType(grp.get(i)[0], grp.get(i)[1], map);
            G[1] = getConnType(grp.get(i)[0], grp.get(i)[2], map);
            G[2] = getConnType(grp.get(i)[0], grp.get(i)[3], map);
            G[3] = getConnType(grp.get(i)[1], grp.get(i)[0], map);
            G[4] = getConnType(grp.get(i)[1], grp.get(i)[2], map);
            G[5] = getConnType(grp.get(i)[1], grp.get(i)[3], map);
            G[6] = getConnType(grp.get(i)[2], grp.get(i)[0], map);
            G[7] = getConnType(grp.get(i)[2], grp.get(i)[1], map);
            G[8] = getConnType(grp.get(i)[2], grp.get(i)[3], map);
            G[9] = getConnType(grp.get(i)[3], grp.get(i)[0], map);
            G[10] = getConnType(grp.get(i)[3], grp.get(i)[1], map);
            G[11] = getConnType(grp.get(i)[3], grp.get(i)[2], map);
            grp2Conn.add(G);
//            System.out.println(Arrays.toString(G));
        }
        return grp2Conn;
    }

    public int initQuadList() {
        final String quadPattern = "rsrc/quadPattern";
        String s;
        quadList = new ArrayList<>(200);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Pattern.class.getResourceAsStream(quadPattern)))) {
            while ((s = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(s, ",");
                int[] currPattern = new int[12];
                for (int j = 0; j < 12; j++) {
                    currPattern[j] = Integer.parseInt(st.nextToken());
                }
                quadList.add(currPattern);
//                System.out.println(Arrays.toString(currPattern));
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        RecursiveList rl = new RecursiveList(4);
        isoList = rl.listIsomorph();
        return quadList.size();
    }

    public int quadPatId(int[] in) {

        int[][] isoP = new int[isoList.size()][isoList.get(0).length];
        for (int i = 0; i < isoP.length; i++) {
            for (int j = 0; j < isoP[i].length; j++) {
                isoP[i][j] = in[isoList.get(i)[j]];
            }
        }

        for (int i = 0;
                i < quadList.size();
                i++) {
            for (int j = 0; j < isoP.length; j++) {
                if (Arrays.equals(quadList.get(i), isoP[j])) {
                    return i;
                }
            }
        }

        System.out.println("Unknown: " + Arrays.toString(in));
        return 0;
    }
}
