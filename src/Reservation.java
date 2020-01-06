import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;

public class Reservation implements Serializable {
    private LocalDate reserveDate;
    private String stockID;
    private String userID;

    // Stores all active reservations
    private static HashMap<String, Reservation> reservedMedia = new HashMap<>();

    // Constructor
    public Reservation(String stockID, String userID) {
        this.reserveDate = LocalDate.now();
        this.stockID = stockID;
        this.userID = userID;
    }

    // Handles reserving a media item
    public static Boolean reserveItem(String stockID, String userID){
        // Get reservation status based on stockID
        Reservation reservedItem = Reservation.getReservation(stockID);
        // Get loan status based on stockID
        Lending lentItem = Lending.getLoanDetails(stockID);
        // Another member has already reserved the item
        if(reservedItem != null && reservedItem.getUserID() != userID){
            System.out.println("Error: Another member has already reserved this item.");
            return false;
            // User is loaning and wants to reserve
        } else if (lentItem != null && lentItem.getUserID() == userID){
            System.out.println("Sorry, you can't reserve an item you're already loaning.");
            return false;
            // User has already reserved the item
        } else if (reservedItem != null) {
            System.out.println("You've already reserved this item.");
            return false;
        }
        // Create new reservation and add to our map
        Reservation reservation = new Reservation(stockID, userID);
        reservedMedia.put(stockID, reservation);
        return true;
    }

    // Returns information about a reservation
    public static Reservation getReservation(String stockID) {
        return reservedMedia.get(stockID);
    }

    // Getter methods
    public String getUserID() {
        return this.userID;
    }

    // Serializes HashMaps
    public static void store() {
        // Serialize
        try {
            FileOutputStream ros = new FileOutputStream("reservationData");

            ObjectOutputStream out = new ObjectOutputStream(ros);
            out.writeObject(reservedMedia);

            out.close();
            ros.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    // Reads in serialized HashMaps
    public static void restore() {
        // Deserialize on startup
        try {
            FileInputStream ris = new FileInputStream("reservationData");

            ObjectInputStream in = new ObjectInputStream(ris);
            reservedMedia = (HashMap) in.readObject();

            in.close();
            ris.close();
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
