import java.time.Duration;

public class Video extends ElectronicMedia {
    private VideoFormat format;
    private Genre genre;

    public Video(String stockID, String title, Double price, String publisher, String runtime,
                 String typeofCase, VideoFormat format, Genre genre) {
        super(stockID, title, price, publisher, runtime, typeofCase);
        this.format = format;
        this.genre = genre;
    }

    // Getter methods
    public VideoFormat getFormat() {
        return this.format;
    }
    public Genre getGenre() {
        return this.genre;
    }


    @Override
    // Prints all info about the video
    public String getFullInfo() {
        return "===============================================" +
                "\n       Video #ID: " + this.getStockID() +
                "\n===============================================" +
                "\nTitle: " + this.getTitle() +
                "\nPublisher: " + this.getPublisher() +
                "\nGenre: " + this.getGenre() +
                "\nRuntime: " + this.getRuntime() +
                "\nVideo format: " + this.getFormat() +
                "\nCase type: " + this.getTypeofCase() +
                "\nPrice: £" + this.getPrice();
    }

}
