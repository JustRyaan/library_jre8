import java.io.Serializable;

public class Address implements Serializable {

    private String address;
    private String town;
    private String postcode;

    public Address(String address, String town, String postcode) {
        this.address = address;
        this.town = town;
        this.postcode = postcode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return this.address + "\n" + this.town + ", " + this.postcode;
    }

}
