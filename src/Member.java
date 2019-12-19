import Data.AccountStatus;
import Data.AccountType;
import Data.Person;

import java.io.Serializable;

public class Member extends Account {

    private int totalActiveLoans;

    public Member(String id, String password, AccountStatus status, AccountType type, Person person) {
        super(id, password, status, type, person);
        this.totalActiveLoans = 0;
    }

    @Override
    public String toString() {
        return super.getPerson().getFullName();
    }
}
