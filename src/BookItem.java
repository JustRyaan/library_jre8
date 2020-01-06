import java.time.LocalDate;

public class BookItem extends Book {

    // Constructor
    public BookItem(String stockID, String title, Double price, Genre genre, String publisher, Integer numPages, String ISBN, String author){
        super(stockID, title, price, genre, publisher, numPages, ISBN, author);
    }

    // Prints all info about the book
    public String getBookInfo() {
        return "ID: " + this.getStockID() + "\n" + this.getTitle() + " by " + this.getAuthor() + "\nISBN: " +
                this.getISBN() + "\nPublisher: " + this.getPublisher() + "\nGenre: " + this.getGenre() + "\nPages: "
                + this.getNumPages() + "\nPrice: £" + this.getPrice();
    }

}
