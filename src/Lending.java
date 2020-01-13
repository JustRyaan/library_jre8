import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.temporal.ChronoUnit;

public class Lending implements Serializable, fileRW {
    public LocalDate lendDate;
    public LocalDate dueDate;
    private String stockID;
    private String userID;
    private String title;

    // Stores details of all items currently out on loan
    private static HashMap<String, Lending> lentMedia = new HashMap<>();
    private static HashMap<String, ArrayList<String>> fineHistory = new HashMap<>();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Constructor
    public Lending(String stockID, String userID){
        this.lendDate = LocalDate.now();
        this.dueDate = lendDate.plusDays(10);
        this.stockID = stockID;
        this.userID = userID;
        this.title = Catalogue.getMediaTitle(stockID);
    }

    // Handles lending a media item
    public static Boolean lendItem(String stockID, String userID){
        // Get loan status based on stockID
        Lending lentItem = Lending.getLoanDetails(stockID);

        // Another member has already loaned the item, alert user of the due date and suggest a reservation
        if(lentItem != null && !lentItem.getUserID().equals(userID)) {
            System.out.println(
                "\nAnother member has already loaned this item." +
                "\nIt is due back on " + lentItem.getDueDate() + "." +
                "\nIt is currently " + (Reservation.checkReservation(stockID) ? "reserved" : "NOT reserved") + ".");
            return false;
            // The member has already loaned the item, remind them of their due date
        } else if(lentItem != null) {
            System.out.println("This item is already on loan from this member and is due to be returned by " +
                    lentItem.getDueDate() + ".");
            return false;
        }
        // Create a new loan and store it in the loans map
        Lending lending = new Lending(stockID, userID);
        lentMedia.put(stockID, lending);
        System.out.println("Loan recorded successfully.");
        return true;
    }

    // Returns information about an item on loan
    public static Lending getLoanDetails(String stockID) {
        return lentMedia.get(stockID);
    }

    public static void listLoans(String userID){
        if(lentMedia.isEmpty()) {
            System.out.println("\n\nThere are no active loans.");
        } else {
            HashMap<String, List<Lending>> loans = new HashMap<>();

            // Fill hashmap searchable by user id
            for (Map.Entry<String, Lending> entry : lentMedia.entrySet()) {
                loans.putIfAbsent(entry.getValue().getUserID(), new ArrayList<>());
                loans.get(entry.getValue().getUserID()).add(entry.getValue());
            }

            // Lists all loans
            if (userID.equals("all")) {
                for (Map.Entry<String, List<Lending>> entry : loans.entrySet()) {
                    System.out.println("\n" + entry.getKey() + " - " + UserStore.getMember(entry.getKey()).getPerson().getFullName()
                            + ": ");
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + entry.getValue().get(i).getStockID() + ": " +
                                entry.getValue().get(i).getTitle() + " - due " + entry.getValue().get(i).getDueDate() + ".");
                    }
                }
            } else {
                // Lists only loans by requesting user
                System.out.println(
                        "\n===============================================" +
                        "\n     Your Loans" +
                        "\n===============================================");
                ArrayList<Lending> items = (ArrayList<Lending>) loans.get(userID);
                if (items == null) {
                    System.out.println("  You have no active loans.");
                } else {
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + items.get(i).getTitle() + " - due " +
                                items.get(i).getDueDate() + ".");
                    }
                }
            }
        }
    }

    public static boolean returnItem(String stockID, String userID) {
        Lending lending = lentMedia.get(stockID);
        if(lending == null) {
            System.out.println("There is no record of a loan for that stock item.");
        } else {
            if(lending.getUserID().equals(userID)) {
                LocalDate dueDate = lending.dueDate;
                LocalDate today = LocalDate.now();
                long daysOverdue = ChronoUnit.DAYS.between(dueDate, today);
                if(daysOverdue > 0) {
                    float fine = (float) (daysOverdue * 0.5);
                    System.out.println("Late return. A fine of £" + fine + " is due.");
                    recordFine(userID, fine);
                }
                lentMedia.remove(stockID);
                System.out.println("Item returned successfully.");
                return true;
            }
            else {
                System.out.println("That item is not on loan to that member.");
            }
        }
        return false;
    }

    public static void recordFine(String userID, Float fineAmt) {
        String fine = Float.toString(fineAmt);
        String date = LocalDate.now().format(formatter);
        String record = date + ": £" + fine;
        fineHistory.putIfAbsent(userID, new ArrayList<>());
        fineHistory.get(userID).add(record);
    }

    public static void listFines() {
        if(fineHistory.isEmpty()) {
            System.out.println("\n\nNo users have been charged a fine yet.");
        } else {
            for (Map.Entry<String, ArrayList<String>> entry : fineHistory.entrySet()) {
                System.out.println("\n" + entry.getKey() + " - " + UserStore.getMember(entry.getKey()).getPerson().getFullName()
                        + ": ");
                for (int i = 0; i < entry.getValue().size(); i++) {
                    System.out.println("  " + entry.getValue().get(i)+ ".");
                }
            }
        }
    }

    // Getter methods
    public String getUserID() {
        return this.userID;
    }
    public String getDueDate() {
        return this.dueDate.format(formatter);
    }
    public String getTitle() {
        return this.title;
    }
    public String getStockID() {
        return this.stockID;
    }

    // Serializes HashMaps
    public static void store() {
        // Serialize
        fileRW.write(lentMedia, "/data/transactions/", "lendingData");
    }
    // Reads in serialized HashMaps
    public static void restore() {
        // Deserialize on startup
        lentMedia = fileRW.read("/data/transactions/", "lendingData");
    }
}
