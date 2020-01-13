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

    @Override
    public int getLoansAvailable() {
        return this.maxLoans -= this.totalActiveLoans;
    }

    @Override
    public void editDetails() {
        Library.clearScreen();
        boolean menu = true;
        while(menu) {
            System.out.print(
                    "\n===============================================" +
                    "\nWhat would you like to update, "+ this.getPerson().getForename() + "?" +
                    "\n===============================================" +
                    "\n 1. Password" +
                    "\n 2. Forename" +
                    "\n 3. Surname" +
                    "\n 4. Phone number");
            if(this.getType().equals(AccountType.STAFF)) {
                System.out.print(
                    "\n 5. Email" +
                    "\n 6. Go back..."
            );
            } else {
                System.out.print(
                    "\n 5. Address" +
                    "\n 6. Go back..."
                );
            }
            System.out.println(
                    "\n Please choose an option: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    if(updatePassword()) UserStore.addMember(this);
                    break;
                case "2":
                    System.out.print("\nEnter new forename: ");
                    this.getPerson().setForename(in.nextLine());
                    UserStore.addMember(this);
                    System.out.println("Forename updated!");
                    break;
                case "3":
                    System.out.print("\nEnter new surname: ");
                    this.getPerson().setSurname(in.nextLine());
                    UserStore.addMember(this);
                    System.out.println("Surname updated!");
                    break;
                case "4":
                    System.out.print("\nEnter new phone number: ");
                    this.getPerson().setPhone(in.nextLine());
                    UserStore.addMember(this);
                    System.out.println("Phone number updated!");
                    break;
                case "5":
                    if(this.getType().equals(AccountType.STAFF)) {
                        System.out.print("\nEnter new email: ");
                        this.getPerson().setEmail(in.nextLine());
                        UserStore.addMember(this);
                        System.out.println("Email updated!");
                    } else {
                        updateAddress();
                    }
                    break;
                case "6":
                    menu = false;
                    break;
                default:
                    System.out.println("Please choose a valid option.");
                    break;
            }
        }
    }

    public void updateAddress() {
        Library.clearScreen();
        boolean menu = true;
        while(menu) {
            System.out.print(
                    "\n===============================================" +
                            "\nWhat would you like to update, " + this.getPerson().getForename() + "?" +
                            "\n===============================================" +
                            "\n 1. Street address" +
                            "\n 2. Town" +
                            "\n 3. Postcode" +
                            "\n 4. Go back..." +
                            "\n Please choose an option: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("\nEnter new street address: ");
                    this.getPerson().getAddress().setAddress(in.nextLine());
                    UserStore.addMember(this);
                    System.out.println("Address updated!");
                    break;
                case "2":
                    System.out.print("\nEnter new town: ");
                    this.getPerson().getAddress().setTown(in.nextLine());
                    UserStore.addMember(this);
                    System.out.println("Town updated!");
                    break;
                case "3":
                    System.out.print("\nEnter new postcode: ");
                    this.getPerson().getAddress().setPostcode(in.nextLine());
                    UserStore.addMember(this);
                    System.out.println("Postcode updated!");
                    break;
                case "4":
                    menu = false;
                    break;
                default:
                    System.out.println("Please choose a valid option.");
                    break;
            }
        }
    }

    // Abstract methods from account meant for admin class
    @Override
    public void addMember() {

    }
    @Override
    public void addMedia() {

    }
    @Override
    public void showEditMenu() {

    }

    
    public void decrementLoanAvailability(){
        this.totalActiveLoans += 1;
    }
    public void addLoanAvailability(){
        this.totalActiveLoans -= 1;
    }
}
