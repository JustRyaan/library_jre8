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

    @Override
    // Prints all info about the book
    public String getFullInfo() {
        return "===============================================" +
                "\n       Book #ID: " + this.getStockID() +
                "\n===============================================" +
                "\nTitle: " + this.getTitle() +
                "\nAuthor: " + this.getAuthor() +
                "\nGenre: " + this.getGenre() +
                "\nISBN: " + this.getISBN() +
                "\nPublisher: " + this.getPublisher() +
                "\nNumber of pages: " + this.getNumPages() +
                "\nPrice: £" + this.getPrice();
    }

}
