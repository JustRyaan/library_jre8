import java.util.HashSet;
import java.util.Set;

public enum Genre {
    ADVENTURE("Adventure"),
    ART("Art"),
    AUTOBIOGRAPHY("Autobiography"),
    BIOGRAPHY("Biography"),
    CHILDRENS("Childrens"),
    COOKBOOK("Cookbook"),
    COMIC("Comic Book"),
    DICTIONARY("Dictionary"),
    DRAMA("Drama"),
    ENCYCLOPEDIA("Encyclopedia"),
    FANTASY("Fantasy"),
    HEALTH_AND_WELL_BEING("Health and Well Being"),
    HISTORICAL_FICTION("Historical Fiction"),
    HISTORY("History"),
    HUMOUR("Humour"),
    HORROR("Horror"),
    JOURNAL("Journal"),
    MATHS("Maths"),
    MYSTERY("Mystery"),
    RELIGION("Religion"),
    ROMANCE("Romance"),
    SATIRE("Satire"),
    SCIENCE("Science"),
    SCIENCE_FICTION("Science Fiction"),
    SELF_IMPROVEMENT("Self Improvement"),
    TEXTBOOK("Textbook"),
    THRILLER("Thriller"),
    TRAVEL("Travel");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    private final static Set<String> values = new HashSet<>(Genre.values().length);

    static {
        for(Genre g: Genre.values())
            values.add(g.name());
    }

    public static Boolean contains(String value) {
        return values.contains(value);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
