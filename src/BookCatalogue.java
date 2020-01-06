import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookCatalogue implements Serializable {
    // TODO: add book catalogue
    private static HashMap<String, List<BookItem>> bookTitles = new HashMap<>();
    private static HashMap<String, List<BookItem>> bookISBNs = new HashMap<>();
    private static HashMap<String, List<BookItem>> bookAuthors = new HashMap<>();
    private static HashMap<String, List<BookItem>> bookPublishers = new HashMap<>();

    public void addBook(BookItem book) {
        // Get needed keys from the book
        String title = book.getTitle();
        String ISBN = book.getISBN();
        String author = book.getAuthor();
        String publisher = book.getPublisher();

        // Add to titles map
        bookTitles.putIfAbsent(title, new ArrayList<BookItem>());
        bookTitles.get(title).add(book);
        // Add to ISBN map
        bookISBNs.putIfAbsent(ISBN, new ArrayList<BookItem>());
        bookISBNs.get(ISBN).add(book);
        // Add to authors map
        bookAuthors.putIfAbsent(author, new ArrayList<BookItem>());
        bookAuthors.get(author).add(book);
        // Add to publishers map
        bookPublishers.putIfAbsent(publisher, new ArrayList<BookItem>());
        bookPublishers.get(publisher).add(book);
    }

    public static void store() {

    }

}
