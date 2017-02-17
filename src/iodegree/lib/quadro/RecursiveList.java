/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.quadro;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Libra
 */
public class RecursiveList {

    private ArrayList<int[]> rawList;
    private int[] value;
    private int depth;
    private int dim;
    private ArrayList<int[]> isoList;

    public RecursiveList(int dimin) {
        dim = dimin;
    }

    ArrayList<int[]> listAll() {

        rawList = new ArrayList<>(100);
        depth = dim * (dim - 1);
        value = new int[depth];
        for (int i = 0; i < 3; i++) {
            recurrFillList(depth, i);
        }
        listIsomorph();
        return isomorphUnique(daleTrue(rawList));
    }

    private void recurrFillList(int currDepth, int connType) {
//        System.out.print(n);
        if (currDepth == 1) {
            value[depth - currDepth] = connType;
//            System.out.println(Arrays.toString(value));
            rawList.add(Arrays.copyOf(value, depth));
            return;
        }
        value[depth - currDepth] = connType;
        for (int i = 0; i < 3; i++) {
            recurrFillList(currDepth - 1, i);
        }
    }

    private ArrayList<int[]> daleTrue(ArrayList<int[]> rawListIn) {
        ArrayList<int[]> daleList = new ArrayList<>(100);

        for (int i = 0; i < rawListIn.size(); i++) {
            boolean daleOK = true;
            int ptr = 0;
            while (ptr < rawListIn.get(i).length) {
                boolean gaba = false;
                boolean glu = false;
                for (int j = 0; j < dim - 1; j++) {
                    switch (rawListIn.get(i)[ptr]) {
                        case 1:
                            glu = true;
                            break;
                        case 2:
                            gaba = true;
                            break;
                    }
                    ptr++;
                }
                if (glu && gaba) {
                    daleOK = false;
                    break;
                }
            }
            if (daleOK) {
                daleList.add(rawListIn.get(i));
            }
        }
        return daleList;
    }

    private ArrayList<int[]> isomorphUnique(ArrayList<int[]> in) {
        ArrayList<int[]> out = new ArrayList<>();
        Pattern[] patList = new Pattern[in.size()];
        for (int i = 0; i < in.size(); i++) {
            patList[i] = new Pattern(in.get(i));
        }

        for (int i = 0; i < patList.length; i++) {
            boolean flag = true;
            int j = 0;
            while (j < i) {
                if (patList[j].isoEqual(patList[i].value())) {
                    flag = false;
                    break;
                }
                j++;
            }
            if (flag) {
                out.add(patList[i].value());
            }
        }
        return out;
    }

    public ArrayList<int[]> listIsomorph() {
        isoList = new ArrayList<>(20);
        int[][] connId = new int[dim][dim];
        int currConnId = 0;
        ArrayList<int[]> posList;
        for (int pre = 0; pre < dim; pre++) {
            for (int post = 0; post < dim; post++) {
                if (pre == post) {
                } else {
                    connId[pre][post] = currConnId;
                    currConnId++;
                }
            }
        }
        ListPermutations pDim = new ListPermutations(dim);
        posList = pDim.listP();

        for (int k = 0; k < posList.size(); k++) {
            int[] oneMorph = new int[dim * (dim - 1)];  //this is one orderd conn ids
            int ptr = 0;
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (i == j) {
                    } else {
                        int pre = posList.get(k)[i] - 1;
                        int post = posList.get(k)[j] - 1;
                        oneMorph[ptr] = connId[pre][post];
                        ptr++;
                    }
                }
            }
            isoList.add(oneMorph);
        }
        return isoList;
    }

    class ListPermutations {

        private int n;
        private int level = -1;
        private int[] Value;
        private ArrayList<int[]> list;

        ArrayList<int[]> listP() {
            list = new ArrayList<>(100);
            visit(0);
            return list;
        }

        private void visit(int k) {
            level = level + 1;
            Value[k] = level;    // = is assignment
            if (level == n) // == is comparison
            {
                AddItem();     // to the list box
            } else {
                for (int i = 0; i < n; i++) {
                    if (Value[i] == 0) {
                        visit(i);
                    }
                }
            }
            level = level - 1;
            Value[k] = 0;
        }

        ListPermutations(int in) {
            n = in;
            level = -1;
            Value = new int[n];
        }

        private void AddItem() {
            int[] v = Arrays.copyOf(Value, n);
            list.add(v);
        }
    }

    class Pattern {

//        private int[] pattern;
        private int[][] isoP;

        Pattern(int[] in) {

            isoP = new int[isoList.size()][isoList.get(0).length];
            for (int i = 0; i < isoP.length; i++) {
                for (int j = 0; j < isoP[i].length; j++) {
                    isoP[i][j] = in[isoList.get(i)[j]];
                }
            }
        }

        boolean isoEqual(int[] in) {
            for (int i = 0; i < isoP.length; i++) {
                if (Arrays.equals(isoP[i], in)) {
                    return true;
                }
            }
            return false;
        }

        int[] value() {
            return isoP[0];
        }
//        void show() {
//            for (int i = 0; i < 6; i++) {
//                Group.dpp(pattern[i] + ",");
//            }
//            Group.dpp("\n");
//        }
    }
}
