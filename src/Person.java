import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person implements Serializable {
    private String forename;
    private String surname;
    private String email;
    private String phone;
    private LocalDate DOB;
    private Address address;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    // Staff Constructor
    public Person(String forename, String surname, String email, String phone) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }
    // Member Constructor
    public Person(String forename, String surname, String phone, String DOB, Address address) {
        this.forename = forename;
        this.surname = surname;
        this.phone = phone;
        this.DOB = LocalDate.parse(DOB, formatter);
        this.address = address;
    }

    // Getter Methods
    public String getForename() {
        return this.forename;
    }
    public String getFullName() {
        return this.forename + " " + this.surname;
    }
    public Address getAddress() {
        return this.address;
    }

    // Setter Methods
    public void setForename(String forename) {
        this.forename = forename;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
//    public void setDOB(String DOB) {
//        this.DOB = LocalDate.parse(DOB, formatter);
//    }

    @Override
    public String toString() {
        return "A person.";
    }

}
