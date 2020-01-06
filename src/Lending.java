import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Lending implements Serializable {
    private LocalDate lendDate;
    private LocalDate dueDate;
    private String stockID;
    private String userID;

    // Stores details of all items currently out on loan
    private static HashMap<String, Lending> lentMedia = new HashMap<>();

    // Constructor
    public Lending(String stockID, String userID){
        this.lendDate = LocalDate.now();
        this.dueDate = lendDate.plusDays(10);
        this.stockID = stockID;
        this.userID = userID;
    }

    // Handles lending a media item
    public static Boolean lendItem(String stockID, String userID){
        // Get loan status based on stockID
        Lending lentItem = Lending.getLoanDetails(stockID);
        // Set the date formatter for prettier strings
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        // Another member has already loaned the item, alert user of the due date and suggest a reservation
        if(lentItem != null && lentItem.getUserID() != userID) {
            System.out.println("Error: Another member has already loaned this item.\nIt is due back on " +
                    lentItem.getDueDate().format(formatter) + ". Please consider reserving.");
            return false;
            // The member has already loaned the item, remind them of their due date
        } else if(lentItem != null) {
            System.out.println("You already have this item on loan. Please make sure to return it by " +
                    lentItem.getDueDate().format(formatter) + ".");
            return false;
        }
        // Create a new loan and store it in the loans map
        Lending lending = new Lending(stockID, userID);
        lentMedia.put(stockID, lending);
        return true;
    }

    // Returns information about an item on loan
    public static Lending getLoanDetails(String stockID) {
        return lentMedia.get(stockID);
    }

    // Getter methods
    public String getUserID() {
        return this.userID;
    }
    public LocalDate getDueDate() {
        return this.dueDate;
    }

    // Serializes HashMaps
    public static void store() {
        // Serialize
        try {
            FileOutputStream lmos = new FileOutputStream("/data/lendingData.ser");

            ObjectOutputStream out = new ObjectOutputStream(lmos);
            out.writeObject(lentMedia);

            out.close();
            lmos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    // Reads in serialized HashMaps
    public static void restore() {
        // Deserialize on startup
        try {
            FileInputStream lmis = new FileInputStream("/data/lendingData.ser");

            ObjectInputStream in = new ObjectInputStream(lmis);
            lentMedia = (HashMap) in.readObject();

            in.close();
            lmis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }
}
