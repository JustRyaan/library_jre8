import com.lambdaworks.crypto.*;

import java.util.EnumSet;
import java.util.Scanner;

// Admin login
// 123456:testing

// Regular login
// 358929:hello123

public class Library {
    private static Member loggedInUser = null;
    private static Admin loggedInAdmin = null;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        // Deserialize on startup
        UserStore.restore();
        Catalogue.restore();
        Reservation.restore();
        Lending.restore();

        // For testing
        Person rootAdmin = new Person("Admin", "User", "admin@thelibrary.com", "591");
        String hashedPass = SCryptUtil.scrypt("admin", 16384, 8, 1);
        Admin root = new Admin("admin", hashedPass, rootAdmin);
        UserStore.addAdmin(root);

        // TODO: main control loop
        // Main control loop
        showWelcome();
        if(loggedInUser != null){
            showUserMenu();
        } else if (loggedInAdmin != null){
            showAdminMenu();
        }



        UserStore.store();
        Catalogue.store();
        Reservation.store();
        Lending.store();

    }

    // To display at startup or when a user logs out
    public static void showWelcome() {
        boolean menu = true;
        while (menu) {
            clearScreen();
            System.out.print(
                    "\n==============================" +
                            "\nWelcome to Glencaldy Library!" +
                            "\n==============================" +
                            "\n 1. Member Login" +
                            "\n 2. Admin Login" +
                            "\n 3. Exit" +
                            "\nPlease choose an option: ");
            int choice = 0;
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    clearScreen();
                    Member user = memberLogin(getID(), getPw());
                    if (user != null) {
                        Library.setLoggedInUser(user);
                        menu = false;
                        break;
                    } else {
                        System.out.println("Username or password incorrect, please try again.");
                        sleepFor(2000);
                    }
                    break;
                case 2:
                    clearScreen();
                    Admin admin = adminLogin(getID(), getPw());
                    if (admin != null) {
                        Library.setLoggedInAdmin(admin);
                        menu = false;
                        break;
                    } else {
                        System.out.println("Username or password incorrect, please try again.");
                        sleepFor(2000);
                    }
                    break;
                case 3:
                    clearScreen();
                    System.out.println("Are you sure you want to exit? y/n");
                    String decision = in.next();
                    switch (decision.toLowerCase()) {
                        case "y":
                        case "Y":
                        case "yes":
                            System.out.println("Okay, see you soon.");
                            menu = false;
                            break;
                        case "n":
                        case "N":
                        case "no":
                            System.out.println("Very well, returning to menu...");
                            sleepFor(1000);
                            break;
                        default:
                            System.out.print("Please choose a valid option.");
                            sleepFor(2000);
                    }
                    break;
                default:
                    System.out.println("Please choose a valid option.");
                    sleepFor(2000);
                    break;
            }
        }
    }

    // To display to logged in users
    public static void showUserMenu() {
        boolean menu = true;
        while(menu) {
            clearScreen();
            System.out.print(
                            "\n===============================================" +
                            "\nWelcome, " + loggedInUser.getPerson().getForename() + "!" +
                                    " What would you like to do?" +
                            "\n===============================================" +
                            "\n 1. Search Library" +
                            "\n 2. View Loans" +
                            "\n 3. View Reservations" +
                            "\n 4. Change Password" +
                            "\n 5. Logout" +
                            "\nPlease choose an option: ");
            int choice = 0;
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                // TODO: finish implementing member menu
            }

        }
    }

    // To display to logged in admins
    public static void showAdminMenu() {
        boolean menu = true;
        while(menu) {
            clearScreen();
            System.out.print(
                    "\n===============================================" +
                            "\nWelcome, " + loggedInAdmin.getPerson().getForename() + "!" +
                            " What would you like to do?" +
                            "\n===============================================" +
                            "\n 1. Add Member" +
                            "\n 2. Edit Member" +
                            "\n 3. Remove Member" +
                            "\n 4. Add Book" +
                            "\n 5. Add Journal" +
                            "\n 6. Add Video" +
                            "\n 7. Add CD" +
                            "\n 8. Edit Book" +
                            "\n 9. Edit Journal" +
                            "\n 10. Edit Video" +
                            "\n 11. Edit CD" +
                            "\n 12. Add Admin" +
                            "\n 13. Edit Admin" +
                            "\n 14. Remove Admin" +
                            "\n 15. Change Password" +
                            "\n 16. Logout" +
                            "\nPlease choose an option: ");

            int choice = 0;
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    Admin.addMember();
                    break;
                case 2:
                    clearScreen();
                    System.out.println("Enter the user's ID that you wish to edit: ");
                    String editid = in.nextLine();
                    if(UserStore.memberExists(editid)) {
                        Admin.editMember(editid);
                    } else {
                        System.out.println("That user does not exist!");
                        sleepFor(1500);
                    }
                    break;
                case 3:
                    clearScreen();
                    System.out.println("WARNING: DELETES WITHOUT CONFIRMATION!\nEnter the user's ID to remove or" +
                            "leave empty to return to menu: ");
                    String removeid = in.nextLine();
                    if(UserStore.memberExists(removeid)) {
                        Admin.removeMember(removeid);
                    } else {
                        System.out.println("That user does not exist!");
                        sleepFor(1500);
                    }
                case 4:
                    Admin.addBook();
                    break;
                case 5:
                    Admin.addJournal();
                    break;
                case 6:
                    Admin.addVideo();
                    break;
                case 7:
                    Admin.addCD();
                    break;
                case 8:
                    // TODO: finish implementing admin menu
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                default:
                    System.out.println("Please enter a valid option!");
            }
        }
    }

    // For re-usability
    public static String getID() {
        System.out.print("\nUsername: ");
        String id = in.next();
        return id;
    }
    public static String getPw() {
        System.out.print("Password: ");
        String pw = in.next();
        return pw;
    }


    public static void setLoggedInUser(Member member) {
        loggedInUser = member;
    }
    public static void setLoggedInAdmin(Admin admin) {
        loggedInAdmin = admin;
    }

    // Returns logged in user
    public static Admin adminLogin(String id, String password) {
        if (!validateAdmin(id, password)) {
            return null;
        }
        // TODO: add logging to admin logins
        return UserStore.getAdmin(id);
    }
    public static Member memberLogin(String id, String password) {
        if (!validateMember(id, password)) {
            return null;
        }
        // TODO: add logging to user logins
        return UserStore.getMember(id);
    }

    // Checks ID and Password against stored users
    public static boolean validateAdmin(String id, String password) {
        if (!UserStore.adminExists(id)) {
            return false;
        }
        return SCryptUtil.check(password, UserStore.getAdmin(id).getPw());
    }
    public static boolean validateMember(String id, String password) {
        if (!UserStore.memberExists(id)) {
            return false;
        }
        return SCryptUtil.check(password, UserStore.getMember(id).getPw());
    }

    // Populates the databases
    public static void populateUserData() {
        // Dummy data for first time startup; all users share the password "password", IDs are full names (eg joebloggs,
        // janedoe etc.). All non-staff members share the same address.

        String hashedUserPass = SCryptUtil.scrypt("password", 16384, 9, 1);
        Address address = new Address("1 Main Road", "Edinburgh", "EH1 1AA");

        // Staff members
        Person joe = new Person("Joe", "Bloggs", "jbloggs@thelibrary.com", "784");
        Member staff1 = new Member("joebloggs", hashedUserPass, AccountStatus.ACTIVE, AccountType.STAFF, joe);
        UserStore.addMember(staff1);
        Person jane = new Person("Jane", "Doe", "jdoe@thelibrary.com", "791");
        Member staff2 = new Member("janedoe", hashedUserPass, AccountStatus.ACTIVE, AccountType.STAFF, jane);
        UserStore.addMember(staff2);
        Person alan = new Person("Alan", "Fresco", "afresco@thelibrary.com", "763");
        Member staff3 = new Member("alanfresco", hashedUserPass, AccountStatus.ACTIVE, AccountType.STAFF, alan);
        UserStore.addMember(staff3);

        // Full members
        Person fletch = new Person("Fletch", "Skinner", "07700 900821", "21/07/1973", address);
        Member full1 = new Member("fletchskinner", hashedUserPass, AccountStatus.BLOCKED, AccountType.FULL, fletch);
        UserStore.addMember(full1);
        Person caspian = new Person("Caspian", "Bellevedere", "013149 60478", "12/11/1982", address);
        Member full2 = new Member("caspianbellevedere", hashedUserPass, AccountStatus.ACTIVE, AccountType.FULL, caspian);
        UserStore.addMember(full2);
        Person russel = new Person("Russel", "Sprout", "01592 569910", "03/02/1999", address);
        Member full3 = new Member("russelsprout", hashedUserPass, AccountStatus.ACTIVE, AccountType.FULL, russel);
        UserStore.addMember(full3);

        // Casual members
        Person joss = new Person("Joss", "Eagle", "07543 182748", "19/10/1963", address);
        Member casual1 = new Member("fletchskinner", hashedUserPass, AccountStatus.ACTIVE, AccountType.CASUAL, joss);
        UserStore.addMember(casual1);
        Person malcolm = new Person("Malcolm", "Function", "07721984023", "12/03/2001", address);
        Member casual2 = new Member("malcolmfunction", hashedUserPass, AccountStatus.ACTIVE, AccountType.CASUAL, malcolm);
        UserStore.addMember(casual2);
        Person nathaneal = new Person("Nathaneal", "Down", "01314 95028", "16/05/1993", address);
        Member casual3 = new Member("nathanealdown", hashedUserPass, AccountStatus.CLOSED, AccountType.CASUAL, nathaneal);
        UserStore.addMember(casual3);

    }
    public static void populateMediaData() {
        // TODO: populate media data at startup
    }

    // UX Stuff
    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
    public static void sleepFor(int msecs) {
        try {
            Thread.sleep(msecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }
}
