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
public class Location {

    private int locationID;
    private String locationName;

    public void setLocationID(int id) {
        this.locationID = id;
    }

    public void setLocationName(String name) {
        this.locationName = name;
    }

    public int getLocationID() {
        return this.locationID;
    }

    public String getLocationName() {
        return this.locationName;
    }

}
