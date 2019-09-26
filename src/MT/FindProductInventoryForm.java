/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author pwann
 */
public class FindProductInventoryForm extends javax.swing.JFrame {

    /**
     * Creates new form FindProductForm
     */
    ConnectDB conn = new ConnectDB();
    Vector brandItems = new Vector();
    Vector GroupTypeItems = new Vector();
    Vector partTypeItems = new Vector();
    Vector carBrandItems = new Vector();
    Vector carGenItems = new Vector();
    ArrayList<String> brandList;
    ArrayList<ProductType> ptlist;
    ArrayList<CarClass> carlist;
    String[] groupName;
    int[] groupID;
    String[] carBrandName;
    int[] carBrandID;
    DefaultComboBoxModel cbGroupTypeModel;
    DefaultComboBoxModel brandModel;
    DefaultComboBoxModel cbPartTypeModel;
    DefaultComboBoxModel cbCarBrandmodel;
    DefaultComboBoxModel cbCarGenModel;
    //MainForm mf;
    JFrame mf;
    private FindInvTableModel model;

    Product Prod = new Product();

    public FindProductInventoryForm(JFrame mf) {
        this.mf = mf;

        initComponents();
        initcbBrand();
        initcbGroupType();
        initCarBrand();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public void initcbBrand() {
        conn.connect();
        brandList = conn.QueryAllProductBrand();
        conn.disconnect();
        brandItems.add("");
        for (String s : brandList) {
            brandItems.add(s);
        }
        brandModel = new DefaultComboBoxModel(brandItems);
        cbBrand.setModel(brandModel);
    }

    public void initcbGroupType() {
        conn.connect();
        ptlist = conn.QueryProductType();
        conn.disconnect();
        groupName = new String[ptlist.size()];
        groupID = new int[ptlist.size()];
        GroupTypeItems.add(" ");
        int i = 0;
        for (ProductType pt : ptlist) {
            groupID[i] = pt.getID();
            groupName[i] = pt.getName();
            GroupTypeItems.add(pt.getName());
            i++;
        }
        cbGroupTypeModel = new DefaultComboBoxModel(GroupTypeItems);
        cbGroup.setModel(cbGroupTypeModel);
        GroupTypeItems = new Vector();
    }

    public void initCarBrand() {
        conn.connect();
        carlist = conn.QueryAllCarBrand();
        conn.disconnect();
        carBrandName = new String[carlist.size()];
        carBrandID = new int[carlist.size()];
        carBrandItems.add(" ");

        int i = 0;
        for (CarClass cc : carlist) {
            carBrandID[i] = cc.getBrandID();
            carBrandName[i] = cc.getBrand();
            carBrandItems.add(cc.getBrand());
            i++;
        }

        cbCarBrandmodel = new DefaultComboBoxModel(carBrandItems);

        cbCarBrand.setModel(cbCarBrandmodel);
        carBrandItems = new Vector();
    }

    public void cbGroupDataChange() {

        if (cbGroup.getSelectedIndex() == 0) {
            cbType.setModel(new DefaultComboBoxModel<>());

        } else if (cbGroup.getSelectedItem().toString().equals("อื่นๆ")) {
            cbType.setModel(new DefaultComboBoxModel<>());

        } else {
            String name = cbGroup.getSelectedItem().toString();

            int index = 0;

            for (String str : groupName) {
                if (name.equals(str)) {

                    break;

                }
                index++;

            }
            int partGroupID = groupID[index];

            conn.connect();

            ArrayList<ProductType> ptlist = conn.QueryPartTypeByPartGroupID(partGroupID);

            conn.disconnect();

            partTypeItems.add(" ");
            for (ProductType pt : ptlist) {

                partTypeItems.add(pt.getName());
            }
            cbPartTypeModel = new DefaultComboBoxModel(partTypeItems);
            cbType.setModel(cbPartTypeModel);
            partTypeItems = new Vector();
        }
    }

    public void cbCarBrandDataChange() {
        if (cbCarBrand.getSelectedIndex() == 0) {
            cbCarGen.setModel(new DefaultComboBoxModel<>());

        } else if (cbCarBrand.getSelectedItem().toString().equals("ใช้ได้ทุกยี่ห้อ")) {
            cbCarGen.setModel(new DefaultComboBoxModel<>());
        } else {
            String name = cbCarBrand.getSelectedItem().toString();

            int index = 0;

            for (String str : carBrandName) {
                if (name.equals(str)) {

                    break;

                }
                index++;
                // System.out.println(index);
            }
            int carBrandID = this.carBrandID[index];

            conn.connect();

            ArrayList<CarClass> carlist = conn.QueryCarGenByCarBrandID(carBrandID);
            conn.disconnect();

            carGenItems.add(" ");
            for (CarClass cc : carlist) {

                carGenItems.add(cc.getGen());
            }
            cbCarGenModel = new DefaultComboBoxModel(carGenItems);
            cbCarGen.setModel(cbCarGenModel);
            carGenItems = new Vector();
        }

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
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        หมวดหมู่ = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfPart = new javax.swing.JTextField();
        cbBrand = new javax.swing.JComboBox<>();
        cbType = new javax.swing.JComboBox<>();
        cbCarBrand = new javax.swing.JComboBox<>();
        cbCarGen = new javax.swing.JComboBox<>();
        btnFind = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        tfInitials = new javax.swing.JTextField();
        cbGroup = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 204, 204));
        jLabel2.setText("ค้นหาสินค้า");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("ชื่อย่อ:");
        jLabel1.setToolTipText("");

        หมวดหมู่.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        หมวดหมู่.setText("หมวดหมู่:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("ประเภทย่อย:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("ยี่ห้อรถยนต์:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("รุ่น:");

        tfPart.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        cbBrand.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        cbType.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        cbCarBrand.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbCarBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCarBrandActionPerformed(evt);
            }
        });

        cbCarGen.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        btnFind.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnFind.setForeground(new java.awt.Color(0, 153, 51));
        btnFind.setText("ค้นหา");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(204, 0, 51));
        btnCancel.setText("ยกเลิก");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        tfInitials.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        cbGroup.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGroupActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("เลขพาร์ท:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("ยี่ห้อ:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(หมวดหมู่)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfPart, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cbType, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cbBrand, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tfInitials)
                                        .addComponent(cbGroup, 0, 341, Short.MAX_VALUE))
                                    .addComponent(cbCarBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(cbCarGen, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnFind)
                                .addGap(8, 8, 8)
                                .addComponent(btnCancel)))
                        .addContainerGap(276, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfInitials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(หมวดหมู่))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCarBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCarGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnFind))
                .addContainerGap(31, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGroupActionPerformed
        // TODO add your handling code here:
        cbGroupDataChange();

    }//GEN-LAST:event_cbGroupActionPerformed

    private void cbCarBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCarBrandActionPerformed
        // TODO add your handling code here:
        cbCarBrandDataChange();
    }//GEN-LAST:event_cbCarBrandActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed

        String sql = "select p.productID,p.productName,p.productNameENG,p.initials,p.Part,p.PartPrivate,p.productBrand,p.productDes,p.costPerUnit,p.pricePerUnit,p.BarcodeName1,p.BarcodeName2,p.BarcodeName3,p.BarcodeName4,mpt.PartGroupName,mpt.`Name`,cg.carGenName,cg.CarBrandName"
                + " from product p  left join  mappingproduct mpp on p.productID=mpp.productID left join mappingparttype mpt on mpp.partTypeID=mpt.ID "
                + " left join cargen cg on mpp.carGenID=cg.carGenID where";
        if (!tfInitials.getText().isEmpty()) {
            String sqlWhereInitials = " p.initials like '%" + tfInitials.getText().toString() + "%' and";
            sql += sqlWhereInitials;
        }
        if (!tfPart.getText().isEmpty()) {
            String sqlWherePart = " p.Part like '%" + tfPart.getText().toString() + "%' and";
            sql += sqlWherePart;
        }
        if (cbBrand.getSelectedIndex() > 0) {
            String sqlWhereBrand = " p.productBrand like '" + cbBrand.getSelectedItem().toString() + "' and";
            sql += sqlWhereBrand;
        }
        if (cbGroup.getSelectedIndex() > 0) {
            String sqlWhereGroup = " mpt.PartGroupName like '" + cbGroup.getSelectedItem().toString() + "' and";
            sql += sqlWhereGroup;
        }
        if (cbType.getSelectedIndex() > 0) {
            String sqlWhereType = " mpt.Name like '" + cbType.getSelectedItem().toString() + "' and";
            sql += sqlWhereType;
        }
        if (cbCarBrand.getSelectedIndex() > 0) {
            String sqlWhereCarBrand = " cg.CarBrandName like '" + cbCarBrand.getSelectedItem().toString() + "' and";
            sql += sqlWhereCarBrand;
        }
        if (cbCarGen.getSelectedIndex() > 0) {
            String sqlWhereCarBrand = " cg.CarGenName like '" + cbCarGen.getSelectedItem().toString() + "' and";
            sql += sqlWhereCarBrand;
        }
        int sqlLenght = sql.length();
        int subLenght = sqlLenght - 4;
        sql = sql.substring(0, subLenght);

        conn.connect();
        ArrayList<Product> prodList = conn.QueryProductByCondition(sql);

        if (prodList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ไม่พบรายการสินค้า");
            return;

        }

        ArrayList<Product> productListToSet = new ArrayList<>();
        Product pro = new Product();

        pro = prodList.get(0);
        String carGenUse = prodList.get(0).getCarGenName();
        String carBrandUse = prodList.get(0).getCarBrandName();

        for (int i = 0; i < prodList.size() - 1; i++) {

            if (prodList.get(i).getID() == prodList.get(i + 1).getID()) {

                carGenUse += "," + prodList.get(i + 1).getCarGenName();
                if (prodList.get(i + 1).getCarBrandName().equals(carBrandUse)) {
                } else {
                    carBrandUse += "," + prodList.get(i + 1).getCarBrandName();
                }

                pro.setCarGenName(carGenUse);
                pro.setCarBrandName(carBrandUse);

            } else {

                productListToSet.add(pro);
                pro = prodList.get(i + 1);
                carGenUse = pro.getCarGenName();
                carBrandUse = pro.getCarBrandName();

            }

        }

        productListToSet.add(pro);
        ArrayList<Product> prodInv = new ArrayList<>();
        String sqlWhere = "";
        for (Product p : productListToSet) {
            sqlWhere += " pi.productID=" + p.getID() + " or";

        }
        int sqlLen = sqlWhere.length();
        int subLen = sqlLen - 3;
        sqlWhere = sqlWhere.substring(0, subLen);
        prodInv = conn.QueryProductInventoryByCondition(sqlWhere);
        conn.disconnect();
        if (prodInv.size() == 0) {
            JOptionPane.showMessageDialog(null, "ไม่พบสินค้าในคลัง");
            return;
        }

        model = new FindInvTableModel();

        model.addAllData(prodInv);
        this.setVisible(false);
        new ResultFinding(model, this.mf);


    }//GEN-LAST:event_btnFindActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        if (mf instanceof MainForm) {
            MainForm _mf = (MainForm) this.mf;
            _mf.refreshProductTable();

        }
        this.setVisible(false);

    }//GEN-LAST:event_btnCancelActionPerformed

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
            java.util.logging.Logger.getLogger(FindProductInventoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FindProductInventoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FindProductInventoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FindProductInventoryForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FindProductInventoryForm(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnFind;
    private javax.swing.JComboBox<String> cbBrand;
    private javax.swing.JComboBox<String> cbCarBrand;
    private javax.swing.JComboBox<String> cbCarGen;
    private javax.swing.JComboBox<String> cbGroup;
    private javax.swing.JComboBox<String> cbType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField tfInitials;
    private javax.swing.JTextField tfPart;
    private javax.swing.JLabel หมวดหมู่;
    // End of variables declaration//GEN-END:variables
}
