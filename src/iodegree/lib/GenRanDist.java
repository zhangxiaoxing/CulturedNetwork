/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

import iodegree.lib.database.LegacyDB;
import iodegree.dataStrucure.EmptyPosition;
//import java.security.SecureRandom;
import java.util.ArrayList;
import org.apache.commons.math3.random.Well44497b;
import iodegree.lib.MyFit;

/**
 *
 * @author Libra
 */
public class GenRanDist {

    
    private MyFit[] fit;
    private String pathToFile;
    private boolean isGlu;
    
    private Well44497b r = new Well44497b();

    public int[][] connRnd(boolean input, int div, int cycle) {
        ArrayList<EmptyPosition> slots = LegacyDB.slotDegQuery(
                pathToFile,
                isGlu,
                input,
                div);
        int[][] arrAllDeg = new int[cycle][4];

        for (int currCycle = 0; currCycle < cycle; currCycle++) {
            int[] arrRndConnDeg = new int[4];
            int currId = 0;
            int count = 0;
            for (int i = 0; i < slots.size(); i++) {
                if (currId == slots.get(i).getId()) {
                    if (connected(slots.get(i).getDist(), div)) {
                        count++;
                    }
                } else {
                    arrRndConnDeg[count]++;
                    count = 0;
                    if (connected(slots.get(i).getDist(), div)) {
                        count++;
                    }
                    currId = slots.get(i).getId();
                }
            }
            arrRndConnDeg[count]++;
            arrRndConnDeg[0]--;
//            System.out.println((arrRndConnDeg[1] + arrRndConnDeg[2] * 2 + arrRndConnDeg[3] * 3));
            arrAllDeg[currCycle] = arrRndConnDeg;
        }
        return arrAllDeg;
    }

    private boolean connected(int d, int div) {
        return (r.nextFloat() < fit[div].predict(d));
    }

    public void setFit(MyFit[] fit) {
        this.fit = fit;
    }

    public void setIsGlu(boolean isGlu) {
        this.isGlu = isGlu;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }
    
}