package main;

public class Bank {

    public BankAccount openAccount() {
        return new BankAccount();
    }

    public void deposit(BankAccount account, double amount) {
        requireAccount(account);
        account.deposit(amount);
    }

    // Keep money movement rules in the bank layer instead of the menu UI.
    public void transfer(BankAccount sourceAccount, BankAccount destinationAccount, double amount) {
        requireAccount(sourceAccount);
        requireAccount(destinationAccount);
        if (sourceAccount == destinationAccount) {
            throw new IllegalArgumentException("Source and destination accounts must be different.");
        }

        sourceAccount.withdraw(amount);
        destinationAccount.deposit(amount);
    }

    public void closeAccount(BankAccount account) {
        requireAccount(account);
        account.close();
    }

    private void requireAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
    }
}
