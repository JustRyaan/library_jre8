import java.time.Duration;

public abstract class ElectronicMedia extends Media {
    private String runtime;
    private String typeofCase;

    public ElectronicMedia(String stockID, String title, Double price, String publisher, String runtime, String typeofCase){
        super(stockID, title, price, publisher);
        this.runtime = runtime + " mins";
        this.typeofCase = typeofCase;
    }

    // Getter methods
    public String getRuntime() {
        return this.runtime;
    }
    public String getTypeofCase() {
        return this.typeofCase;
    }

}
