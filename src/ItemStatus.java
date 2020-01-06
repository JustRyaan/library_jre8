public enum ItemStatus {
    AVAILABLE("Available"),
    UNAVAILABLE("Unavailable"),
    RESERVED("Reserved"),
    LOANED("Loaned");

    private final String displayName;

    ItemStatus(String displayName) {
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
