package banking;

public class LoggedBankAccount extends BankAccount {

    LoggedBankAccount(double currentAmount) throws Exception {
        super(currentAmount);
    }
    public void deposit(double deposit) throws Exception {
        super.deposit(deposit);
        System.out.println("current money: " + deposit);
    }

    public double withdraw(double withdraw) throws Exception {
        double amount = super.withdraw(withdraw);
        if (amount == 0) {
            //System.out.println("withdraw error");
            //return amount;
            throw new Exception("current amount less than 0");
        }
        System.out.println("current money: " + amount);
        return amount;
    }
}
