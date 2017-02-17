/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Libra
 */
public class MyTableModel extends AbstractTableModel {

    private String[] columnNames;
    private String[] columnNamesD = {"Dist@5", "Prob@5", "Fit@5",
        "Dist@6", "Prob@6", "Fit@6",
        "Dist@7", "Prob@7", "Fit@7",
        "Dist@8", "Prob@7", "Fit@8",};
    private String[] columnNamesA = {"Dist", "Prob", "Fit"};
    private Object[][] data;

    public MyTableModel(Object[][] indata, boolean boolDivAll) {
        data = indata;
        if (boolDivAll) {
            columnNames = columnNamesA;
        } else {
            columnNames = columnNamesD;
        }
    }

    public MyTableModel(Object[][] indata) {
        data = indata;
        columnNames = new String[indata[0].length];
    }

    public MyTableModel(Object[] indata) {
        Object[][] tempData=new Object[1][indata.length];
        tempData[0]=indata;
        data = tempData;
        columnNames = new String[indata.length];
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        try {
            return data[row][col];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

    }

    /*
     * JTable uses this method to determine the default renderer/ editor for
     * each cell. If we didn't implement this method, then the last column would
     * contain text ("true"/"false"), rather than a check box.
     */
//        public Class getColumnClass(int c) {
//            return getValueAt(0, c).getClass();
//        }
//
//        /*
//         * Don't need to implement this method unless your table's
//         * editable.
//         */
//        public boolean isCellEditable(int row, int col) {
//            //Note that the data/cell address is constant,
//            //no matter where the cell appears onscreen.
//            if (col < 2) {
//                return false;
//            } else {
//                return true;
//            }
//        }
//
//        /*
//         * Don't need to implement this method unless your table's
//         * data can change.
//         */
//        @Override
//        public void setValueAt(Object value, int row, int col) {
//            data[row][col] = value;
//            fireTableCellUpdated(row, col);
//        }
}
