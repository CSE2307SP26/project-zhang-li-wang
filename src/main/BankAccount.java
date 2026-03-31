package main;

public class BankAccount {

    private double balance;
    private final double interestRate;
    private boolean closed;

    public BankAccount() {
        this(0.0);
    }

    public BankAccount(double interestRate) {
        this.balance = 0;
        this.interestRate = interestRate;
        this.closed = false;
    }

    public void deposit(double amount) {
        validateOpen();
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }

        this.balance += amount;
    }

    public void withdraw(double amount) {
        validateOpen();
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        this.balance -= amount;
    }

    public void close() {
        this.balance = 0;
        this.closed = true;
    }

    public double getBalance() {
        return this.balance;
    }

    public double getInterestRate() {
        return this.interestRate;
    }

    public boolean isClosed() {
        return this.closed;
    }

    private void validateOpen() {
        if (this.closed) {
            throw new IllegalStateException("Account is closed.");
        }
    }
}
