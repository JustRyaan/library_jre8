public enum AccountStatus {
    ACTIVE("Active"),
    BLOCKED("Blocked"),
    CLOSED("Closed");

    private final String displayName;

    AccountStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
