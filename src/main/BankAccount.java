package main;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private double balance;
    private double interestRate;
    private String pin;
   private boolean isFrozen = false;
    private final List<String> transactionHistory = new ArrayList<>();

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
            transactionHistory.add("Deposit: $" + amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        if (amount > balance) {
            throw new IllegalArgumentException();
        }
        balance -= amount;
        transactionHistory.add("Withdrawal: $" + amount);
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
    
    public boolean setPin(String pin) {
        if (pin != null && pin.matches("\\d{4}")) {
            this.pin = pin;
            return true;
        }
        return false;
    }

    public String getPin() {
        return pin;
    }

    public boolean checkPin(String pin) {
        return this.pin != null && this.pin.equals(pin);
    }

    public boolean collectFee(double fee) {
        if (fee > 0 && fee <= balance) {
            balance -= fee;
            return true;
        }
        return false;
    }  

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public String getSummary() {
        String pinStatus = (pin != null) ? "PIN set" : "No PIN set";
        return "Balance: $" + balance + ", Interest Rate: " + interestRate + ", " + pinStatus;
    }
    public void freezeAccount() {
        isFrozen = true;
    }

    public void unfreezeAccount() {
        isFrozen = false;
    }

    public boolean isFrozen() {
        return isFrozen;
    }
}
