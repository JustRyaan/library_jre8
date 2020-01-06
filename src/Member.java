public class Member extends Account {

    private int totalActiveLoans;
    private int maxLoans;

    public Member(String id, String password, AccountStatus status, AccountType type, Person person) {
        super(id, password, status, type, person);
        this.totalActiveLoans = 0;
        if(type == AccountType.FULL){
            this.maxLoans = 4;
        } else if(type == AccountType.STAFF) {
            this.maxLoans = 6;
        }
        else {
            this.maxLoans = 0;
        }
    }

    @Override
    public String toString() {
        return super.getPerson().getFullName();
    }
}
