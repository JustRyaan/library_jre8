import com.lambdaworks.crypto.SCryptUtil;

import java.io.Serializable;
import java.util.Scanner;

public abstract class Account implements Serializable {

    private String id;
    private String password;
    private AccountStatus status;
    private AccountType type;
    private Person person;
    protected static Scanner in = new Scanner(System.in);

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
    public String getPassword() {
        return this.password;
    }
    public AccountType getType() {
        return this.type;
    }

    public void setPassword(String password) {
        this.password = SCryptUtil.scrypt(password, 16384, 8, 1);
    }

    public boolean updatePassword() {
        System.out.print("Enter your current password: ");
        String currentPass = in.nextLine();
        if(SCryptUtil.check(currentPass, this.getPassword())) {
            System.out.print("Enter new password: ");
            String newPass = in.nextLine();
            System.out.print("Confirm new password: ");
            String newPass2 = in.nextLine();
            if(newPass.equals(newPass2)) {
                this.setPassword(newPass);
                System.out.println("Password updated!");
                Library.sleepFor(2000);
                return true;
            } else {
                System.out.println("Passwords don't match. Please try again.");
            }
        } else {
            System.out.println("Incorrect password. Please try again.");
        }
        Library.sleepFor(2000);
        return false;
    }

    public abstract int getLoansAvailable();
    public abstract void editDetails();
    public abstract void addMember();
    public abstract void addMedia();
    public abstract void showEditMenu();
}
