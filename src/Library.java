import Data.*;
import com.lambdaworks.crypto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

// Admin login
// 123456:testing

public class Library {
    private static Person loggedInUser = null;
    private static Scanner in = new Scanner(System.in);
    private static HashMap<String, List<Book>> bookTitles;

    public static void main(String[] args) throws Exception {
        Book book = new Book("1234","129124","ngnk","jksdn", Genre.ADVENTURE,123,"8239hf");


        DataStore.restore();
        DataStore.verify();
//        Admin.addMember();
        showWelcome();
        if(loggedInUser != null){
            showMenu();
        }
//        String date = "19/03/1996";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd LLLL yyyy");
//        LocalDate newDate = LocalDate.parse(date,formatter);
//        System.out.println(newDate.format(formatter2));
//

//        String password = "testing";
//        String hashedPass = SCryptUtil.scrypt(password, 16, 16, 16);
//
//        Person rootAdmin = new Person("Admin", "User", "admin@thelibrary.com","514289");
//
//        Admin root = new Admin("123456", hashedPass, rootAdmin);
//
//        DataStore.addAdmin(root);
//
//        // Create Person
//        Person testPerson = new Person("Ryan", "Mitchell",
//                "me@rpmitchell.com", "07887841835");
//
//        Member testMember = new Member("1868588", "hello123", AccountStatus.ACTIVE, AccountType.FULL,  testPerson);
//
//        DataStore.addMember(testMember);

//        System.out.println(adminLogin("123456", "testing"));

        DataStore.store();

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
                    Person user = memberLogin(getID(), getPw());
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
                    Person admin = adminLogin(getID(), getPw());
                    if (admin != null) {
                        Library.setLoggedInUser(admin);
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
                        case "yes":
                            System.out.println("Okay, see you soon.");
                            menu = false;
                            break;
                        case "n":
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
    public static void showMenu() {
        boolean menu = true;
        while(menu) {
            clearScreen();
            System.out.print(
                            "\n===============================================" +
                            "\nWelcome, " + loggedInUser.getForename() + "!" +
                                    " What would you like to do?" +
                            "\n===============================================" +
                            "\n 1. Search Library" +
                            "\n 2. Admin Login" +
                            "\n 3. Exit" +
                            "\nPlease choose an option: ");
            int choice = 0;
            choice = in.nextInt();

            switch (choice) {

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


    public static void setLoggedInUser(Person person) {
        loggedInUser = person;
    }

    // Returns logged in user
    public static Person adminLogin(String id, String password) {
        if (!validateAdmin(id, password)) {
            return null;
        }
        return DataStore.getAdmin(id).getPerson();
    }
    public static Person memberLogin(String id, String password) {
        if (!validateMember(id, password)) {
            return null;
        }
        return DataStore.getMember(id).getPerson();
    }

    // Checks ID and Password against stored users
    public static boolean validateAdmin(String id, String password) {
        if (!DataStore.adminExists(id)) {
            return false;
        }
        return SCryptUtil.check(password, DataStore.getAdmin(id).getPw());
    }
    public static boolean validateMember(String id, String password) {
        if (!DataStore.memberExists(id)) {
            return false;
        }
        return SCryptUtil.check(password, DataStore.getMember(id).getPw());
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
