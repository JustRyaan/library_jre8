package Data;

import java.io.Serializable;

public class Address implements Serializable {
    private String address;
    private String town;
    private String county;
    private String country = "Scotland";
    private String postcode;

    public String getAddress() {
        return this.address;
    }
    public String getTown() {
        return this.town;
    }
    public String getCounty() {
        return this.county;
    }
    public String getCountry() {
        return this.country;
    }
    public String getPostcode() {
        return this.postcode;
    }
    public String getFullAddress() {
        return this.address + "\n" + this.town + ", " + this.county + "\n" + this.country + " " + this.postcode;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setTown(String town) {
        this.town = town;
    }
    public void setCounty(String county) {
        this.county = county;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
