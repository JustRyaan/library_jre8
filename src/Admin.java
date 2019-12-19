import Data.Person;

import java.util.Scanner;

public class Admin extends Account implements UserControl {
    private static Scanner in = new Scanner(System.in);

    public Admin(String id, String password, Person person) {
        super(id, password, person);
    }

    @Override
    public String toString() {
        return super.getPerson().getFullName();
    }
}
