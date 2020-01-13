import java.io.Serializable;

public abstract class Media implements Serializable {
    private String stockID;
    private String title;
    private Double price;
    private String publisher;

    // Constructor
    public Media(String stockID, String title, Double price, String publisher){
        this.stockID = stockID;
        this.title = title;
        this.price = price;
        this.publisher = publisher;
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
    public String getFullInfo() {
        return "";
    }

}
