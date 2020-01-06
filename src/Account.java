import java.io.Serializable;

public abstract class Account implements Serializable {

    private String id;
    private String password;
    private AccountStatus status;
    private AccountType type;
    private Person person;

    // Admin constructor
    public Account(String id, String password, Person person) {
        this.id = id;
        this.password = password;
        this.person = person;
    }
    // User constructor
    public Account(String id, String password, AccountStatus status, AccountType type, Person person) {
        this.id = id;
        this.password = password;
        this.status = status;
        this.type = type;
        this.person = person;
    }

    // For testing
    public Person getPerson() {
        return this.person;
    }

    public String getId() {
        return this.id;
    }
    public String getPw() {
        return this.password;
    }
    public AccountStatus getStatus() {
        return this.status;
    }
    public AccountType getType() {
        return this.type;
    }

}
