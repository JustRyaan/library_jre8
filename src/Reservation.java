import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reservation implements Serializable {
    private LocalDate reserveDate;
    private String stockID;
    private String userID;
    private String title;

    // Stores all active reservations
    private static HashMap<String, Reservation> reservedMedia = new HashMap<>();

    // Constructor
    public Reservation(String stockID, String userID) {
        this.reserveDate = LocalDate.now();
        this.stockID = stockID;
        this.userID = userID;
        this.title = Catalogue.getMediaTitle(stockID);
    }

    // Checks to see if an item is currently reserved
    public static boolean checkReservation(String stockID) {
        return reservedMedia.containsKey(stockID);
    }

    // Handles reserving a media item
    public static void reserveItem(String stockID, String userID){
        // Get reservation status based on stockID
        Reservation reservedItem = getReservation(stockID);
        // Get loan status based on stockID
        Lending lentItem = Lending.getLoanDetails(stockID);

        // Another member has already reserved the item
        if(reservedItem != null && !reservedItem.getUserID().equals(userID)){
            System.out.println("\nSorry, another member has already reserved this item.");
            return;
            // User is loaning and wants to reserve
        } else if (lentItem != null && lentItem.getUserID().equals(userID)){
            System.out.println("\nSorry, you can't reserve an item you're already loaning.");
            return;
            // User has already reserved the item
        } else if (reservedItem != null) {
            System.out.println("\nYou've already reserved this item.");
            return;
        }
        // Create new reservation and add to our map
        Reservation reservation = new Reservation(stockID, userID);
        reservedMedia.put(stockID, reservation);
        System.out.println("\nItem reserved! ");
        if(lentItem == null) {
            System.out.println("You can pick it up immediately.");
        } else {
            System.out.println("You can pick it up from " + lentItem.getDueDate());
        }
    }

    public static void cancelReservation(String stockID, String userID) {
        Reservation reservation = getReservation(stockID);

        if(reservation != null && reservation.getUserID().equals(userID)){
            System.out.println("\nReservation removed.");
            reservedMedia.remove(stockID);
            return;
        }
        System.out.println("\nYou don't currently have a reservation against that item.");
    }

    // Returns information about a reservation
    public static Reservation getReservation(String stockID) {
        return reservedMedia.get(stockID);
    }

    // Lists all reservations or user's reservations
    public static void listReservations (String userID) {
        if(reservedMedia.isEmpty()){
            System.out.println("\n\nThere are no active reservations.");
        } else {
            HashMap<String, List<Reservation>> reservations = new HashMap<>();
            // Fill hashmap searchable by user id
            for (Map.Entry<String, Reservation> entry : reservedMedia.entrySet()) {
                reservations.putIfAbsent(entry.getValue().getUserID(), new ArrayList<>());
                reservations.get(entry.getValue().getUserID()).add(entry.getValue());
            }

            // Lists all reservations
            if (userID.equals("all")) {
                for (Map.Entry<String, List<Reservation>> entry : reservations.entrySet()) {
                    System.out.println("\n" + UserStore.getMember(entry.getKey()).getPerson().getFullName() + " (" +
                            entry.getKey() + "): ");
                    for (int i = 0; i < entry.getValue().size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + entry.getValue().get(i).getTitle() + " (" +
                                entry.getValue().get(i).getStockID() + ")");
                    }
                }
            } else {
                // Lists only reservations by requesting user
                System.out.println(
                        "\n===============================================" +
                                "\n     Your Reservations" +
                                "\n===============================================");
                ArrayList<Reservation> items = (ArrayList<Reservation>) reservations.get(userID);
                if (items == null) {
                    System.out.println("  You have no active reservations.");
                } else {
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println("  " + (i + 1) + ". " + items.get(i).getTitle());
                    }
                }
            }
        }
    }

    // Getter methods
    public String getUserID() {
        return this.userID;
    }
    public String getStockID() {
        return this.stockID;
    }
    public String getTitle() {
        return this.title;
    }

    // Serializes HashMaps
    public static void store() {
        // Serialize
        fileRW.write(reservedMedia, "/data/transactions/", "reservationData");
    }
    // Reads in serialized HashMaps
    public static void restore() {
        // Deserialize on startup
        reservedMedia = fileRW.read("/data/transactions/", "reservationData");
    }
}
