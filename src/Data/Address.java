package Data;

import java.io.Serializable;

public class Address implements Serializable {
    private String address;
    private String town;
    private String county;
    private String country = "Scotland";
    private String postcode;

    private String getAddress() {
        return this.address;
    }
    private String getTown() {
        return this.town;
    }
    private String getCounty() {
        return this.county;
    }
    private String getCountry() {
        return this.country;
    }
    private String getPostcode() {
        return this.postcode;
    }
    private String getFullAddress() {
        return this.address + "\n" + this.town + ", " + this.county + "\n" + this.country + " " + this.postcode;
    }

    private void setAddress(String address) {
        this.address = address;
    }
    private void setTown(String town) {
        this.town = town;
    }
    private void setCounty(String county) {
        this.county = county;
    }
    private void setCountry(String country) {
        this.country = country;
    }
    private void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
