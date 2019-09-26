/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author pwann
 */
public class addCarToProduct extends javax.swing.JFrame {

    /**
     * Creates new form addCarToProduct
     *
     * @param prod
     */
    private ConnectDB conn;
    private Product pro;
    DefaultComboBoxModel cbCarBrandmodel;
    DefaultComboBoxModel cbCarGenModel;
    Vector carBrandItems;
    Vector carGenItems;
    private AddCarToProdModel model;
    ArrayList<Product> Prod;
    ArrayList<CarClass> carlist;
    String[] carBrandName;
    String[] carGenName;
    String[] Engine;
    String[] carBrandName2;
    int[] carBrandID;
    int[] carGenID;
    MainForm mf;
    private Font f = new Font("Tahoma", Font.BOLD, 20);

    public addCarToProduct(Product prod, MainForm mf) {
        initComponents();
        pro = new Product();
        pro = prod;
        this.mf = mf;
        initData();

    }

    public void initData() {

        jlID.setText(Integer.toString(pro.getID()));
        jlName.setText(pro.getName());
        jlInitials.setText(pro.getInitials());
        jlPV.setText(pro.getPV());
        jlPart.setText(pro.getPart());
        jlBrand.setText(pro.getBrand());
        jlDes.setText(pro.getDes());
        jlCost.setText(Double.toString(pro.getCost()));
        jlPrice.setText(Double.toString(pro.getPrice()));
        jlBarcode1.setText(pro.getBarcode1());
        jlBarcode2.setText(pro.getBarcode2());
        jlBarcode3.setText(pro.getBarcode3());
        jlBarcode4.setText(pro.getBarcode4());

        conn = new ConnectDB();
        conn.connect();

        Prod = conn.QueryMappingProductByProID(pro.getID());
        carGenID = new int[Prod.size()];
        carGenName = new String[Prod.size()];
        Engine=new String[Prod.size()];
        int i = 0;
        for (Product p : Prod) {
            carGenID[i] = p.getCarGenID();
            i++;
        }
        int totalGenID = carGenID.length;

        ArrayList<Product> ProductSetCarBrand = new ArrayList<>();
        carBrandName2 = new String[totalGenID];
        int j = 0;
        for (i = 0; i < totalGenID; i++) {
            ProductSetCarBrand = conn.QueryCarBrandCarGenByCarGenID(carGenID[i]);
            for (Product p : ProductSetCarBrand) {
                carBrandName2[j] = p.getCarBrandName();
                carGenName[j] = p.getCarGenName();
                Engine[j]=p.getEngine();

                j++;

            }
        }

        carBrandItems = new Vector();
        carlist = conn.QueryAllCarBrand();

        conn.disconnect();

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

        model = new AddCarToProdModel();

        model.addAllData(carBrandName2, carGenName,Engine);

        addCarTable.setModel(model);
        addCarTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JTableHeader header = addCarTable.getTableHeader();
        header.setFont(f);

        btnDel.setEnabled(false);
        cbCarGen.setModel(new DefaultComboBoxModel<>());

        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

    public int getGenIDbyGenName(String Name) {

        int index = 0;

        conn.connect();
        ArrayList<CarClass> carGenList = conn.QueryAllCarGen();

        String[] genName = new String[carGenList.size()];
        int[] listGenID = new int[carGenList.size()];
        int i = 0;
        for (CarClass c : carGenList) {
            genName[i] = c.getGen();
            listGenID[i] = c.getGenID();

            i++;
        }
        for (String s : genName) {

            if (Name.equals(s)) {
                break;
            }
            index++;
        }
        conn.disconnect();
        return listGenID[index];
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
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jlID = new javax.swing.JLabel();
        jlName = new javax.swing.JLabel();
        jlInitials = new javax.swing.JLabel();
        jlPV = new javax.swing.JLabel();
        jlPart = new javax.swing.JLabel();
        jlBrand = new javax.swing.JLabel();
        jlDes = new javax.swing.JLabel();
        jlCost = new javax.swing.JLabel();
        jlPrice = new javax.swing.JLabel();
        jlBarcode1 = new javax.swing.JLabel();
        jlBarcode2 = new javax.swing.JLabel();
        jlBarcode3 = new javax.swing.JLabel();
        jlBarcode4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        cbCarBrand = new javax.swing.JComboBox<>();
        cbCarGen = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        addCarTable = new javax.swing.JTable();
        btnFinish = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("สินค้าสำหรับรถยนต์");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("รหัสสินค้า:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("ชื่อ:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("ชื่อย่อ:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("เลขพาร์ท:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("ยี่ห้อ:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("รายละเอียด:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("เลขพาร์ททั่วไป:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("ราคาทุนประมาณการ/หน่วย:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setText("ราคาขาย/หน่วย:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setText("Barcode2:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setText("Barcode3:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("Barcode1:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setText("Barcode4:");

        jlID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlID.setForeground(new java.awt.Color(51, 204, 0));
        jlID.setText("jLabel15");

        jlName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlName.setForeground(new java.awt.Color(51, 204, 0));
        jlName.setText("jLabel16");

        jlInitials.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlInitials.setForeground(new java.awt.Color(51, 204, 0));
        jlInitials.setText("jLabel17");

        jlPV.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlPV.setForeground(new java.awt.Color(51, 204, 0));
        jlPV.setText("jLabel18");

        jlPart.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlPart.setForeground(new java.awt.Color(51, 204, 0));
        jlPart.setText("jLabel19");

        jlBrand.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlBrand.setForeground(new java.awt.Color(51, 204, 0));
        jlBrand.setText("jLabel20");

        jlDes.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlDes.setForeground(new java.awt.Color(51, 204, 0));
        jlDes.setText("jLabel21");

        jlCost.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlCost.setForeground(new java.awt.Color(51, 204, 0));
        jlCost.setText("jLabel22");

        jlPrice.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlPrice.setForeground(new java.awt.Color(51, 204, 0));
        jlPrice.setText("jLabel23");

        jlBarcode1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlBarcode1.setForeground(new java.awt.Color(51, 204, 0));
        jlBarcode1.setText("jLabel24");

        jlBarcode2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlBarcode2.setForeground(new java.awt.Color(51, 204, 0));
        jlBarcode2.setText("jLabel25");

        jlBarcode3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlBarcode3.setForeground(new java.awt.Color(51, 204, 0));
        jlBarcode3.setText("jLabel26");

        jlBarcode4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlBarcode4.setForeground(new java.awt.Color(51, 204, 0));
        jlBarcode4.setText("jLabel27");

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51)));

        cbCarBrand.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbCarBrand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCarBrandActionPerformed(evt);
            }
        });

        cbCarGen.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setText("ยี่ห้อ");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setText("รุ่น");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCarBrand, 0, 231, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbCarGen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCarBrand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCarGen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(51, 204, 0));
        btnAdd.setText("Add>>");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnDel.setForeground(new java.awt.Color(204, 0, 0));
        btnDel.setText("<<Delete");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlInitials)
                                    .addComponent(jlPV)
                                    .addComponent(jlPart)
                                    .addComponent(jlBrand)
                                    .addComponent(jlDes)
                                    .addComponent(jlCost)
                                    .addComponent(jlPrice)
                                    .addComponent(jlBarcode1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlID)
                                    .addComponent(jlName)
                                    .addComponent(jlBarcode3)
                                    .addComponent(jlBarcode4))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(24, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlBarcode2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jlID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jlName))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jlInitials))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(jlPV))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(jlPart))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jlBrand))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(jlDes))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(jlCost))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jlPrice)))
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jlBarcode1)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jlBarcode2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jlBarcode3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jlBarcode4))
                .addContainerGap(125, Short.MAX_VALUE))
        );

        addCarTable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        addCarTable.setModel(new javax.swing.table.DefaultTableModel(
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
        addCarTable.setRowHeight(30);
        addCarTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addCarTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(addCarTable);

        btnFinish.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnFinish.setForeground(new java.awt.Color(0, 153, 153));
        btnFinish.setText("เสร็จสิ้น");
        btnFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinishActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFinish)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(btnFinish))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        Product p = new Product();
        int genID = -1;
        if (cbCarBrand.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาเลือกยี่ห้อรถยนต์");
            return;
        } else if (cbCarBrand.getSelectedItem().toString().equals("ใช้ได้ทุกยี่ห้อ")) {
            genID = 0;

        } else {
            if (cbCarGen.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, "กรุณาเลือกรุ่นรถ");
                return;
            }
            genID = getGenIDbyGenName(cbCarGen.getSelectedItem().toString());

        }

        p.setCarGenID(genID);
        p.setID(pro.getID());
        p.setTypeID(pro.getTypeID());

        conn.connect();
        conn.insertMappingProduct(p);
        initData();
        this.mf.refreshProductTable();
        conn.disconnect();


    }//GEN-LAST:event_btnAddActionPerformed

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

    private void addCarTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addCarTableMouseClicked
        // TODO add your handling code here:

        btnDel.setEnabled(true);
    }//GEN-LAST:event_addCarTableMouseClicked

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
        // TODO add your handling code here:
        if (addCarTable.getRowCount() == 1) {
            JOptionPane.showMessageDialog(null, "ไม่อนุญาติให้ลบรายการ");
        } else if (addCarTable.getRowCount() > 1) {
            int selectedRow = addCarTable.getSelectedRow();
            String genName = (String) addCarTable.getValueAt(selectedRow, 1);
            int genID = getGenIDbyGenName(genName);
            conn.connect();
            conn.deleteMappingProductByPK(pro.getID(), pro.getTypeID(), genID);
            conn.disconnect();
            initData();
            this.mf.refreshProductTable();

        }
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinishActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btnFinishActionPerformed

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
            java.util.logging.Logger.getLogger(addCarToProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addCarToProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addCarToProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addCarToProduct.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addCarToProduct(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable addCarTable;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnFinish;
    private javax.swing.JComboBox<String> cbCarBrand;
    private javax.swing.JComboBox<String> cbCarGen;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jlBarcode1;
    private javax.swing.JLabel jlBarcode2;
    private javax.swing.JLabel jlBarcode3;
    private javax.swing.JLabel jlBarcode4;
    private javax.swing.JLabel jlBrand;
    private javax.swing.JLabel jlCost;
    private javax.swing.JLabel jlDes;
    private javax.swing.JLabel jlID;
    private javax.swing.JLabel jlInitials;
    private javax.swing.JLabel jlName;
    private javax.swing.JLabel jlPV;
    private javax.swing.JLabel jlPart;
    private javax.swing.JLabel jlPrice;
    // End of variables declaration//GEN-END:variables
}
