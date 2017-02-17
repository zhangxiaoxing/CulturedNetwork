/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.database;

import iodegree.dataStrucure.BatchNInterest;
import iodegree.dataStrucure.EmptyPosition;
import iodegree.dataStrucure.QryResult;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Libra
 */
public class LegacyDB {
    
    private static int[] degree;
    
    public static int[] degreeQuery(String file, boolean isGlu, boolean isInput, int div) {
        String divFrom = (div == 0)
                ? "" : "INNER JOIN tblGADCell ON tblConnection.PreCellID = tblGADCell.CellID ";
        String divWhere = (div == 0)
                ? "" : " AND tblGADCell.DIV=" + Integer.toString(div) + " ";
        
        degree = new int[4];
        String strGluTF = isGlu ? "True" : "False";
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                
                String queryOutput = "SELECT tblConnection.PreCellID AS ID, "
                        + "Count(tblConnection.PostCellID) AS CellCounts "
                        + "FROM tblConnection " + divFrom
                        + "WHERE tblConnection.Glu=" + strGluTF + " " + divWhere
                        + "GROUP BY tblConnection.PreCellID;";
                
                String queryInput = "SELECT tblConnection.PostCellID AS ID, "
                        + "Count(tblConnection.PreCellID) AS CellCounts "
                        + "FROM tblConnection " + divFrom
                        + "WHERE tblConnection.Glu=" + strGluTF + " " + divWhere
                        + "GROUP BY tblConnection.PostCellID;";
                
                ResultSet rs;
                if (isInput) {
                    rs = stmt.executeQuery(queryInput);
                    
                } else {
                    rs = stmt.executeQuery(queryOutput);
                }
                boolean flag = rs.next();
                while (flag) {
                    try {
                        int CellCounts = rs.getInt("CellCounts");
                        switch (CellCounts) {
                            case 1:
                                degree[1]++;
                                break;
                            case 2:
                                degree[2]++;
                                break;
                            case 3:
                                degree[3]++;
                                break;
                            default:
                                System.out.println("Error in switch");
                        }
                        flag = rs.next();
                    } catch (SQLException e) {
//                        System.out.println("Still the same "
//                                + e.toString());
                        flag = false;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return degree;
    }
    
    public static QryResult nSlotQuery(String file, boolean isGlu, int div) {
        String divWhere = (div == 0)
                ? "" : "WHERE qGroupSizenCell.DIV=" + Integer.toString(div);
        QryResult result = new QryResult();
        result.setCurrTypeInGrp(new ArrayList<Integer>(100));
        result.setGroupSize(new ArrayList<Integer>(100));
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            TODO leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT qGroupSizenCell.Size, "
                        + "qGroupSizenCell.nGABACell "
                        + "FROM qGroupSizenCell "
                        + divWhere + " "
                        + "ORDER BY qGroupSizenCell.DIV, qGroupSizenCell.RecordDate, qGroupSizenCell.GroupNo;";
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        int size = rs.getInt("Size");
                        int nGaba = rs.getInt("nGABACell");
                        int nCurrTypeInGrp;
                        result.getGroupSize().add(size);
                        nCurrTypeInGrp = isGlu ? size - nGaba : nGaba;
                        result.getCurrTypeInGrp().add(nCurrTypeInGrp);
                        result.addBothTypeCell(size);
                        result.addCurrTypeCell(nCurrTypeInGrp);
                    } catch (SQLException e) {
                        System.out.println("2nd Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return result;
    }
    
    public static ArrayList<Integer> distSlotQuery(String file, boolean isGlu, int div) {
        ArrayList<Integer> slotD = new ArrayList<>(100);
        String strGlu = isGlu ? "False" : "True";
        String strDiv = (div == 0) ? "" : " AND qSlotWCoord.DIV=" + Integer.toString(div);
        
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            Leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT  qSlotWCoord.X1, qSlotWCoord.Y1, qSlotWCoord.X2, qSlotWCoord.Y2 "
                        + "FROM qSlotWCoord "
                        + "WHERE qSlotWCoord.GAD=" + strGlu + strDiv + ";";
                
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        slotD.add(DBHandle.coord2dist(rs));
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return slotD;
    }
    
    public static ArrayList<Integer> distConnQuery(String file, boolean isGlu, int div) {
        ArrayList<Integer> connD = new ArrayList<>(100);
        String strGlu = isGlu ? "True" : "False";
        String strDiv = (div == 0) ? "" : " AND qSlotWCoord.DIV=" + Integer.toString(div);
        
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            TODO leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT  qSlotWCoord.X1, qSlotWCoord.Y1, qSlotWCoord.X2, qSlotWCoord.Y2 "
                        + "FROM tblConnection "
                        + "INNER JOIN qSlotWCoord "
                        + "ON (tblConnection.PostCellID = qSlotWCoord.id2) "
                        + "AND (tblConnection.PreCellID = qSlotWCoord.id1) "
                        + "WHERE tblConnection.Glu=" + strGlu + strDiv + ";";
                
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        connD.add(DBHandle.coord2dist(rs));
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return connD;
    }
    
    public static ArrayList<Integer> uniConnQuery(String file, boolean fw, boolean rev, int div) {
        ArrayList<Integer> connD = new ArrayList<>(100);
        String fwGlu = fw ? "False" : "True";
        String revGlu = rev ? "False" : "True";
        String strDiv = (div == 0) ? "" : " AND qSlotWCoord.DIV=" + Integer.toString(div);
        
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            TODO leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT  qSlotWCoord.X1, qSlotWCoord.Y1, qSlotWCoord.X2, qSlotWCoord.Y2 "
                        + "FROM tblConnection INNER JOIN qSlotWCoord "
                        + "ON (tblConnection.PostCellID = qSlotWCoord.id2) "
                        + "AND (tblConnection.PreCellID = qSlotWCoord.id1) "
                        + "WHERE qSlotWCoord.GAD=" + fwGlu + " "
                        + "AND qSlotWCoord.revGAD=" + revGlu
                        + strDiv + ";";
                
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        connD.add(DBHandle.coord2dist(rs));
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        System.out.println("DIV" + div + "," + connD.size() + " Connects");
        return connD;
    }
    
    public static ArrayList<Integer> reciSlotQuery(String file, boolean fw, boolean rev, int div) {
        ArrayList<Integer> slotD = new ArrayList<>(100);
        String fwGlu = fw ? "False" : "True";
        String revGlu = rev ? "False" : "True";
        String strDiv = (div == 0) ? "" : " AND qSlotWCoord.DIV=" + Integer.toString(div);
        
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            Leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT  qSlotWCoord.X1,qSlotWCoord.X2,qSlotWCoord.Y1,qSlotWCoord.Y2 "
                        + "FROM qSlotWCoord "
                        + "WHERE qSlotWCoord.GAD=" + fwGlu + " "
                        + "AND qSlotWCoord.revGAD=" + revGlu
                        + strDiv + ";";
                
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        slotD.add(rs.getInt("dist"));
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        System.out.println("DIV" + div + "," + slotD.size() + " Slots");
        return slotD;
    }
    
    public static ArrayList<BatchNInterest> slotWDivQry(String file,
            boolean sep, boolean fw, boolean rev, String soi) {
        ArrayList<BatchNInterest> slotWDiv = new ArrayList<>(100);
        String fwGlu = fw ? "False" : "True";
        String revGlu = sep
                ? rev ? "AND qSlotWCoord.revGAD=False" : "AND qSlotWCoord.revGAD=True"
                : "";
        
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            Leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT  qSlotWCoord.DIV, qSlotWCoord." + DBHandle.soiConvert(soi) + " "
                        + "FROM qSlotWCoord "
                        + "WHERE qSlotWCoord.GAD=" + fwGlu + " "
                        + revGlu + " "
                        + "ORDER BY qSlotWCoord.DIV;";
                
                ResultSet rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        if (soi.equalsIgnoreCase("div")) {
                            slotWDiv.add(new BatchNInterest(rs.getInt("DIV"), rs.getInt(soi)));
                        } else {
                            slotWDiv.add(new BatchNInterest(rs.getInt("DIV"), DBHandle.coord2dist(rs)));
                        }                        
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return slotWDiv;
    }
    
    public static ArrayList<BatchNInterest> connWDivQry(String file,
            boolean sep, boolean fw, boolean rev, String soi) {
        ArrayList<BatchNInterest> connD = new ArrayList<>(100);
        BatchNInterest connWDiv;
        String fwGlu = fw ? "False" : "True";
        String revGlu = sep
                ? rev ? "AND qSlotWCoord.revGAD=False" : "AND qSlotWCoord.revGAD=True"
                : "";
        
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            TODO leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT  qSlotWCoord.DIV, qSlotWCoord." + DBHandle.soiConvert(soi) + " "
                        + "FROM tblConnection "
                        + "INNER JOIN qSlotWCoord "
                        + "ON (tblConnection.PostCellID = qSlotWCoord.id2) "
                        + "AND (tblConnection.PreCellID = qSlotWCoord.id1) "
                        + "WHERE qSlotWCoord.GAD=" + fwGlu + " "
                        + revGlu + " "
                        + "ORDER BY qSlotWCoord.DIV;";
                
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        if (soi.equalsIgnoreCase("div")) {
                            connWDiv = new BatchNInterest(rs.getInt("DIV"), rs.getInt(soi));
                        } else {
                            connWDiv = new BatchNInterest(rs.getInt("DIV"), DBHandle.coord2dist(rs));
                        }
                        connD.add(connWDiv);
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return connD;
    }

    /*
     * Used in io degree random connection simulation
     */
    public static ArrayList<EmptyPosition> slotDegQuery(
            String file,
            boolean isGlu,
            boolean boolInput,
            int div) {
        ArrayList<EmptyPosition> slots = new ArrayList<>(100);
        String strID = boolInput ? "id2" : "id1";
        String strGlu = isGlu ? "False" : "True";
        String strDiv = (div == 0) ? "" : " AND qSlotWCoord.DIV=" + Integer.toString(div);
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT  qSlotWCoord." + strID
                        + ", qSlotWCoord.X1, qSlotWCoord.Y1, qSlotWCoord.X2, qSlotWCoord.Y2 "
                        + "FROM qSlotWCoord "
                        + "WHERE qSlotWCoord.GAD=" + strGlu + strDiv + " "
                        + "ORDER BY qSlotWCoord." + strID + ";";
                
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        EmptyPosition s = new EmptyPosition(
                                rs.getInt(strID),
                                DBHandle.coord2dist(rs));
                        slots.add(s);
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
//        System.out.println(slots.size());
        return slots;
    }
    
    public static int[][] chr2SizeNIdSplit(String file, int div) {
//        String strDiv = (div == 0) ? "" : " AND qGroupSizenCell.DIV=" + Integer.toString(div);

        ArrayList<Integer> size = new ArrayList<>(100);
        ArrayList<Integer> id = new ArrayList<>(100);
        ArrayList<Integer> photoCurrent = new ArrayList<>(100);
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            Leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT SizenCell.GroupSize, SizenCell.CellID, SizenCell.PhotoCurrent "
                        + "FROM SizenCell";
                
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        size.add(rs.getInt("GroupSize"));
                        id.add(rs.getInt("CellID"));
                        photoCurrent.add(rs.getInt("PhotoCurrent"));
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
            sizeNId[i][2] = photoCurrent.get(i);
        }
        return sizeNId;
    }
    
    public static int[][] chr2PrePostType(String file, int div) {
//        String strDiv = (div == 0) ? " " : " WHERE tblGADCell.DIV="
//                + Integer.toString(div) + " ";
//        String strFrom = (div == 0) ? " " : " INNER JOIN tblGADCell "
//                + "ON tblConnection.PreCellID = tblGADCell.CellID ";

        ArrayList<Integer> pre = new ArrayList<>(100);
        ArrayList<Integer> post = new ArrayList<>(100);
        ArrayList<Integer> type = new ArrayList<>(100);
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            Leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT qConnList.preCellID, qConnList.postCellID, qConnList.Glu "
                        + " FROM qConnList";
                
                ResultSet rs;
                rs = stmt.executeQuery(query);
                
                while (rs.next()) {
                    try {
                        pre.add(rs.getInt("PreCellID"));
                        post.add(rs.getInt("PostCellID"));
                        type.add(rs.getBoolean("Glu") ? 1 : 2);
                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        int[][] prePostType = new int[pre.size()][3];
        for (int i = 0; i < pre.size(); i++) {
            prePostType[i][0] = pre.get(i);
            prePostType[i][1] = post.get(i);
            prePostType[i][2] = type.get(i);
        }
        return prePostType;
    }
}
