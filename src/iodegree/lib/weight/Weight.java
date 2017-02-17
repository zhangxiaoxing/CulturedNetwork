/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.weight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Libra
 */
public class Weight {

    String pathToFile;

    public Weight(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    private void sortList(int[][] in) {
        java.util.Arrays.sort(in, new java.util.Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return (a[0] == b[0]) ? (a[1] - b[1]) : (a[0] - b[0]);
            }
        });
    }

    private int[][] typeWeightTri() {
        ArrayList<Integer> pre = new ArrayList<>(1000);
        ArrayList<Integer> post = new ArrayList<>(1000);
        ArrayList<Integer> type = new ArrayList<>(1000);
        ArrayList<Integer> amp = new ArrayList<>(1000);

        String jdbcPath = "jdbc:ucanaccess://" + pathToFile;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {

                String query = "SELECT tblConnection.PreCellID, tblConnection.PostCellID, tblConnection.Glu, tblConnection.Amp "
                        + " FROM tblConnection "
                        + " ORDER BY tblConnection.PreCellID, tblConnection.PostCellID;";
                ResultSet rs;
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    try {
                        pre.add(rs.getInt("PreCellID"));
                        post.add(rs.getInt("PostCellID"));
                        type.add(rs.getBoolean("Glu") ? 1 : 2);
                        amp.add(rs.getInt("Amp"));
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        int[][] prePostType = new int[pre.size()][4];
        for (int i = 0; i < pre.size(); i++) {
            prePostType[i][0] = pre.get(i);
            prePostType[i][1] = post.get(i);
            prePostType[i][2] = type.get(i);
            prePostType[i][3] = amp.get(i);
        }
//        System.err.print(Arrays.deepToString(prePostType));
        return prePostType;
    }

    private int[] getConnWeight(int pre, int post, int[][] list) {
        //sort list for optimized algorithm

//        System.out.print(pre + "," + post + ",");
        int[] value = {pre, post, 0};
        int findPair = java.util.Arrays.binarySearch(list, value, new java.util.Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return (a[0] == b[0]) ? (a[1] - b[1]) : (a[0] - b[0]);
            }
        });
        if (findPair < 0) {
//            System.out.println("0");
            int[] rtn = {0, 0};
            return rtn;
        } else {
//            System.out.println(list[findPair][2]);
            int[] rtn = {list[findPair][2], list[findPair][3]};
            return rtn;
        }
    }

    private void shuffleList(int[][] oriList) {
        ArrayList<Integer> gluWeights = new ArrayList<>();
        ArrayList<Integer> GABAWeights = new ArrayList<>();

        for (int i = 0; i < oriList.length; i++) {
            if (oriList[i][2] == 1) {
                gluWeights.add(oriList[i][3]);
            } else {
                GABAWeights.add(oriList[i][3]);
            }
        }
        //DIFF GLU GABA
        Collections.shuffle(gluWeights);
        Collections.shuffle(GABAWeights);
        for (int i = 0, gluIdx = 0, gabaIdx = 0; i < oriList.length; i++) {
            if (oriList[i][2] == 1) {
                oriList[i][3] = gluWeights.get(gluIdx);
                gluIdx++;
            } else {
                oriList[i][3] = GABAWeights.get(gabaIdx);
                gabaIdx++;
            }
        }
    }

    public ClusterWeightResult[] clusterWeight(int type, boolean shuffled) {
        ArrayList<Integer[]> grp = getTriList();
        int[][] connWeightList = typeWeightTri();
        if (shuffled) {
            shuffleList(connWeightList);
        }

        ClusterWeightResult[] rtn = new ClusterWeightResult[7];
        sortList(connWeightList);
        ArrayList<Integer> gluAmp = new ArrayList<>(10);
        ArrayList<Integer> gabaAmp = new ArrayList<>(10);

        for (int i = 0; i < rtn.length; i++) {
            rtn[i] = new ClusterWeightResult();
        }

        for (int i = 0; i < grp.size(); i++) {
//            System.err.println(i);
            int[][] G = new int[6][2];

            G[0] = getConnWeight(grp.get(i)[0], grp.get(i)[1], connWeightList);//[type][amp]
            G[1] = getConnWeight(grp.get(i)[1], grp.get(i)[0], connWeightList);
            G[2] = getConnWeight(grp.get(i)[1], grp.get(i)[2], connWeightList);
            G[3] = getConnWeight(grp.get(i)[2], grp.get(i)[1], connWeightList);
            G[4] = getConnWeight(grp.get(i)[2], grp.get(i)[0], connWeightList);
            G[5] = getConnWeight(grp.get(i)[0], grp.get(i)[2], connWeightList);
            int gluCount = 0;
            int gabaCount = 0;
            gluAmp.clear();
            gabaAmp.clear();

            //CHANGE HERE
            for (int j = 0; j < 6; j++) {
                switch (G[j][0]) {
                    case 1:
                        gluCount++;
                        gluAmp.add(G[j][1]);
                        break;
                    case 2:
                        gabaCount++;
                        gabaAmp.add(G[j][1]);
                        break;
                }
            }

            int j;
            switch (type) {
                case 0:
                    rtn[gluCount + gabaCount].addCount();
                    j = 0;
                    while (gluAmp.size() > 0 && j < gluAmp.size()) {
                        rtn[gluCount + gabaCount].addGluStats(gluAmp.get(j));
//                        System.out.println("GluInGrp\t" + Integer.toString(gluCount)
//                                + "\tGABAInGrp\t" + Integer.toString(gabaCount)
//                                + "\tgluAmp\t" + Integer.toString(gluAmp.get(j)));
                        j++;
                    }
                    j = 0;
                    while (gabaAmp.size() > 0 && j < gabaAmp.size()) {
                        rtn[gluCount + gabaCount].addGABAStats(gabaAmp.get(j));
//                        System.out.println("GluInGrp\t" + Integer.toString(gluCount)
//                                + "\tGABAInGrp\t" + Integer.toString(gabaCount)
//                                + "\tgabaAmp\t" + Integer.toString(gabaAmp.get(j)));
                        j++;
                    }
                    break;
                case 1:
//                    System.err.println("case1");
                    rtn[gluCount].addCount();
                    j = 0;
                    while (gluAmp.size() > 0 && j < gluAmp.size()) {
                        rtn[gluCount].addGluStats(gluAmp.get(j));
                        j++;
                    }
                    j = 0;
                    while (gabaAmp.size() > 0 && j < gabaAmp.size()) {
                        rtn[gluCount].addGABAStats(gabaAmp.get(j));
                        j++;
                    }
                    break;
                case 2:
//                    System.err.println("case2");
                    rtn[gabaCount].addCount();
                    j = 0;
                    while (gluAmp.size() > 0 && j < gluAmp.size()) {
                        rtn[gabaCount].addGluStats(gluAmp.get(j));
                        j++;
                    }
                    j = 0;
                    while (gabaAmp.size() > 0 && j < gabaAmp.size()) {
                        rtn[gabaCount].addGABAStats(gabaAmp.get(j));
                        j++;
                    }
                    break;
            }
        }

        return rtn;
    }

    private int[][] typeWeightQuad() {
        ArrayList<Integer> pre = new ArrayList<>(100);
        ArrayList<Integer> post = new ArrayList<>(100);
        ArrayList<Integer> type = new ArrayList<>(100);
        ArrayList<Integer> amp = new ArrayList<>(100);
        String jdbcPath = "jdbc:ucanaccess://" + pathToFile;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT tblConnection.PreCellID, tblConnection.PostCellID, tblConnection.Glu, tblConnection.Amp "
                        + " FROM tblConnection "
                        + " ORDER BY tblConnection.PreCellID, tblConnection.PostCellID;";
                ResultSet rs;
                rs = stmt.executeQuery(query);
                while (rs.next()) {
                    try {
                        pre.add(rs.getInt("PreCellID"));
                        post.add(rs.getInt("PostCellID"));
                        type.add(rs.getBoolean("Glu") ? 1 : 2);
                        amp.add(rs.getInt("Amp"));
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        int[][] prePostType = new int[pre.size()][4];
        for (int i = 0; i < pre.size(); i++) {
            prePostType[i][0] = pre.get(i);
            prePostType[i][1] = post.get(i);
            prePostType[i][2] = type.get(i);
            prePostType[i][3] = amp.get(i);
        }
//        System.err.print(Arrays.deepToString(prePostType));
        return prePostType;
    }

    private ArrayList<Integer[]> splitTQtoT(int[][] sizeNId) {
        Integer[] thisGrp;
        int currPointer = 0;
        ArrayList<Integer[]> triGrpList = new ArrayList<>(100);
        while (currPointer < sizeNId.length - 2) {
            switch (sizeNId[currPointer][0]) {
                case 2:
                    currPointer += 2;
                    break;
                case 3:
                    thisGrp = new Integer[3];
                    for (int i = 0; i < 3; i++) {
                        thisGrp[i] = sizeNId[currPointer + i][1];
                    }
                    triGrpList.add(thisGrp);
                    currPointer += 3;
                    break;
                case 4:
                    thisGrp = new Integer[3];
                    thisGrp[0] = sizeNId[currPointer][1];
                    thisGrp[1] = sizeNId[currPointer + 1][1];
                    thisGrp[2] = sizeNId[currPointer + 2][1];
                    triGrpList.add(thisGrp);

                    thisGrp = new Integer[3];
                    thisGrp[0] = sizeNId[currPointer][1];
                    thisGrp[1] = sizeNId[currPointer + 1][1];
                    thisGrp[2] = sizeNId[currPointer + 3][1];
                    triGrpList.add(thisGrp);

                    thisGrp = new Integer[3];
                    thisGrp[0] = sizeNId[currPointer][1];
                    thisGrp[1] = sizeNId[currPointer + 2][1];
                    thisGrp[2] = sizeNId[currPointer + 3][1];
                    triGrpList.add(thisGrp);

                    thisGrp = new Integer[3];
                    thisGrp[0] = sizeNId[currPointer + 1][1];
                    thisGrp[1] = sizeNId[currPointer + 2][1];
                    thisGrp[2] = sizeNId[currPointer + 3][1];
                    triGrpList.add(thisGrp);
                    currPointer += 4;
                    break;
            }
        }
//        for (int i = 0; i < triGrpList.size(); i++) {
//            System.out.println(Arrays.toString(triGrpList.get(i)));
//        }
        return triGrpList;
    }

    private ArrayList<Integer[]> getTriList() {
        return splitTQtoT(sizeNIdForSplit());
    }

    private int[][] sizeNIdForSplit() {
        ArrayList<Integer> size = new ArrayList<>(100);
        ArrayList<Integer> id = new ArrayList<>(100);
        ArrayList<Integer> type = new ArrayList<>(100);
        String jdbcPath = "jdbc:ucanaccess://" + pathToFile;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            Leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT qGroupSizenCell.Size, tblGADCell.CellID, tblGADCell.GAD "
                        + "FROM qGroupSizenCell INNER JOIN tblGADCell ON (qGroupSizenCell.GroupNo = tblGADCell.GroupNo) AND (qGroupSizenCell.RecordDate = tblGADCell.RecordDate) "
                        + "WHERE tblGADCell.BadSeal=False "
                        + "ORDER BY qGroupSizenCell.RecordDate, qGroupSizenCell.GroupNo;";

                ResultSet rs;
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    try {
                        size.add(rs.getInt("Size"));
                        id.add(rs.getInt("CellID"));
                        type.add(rs.getBoolean("GAD") ? 2 : 1);

                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        int[][] sizeNId = new int[size.size()][3];
        for (int i = 0; i < size.size(); i++) {
            sizeNId[i][0] = size.get(i);
            sizeNId[i][1] = id.get(i);
            sizeNId[i][2] = type.get(i);
        }
//        D.p(Arrays.deepToString(sizeNId));
//        System.out.println(Arrays.deepToString(sizeNId));
        return sizeNId;
    }

    public ClusterWeightResult[] clusterWeightQuad(int type, boolean shuffled) {
        ArrayList<Integer[]> grp = getQuadList();
        int[][] connWeightList = typeWeightQuad();
        if (shuffled) {
            shuffleList(connWeightList);
        }
        int bin = 13;
        ClusterWeightResult[] rtn = new ClusterWeightResult[bin];
        sortList(connWeightList);
        ArrayList<Integer> gluAmp = new ArrayList<>(10);
        ArrayList<Integer> gabaAmp = new ArrayList<>(10);

        for (int i = 0; i < rtn.length; i++) {
            rtn[i] = new ClusterWeightResult();
        }

        for (int i = 0; i < grp.size(); i++) {
            int[][] G = new int[bin][2];
            G[0] = getConnWeight(grp.get(i)[0], grp.get(i)[1], connWeightList);
            G[1] = getConnWeight(grp.get(i)[0], grp.get(i)[2], connWeightList);
            G[2] = getConnWeight(grp.get(i)[0], grp.get(i)[3], connWeightList);
            G[3] = getConnWeight(grp.get(i)[1], grp.get(i)[0], connWeightList);
            G[4] = getConnWeight(grp.get(i)[1], grp.get(i)[2], connWeightList);
            G[5] = getConnWeight(grp.get(i)[1], grp.get(i)[3], connWeightList);
            G[6] = getConnWeight(grp.get(i)[2], grp.get(i)[0], connWeightList);
            G[7] = getConnWeight(grp.get(i)[2], grp.get(i)[1], connWeightList);
            G[8] = getConnWeight(grp.get(i)[2], grp.get(i)[3], connWeightList);
            G[9] = getConnWeight(grp.get(i)[3], grp.get(i)[0], connWeightList);
            G[10] = getConnWeight(grp.get(i)[3], grp.get(i)[1], connWeightList);
            G[11] = getConnWeight(grp.get(i)[3], grp.get(i)[2], connWeightList);

            int gluCount = 0;
            int gabaCount = 0;
            gluAmp.clear();
            gabaAmp.clear();

            for (int j = 0; j < 12; j++) {
                switch (G[j][0]) {
                    case 1:
                        gluCount++;
                        gluAmp.add(G[j][1]);
                        break;
                    case 2:
                        gabaCount++;
                        gabaAmp.add(G[j][1]);
                        break;
                }
            }
            int j;
            switch (type) {
                case 0:
//                    System.err.println("case0");
                    rtn[gluCount + gabaCount].addCount();
                    j = 0;
                    while (gluAmp.size() > 0 && j < gluAmp.size()) {
                        rtn[gluCount + gabaCount].addGluStats(gluAmp.get(j));
                        System.out.println("GluInGrp\t" + Integer.toString(gluCount)
                                + "\tGABAInGrp\t" + Integer.toString(gabaCount)
                                + "\tgluAmp\t" + Integer.toString(gluAmp.get(j)));
                        j++;
                    }
                    j = 0;
                    while (gabaAmp.size() > 0 && j < gabaAmp.size()) {
                        rtn[gluCount + gabaCount].addGABAStats(gabaAmp.get(j));
                        System.out.println("GluInGrp\t" + Integer.toString(gluCount)
                                + "\tGABAInGrp\t" + Integer.toString(gabaCount)
                                + "\tgabaAmp\t" + Integer.toString(gabaAmp.get(j)));
                        j++;
                    }
                    break;
                case 1:
//                    System.err.println("case1");
                    rtn[gluCount].addCount();
                    j = 0;
                    while (gluAmp.size() > 0 && j < gluAmp.size()) {
                        rtn[gluCount].addGluStats(gluAmp.get(j));
                        j++;
                    }
                    j = 0;
                    while (gabaAmp.size() > 0 && j < gabaAmp.size()) {
                        rtn[gluCount].addGABAStats(gabaAmp.get(j));
                        j++;
                    }
                    break;
                case 2:
//                    System.err.println("case2");
                    rtn[gabaCount].addCount();
                    j = 0;
                    while (gluAmp.size() > 0 && j < gluAmp.size()) {
                        rtn[gabaCount].addGluStats(gluAmp.get(j));
                        j++;
                    }
                    j = 0;
                    while (gabaAmp.size() > 0 && j < gabaAmp.size()) {
                        rtn[gabaCount].addGABAStats(gabaAmp.get(j));
                        j++;
                    }
                    break;
            }
        }
        return rtn;
    }

    private int[][] getSimSizeNId() {
        ArrayList<Integer> id = new ArrayList<>(100);
        String jdbcPath = "jdbc:ucanaccess://" + pathToFile;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            Leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT tblGADCell.CellID "
                        + "FROM qGroupSizenCell INNER JOIN tblGADCell ON (qGroupSizenCell.GroupNo = tblGADCell.GroupNo) AND (qGroupSizenCell.RecordDate = tblGADCell.RecordDate) "
                        + "WHERE tblGADCell.BadSeal=False AND qGroupSizenCell.Size=4 "
                        + "ORDER BY qGroupSizenCell.RecordDate, qGroupSizenCell.GroupNo;";

                ResultSet rs;
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    try {
                        id.add(rs.getInt("CellID"));
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        int[][] grpNid = new int[id.size() / 4][4];

        for (int i = 0; i < grpNid.length; i++) {
            for (int j = 0; j < 4; j++) {
                grpNid[i][j] = id.get(i * 4 + j);
            }
        }
//        System.err.print(Arrays.deepToString(grpNid));

        return grpNid;
    }

    private ArrayList<Integer[]> getQuadList() {
        int[][] ids = getSimSizeNId();
        ArrayList<Integer[]> rtn = new ArrayList<>(100);
        for (int i = 0; i < ids.length; i++) {
            Integer[] temp = new Integer[ids[i].length];
            for (int j = 0; j < ids[i].length; j++) {
                temp[j] = ids[i][j];
            }
            rtn.add(temp);
        }
        return rtn;
    }
}
