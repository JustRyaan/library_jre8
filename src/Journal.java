import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public  class Journal extends PrintMedia {
    private String ISSN;
    private String issueNum;
    private LocalDate dateOfIssue;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Journal(String stockID, String title, Double price, String publisher, Genre genre, Integer numPages, String ISSN,
                   String issueNum, String dateOfIssue){
        super(stockID, title, price, publisher, genre, numPages);
        this.ISSN = ISSN;
        this.issueNum = issueNum;
        this.dateOfIssue = LocalDate.parse(dateOfIssue, formatter);
    }

    public String getISSN() {
        return this.ISSN;
    }
    public String getIssueNum() {
        return this.issueNum;
    }
    public String getIssueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LLLL/yyyy");
        return this.dateOfIssue.format(formatter);
    }

    @Override
    // Prints all info about the journal
    public String getFullInfo() {
        return "===============================================" +
                "\n       Journal #ID: " + this.getStockID() +
                "\n===============================================" +
                "\nTitle: " + this.getTitle() + " Vol. " + this.getIssueNum() +
                "\nDate: " + this.getIssueDate() +
                "\nSubject area: " + this.getGenre() +
                "\nISSN: " + this.getISSN() +
                "\nPublisher: " + this.getPublisher() +
                "\nNumber of pages: " + this.getNumPages() +
                "\nPrice: £" + this.getPrice();
    }
}
