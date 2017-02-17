/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree;

import iodegree.lib.database.LegacyDB;
import iodegree.lib.MyFit;
import iodegree.lib.MyTableModel;
import iodegree.dataStrucure.ConnProbResult;
import iodegree.lib.CommonsLib;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Libra
 */
public class DistUI2 extends javax.swing.JFrame {

    /**
     * Creates new form DistUI2
     */
    public DistUI2() {
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
        gluGABAGroup = new javax.swing.ButtonGroup();
        timeFrameGroup = new javax.swing.ButtonGroup();
        lblQueryOption1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnIO = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtFitResult = new javax.swing.JTextArea();
        rdoDivEach = new javax.swing.JRadioButton();
        rdoDivAll = new javax.swing.JRadioButton();
        btnOpenDbDefault = new javax.swing.JButton();
        btnQuery = new javax.swing.JButton();
        btnOpenDatabase = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        lblOpenDatabase = new javax.swing.JLabel();
        rdoGABAOnGABA = new javax.swing.JRadioButton();
        rdoGluOnGlu = new javax.swing.JRadioButton();
        lblQueryOption = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtBinSize = new javax.swing.JTextField();
        rdoGluOnGABA = new javax.swing.JRadioButton();
        rdoGABAOnGlu = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblQueryOption1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblQueryOption1.setText("3. Degree Test");
        lblQueryOption1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Fit Result");

        btnIO.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnIO.setText("IO Degree");
        btnIO.setEnabled(false);
        btnIO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIOActionPerformed(evt);
            }
        });

        txtFitResult.setColumns(20);
        txtFitResult.setRows(4);
        jScrollPane1.setViewportView(txtFitResult);

        timeFrameGroup.add(rdoDivEach);
        rdoDivEach.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoDivEach.setSelected(true);
        rdoDivEach.setText("Day By Day");
        rdoDivEach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDivEachActionPerformed(evt);
            }
        });

        timeFrameGroup.add(rdoDivAll);
        rdoDivAll.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoDivAll.setText("All");
        rdoDivAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDivAllActionPerformed(evt);
            }
        });

        btnOpenDbDefault.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOpenDbDefault.setText("Default");
        btnOpenDbDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenDbDefaultActionPerformed(evt);
            }
        });

        btnQuery.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnQuery.setText("Query");
        btnQuery.setEnabled(false);
        btnQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQueryActionPerformed(evt);
            }
        });

        btnOpenDatabase.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOpenDatabase.setText("Open");
        btnOpenDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenDatabaseActionPerformed(evt);
            }
        });

        resultTable.setModel(new MyTableModel(arrTableData, boolDivMixed));
        jScrollPane5.setViewportView(resultTable);

        lblOpenDatabase.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblOpenDatabase.setText("1. Open Database File :");

        gluGABAGroup.add(rdoGABAOnGABA);
        rdoGABAOnGABA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoGABAOnGABA.setText("GABA On GABA");
        rdoGABAOnGABA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGABAOnGABAActionPerformed(evt);
            }
        });

        gluGABAGroup.add(rdoGluOnGlu);
        rdoGluOnGlu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoGluOnGlu.setSelected(true);
        rdoGluOnGlu.setText("Glu On Glu");
        rdoGluOnGlu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGluOnGluActionPerformed(evt);
            }
        });

        lblQueryOption.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblQueryOption.setText("2. Query Option:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Bin Size");

        txtBinSize.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtBinSize.setText("23");

        gluGABAGroup.add(rdoGluOnGABA);
        rdoGluOnGABA.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoGluOnGABA.setText("Glu On GABA");
        rdoGluOnGABA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGluOnGABAActionPerformed(evt);
            }
        });

        gluGABAGroup.add(rdoGABAOnGlu);
        rdoGABAOnGlu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        rdoGABAOnGlu.setText("GABA On Glu");
        rdoGABAOnGlu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoGABAOnGluActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOpenDatabase)
                    .addComponent(lblQueryOption)
                    .addComponent(lblQueryOption1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnIO)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnOpenDatabase)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnOpenDbDefault))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtBinSize, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnQuery)))
                                .addGap(0, 600, Short.MAX_VALUE))
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdoGluOnGlu)
                                .addGap(18, 18, 18)
                                .addComponent(rdoGluOnGABA)
                                .addGap(18, 18, 18)
                                .addComponent(rdoGABAOnGlu)
                                .addGap(18, 18, 18)
                                .addComponent(rdoGABAOnGABA))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdoDivEach)
                        .addGap(18, 18, 18)
                        .addComponent(rdoDivAll)
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblOpenDatabase)
                            .addComponent(btnOpenDatabase)
                            .addComponent(btnOpenDbDefault))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(rdoDivEach)
                            .addComponent(rdoDivAll)
                            .addComponent(rdoGluOnGlu)
                            .addComponent(rdoGABAOnGlu)
                            .addComponent(rdoGABAOnGABA)
                            .addComponent(rdoGluOnGABA))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel1)
                            .addComponent(btnQuery)
                            .addComponent(txtBinSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(lblQueryOption)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblQueryOption1)
                    .addComponent(btnIO))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIOActionPerformed
        IoDistanceUI.main(null);
    }//GEN-LAST:event_btnIOActionPerformed

    private void rdoDivEachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDivEachActionPerformed
        boolDivMixed = false;
    }//GEN-LAST:event_rdoDivEachActionPerformed

    private void rdoDivAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDivAllActionPerformed
        boolDivMixed = true;
    }//GEN-LAST:event_rdoDivAllActionPerformed

    private void btnOpenDbDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenDbDefaultActionPerformed
        pathToFile = CommonsLib.getDefaultFile();

        btnQuery.setEnabled(true);

    }//GEN-LAST:event_btnOpenDbDefaultActionPerformed

    private void btnQueryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQueryActionPerformed

        txtFitResult.setText("");

        System.out.println("============");

        if (boolDivMixed) {
//            fitTransfer = new MyFit[1];
            lstSlotVDist = LegacyDB.reciSlotQuery(pathToFile, fwGlu, revGlu, 0);
            lstUniConn = LegacyDB.uniConnQuery(pathToFile, fwGlu, revGlu, 0);
            Collections.sort(lstSlotVDist);
            Collections.sort(lstUniConn);
            arrTableData = new Object[lstSlotVDist.size() / 5][3];
            sumUpUni(0);
        } else {
//            fitTransfer = new MyFit[9];
            lstSlotVDist = LegacyDB.reciSlotQuery(pathToFile, fwGlu, revGlu, 0);
            arrTableData = new Object[lstSlotVDist.size() / 20][12];
            for (int i = 5; i <= 8; i++) {
                lstSlotVDist = LegacyDB.reciSlotQuery(pathToFile, fwGlu, revGlu, i);
                lstUniConn = LegacyDB.uniConnQuery(pathToFile, fwGlu, revGlu, i);
                Collections.sort(lstSlotVDist);
                Collections.sort(lstUniConn);
                sumUpUni(i);
            }
        }
        MyTableModel table = new MyTableModel(arrTableData, boolDivMixed);
        resultTable.setModel(table);
        btnIO.setEnabled(true);
    }//GEN-LAST:event_btnQueryActionPerformed

    private void sumUpUni(int div) {

        int binSize = Integer.parseInt(txtBinSize.getText());
        //TODO check for too large and too small bin size;
        int nGroup = (int) Math.floor((float) lstSlotVDist.size() / binSize);
        ConnProbResult[] distGroup;
        double[][] regData;

        if (nGroup > 1) {
            distGroup = new ConnProbResult[nGroup];
            regData = new double[nGroup][3];
            /*
             * n-1 Group, decides dist range in group
             */
            int j = 0;
            for (int i = 0; i < nGroup - 1; i++) {
                distGroup[i] = new ConnProbResult(binSize,
                        lstSlotVDist.get(j), //low
                        lstSlotVDist.get(j + binSize - 1));     //high
                j += binSize;
            }

            /*
             * Last Group
             */
            distGroup[nGroup - 1] = new ConnProbResult(lstSlotVDist.size() - j,
                    lstSlotVDist.get(j),
                    lstSlotVDist.get(lstSlotVDist.size() - 1));
        } else {

            /*
             * for rare connects, only 1 group;
             */
            nGroup = 1;
            distGroup = new ConnProbResult[nGroup];
            regData = new double[nGroup][3];
            distGroup[0] = new ConnProbResult(lstSlotVDist.size(),
                    lstSlotVDist.get(0),
                    lstSlotVDist.get(lstSlotVDist.size() - 1));
        }

        /*
         * dispatch connects in group
         */
        int currGroup = 0;
        for (int i = 0; i < lstUniConn.size();) {
//            System.out.println(lstUniConn.size()+","+i+","+nGroup+","+currGroup);
            if (lstUniConn.get(i) <= distGroup[currGroup].getHighBound()) {
                distGroup[currGroup].addNConn();
                i++;
            } else {
                currGroup++;
            }
        }

        /*
         * Fill the table
         */
        int column = boolDivMixed ? 0 : (div - 5) * 3; //array starts with 0
        for (int i = 0; i < nGroup; i++) {
            distGroup[i].fill();

            arrTableData[i][column] = new Integer(distGroup[i].getAvg());
            arrTableData[i][column + 1] = new Float(distGroup[i].getConnProb());

            regData[i][0] = distGroup[i].getAvg();
            regData[i][1] = distGroup[i].getConnProb();

        }

        /*
         * Generate fit data
         */
        MyFit fitData = new MyFit(regData);
        txtFitResult.append("DIV=" + div
                + ", Intercept=" + String.format("%.4f", fitData.getIntercept())
                + ", Slop=" + String.format("%.4f", fitData.getSlope())
                + ", r=" + String.format("%.4f", fitData.getR()) + "\n");

        for (int i = 0; i < nGroup; i++) {
            arrTableData[i][column + 2] =
                    new Float(fitData.predict(distGroup[i].getAvg()));
        }

    }

    private void btnOpenDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenDatabaseActionPerformed
        int returnVal = DataFileChooser.showOpenDialog(this);
        if (returnVal == DataFileChooser.APPROVE_OPTION) {
            pathToFile = DataFileChooser.getSelectedFile().getAbsolutePath();
//            fileTransfer = pathToFile;
//            System.out.println("File " + pathToFile + " opened.");
            btnQuery.setEnabled(true);
        }
    }//GEN-LAST:event_btnOpenDatabaseActionPerformed

    private void rdoGABAOnGABAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGABAOnGABAActionPerformed
        fwGlu = false;
        revGlu = false;
//        gluTransfer = false;
//        txtBinSize.setText("12");
    }//GEN-LAST:event_rdoGABAOnGABAActionPerformed

    private void rdoGluOnGluActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGluOnGluActionPerformed
        fwGlu = true;
        revGlu = true;
//        gluTransfer = true;
//        txtBinSize.setText("23");
    }//GEN-LAST:event_rdoGluOnGluActionPerformed

    private void rdoGluOnGABAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGluOnGABAActionPerformed
        fwGlu = true;
        revGlu = false;
    }//GEN-LAST:event_rdoGluOnGABAActionPerformed

    private void rdoGABAOnGluActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoGABAOnGluActionPerformed
        fwGlu = false;
        revGlu = true;
    }//GEN-LAST:event_rdoGABAOnGluActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DistUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DistUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DistUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DistUI2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DistUI2().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser DataFileChooser;
    private javax.swing.JButton btnIO;
    private javax.swing.JButton btnOpenDatabase;
    private javax.swing.JButton btnOpenDbDefault;
    private javax.swing.JButton btnQuery;
    private javax.swing.ButtonGroup gluGABAGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblOpenDatabase;
    private javax.swing.JLabel lblQueryOption;
    private javax.swing.JLabel lblQueryOption1;
    private javax.swing.JRadioButton rdoDivAll;
    private javax.swing.JRadioButton rdoDivEach;
    private javax.swing.JRadioButton rdoGABAOnGABA;
    private javax.swing.JRadioButton rdoGABAOnGlu;
    private javax.swing.JRadioButton rdoGluOnGABA;
    private javax.swing.JRadioButton rdoGluOnGlu;
    private javax.swing.JTable resultTable;
    private javax.swing.ButtonGroup timeFrameGroup;
    private javax.swing.JTextField txtBinSize;
    private javax.swing.JTextArea txtFitResult;
    // End of variables declaration//GEN-END:variables
    private String pathToFile;
    private boolean fwGlu = true;
    private boolean revGlu = true;
    private ArrayList<Integer> lstSlotVDist;
    private ArrayList<Integer> lstUniConn;
    private Object[][] arrTableData = new Object[20][12];
    private static boolean boolDivMixed = false;
//    private static MyFit[] fitTransfer;//=new MyFit[4];
//    private static String fileTransfer;
//    private static boolean gluTransfer = true;
}