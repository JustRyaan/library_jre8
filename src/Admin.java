import com.lambdaworks.crypto.SCryptUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.EnumSet;
import java.util.Random;
import java.util.Scanner;
import java.util.function.ToDoubleBiFunction;

public class Admin extends Account  {
    private static Scanner in = new Scanner(System.in);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Admin(String id, String password, Person person) {
        super(id, password, person);
    }


    // Walks admin user through member creation
    public static void addMember(){
        Library.clearScreen();

        // Automatically assign a 6 digit ID
        String id = generateID();
        while(UserStore.memberExists(id)) {
            id = generateID();
        }
        System.out.println("New ID: " + id);

        // Create password and hash it
        System.out.print("Password: ");
        String pass = in.nextLine();
        String hashedPass = SCryptUtil.scrypt(pass, 16384, 8, 1);

        // Get user details
        System.out.print("Forename: ");
        String fname = in.nextLine();
        // Surname
        System.out.print("Surname: ");
        String sname = in.nextLine();
        // Phone
        System.out.print("Phone: ");
        String phone = in.nextLine();
        // Select account type
        boolean select = true;
        System.out.print(
                "    1. " + AccountType.FULL.displayName() +
                        "\n    2. " + AccountType.CASUAL.displayName() +
                        "\n    3. " + AccountType.STAFF.displayName() +
                        "\nAccount type: ");
        AccountType type = null;
        while(select) {
            int choice;
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    type = AccountType.FULL;
                    select = false;
                    break;
                case 2:
                    type = AccountType.CASUAL;
                    select = false;
                    break;
                case 3:
                    type = AccountType.STAFF;
                    select = false;
                    break;
                default:
                    System.out.print("Please choose a valid option: ");
            }
        }
        // Status is always active for new accounts
        AccountStatus status = AccountStatus.ACTIVE;
        // Staff need less info saved, so we can just create them now
        if(type == AccountType.STAFF) {
            // Email
            System.out.print("Email: ");
            String email = in.nextLine();
            // Create the member and update userstore
            Person sp = new Person(fname, sname, email, phone);
            Member sm = new Member(id, hashedPass, status, type, sp);
            UserStore.addMember(sm);
            System.out.println("\nNew staff user created!\nID: " + id + "  -  Password: " + pass);
        } else {
            // Everyone else requires more details
            // D.O.B
            System.out.print("D.O.B (dd/mm/yyyy): ");
            LocalDate dob = LocalDate.parse(in.nextLine(), formatter);
            // Address ln.1
            System.out.print("Street Address: ");
            String address = in.nextLine();
            // Town
            System.out.print("Town: ");
            String town = in.nextLine();
            // Postcode
            System.out.print("Postcode: ");
            String postcode = in.nextLine();
            // Create member and update userstore
            Address na = new Address(address, town, postcode);
            Person np = new Person(fname, sname, phone, dob, na);
            Member nm = new Member(id, hashedPass, status, type, np);
            UserStore.addMember(nm);
            System.out.println("\nNew user created!\nID: " + id + "  -  Password: " + pass);
        }
        UserStore.store();
    }
    // Walks admin user through member editing
    public static void editMember(String id){
        // Create locally editable member object
        Member member = UserStore.getMember(id);
        // Display menu of fields to edit
        boolean menu = true;
        while (menu) {
            Library.clearScreen();
            System.out.print(
                    "\nUser: " + member.getPerson().getFullName() + " (" + member.getId() + ")" +
                            "\nWhat would you like to edit?" +
                            "\n1. Name" +
                            "\n2. Phone" +
                            "\n3. D.O.B" +
                            "\n4. Address" +
                            "\n5. Email" +
                            "\n6. Return to menu" +
                            "\nPlease choose an option: ");
            int choice;
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter new forename: ");
                        String fname = in.nextLine();
                    System.out.println("Enter new surname: ");
                        String sname = in.nextLine();
                    member.getPerson().setName(fname, sname);
                    System.out.println("Name has been updated!");
                    Library.sleepFor(1500);
                    break;
                case 2:
                    System.out.println("Enter new phone number: ");
                        String phone = in.nextLine();
                    member.getPerson().setPhone(phone);
                    System.out.println("Phone number has been updated!");
                    Library.sleepFor(1500);
                    break;
                case 3:
                    if(member.getType() == AccountType.STAFF) {
                        System.out.println("Error: This type of user doesn't store their D.O.B.");
                        break;
                    } else {
                        System.out.println("Enter new D.O.B (dd/mm/yyy): ");
                            String dob = in.nextLine();
                        member.getPerson().setDOB(dob);
                        System.out.println("Date of birth has been updated!");
                    }
                    Library.sleepFor(1500);
                    break;
                case 4:
                    if(member.getType() == AccountType.STAFF) {
                        System.out.println("Error: This type of user doesn't store their address.");
                        break;
                    } else {
                        System.out.println("Enter new street address: ");
                            String address = in.nextLine();
                        System.out.println("Enter new town: ");
                            String town = in.nextLine();
                        System.out.println("Enter new postcode: ");
                            String postcode = in.nextLine();
                        Address newAddress = new Address(address, town, postcode);
                        member.getPerson().setAddress(newAddress);
                        System.out.println("Address has been updated!");
                    }
                    Library.sleepFor(1500);
                    break;
                case 5:
                    if(member.getType() != AccountType.STAFF) {
                        System.out.println("Error: This type of user doesn't have an email address!");
                    } else {
                        System.out.println("Enter new email: ");
                            String email = in.nextLine();
                        member.getPerson().setEmail(email);
                        System.out.println("Email has been updated!");
                    }
                    Library.sleepFor(1500);
                    break;
                case 6:
                    UserStore.updateMember(member);
                    System.out.println("Returning to menu... ");
                    Library.sleepFor(1500);
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    Library.sleepFor(1500);
                    break;
            }
        }
    }
    // Removes a member
    public static void removeMember(String id) {
        UserStore.removeMember(id);
        System.out.println("User deleted successfully.");
    }


    // Adds book
    public static void addBook() {
        Library.clearScreen();
        String id = generateID();
        while(UserStore.checkStockID(id)) {
            id = generateID();
        }
        System.out.println("Stock ID: " + id);
        System.out.println("Enter Title: ");
            String title = in.nextLine();
        System.out.println("Enter Author: ");
            String author = in.nextLine();
        System.out.println("Enter publisher: ");
            String publisher = in.nextLine();
        System.out.println("Enter ISBN: ");
            String ISBN = in.nextLine();
        Genre genre = selectGenre();
        Double price = inputPrice();
        System.out.println("Enter number of pages:");
        Integer numPages = inputInteger();

        BookItem newBook = new BookItem(id, title, price, genre, publisher, numPages, ISBN, author);
        Library.clearScreen();

        System.out.println(newBook.getBookInfo());
        System.out.println("Is the above information correct? y/n: ");
        if(in.nextLine().toLowerCase().equals("y")){
            UserStore.addMedia(newBook);
            System.out.println("Book added successfully!");
            Library.sleepFor(1500);
            return;
        }
        System.out.println("Returning you to the menu, please try again... ");
        Library.sleepFor(2000);
    }
    // Edit book
    public static void editBook() {
        // TODO: implement edit book
    }


    // Adds journal
    public static void addJournal() {
        Library.clearScreen();
        String id = generateID();
        while(UserStore.checkStockID(id)) {
            id = generateID();
        }
        System.out.println("Stock ID: " + id);
        System.out.println("Enter Title: ");
            String title = in.nextLine();
        System.out.println("Enter Issue Number: ");
            String issueNum = in.nextLine();
        System.out.println("Enter Issue Date (dd/mm/yyyy): ");
            LocalDate issueDate = LocalDate.parse(in.nextLine(), formatter);
        System.out.println("Enter publisher: ");
            String publisher = in.nextLine();
        System.out.println("Enter ISSN: ");
            String ISSN = in.nextLine();
        Genre genre = selectGenre();
        Double price = inputPrice();
        System.out.println("Enter number of pages: ");
        Integer numPages = inputInteger();

        JournalItem newJournal = new JournalItem(id, title, price, publisher, genre, numPages, ISSN, issueNum, issueDate);
        Library.clearScreen();

        System.out.println(newJournal.getJournalInfo());
        System.out.println("Is the above information correct? y/n: ");
        if(in.nextLine().toLowerCase().equals("y")){
            UserStore.addMedia(newJournal);
            System.out.println("Journal added successfully!");
            Library.sleepFor(1500);
            return;
        }
        System.out.println("Returning you to the menu, please try again... ");
        Library.sleepFor(2000);
    }
    // Edit journal
    public static void editJournal() {
        // TODO: implement edit journal
    }


    // Adds video
    public static void addVideo() {
        Library.clearScreen();
        String id = generateID();
        while(UserStore.checkStockID(id)) {
            id = generateID();
        }
        System.out.println("Stock ID: " + id);
        System.out.println("Enter Title: ");
            String title = in.nextLine();
        System.out.println("Enter publisher: ");
            String publisher = in.nextLine();
        System.out.println("Enter type of storage case: ");
            String typeofCase = in.nextLine();
        System.out.println("Enter runtime (in minutes): ");
            int minutes = inputInteger();
            Duration runtime = Duration.ofMinutes(minutes);
        Genre genre = selectGenre();
        Double price = inputPrice();
        VideoFormat format = selectVideoFormat();

        VideoItem newVideo = new VideoItem(id, title, price, publisher, runtime, typeofCase, format, genre);
        Library.clearScreen();

        System.out.println(newVideo.getVideoInfo());
        System.out.println("Is the above information correct? y/n: ");
        if(in.nextLine().toLowerCase().equals("y")){
            UserStore.addMedia(newVideo);
            System.out.println("Video added successfully!");
            Library.sleepFor(1500);
            return;
        }
        System.out.println("Returning you to the menu, please try again... ");
        Library.sleepFor(2000);
    }
    // Edit video
    public static void editVideo() {
        // TODO: implement edit video
    }


    // Adds CD
    public static void addCD() {
        Library.clearScreen();
        String id = generateID();
        while(UserStore.checkStockID(id)) {
            id = generateID();
        }
        System.out.println("Stock ID: " + id);
        System.out.println("Enter Title: ");
        String title = in.nextLine();
        System.out.println("Enter Artist: ");
        String artist = in.nextLine();
        System.out.println("Enter Publisher: ");
        String publisher = in.nextLine();
        System.out.println("Enter type of storage case: ");
        String typeofCase = in.nextLine();
        System.out.println("Enter runtime (in minutes): ");
        int minutes = inputInteger();
        Duration runtime = Duration.ofMinutes(minutes);
        System.out.println("Enter the number of tracks: ");
        Integer numTracks = inputInteger();
        Double price = inputPrice();
        CDType type = selectCDType();

        CDItem newCD = new CDItem(id, title, price, publisher, runtime, typeofCase, type, numTracks, artist);
        Library.clearScreen();

        System.out.println(newCD.getCDInfo());
        System.out.println("Is the above information correct? y/n: ");
        if(in.nextLine().toLowerCase().equals("y")){
            UserStore.addMedia(newCD);
            System.out.println("CD added successfully!");
            Library.sleepFor(1500);
            return;
        }
        System.out.println("Returning you to the menu, please try again... ");
        Library.sleepFor(2000);
    }
    // Edit CD
    public static void editCD() {
        // TODO: implement edit CD
    }



    // REUSABLE GENERATORS
    // Generates 6 Digit ID
    private static String generateID() {
        Random r = new Random();
        int num = r.nextInt(999999);
        return String.format("%06d", num);
    }
    // Gets a genre from enum list
    private static Genre selectGenre() {
        in.nextLine();
        for (Genre info : EnumSet.allOf(Genre.class)) {
            System.out.println(info);
        }
        System.out.println("Please choose a genre from the list above: ");
        String type = in.nextLine();
        type = type.replace(" ", "_").toUpperCase();
        if(!Genre.contains(type)) {
            System.out.println("Something's not right, please check your spelling and try again.");
            return null;
        }
        return Genre.valueOf(type);
    }
    // validates integer
    private static Integer inputInteger() {
        // Quick integer validation
        int num;
        do {
            while (!in.hasNextInt()) {
                System.out.println("Please enter a number!");
                in.next();
            }
            num = in.nextInt();
        } while (num <= 0);
        return num;
    }
    // validates double
    private static Double inputPrice() {
        // Quick double validation
        double price;
        do {
            System.out.print("Enter price (digits only, e.g. 12.35): ");
            while (!in.hasNextDouble()) {
                System.out.println("Please check you're only entering digits and a decimal place!");
                in.next();
            }
            price = in.nextDouble();
        } while (price < 0);
        return price;
    }
    // Gets video format
    public static VideoFormat selectVideoFormat() {
        boolean select = true;
        System.out.print(
                "    1. " + VideoFormat.VCR.displayName() +
                        "\n    2. " + VideoFormat.DVD.displayName() +
                        "\n    3. " + VideoFormat.DIGITAL.displayName() +
                        "\n    4. " + VideoFormat.BLURAY.displayName() +
                        "\nSelect video format: ");
        VideoFormat format = null;
        while(select) {
            int choice;
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    format = VideoFormat.VCR;
                    select = false;
                    break;
                case 2:
                    format = VideoFormat.DVD;
                    select = false;
                    break;
                case 3:
                    format = VideoFormat.DIGITAL;
                    select = false;
                    break;
                case 4:
                    format = VideoFormat.BLURAY;
                    select = false;
                    break;
                default:
                    System.out.println("Please choose a valid option.");
            }
        }
        return format;
    }
    // Gets CD type
    public static CDType selectCDType() {
        boolean select = true;
        System.out.print(
                "    1. " + CDType.CDDA.displayName() +
                        "\n    2. " + CDType.CDR.displayName() +
                        "\n    3. " + CDType.CDRW.displayName() +
                        "\nSelect video format: ");
        CDType type = null;
        while(select) {
            int choice;
            choice = in.nextInt();
            in.nextLine();
            switch (choice) {
                case 1:
                    type = CDType.CDDA;
                    select = false;
                    break;
                case 2:
                    type = CDType.CDR;
                    select = false;
                    break;
                case 3:
                    type = CDType.CDRW;
                    select = false;
                    break;
                default:
                    System.out.println("Please choose a valid option.");
            }
        }
        return type;
    }

    @Override
    public String toString() {
        return super.getPerson().getFullName();
    }


}
