import java.time.Duration;
import java.time.LocalDate;

public class CDItem extends CD {

    // Constructor
    public CDItem(String stockID, String title, Double price, String publisher, Duration runtime,
                  String typeofCase, CDType type, Integer numTracks, String artist){
        super(stockID, title, price, publisher, runtime, typeofCase, type, numTracks, artist);
    }

    // Prints all info about the CD
    public String getCDInfo() {
        return "ID: " + this.getStockID() + "\n" + this.getTitle() + " by " + this.getArtist() + "\nPublisher: " +
                this.getPublisher() + "\nNumber of tracks: " + this.getNumTracks() + "\nTotal run time: " +
                this.getRuntime().toMinutes() + "m\nCD type: " + this.getCDType().displayName() + "\nType of case: " +
                getTypeofCase() + "\nPrice: £" + this.getPrice();
    }
}
