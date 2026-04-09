package main;

import java.util.LinkedList;

public class Bank {

    private final LinkedList<BankAccount> userAccounts;

    public Bank() {
        this.userAccounts = new LinkedList<>();
        this.userAccounts.add(new BankAccount());
    }

    public void createAccount() {
        userAccounts.add(new BankAccount());
    }

    public void closeAccount(int accountIndex) {
        if (userAccounts.size() == 1) {
            throw new IllegalStateException();
        }
        userAccounts.remove(accountIndex);
    }

    public void depositToAccount(int accountIndex, double amount) {
        userAccounts.get(accountIndex).deposit(amount);
    }

    public void withdrawFromAccount(int accountIndex, double amount) {
        userAccounts.get(accountIndex).withdraw(amount);
    }

    public double getBalance(int accountIndex) {
        return userAccounts.get(accountIndex).getBalance();
    }

    public void transfer(int fromIndex, int toIndex, double amount) {
        userAccounts.get(fromIndex).withdraw(amount);
        userAccounts.get(toIndex).deposit(amount);
    }

    public void addInterest(int accountIndex, double rate) {
        BankAccount account = userAccounts.get(accountIndex);
        account.setInterestRate(rate);
        account.addInterest();
    }

    public int getNumberOfAccounts() {
        return userAccounts.size();
    }

    public BankAccount getAccount(int index) {
        return userAccounts.get(index);
    }

    public boolean setAccountPin(int accountIndex, String pin) {
        return userAccounts.get(accountIndex).setPin(pin);
    }

    public boolean collectFeeFromAccount(int accountIndex, double fee) {
        return userAccounts.get(accountIndex).collectFee(fee);
    }
    
}
