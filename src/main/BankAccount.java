package main;

public class BankAccount {

    private double balance;
    private double interestRate;

    public BankAccount() {
        this(0.0);
    }

    public BankAccount(double interestRate) {
        this.balance = 0;
        setInterestRate(interestRate);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        if (amount > balance) {
            throw new IllegalArgumentException();
        }
        balance -= amount;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException();
        }
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
    }

}
