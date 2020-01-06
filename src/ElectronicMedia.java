import java.time.Duration;

public class ElectronicMedia extends Media {
    private Duration runtime;
    private String typeofCase;

    public ElectronicMedia(String stockID, String title, Double price, String publisher, Duration runtime, String typeofCase){
        super(stockID, title, price, publisher);
        this.runtime = runtime;
        this.typeofCase = typeofCase;
    }

    // Getter methods
    public Duration getRuntime() {
        return this.runtime;
    }
    public String getTypeofCase() {
        return this.typeofCase;
    }

    // Setter methods
    public void setRuntime(Duration duration) {
        this.runtime = runtime;
    }
    public void setTypeofCase(String typeofCase) {
        this.typeofCase = typeofCase;
    }
}
