/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.interact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author Libra
 */
public class InteractDB {

    private HashSet<Integer> cellSet = new HashSet<>(1000);
    private HashMap<Integer, InteractItem> cells = new HashMap<>(1000);

    public int[] degreeQuery(String file, boolean isGlu, boolean isInput, int div) {
        int[] degree = new int[4];
        String divFrom = (div == 0)
                ? "" : "INNER JOIN tblGADCell ON tblConnection.PreCellID = tblGADCell.CellID ";
        String divWhere = (div == 0)
                ? "" : " AND tblGADCell.DIV=" + Integer.toString(div) + " ";

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

    public void queryDB(String file) {


        String jdbcPath = "jdbc:ucanaccess://" + file;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                            ResultSet.TYPE_FORWARD_ONLY,
                            ResultSet.CONCUR_READ_ONLY)) {

                /*
                 * Id and type;
                 */
                String qryStr = "SELECT tblGADCell.CellID, tblGADCell.GAD "
                        + "FROM tblGADCell "
                        + "WHERE tblGADCell.BadSeal=False;";
                ResultSet rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    int cellId = rs.getInt(1);
                    cellSet.add(cellId);
                    InteractItem newItem = new InteractItem();
                    newItem.setId(cellId);
                    newItem.setIsGlu(!rs.getBoolean(2));
                    cells.put(cellId, newItem);
                }

                /*
                 * out slots
                 */
                qryStr = "SELECT qSlotWCoord.id1, Count(qSlotWCoord.id2) AS slots "
                        + "FROM qSlotWCoord "
                        + "GROUP BY qSlotWCoord.id1;";
                rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    try{
                    cells.get(rs.getInt(1)).setOutSlots(rs.getInt(2));
                    }catch (SQLException e){
                        break;
                    }
                }

                /*
                 * outputs
                 */
                qryStr = "SELECT tblConnection.PreCellID, Count(tblConnection.PostCellID) AS outputs "
                        + "FROM tblConnection "
                        + "GROUP BY tblConnection.PreCellID;";
                rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    try{
                    cells.get(rs.getInt(1)).setOutConn(rs.getInt(2));
                    }catch (SQLException e){
                        break;
                    }
                }


                /*
                 * Glu In Slots
                 */
                qryStr = "SELECT qSlotWCoord.id2, Count(qSlotWCoord.id1) AS count "
                        + "FROM qSlotWCoord "
                        + "WHERE qSlotWCoord.GAD=False "
                        + "GROUP BY qSlotWCoord.id2; ";
                rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    try{
                    cells.get(rs.getInt(1)).setGluInSlots(rs.getInt(2));
                    }catch (SQLException e){
                        break;
                    }
                }

                /*
                 * GABA In Slots
                 */
                qryStr = "SELECT qSlotWCoord.id2, Count(qSlotWCoord.id1) AS count "
                        + "FROM qSlotWCoord "
                        + "WHERE qSlotWCoord.GAD=True "
                        + "GROUP BY qSlotWCoord.id2;";
                rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    try{
                    cells.get(rs.getInt(1)).setGabaInSlots(rs.getInt(2));
                    }catch (SQLException e){
                        break;
                    }
                }


                /*
                 * Glu In Conn
                 */
                qryStr = "SELECT tblConnection.PostCellID, Count(tblConnection.PreCellID) AS [count] "
                        + "FROM tblConnection "
                        + "WHERE tblConnection.Glu=True "
                        + "GROUP BY tblConnection.PostCellID;";
                rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    try{
                    cells.get(rs.getInt(1)).setGluInConn(rs.getInt(2));
                    }catch (SQLException e){
                        break;
                    }
                }


                /*
                 * GABA In Conn
                 */
                qryStr = "SELECT tblConnection.PostCellID, Count(tblConnection.PreCellID) AS [count] "
                        + "FROM tblConnection "
                        + "WHERE tblConnection.Glu=False "
                        + "GROUP BY tblConnection.PostCellID;";
                rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    try{
                    cells.get(rs.getInt(1)).setGabaInConn(rs.getInt(2));
                    }catch (SQLException e){
                        break;
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public HashSet<Integer> getCellSet() {
        return cellSet;
    }

    public HashMap<Integer, InteractItem> getCells() {
        return cells;
    }
}
