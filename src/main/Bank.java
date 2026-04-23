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

    public boolean verifyAccountPin(int accountIndex, String pin) {
        return userAccounts.get(accountIndex).checkPin(pin);
    }


    public boolean collectFeeFromAccount(int accountIndex, double fee) {
        return userAccounts.get(accountIndex).collectFee(fee);
    }
    

    public void freezeAccount(int accountIndex) {
       userAccounts.get(accountIndex).freezeAccount();
  }

    public void unfreezeAccount(int accountIndex) {
        userAccounts.get(accountIndex).unfreezeAccount();
    }

    public boolean isAccountFrozen(int accountIndex) {
        return userAccounts.get(accountIndex).isFrozen();
    }

    public double calculateLoanMonthlyPayment(double principal, double apr, int termMonths) {
        if (principal <= 0 || apr < 0 || termMonths <= 0) {
            throw new IllegalArgumentException();
        }

        double monthlyRate = apr / 100.0 / 12.0;
        if (monthlyRate == 0) {
            return principal / termMonths;
        }

        double growthFactor = Math.pow(1 + monthlyRate, termMonths);
        return principal * monthlyRate * growthFactor / (growthFactor - 1);
    }

    public int estimateMonthsToReachSavingsGoal(double currentBalance, double monthlyDeposit, double apr, double goalAmount) {
        if (currentBalance < 0 || monthlyDeposit < 0 || apr < 0 || goalAmount <= 0) {
            throw new IllegalArgumentException();
        }

        if (currentBalance >= goalAmount) {
            return 0;
        }

        double monthlyRate = apr / 100.0 / 12.0;
        if (monthlyDeposit == 0 && (monthlyRate == 0 || currentBalance == 0)) {
            throw new IllegalArgumentException();
        }

        int months = 0;
        double balance = currentBalance;
        while (balance < goalAmount) {
            balance = balance * (1 + monthlyRate) + monthlyDeposit;
            months++;
        }

        return months;
    }
}
