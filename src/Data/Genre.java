package Data;

public enum Genre {
    ADVENTURE("Adventure"),
    ART("Art"),
    AUTOBIOGRAPHY("Autobiography"),
    BIOGRAPHY("Biography"),
    CHILDRENS("Children's"),
    COOKBOOK("Cookbook"),
    COMIC("Comic Book"),
    DICTIONARY("Dictionary"),
    DRAMA("Drama"),
    ENCYCLOPEDIA("Encyclopedia"),
    FANTASY("Fantasy"),
    HEALTH("Health and Well-being"),
    HISTORICAL_FICTION("Historical Fiction"),
    HISTORY("History"),
    HUMOuR("Humour"),
    HORROR("Horror"),
    JOURNAL("Journal"),
    MATHS("Maths"),
    MYSTERY("Mystery"),
    RELIGION("Religion"),
    ROMANCE("Romance"),
    SATIRE("Satire"),
    SCIENCE("Science"),
    SCIENCE_FICTION("Science Fiction"),
    TEXTBOOK("Textbook"),
    THRILLER("Thriller"),
    TRAVEL("Travel");

    private final String displayName;

    Genre(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Defines book genre.";
    }
}
