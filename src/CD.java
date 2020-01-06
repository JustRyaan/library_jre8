import java.time.Duration;

public abstract class CD extends ElectronicMedia {
    private CDType type;
    private Integer numTracks;
    private String artist;

    public CD(String stockID, String title, Double price, String publisher, Duration runtime, String typeofCase,
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

    // Setter methods
    public void setType(CDType type) {
        this.type = type;
    }
    public void setNumTracks(Integer numTracks) {
        this.numTracks = numTracks;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }
}
