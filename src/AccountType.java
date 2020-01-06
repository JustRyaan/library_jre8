public enum AccountType {
    FULL("Full Member"),
    CASUAL("Casual Member"),
    STAFF("Staff Member");

    private final String displayName;

    AccountType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Defines account type.";
    }
}
