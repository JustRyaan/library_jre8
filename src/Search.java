import java.util.List;

public interface Search {
    // Book searches
    public List<BookItem> searchBookTitle(String title);
    public List<BookItem> searchBookISBN(String ISBN);
    public List<BookItem> searchBookAuthor(String author);
    public List<BookItem> searchBookPublisher(String publisher);

    // Journal searches
    public List<JournalItem> searchJournalTitle(String title);
    public List<JournalItem> searchJournalISSN(String ISSN);
    public List<JournalItem> searchJournalPublisher(String publisher);

    // Video searches
    public List<VideoItem> searchVideoTitle(String title);
    public List<VideoItem> searchVideoPublisher(String publisher);

    // CD searches
    public List<CDItem> searchCDTitle(String title);
    public List<CDItem> searchCDArtist(String artist);
    public List<CDItem> searchCDPublisher(String publisher);

}
