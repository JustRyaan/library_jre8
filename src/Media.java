import java.io.Serializable;
import java.time.LocalDate;

public abstract class Media implements Serializable {
    private String stockID;
    private String title;
    private Double price;
    private String publisher;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private ItemStatus status;

    // Constructor
    public Media(String stockID, String title, Double price, String publisher){
        this.stockID = stockID;
        this.title = title;
        this.price = price;
        this.publisher = publisher;
        this.borrowDate = null;
        this.dueDate = null;
        this.status = ItemStatus.AVAILABLE;
    }

    // Sets status to loaned, providing it is available
    public boolean rentItem(String userID) {
        // Returns false if the item already exists in the lent items map
        if(!Lending.lendItem(this.getStockID(), userID)){
            return false;
        }
        this.setItemStatus(ItemStatus.LOANED);
        return true;
    }

    // Sets status to reserved, providing it is available or on loan
    public boolean reserveItem(String userID) {
        // Returns false if the item is already in the reserved items map
        if(!Reservation.reserveItem(this.getStockID(), userID)){
            return false;
        }
        this.setItemStatus(ItemStatus.RESERVED);
        return true;
    }

    // Getter methods
    public String getStockID() { return this.stockID;}
    public String getTitle() {
        return this.title;
    }
    public String getPrice() {
        return this.price.toString();
    }
    public String getPublisher() {
        return this.publisher;
    }
    public LocalDate getBorrowDate() {
        return this.borrowDate;
    }
    public LocalDate getDueDate() {
        return this.dueDate;
    }
    public ItemStatus getStatus() {
        return this.status;
    }

    // Setter methods
    public void setItemStatus(ItemStatus status){
        this.status = status;
    }
}
