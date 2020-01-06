public class Book extends PrintMedia {
    private String ISBN;
    private String author;

    public Book(String stockID, String title, Double price, Genre genre, String publisher, Integer numPages, String ISBN, String author){
        super(stockID, title, price, publisher, genre, numPages);
        this.ISBN = ISBN;
        this.author = author;
    }

    // Getter methods
    public String getISBN() {
        return this.ISBN;
    }
    public String getAuthor() {
        return this.author;
    }

    // Setter methods
    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }
    public void setAuthor(String author){
        this.author = author;
    }
}
