/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author pwann
 */
public class ConnectInvoiceBase {

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
     */
    private static Connection conn;
    final static String className = "org.mariadb.jdbc.Driver";

    public boolean connect() {

        try {

            Class.forName(className);
//jdbc:mysql://localhost:3306/spareparts?zeroDateTimeBehavior=convertToNull [root on Default schema]
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoicedata"
                    + "?user=root&password=root");

            if (conn != null) {

                //System.out.println("Database Connected.");
            } else {

                System.out.println("Database Connect Failed.");

                return false;

            }

        } catch (Exception e) {

// TODO Auto-generated catch block
            e.printStackTrace();

        }

        /*try {

            if (conn != null) {

                conn.close();

            }

        } catch (SQLException e) {

// TODO Auto-generated catch block
            e.printStackTrace();

        }*/
        return true;
    }

    public boolean disconnect() {
        try {

            if (conn != null) {

                conn.close();

            }

        } catch (SQLException e) {

// TODO Auto-generated catch block
            e.printStackTrace();

        }
        return true;
    }

    public PreparedStatement prepareStmtQueryBySQLString(String sql) {
        PreparedStatement stmt;

        try {
            stmt = conn.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            return stmt;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "SQLException" + "\n" + e.getMessage(),
                    "prepareStmtQueryBySQLString", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Product QueryProductByPK(int id, int parttype) {

        Product product = new Product();
        String sql = "select p.Barcode,pt.Name,p.Name,Part,PartPrivate,p.ID,Des,CostPerUnit,PricePerUnit"
                + " from product p"
                + " join parttype pt on p.parttype=pt.ID"
                + " where p.ID =  ? and pt.ID=?";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {
            pstm.setInt(1, id);
            pstm.setInt(2, parttype);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;

            }

            //  product.setBC(rs.getString("Barcode"));
            product.setName(rs.getString("p.Name"));
            product.setPart(rs.getString("Part"));
            product.setPV(rs.getString("PartPrivate"));
            // product.setBrand(rs.getString("Brand"));
            product.setDes(rs.getString("Des"));
            product.setID(rs.getInt("ID"));
            product.setCost(rs.getDouble("CostPerUnit"));
            product.setPrice(rs.getDouble("PricePerUnit"));
            product.setTypeName(rs.getString("pt.Name"));
            //product.setQty(rs.getInt("Qty"));
            // product.setLotDate(rs.getString("LotDate"));
            // product.setLocation(rs.getString("LocationName"));

            rs.close();
            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }

    public ArrayList<Product> QueryInv() {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "select i.BarcodeID,i.ID,pt.Name,p.Name,p.Part,p.PartPrivate,p.Des,i.Qty,l.LocationName,i.LotDate,i.CostPerUnit,i.PricePerUnit"
                + " from inventory i"
                + " join product p"
                + " on i.ID = p.ID and i.PartType=p.PartType"
                + " join parttype pt on i.PartType=pt.ID"
                + " join location l on i.LocationID=l.LocationID"
                + " where Qty !=  0";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                //    product.setBC(rs.getString("i.BarcodeID"));
                product.setID(rs.getInt("i.ID"));
                product.setTypeName(rs.getString("pt.Name"));
                product.setName(rs.getString("p.Name"));
                product.setPart(rs.getString("p.Part"));
                product.setPV(rs.getString("p.PartPrivate"));
                //product.setBrand(rs.getString("Brand"));
                product.setDes(rs.getString("p.Des"));
                product.setQty(rs.getInt("i.Qty"));
                product.setCost(rs.getDouble("i.CostPerUnit"));
                //product.setCost2(rs.getDouble("CostPerUnit2"));
                product.setPrice(rs.getDouble("i.PricePerUnit"));
                product.setLotDate(rs.getString("i.LotDate"));
                product.setLocation(rs.getString("l.LocationName"));
                //product.setLocationID(rs.getInt("i.LocationID"));
                products.add(product);
            }
            rs.close();
            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }

    public int QueryMaxSellInvoiceID() {
        int maxIndex = 0;
        String sql = "select max(SellInvoiceID) as maxID from sellinvoice ";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                maxIndex = rs.getInt("maxID");
            }

            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return maxIndex;

    }

    public ArrayList<Location> QueryAllLocation() {
        ArrayList<Location> location = new ArrayList<>();

        String sql = "SELECT LocationID,LocationName"
                + " FROM location";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Location loc = new Location();
                loc.setLocationID(rs.getInt("LocationID"));
                loc.setLocationName(rs.getString("LocationName"));

                location.add(loc);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return location;
    }

    public void deleteTemp() {
        String sql = "DELETE FROM temp";
        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (!rs.next()) {
                return;
            }
            rs.close();

            pstm.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<ProductSupply> QueryProductToInvoice() {
        ArrayList<ProductSupply> products = new ArrayList<>();

        String sql = "SELECT ID,TypeName,Details,Qty,CostPerUnit2,Discount,Amount"
                + " FROM temp limit 200";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ProductSupply product = new ProductSupply();
                product.setID(rs.getInt("ID"));
                product.setTypeName(rs.getString("TypeName"));
                product.setProductName(rs.getString("Details"));
                product.setQty(rs.getInt("Qty"));
                product.setPriceUnit(rs.getDouble("CostPerUnit2"));
                product.setDiscount(rs.getDouble("Discount"));
                product.setAmount(rs.getDouble("Amount"));

                products.add(product);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }

    public ArrayList<Product> QueryAllProduct() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "select p.ID,pt.Name,p.Barcode,p.Name,p.Part,p.PartPrivate,p.Des,p.CostPerUnit2,p.CostPerUnit,p.PricePerUnit"
                + " from product p join parttype pt on p.PartType=pt.ID limit 200";
        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("ID"));
                product.setTypeName(rs.getString("pt.Name"));
                //    product.setBC(rs.getString("p.Barcode"));
                product.setName(rs.getString("p.Name"));
                product.setPart(rs.getString("p.Part"));
                product.setPV(rs.getString("p.PartPrivate"));
                // product.setBrand(rs.getString("Brand"));
                product.setDes(rs.getString("p.Des"));
                product.setCost2(rs.getDouble("p.CostPerUnit2"));
                product.setCost(rs.getDouble("p.CostPerUnit"));
                product.setPrice(rs.getDouble("p.PricePerUnit"));
                products.add(product);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }

    public ArrayList<Product> QueryProductByName(String name) {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "select p.ID,PartType,pt.Name,Barcode,p.Name,Part,PartPrivate,Brand,Des"
                + " from product p join parttype pt on PartType=pt.ID where p.Name like '%" + name + "%' limit 200";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("p.ID"));
                product.setTypeName(rs.getString("pt.Name"));
                product.setTypeID(rs.getInt("PartType"));
                //   product.setBC(rs.getString("Barcode"));
                product.setName(rs.getString("p.Name"));
                product.setPart(rs.getString("Part"));
                product.setPV(rs.getString("PartPrivate"));
                product.setBrand(rs.getString("Brand"));
                product.setDes(rs.getString("Des"));
                products.add(product);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return products;
    }

    public int QueryProductMaxIndex() {
        int maxIndex = 0;
        String sql = "select max(ID) as maxID from Product";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                maxIndex = rs.getInt("maxID");
            }

            rs.close();

            pstm.close();
            return maxIndex;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    public ArrayList<ProductType> QueryProductType() {
        ArrayList<ProductType> ptlist = new ArrayList<>();
        String sql = "select ID,Name from parttype order by ID";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ProductType type = new ProductType();
                type.setID(rs.getInt("ID"));
                type.setName(rs.getString("Name"));
                ptlist.add(type);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return ptlist;

    }

    public int QueryTypeIDByName(String name) {
        int typeID = 99;
        String sql = "select ID from parttype where Name like '" + name + "'";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                typeID = rs.getInt("ID");

            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return typeID;

    }

    public ArrayList<Supply> QueryAllSupplier() {
        ArrayList<Supply> splist = new ArrayList<>();
        String sql = "select ID,Name,Address,Address2,TAX_ID,Tel"
                + " from supplier order by ID";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Supply sp = new Supply();
                sp.setID(rs.getInt("ID"));
                sp.setName(rs.getString("Name"));
                sp.setAddress(rs.getString("Address"));
                sp.setAddress2(rs.getString("Address2"));
                sp.setTAX(rs.getString("TAX_ID"));
                sp.setTEL(rs.getString("Tel"));
                splist.add(sp);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return splist;
    }

    public void insertProduct(Product obj) {

        String sql = "INSERT INTO product (ID,PartType,Barcode,Name,Part,PartPrivate,Brand,Des,CostPerUnit,PricePerunit)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, obj.getID());
            pstm.setInt(2, obj.getTypeID());
//            pstm.setString(3, obj.getBC());
            pstm.setString(4, obj.getName());
            pstm.setString(5, obj.getPart());
            pstm.setString(6, obj.getPV());
            pstm.setString(7, obj.getBrand());
            pstm.setString(8, obj.getDes());
            pstm.setDouble(9, obj.getCost());
            //pstm.setDouble(10, obj.getCost2());
            pstm.setDouble(10, obj.getPrice());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "เพิ่มรายชื่อสินค้าเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertTemp(ProductSupply proS) {

        String sql = "INSERT INTO temp (ID,TypeName,Details,Qty,CostPerUnit2,Discount,Amount)"
                + " VALUES(?,?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, proS.getID());
            pstm.setString(2, proS.getTypeName());
            pstm.setString(3, proS.getProductName());
            pstm.setDouble(5, proS.getCostPerUnit2());
            pstm.setInt(4, proS.getQty());
            pstm.setDouble(6, proS.getDiscount());
            pstm.setDouble(7, proS.getAmount());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "เพิ่มลงตารางการเก็บชั่วคราวแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertInvSup(ProductSupply obj) {

        String sql = "INSERT INTO inventory(ID,PartType,Qty,CostPerUnit,CostPerUnit2,LotDate,PricePerUnit,LocationID,InvoiceID,BarcodeID)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, obj.getID());
            pstm.setInt(2, obj.getTypeID());

            pstm.setInt(3, obj.getQty());
            pstm.setDouble(4, obj.getCostperUnit());
            pstm.setDouble(5, obj.getCostPerUnit2());
            pstm.setString(6, obj.getDate());
            pstm.setDouble(7, obj.getPricePerUnit());
            pstm.setInt(8, obj.getLocationID());
            pstm.setString(9, obj.getInvoiceID());
            pstm.setString(10, obj.getBarcodeID());

            pstm.execute();
            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertInvoiceDetails(ProductSupply obj) {

        String sql = "INSERT INTO invoicedetails(ProductID,Qty,ProductName,CostPerUnit,CostPerUnit2,InvoiceID,Barcode)"
                + " VALUES(?,?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, obj.getID());
            pstm.setInt(2, obj.getQty());
            pstm.setString(3, obj.getProductName());
            pstm.setDouble(4, obj.getCostperUnit());
            pstm.setDouble(5, obj.getCostPerUnit2());
            pstm.setString(6, obj.getInvoiceID());
            pstm.setString(7, obj.getBarcodeID());

            pstm.execute();
            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Supply QuerySupplierByIndex(int index) {
        Supply sp = new Supply();
        String sql = "select ID,Name,Address,Address2,TAX_ID,Tel"
                + " from supplier where ID = " + index;

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {

                sp.setID(rs.getInt("ID"));
                sp.setName(rs.getString("Name"));
                sp.setAddress(rs.getString("Address"));
                sp.setAddress2(rs.getString("Address2"));
                sp.setTAX(rs.getString("TAX_ID"));
                sp.setTEL(rs.getString("Tel"));

            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return sp;
    }

    public void insertSupplier(Supply sp) {
        String sql = "INSERT INTO supplier(Name,Address,Address2,TAX_ID,Tel)"
                + " VALUES(?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(2, sp.getAddress());
            pstm.setString(3, sp.getAddress2());
            pstm.setString(1, sp.getName());
            pstm.setString(4, sp.getTAX());
            pstm.setString(5, sp.getTEL());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "จัดเก็บชื่อผู้ผลิตเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertInvoice(String invoieId, String supplyName, String dateInvoiceFormatted, int credit, Double ServiceCost, Double Amount, String ServiceType) {

        String sql = "INSERT INTO invoice(InvoiceID,SupplierName,LotDate,CashOrCredit,ServiceType,ServiceCost,Amount)"
                + " VALUES(?,?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, invoieId);
            pstm.setString(2, supplyName);
            pstm.setString(3, dateInvoiceFormatted);
            pstm.setInt(4, credit);
            pstm.setString(5, ServiceType);
            pstm.setDouble(6, ServiceCost);
            pstm.setDouble(7, Amount);

            pstm.execute();
            pstm.close();

            //JOptionPane.showMessageDialog(null, "จัดเก็บใบกำกับสินค้าเรียบร้อยแล้ว");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertInvoice(String invoieId, String supplyName, String LotDate, int flag, int credit, Double ServiceCost, Double Amount, String ServiceType) {

        String sql = "INSERT INTO invoice(InvoiceID,SupplierName,LotDate,Vat_NoneVat,CashOrCredit,ServiceType,ServiceCost,Amount)"
                + " VALUES(?,?,?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, invoieId);
            pstm.setString(2, supplyName);
            pstm.setString(3, LotDate);
            pstm.setInt(4, flag);
            pstm.setInt(5, credit);
            pstm.setString(6, ServiceType);
            pstm.setDouble(7, ServiceCost);
            pstm.setDouble(8, Amount);

            pstm.execute();
            pstm.close();

            //JOptionPane.showMessageDialog(null, "จัดเก็บใบกำกับสินค้าเรียบร้อยแล้ว");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
