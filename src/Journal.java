import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Journal extends PrintMedia {
    private String ISSN;
    private String issueNum;
    private LocalDate dateOfIssue;

    public Journal(String stockID, String title, Double price, String publisher, Genre genre, Integer numPages, String ISSN,
                   String issueNum, LocalDate dateOfIssue){
        super(stockID, title, price, publisher, genre, numPages);
        this.ISSN = ISSN;
        this.issueNum = issueNum;
        this.dateOfIssue = dateOfIssue;
    }

    public String getISSN() {
        return this.ISSN;
    }
    public String getIssueNum() {
        return this.issueNum;
    }
    public String getIssueDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        return this.dateOfIssue.format(formatter);
    }
}
