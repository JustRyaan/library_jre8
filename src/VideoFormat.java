public enum VideoFormat {

    VCR("VCR"),
    DVD("DVD"),
    BLURAY("Blu-ray"),
    DIGITAL("Digital");

    private final String displayName;

    VideoFormat(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "Defines video format.";
    }

}
