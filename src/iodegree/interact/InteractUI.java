/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.interact;

import iodegree.lib.MyTableModel;
import iodegree.lib.CommonsLib;
import iodegree.lib.D;
import iodegree.lib.StatisticsConversion;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JFileChooser;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.inference.TestUtils;

/**
 *
 * @author Libra
 */
public class InteractUI extends javax.swing.JFrame {

    /**
     * Creates new form InteractUI
     */
    public InteractUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DataFileChooser = new javax.swing.JFileChooser();
        btnGrpType = new javax.swing.ButtonGroup();
        btnGrpInOut = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblOpenDatabase = new javax.swing.JLabel();
        btnOpenDatabase = new javax.swing.JButton();
        btnOpenDbDefault = new javax.swing.JButton();
        rdoGlu = new javax.swing.JRadioButton();
        rdoGABA = new javax.swing.JRadioButton();
        resultPane = new javax.swing.JScrollPane();
        resultTableReci = new javax.swing.JTable();
        rdoInput = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        rdoOutput = new javax.swing.JRadioButton();
        btnQry = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblOpenDatabase.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblOpenDatabase.setText("1. Open Database File :");

        btnOpenDatabase.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOpenDatabase.setText("Open");
        btnOpenDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenDatabaseActionPerformed(evt);
            }
        });

        btnOpenDbDefault.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOpenDbDefault.setText("Default");
        btnOpenDbDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenDbDefaultActionPerformed(evt);
            }
        });

        btnGrpType.add(rdoGlu);
        rdoGlu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoGlu.setSelected(true);
        rdoGlu.setText("Glu");
        rdoGlu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGluActionPerformed(evt);
            }
        });

        btnGrpType.add(rdoGABA);
        rdoGABA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoGABA.setText("GABA");
        rdoGABA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGABAActionPerformed(evt);
            }
        });

        resultPane.setViewportView(resultTableReci);

        btnGrpInOut.add(rdoInput);
        rdoInput.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoInput.setSelected(true);
        rdoInput.setText("Input");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("2. Effect of");

        btnGrpInOut.add(rdoOutput);
        rdoOutput.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoOutput.setText("Output (Recurrent)");

        btnQry.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnQry.setText("Query");
        btnQry.setEnabled(false);
        btnQry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resultPane, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpenDatabase)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnOpenDatabase)
                        .addGap(18, 18, 18)
                        .addComponent(btnOpenDbDefault)
                        .addGap(18, 18, 18)
                        .addComponent(btnQry))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoInput)
                                .addGap(18, 18, 18)
                                .addComponent(rdoOutput))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoGlu)
                                .addGap(18, 18, 18)
                                .addComponent(rdoGABA)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnOpenDbDefault)
                    .addComponent(btnOpenDatabase)
                    .addComponent(lblOpenDatabase)
                    .addComponent(btnQry))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(rdoGABA)
                    .addComponent(rdoGlu)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdoInput)
                    .addComponent(rdoOutput))
                .addGap(18, 18, 18)
                .addComponent(resultPane, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOpenDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenDatabaseActionPerformed
        int returnVal = DataFileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            pathToFile = DataFileChooser.getSelectedFile().getAbsolutePath();
            readFile();
            btnQry.setEnabled(true);
        }
    }//GEN-LAST:event_btnOpenDatabaseActionPerformed

    private void btnOpenDbDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenDbDefaultActionPerformed
        pathToFile = CommonsLib.getDefaultFile();
        readFile();
        btnQry.setEnabled(true);

    }//GEN-LAST:event_btnOpenDbDefaultActionPerformed

    private void rdoGluActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGluActionPerformed
    }//GEN-LAST:event_rdoGluActionPerformed

    private void rdoGABAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGABAActionPerformed
    }//GEN-LAST:event_rdoGABAActionPerformed

    private void btnQryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQryActionPerformed
        process();
    }//GEN-LAST:event_btnQryActionPerformed

    private void readFile() {
        InteractDB db = new InteractDB();
        db.queryDB(pathToFile);
        cellSet = db.getCellSet();
        cells = db.getCells();
    }

    private void process() {


        SummaryStatistics WGlu = new SummaryStatistics();
        SummaryStatistics WOGlu = new SummaryStatistics();
        SummaryStatistics WGABA = new SummaryStatistics();
        SummaryStatistics WOGABA = new SummaryStatistics();

        if (rdoInput.isSelected()) { //input drive output
            for (Integer i : cellSet) {
                InteractItem cell = cells.get(i);
                if (rdoGlu.isSelected() && cell.getGluInRatio() >= 0) {
                    SummaryStatistics stat = (cell.getGluInRatio() > 0) ? (cell.isGlu() ? WGlu : WGABA) : (cell.isGlu() ? WOGlu : WOGABA);
                    stat.addValue(cell.getOutRatio());
                } else if (rdoGABA.isSelected() && cell.getGabaInRatio() >= 0) {
                    SummaryStatistics stat = (cell.getGabaInRatio() > 0) ? (cell.isGlu() ? WGlu : WGABA) : (cell.isGlu() ? WOGlu : WOGABA);
                    stat.addValue(cell.getOutRatio());
                }
            }
        } else {// output drive input
            for (Integer i : cellSet) {
                InteractItem cell = cells.get(i);
                if (rdoGlu.isSelected() && cell.isGlu()) {
                    if (cell.getOutRatio() > 0) {
                        if (cell.getGluInRatio() >= 0) {
                            WGlu.addValue(cell.getGluInRatio());
                        }
                        if (cell.getGabaInRatio() >= 0) {
                            WGABA.addValue(cell.getGabaInRatio());
                        }
                    } else {
                        if (cell.getGluInRatio() >= 0) {
                            WOGlu.addValue(cell.getGluInRatio());
                        }
                        if (cell.getGabaInRatio() >= 0) {
                            WOGABA.addValue(cell.getGabaInRatio());
                        }
                    }
                } else if (!(rdoGlu.isSelected() || cell.isGlu())) {
                    if (cell.getOutRatio() > 0) {
                        if (cell.getGluInRatio() >= 0) {
                            WGlu.addValue(cell.getGluInRatio());
                        }
                        if (cell.getGabaInRatio() >= 0) {
                            WGABA.addValue(cell.getGabaInRatio());
                        }
                    } else {
                        if (cell.getGluInRatio() >= 0) {
                            WOGlu.addValue(cell.getGluInRatio());
                        }
                        if (cell.getGabaInRatio() >= 0) {
                            WOGABA.addValue(cell.getGabaInRatio());
                        }
                    }
                }
            }
        }
        double t1 = TestUtils.homoscedasticTTest(WGlu, WOGlu);
        double t2 = TestUtils.homoscedasticTTest(WGABA, WOGABA);


        String glu = rdoGlu.isSelected() ? "Glutamate " : "GABA ";
        String in = rdoInput.isSelected() ? "Input " : "Output ";
        String out = rdoInput.isSelected() ? "Output " : "Input ";
        String w = "With ";
        String wo = "Without ";
        String g = "Glutamate ";
        String a = "GABA ";
        String[] header = {g + out + wo + glu + in, g + out + w + glu + in, a + out + wo + glu + in, a + out + w + glu + in};
        String[] rowTitle = {"Mean", "S.D.", "S.E.M", "p"};
        Double[][] data = new Double[4][4];
        SummaryStatistics[] tempCyc = new SummaryStatistics[4];
        tempCyc[0] = WOGlu;
        tempCyc[1] = WGlu;
        tempCyc[2] = WOGABA;
        tempCyc[3] = WGABA;

        for (int i = 0; i < 4; i++) {
            data[0][i] = tempCyc[i].getMean();
            data[1][i] = tempCyc[i].getStandardDeviation();
            data[2][i] = tempCyc[i].getStandardDeviation() / Math.sqrt(tempCyc[i].getN());
        }

        data[3][1] = new Double(t1);
        data[3][3] = new Double(t2);

        fillTable(StatisticsConversion.fillTable(header, rowTitle, data));
    }

    private void fillTable(ArrayList<Object[]> dataList) {
        Object[][] tabData = new Object[dataList.size()][dataList.get(0).length];
        int currRow = 0;
        for (Object[] row : dataList) {
            tabData[currRow] = row;
            currRow++;
        }
        MyTableModel model = new MyTableModel(tabData);
        resultPane.setColumnHeaderView(null);
        resultTableReci.setModel(model);
    }

    private void fillTable(Object[][] tabData) {
        MyTableModel model = new MyTableModel(tabData);
        resultPane.setColumnHeaderView(null);
        resultTableReci.setModel(model);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InteractUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InteractUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser DataFileChooser;
    private javax.swing.ButtonGroup btnGrpInOut;
    private javax.swing.ButtonGroup btnGrpType;
    private javax.swing.JButton btnOpenDatabase;
    private javax.swing.JButton btnOpenDbDefault;
    private javax.swing.JButton btnQry;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblOpenDatabase;
    private javax.swing.JRadioButton rdoGABA;
    private javax.swing.JRadioButton rdoGlu;
    private javax.swing.JRadioButton rdoInput;
    private javax.swing.JRadioButton rdoOutput;
    private javax.swing.JScrollPane resultPane;
    private transient javax.swing.JTable resultTableReci;
    // End of variables declaration//GEN-END:variables
    private String pathToFile;
    HashSet<Integer> cellSet;
    HashMap<Integer, InteractItem> cells;
//    boolean glu;
}