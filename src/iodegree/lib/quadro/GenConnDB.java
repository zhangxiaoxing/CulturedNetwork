/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib.quadro;

import iodegree.lib.D;
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
public class GenConnDB {

    private String pathToFile;

    public GenConnDB(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public ArrayList<PotentialSynapse> getSlots() {
        ArrayList<PotentialSynapse> slots = new ArrayList<>(500);
        String jdbcPath = "jdbc:ucanaccess://" + this.pathToFile;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY)) {
                String qryStr = "SELECT DISTINCT tblCellwCoord.RecordDate, tblCellwCoord.CultureDate, tblCellwCoord.GroupNo, tblCellwCoord.DIV, tblCellwCoord.CellID AS id1, tblCellwCoord_1.CellID AS id2, tblCellwCoord.GAD, tblCellwCoord_1.GAD AS revGAD, tblCellwCoord.CellX, tblCellwCoord.CellY, tblCellwCoord_1.CellX, tblCellwCoord_1.CellY "
                        + "FROM tblCellwCoord INNER JOIN tblCellwCoord AS tblCellwCoord_1 ON tblCellwCoord.GroupNo = tblCellwCoord_1.GroupNo AND tblCellwCoord.RecordDate = tblCellwCoord_1.RecordDate "
                        + "WHERE tblCellwCoord_1.CellID<>tblCellwCoord.CellID "
                        + "ORDER BY tblCellwCoord.RecordDate, tblCellwCoord.GroupNo, tblCellwCoord.CellID;";
                ResultSet rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    PotentialSynapse aSlot = new PotentialSynapse();
                    aSlot.setDate(rs.getDate(1));
                    aSlot.setGroup(rs.getInt(3));
                    aSlot.setDiv(rs.getInt(4));
                    aSlot.setId1(rs.getInt(5));
                    aSlot.setId2(rs.getInt(6));
                    aSlot.setFwdGlu(!rs.getBoolean(7));//GAD
                    aSlot.setRevGlu(!rs.getBoolean(8));//GAD
                    int x1 = rs.getInt(9);
                    int y1 = rs.getInt(10);
                    int deltaX = rs.getInt(11) - x1;
                    int deltaY = rs.getInt(12) - y1;
                    double dist = (double) Math.sqrt(deltaX * deltaX + deltaY * deltaY) * 0.638;
                    aSlot.setDist(dist);
                    slots.add(aSlot);
                }
            }

        } catch (SQLException e) {
            D.p(e.toString());
        }
        return slots;
    }

    public ArrayList<PotentialSynapse> getConns() {
        ArrayList<PotentialSynapse> conns = new ArrayList<>(500);
        String jdbcPath = "jdbc:ucanaccess://" + this.pathToFile;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY)) {
                String qryStr = "SELECT DISTINCT tblCellwCoord.DIV, tblCellwCoord.GAD, tblCellwCoord_1.GAD, tblCellwCoord.CellX, tblCellwCoord.CellY, tblCellwCoord_1.CellX, tblCellwCoord_1.CellY, tblConnection.PreCellID, tblConnection.PostCellID "
                        + "FROM (tblConnection INNER JOIN tblCellwCoord ON tblConnection.PreCellID = tblCellwCoord.CellID) INNER JOIN tblCellwCoord AS tblCellwCoord_1 ON tblConnection.PostCellID = tblCellwCoord_1.CellID; ";
                ResultSet rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    PotentialSynapse aConn = new PotentialSynapse();
                    aConn.setDiv(rs.getInt(1));
                    aConn.setFwdGlu(!rs.getBoolean(2));//GAD
                    aConn.setRevGlu(!rs.getBoolean(3));//GAD
                    int x1 = rs.getInt(4);
                    int y1 = rs.getInt(5);
                    int deltaX = rs.getInt(6) - x1;
                    int deltaY = rs.getInt(7) - y1;
                    double dist = (double) Math.sqrt(deltaX * deltaX + deltaY * deltaY) * 0.638;
                    aConn.setDist(dist);
                    aConn.setId1(rs.getInt(8));
                    aConn.setId2(rs.getInt(9));
                    conns.add(aConn);
                }
            }

        } catch (SQLException e) {
            D.p(e.toString());
        }
        return conns;
    }

    public ArrayList<PotentialSynapse> getGluOnlySlots() {
        ArrayList<PotentialSynapse> slots = new ArrayList<>(500);
        String jdbcPath = "jdbc:ucanaccess://" + this.pathToFile;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY)) {
                String qryStr = "SELECT DISTINCT tblCellwCoord.RecordDate, tblCellwCoord.CultureDate, tblCellwCoord.GroupNo, tblCellwCoord.DIV, tblCellwCoord.CellID AS id1, tblCellwCoord_1.CellID AS id2, tblCellwCoord.GAD, tblCellwCoord_1.GAD AS revGAD, tblCellwCoord.CellX, tblCellwCoord.CellY, tblCellwCoord_1.CellX, tblCellwCoord_1.CellY "
                        + "FROM tblCellwCoord INNER JOIN tblCellwCoord AS tblCellwCoord_1 ON tblCellwCoord.GroupNo = tblCellwCoord_1.GroupNo AND tblCellwCoord.RecordDate = tblCellwCoord_1.RecordDate "
                        + "WHERE tblCellwCoord_1.CellID<>tblCellwCoord.CellID "
                        + "ORDER BY tblCellwCoord.RecordDate, tblCellwCoord.GroupNo, tblCellwCoord.CellID;";
                ResultSet rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    PotentialSynapse aSlot = new PotentialSynapse();
                    aSlot.setDate(rs.getDate(1));
                    aSlot.setGroup(rs.getInt(3));
                    aSlot.setDiv(rs.getInt(4));
                    aSlot.setId1(rs.getInt(5));
                    aSlot.setId2(rs.getInt(6));
                    aSlot.setFwdGlu(!rs.getBoolean(7));//GAD
                    aSlot.setRevGlu(!rs.getBoolean(8));//GAD
                    int x1 = rs.getInt(9);
                    int y1 = rs.getInt(10);
                    int deltaX = rs.getInt(11) - x1;
                    int deltaY = rs.getInt(12) - y1;
                    double dist = (double) Math.sqrt(deltaX * deltaX + deltaY * deltaY) * 0.638;
                    aSlot.setDist(dist);
                    if (aSlot.getFwdGlu() && aSlot.getRevGlu()) {
                        slots.add(aSlot);
                    }
                }
            }

        } catch (SQLException e) {
            D.p(e.toString());
        }
        return slots;
    }

    public ArrayList<PotentialSynapse> getGluOnlyConns() {
        ArrayList<PotentialSynapse> conns = new ArrayList<>(500);
        String jdbcPath = "jdbc:ucanaccess://" + this.pathToFile;
        try (Connection con = DriverManager.getConnection(jdbcPath)) {
            try (Statement stmt = con.createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY)) {
                String qryStr = "SELECT DISTINCT tblCellwCoord.DIV, tblCellwCoord.GAD, tblCellwCoord_1.GAD, tblCellwCoord.CellX, tblCellwCoord.CellY, tblCellwCoord_1.CellX, tblCellwCoord_1.CellY, tblConnection.PreCellID, tblConnection.PostCellID "
                        + "FROM (tblConnection INNER JOIN tblCellwCoord ON tblConnection.PreCellID = tblCellwCoord.CellID) INNER JOIN tblCellwCoord AS tblCellwCoord_1 ON tblConnection.PostCellID = tblCellwCoord_1.CellID; ";
                ResultSet rs = stmt.executeQuery(qryStr);
                while (rs.next()) {
                    PotentialSynapse aConn = new PotentialSynapse();
                    aConn.setDiv(rs.getInt(1));
                    aConn.setFwdGlu(!rs.getBoolean(2));//GAD
                    aConn.setRevGlu(!rs.getBoolean(3));//GAD
                    int x1 = rs.getInt(4);
                    int y1 = rs.getInt(5);
                    int deltaX = rs.getInt(6) - x1;
                    int deltaY = rs.getInt(7) - y1;
                    double dist = (double) Math.sqrt(deltaX * deltaX + deltaY * deltaY) * 0.638;
                    aConn.setDist(dist);
                    aConn.setId1(rs.getInt(8));
                    aConn.setId2(rs.getInt(9));
                    if (aConn.getFwdGlu() && aConn.getRevGlu()) {
                        conns.add(aConn);
                    }
                }
            }

        } catch (SQLException e) {
            D.p(e.toString());
        }
        return conns;
    }
}
