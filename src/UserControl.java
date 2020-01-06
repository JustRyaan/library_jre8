import com.lambdaworks.crypto.SCryptUtil;

import java.util.Random;
import java.util.Scanner;

public interface UserControl {
    Scanner in = new Scanner(System.in);

    static String generateID() {
        Random r = new Random();
        int num = r.nextInt(999999);
        return String.format("%06d", num);
    }

    static void addMember(){
        Library.clearScreen();

        // Automatically assign a 6 digit ID
        String id = UserControl.generateID();
        while(DataStore.memberExists(id)) {
            id = UserControl.generateID();
        }
        System.out.println("New ID: " + id);

        // Create password and hash it
        System.out.print("Password: ");
        String pass = in.next();
        String hashedPass = SCryptUtil.scrypt(pass, 16384, 8, 1);

        // Get user details
        System.out.print("Forename: ");
        String fname = in.next();

        System.out.print("Surname: ");
        String sname = in.next();

        System.out.print("Email: ");
        String email = in.next();

        System.out.print("Phone: ");
        String phone = in.next();

        // Select account type
        boolean select = true;
        System.out.print(
                "    1. " + AccountType.FULL.displayName() +
                        "\n    2. " + AccountType.CASUAL.displayName() +
                        "\n    3. " + AccountType.STAFF.displayName() +
                        "\nAccount type: ");
        AccountType type = null;
        while(select) {
            String choice = "0";
            choice = in.next();
            switch (choice.toLowerCase()) {
                case "1":
                case "full":
                case "full member":
                    type = AccountType.FULL;
                    select = false;
                    break;
                case "2":
                case "casual":
                case "casual member":
                    type = AccountType.CASUAL;
                    select = false;
                    break;
                case "3":
                case "staff":
                case "staff member":
                    type = AccountType.STAFF;
                    select = false;
                    break;
                default:
                    System.out.print("Please choose a valid option: ");
            }
        }
        AccountStatus status = AccountStatus.ACTIVE;

        // Staff need less info saved, so we can just create them now
        if(type == AccountType.STAFF) {
            Person sp = new Person(fname,sname,email,phone);
            Member sm = new Member(id,hashedPass,status,type,sp);
            DataStore.addMember(sm);
            System.out.println("\nNew staff user created!\nID: " + id + "  -  Password: " + pass);
        } else { // Everyone else has to get remaining details
            System.out.print("D.O.B (dd/mm/yyyy): ");
        }
    }
}
