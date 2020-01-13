import java.time.Duration;

public class CD extends ElectronicMedia {
    private CDType type;
    private Integer numTracks;
    private String artist;

    public CD(String stockID, String title, Double price, String publisher, String runtime, String typeofCase,
              CDType type, Integer numTracks, String artist) {
        super(stockID, title, price, publisher, runtime, typeofCase);
        this.type = type;
        this.numTracks = numTracks;
        this.artist = artist;
    }

    // Getter methods
    public CDType getCDType() {
        return this.type;
    }
    public Integer getNumTracks() {
        return this.numTracks;
    }
    public String getArtist() {
        return this.artist;
    }

    @Override
    // Prints all info about the CD
    public String getFullInfo() {
        return "===============================================" +
                "\n       CD #ID: " + this.getStockID() +
                "\n===============================================" +
                "\nTitle: " + this.getTitle() +
                "\nArtist: " + this.getArtist() +
                "\nPublisher: " + this.getPublisher() +
                "\nTracks: " + this.getNumTracks() +
                "\nRuntime: " + this.getRuntime() +
                "\nCase type: " + this.getTypeofCase() +
                "\nCD type: " + this.getCDType() +
                "\nPrice: £" + this.getPrice();
    }

}
