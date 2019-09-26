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
public class ProductSupply {

    private String BarcodeID;
    private String ProductName;
    private int Qty;
    private String invoiceID;
    private double CostPerUnit;
    private double CostPerUnit2;
    private double Price;
    private double Discount;
    private double Amount;
    private int LocationID;
    private String supplyName;
    private String Date;
    private String typeName;
    private int id;
    private int TypeID;
    private String getBarcodeID;

    public void setID(int id) {
        this.id = id;
    }

    public void setBarcodeID(String barcode) {
        this.BarcodeID = barcode;
    }

    public String getBarcodeID() {
        return BarcodeID;
    }

    public void setTypeID(int typeid) {
        this.TypeID = typeid;
    }

    public int getTypeID() {
        return this.TypeID;
    }

    public void setTypeName(String name) {
        this.typeName = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setAmount(double amount) {
        this.Amount = amount;
    }

    public double getAmount() {
        return this.Amount;
    }

    public void setDiscount(double dis) {
        this.Discount = dis;
    }

    public void calAmount(double dis, int qty, double price) {
        this.Amount = ((100 - dis) * (qty * price)) / 100;
    }

    public void setProductName(String name) {
        this.ProductName = name;
    }

    public void setInvoiceID(String id) {
        this.invoiceID = id;
    }

    public void setSupplyName(String name) {
        this.supplyName = name;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public void setQty(int qty) {
        this.Qty = qty;
    }

    public void setCostPerUnit(double cost) {
        this.CostPerUnit = cost;
    }

    public void setPriceUnit(double price) {
        this.Price = price;
    }

    public void setCostPerUnit2(double cost2) {
        this.CostPerUnit2 = cost2;
    }

    public void setLocationID(int id) {
        this.LocationID = id;
    }

    public int getID() {
        return this.id;
    }

    public String getSuplyName() {
        return this.supplyName;
    }

    public String getDate() {
        return this.Date;
    }

    public String getProductName() {
        return this.ProductName;
    }

    public int getQty() {
        return this.Qty;
    }

    public int getLocationID() {
        return this.LocationID;
    }

    public double getCostperUnit() {
        return this.CostPerUnit;
    }

    public double getDiscount() {
        return this.Discount;
    }

    public double getCostPerUnit2() {
        return this.CostPerUnit2;
    }

    public double getPricePerUnit() {
        return this.Price;
    }

    public String getInvoiceID() {
        return this.invoiceID;
    }

}
