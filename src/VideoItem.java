import java.time.Duration;
import java.time.LocalDate;

public class VideoItem extends Video {

    public VideoItem(String stockID, String title, Double price, String publisher, Duration runtime,
                 String typeofCase, VideoFormat format, Genre genre) {
        super(stockID, title, price, publisher, runtime, typeofCase, format, genre);
    }

    // Prints all info about the video
    public String getVideoInfo() {
        return "ID: " + this.getStockID() + "\n" + this.getTitle() + "\nGenre: " + this.getGenre().displayName() +
                "\nPublisher: " + this.getPublisher() + "\nTotal run time: " + this.getRuntime().toMinutes() +
                "m\nFormat: " + this.getFormat().displayName() + "\nType of case: " + this.getTypeofCase() + "\nPrice: £"
                + this.getPrice();
    }
}
