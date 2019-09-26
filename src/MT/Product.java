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
public class Product {

    /**
     * @return the productNameENG
     */
    public String getProductNameENG() {
        return productNameENG;
    }

    /**
     * @param productNameENG the productNameENG to set
     */
    public void setProductNameENG(String productNameENG) {
        this.productNameENG = productNameENG;
    }

    /**
     * @return the engine
     */
    public String getEngine() {
        return engine;
    }

    /**
     * @param engine the engine to set
     */
    public void setEngine(String engine) {
        this.engine = engine;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the Discount
     */
    public double getDiscount() {
        return Discount;
    }

    /**
     * @param Discount the Discount to set
     */
    public void setDiscount(double Discount) {
        this.Discount = Discount;
    }

    /**
     * @return the qty2
     */
  

    private String Barcode1 = "";
    private String Barcode2 = "";
    private String Barcode3 = "";
    private String Barcode4 = "";
    private String Name = "";
    private String initials = "";
    private String Brand = "";
    private String Part;
    private String PV;
    private String LotDate;
    private int qty;
    private int qty2;
    private String invoiceID;
    private double Cost;
    private double Price;
    private double Cost2;
    private String Location = "";
    private String carGenName = "";
    private String typeName = "";
    private String Des = "";
    private int LocationID;
    private int ID;
    private int PartID;
    private int TypeID;
    private int carGenID;
    private String groupName = "";
    private String carBrandName = "";
    private String Barcode = "";
    private double Amount;
    private String Fuel = "";
    private double Discount;
    private String engine;
    private String productNameENG;
  public int getQty2() {
        return qty2;
    }

    /**
     * @param qty2 the qty2 to set
     */
    public void setQty2(int qty2) {
        this.qty2 = qty2;
    }

    /**
     * @return the invoiceID
     */
    public String getInvoiceID() {
        return invoiceID;
    }

    /**
     * @param invoiceID the invoiceID to set
     */
    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    /**
     * @return the Barcode
     */
    public String getBarcode() {
        return Barcode;
    }

    /**
     * @param Barcode the Barcode to set
     */
    public void setBarcode(String Barcode) {
        this.Barcode = Barcode;
    }
    
    public void setName(String Name) {
        this.Name = Name;
    }

    public void setTypeName(String Name) {
        this.typeName = Name;
    }

    public void setBrand(String Brand) {
        this.Brand = Brand;
    }

    public void setPart(String Part) {
        this.Part = Part;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public void setTypeID(int id) {
        this.TypeID = id;
    }

    public void setPartID(int partid) {
        this.PartID = partid;
    }

    public void setPV(String PV) {
        this.PV = PV;
    }

    public void setLotDate(String date) {
        this.LotDate = date;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setCost(double cost) {
        this.Cost = cost;
    }

    public void setCost2(double cost2) {
        this.Cost2 = cost2;
    }

    public void setLocation(String loc) {
        this.Location = loc;
    }

    public void setLocationID(int locid) {
        this.LocationID = locid;
    }

    public void setDes(String des) {
        this.Des = des;

    }

    public void setPrice(double price) {
        this.Price = price;
    }

    public String getName() {
        return this.Name;
    }

    public String getBrand() {
        return this.Brand;
    }

    public String getPart() {
        return this.Part;
    }

    public String getPV() {
        return this.PV;
    }

    public int getID() {
        return this.ID;
    }

    public int getPartID() {
        return this.PartID;
    }

    public String getDate() {
        return this.LotDate;
    }

    public int getQty() {
        return this.qty;
    }

    public double getCost() {
        return this.Cost;
    }

    public double getPrice() {
        return this.Price;
    }

    public double getCost2() {
        return this.Cost2;
    }

    public String getLocation() {
        return this.Location;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public int getLocationID() {
        return LocationID;
    }

    public String getDes() {
        return this.Des;
    }

    public int getTypeID() {
        return this.TypeID;
    }

    /**
     *
     */
    public String getBarcode1() {
        return Barcode1;
    }

    /**
     *
     */
    public void setBarcode1(String Barcode1) {
        this.Barcode1 = Barcode1;
    }

    /**
     *
     */
    public String getBarcode2() {
        return Barcode2;
    }

    /**
     *
     */
    public void setBarcode2(String Barcode2) {
        this.Barcode2 = Barcode2;
    }

    /**
     *
     */
    public String getBarcode3() {
        return Barcode3;
    }

    /**
     *
     */
    public void setBarcode3(String Barcode3) {
        this.Barcode3 = Barcode3;
    }

    /**
     *
     */
    public String getBarcode4() {
        return Barcode4;
    }

    /**
     *
     */
    public void setBarcode4(String Barcode4) {
        this.Barcode4 = Barcode4;
    }

    /**
     * @return the initials
     */
    public String getInitials() {
        return initials;
    }

    /**
     * @param initials the initials to set
     */
    public void setInitials(String initials) {
        this.initials = initials;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the carGenName
     */
    public String getCarGenName() {
        return carGenName;
    }

    /**
     * @param carGenName the carGenName to set
     */
    public void setCarGenName(String carGenName) {
        this.carGenName = carGenName;
    }

    /**
     * @return the carGenID
     */
    public int getCarGenID() {
        return carGenID;
    }

    /**
     * @param carGenID the carGenID to set
     */
    public void setCarGenID(int carGenID) {
        this.carGenID = carGenID;
    }

    /**
     * @return the carBrandName
     */
    public String getCarBrandName() {
        return carBrandName;
    }

    /**
     * @param carBrandName the carBrandName to set
     */
    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public void calAmount(double dis, int qty, double price) {
        this.setAmount(((100 - dis) * (qty * price)) / 100);
    }

    public double getAmount() {
        return this.Amount;
    }
}
