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
public class Supply {

    private int ID;
    private String Name;
    private String Add;
    private String Add2;
    private String Tax;
    private String Tel;

    public void setID(int id) {
        this.ID = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setAddress(String add) {
        this.Add = add;
    }

    public void setAddress2(String add2) {
        this.Add2 = add2;
    }

    public void setTAX(String tax) {
        this.Tax = tax;
    }

    public void setTEL(String tel) {
        this.Tel = tel;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.Name;
    }

    public String getAddress() {
        return this.Add;
    }

    public String getAddress2() {
        return this.Add2;
    }

    public String getTAX() {
        return this.Tax;
    }

    public String getTEL() {
        return this.Tel;
    }

}
