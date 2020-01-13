import com.lambdaworks.crypto.SCryptUtil;

import java.util.EnumSet;
import java.util.Random;

public class Admin extends Account  {

    public Admin(String id, String password, Person person) {
        super(id, password, person);
    }

    @Override
    public int getLoansAvailable() {
        return 0;
    }

    @Override
    public void editDetails() {
        boolean menu = true;
        while(menu) {
            Library.clearScreen();
            System.out.print(
                    "\n===============================================" +
                            "\nWhat would you like to update, "+ this.getPerson().getForename() + "?" +
                            "\n===============================================" +
                            "\n 1. Password" +
                            "\n 2. Forename" +
                            "\n 3. Surname" +
                            "\n 4. Email" +
                            "\n 5. Phone number" +
                            "\n 6. Go back..." +
                            "\n Please choose an option: "
            );
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    if(updatePassword()) UserStore.addAdmin(this);
                    break;
                case "2":
                    System.out.print("\nEnter new forename: ");
                    this.getPerson().setForename(in.nextLine());
                    UserStore.addAdmin(this);
                    System.out.println("Forename updated!");
                    break;
                case "3":
                    System.out.print("\nEnter new surname: ");
                    this.getPerson().setSurname(in.nextLine());
                    UserStore.addAdmin(this);
                    System.out.println("Surname updated!");
                    break;
                case "4":
                    System.out.print("\nEnter new email: ");
                    this.getPerson().setEmail(in.nextLine());
                    UserStore.addAdmin(this);
                    System.out.println("Email updated!");
                    break;
                case "5":
                    System.out.print("\nEnter new phone number: ");
                    this.getPerson().setPhone(in.nextLine());
                    UserStore.addAdmin(this);
                    System.out.println("Phone number updated!");
                    break;
                case "6":
                    menu = false;
                    break;
                default:
                    System.out.println("Please choose a valid option.");
                    break;
            }
        }
    }

    @Override
    // Walks admin user through member creation
    public void addMember(){
        Library.clearScreen();

        // Automatically assign a 6 digit ID
        System.out.print("\nUser ID: ");
        String id = in.nextLine();
        while(UserStore.memberExists(id)) {
            System.out.print("\nSorry, ID '" + id + "' is already in use, try again: ");
            id = in.nextLine();
        }


        // Create password and hash it
        String pass = generatePw();
        String hashedPass = SCryptUtil.scrypt(pass, 16384, 8, 1);
        System.out.print("Generated password (write this down): " + pass + "\n");
        System.out.println("Press enter to continue...");
        in.nextLine();
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
            String dob = in.nextLine();
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
            System.out.println("Press enter to continue...");
            in.nextLine();
        }
        UserStore.store();
    }

    @Override
    public void addMedia() {
        boolean menu = true;
        while(menu) {
            Library.clearScreen();
            System.out.print(
                    "\n===============================================" +
                            "\nWhat would you like to add?" +
                            "\n===============================================" +
                            "\n 1. Book" +
                            "\n 2. Journal" +
                            "\n 3. Video" +
                            "\n 4. CD" +
                            "\n 5. Go back..." +
                            "\nPlease choose an option: ");
            String choice = in.nextLine();
            switch(choice) {
                case "1":
                    addBook();
                    Library.sleepFor(1500);
                    break;
                case "2":
                    addJournal();
                    Library.sleepFor(1500);
                    break;
                case "3":
                    addVideo();
                    Library.sleepFor(1500);
                    break;
                case "4":
                    addCD();
                    Library.sleepFor(1500);
                    break;
                case "5":
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    Library.sleepFor(2000);
                    break;
            }
        }
    }

    @Override
    public void showEditMenu() {
        boolean menu = true;
        while(menu){
            Library.clearScreen();
            System.out.print(
                    "\n===============================================" +
                            "\nWhat would you like to edit?" +
                            "\n===============================================" +
                            "\n 1. A Book" +
                            "\n 2. A Journal" +
                            "\n 3. A Video" +
                            "\n 4. A CD" +
                            "\n 5. Go back..." +
                            "\nPlease choose an option: ");
            String choice = in.nextLine();
            switch(choice) {
                case "1":
                    System.out.println("Enter book ID: ");
                    String bookID = in.nextLine();
                    editBook(bookID.toUpperCase());
                    Library.sleepFor(1500);
                    break;
                case "2":
                    System.out.println("Enter journal ID: ");
                    String journalID = in.nextLine();
                    editJournal(journalID.toUpperCase());
                    Library.sleepFor(1500);
                    break;
                case "3":
                    System.out.println("Enter video ID: ");
                    String videoID = in.nextLine();
                    editVideo(videoID.toUpperCase());
                    Library.sleepFor(1500);
                    break;
                case "4":
                    System.out.println("Enter CD ID: ");
                    String CDID = in.nextLine();
                    editCD(CDID.toUpperCase());
                    Library.sleepFor(1500);
                    break;
                case "5":
                    menu = false;
                    break;
                default:
                    System.out.println("Please enter a valid option.");
                    Library.sleepFor(2000);
                    break;
            }
        }

    }

    // Adds book
    public static void addBook() {
        Library.clearScreen();
        String id = "B" + generateID();
        while(Catalogue.checkStockID(id)) {
            id = "B" + generateID();
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

        Book newBook = new Book(id, title, price, genre, publisher, numPages, ISBN, author);
        Library.clearScreen();

        System.out.println(newBook.getFullInfo());
        System.out.println("Is the above information correct? y/n: ");
        if(in.nextLine().toLowerCase().equals("y")){
            Catalogue.addMedia(newBook);
            System.out.println("Book added successfully!");
            Library.sleepFor(1500);
            return;
        }
        System.out.println("Returning you to the menu, please try again... ");
        Library.sleepFor(2000);
    }
    // Edit book
    public static void editBook(String bookID) {
        Library.clearScreen();
        if (!Catalogue.checkStockID(bookID)) {
            System.out.println("No book found with the ID \"" + bookID + "\"");
            Library.sleepFor(1500);
        } else {
            System.out.println("Stock ID: " + bookID);
            System.out.println("Enter new title: ");
            String newtitle = in.nextLine();
            System.out.println("Enter new author: ");
            String newauthor = in.nextLine();
            System.out.println("Enter new publisher: ");
            String newpublisher = in.nextLine();
            System.out.println("Enter new ISBN: ");
            String newISBN = in.nextLine();
            Genre newgenre = selectGenre();
            Double newprice = inputPrice();
            System.out.println("Enter number of pages:");
            Integer newnumPages = inputInteger();

            Book newBook = new Book(bookID, newtitle, newprice, newgenre, newpublisher, newnumPages, newISBN, newauthor);
            Library.clearScreen();

            System.out.println(newBook.getFullInfo());
            System.out.println("Is the above information correct? y/n: ");
            if (in.nextLine().toLowerCase().equals("y")) {
                Catalogue.updateMedia(newBook);
                System.out.println("Book added successfully!");
                Library.sleepFor(1500);
                return;
            }
            System.out.println("Returning you to the menu, please try again... ");
            Library.sleepFor(2000);
        }
    }


    // Adds journal
    public static void addJournal() {
        Library.clearScreen();
        String id = "J" + generateID();
        while(Catalogue.checkStockID(id)) {
            id = "J" + generateID();
        }
        System.out.println("Stock ID: " + id);
        System.out.println("Enter Title: ");
        String title = in.nextLine();
        System.out.println("Enter Issue Number: ");
        String issueNum = in.nextLine();
        System.out.println("Enter Issue Date (dd/mm/yyyy): ");
        String issueDate = in.nextLine();
        System.out.println("Enter publisher: ");
        String publisher = in.nextLine();
        System.out.println("Enter ISSN: ");
        String ISSN = in.nextLine();
        Genre genre = selectGenre();
        Double price = inputPrice();
        System.out.println("Enter number of pages: ");
        Integer numPages = inputInteger();

        Journal newJournal = new Journal(id, title, price, publisher, genre, numPages, ISSN, issueNum, issueDate);
        Library.clearScreen();

        System.out.println(newJournal.getFullInfo());
        System.out.println("Is the above information correct? y/n: ");
        if(in.nextLine().toLowerCase().equals("y")){
            Catalogue.addMedia(newJournal);
            System.out.println("Journal added successfully!");
            Library.sleepFor(1500);
            return;
        }
        System.out.println("Returning you to the menu, please try again... ");
        Library.sleepFor(2000);
    }
    // Edit journal
    public static void editJournal(String journalID) {
        Library.clearScreen();
        if (!Catalogue.checkStockID(journalID)) {
            System.out.println("No journal found with the ID \"" + journalID + "\"");
            Library.sleepFor(1500);
        } else {
            System.out.println("Stock ID: " + journalID);
            System.out.println("Enter new title: ");
            String title = in.nextLine();
            System.out.println("Enter new issue number: ");
            String issueNum = in.nextLine();
            System.out.println("Enter new issue date (dd/mm/yyyy): ");
            String issueDate = in.nextLine();
            System.out.println("Enter new publisher: ");
            String publisher = in.nextLine();
            System.out.println("Enter new ISSN: ");
            String ISSN = in.nextLine();
            Genre genre = selectGenre();
            Double price = inputPrice();
            System.out.println("Enter number of pages: ");
            Integer numPages = inputInteger();

            Journal newJournal = new Journal(journalID, title, price, publisher, genre, numPages, ISSN, issueNum, issueDate);
            Library.clearScreen();

            System.out.println(newJournal.getFullInfo());
            System.out.println("Is the above information correct? y/n: ");
            if(in.nextLine().toLowerCase().equals("y")){
                Catalogue.updateMedia(newJournal);
                System.out.println("Journal added successfully!");
                Library.sleepFor(1500);
                return;
            }
            System.out.println("Returning you to the menu, please try again... ");
            Library.sleepFor(2000);
        }
    }



    // Adds video
    public static void addVideo() {
        Library.clearScreen();
        String id = "V" + generateID();
        while(Catalogue.checkStockID(id)) {
            id = "V" + generateID();
        }
        System.out.println("Stock ID: " + id);
        System.out.println("Enter Title: ");
        String title = in.nextLine();
        System.out.println("Enter publisher: ");
        String publisher = in.nextLine();
        System.out.println("Enter type of storage case: ");
        String typeofCase = in.nextLine();
        System.out.println("Enter runtime (in minutes): ");
        String runtime = in.nextLine();
        Genre genre = selectGenre();
        Double price = inputPrice();
        VideoFormat format = selectVideoFormat();

        Video newVideo = new Video(id, title, price, publisher, runtime, typeofCase, format, genre);
        Library.clearScreen();

        System.out.println(newVideo.getFullInfo());
        System.out.println("Is the above information correct? y/n: ");
        if(in.nextLine().toLowerCase().equals("y")){
            Catalogue.addMedia(newVideo);
            System.out.println("Video added successfully!");
            Library.sleepFor(1500);
            return;
        }
        System.out.println("Returning you to the menu, please try again... ");
        Library.sleepFor(2000);
    }
    // Edit video
    public static void editVideo(String videoID) {
        Library.clearScreen();
        if (!Catalogue.checkStockID(videoID)) {
            System.out.println("No video found with the ID \"" + videoID + "\"");
            Library.sleepFor(1500);
        } else {
            System.out.println("Stock ID: " + videoID);
            System.out.println("Enter new title: ");
            String title = in.nextLine();
            System.out.println("Enter new publisher: ");
            String publisher = in.nextLine();
            System.out.println("Enter new type of storage case: ");
            String typeofCase = in.nextLine();
            System.out.println("Enter new runtime (in minutes): ");
            String runtime = in.nextLine();
            Genre genre = selectGenre();
            Double price = inputPrice();
            VideoFormat format = selectVideoFormat();

            Video newVideo = new Video(videoID, title, price, publisher, runtime, typeofCase, format, genre);
            Library.clearScreen();

            System.out.println(newVideo.getFullInfo());
            System.out.println("Is the above information correct? y/n: ");
            if (in.nextLine().toLowerCase().equals("y")) {
                Catalogue.updateMedia(newVideo);
                System.out.println("Video added successfully!");
                Library.sleepFor(1500);
                return;
            }
            System.out.println("Returning you to the menu, please try again... ");
            Library.sleepFor(2000);
        }
    }


    // Adds CD
    public static void addCD() {
        Library.clearScreen();
        String id = "CD" + generateID();
        while(Catalogue.checkStockID(id)) {
            id = "CD" + generateID();
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
        System.out.println("Enter runtime (in minutes only): ");
        String runtime = in.nextLine();
        System.out.println("Enter the number of tracks: ");
        Integer numTracks = inputInteger();
        Double price = inputPrice();
        CDType type = selectCDType();

        CD newCD = new CD(id, title, price, publisher, runtime, typeofCase, type, numTracks, artist);
        Library.clearScreen();

        System.out.println(newCD.getFullInfo());
        System.out.println("Is the above information correct? y/n: ");
        if(in.nextLine().toLowerCase().equals("y")){
            Catalogue.addMedia(newCD);
            System.out.println("CD added successfully!");
            Library.sleepFor(1500);
            return;
        }
        System.out.println("Returning you to the menu, please try again... ");
        Library.sleepFor(2000);
    }
    // Edit CD
    public static void editCD(String CDID) {
        Library.clearScreen();
        if (!Catalogue.checkStockID(CDID)) {
            System.out.println("No video found with the ID \"" + CDID + "\"");
            Library.sleepFor(1500);
        } else {
            System.out.println("Stock ID: " + CDID);
            System.out.println("Enter new title: ");
            String title = in.nextLine();
            System.out.println("Enter new artist: ");
            String artist = in.nextLine();
            System.out.println("Enter new publisher: ");
            String publisher = in.nextLine();
            System.out.println("Enter new type of storage case: ");
            String typeofCase = in.nextLine();
            System.out.println("Enter new runtime (in minutes only): ");
            String runtime = in.nextLine();
            System.out.println("Enter the new number of tracks: ");
            Integer numTracks = inputInteger();
            Double price = inputPrice();
            CDType type = selectCDType();

            CD newCD = new CD(CDID, title, price, publisher, runtime, typeofCase, type, numTracks, artist);
            Library.clearScreen();

            System.out.println(newCD.getFullInfo());
            System.out.println("Is the above information correct? y/n: ");
            if(in.nextLine().toLowerCase().equals("y")){
                Catalogue.updateMedia(newCD);
                System.out.println("CD added successfully!");
                Library.sleepFor(1500);
                return;
            }
            System.out.println("Returning you to the menu, please try again... ");
            Library.sleepFor(2000);
        }
    }



    // REUSABLE GENERATORS
    // Generates 6 Digit ID
    private static String generateID() {
        Random r = new Random();
        int num = r.nextInt(999999);
        return String.format("%06d", num);
    }
    private static String generatePw() {
//        Random r = new Random();
//        int num = r.nextInt(99999999);
//        return String.format("%08d", num);

        // Hi Jacqui, above is my implementation for a password generator that returns an 8-digit number, I found the
        // one below at https://stackoverflow.com/a/53349505 because I wanted to use more than just numbers.

        return new Random().ints(10, 33, 127).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
    // Gets a genre from enum list
    private static Genre selectGenre() {
        for (Genre info : EnumSet.allOf(Genre.class)) {
            System.out.println(info);
        }
        System.out.println("Please choose a genre from the list above: ");
        String type = in.nextLine();
        type = type.replaceAll("\\s", "_").toUpperCase();
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
            in.nextLine();
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
            in.nextLine();
        } while (price < 0);
        return price;
    }
    // Gets video format
    public static VideoFormat selectVideoFormat() {
        boolean select = true;
        System.out.print(
                "    1. " + VideoFormat.VCR.displayName() +
                        "\n    2. " + VideoFormat.DVD.displayName() +
                        "\n    3. " + VideoFormat.BLURAY.displayName() +
                        "\nSelect video format: ");
        VideoFormat format = null;
        while(select) {
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    format = VideoFormat.VCR;
                    select = false;
                    break;
                case "2":
                    format = VideoFormat.DVD;
                    select = false;
                    break;
                case "3":
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
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    type = CDType.CDDA;
                    select = false;
                    break;
                case "2":
                    type = CDType.CDR;
                    select = false;
                    break;
                case "3":
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
