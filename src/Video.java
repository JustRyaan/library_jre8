import java.time.Duration;

public abstract class Video extends ElectronicMedia {
    private VideoFormat format;
    private Genre genre;

    public Video(String stockID, String title, Double price, String publisher, Duration runtime,
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

    // Setter methods
    public void setFormat(VideoFormat format) {
        this.format = format;
    }
    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
