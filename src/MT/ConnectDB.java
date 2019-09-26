/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MT;

/**
 *
 * @author pwann
 */
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConnectDB {

    private static Connection conn;
    final static String className = "org.mariadb.jdbc.Driver";

    public boolean connect() {

        try {

            Class.forName(className);
//jdbc:mysql://localhost:3306/spareparts?zeroDateTimeBehavior=convertToNull [root on Default schema]
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/spareparts"
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

    public Product QueryProductByPK(int id) {

        Product product = new Product();
        String sql = "select p.productID,productName,initials,Part,PartPrivate,productBrand,productDes,costPerUnit,pricePerUnit,BarcodeName1,BarcodeName2,BarcodeName3,BarcodeName4,mpt.PartGroupName,mpt.Name,cg.carGenName,cg.CarBrandName"
                + " from product p left join mappingproduct mpp on p.productID=mpp.productID left join mappingparttype mpt on mpp.partTypeID=mpt.ID "
                + "join cargen cg on mpp.carGenID=cg.carGenID where p.productID=?";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                return null;

            }

            product.setID(rs.getInt("p.productID"));
            product.setName(rs.getString("p.productName"));
            product.setInitials(rs.getString("p.initials"));
            product.setPart(rs.getString("p.Part"));
            product.setPV(rs.getString("p.PartPrivate"));
            product.setBrand(rs.getString("productBrand"));
            product.setDes(rs.getString("p.productDes"));
            product.setCost(rs.getDouble("p.costPerUnit"));
            product.setPrice(rs.getDouble("p.pricePerUnit"));
            product.setBarcode1(rs.getString("p.BarcodeName1"));
            product.setBarcode2(rs.getString("p.BarcodeName2"));
            product.setBarcode3(rs.getString("p.BarcodeName3"));
            product.setBarcode4(rs.getString("p.BarcodeName4"));
            product.setGroupName(rs.getString("mpt.PartGroupName"));
            product.setTypeName(rs.getString("mpt.Name"));
            product.setCarBrandName(rs.getString("cg.carBrandName"));
            product.setCarGenName(rs.getString("cg.carGenName"));

            rs.close();
            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }

    public ArrayList<Product> QueryCarBrandCarGenByCarGenID(int id) {

        ArrayList<Product> product = new ArrayList<>();
        String sql = "select cb.CarBrandName,cg.Engine,cg.CarGenName from carbrand cb"
                + " join cargen cg on cb.CarBrandID=cg.CarBrandID"
                + " where cg.CarGenID =" + id;

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Product prod = new Product();
                prod.setCarBrandName(rs.getString("cb.CarBrandName"));
                prod.setCarGenName(rs.getString("cg.CarGenName"));
                prod.setEngine(rs.getString("cg.Engine"));

                rs.close();
                pstm.close();
                product.add(prod);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return product;
    }

    public ArrayList<Product> QueryInv() {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "select pi.productID,pi.productName,pi.LocationName,sum(Qty) as 'Qty'"
                + " from productinventory pi"
                + " group by pi.productID";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
       
                product.setID(rs.getInt("pi.productID"));
                product.setName(rs.getString("pi.productName"));
                product.setQty(rs.getInt("Qty"));
                product.setLocation(rs.getString("pi.LocationName"));
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

    public ArrayList<Product> QueryProductToInvoice() {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT productID,groupType,typeName,productName,initials,Brand,Part,partPrivate,Des,costPerUnit,costPerUnit2,PricePerUnit,Amount"
                + ",Discount,Qty,CarBrandName,CarGenName,locationName"
                + " FROM temp limit 200";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("productID"));
                product.setGroupName(rs.getString("groupType"));
                product.setTypeName(rs.getString("typeName"));
                product.setName(rs.getString("productName"));
                product.setInitials(rs.getString("initials"));
                product.setBrand(rs.getString("Brand"));
                product.setPart(rs.getString("Part"));
                product.setPV(rs.getString("partPrivate"));
                product.setDes(rs.getString("Des"));
                product.setCost(rs.getDouble("costPerUnit"));
                product.setCost2(rs.getDouble("costPerUnit2"));
                product.setPrice(rs.getDouble("PricePerUnit"));
                product.setAmount(rs.getDouble("Amount"));
                product.setDiscount(rs.getDouble("Discount"));
                product.setQty(rs.getInt("Qty"));
                product.setCarBrandName(rs.getString("CarBrandName"));
                product.setCarGenName(rs.getString("CarGenName"));
                product.setLocation(rs.getString("locationName"));

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
        String sql = "select p.productID,p.productName,p.initials,p.Part,p.PartPrivate,p.productBrand,p.productNameENG,p.productDes,p.costPerUnit,p.pricePerUnit\n"
                + "                ,p.BarcodeName1,p.BarcodeName2,p.BarcodeName3,p.BarcodeName4,mpt.PartGroupName,mpt.`Name`,cg.carGenName,cg.CarBrandName,cg.`Engine` \n"
                + "                 from  product p left join  mappingproduct mpp on p.productID=mpp.productID left join mappingparttype mpt on mpp.partTypeID=mpt.ID \n"
                + "                 left join cargen cg on mpp.carGenID=cg.carGenID  order by p.productID  ";
        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("p.productID"));
                product.setName(rs.getString("productName"));
                product.setInitials(rs.getString("initials"));
                product.setPart(rs.getString("Part"));
                product.setPV(rs.getString("PartPrivate"));
                product.setBrand(rs.getString("productBrand"));
                product.setDes(rs.getString("productDes"));
                product.setCost(rs.getDouble("costPerUnit"));
                product.setPrice(rs.getDouble("pricePerUnit"));
                product.setBarcode1(rs.getString("BarcodeName1"));
                product.setBarcode2(rs.getString("BarcodeName2"));
                product.setBarcode3(rs.getString("BarcodeName3"));
                product.setBarcode4(rs.getString("BarcodeName4"));
                product.setGroupName(rs.getString("mpt.PartGroupName"));
                product.setTypeName(rs.getString("mpt.Name"));
                product.setCarBrandName(rs.getString("cg.CarBrandName"));
                product.setCarGenName(rs.getString("carGenName"));
                product.setEngine(rs.getString("Engine"));
                product.setProductNameENG(rs.getString("productNameENG"));
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

    public ArrayList<Product> QueryProductByCondition(String xsql) {
        ArrayList<Product> products = new ArrayList<>();
        String sql = xsql;
        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("p.productID"));
                product.setName(rs.getString("productName"));
                product.setInitials(rs.getString("initials"));
                product.setPart(rs.getString("Part"));
                product.setPV(rs.getString("PartPrivate"));
                product.setBrand(rs.getString("productBrand"));
                product.setDes(rs.getString("productDes"));
                product.setCost(rs.getDouble("costPerUnit"));
                product.setPrice(rs.getDouble("pricePerUnit"));
                product.setBarcode1(rs.getString("BarcodeName1"));
                product.setBarcode2(rs.getString("BarcodeName2"));
                product.setBarcode3(rs.getString("BarcodeName3"));
                product.setBarcode4(rs.getString("BarcodeName4"));
                product.setGroupName(rs.getString("mpt.PartGroupName"));
                product.setTypeName(rs.getString("mpt.Name"));
                product.setCarBrandName(rs.getString("cg.carBrandName"));
                product.setCarGenName(rs.getString("carGenName"));
                product.setProductNameENG(rs.getString("productNameENG"));
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

    public ArrayList<Product> QueryProductInventoryByCondition(String condition) {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "select pi.productID,pi.invoiceID,pi.productName,pi.Barcode,pi.LotDate,pi.LocationName,sum(Qty) as 'Qty',p.pricePerUnit "
                + "from productinventory pi left join product p on pi.productID=p.productID "
                + "where " + condition + " group by pi.productID";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);

        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();

                product.setID(rs.getInt("pi.productID"));
                product.setInvoiceID(rs.getString("pi.invoiceID"));
                product.setName(rs.getString("pi.productName"));
                product.setBarcode(rs.getString("pi.Barcode"));
                product.setLotDate(rs.getString("pi.LotDate"));
                product.setPrice(rs.getDouble("p.pricePerUnit"));
                product.setLocation(rs.getString("pi.LocationName"));
                product.setQty(rs.getInt("Qty"));
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
        String sql = "select p.productID,p.productName,p.initials,p.Part,p.PartPrivate,p.productBrand,p.productDes,p.costPerUnit,p.pricePerUnit,p.BarcodeName1,p.BarcodeName2,p.BarcodeName3,p.BarcodeName4,mpt.PartGroupName,mpt.`Name`,cg.carGenName,cg.CarBrandName"
                + " from product p  left join  mappingproduct mpp on p.productID=mpp.productID left join mappingparttype mpt on mpp.partTypeID=mpt.ID "
                + " left join cargen cg on mpp.carGenID=cg.carGenID where  p.productName like '%" + name + "%' ";
        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setID(rs.getInt("p.productID"));
                product.setName(rs.getString("productName"));
                product.setInitials(rs.getString("initials"));
                product.setPart(rs.getString("Part"));
                product.setPV(rs.getString("PartPrivate"));
                product.setBrand(rs.getString("productBrand"));
                product.setDes(rs.getString("productDes"));
                product.setCost(rs.getDouble("costPerUnit"));
                product.setPrice(rs.getDouble("pricePerUnit"));
                product.setBarcode1(rs.getString("BarcodeName1"));
                product.setBarcode2(rs.getString("BarcodeName2"));
                product.setBarcode3(rs.getString("BarcodeName3"));
                product.setBarcode4(rs.getString("BarcodeName4"));
                product.setGroupName(rs.getString("mpt.PartGroupName"));
                product.setTypeName(rs.getString("mpt.Name"));
                product.setCarBrandName(rs.getString("cg.carBrandName"));
                product.setCarGenName(rs.getString("cg.carGenName"));
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
        String sql = "select max(productID) as maxID from product";

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

    public int QueryMaxIDPartType() {
        int maxIndex = 0;
        String sql = "select max(ID) as maxID from mappingparttype ";

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

    public int QueryMaxIDProductBrand() {
        int maxIndex = 0;
        String sql = "select max(BrandID) as maxID from ProductBrand ";

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

    public ArrayList<Product> QueryTempSellProduct() {
        ArrayList<Product> Prod = new ArrayList<>();
        String sql = "select productID,productName,Qty,PricePerUnit,Amount  from tempsellproduct ";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product prod = new Product();
                prod.setAmount(rs.getDouble("Amount"));
                prod.setID(rs.getInt("productID"));
                prod.setName(rs.getString("productName"));
                prod.setPrice(rs.getDouble("PricePerUnit"));
                prod.setQty(rs.getInt("Qty"));

                Prod.add(prod);
            }

            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return Prod;

    }

    public int QueryCarBrandMaxIndex() {
        int maxIndex = 0;
        String sql = "select max(CarBrandID) as maxID from carbrand";

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

    public int QueryPartTypeMaxIndex() {
        int maxIndex = 0;
        String sql = "select max(ID) as maxID from parttype";

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

    public int QueryCarGenMaxIndex() {
        int maxIndex = 0;
        String sql = "select max(CarGenID) as maxID from cargen ";

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

    public int QuerySuplierMaxIndex() {
        int maxIndex = 0;
        String sql = "select max(ID) as maxID from supplier";

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

    public ArrayList<PartGroup> QueryAllPartGroup() {

        String sql = "select ID,Name from parttype where ID>0 order by ID";

        ArrayList<PartGroup> listGroup = new ArrayList<>();

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                PartGroup pg = new PartGroup();

                pg.setID(rs.getInt("ID"));
                pg.setName(rs.getString("Name"));

                listGroup.add(pg);

            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return listGroup;

    }

    public ArrayList<ProductBrand> QueryAllformTableProductBrand() {

        String sql = "select BrandID,BrandName from ProductBrand  order by BrandID";

        ArrayList<ProductBrand> listGroup = new ArrayList<>();

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ProductBrand pg = new ProductBrand();

                pg.setProductBrandID(rs.getInt("BrandID"));
                pg.setBrandName(rs.getString("BrandName"));

                listGroup.add(pg);

            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return listGroup;
    }

    public ArrayList<ProductType> QueryAllPartType() {

        String sql = "select ID,PartTypeID,Name from mappingparttype order by ID";

        ArrayList<ProductType> listType = new ArrayList<>();

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ProductType pt = new ProductType();

                pt.setID(rs.getInt("ID"));
                pt.setName(rs.getString("Name"));
                pt.setPartGroupID(rs.getInt("PartTypeID"));

                listType.add(pt);

            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return listType;

    }

    public ArrayList<ProductType> QueryPartTypeByPartGroupID(int groupID) {

        String sql = "select ID,PartTypeID,Name from mappingparttype where PartTypeID=" + groupID + " order by name";

        ArrayList<ProductType> listGroup = new ArrayList<>();

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                ProductType pg = new ProductType();

                pg.setID(rs.getInt("ID"));
                pg.setPartGroupID(rs.getInt("PartTypeID"));
                pg.setName(rs.getString("Name"));

                listGroup.add(pg);

            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return listGroup;

    }

    public ArrayList<CarClass> QueryCarGenByCarBrandID(int brandID) {

        String sql = "select CarGenID,CarBrandID,CarGenName,Engine,Fuel,Gear from cargen where CarBrandID=" + brandID + " order by CarGenID";

        ArrayList<CarClass> listGroup = new ArrayList<>();

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                CarClass cc = new CarClass();

                cc.setBrandID(rs.getInt("CarBrandID"));
                cc.setGenID(rs.getInt("CarGenID"));
                cc.setGen(rs.getString("CarGenName"));
                cc.setEngine(rs.getString("Engine"));
                cc.setFuel(rs.getString("Fuel"));
                cc.setGear(rs.getString("Gear"));

                listGroup.add(cc);

            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return listGroup;

    }

    public ArrayList<Product> QueryMappingProductByProID(int proID) {

        String sql = "select productID,partTypeID,carGenID from mappingproduct where productID=" + proID;

        ArrayList<Product> listProd = new ArrayList<>();

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Product prod = new Product();

                prod.setID(rs.getInt("productID"));
                prod.setTypeID(rs.getInt("partTypeID"));
                prod.setCarGenID(rs.getInt("carGenID"));

                listProd.add(prod);

            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return listProd;

    }

    public ArrayList<Supply> QueryAllSupplierShowTable() {
        ArrayList<Supply> splist = new ArrayList<>();
        String sql = "select ID,Name,Address,Address2,TAX_ID,Tel"
                + " from supplier  order by ID";

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

    public ArrayList<CarClass> QueryAllCarBrand() {
        ArrayList<CarClass> brandlist = new ArrayList<>();
        String sql = "select CarBrandID,CarBrandName"
                + " from carbrand  order by CarBrandID";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                CarClass car = new CarClass();
                car.setBrandID(rs.getInt("CarBrandID"));
                car.setBrand(rs.getString("CarBrandName"));

                brandlist.add(car);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return brandlist;
    }

    public ArrayList<CarClass> QueryAllCarGen() {
        ArrayList<CarClass> genlist = new ArrayList<>();
        String sql = "select CarGenID,CarBrandID,CarGenName,Engine,Fuel,Gear"
                + " from cargen  order by CarGenName";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                CarClass car = new CarClass();
                car.setBrandID(rs.getInt("CarBrandID"));
                car.setGen(rs.getString("CarGenName"));
                car.setGenID(rs.getInt("CarGenID"));
                car.setEngine("Engine");
                car.setFuel("Fuel");
                car.setGear("Gear");

                genlist.add(car);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return genlist;
    }

    public ArrayList<String> QueryAllProductBrand() {
        ArrayList<String> brandlist = new ArrayList<>();
        String sql = "select productBrand"
                + " from product  group by productBrand order by productBrand";

        PreparedStatement pstm = prepareStmtQueryBySQLString(sql);
        try {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                String brand = new String();
                brand = rs.getString("productBrand");

                brandlist.add(brand);
            }
            rs.close();

            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        return brandlist;
    }

    public ArrayList<Supply> QueryAllSupplier() {
        ArrayList<Supply> splist = new ArrayList<>();
        String sql = "select ID,Name,Address,Address2,TAX_ID,Tel"
                + " from supplier  order by ID";

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

        String sql = "INSERT INTO product (productID,productName,initials,Part,PartPrivate,productBrand,productDes,costPerUnit,pricePerUnit,BarcodeName1,BarcodeName2,BarcodeName3,BarcodeName4)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, obj.getID());
            pstm.setString(2, obj.getName());
            pstm.setString(3, obj.getInitials());
            pstm.setString(4, obj.getPart());
            pstm.setString(5, obj.getPV());
            pstm.setString(6, obj.getBrand());
            pstm.setString(7, obj.getDes());
            pstm.setDouble(8, obj.getCost());
            pstm.setDouble(9, obj.getPrice());
            pstm.setString(10, obj.getBarcode1());
            pstm.setString(11, obj.getBarcode2());
            pstm.setString(12, obj.getBarcode3());
            pstm.setString(13, obj.getBarcode4());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "เพิ่มรายชื่อสินค้าเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertTempSellProduct(Product obj) {

        String sql = "INSERT INTO tempsellproduct (productID,productName,Qty,pricePerUnit,Amount)"
                + " VALUES(?,?,?,?,?);";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, obj.getID());
            pstm.setString(2, obj.getName());
            pstm.setInt(3, obj.getQty());
            pstm.setDouble(4, obj.getPrice());
            pstm.setDouble(5, obj.getAmount());

            pstm.execute();
            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateCarBrandByID(int id, String name) {

        String sql = "UPDATE  carbrand SET CarBrandName=?"
                + " WHERE CarBrandID = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setInt(2, id);

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "แก้ไขเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateProductBrandByBrandID(int id, String name) {

        String sql = "UPDATE  productbrand SET BrandName=?"
                + " WHERE BrandID = ?";

        PreparedStatement pstm = null;
        try {

            pstm = conn.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setInt(2, id);

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "แก้ไขเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateProductBrandIntoProductByBrandName(String name, String oldname) {

        String sql = "UPDATE  product SET productBrand=?"
                + " WHERE productBrand = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setString(2, oldname);

            pstm.execute();
            pstm.close();

            //JOptionPane.showMessageDialog(null, "แก้ไขเรียบร้อยแล้ว");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateProductByProdID(Product p) {

        String sql = "UPDATE  product SET productName=?,initials=?,Part=?,PartPrivate=?,productBrand=?,productDes=?,costPerUnit=?,pricePerUnit=?,BarcodeName1=?,BarcodeName2=?,BarcodeName3=?,BarcodeName4=?"
                + " WHERE productID = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(13, p.getID());
            pstm.setString(1, p.getName());
            pstm.setString(2, p.getInitials());
            pstm.setString(3, p.getPart());
            pstm.setString(4, p.getPV());
            pstm.setString(5, p.getBrand());
            pstm.setString(6, p.getDes());
            pstm.setDouble(7, p.getCost());
            pstm.setDouble(8, p.getPrice());
            pstm.setString(9, p.getBarcode1());
            pstm.setString(10, p.getBarcode2());
            pstm.setString(11, p.getBarcode3());
            pstm.setString(12, p.getBarcode4());

            pstm.execute();
            pstm.close();

            //JOptionPane.showMessageDialog(null, "แก้ไขเรียบร้อยแล้ว");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateMappingProductbyProdID(Product prod) {

        String sql = "UPDATE  mappingproduct SET partTypeID=?"
                + " WHERE productID = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, prod.getTypeID());
            pstm.setInt(2, prod.getID());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "แก้ไขเรียบร้อยแล้ว");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateGroupTypeByID(int id, String name) {

        String sql = "UPDATE  parttype SET Name=?"
                + " WHERE ID = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, name);
            pstm.setInt(2, id);

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "แก้ไขเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void updateSupplybyID(Supply sp) {

        String sql = "UPDATE  supplier SET Name=?,Address=?,Address2=?,TAX_ID=?,Tel=?"
                + " WHERE ID = ?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, sp.getName());
            pstm.setString(2, sp.getAddress());
            pstm.setString(3, sp.getAddress2());
            pstm.setString(4, sp.getTAX());
            pstm.setString(5, sp.getTEL());
            pstm.setInt(6, sp.getID());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "แก้ไขเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteCarBrandByID(int id) {

        String sql = "DELETE  FROM carbrand WHERE CarBrandID=?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "ลบรายการเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteMappingProductByPK(int proid, int parttypeid, int cargenid) {

        String sql = "DELETE  FROM mappingproduct WHERE productID=? and partTypeID=? and carGenID=?";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, proid);
            pstm.setInt(2, parttypeid);
            pstm.setInt(3, cargenid);

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "ลบรายการเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertCarBrand(CarClass obj) {

        String sql = "INSERT INTO  carbrand(CarBrandID,CarBrandName)"
                + " VALUES(?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, obj.getBrandID());
            pstm.setString(2, obj.getBrand());

            pstm.execute();
            pstm.close();

            // JOptionPane.showMessageDialog(null, "");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertProductBrand(String Brand) {

        String sql = "INSERT INTO  productbrand(BrandName)"
                + " VALUES(?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, Brand);

            pstm.execute();
            pstm.close();

            // JOptionPane.showMessageDialog(null, "");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertPartType(PartGroup obj) {

        String sql = "INSERT INTO  parttype(ID,NAME)"
                + " VALUES(?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, obj.getID());
            pstm.setString(2, obj.getName());

            pstm.execute();
            pstm.close();

            // JOptionPane.showMessageDialog(null, "");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertMappingPartType(ProductType obj) {

        String sql = "INSERT INTO  mappingparttype(ID,PartTypeID,Name,PartGroupName)"
                + " VALUES(?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, obj.getID());
            pstm.setInt(2, obj.getPartGroupID());
            pstm.setString(3, obj.getName());
            pstm.setString(4, obj.getNameGroup());

            pstm.execute();
            pstm.close();

            // JOptionPane.showMessageDialog(null, "");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertMappingProduct(Product obj) {

        String sql = "INSERT INTO  mappingproduct(productID,partTypeID,carGenID)"
                + " VALUES(?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, obj.getID());
            pstm.setInt(2, obj.getTypeID());
            pstm.setInt(3, obj.getCarGenID());

            pstm.execute();
            pstm.close();

            // JOptionPane.showMessageDialog(null, "");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertCarGen(CarClass obj) {

        String sql = "INSERT INTO cargen(CarGenID,CarGenName,Engine,Fuel,Gear,CarBrandID,CarBrandName)"
                + " VALUES(?,?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, obj.getGenID());
            pstm.setString(2, obj.getGen());
            pstm.setString(3, obj.getEngine());
            pstm.setString(4, obj.getFuel());
            pstm.setString(5, obj.getGear());
            pstm.setInt(6, obj.getBrandID());
            pstm.setString(7, obj.getBrand());

            pstm.execute();
            pstm.close();

            // JOptionPane.showMessageDialog(null, "");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertTemp(Product proS) {

        String sql = "INSERT INTO temp (productID,groupType,typeName,productName,initials,Brand,Part"
                + ",partPrivate,Des,costPerUnit,costPerUnit2,PricePerUnit,CarBrandName,CarGenName,locationName,Qty,Discount,Amount)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, proS.getID());
            pstm.setString(2, proS.getGroupName());
            pstm.setString(3, proS.getTypeName());
            pstm.setString(4, proS.getName());
            pstm.setString(5, proS.getInitials());
            pstm.setString(6, proS.getBrand());
            pstm.setString(7, proS.getPart());
            pstm.setString(8, proS.getPV());
            pstm.setString(9, proS.getDes());
            pstm.setDouble(10, proS.getCost());
            pstm.setDouble(11, proS.getCost2());
            pstm.setDouble(12, proS.getPrice());
            pstm.setString(13, proS.getCarBrandName());
            pstm.setString(14, proS.getCarGenName());
            pstm.setString(15, proS.getLocation());
            pstm.setInt(16, proS.getQty());
            pstm.setDouble(17, proS.getDiscount());
            pstm.setDouble(18, proS.getAmount());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "เพิ่มลงตารางการเก็บชั่วคราวแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertProductInventory(Product obj) {

        String sql = "INSERT INTO productinventory(productID,productName,Barcode,LocationID,LocationName,LotDate,invoiceID,CostPerUnit2,Qty)"
                + " VALUES(?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setInt(1, obj.getID());
            pstm.setString(2, obj.getName());
            pstm.setString(3, obj.getBarcode());

            pstm.setInt(4, obj.getLocationID());
            pstm.setString(5, obj.getLocation());
            pstm.setString(6, obj.getDate());
            pstm.setString(7, obj.getInvoiceID());
            pstm.setDouble(8, obj.getCost2());
            pstm.setDouble(9, obj.getQty());
            pstm.execute();
            pstm.close();

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertProductInventoryDetails(Product obj, String sqlx) {

        String sql = sqlx;

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, obj.getInvoiceID());
            pstm.setInt(2, obj.getID());

            pstm.setInt(3, obj.getQty());
            pstm.setDouble(4, obj.getCost2());

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
        String sql = "INSERT INTO supplier(ID,Name,Address,Address2,TAX_ID,Tel)"
                + " VALUES(?,?,?,?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(3, sp.getAddress());
            pstm.setString(4, sp.getAddress2());
            pstm.setString(2, sp.getName());
            pstm.setString(5, sp.getTAX());
            pstm.setString(6, sp.getTEL());
            pstm.setInt(1, sp.getID());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "จัดเก็บชื่อผู้ผลิตเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertProductBrand(ProductBrand pb) {
        String sql = "INSERT INTO ProductBrand(BrandName)"
                + " VALUES(?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, pb.getBrandName());

            pstm.execute();
            pstm.close();

            JOptionPane.showMessageDialog(null, "เพิ่มยี่ห้อเรียบร้อยแล้ว");

        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertInvoice(String invoieId, String supplyName, String LotDate) {

        String sql = "INSERT INTO invoice(InvoiceID,SupplierName,LotDate)"
                + " VALUES(?,?,?)";

        PreparedStatement pstm = null;
        try {
            pstm = conn.prepareStatement(sql);

            pstm.setString(1, invoieId);
            pstm.setString(2, supplyName);
            pstm.setString(3, LotDate);

            pstm.execute();
            pstm.close();

            //JOptionPane.showMessageDialog(null, "จัดเก็บใบกำกับสินค้าเรียบร้อยแล้ว");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
