package Data;

public enum AccountStatus {
    ACTIVE("Active"),
    BLOCKED("Blocked"),
    CLOSED("Closed");

    private final String displayName;

    AccountStatus(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Defines account status.";
    }
}
