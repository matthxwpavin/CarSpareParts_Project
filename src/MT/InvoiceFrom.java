/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.JTableHeader;

/**
 *
 * @author pwann
 */
public class InvoiceFrom extends javax.swing.JFrame {

    /**
     * @return the ServiceCost
     */
    public Double getServiceCost() {
        return ServiceCost;
    }

    /**
     * @param ServiceCost the ServiceCost to set
     */
    public void setServiceCost(Double ServiceCost) {
        this.ServiceCost = ServiceCost;
    }

    /**
     * @return the ServiceName
     */
    public String getServiceName() {
        return ServiceName;
    }

    /**
     * @param ServiceName the ServiceName to set
     */
    public void setServiceName(String ServiceName) {
        this.ServiceName = ServiceName;
    }

    /**
     * Creates new form InvoiceFrom
     */
    private ConnectDB conn;
    private ConnectInvoiceBase cib;
    private Vector Items = new Vector();
    DefaultComboBoxModel commodel;
    private Font f = new Font("Tahoma", Font.BOLD, 20);
    private InvoiceTableModel Invoicemodel;
    private int LocationID;
    private double cost2;
    private int vat = 7;
    private MainForm mf;
    private String Lotdate;
    private Double ServiceCost;
    private String ServiceName;
    private int clickTime = 1;
    DateFormat dateFormat;
    Date dateInvocie;
    Double grand;
    boolean VatNoneVat;
    boolean service;

    public InvoiceFrom(MainForm mf) {
        initComponents();
        jProductNameTable.setModel(new InvoiceTableModel());

        makeFrameFullSize();
        this.mf = mf;

        conn = new ConnectDB();
        conn.connect();
        JTextFieldDateEditor editor = (JTextFieldDateEditor) jDateChooser1.getDateEditor();
        editor.setEditable(false);
        ArrayList<Supply> suplist = conn.QueryAllSupplier();
        Items.add(" ");
        for (Supply sp : suplist) {
            Items.add(sp.getName());
        }
        commodel = new DefaultComboBoxModel(Items);
        conn.deleteTemp();

        conn.disconnect();
        initData();

    }

    public void AddProductToInv() {
        if (clickTime == 1) {

            VatNoneVat = chkbVat.isSelected();

            if (this.getServiceCost() != 0.0) {
                Invoicemodel.addRowDataAt(this.getServiceName(), this.getServiceCost());
                jProductNameTable.setModel(Invoicemodel);
                this.grand += this.getServiceCost();
                jlGrand.setText(Double.toString(this.grand));
                service = true;
                clickTime = 2;
                return;
            }
            service = false;
            insertInv(VatNoneVat, service);
            InvoiceTableModel iv = new InvoiceTableModel();
            jProductNameTable.setModel(iv);
            this.mf.refreshInvPartTable();
            this.setVisible(false);
        }

    }

    public void initData() {
        cbSupplyName.setModel(commodel);
        JTableHeader header = jProductNameTable.getTableHeader();
        header.setFont(f);
        tfVat.setText(Integer.toString(vat));
        jlGrand.setText("");
        jlVat.setText("");
        jlTotal.setText("");
    }

    private void makeFrameFullSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
    }

    public void setProductToTable(ArrayList<Product> ps) {
        Invoicemodel = new InvoiceTableModel();
        Invoicemodel.addAllData(ps);

        jProductNameTable.setModel(Invoicemodel);
    }

    public void insertInv(boolean vatnonevat, boolean service) {
        if (service == false) {

            dateInvocie = jDateChooser1.getDate();
            dateFormat = new SimpleDateFormat("yyMMdd");
            String dateInvoiceFormatted = dateFormat.format(dateInvocie);
            int maxRow = jProductNameTable.getRowCount();
            String SelectedPay = cbPay.getSelectedItem().toString();
            int credit = -1;
            if (SelectedPay.equals("เงินสด")) {
                credit = 0;
            } else {
                String[] duedates = SelectedPay.split(" ");
                credit = Integer.parseInt(duedates[1]);
            }

            String supplyName = cbSupplyName.getSelectedItem().toString();
            String invoiceID = tfInvoiceID.getText();

            cib = new ConnectInvoiceBase();
            cib.connect();
            conn.connect();

            if (vatnonevat) {
                cib.insertInvoice(invoiceID, supplyName, dateInvoiceFormatted, credit, getServiceCost(), this.grand, getServiceName());

            } else {
                int flag = 1;
                cib.insertInvoice(invoiceID, supplyName, dateInvoiceFormatted, flag, credit, getServiceCost(), this.grand, getServiceName());
            }
            for (int i = 0; i < maxRow; i++) {
                int locID = -1;
                ArrayList<Location> locList = conn.QueryAllLocation();
                String locName = (String) jProductNameTable.getValueAt(i, 8);
                for (int j = 0; j < locList.size(); j++) {
                    if (locList.get(j).getLocationName().equals(locName)) {
                        locID = locList.get(j).getLocationID();
                        break;
                    }
                }
                int typeID = -1;
                String typeName = (String) jProductNameTable.getValueAt(i, 2);
                ArrayList<ProductType> typeList = conn.QueryAllPartType();
                for (int k = 0; k < typeList.size(); k++) {
                    if (typeList.get(k).getName().equals(typeName)) {
                        typeID = typeList.get(k).getID();
                        break;
                    }
                }
                Product Prod = new Product();

                Prod.setID((int) jProductNameTable.getValueAt(i, 0));
                Prod.setQty((int) jProductNameTable.getValueAt(i, 9));
                Prod.setCost2((Double) jProductNameTable.getValueAt(i, 10));
                Prod.setName((String) jProductNameTable.getValueAt(i, 3));
                Prod.setLocation((String) jProductNameTable.getValueAt(i, 8));
                Prod.setLocationID(locID);
                Prod.setTypeID(typeID);
                Prod.setLotDate(dateInvoiceFormatted);
                String Barcode = String.format("P%07dT%03dL%s", Prod.getID(), Prod.getTypeID(), dateInvoiceFormatted);
                Prod.setBarcode(Barcode);
                Prod.setInvoiceID(invoiceID);

                conn.insertProductInventory(Prod);

            }
            conn.deleteTemp();

            JOptionPane.showMessageDialog(null, "เพิ่มสินค้าในคลังเรียบร้อยแล้ว");
            cib.disconnect();
            conn.disconnect();
        } else {
            dateInvocie = jDateChooser1.getDate();
            dateFormat = new SimpleDateFormat("yyMMdd");
            String dateInvoiceFormatted = dateFormat.format(dateInvocie);
            int maxRow = jProductNameTable.getRowCount() - 1;
            String SelectedPay = cbPay.getSelectedItem().toString();
            int credit = -1;
            if (SelectedPay.equals("เงินสด")) {
                credit = 0;
            } else {
                String[] duedates = SelectedPay.split(" ");
                credit = Integer.parseInt(duedates[1]);
            }

            String supplyName = cbSupplyName.getSelectedItem().toString();
            String invoiceID = tfInvoiceID.getText();

            cib = new ConnectInvoiceBase();
            cib.connect();
            conn.connect();

            if (vatnonevat) {
                cib.insertInvoice(invoiceID, supplyName, dateInvoiceFormatted, credit, getServiceCost(), this.grand, getServiceName());

            } else {
                int flag = 1;
                cib.insertInvoice(invoiceID, supplyName, dateInvoiceFormatted, flag, credit, getServiceCost(), this.grand, getServiceName());
            }
            for (int i = 0; i < maxRow; i++) {
                int locID = -1;
                ArrayList<Location> locList = conn.QueryAllLocation();
                String locName = (String) jProductNameTable.getValueAt(i, 8);
                for (int j = 0; j < locList.size(); j++) {
                    if (locList.get(j).getLocationName().equals(locName)) {
                        locID = locList.get(j).getLocationID();
                        break;
                    }
                }
                int typeID = -1;
                String typeName = (String) jProductNameTable.getValueAt(i, 2);
                ArrayList<ProductType> typeList = conn.QueryAllPartType();
                for (int k = 0; k < typeList.size(); k++) {
                    if (typeList.get(k).getName().equals(typeName)) {
                        typeID = typeList.get(k).getID();
                        break;
                    }
                }
                Product Prod = new Product();

                Prod.setID((int) jProductNameTable.getValueAt(i, 0));
                Prod.setQty((int) jProductNameTable.getValueAt(i, 9));
                Prod.setCost2((Double) jProductNameTable.getValueAt(i, 10));
                Prod.setName((String) jProductNameTable.getValueAt(i, 3));
                Prod.setLocation((String) jProductNameTable.getValueAt(i, 8));
                Prod.setLocationID(locID);
                Prod.setTypeID(typeID);
                Prod.setLotDate(dateInvoiceFormatted);
                String Barcode = String.format("P%07dT%03dL%s", Prod.getID(), Prod.getTypeID(), dateInvoiceFormatted);
                Prod.setBarcode(Barcode);
                Prod.setInvoiceID(invoiceID);

                conn.insertProductInventory(Prod);

            }
            conn.deleteTemp();

            JOptionPane.showMessageDialog(null, "เพิ่มสินค้าในคลังเรียบร้อยแล้ว");
            cib.disconnect();
            conn.disconnect();
        }
    }

    public void getDataFromAddInv(Product p) {
        LocationID = p.getLocationID();
        this.cost2 = p.getCost2();

    }

    public void refreshTableData() {
        this.grand = 0.0;
        ArrayList<Product> products = new ArrayList<>();
        products = conn.QueryProductToInvoice();
        double amount = 0;
        for (Product ps : products) {
            amount = amount + ps.getAmount();

        }

        String doubleAsString2f = String.format("%.2f", (amount / 100) * this.vat);
        double vatt = Double.parseDouble(doubleAsString2f);
        this.jlVat.setText(doubleAsString2f);
        this.grand = amount + vatt;
        this.setProductToTable(products);
        doubleAsString2f = String.format("%.2f", amount);
        this.jlTotal.setText(doubleAsString2f);

        doubleAsString2f = String.format("%.2f", this.grand);
        this.jlGrand.setText(doubleAsString2f);
        this.tfProductName.setText("");

        products.clear();

        //System.out.println(products.size());
        // conn.deleteTemp();
        conn.disconnect();
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
        jLabel2 = new javax.swing.JLabel();
        tfInvoiceID = new javax.swing.JTextField();
        cbSupplyName = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbPay = new javax.swing.JComboBox<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        จัดเก็บสินค้าเข้าคลัง = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        tfProductName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jProductNameTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnAddInvoice = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        j = new javax.swing.JLabel();
        btnCancel = new javax.swing.JButton();
        tfVat = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        chkbVat = new javax.swing.JCheckBox();
        jlTotal = new javax.swing.JLabel();
        jlGrand = new javax.swing.JLabel();
        jlVat = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setText("ชื่อร้านค้า");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 0, 51));
        jLabel2.setText("เล่มที่/เลขที่");

        tfInvoiceID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        cbSupplyName.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 0, 0));
        jLabel7.setText("วันที่");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 0, 0));
        jLabel8.setText("เงินสด/เครดิต");

        cbPay.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        cbPay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "เงินสด", "เครดิต 7 วัน", "เครดิต 15 วัน", "เครดิต 30 วัน" }));

        jDateChooser1.setDateFormatString("yyyyMMdd");
        jDateChooser1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfInvoiceID, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbSupplyName, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbPay, 0, 199, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbPay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(tfInvoiceID)
                            .addComponent(cbSupplyName)
                            .addComponent(jLabel7)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(33, 33, 33))))
        );

        จัดเก็บสินค้าเข้าคลัง.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        จัดเก็บสินค้าเข้าคลัง.setForeground(new java.awt.Color(0, 204, 51));
        จัดเก็บสินค้าเข้าคลัง.setText("จัดเก็บสินค้าเข้าคลัง");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(จัดเก็บสินค้าเข้าคลัง)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(จัดเก็บสินค้าเข้าคลัง)
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("ค้นหาสินค้าในรายการ:");

        tfProductName.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        tfProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfProductNameActionPerformed(evt);
            }
        });

        jProductNameTable.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jProductNameTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jProductNameTable.setRowHeight(30);
        jProductNameTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jProductNameTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jProductNameTable);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("รวมราคาสินค้า:");

        btnAddInvoice.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnAddInvoice.setForeground(new java.awt.Color(0, 153, 0));
        btnAddInvoice.setText("เพิ่มเข้าคลังสินค้า");
        btnAddInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddInvoiceActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("ภาษีมูลค่าเพิ่ม ");

        j.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        j.setText("จำนวนเงินรวมทั้งสิ้น:");

        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(204, 0, 0));
        btnCancel.setText("ยกเลิก");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        tfVat.setEditable(false);
        tfVat.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        tfVat.setForeground(new java.awt.Color(204, 0, 0));
        tfVat.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfVat.setText("7");
        tfVat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfVatActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setText("% :");

        chkbVat.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        chkbVat.setSelected(true);
        chkbVat.setText("รายการสินค้ามีVAT");
        chkbVat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkbVatActionPerformed(evt);
            }
        });

        jlTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlTotal.setText("jLabel12");

        jlGrand.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlGrand.setForeground(new java.awt.Color(204, 0, 0));
        jlGrand.setText("jLabel12");

        jlVat.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jlVat.setText("jLabel12");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkbVat)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(btnAddInvoice)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1002, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(tfVat, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel6))
                                            .addComponent(j, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel5)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlVat)
                                    .addComponent(jlTotal)
                                    .addComponent(jlGrand))
                                .addGap(24, 24, 24))))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tfProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(chkbVat, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlTotal)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfVat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jlVat))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(j)
                            .addComponent(jlGrand)))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddInvoice)
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(140, 140, 140))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfProductNameActionPerformed
        // TODO add your handling code here:
        if (tfProductName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "กรุณาใส่คำที่ต้องการค้นหา");
            return;
        }

        conn.connect();
        ArrayList<Product> prodList = new ArrayList<>();
        prodList = conn.QueryProductByName(tfProductName.getText());

        if (prodList.size() == 0) {
            JOptionPane.showMessageDialog(this, "ไม่พบรายการสินค้าที่ค้นหา");
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

        ProductName productname = new ProductName(this);

        productname.setTableData(productListToSet);

        conn.disconnect();
    }//GEN-LAST:event_tfProductNameActionPerformed

    private void btnAddInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInvoiceActionPerformed
        // TODO add your handling code here:
        int getRow = jProductNameTable.getRowCount();
        if (getRow == 0) {
            JOptionPane.showMessageDialog(this, "ยังไม่มีรายการสินค้าที่จะเพิ่ม");
            return;
        }
        if (tfInvoiceID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "กรุณาใส่เลขที่ใบกำกับ");
            return;
        }
        if (cbSupplyName.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "กรุณาเลือกร้านค้า");
            return;
        }
        if (jDateChooser1.getDate().toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, "กรุณาเลือกวันที่");
            return;
        }
        if (cbPay.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "กรุณาเลือกประเภทการชำระ");
            return;
        }
        if (clickTime == 1) {
            new ServiceCostForm(this);
        } else if (clickTime == 2) {
            insertInv(VatNoneVat, service);
            InvoiceTableModel iv = new InvoiceTableModel();
            jProductNameTable.setModel(iv);
            clickTime = 1;
            this.mf.refreshInvPartTable();
            this.setVisible(false);
        }


    }//GEN-LAST:event_btnAddInvoiceActionPerformed

    private void tfVatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfVatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfVatActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:

        this.setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void chkbVatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkbVatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkbVatActionPerformed

    private void jProductNameTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jProductNameTableMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_jProductNameTableMouseClicked

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
            java.util.logging.Logger.getLogger(InvoiceFrom.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InvoiceFrom.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InvoiceFrom.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InvoiceFrom.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InvoiceFrom(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddInvoice;
    private javax.swing.JButton btnCancel;
    private javax.swing.JComboBox<String> cbPay;
    private javax.swing.JComboBox<String> cbSupplyName;
    private javax.swing.JCheckBox chkbVat;
    private javax.swing.JLabel j;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTable jProductNameTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel jlGrand;
    private javax.swing.JLabel jlTotal;
    private javax.swing.JLabel jlVat;
    private javax.swing.JTextField tfInvoiceID;
    private javax.swing.JTextField tfProductName;
    private javax.swing.JTextField tfVat;
    private javax.swing.JLabel จัดเก็บสินค้าเข้าคลัง;
    // End of variables declaration//GEN-END:variables
}
