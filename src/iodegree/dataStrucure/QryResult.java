/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.dataStrucure;

import java.util.ArrayList;

/**
 *
 * @author Libra
 */
public class QryResult {

    ArrayList<Integer> groupSize;
    ArrayList<Integer> currTypeInGrp;
    int nBothTypeCell;
    int nCurrTypeCell;

    public void setGroupSize(ArrayList<Integer> gs) {
        groupSize = gs;
    }

    public void setCurrTypeInGrp(ArrayList<Integer> type) {
        currTypeInGrp = type;
    }

    public ArrayList<Integer> getGroupSize() {
        return groupSize;
    }

    public ArrayList<Integer> getCurrTypeInGrp() {
        return currTypeInGrp;
    }

    public void setNBothTypeCell(int n) {
        nBothTypeCell = n;
    }

    public void addBothTypeCell(int n) {
        nBothTypeCell += n;
    }

    public int getBothTypeCell() {
        return nBothTypeCell;
    }

    public void setNCurrTypeCell(int n) {
        nCurrTypeCell = n;
    }

    public void addCurrTypeCell(int n) {
        nCurrTypeCell += n;
    }

    public int getCurrTypeCell() {
        return nCurrTypeCell;
    }
}