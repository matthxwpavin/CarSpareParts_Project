/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author pwann
 */
public class PartGroupEdit extends javax.swing.JFrame {

    /**
     * Creates new form PartGroupEdit
     */
    GroupTypeModel model;
    ConnectDB conn;
    private int groupID;
    private Font f = new Font("Tahoma", Font.BOLD, 20);

    public PartGroupEdit() {
        initComponents();

        conn = new ConnectDB();
        conn.connect();

        initGroupTypeTable();

        conn.disconnect();

        btnOK.setEnabled(false);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void initGroupTypeTable() {
        ArrayList<PartGroup> group = new ArrayList<PartGroup>();
        group = conn.QueryAllPartGroup();
        if (group.size() == 0) {
            JOptionPane.showMessageDialog(null, "ยังไม่มีหมวดหมู่สินค้า");
            return;
        }

        model = new GroupTypeModel();
        model.addAllData(group);

        GroupTypeTable.setModel(model);
        JTableHeader header = GroupTypeTable.getTableHeader();
        header.setFont(f);
        TableColumnModel columnModel = GroupTypeTable.getColumnModel();
        GroupTypeTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        columnModel.getColumn(0).setPreferredWidth(130);
        columnModel.getColumn(1).setPreferredWidth(880);

        // CarListTable.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = GroupTypeTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        GroupTypeTable = new javax.swing.JTable();
        btnOK = new javax.swing.JButton();
        btnCanCel = new javax.swing.JButton();
        tfEditGroupType = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 153, 0));
        jLabel1.setText("GroupTypeEdit");

        GroupTypeTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0)));
        GroupTypeTable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        GroupTypeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        GroupTypeTable.setRowHeight(30);
        GroupTypeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GroupTypeTableMouseClicked(evt);
            }
        });
        GroupTypeTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GroupTypeTableKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                GroupTypeTableKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(GroupTypeTable);

        btnOK.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnOK.setForeground(new java.awt.Color(0, 204, 0));
        btnOK.setText("ตกลง");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCanCel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCanCel.setForeground(new java.awt.Color(204, 51, 0));
        btnCanCel.setText("ยกเลิก");
        btnCanCel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCanCelActionPerformed(evt);
            }
        });

        tfEditGroupType.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfEditGroupType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfEditGroupTypeKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("แก้ไข:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(tfEditGroupType, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnOK)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnCanCel)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfEditGroupType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOK)
                    .addComponent(btnCanCel))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GroupTypeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GroupTypeTableMouseClicked
        // TODO add your handling code here:
        int selectedRow = GroupTypeTable.getSelectedRow();

        Object value = GroupTypeTable.getValueAt(selectedRow, 0);
        Object value2 = GroupTypeTable.getValueAt(selectedRow, 1);

        groupID = (int) value;
        String carBrand = (String) value2;
        tfEditGroupType.setText(carBrand);

    }//GEN-LAST:event_GroupTypeTableMouseClicked

    private void GroupTypeTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GroupTypeTableKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GroupTypeTableKeyPressed

    private void GroupTypeTableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GroupTypeTableKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_GroupTypeTableKeyTyped

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        // TODO add your handling code here:
        String newName = tfEditGroupType.getText();
        conn.connect();

        conn.updateGroupTypeByID(groupID, newName);
        initGroupTypeTable();

        conn.disconnect();

        tfEditGroupType.setText("");
        btnOK.setEnabled(false);
    }//GEN-LAST:event_btnOKActionPerformed

    private void btnCanCelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCanCelActionPerformed
        // TODO add your handling code here:
        PartType pt = new PartType();
        this.setVisible(false);
    }//GEN-LAST:event_btnCanCelActionPerformed

    private void tfEditGroupTypeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfEditGroupTypeKeyTyped
        // TODO add your handling code here:
        btnOK.setEnabled(true);
    }//GEN-LAST:event_tfEditGroupTypeKeyTyped

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PartGroupEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PartGroupEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PartGroupEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PartGroupEdit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PartGroupEdit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable GroupTypeTable;
    private javax.swing.JButton btnCanCel;
    private javax.swing.JButton btnOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField tfEditGroupType;
    // End of variables declaration//GEN-END:variables
}
