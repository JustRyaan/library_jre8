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

    @Override
    public String toString() {
        return this.address + "\n" + this.town + ", " + this.postcode;
    }

}
