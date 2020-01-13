import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Catalogue implements Serializable, fileRW {
    private static final String bookPath = "/data/media/books/";
    private static final String journalPath = "/data/media/journals/";
    private static final String videoPath = "/data/media/videos/";
    private static final String cdPath = "/data/media/cds/";

    // id map
    private static HashMap<String, Media> mediaIDs = new HashMap<>();

    // book maps
    private static HashMap<String, ArrayList<Media>> bookTitles = new HashMap<>();
    private static HashMap<String, ArrayList<Media>> bookISBNs = new HashMap<>();
    private static HashMap<String, ArrayList<Media>> bookAuthors = new HashMap<>();
    private static HashMap<String, ArrayList<Media>> bookPublishers = new HashMap<>();

    // journal maps
    private static HashMap<String, ArrayList<Media>> journalTitles = new HashMap<>();
    private static HashMap<String, ArrayList<Media>> journalISSNs = new HashMap<>();
    private static HashMap<String, ArrayList<Media>> journalPublishers = new HashMap<>();

    // video maps
    private static HashMap<String, ArrayList<Media>> videoTitles = new HashMap<>();
    private static HashMap<String, ArrayList<Media>> videoPublishers = new HashMap<>();

    // cd maps
    private static HashMap<String, ArrayList<Media>> cdTitles = new HashMap<>();
    private static HashMap<String, ArrayList<Media>> cdArtists = new HashMap<>();
    private static HashMap<String, ArrayList<Media>> cdPublishers = new HashMap<>();

    // Check if stockID has been used for media - TRUE if FOUND, otherwise false
    public static boolean checkStockID(String id) {
        return mediaIDs.containsKey(id);
    }

    public static String getMediaTitle(String id) {
        return mediaIDs.get(id).getTitle();
    }

    // Get needed keys from media items, remove all punctuation but leave spaces, and convert to lower case
    public static String getKey(Media media, String key){
        switch(key){
            case "title":
                return media.getTitle().replaceAll("\\p{Punct}", "").toLowerCase();
            case "publisher":
                return media.getPublisher().replaceAll("\\p{Punct}", "").toLowerCase();
            case "ISBN":
                Book isbn = (Book) media;
                return isbn.getISBN().replaceAll("\\p{Punct}", "").toLowerCase();
            case "author":
                Book author = (Book) media;
                return author.getAuthor().replaceAll("\\p{Punct}", "").toLowerCase();
            case "ISSN":
                Journal issn = (Journal) media;
                return issn.getISSN().replaceAll("\\p{Punct}", "").toLowerCase();
            case "artist":
                CD artist = (CD) media;
                return artist.getArtist().replaceAll("\\p{Punct}", "").toLowerCase();
        }
        return "";
    }

    // Gets the index of a media item within a list
    public static int getIndex(String id, ArrayList<Media> list) {
        for (int i = 0; i < list.size(); i++) {
            Media media = list.get(i);
            if (media.getStockID().equals(id)) return i;
        }
        return list.size();
    }

    //
    //      ADD ITEMS
    //

    // Add media item to map of IDs
    public static void addMedia(Media media) {
        String mediaType = media.getClass().getName();
        if(mediaIDs.containsKey(media.getStockID())) {
            // Prevents duplicates of dummy data after first run (IDs have to be unique)
            return;
        }
        mediaIDs.put(media.getStockID(), media);
        switch(mediaType) {
            case "Book":
                addBook(media);
                break;
            case "Journal":
                addJournal(media);
                break;
            case "Video":
                addVideo(media);
                break;
            case "CD":
                addCD(media);
                break;
        }
    }

    // adds book to respective maps
    public static void addBook(Media media) {
        addMedia(media);
        String title = getKey(media, "title");
        String ISBN = getKey(media, "ISBN");
        String author = getKey(media, "author");
        String publisher = getKey(media, "publisher");

        // Add to titles map
        bookTitles.putIfAbsent(title, new ArrayList<>());
        bookTitles.get(title).add(media);
        // Add to ISBN map
        bookISBNs.putIfAbsent(ISBN, new ArrayList<>());
        bookISBNs.get(ISBN).add(media);
        // Add to authors map
        bookAuthors.putIfAbsent(author, new ArrayList<>());
        bookAuthors.get(author).add(media);
        // Add to publishers map
        bookPublishers.putIfAbsent(publisher, new ArrayList<>());
        bookPublishers.get(publisher).add(media);
    }

    // adds journal to respective maps
    public static void addJournal(Media media) {
        addMedia(media);
        String title = getKey(media, "title");
        String ISSN = getKey(media, "ISSN");
        String publisher = getKey(media, "publisher");

        // Add to titles map
        journalTitles.putIfAbsent(title, new ArrayList<>());
        journalTitles.get(title).add(media);
        // Add to ISSN map
        journalISSNs.putIfAbsent(ISSN, new ArrayList<>());
        journalISSNs.get(ISSN).add(media);
        // Add to publishers map
        journalPublishers.putIfAbsent(publisher, new ArrayList<>());
        journalPublishers.get(publisher).add(media);
    }

    // adds video to respective maps
    public static void addVideo(Media media) {
        addMedia(media);
        String title = getKey(media, "title");
        String publisher = getKey(media, "publisher");

        // Add to titles map
        videoTitles.putIfAbsent(title, new ArrayList<>());
        videoTitles.get(title).add(media);
        // Add to publishers map
        videoPublishers.putIfAbsent(publisher, new ArrayList<>());
        videoPublishers.get(publisher).add(media);
    }

    // adds cd to respective maps
    public static void addCD(Media media) {
        addMedia(media);
        // Get keys, format
        String title = getKey(media, "title");
        String artist = getKey(media, "artist");
        String publisher = getKey(media, "publisher");

        // Add to titles map
        cdTitles.putIfAbsent(title, new ArrayList<>());
        cdTitles.get(title).add(media);
        // Add to ISSN map
        cdArtists.putIfAbsent(artist, new ArrayList<>());
        cdArtists.get(artist).add(media);
        // Add to publishers map
        cdPublishers.putIfAbsent(publisher, new ArrayList<>());
        cdPublishers.get(publisher).add(media);
    }

    //
    //      UPDATE ITEMS
    //

    // Selects update function
    public static void updateMedia(Media media) {
        String mediaType = media.getClass().getName();
        mediaIDs.replace(media.getStockID(), media);
        switch(mediaType) {
            case "Book":
                updateBook(media);
                break;
            case "Journal":
                updateJournal(media);
                break;
            case "Video":
                updateVideo(media);
                break;
            case "CD":
                updateCD(media);
                break;
        }
    }

    // Updates book lists in hashmaps
    public static void updateBook(Media media) {
        Book book = (Book) media;
        String id = book.getStockID();

        // Get title list and update
        String titlekey = getKey(book, "title");
        ArrayList<Media> titlelist = bookTitles.get(titlekey);
        titlelist.set(getIndex(id, titlelist), book);

        // Get isbn list and update
        String isbnkey = getKey(book, "ISBN");
        ArrayList<Media> isbnlist = bookISBNs.get(isbnkey);
        isbnlist.set(getIndex(id, isbnlist), book);

        // Get author list and update
        String authorkey = getKey(book, "author");
        ArrayList<Media> authorlist = bookAuthors.get(authorkey);
        authorlist.set(getIndex(id, authorlist), book);

        // Get publisher list and update
        String publisherkey = getKey(book, "publisher");
        ArrayList<Media> publisherlist = bookPublishers.get(publisherkey);
        publisherlist.set(getIndex(id, publisherlist), book);

        // Swap into hashmaps last to avoid collisions in the case that findIndex() takes too long
        bookTitles.replace(titlekey, titlelist);
        bookISBNs.replace(isbnkey, isbnlist);
        bookAuthors.replace(authorkey, authorlist);
        bookPublishers.replace(publisherkey, publisherlist);
    }

    // Updates journal lists in hashmaps
    public static void updateJournal(Media media) {
        Journal journal = (Journal) media;
        String id = journal.getStockID();

        // Get title list and update
        String titlekey = getKey(journal, "title");
        ArrayList<Media> titlelist = journalTitles.get(titlekey);
        titlelist.set(getIndex(id, titlelist), journal);

        // Get issn list and update
        String issnkey = getKey(journal, "ISSN");
        ArrayList<Media> issnlist = journalISSNs.get(issnkey);
        issnlist.set(getIndex(id, issnlist), journal);

        // Get publisher list and update
        String publisherkey = getKey(journal, "publisher");
        ArrayList<Media> publisherlist = journalPublishers.get(publisherkey);
        publisherlist.set(getIndex(id, publisherlist), journal);

        journalTitles.replace(titlekey, titlelist);
        journalISSNs.replace(issnkey, issnlist);
        journalPublishers.replace(publisherkey, publisherlist);
    }

    // Updates video lists in hashmaps
    public static void updateVideo(Media media) {
        Video video = (Video) media;
        String id = video.getStockID();

        // Get title list and update
        String titlekey = getKey(video, "title");
        ArrayList<Media> titlelist = videoTitles.get(titlekey);
        titlelist.set(getIndex(id, titlelist), video);

        // Get publisher list and update
        String publisherkey = getKey(video, "publisher");
        ArrayList<Media> publisherlist = videoPublishers.get(publisherkey);
        publisherlist.set(getIndex(id, publisherlist), video);

        videoTitles.replace(titlekey, titlelist);
        videoPublishers.replace(publisherkey, publisherlist);
    }

    // Updates cd lists in hashmaps
    public static void updateCD(Media media) {
        CD cd = (CD) media;
        String id = cd.getStockID();

        // Get title list and update
        String titlekey = getKey(cd, "title");
        ArrayList<Media> titlelist = cdTitles.get(titlekey);
        titlelist.set(getIndex(id, titlelist), cd);

        // Get artist list and update
        String artistkey = getKey(cd, "artist");
        ArrayList<Media> artistlist = cdArtists.get(artistkey);
        artistlist.set(getIndex(id, artistlist), cd);

        // Get publisher list and update
        String publisherkey = getKey(cd, "publisher");
        ArrayList<Media> publisherlist = cdPublishers.get(publisherkey);
        publisherlist.set(getIndex(id, publisherlist), cd);

        cdTitles.replace(titlekey, titlelist);
        cdArtists.replace(artistkey, artistlist);
        cdPublishers.replace(publisherkey, publisherlist);
    }

    //
    //      SERIALIZE & DESERIALIZE
    //

    // serializes maps
    public static void store() {
        // Write ids
        fileRW.write(mediaIDs, "/data/media/", "mediaIDs");

        // Write books
        fileRW.write(bookTitles, bookPath, "titles");
        fileRW.write(bookISBNs, bookPath, "isbns");
        fileRW.write(bookAuthors, bookPath, "authors");
        fileRW.write(bookPublishers, bookPath, "publishers");

        // Write journals
        fileRW.write(journalTitles, journalPath, "titles");
        fileRW.write(journalISSNs, journalPath, "issns");
        fileRW.write(journalPublishers, journalPath, "publishers");

        // Write videos
        fileRW.write(videoTitles, videoPath, "titles");
        fileRW.write(videoPublishers, videoPath, "publishers");

        // Write cds
        fileRW.write(cdTitles, cdPath, "titles");
        fileRW.write(cdArtists, cdPath, "artists");
        fileRW.write(cdPublishers, cdPath, "publishers");

    }

    // deserializes maps
    @SuppressWarnings("unchecked")
    public static void restore() {
        // deSerialize ids
        mediaIDs = fileRW.read("/data/media/", "mediaIDs");

        // deSerialize books
        bookTitles = fileRW.read(bookPath, "titles");
        bookISBNs = fileRW.read(bookPath, "isbns");
        bookAuthors = fileRW.read(bookPath, "authors");
        bookPublishers = fileRW.read(bookPath, "publishers");

        // Write journals
        journalTitles = fileRW.read(journalPath, "titles");
        journalISSNs = fileRW.read(journalPath, "issns");
        journalPublishers = fileRW.read(journalPath, "publishers");

        // Write videos
        videoTitles = fileRW.read(videoPath, "titles");
        videoPublishers = fileRW.read(videoPath, "publishers");

        // Write cds
        cdTitles = fileRW.read(cdPath, "titles");
        cdArtists = fileRW.read(cdPath, "artists");
        cdPublishers = fileRW.read(cdPath, "publishers");
    }

    //
    //      SEARCH
    //

    // Searches hashmaps utilising regex to find partial matches
    public static ArrayList<Media> searchHashMap(HashMap<String, ArrayList<Media>> map, String query) {
        double before = System.currentTimeMillis();
        query = query.replaceAll("\\p{Punct}", "").toLowerCase();
        ArrayList<Media> results = new ArrayList<>();
        String regex = "(.+)?(" + query + ")(.+)?";
        Pattern pattern = Pattern.compile(regex);
        // iterate over hashmap
        for(Map.Entry<String, ArrayList<Media>> entry : map.entrySet()) {
            Matcher matcher = pattern.matcher(entry.getKey());
            // if key matches query, add that list to the end of our results list
            if(matcher.matches()) results.addAll(entry.getValue());
        }
        double after = System.currentTimeMillis();
        double seconds = (after - before) / 1000;
        Library.clearScreen();
        System.out.print(
            "\n===============================================" +
            "\nSearch Results " +
            "\n===============================================");
        if(results.size() == 0){
            System.out.println("\nNo results found for \"" + query + "\".\n");
        } else {
            System.out.printf("\nFound " + results.size() + " results (%.2f seconds)", seconds);
        }
        return results;
    }
    
    // Book Searches
    public static ArrayList<Media> searchBookTitle(String title) {
        return searchHashMap(bookTitles, title);
    }
    public static ArrayList<Media> searchBookISBN(String ISBN) {
        return searchHashMap(bookISBNs, ISBN);
    }
    public static ArrayList<Media> searchBookAuthor(String author) {
        return searchHashMap(bookAuthors, author);
    }
    public static ArrayList<Media> searchBookPublisher(String publisher) {
        return searchHashMap(bookPublishers, publisher);
    }

    // Journal Searches
    public static ArrayList<Media> searchJournalTitle(String title) {
        return searchHashMap(journalTitles, title);
    }
    public static ArrayList<Media> searchJournalISSN(String ISSN) {
        return searchHashMap(journalISSNs, ISSN);
    }
    public static ArrayList<Media> searchJournalPublisher(String publisher) {
        return searchHashMap(journalPublishers, publisher);
    }

    // Video Searches
    public static ArrayList<Media> searchVideoTitle(String title) {
        return searchHashMap(videoTitles, title);
    }
    public static ArrayList<Media> searchVideoPublisher(String publisher) {
        return searchHashMap(videoPublishers, publisher);
    }

    // CD Searches
    public static ArrayList<Media> searchCDTitle(String title) {
        return searchHashMap(cdTitles, title);
    }
    public static ArrayList<Media> searchCDArtist(String artist) {
        return searchHashMap(cdArtists, artist);
    }
    public static ArrayList<Media> searchCDPublisher(String publisher) {
        return searchHashMap(cdPublishers, publisher);
    }

}
