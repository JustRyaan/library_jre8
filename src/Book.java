import Data.Genre;

public class Book {
    private String id;
    private String isbn;
    private String title;
    private String author;
    private Genre genre;
    private int numPages;
    private String publisher;

    public Book(String id, String isbn, String title, String author, Genre genre, int numPages, String publisher) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.numPages = numPages;
        this.publisher = publisher;
    }
}
