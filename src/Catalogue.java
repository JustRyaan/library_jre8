import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("typo")
public class Catalogue implements Serializable, Search {
    private static final String bookPath = "/data/media/books/";
    private static final String journalPath = "/data/media/journals";
    private static final String videoPath = "/data/media/videos";
    private static final String cdPath = "/data/media/cds";

    // book maps
    private static HashMap<String, List<BookItem>> bookTitles = new HashMap<>();
    private static HashMap<String, List<BookItem>> bookISBNs = new HashMap<>();
    private static HashMap<String, List<BookItem>> bookAuthors = new HashMap<>();
    private static HashMap<String, List<BookItem>> bookPublishers = new HashMap<>();

    // journal maps
    private static HashMap<String, List<JournalItem>> journalTitles = new HashMap<>();
    private static HashMap<String, List<JournalItem>> journalISSNs = new HashMap<>();
    private static HashMap<String, List<JournalItem>> journalPublishers = new HashMap<>();

    // video maps
    private static HashMap<String, List<VideoItem>> videoTitles = new HashMap<>();
    private static HashMap<String, List<VideoItem>> videoPublishers = new HashMap<>();

    // cd maps
    private static HashMap<String, List<CDItem>> cdTitles = new HashMap<>();
    private static HashMap<String, List<CDItem>> cdArtists = new HashMap<>();
    private static HashMap<String, List<CDItem>> cdPublishers = new HashMap<>();

    // adds book to respective maps
    public static void addBook(BookItem book) {
        // Get needed keys from the book, remove all punctuation but leave spaces, and convert to lower case
        String title = book.getTitle().replaceAll("\\p{Punct}", "").toLowerCase();
        String ISBN = book.getISBN().replaceAll("\\p{Punct}", "").toLowerCase();
        String author = book.getAuthor().replaceAll("\\p{Punct}", "").toLowerCase();
        String publisher = book.getPublisher().replaceAll("\\p{Punct}", "").toLowerCase();

        // Add to titles map
        bookTitles.putIfAbsent(title, new ArrayList<>());
        bookTitles.get(title).add(book);
        // Add to ISBN map
        bookISBNs.putIfAbsent(ISBN, new ArrayList<>());
        bookISBNs.get(ISBN).add(book);
        // Add to authors map
        bookAuthors.putIfAbsent(author, new ArrayList<>());
        bookAuthors.get(author).add(book);
        // Add to publishers map
        bookPublishers.putIfAbsent(publisher, new ArrayList<>());
        bookPublishers.get(publisher).add(book);
    }

    // adds journal to respective maps
    public static void addJournal(JournalItem journal) {
        // Get needed keys from the journal and format
        String title = journal.getTitle().replaceAll("\\p{Punct}", "").toLowerCase();
        String ISSN = journal.getISSN().replaceAll("\\p{Punct}", "").toLowerCase();
        String publisher = journal.getPublisher().replaceAll("\\p{Punct}", "").toLowerCase();
    }

    // adds video to respective maps
    public static void addVideo(VideoItem video) {
        // Get keys, format
        String title = video.getTitle().replaceAll("\\p{Punct}", "").toLowerCase();
        String publisher = video.getPublisher().replaceAll("\\p{Punct}", "").toLowerCase();
    }

    // adds cd to respective maps
    public static void addCD(CDItem cd) {
        // Get keys, format
        String title = cd.getTitle().replaceAll("\\p{Punct}", "").toLowerCase();
        String artist = cd.getArtist().replaceAll("\\p{Punct}", "").toLowerCase();
        String publisher = cd.getPublisher().replaceAll("\\p{Punct}", "").toLowerCase();
    }

    // serializes maps
    public static void store() {
        // Serialize books
        try {
            // Setup output streams
            FileOutputStream btitleos = new FileOutputStream(bookPath + "titles.ser");
            FileOutputStream isbnos = new FileOutputStream(bookPath + "isbns.ser");
            FileOutputStream authorsos = new FileOutputStream(bookPath + "authors.ser");
            FileOutputStream bpublishersos = new FileOutputStream(bookPath + "publishers.ser");

            // Write titles to file
            ObjectOutputStream out = new ObjectOutputStream(btitleos);
            out.writeObject(bookTitles);

            // Write ISBNs to file
            out = new ObjectOutputStream(isbnos);
            out.writeObject(bookISBNs);

            // Write authors to file
            out = new ObjectOutputStream(authorsos);
            out.writeObject(bookAuthors);

            // Write publishers to file
            out = new ObjectOutputStream(bpublishersos);
            out.writeObject(bookPublishers);

            // Close book streams
            out.close();
            btitleos.close();
            isbnos.close();
            authorsos.close();
            bpublishersos.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        // Serialize journals
        try {
            // Setup output streams
            FileOutputStream jtitleos = new FileOutputStream(journalPath + "titles.ser");
            FileOutputStream issnos = new FileOutputStream(journalPath + "issns.ser");
            FileOutputStream jpublishersos = new FileOutputStream(journalPath + "publishers.ser");

            // Write titles to file
            ObjectOutputStream out = new ObjectOutputStream(jtitleos);
            out.writeObject(journalTitles);

            // Write ISBNs to file
            out = new ObjectOutputStream(issnos);
            out.writeObject(journalISSNs);

            // Write publishers to file
            out = new ObjectOutputStream(jpublishersos);
            out.writeObject(journalPublishers);

            // Close book streams
            out.close();
            jtitleos.close();
            issnos.close();
            jpublishersos.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Serialize videos
        try {
            // Setup output streams
            FileOutputStream vtitleos = new FileOutputStream(videoPath + "titles.ser");
            FileOutputStream vpublishersos = new FileOutputStream(videoPath + "publishers.ser");

            // Write titles to file
            ObjectOutputStream out = new ObjectOutputStream(vtitleos);
            out.writeObject(videoTitles);

            // Write publishers to file
            out = new ObjectOutputStream(vpublishersos);
            out.writeObject(videoPublishers);

            // Close book streams
            out.close();
            vtitleos.close();
            vpublishersos.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // Serialize CDs
        try {
            // Setup output streams
            FileOutputStream cdtitleos = new FileOutputStream(cdPath + "titles.ser");
            FileOutputStream artistos = new FileOutputStream(cdPath + "artists.ser");
            FileOutputStream cdpublishersos = new FileOutputStream(cdPath + "publishers.ser");

            // Write titles to file
            ObjectOutputStream out = new ObjectOutputStream(cdtitleos);
            out.writeObject(cdTitles);

            // Write ISBNs to file
            out = new ObjectOutputStream(artistos);
            out.writeObject(cdArtists);

            // Write publishers to file
            out = new ObjectOutputStream(cdpublishersos);
            out.writeObject(cdPublishers);

            // Close book streams
            out.close();
            cdtitleos.close();
            artistos.close();
            cdpublishersos.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // deserializes maps
    @SuppressWarnings("unchecked")
    public static void restore() {
        // deSerialize books
        try {
            // Setup output streams
            FileInputStream btitleis = new FileInputStream(bookPath + "titles.ser");
            FileInputStream isbnis = new FileInputStream(bookPath + "isbns.ser");
            FileInputStream authorsis = new FileInputStream(bookPath + "authors.ser");
            FileInputStream bpublishersis = new FileInputStream(bookPath + "publishers.ser");

            // Write titles to file
            ObjectInputStream in = new ObjectInputStream(btitleis);
            bookTitles = (HashMap<String, List<BookItem>>) in.readObject();

            // Write ISBNs to file
            in = new ObjectInputStream(isbnis);
            bookISBNs = (HashMap<String, List<BookItem>>) in.readObject();

            // Write authors to file
            in = new ObjectInputStream(authorsis);
            bookAuthors = (HashMap<String, List<BookItem>>) in.readObject();

            // Write publishers to file
            in = new ObjectInputStream(bpublishersis);
            bookPublishers = (HashMap<String, List<BookItem>>) in.readObject();

            // Close book streams
            in.close();
            btitleis.close();
            isbnis.close();
            authorsis.close();
            bpublishersis.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }

        // deSerialize journals
        try {
            // Setup output streams
            FileInputStream jtitleis = new FileInputStream(journalPath + "titles.ser");
            FileInputStream issnis = new FileInputStream(journalPath + "issns.ser");
            FileInputStream jpublishersis = new FileInputStream(journalPath + "publishers.ser");

            // Write titles to file
            ObjectInputStream in = new ObjectInputStream(jtitleis);
            journalTitles = (HashMap<String, List<JournalItem>>) in.readObject();

            // Write ISBNs to file
            in = new ObjectInputStream(issnis);
            journalISSNs = (HashMap<String, List<JournalItem>>) in.readObject();

            // Write publishers to file
            in = new ObjectInputStream(jpublishersis);
            journalPublishers = (HashMap<String, List<JournalItem>>) in.readObject();

            // Close book streams
            in.close();
            jtitleis.close();
            issnis.close();
            jpublishersis.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }

        // deSerialize videos
        try {
            // Setup output streams
            FileInputStream vtitleis = new FileInputStream(videoPath + "titles.ser");
            FileInputStream vpublishersis = new FileInputStream(videoPath + "publishers.ser");

            // Write titles to file
            ObjectInputStream in = new ObjectInputStream(vtitleis);
            videoTitles = (HashMap<String, List<VideoItem>>) in.readObject();

            // Write publishers to file
            in = new ObjectInputStream(vpublishersis);
            videoPublishers = (HashMap<String, List<VideoItem>>) in.readObject();

            // Close book streams
            in.close();
            vtitleis.close();
            vpublishersis.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }

        // deSerialize CDs
        try {
            // Setup output streams
            FileInputStream cdtitleis = new FileInputStream(cdPath + "titles.ser");
            FileInputStream artistis = new FileInputStream(cdPath + "artists.ser");
            FileInputStream cdpublishersis = new FileInputStream(cdPath + "publishers.ser");

            // Write titles to file
            ObjectInputStream in = new ObjectInputStream(cdtitleis);
            cdTitles = (HashMap<String, List<CDItem>>) in.readObject();

            // Write ISBNs to file
            in = new ObjectInputStream(artistis);
            cdArtists = (HashMap<String, List<CDItem>>) in.readObject();

            // Write publishers to file
            in = new ObjectInputStream(cdpublishersis);
            cdPublishers = (HashMap<String, List<CDItem>>) in.readObject();

            // Close book streams
            in.close();
            cdtitleis.close();
            artistis.close();
            cdpublishersis.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public List<BookItem> searchBookTitle(String title) {

        return bookTitles.get(title.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<BookItem> searchBookISBN(String ISBN) {
        return bookTitles.get(ISBN.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<BookItem> searchBookAuthor(String author) {
        return bookTitles.get(author.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<BookItem> searchBookPublisher(String publisher) {
        return bookTitles.get(publisher.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<JournalItem> searchJournalTitle(String title) {
        return journalTitles.get(title.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<JournalItem> searchJournalISSN(String ISSN) {
        return journalISSNs.get(ISSN.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<JournalItem> searchJournalPublisher(String publisher) {
        return journalPublishers.get(publisher.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<VideoItem> searchVideoTitle(String title) {
        return videoTitles.get(title.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<VideoItem> searchVideoPublisher(String publisher) {
        return videoPublishers.get(publisher.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<CDItem> searchCDTitle(String title) {
        return cdTitles.get(title.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<CDItem> searchCDArtist(String artist) {
        return cdArtists.get(artist.replaceAll("\\p{Punct}", "").toLowerCase());
    }

    @Override
    public List<CDItem> searchCDPublisher(String publisher) {
        return cdPublishers.get(publisher.replaceAll("\\p{Punct}", "").toLowerCase());
    }
}
