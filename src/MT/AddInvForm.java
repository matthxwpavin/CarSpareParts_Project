/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import static com.sun.javafx.tk.Toolkit.getToolkit;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author pwann
 */
public class AddInvForm extends javax.swing.JFrame {

    /**
     * Creates new form AddInvForm
     */
    private ConnectDB conn;
    private double Qty;
    private int ID;
    private Vector Items = new Vector();
    private Vector Items2 = new Vector();
    private int partType;
    DefaultComboBoxModel model2;
    DefaultComboBoxModel model;
    private InvoiceFrom IF;
    private Product proI; 
    String[] locName;
    int[] locID;

    public AddInvForm(InvoiceFrom IF) {
        initComponents();
        this.IF = IF;

        conn = new ConnectDB();
        conn.connect();
        ArrayList<Location> loclist = conn.QueryAllLocation();
        locName=new String[loclist.size()];
        locID=new int[loclist.size()];
        int i=0;
        Items.add(" ");
        for (Location loc : loclist) {
            locName[i]=loc.getLocationName();
            locID[i]=loc.getLocationID();
            Items.add(loc.getLocationName());
            i++;
        }
        model = new DefaultComboBoxModel(Items);
        /*ArrayList<ProductType> ptlist = conn.QueryProductType();

        for (ProductType pt : ptlist) {
            Items2.add(pt.getName());
        }
        model2 = new DefaultComboBoxModel(Items2);*/

        initData();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        conn.disconnect();

    }

    public void initData() {

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-LL-yyyy");
        String formattedString = localDate.format(formatter);

        cbLocation.setModel(model);
        //cbPartType.setModel(model2);
        //tfLotDate.setText(formattedString);

    }

    public void setProductToInv(Product pro) {
        this.proI = new Product();
        this.proI = pro;
        tfProductName.setText(this.proI.getName());
        tfPartID.setText(this.proI.getPart());
        jlProductID.setText(Integer.toString(this.proI.getID()));
        jlProductType.setText(this.proI.getTypeName());
        tfCost.setText(Double.toString(this.proI.getCost()));
        tfPrice2.setText(Double.toString(this.proI.getPrice()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField7 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jlProductName = new javax.swing.JLabel();
        jlPartID = new javax.swing.JLabel();
        tfProductName = new javax.swing.JLabel();
        tfPartID = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jlID = new javax.swing.JLabel();
        jlProductType = new javax.swing.JLabel();
        jlProductID = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tfQty = new javax.swing.JTextField();
        tfCost = new javax.swing.JTextField();
        tfCost2 = new javax.swing.JTextField();
        cbLocation = new javax.swing.JComboBox<>();
        btnAddInv = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        tfPrice2 = new javax.swing.JTextField();

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel16.setText("jLabel16");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("เพิ่มสินค้าในคลัง");

        jlProductName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlProductName.setText("ชื่อสินค้า:");

        jlPartID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlPartID.setText("Part:");

        tfProductName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfProductName.setForeground(new java.awt.Color(0, 153, 51));

        tfPartID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfPartID.setForeground(new java.awt.Color(0, 153, 51));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setText("ประเภทสินค้า:");

        jlID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlID.setText("รหัสสินค้า:");

        jlProductType.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jlProductID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlProductName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlPartID, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfPartID, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jlID, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlProductID, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                            .addComponent(jlProductType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfProductName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlProductName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlPartID, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(tfPartID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(3, 3, 3)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jlProductType, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlID, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                            .addComponent(jlProductID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel2.setMaximumSize(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("จำนวน");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("ต้นทุนจริง/หน่วย");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("ต้นทุน/หน่วย");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("ราคาขาย/หน่วย");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("สถานที่เก็บ");

        tfQty.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfQty.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfQty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfQtyKeyTyped(evt);
            }
        });

        tfCost.setEditable(false);
        tfCost.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfCost.setForeground(new java.awt.Color(204, 0, 0));
        tfCost.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        tfCost2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfCost2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfCost2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCost2ActionPerformed(evt);
            }
        });
        tfCost2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfCost2KeyTyped(evt);
            }
        });

        cbLocation.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbLocation.setMaximumRowCount(20);

        btnAddInv.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnAddInv.setForeground(new java.awt.Color(0, 153, 0));
        btnAddInv.setText("เพิ่มสินค้า");
        btnAddInv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddInvActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(204, 0, 0));
        btnCancel.setText("ยกเลิก");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        tfPrice2.setEditable(false);
        tfPrice2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfPrice2.setForeground(new java.awt.Color(204, 0, 0));
        tfPrice2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfCost2)
                            .addComponent(tfQty)
                            .addComponent(cbLocation, 0, 236, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfPrice2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(33, 33, 33)
                                .addComponent(tfCost, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addComponent(btnAddInv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfQty, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(tfCost, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfCost2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(tfPrice2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbLocation)
                    .addComponent(jLabel8))
                .addGap(88, 88, 88)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddInv)
                    .addComponent(btnCancel))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        /* MainForm mf = new MainForm();
        mf.setLocationRelativeTo(null);
        mf.setVisible(true);*/
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnAddInvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInvActionPerformed
        // TODO add your handling code here:
        if (tfProductName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "กรุณาเลือกสินค้าที่ต้องการเพิ่มเข้าคลังสินค้า");
            return;
        }
        if (tfQty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "กรุณาใส่จำนวนสินค้า");
            return;
        }
        if (cbLocation.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาเลือกสถานที่เก็บสินค้า");
            return;
        }
        /*  if (tfSupplierName.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาใส่ชื่อร้านค้า");
            return;
        }*/
        if (tfCost2.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาใส่ราคาต้นทุนจริง");
            return;
        }
        if (tfCost.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาใส่ราคาต้นทุนประมาณการ");
            return;
        }
        if (tfPrice2.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาใส่ราคาขาย");
            return;
        }

        /* if (tfInvoiceID.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาใส่รหัสใบกำกับ/ใบส่งของ");
            return;
        }*/
        int index=0;
        String SelectedLocName=cbLocation.getSelectedItem().toString();
        for(String s:locName){
            if(SelectedLocName.equals(s)){
                break;
            }
            index++;
        }
        int locID=this.locID[index];
        
        this.proI.setLocationID(locID);
        this.proI.setLocation(SelectedLocName);
        this.proI.setQty(Integer.parseInt(tfQty.getText()));
        this.proI.setCost2(Double.parseDouble(tfCost2.getText()));
        
       
        this.proI.calAmount(0, Integer.parseInt(tfQty.getText()), Double.parseDouble(tfCost2.getText()));

        ConnectDB conn = new ConnectDB();

        conn.connect();

        conn.insertTemp(this.proI);

        this.IF.refreshTableData();
        this.IF.getDataFromAddInv(this.proI);

        this.setVisible(false);
        //tfBarcode.setText("");
        //tfSupplierName.setText("");
        tfCost.setText("");
        tfCost2.setText("");
      
        //  tfInvoiceID.setText("");
        tfQty.setText("");
        tfPrice2.setText("");
        
        initData();


    }//GEN-LAST:event_btnAddInvActionPerformed

    private void tfQtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfQtyKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();

        }
    }//GEN-LAST:event_tfQtyKeyTyped

    private void tfCost2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCost2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCost2ActionPerformed

    private void tfCost2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCost2KeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();

        }
    }//GEN-LAST:event_tfCost2KeyTyped

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
            java.util.logging.Logger.getLogger(AddInvForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddInvForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddInvForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddInvForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddInvForm(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddInv;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cbLocation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel jlID;
    private javax.swing.JLabel jlPartID;
    private javax.swing.JLabel jlProductID;
    private javax.swing.JLabel jlProductName;
    private javax.swing.JLabel jlProductType;
    private javax.swing.JTextField tfCost;
    private javax.swing.JTextField tfCost2;
    private javax.swing.JLabel tfPartID;
    private javax.swing.JTextField tfPrice2;
    private javax.swing.JLabel tfProductName;
    private javax.swing.JTextField tfQty;
    // End of variables declaration//GEN-END:variables
}