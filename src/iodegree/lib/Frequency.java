/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Libra
 */
public class Frequency {
    public Integer[][] histogram(int[] in) {
        Arrays.sort(in);
        int min = in[0];
        int max = in[in.length - 1];
        int width = max - min + 1;
        Integer[][] hist = new Integer[width][2];
        for (int i = 0; i < width; i++) {
            hist[i][0] = min + i;
        }
        for (int i = 0; i < in.length; i++) {
            try {
                hist[in[i] - min][1]++;
            } catch (NullPointerException e) {
                hist[in[i] - min][1] = 1;
            }
        }
        return hist;
    }

    public Integer[][] histogram(int[][] in) {
        ArrayList<Integer[][]> list = new ArrayList<>(15);
        for (int i = 0; i < in.length; i++) {
            list.add(histogram(in[i]));
        }
        int min = list.get(0)[0][0];
        int max = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)[0][0] < min) {
                min = list.get(i)[0][0];
            }
            int lastIndex = list.get(i).length - 1;
            if (list.get(i)[lastIndex][0] > max) {
                max = list.get(i)[lastIndex][0];
            }
        }
        int width = max - min + 1;


        Integer[][] hist = new Integer[width][list.size() + 1];

        for (int i = 0; i < width; i++) {
            hist[i][0] = min + i;
        }

        for (int i = 0; i < list.size(); i++) {

            for (int j = 0; j < list.get(i).length; j++) {
                int index = list.get(i)[j][0] - min;
                hist[index][i + 1] = list.get(i)[j][1];
            }
        }

        return hist;

    }
}
