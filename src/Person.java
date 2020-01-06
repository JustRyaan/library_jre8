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

    // Staff Constructor
    public Person(String forename, String surname, String email, String phone) {
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }
    // Member Constructor
    public Person(String forename, String surname, String phone, LocalDate DOB, Address address) {
        this.forename = forename;
        this.surname = surname;
        this.phone = phone;
        this.DOB = DOB;
        this.address = address;
    }

    // Getter Methods
    public String getForename() {
        return this.forename;
    }
    public String getSurname() {
        return this.surname;
    }
    public String getFullName() {
        return this.forename + " " + this.surname;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPhone() {
        return this.phone;
    }
    public String getDOB() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return this.DOB.format(formatter);
    }
    public Address getAddress() {
        return this.address;
    }

    // Setter Methods
    public void setName(String forename, String surname) {
        this.forename = forename;
        this.surname = surname;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setDOB(String DOB) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.DOB = LocalDate.parse(DOB, formatter);
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "A person.";
    }

}
