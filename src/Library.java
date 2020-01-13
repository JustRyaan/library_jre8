import com.lambdaworks.crypto.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Library {
    private static Account loggedInUser = null;
    private static boolean active = true;
    private static LocalDateTime sessionStart;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        start();

        // Create and add admin (will only run once)
        if(!UserStore.adminExists("admin")) {
            Person rootAdmin = new Person("Admin", "User", "admin@thelibrary.com", "591");
            String hashedPass = SCryptUtil.scrypt("admin", 16384, 8, 1);
            Admin root = new Admin("admin", hashedPass, rootAdmin);
            UserStore.addAdmin(root);
        }

        // Main control loop
        do {
            showWelcome();
            if(loggedInUser != null){
                if(loggedInUser.getClass().getName().equals("Admin")) {
                    showAdminMenu();
                } else if(loggedInUser.getClass().getName().equals("Member")) {
                    showUserMenu();
                }
            }
        } while(active);

        stop();

    }

    // To display at startup or when a user logs out
    public static void showWelcome() {
        boolean menu = true;
        while (menu) {
            clearScreen();
            System.out.print(
                "\n===============================================" +
                "\nWelcome to Glencaldy Library!" +
                "\n===============================================" +
                "\n 1. Member Login" +
                "\n 2. Admin Login" +
                "\n 3. Exit" +
                "\nPlease choose an option: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    clearScreen();
                    Member member = memberLogin(getID(), getPw());
                    if (member != null) {
                        Library.login(member);
                        menu = false;
                        break;
                    } else {
                        System.out.println("Username or password incorrect, please try again.");
                        sleepFor(2000);
                    }
                    break;
                case "2":
                    clearScreen();
                    Admin admin = adminLogin(getID(), getPw());
                    if (admin != null) {
                        Library.login(admin);
                        menu = false;
                        break;
                    } else {
                        System.out.println("Username or password incorrect, please try again.");
                        sleepFor(2000);
                    }
                    break;
                case "3":
                    clearScreen();
                    System.out.print("Are you sure you want to exit? y/n: ");
                    String decision = in.next().toLowerCase();
                    in.nextLine();
                    switch (decision.substring(0, 1)) {
                        case "y":
                            clearScreen();
                            System.out.println("Okay, shutting down...\n");
                            active = false;
                            menu = false;
                            break;
                        case "n":
                            System.out.println("Very well, returning to menu...");
                            sleepFor(1000);
                            break;
                        default:
                            System.out.print("Please choose a valid option.");
                            sleepFor(2000);
                            break;
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
            String name = loggedInUser.getPerson().getFullName();
            System.out.print(
                "\n=====================================" + printLines(name) +
                "\nWelcome, " + name + "!" + " What would you like to do?" +
                "\n=====================================" + printLines(name) +
                "\n 1. Search Library" +
                "\n 2. View Loans" +
                "\n 3. View Reservations" +
                "\n 4. Edit Account" +
                "\n 5. View Login History" +
                "\n 6. Logout" +
                "\nPlease choose an option: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    showSearchMenu();
                    break;
                case "2":
                    // View Loans
                    clearScreen();
                    Lending.listLoans(loggedInUser.getId());
                    System.out.println("\nRemaining loans available: " + loggedInUser.getLoansAvailable());
                    System.out.println("Press enter to continue... ");
                    in.nextLine();
                    break;
                case "3":
                    // View Reservations
                    clearScreen();
                    Reservation.listReservations(loggedInUser.getId());
                    System.out.println("\nPress enter to continue... ");
                    in.nextLine();
                    break;
                case "4":
                    // Edit Account
                    loggedInUser.editDetails();
                    break;
                case "5":
                    // View Login History
                    UserStore.readLog(loggedInUser.getId());
                    break;
                case "6":
                    logout();
                    menu = false;
                    break;
                default:
                    System.out.println("Please choose a valid option.");
                    sleepFor(2000);
                    break;
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
                "\nWelcome, " + loggedInUser.getPerson().getFullName() + "!" +
                " What would you like to do?" +
                "\n===============================================" +
                "\n 1. Process Loan" +
                "\n 2. Process Return" +
                "\n 3. Add Media" +
                "\n 4. Add a Member" +
                "\n 5. Edit Media" +
                "\n 6. Edit Account Details" +
                "\n 7. Search Library" +
                "\n 8. View Reports" +
                "\n 9. Logout" +
                "\nPlease choose an option: ");

            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("\nEnter member ID: ");
                    Member loaner;
                    String loanerID = in.nextLine();
                    if(UserStore.memberExists(loanerID)){
                        loaner = UserStore.getMember(loanerID);
                    } else {
                        System.out.println("Member doesn't exist, try again.");
                        sleepFor(2000);
                        break;
                    }
                    System.out.print("Enter stock ID: ");
                    String stockkey = in.nextLine().toUpperCase();
                    if(!Catalogue.checkStockID(stockkey)){
                        System.out.println("No stock found matching that ID, try again.");
                        sleepFor(2000);
                        break;
                    }
                    if(loaner.getLoansAvailable() > 0) {
                        loaner.decrementLoanAvailability();
                        if(Lending.lendItem(stockkey, loanerID)) UserStore.updateMember(loaner);
                        System.out.println("Press enter to continue...");
                        in.nextLine();
                    }
                    break;
                case "2":
                    System.out.print("\nEnter member ID: ");
                    Member returnee;
                    String returneeID = in.nextLine();
                    if(UserStore.memberExists(returneeID)){
                        returnee = UserStore.getMember(returneeID);
                    } else {
                        System.out.println("Member doesn't exist, try again.");
                        sleepFor(2000);
                        break;
                    }
                    System.out.print("Enter stock ID: ");
                    String stockID = in.nextLine();
                    if(Lending.returnItem(stockID, returneeID)) {
                        returnee.addLoanAvailability();
                        UserStore.updateMember(returnee);
                    }
                    System.out.println("Press enter to continue...");
                    in.nextLine();
                    break;
                case "3":
                    clearScreen();
                    loggedInUser.addMedia();
                    break;
                case "4":
                    clearScreen();
                    loggedInUser.addMember();
                    break;
                case "5":
                    clearScreen();
                    loggedInUser.showEditMenu();
                    break;
                case "6":
                    clearScreen();
                    loggedInUser.editDetails();
                    UserStore.updateAdmin((Admin) loggedInUser);
                    break;
                case "7":
                    showSearchMenu();
                    break;
                case "8":
                    System.out.println("\n1. Loans    2. Reservations    3. All logs    4. Own logs    5. Fines");
                    System.out.print("Please choose an option: ");
                    String lr = in.nextLine();
                    switch (lr){
                        case "1":
                            clearScreen();
                            System.out.print(
                                    "\n===============================================" +
                                    "\n All loans" +
                                    "\n===============================================");
                            Lending.listLoans("all");
                            System.out.println("\nPress enter to continue...");
                            in.nextLine();
                            break;
                        case "2":
                            clearScreen();
                            System.out.print(
                                    "\n===============================================" +
                                    "\n All reservations" +
                                    "\n===============================================");
                            Reservation.listReservations("all");
                            System.out.println("\nPress enter to continue... ");
                            in.nextLine();
                            break;
                        case "3":
                            // User Logs
                            UserStore.readLog("master");
                            break;
                        case "4":
                            UserStore.readLog(loggedInUser.getId());
                            break;
                        case "5":
                            clearScreen();
                            System.out.print(
                                "\n===============================================" +
                                "\n All fines" +
                                "\n===============================================");
                            Lending.listFines();
                            System.out.println("\nPress enter to continue... ");
                            in.nextLine();
                            break;
                        default:
                            System.out.println("Invalid choice, please try again.");
                            break;
                    }
                    break;
                case "9":
                    logout();
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option!");
                    sleepFor(2000);
                    break;
            }
        }
    }

    // Search selector menu
    public static void showSearchMenu() {
        boolean menu = true;
        while(menu) {
            clearScreen();
            System.out.print(
                    "\n===============================================" +
                    "\nSearch Library " +
                    "\n===============================================" +
                    "\n 1. Search Books" +
                    "\n 2. Search Journals" +
                    "\n 3. Search Videos" +
                    "\n 4. Search CDs" +
                    "\n 5. Go back... " +
                    "\nPlease choose an option: ");

            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    searchBookMenu();
                    break;
                case "2":
                    searchJournalMenu();
                    break;
                case "3":
                    searchVideoMenu();
                    break;
                case "4":
                    searchCDMenu();
                    break;
                case "5":
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    sleepFor(2000);
                    break;
            }
        }
    }

    // Gets and parses a search query
    public static String getSearchQuery() {
        System.out.print("Search: ");
        String query = in.nextLine();
        return query.replaceAll("\\p{Punct}", "").toLowerCase();
    }

    // Menus for media searches
    public static void searchBookMenu() {
        boolean menu = true;
        while(menu) {
            clearScreen();
            System.out.print(
                    "\n===============================================" +
                    "\nSearch Books " +
                    "\n===============================================" +
                    "\n 1. Search by Title" +
                    "\n 2. Search by Author" +
                    "\n 3. Search by ISBN" +
                    "\n 4. Search by Publisher" +
                    "\n 5. Go back... " +
                    "\nPlease choose an option: ");

            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    processSearch(Catalogue.searchBookTitle(getSearchQuery()));
                    break;
                case "2":
                    processSearch(Catalogue.searchBookAuthor(getSearchQuery()));
                    break;
                case "3":
                    processSearch(Catalogue.searchBookISBN(getSearchQuery()));
                    break;
                case "4":
                    processSearch(Catalogue.searchBookPublisher(getSearchQuery()));
                    break;
                case "5":
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    sleepFor(2000);
                    break;
            }
        }
    }
    public static void searchJournalMenu() {
        boolean menu = true;
        while(menu) {
            clearScreen();
            System.out.print(
                    "\n===============================================" +
                    "\nSearch Journals " +
                    "\n===============================================" +
                    "\n 1. Search by Title" +
                    "\n 2. Search by ISSN" +
                    "\n 3. Search by Publisher" +
                    "\n 4. Go back... " +
                    "\nPlease choose an option: ");

            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    processSearch(Catalogue.searchJournalTitle(getSearchQuery()));
                    break;
                case "2":
                    processSearch(Catalogue.searchJournalISSN(getSearchQuery()));
                    break;
                case "3":
                    processSearch(Catalogue.searchJournalPublisher(getSearchQuery()));
                    break;
                case "4":
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    sleepFor(2000);
                    break;
            }
        }
    }
    public static void searchVideoMenu() {
        boolean menu = true;
        while(menu) {
            clearScreen();
            System.out.print(
                    "\n===============================================" +
                    "\nSearch Videos " +
                    "\n===============================================" +
                    "\n 1. Search by Title" +
                    "\n 3. Search by Publisher" +
                    "\n 3. Go back... " +
                    "\nPlease choose an option: ");

            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    processSearch(Catalogue.searchVideoTitle(getSearchQuery()));
                    break;
                case "2":
                    processSearch(Catalogue.searchVideoPublisher(getSearchQuery()));
                    break;
                case "3":
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    sleepFor(2000);
                    break;
            }
        }
    }
    public static void searchCDMenu() {
        boolean menu = true;
        while(menu) {
            clearScreen();
            System.out.print(
                    "\n===============================================" +
                    "\nSearch CDs " +
                    "\n===============================================" +
                    "\n 1. Search by Title" +
                    "\n 2. Search by Artist" +
                    "\n 3. Search by Publisher" +
                    "\n 4. Go back... " +
                    "\nPlease choose an option: ");

            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    processSearch(Catalogue.searchCDTitle(getSearchQuery()));
                    break;
                case "2":
                    processSearch(Catalogue.searchCDArtist(getSearchQuery()));
                    break;
                case "3":
                    processSearch(Catalogue.searchCDPublisher(getSearchQuery()));
                    break;
                case "4":
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    sleepFor(2000);
                    break;
            }
        }
    }

    // For search results
    public static void processSearch(ArrayList<Media> result) {
        String type = null;
        if (result.size() == 0) {
            System.out.println("Press enter to continue...");
            in.nextLine();
        } else {
            for (int i = 0; i < result.size(); i++) {
                Media media = result.get(i);
                type = media.getClass().getName().toLowerCase();
                System.out.print("\n" + (i + 1) + ". " + media.getTitle());
            }
            System.out.print("\n\nPlease choose a " + type + " by number to see more: ");
            int choice;
            do {
                while(!in.hasNextInt()) in.next();
                choice = in.nextInt();
                in.nextLine();
            } while(choice > result.size());
            choice -= 1;
            Media media = result.get(choice);
            clearScreen();
            System.out.println(media.getFullInfo());
            if (loggedInUser.getClass().getName().equals("Member")) {
                showReserveOptions(media.getStockID());
            } else {
                System.out.println("Press enter to return...");
                in.nextLine();
            }
        }
    }

    // Menu for reservations
    public static void showReserveOptions(String stockID) {
        boolean menu = true;
        String userID = loggedInUser.getId();
        System.out.println("\n1. Make Reservation   2. Cancel Reservation    3. Go back... ");
        System.out.print("Please choose from the above options: ");
        while(menu) {
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    Reservation.reserveItem(stockID, userID);
                    System.out.println("Press enter to continue...");
                    in.nextLine();
                    menu = false;
                    break;
                case "2":
                    Reservation.cancelReservation(stockID, userID);
                    System.out.println("Press enter to continue...");
                    in.nextLine();
                    menu = false;
                    break;
                case "3":
                    menu = false;
                    break;
                default:
                    System.out.print("\nPlease enter a valid option: ");
                    break;
            }
        }
    }

    // Deserializes on startup and populates with dummy data on first run
    public static void start() {
        UserStore.restore();
        Catalogue.restore();
        Reservation.restore();
        Lending.restore();
        populateUserData();
        populateMediaData();
    }
    // Serializes on shutdown
    public static void stop() {
        UserStore.store();
        Catalogue.store();
        Reservation.store();
        Lending.store();
    }

    // For re-usability
    public static String getID() {
        System.out.print("\nUsername: ");
        String id = in.next();
        in.nextLine();
        return id;
    }
    public static String getPw() {
        System.out.print("Password: ");
        String pw = in.next();
        in.nextLine();
        return pw;
    }

    // Handles logging in/out & logging
    public static void login(Account user) {
        sessionStart = LocalDateTime.now();
        loggedInUser = user;
    }
    public static void logout() {
        LocalDateTime sessionEnd = LocalDateTime.now();
        DateTimeFormatter login = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter logout = DateTimeFormatter.ofPattern("HH:mm:SS");
        String log = login.format(sessionStart) + " - " + logout.format(sessionEnd);
        UserStore.writeLog(loggedInUser.getId(), getComputerName(), log);
        loggedInUser = null;
    }

    // Returns logged in user
    public static Admin adminLogin(String id, String password) {
        if (!validateAdmin(id, password)) {
            return null;
        }
        return UserStore.getAdmin(id);
    }
    public static Member memberLogin(String id, String password) {
        if (!validateMember(id, password)) {
            return null;
        }
        return UserStore.getMember(id);
    }

    // Checks ID and Password against stored users
    public static boolean validateAdmin(String id, String password) {
        if (!UserStore.adminExists(id)) {
            return false;
        }
        return SCryptUtil.check(password, UserStore.getAdmin(id).getPassword());
    }
    public static boolean validateMember(String id, String password) {
        if (!UserStore.memberExists(id)) {
            return false;
        }
        return SCryptUtil.check(password, UserStore.getMember(id).getPassword());
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
        Member casual1 = new Member("josseagle", hashedUserPass, AccountStatus.ACTIVE, AccountType.CASUAL, joss);
        UserStore.addMember(casual1);
        Person malcolm = new Person("Malcolm", "Function", "07721984023", "12/03/2001", address);
        Member casual2 = new Member("malcolmfunction", hashedUserPass, AccountStatus.ACTIVE, AccountType.CASUAL, malcolm);
        UserStore.addMember(casual2);
        Person nathaneal = new Person("Nathaneal", "Down", "01314 95028", "16/05/1993", address);
        Member casual3 = new Member("nathanealdown", hashedUserPass, AccountStatus.CLOSED, AccountType.CASUAL, nathaneal);
        UserStore.addMember(casual3);

    }
    public static void populateMediaData() {
        // Books
        Book lotr = new Book("LOTR", "The Lord of the Rings",  14.57, Genre.FANTASY,
                "Allen & Unwin", 1137, "9780261103252", "J.R.R. Tolkien");
        Catalogue.addMedia(lotr);
        Book attwn = new Book("ATTWN", "And Then There Were None", 10.47, Genre.MYSTERY,
                "Collins Crime Club", 272, "9780008123208", "Agatha Christie");
        Catalogue.addMedia(attwn);
        Book tagr = new Book("TAGR", "Think and Grow Rich", 10.51, Genre.SELF_IMPROVEMENT,
                "The Ralston Society", 238, "9781788441025", "Napoleon Hill");
        Catalogue.addMedia(tagr);

        // Journals
        Journal ijbb = new Journal("IJBB", "International Journal of Biotechnology & " +
                "Biochemistry (IJBB)",40.00, "Research India Publications", Genre.SCIENCE, 3950,
                "0973-2691", "2.1", "05/07/2006");
        Catalogue.addMedia(ijbb);
        Journal aiap = new Journal("AIAP", "Advances in Applied Probability",10.00,
                "Applied Probability Trust", Genre.SCIENCE, 1753, "0001-8678",
                "51.4", "01/12/2019");
        Catalogue.addMedia(aiap);
        Journal sjot = new Journal("SJOT", "Scottish Journal of Theology",33.54,
                "Cambridge University Press", Genre.RELIGION, 872, "0036-9306",
                "72.4", "19/08/2019");
        Catalogue.addMedia(sjot);

        // Videos
        Video tlk = new Video("TLK", "The Lion King", 4.99, "Disney", "89",
                "Keep Case", VideoFormat.DVD, Genre.CHILDRENS);
        Catalogue.addMedia(tlk);
        Video got = new Video("GOT", "Game of Thrones: The Complete Seasons 1-8", 119.99,
                "HBO", "4186", "Box Set", VideoFormat.BLURAY, Genre.FANTASY);
        Catalogue.addMedia(got);
        Video joker = new Video("JOKER", "Joker", 14.99, "DC", "122",
                "Keep Case (Slim)", VideoFormat.BLURAY, Genre.THRILLER);
        Catalogue.addMedia(joker);

        // CDs
        CD kob = new CD("KOB", "Kind of Blue", 3.99, "Columbia", "46",
                "Jewel Case", CDType.CDDA, 15, "Miles Davis");
        Catalogue.addMedia(kob);
        CD totbl = new CD("TOTBL", "Turn On the Bright Lights", 7.99, "Matador",
                "49", "Jewel Case", CDType.CDDA, 11, "Interpol");
        Catalogue.addMedia(totbl);
        CD dsotm = new CD("DSOTM", "Dark Side of The Moon", 7.99, "Harvest",
                "43", "Jewel Case (Slim)", CDType.CDR, 10, "Pink Floyd");
        Catalogue.addMedia(dsotm);

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
        }
    }
    public static String printLines(String name) {
        StringBuilder lines = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            lines.append("=");
        }
        return lines.toString();
    }

    private static String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else return env.getOrDefault("HOSTNAME", "Unknown Computer");
    }
}
