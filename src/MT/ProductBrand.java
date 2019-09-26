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
public class ProductBrand {

    /**
     * @return the productBrandID
     */
    public int getProductBrandID() {
        return productBrandID;
    }

    /**
     * @param productBrandID the productBrandID to set
     */
    public void setProductBrandID(int productBrandID) {
        this.productBrandID = productBrandID;
    }

    /**
     * @return the BrandName
     */
    public String getBrandName() {
        return BrandName;
    }

    /**
     * @param BrandName the BrandName to set
     */
    public void setBrandName(String BrandName) {
        this.BrandName = BrandName;
    }
    private int productBrandID;
    private String BrandName;
    
}
