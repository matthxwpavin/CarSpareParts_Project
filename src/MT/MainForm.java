/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pwann
 */
public class MainForm extends javax.swing.JFrame {

    private String BC = "";
    private PartListTableModel model;
    private ProductListTableModel model2;
    private ConnectDB conn;
    private Font f = new Font("Tahoma", Font.BOLD, 20);
    private int choice;
    private int subchoice;

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        makeFrameFullSize();
        JTableHeader header = mainTable.getTableHeader();
        header.setFont(f);
        btnAdd.setVisible(false);
        btnEdit.setVisible(false);
        btnFind.setVisible(false);
        jUser.setText(UsersLogin.User);

        ListSelectionModel cellSelectionModel = mainTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //System.out.println(UsersLogin.User);
    }

    private void makeFrameFullSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // System.out.println(screenSize);
        this.setSize(screenSize.width, screenSize.height);
    }

    public void refreshProductTable() {
        conn = new ConnectDB();
        conn.connect();
        ArrayList<Product> productList = conn.QueryAllProduct();

        ArrayList<Product> productListToSet = new ArrayList<>();

        if (productList.size() != 0) {
            model2 = new ProductListTableModel();
            Product pro = new Product();
            pro = productList.get(0);
            String carGenUse = productList.get(0).getCarGenName();
            String carBrandUse = productList.get(0).getCarBrandName();
            String Engine = productList.get(0).getEngine();

            for (int i = 0; i < productList.size() - 1; i++) {

                if (productList.get(i).getID() == productList.get(i + 1).getID()) {

                    carGenUse += "," + productList.get(i + 1).getCarGenName();
                    Engine += "," + productList.get(i + 1).getEngine();
                    if (productList.get(i + 1).getCarBrandName().equals(carBrandUse)) {
                    } else {
                        carBrandUse += "," + productList.get(i + 1).getCarBrandName();
                    }

                    pro.setCarGenName(carGenUse);
                    pro.setCarBrandName(carBrandUse);
                    pro.setEngine(Engine);

                } else {

                    productListToSet.add(pro);
                    pro = productList.get(i + 1);
                    carGenUse = pro.getCarGenName();
                    carBrandUse = pro.getCarBrandName();
                    Engine = pro.getEngine();

                }

            }

            productListToSet.add(pro);
            //System.out.println(productListToSet.size());

            model2.addAllData(productListToSet);

            mainTable.removeAll();
            mainTable.setModel(model2);
            btnFind.setVisible(true);
            btnFind.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "ยังไม่มีรายการสินค้า");
            model2 = new ProductListTableModel();
            mainTable.setModel(model2);
            btnFind.setVisible(true);
            btnFind.setEnabled(false);
        }

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 0, 153));
        btnAdd.setText("เพิ่มสินค้าใหม่");
        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 0, 153));
        btnEdit.setText("แก้ไขรายการสินค้า");
        btnAdd.setVisible(true);
        btnEdit.setVisible(true);
        btnEdit.setEnabled(false);
        choice = 1;

        conn.disconnect();

        mainTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public void refreshInvPartTable() {
        conn = new ConnectDB();
        conn.connect();
        ArrayList<Product> productList = conn.QueryInv();
        if (productList.size() != 0) {
            model = new PartListTableModel();

            model.addAllData(productList);
            //jTableProduct.removeAll();
            mainTable.setModel(model);

        } else {

            JOptionPane.showMessageDialog(null, "ไม่มีอะไหล่คงเหลือในคลังสินค้า");
            model = new PartListTableModel();
            mainTable.setModel(model);

        }

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 0, 153));
        btnAdd.setText("เพิ่มสินค้าเข้าคลัง");
        btnAdd.setVisible(true);
        btnEdit.setVisible(false);
        btnFind.setVisible(false);
        choice = 2;
        conn.disconnect();
        mainTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    public void setTableByFinding(ProductListTableModel prodModel) {
        mainTable.setModel(new DefaultTableModel());
        mainTable.setModel(prodModel);

    }

    public void refreshSupplyTable() {
        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 0, 153));
        btnAdd.setText("เพิ่มรายการผู้ผลิต");
        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 0, 153));
        btnEdit.setText("แก้ไขรายการผู้ผลิต");
        btnAdd.setVisible(true);
        btnEdit.setVisible(true);
        btnEdit.setEnabled(false);
        choice = 3;
        btnFind.setVisible(false);
        ArrayList<Supply> sp = new ArrayList<>();
        SupplyModel sm = new SupplyModel();
        ConnectDB conn = new ConnectDB();
        conn.connect();
        sp = conn.QueryAllSupplierShowTable();
        if (sp.size() == 0) {
            JOptionPane.showMessageDialog(null, "ยังไม่มรายการผู้ผลิต");
            mainTable.setModel(new DefaultTableModel());
            return;
        }
        sm.addAllData(sp);
        mainTable.setModel(sm);
        TableColumnModel columnModel = mainTable.getColumnModel();
        mainTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        columnModel.getColumn(0).setPreferredWidth(90);
        columnModel.getColumn(1).setPreferredWidth(451);
        columnModel.getColumn(2).setPreferredWidth(451);
        columnModel.getColumn(3).setPreferredWidth(451);
        columnModel.getColumn(4).setPreferredWidth(451);

        conn.disconnect();

    }

    public void editSupp() {
        int selectedRow = mainTable.getSelectedRow();
        int columnCount = mainTable.getColumnCount();
        int col = 0;
        Supply sp = new Supply();
        String[] str = new String[columnCount];

        int id = (int) mainTable.getValueAt(selectedRow, 0);

        for (int i = 0; i < columnCount - 1; i++) {
            col += 1;
            String value = (String) mainTable.getValueAt(selectedRow, col);
            str[i] = value;
        }

        // System.out.println(str);
        String[] split = str[1].split("อำ");
        sp.setID(id);
        sp.setName(str[0]);
        sp.setAddress(split[0]);
        sp.setAddress2("อำ" + split[1]);
        sp.setTEL(str[2]);
        sp.setTAX(str[3]);

        SupplierFrom sf = new SupplierFrom(this, subchoice, sp);

    }


    /* private void initTableList() {
        // inputLotjTextField.setDocument(new JTextFieldLimit(50, true));
        model = new PartListTableModel();
        jTableProduct.setModel(model);
        //jTableProduct.setDefaultRenderer(Color.class, new ColorTableModel(true));
        jTableProduct.getColumnModel().getColumn(0).setPreferredWidth(27);
        jTableProduct.getColumnModel().getColumn(4).setPreferredWidth(50);
    }*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainTable =  new javax.swing.JTable();
        btnProductShow = new javax.swing.JButton();
        btnInvShow = new javax.swing.JButton();
        btnSaleShow = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnSupply = new javax.swing.JButton();
        btnCarDetail = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        btnProductBrand = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jUser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        mainTable.setBackground(new java.awt.Color(0, 0, 0));
        mainTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 51), 2));
        mainTable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        mainTable.setForeground(new java.awt.Color(0, 255, 204));
        mainTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        mainTable.setRowHeight(30);
        mainTable.setSelectionBackground(new java.awt.Color(255, 255, 102));
        mainTable.setSelectionForeground(new java.awt.Color(204, 0, 0));
        mainTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mainTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(mainTable);

        btnProductShow.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnProductShow.setForeground(new java.awt.Color(0, 0, 204));
        btnProductShow.setText("ข้อมูลสินค้า");
        btnProductShow.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.red, java.awt.Color.white, java.awt.Color.magenta, java.awt.Color.lightGray));
        btnProductShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductShowActionPerformed(evt);
            }
        });

        btnInvShow.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnInvShow.setForeground(new java.awt.Color(255, 102, 0));
        btnInvShow.setText("คลังสินค้า");
        btnInvShow.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.red, java.awt.Color.white, java.awt.Color.magenta, java.awt.Color.lightGray));
        btnInvShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInvShowActionPerformed(evt);
            }
        });

        btnSaleShow.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSaleShow.setForeground(new java.awt.Color(51, 204, 0));
        btnSaleShow.setText("การขาย");
        btnSaleShow.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.red, java.awt.Color.white, java.awt.Color.magenta, java.awt.Color.lightGray));
        btnSaleShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaleShowActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 0, 153));
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnSupply.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnSupply.setForeground(new java.awt.Color(0, 102, 204));
        btnSupply.setText("รายการผู้ผลิต");
        btnSupply.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.red, java.awt.Color.white, java.awt.Color.magenta, java.awt.Color.lightGray));
        btnSupply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSupplyActionPerformed(evt);
            }
        });

        btnCarDetail.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCarDetail.setForeground(new java.awt.Color(204, 0, 204));
        btnCarDetail.setText("รถยนต์");
        btnCarDetail.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.red, java.awt.Color.white, java.awt.Color.magenta, java.awt.Color.lightGray));
        btnCarDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarDetailActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(0, 0, 153));
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 51, 0));
        jButton1.setText("ประเภทอะไหล่");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.red, java.awt.Color.white, java.awt.Color.magenta, java.awt.Color.lightGray));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnFind.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnFind.setForeground(new java.awt.Color(204, 0, 204));
        btnFind.setText("ค้นหาสินค้า");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnProductBrand.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnProductBrand.setForeground(new java.awt.Color(0, 153, 153));
        btnProductBrand.setText("ยี่ห้อสินค้า");
        btnProductBrand.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.red, java.awt.Color.white, java.awt.Color.magenta, new java.awt.Color(204, 204, 255)));
        btnProductBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductBrandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnProductShow, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSupply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnInvShow, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnSaleShow, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 424, Short.MAX_VALUE)
                        .addComponent(btnProductBrand, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCarDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnFind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEdit)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnProductShow, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInvShow, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSaleShow, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSupply, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnCarDetail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProductBrand, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
                    .addComponent(btnFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(198, 198, 198))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Car Spare Parts Menu");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204), 2));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("User:");

        jUser.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jUser.setForeground(new java.awt.Color(0, 204, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jUser, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jUser, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 772, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSupplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSupplyActionPerformed
        // TODO add your handling code here:
        refreshSupplyTable();
    }//GEN-LAST:event_btnSupplyActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if (choice == 1) {
            AddProductForm ad = new AddProductForm(this, null);
            ad.setLocationRelativeTo(null);
            ad.setVisible(true);
            //this.setVisible(false);

        } else if (choice == 2) {
            InvoiceFrom iv = new InvoiceFrom(this);
            iv.setLocationRelativeTo(null);
            iv.setVisible(true);
            //this.setVisible(false);

        } else if (choice == 3) {
            subchoice = 1;
            SupplierFrom sp = new SupplierFrom(this, subchoice, null);
            sp.setLocationRelativeTo(null);
            sp.setVisible(true);
            //this.setVisible(false);

        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnInvShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInvShowActionPerformed
        // TODO add your handling code here:
        refreshInvPartTable();
    }//GEN-LAST:event_btnInvShowActionPerformed

    private void btnProductShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductShowActionPerformed
        // TODO add your handling code here:
        refreshProductTable();

    }//GEN-LAST:event_btnProductShowActionPerformed

    private void mainTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainTableMouseClicked

        btnEdit.setEnabled(true);


    }//GEN-LAST:event_mainTableMouseClicked

    private void btnCarDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarDetailActionPerformed
        // TODO add your handling code here:
        CarCreate cc = new CarCreate();

    }//GEN-LAST:event_btnCarDetailActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        subchoice = 2;

        if (choice == 1) {
            String[] buttons = {"เพิ่ม/ลบรถยนต์", "แก้ไขสินค้า", "ยกเลิก"};
            int returnValue = JOptionPane.showOptionDialog(this, "ท่านต้องการ..", "สินค้า",
                    JOptionPane.INFORMATION_MESSAGE, 0, null, buttons, buttons[0]);

            if (returnValue == 0) {

                Product prod = new Product();
                prod = getProductFromTable();

                addCarToProduct acd = new addCarToProduct(prod, this);

            }
            if (returnValue == 1) {
                Product prod = new Product();
                prod = getProductFromTable();
                AddProductForm apd = new AddProductForm(this, prod);
            }

        } else if (choice == 2) {

        } else if (choice == 3) {

            editSupp();

        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        PartType pt = new PartType();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        // TODO add your handling code here:
        FindProductForm finding = new FindProductForm(this);
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnProductBrandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductBrandActionPerformed
        // TODO add your handling code here:
        new ProductBrandForm();
    }//GEN-LAST:event_btnProductBrandActionPerformed

    private void btnSaleShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaleShowActionPerformed

        new SellInvoice();
    }//GEN-LAST:event_btnSaleShowActionPerformed

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
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnCarDetail;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnInvShow;
    private javax.swing.JButton btnProductBrand;
    private javax.swing.JButton btnProductShow;
    private javax.swing.JButton btnSaleShow;
    private javax.swing.JButton btnSupply;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jUser;
    private javax.swing.JTable mainTable;
    // End of variables declaration//GEN-END:variables

    private Product getProductFromTable() {
        int selectedRow = mainTable.getSelectedRow();
        int columnCount = mainTable.getColumnCount();

        int id = 0;
        double cost = 0;
        double price = 0;
        String[] value = new String[columnCount - 3];
        int j = 0;
        for (int i = 0; i < columnCount; i++) {
            if (i == 0) {
                id = (int) mainTable.getValueAt(selectedRow, i);
            } else if (i == 10) {
                cost = (double) mainTable.getValueAt(selectedRow, i);
            } else if (i == 11) {
                price = (double) mainTable.getValueAt(selectedRow, i);
            } else {
                value[j] = (String) mainTable.getValueAt(selectedRow, i);
                j++;
            }

        }

        conn.connect();
        ArrayList<ProductType> pt = conn.QueryAllPartType();
        String[] typeNameList = new String[pt.size()];
        int[] typeIDList = new int[pt.size()];
        int i = 0;
        for (ProductType t : pt) {
            typeNameList[i] = t.getName();
            typeIDList[i] = t.getID();
            i++;
        }
        int index = 0;
        for (String s : typeNameList) {
            if (value[1].equals(s)) {
                break;
            }
            index++;
        }
        int typeID = typeIDList[index];

        Product pro = new Product();
        pro.setID(id);
        pro.setCost(cost);
        pro.setPrice(price);
        pro.setGroupName(value[0]);
        pro.setTypeName(value[1]);
        pro.setTypeID(typeID);
        pro.setName(value[2]);
        pro.setInitials(value[3]);
        pro.setBrand(value[4]);
        pro.setPart(value[5]);
        pro.setPV(value[6]);
        pro.setDes(value[7]);
        pro.setCarBrandName(value[8]);
        pro.setCarGenName(value[9]);
        pro.setEngine(value[10]);

        return pro;
    }
}
