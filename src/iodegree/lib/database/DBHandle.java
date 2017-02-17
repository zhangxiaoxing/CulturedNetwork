/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.database;

import iodegree.dataStrucure.BatchRatio;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Libra
 */
public class DBHandle {

    public int[][] connType(String file, int div) {
        String strDiv = (div == 0) ? " " : " WHERE tblGADCell.DIV=" + Integer.toString(div) + " ";
        String strFrom = (div == 0) ? " " : " INNER JOIN tblGADCell " + "ON tblConnection.PreCellID = tblGADCell.CellID ";
        ArrayList<Integer> pre = new ArrayList<>(100);
        ArrayList<Integer> post = new ArrayList<>(100);
        ArrayList<Integer> type = new ArrayList<>(100);
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT tblConnection.PreCellID, " + " tblConnection.PostCellID, tblConnection.Glu " + " FROM tblConnection " + strFrom + strDiv + " ORDER BY tblConnection.PreCellID, tblConnection.PostCellID;";
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

    public int[][] sizeNIdForSplit(String file, int div) {
        String strDiv = (div == 0) ? "" : " AND qGroupSizenCell.DIV=" + Integer.toString(div);

        ArrayList<Integer> size = new ArrayList<>(100);
        ArrayList<Integer> id = new ArrayList<>(100);
        ArrayList<Integer> type = new ArrayList<>(100);
        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            Leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT qGroupSizenCell.Size, tblGADCell.CellID, tblGADCell.GAD "
                        + "FROM qGroupSizenCell INNER JOIN tblGADCell ON (qGroupSizenCell.GroupNo = tblGADCell.GroupNo) AND (qGroupSizenCell.RecordDate = tblGADCell.RecordDate) "
                        + "WHERE tblGADCell.BadSeal=False " + strDiv + " "
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
        return sizeNId;
    }

    public BatchRatio ratioBatches(String file,
            boolean sep, boolean fw, boolean rev, String soi, BatchRatio ratio) {
        String qstr = soi.equalsIgnoreCase("div") ? soi
                : "X1, qSlotWCoord.Y1, qSlotWCoord.X2, qSlotWCoord.Y2";

//        ArrayList<BatchNInterest> slotD = new ArrayList<>(100);
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

                String query = "SELECT  qSlotWCoord.CultureDate, qSlotWCoord." + qstr + " "
                        + "FROM qSlotWCoord "
                        + "WHERE qSlotWCoord.GAD=" + fwGlu + " "
                        + revGlu + " "
                        + "ORDER BY qSlotWCoord.CultureDate;";

                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    try {
                        if (soi.equalsIgnoreCase("div")) {
                            ratio.addSlot(rs.getDate("CultureDate"), soi, rs.getInt(soi));
                        } else {
                            ratio.addSlot(rs.getDate("CultureDate"), soi, coord2dist(rs));
                        }

                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
//        return slotD;
//    }

//    public ArrayList<BatchNInterest> connWBatchQry(String file,
//            boolean sep, boolean fw, boolean rev, String soi) {
//        ArrayList<BatchNInterest> connD = new ArrayList<>(100);
//        BatchNInterest connWDate;
//        String fwGlu = fw ? "False" : "True";
//        String revGlu = sep
//                ? rev ? "AND qSlotWDist.revGAD=False" : "AND qSlotWDist.revGAD=True"
//                : "";
//        String jdbcPath = "jdbc:odbc:DRIVER="
//                + "Microsoft Access Driver (*.mdb, *.accdb);DBQ=" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
//            TODO leave this debuging info
//            System.out.println("Database connected");
            try (Statement stmt = con.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY)) {
                String query = "SELECT  qSlotWCoord.CultureDate, qSlotWCoord." + qstr + " "
                        + "FROM tblConnection "
                        + "INNER JOIN qSlotWCoord "
                        + "ON (tblConnection.PostCellID = qSlotWCoord.id2) "
                        + "AND (tblConnection.PreCellID = qSlotWCoord.id1) "
                        + "WHERE qSlotWCoord.GAD=" + fwGlu + " "
                        + revGlu + " "
                        + "ORDER BY qSlotWCoord.CultureDate;";

                ResultSet rs;
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    try {
//                        connWDate = new BatchNInterest(rs.getDate("CultureDate"), rs.getInt(soi));
//                        connD.add(connWDate);
                        ratio.addConn(rs.getDate("CultureDate"), soi, soi.equalsIgnoreCase("div") ? rs.getInt(soi) : coord2dist(rs));
//                        D.tp(rs.getDate("CultureDate"), soi, rs.getInt(soi));

                    } catch (SQLException e) {
                        System.out.println("Slot Dist Query Error: " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ratio;
    }

    public static int coord2dist(ResultSet rs) throws SQLException {
        int x1 = rs.getInt("X1");
        int x2 = rs.getInt("X2");
        int y1 = rs.getInt("Y1");
        int y2 = rs.getInt("Y2");
        return (int) Math.round(Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))) * +0.5);
    }

    public static String soiConvert(String soi) {
        return soi.equalsIgnoreCase("div") ? soi
                : "X1, qSlotWCoord.Y1, qSlotWCoord.X2, qSlotWCoord.Y2";
    }
}
