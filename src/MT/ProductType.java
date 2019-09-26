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
public class ProductType {

    private int ID;
    private String NameType;
    private int PartGroupID;
    private String NameGroup;

    public void setID(int id) {
        ID = id;
    }

    public void setName(String str) {
        NameType = str;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return NameType;
    }

    /**
     * @return the PartGroupID
     */
    public int getPartGroupID() {
        return PartGroupID;
    }

    /**
     * @param PartGroupID the PartGroupID to set
     */
    public void setPartGroupID(int PartGroupID) {
        this.PartGroupID = PartGroupID;
    }

    /**
     * @return the NameGroup
     */
    public String getNameGroup() {
        return NameGroup;
    }

    /**
     * @param NameGroup the NameGroup to set
     */
    public void setNameGroup(String NameGroup) {
        this.NameGroup = NameGroup;
    }

}
