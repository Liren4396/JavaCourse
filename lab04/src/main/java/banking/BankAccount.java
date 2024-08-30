package banking;

public class BankAccount {

    private double currentAmount;

    BankAccount(double currentAmount) throws Exception {
        if (currentAmount < 0) {
            //System.out.println("current amount less than 0");
            throw new Exception("current amount less than 0");
            //return;
        }
        this.currentAmount = 0;
    }
    public void deposit(double money) throws Exception {
        if (money < 0) {
            //System.out.println("deposit less than 0");
            //return;
            throw new Exception("current amount less than 0");
        }
        this.currentAmount += money;
    }
    public double withdraw(double money) throws Exception {
        return (this.currentAmount - money) < 0 ? 0.0 : this.currentAmount - money;
    }
}
