/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import com.sun.jndi.ldap.Connection;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;
import javafx.scene.text.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author pwann
 */
public class AddProductForm extends javax.swing.JFrame {

    private ConnectDB conn;
    MainForm mf;
    String[] groupName;
    String[] carBrandName;
    int[] carBrandID;
    int[] groupID;
    ArrayList<ProductType> ptlist;
    ArrayList<CarClass> carlist;
    ArrayList<String> BrandList;
    Product Prod;
    private java.awt.Font fh = new java.awt.Font("Tahoma", java.awt.Font.BOLD, 36);

    DefaultComboBoxModel cbGroupTypeModel;
    DefaultComboBoxModel cbCarBrandmodel;
    DefaultComboBoxModel cbCarGenModel;
    DefaultComboBoxModel cbPartTypeModel;
    DefaultComboBoxModel cbBrandProductModel;
    Vector GroupTypeItems;
    Vector carBrandItems;
    Vector partTypeItems;
    Vector carGenItems;
    Vector BrandProductItems;

    /**
     * Creates new form AddProductForm
     */
    public AddProductForm(MainForm mf, Product prod) {
        initComponents();
        this.mf = mf;
        this.Prod = prod;
        conn = new ConnectDB();
        conn.connect();

        initData();
        conn.disconnect();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public void initData() {
        if (this.Prod == null) {
            BrandProductItems = new Vector();
            BrandList = conn.QueryAllProductBrand();
            int i = 0;
            BrandProductItems.add(" ");
            for (String brand : BrandList) {
                BrandProductItems.add(brand);
            }
            cbBrandProductModel = new DefaultComboBoxModel(BrandProductItems);
            cbBrandProduct.setModel(cbBrandProductModel);
            GroupTypeItems = new Vector();
            ptlist = conn.QueryProductType();
            groupName = new String[ptlist.size()];
            groupID = new int[ptlist.size()];
            GroupTypeItems.add(" ");
            i = 0;
            for (ProductType pt : ptlist) {
                groupID[i] = pt.getID();
                groupName[i] = pt.getName();
                GroupTypeItems.add(pt.getName());
                i++;
            }
            cbGroupTypeModel = new DefaultComboBoxModel(GroupTypeItems);
            cbGroupType.setModel(cbGroupTypeModel);

            carBrandItems = new Vector();
            carlist = conn.QueryAllCarBrand();
            carBrandName = new String[carlist.size()];
            carBrandID = new int[carlist.size()];
            carBrandItems.add(" ");

            i = 0;
            for (CarClass cc : carlist) {
                carBrandID[i] = cc.getBrandID();
                carBrandName[i] = cc.getBrand();
                carBrandItems.add(cc.getBrand());
                i++;
            }

            cbCarBrandmodel = new DefaultComboBoxModel(carBrandItems);

            cbCarBrand.setModel(cbCarBrandmodel);
            int maxID = conn.QueryProductMaxIndex();
            tfPartID.setText(Integer.toString(maxID + 1));
            tfBarcode1.setText("");
            tfBarcode2.setText("");
            tfBarcode3.setText("");
            tfBarcode4.setText("");
            tfPartPrivate.setText("");
            tfProductPart.setText("");
            tfInitials.setText("");
            tfProductName.setText("");
            tfPrice.setText("");
            tfCost.setText("");
            taProductDetail.setText("");

            jlHead.setText("เพิ่มสินค้าใหม่");
            jlHead.setFont(fh);
            jlHead.setForeground(Color.BLUE);

            cbCarGen.setModel(new DefaultComboBoxModel<>());
            cbPartType.setModel(new DefaultComboBoxModel<>());
        } else {
            BrandProductItems = new Vector();
            BrandList = conn.QueryAllProductBrand();
            BrandProductItems.add(" ");
            for (String brand : BrandList) {
                BrandProductItems.add(brand);
            }
            cbBrandProductModel = new DefaultComboBoxModel(BrandProductItems);
            cbBrandProduct.setModel(cbBrandProductModel);
            GroupTypeItems = new Vector();
            ptlist = conn.QueryProductType();
            Product pro = conn.QueryProductByPK(this.Prod.getID());
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
            cbGroupType.setModel(cbGroupTypeModel);
            i = 0;
            tfBarcode1.setText(pro.getBarcode1());
            tfBarcode2.setText(pro.getBarcode2());
            tfBarcode3.setText(pro.getBarcode3());
            tfBarcode4.setText(pro.getBarcode4());
            tfPartID.setText(Integer.toString(pro.getID()));
            tfPartPrivate.setText(pro.getPV());
            tfProductPart.setText(pro.getPart());
            tfInitials.setText(pro.getInitials());

            tfProductName.setText(pro.getName());
            tfPrice.setText(Double.toString(pro.getPrice()));
            tfCost.setText(Double.toString(pro.getCost()));
            taProductDetail.setText(pro.getDes());
            String groupType = pro.getGroupName();
            String typeName = pro.getTypeName();
            cbGroupType.setSelectedItem(groupType);
            cbBrandProduct.setSelectedItem(pro.getBrand());
            if (cbGroupType.getSelectedIndex() == 0) {
                cbPartType.setModel(new DefaultComboBoxModel<>());

            } else if (cbGroupType.getSelectedItem().toString().equals("อื่นๆ")) {
                cbPartType.setModel(new DefaultComboBoxModel<>());

            } else {
                String name = cbGroupType.getSelectedItem().toString();

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

                partTypeItems = new Vector();
                partTypeItems.add(" ");
                for (ProductType pt : ptlist) {

                    partTypeItems.add(pt.getName());
                }
                cbPartTypeModel = new DefaultComboBoxModel(partTypeItems);
                cbPartType.setModel(cbPartTypeModel);
                cbPartType.setSelectedItem(typeName);

            }

            cbCarBrand.setVisible(false);
            cbCarGen.setVisible(false);
            jlCarBrand.setVisible(false);
            jlCarGen.setVisible(false);

            jlHead.setText("แก้ไขสินค้า");
            jlHead.setFont(fh);
            jlHead.setForeground(Color.BLUE);

            btnAdd.setText("เรียบร้อย");
            btnAdd.setForeground(Color.GREEN);
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
        jlHead = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tfPartPrivate = new javax.swing.JTextField();
        tfProductName = new javax.swing.JTextField();
        tfProductPart = new javax.swing.JTextField();
        taProductDetail = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        tfBarcode1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tfPrice = new javax.swing.JTextField();
        tfCost = new javax.swing.JTextField();
        tfBarcode3 = new javax.swing.JTextField();
        tfBarcode2 = new javax.swing.JTextField();
        tfBarcode4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cbGroupType = new javax.swing.JComboBox<>();
        jlGroupType = new javax.swing.JLabel();
        cbPartType = new javax.swing.JComboBox<>();
        cbCarBrand = new javax.swing.JComboBox<>();
        cbCarGen = new javax.swing.JComboBox<>();
        jlType = new javax.swing.JLabel();
        jlCarBrand = new javax.swing.JLabel();
        jlCarGen = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        tfInitials = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tfPartID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbBrandProduct = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlHead.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jlHead.setForeground(new java.awt.Color(0, 0, 255));
        jlHead.setText("เพิ่มสินค้าใหม่");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("ฺBarcode1:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("Part");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("ชื่อสินค้า");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("รายละเอียดสินค้า");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("PartPrivate");

        tfPartPrivate.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfPartPrivate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        tfProductName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        tfProductPart.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfProductPart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        taProductDetail.setColumns(20);
        taProductDetail.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        taProductDetail.setRows(5);

        btnAdd.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 255, 0));
        btnAdd.setText("เพิ่ม");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 0, 0));
        btnCancel.setText("ยกเลิก");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        tfBarcode1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfBarcode1.setForeground(new java.awt.Color(255, 51, 0));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("ราคาทุนประมาณการ/หน่วย");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setText("ราคาขาย/หน่วย");

        tfPrice.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfPrice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfPriceKeyTyped(evt);
            }
        });

        tfCost.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfCost.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCostActionPerformed(evt);
            }
        });
        tfCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfCostKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfCostKeyTyped(evt);
            }
        });

        tfBarcode3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfBarcode3.setForeground(new java.awt.Color(255, 51, 0));

        tfBarcode2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfBarcode2.setForeground(new java.awt.Color(255, 51, 0));

        tfBarcode4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfBarcode4.setForeground(new java.awt.Color(255, 51, 0));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("ฺBarcode2:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setText("ฺBarcode4:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setText("ฺBarcode3:");

        cbGroupType.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbGroupType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbGroupTypeActionPerformed(evt);
            }
        });

        jlGroupType.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlGroupType.setText("หมวดสินค้า");

        cbPartType.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbPartType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPartTypeActionPerformed(evt);
            }
        });

        cbCarBrand.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbCarBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCarBrandActionPerformed(evt);
            }
        });

        cbCarGen.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbCarGen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCarGenActionPerformed(evt);
            }
        });

        jlType.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlType.setText("ประเภทสินค้า");

        jlCarBrand.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlCarBrand.setText("ใช้กับรถยี่ห้อ");

        jlCarGen.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlCarGen.setText("ใช้กับรถรุ่น");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlGroupType)
                            .addComponent(jlType))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbGroupType, 0, 356, Short.MAX_VALUE)
                            .addComponent(cbPartType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlCarBrand)
                            .addComponent(jlCarGen))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbCarBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbCarGen, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbGroupType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlGroupType))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPartType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlType))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCarBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCarBrand))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCarGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlCarGen))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("ยี่ห้อ");

        tfInitials.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfInitials.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setText("ชื่อย่อ");

        tfPartID.setEditable(false);
        tfPartID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tfPartID.setForeground(new java.awt.Color(204, 0, 0));
        tfPartID.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("รหัสสินค้า");

        cbBrandProduct.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(tfPartID, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbBrandProduct, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tfInitials, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPartID)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfInitials, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbBrandProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel12))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfBarcode4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfBarcode3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(jLabel2))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfBarcode2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfBarcode1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addGap(114, 114, 114)
                                    .addComponent(tfPrice, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfCost))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(tfProductPart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                                        .addComponent(tfProductName, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tfPartPrivate)))))
                        .addGap(254, 254, 254)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlHead, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(taProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 671, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlHead, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfProductPart, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfPartPrivate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(tfCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfBarcode1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfBarcode2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfBarcode3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfBarcode4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(taProductDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:

        if (tfProductName.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาใส่ชื่อสินค้า");
            return;
        }
        if (cbGroupType.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาเลือกหมวดหมู่สินค้า");
            return;
        }
        if (cbCarBrand.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาเลือกยี่ห้อรถ");
            return;
        }
        if (tfCost.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "กรุณาใส่ราคาทุนประมาณการ/หน่วย");
            return;
        }
        if (tfPrice.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "กรุณาใส่ราคาขาย/หน่วย");
            return;
        }
        if (cbBrandProduct.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาเลือกยี่ห้อสินค้า");
            return;
        }
        conn = new ConnectDB();
        conn.connect();

        ArrayList<ProductType> typeList = new ArrayList<>();
        typeList = conn.QueryAllPartType();

        String[] typeName;
        int[] listtypeID;

        typeName = new String[typeList.size()];
        listtypeID = new int[typeList.size()];

        int i = 0;
        for (ProductType pt : typeList) {
            typeName[i] = pt.getName();
            listtypeID[i] = pt.getID();

            i++;

        }

        Product p = new Product();
        p.setName(tfProductName.getText());
        p.setID(Integer.parseInt(tfPartID.getText()));
        p.setInitials(tfInitials.getText());
        p.setPart(tfProductPart.getText());
        p.setPV(tfPartPrivate.getText());
        p.setBrand(cbBrandProduct.getSelectedItem().toString());
        p.setDes(taProductDetail.getText());
        p.setCost(Double.parseDouble(tfCost.getText()));
        p.setPrice(Double.parseDouble(tfPrice.getText()));
        p.setBarcode1(tfBarcode1.getText());
        p.setBarcode2(tfBarcode2.getText());
        p.setBarcode3(tfBarcode3.getText());
        p.setBarcode4(tfBarcode4.getText());

        if (cbGroupType.getSelectedItem().toString().equals("อื่นๆ")) {
            p.setTypeID(0);
        } else {
            if (cbPartType.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "กรุณาเลือกประเภทย่อย");
                return;
            }
            int index = 0;

            for (String s : typeName) {

                if (cbPartType.getSelectedItem().toString().equals(s)) {
                    break;
                }
                index++;
            }

            int typeID = listtypeID[index];

            p.setTypeID(typeID);

        }
        if (this.Prod == null) {
            ArrayList<CarClass> genList = new ArrayList<>();
            genList = conn.QueryAllCarGen();
            String[] genName;
            int[] listGenID;
            genName = new String[genList.size()];
            listGenID = new int[genList.size()];
            i = 0;
            for (CarClass cc : genList) {
                genName[i] = cc.getGen();
                listGenID[i] = cc.getGenID();

                i++;

            }

            if (cbCarBrand.getSelectedItem().toString().equals("ใช้ได้ทุกยี่ห้อ")) {
                p.setCarGenID(0);

            } else {
                if (cbCarGen.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "กรุณาเลือกรุ่นรถ");
                    return;
                }
                int index = 0;

                for (String s : genName) {

                    if (cbCarGen.getSelectedItem().toString().equals(s)) {
                        break;
                    }
                    index++;
                }

                int genID = listGenID[index];

                p.setCarGenID(genID);

            }
            conn.insertProduct(p);
            conn.insertMappingProduct(p);
        } else {
            conn.updateMappingProductbyProdID(p);
            conn.updateProductByProdID(p);
            this.setVisible(false);

        }
        initData();

        conn.disconnect();

        this.mf.refreshProductTable();


        /* MainForm mf = new MainForm();
        mf.setLocationRelativeTo(null);
        mf.setVisible(true);
        this.setVisible(false);*/

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        /*MainForm mf = new MainForm();
        mf.setLocationRelativeTo(null);
        mf.setVisible(true);*/
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void cbPartTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPartTypeActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_cbPartTypeActionPerformed

    private void tfCostKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCostKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCostKeyPressed

    private void tfCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCostKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();

        }
    }//GEN-LAST:event_tfCostKeyTyped

    private void tfPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPriceKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();

        }
    }//GEN-LAST:event_tfPriceKeyTyped

    private void cbGroupTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbGroupTypeActionPerformed
        // TODO add your handling code here:
        if (cbGroupType.getSelectedIndex() == 0) {
            cbPartType.setModel(new DefaultComboBoxModel<>());

        } else if (cbGroupType.getSelectedItem().toString().equals("อื่นๆ")) {
            cbPartType.setModel(new DefaultComboBoxModel<>());

        } else {
            String name = cbGroupType.getSelectedItem().toString();

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

            partTypeItems = new Vector();
            partTypeItems.add(" ");
            for (ProductType pt : ptlist) {

                partTypeItems.add(pt.getName());
            }
            cbPartTypeModel = new DefaultComboBoxModel(partTypeItems);
            cbPartType.setModel(cbPartTypeModel);

        }

    }//GEN-LAST:event_cbGroupTypeActionPerformed

    private void cbCarGenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCarGenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCarGenActionPerformed

    private void cbCarBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCarBrandActionPerformed
        // TODO add your handling code here:
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

            carGenItems = new Vector();
            carGenItems.add(" ");
            for (CarClass cc : carlist) {

                carGenItems.add(cc.getGen());
            }
            cbCarGenModel = new DefaultComboBoxModel(carGenItems);
            cbCarGen.setModel(cbCarGenModel);
        }
    }//GEN-LAST:event_cbCarBrandActionPerformed

    private void tfCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfCostActionPerformed

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
            java.util.logging.Logger.getLogger(AddProductForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddProductForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddProductForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddProductForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddProductForm(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cbBrandProduct;
    private javax.swing.JComboBox<String> cbCarBrand;
    private javax.swing.JComboBox<String> cbCarGen;
    private javax.swing.JComboBox<String> cbGroupType;
    private javax.swing.JComboBox<String> cbPartType;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jlCarBrand;
    private javax.swing.JLabel jlCarGen;
    private javax.swing.JLabel jlGroupType;
    private javax.swing.JLabel jlHead;
    private javax.swing.JLabel jlType;
    private javax.swing.JTextArea taProductDetail;
    private javax.swing.JTextField tfBarcode1;
    private javax.swing.JTextField tfBarcode2;
    private javax.swing.JTextField tfBarcode3;
    private javax.swing.JTextField tfBarcode4;
    private javax.swing.JTextField tfCost;
    private javax.swing.JTextField tfInitials;
    private javax.swing.JTextField tfPartID;
    private javax.swing.JTextField tfPartPrivate;
    private javax.swing.JTextField tfPrice;
    private javax.swing.JTextField tfProductName;
    private javax.swing.JTextField tfProductPart;
    // End of variables declaration//GEN-END:variables

}
