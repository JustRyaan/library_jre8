public enum CDType {

    CDDA("CD-DA"),
    CDR("CD-R"),
    CDRW("CD-RW");

    private final String displayName;

    CDType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Defines CD type.";
    }

}
