package Data;

public enum BookStatus {
    AVAILABLE("Available"),
    RESERVED("Reserved"),
    LOANED("Loaned");

    private final String displayName;

    BookStatus(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Defines book status.";
    }
}
