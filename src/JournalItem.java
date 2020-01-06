import java.time.LocalDate;

public class JournalItem extends Journal {

    public JournalItem(String stockID, String title, Double price, String publisher, Genre genre, Integer numPages,
                       String ISSN, String issueNum, LocalDate dateOfIssue) {
        super(stockID, title, price, publisher, genre, numPages, ISSN, issueNum, dateOfIssue);
    }

    // Prints all info about the journal
    public String getJournalInfo() {
        return "ID: " + this.getStockID() + "\nTitle: " + this.getTitle() + " " + this.getIssueNum() + "\nIssue Date: "
                + this.getIssueDate() + "\nISSN: " + this.getISSN() + "\nPublisher: " + this.getPublisher() +
                "\nSubject: " + this.getGenre() + "\nPages: " + this.getNumPages() + "\nPrice: £" + this.getPrice();
    }
}


