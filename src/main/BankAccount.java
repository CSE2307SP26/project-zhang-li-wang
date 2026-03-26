package main;

public class BankAccount {

    private double balance;

    public BankAccount() {
        this.balance = 0;
    }

    public void deposit(double amount) {
        if(amount > 0) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public double getBalance() {
        return this.balance;
    }

    private boolean isClosed;

    public boolean isClosed() {
        return this.isClosed;
    }

    public void close() {
        this.isClosed = true;
        System.out.println("Your account has been closed.");
    }
}
