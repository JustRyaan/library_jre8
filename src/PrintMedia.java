public abstract class PrintMedia extends Media {
    private Genre genre;
    private Integer numPages;

    public PrintMedia(String stockID, String title, Double price, String publisher, Genre genre, Integer numPages){
        super(stockID, title, price, publisher);
        this.genre = genre;
        this.numPages = numPages;
    }

    // Getter methods
    public Genre getGenre() {
        return this.genre;
    }
    public String getNumPages() {
        return this.numPages.toString();
    }


}
