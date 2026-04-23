package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 13;
    private static final int MAX_SELECTION = 13;

    private final Bank bank;
    private Scanner keyboardInput;

    public MainMenu() {
        this(new Bank(), new Scanner(System.in));
    }

    public MainMenu(Bank bank, Scanner keyboardInput) {
        this.bank = bank;
        this.keyboardInput = keyboardInput;
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("1. Deposit into an existing account");
        System.out.println("2. Withdraw from an account");
        System.out.println("3. Check account balance");
        System.out.println("4. Create an additional account");
        System.out.println("5. Close an existing account");
        System.out.println("6. Transfer money between accounts");
        System.out.println("7. Add interest payment (Admin)");
        System.out.println("8. Set account PIN");
        System.out.println("9. Collect fee from an existing account (Admin)");
        System.out.println("10. View transaction history");
        System.out.println("11. Freeze an account (Admin)");
        System.out.println("12. Unfreeze an account (Admin)");
        System.out.println("13. Exit the app");
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while (selection < 1 || selection > max) {
            System.out.print("Please make a selection: ");
            selection = keyboardInput.nextInt();
        }
        return selection;
    }

    public void processInput(int selection) {
        switch (selection) {
            case 1:
                performDeposit();
                break;
            case 2:
                performWithdraw();
                break;
            case 3:
                checkBalance();
                break;
            case 4:
                createAccount();
                break;
            case 5:
                closeAccount();
                break;
            case 6:
                performTransfer();
                break;
            case 7:
                addInterest();
                break;
            case 8:
                setAccountPin();
                break;
            case 9:
                collectFee();
                break;
            case 10:
                viewTransactionHistory();
                break;
            case 11:
                freezeAccount();
                break;
            case 12:
                unfreezeAccount();
                break;
            default:
                break;
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        int accountIndex = 0;
        while (depositAmount <= 0) {
            System.out.print("Which account would you like to deposit to: ");
            accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
        }
        
        try {
            bank.depositToAccount(accountIndex, depositAmount);
            System.out.println("Deposit successful.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid operation. Please try again.");
        }
    }

    public void performWithdraw() {
        System.out.print("Which account would you like to withdraw from: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;

        System.out.print("Enter PIN for this account: ");
        String pin = keyboardInput.next();

        if (!bank.verifyAccountPin(accountIndex, pin)) {
            System.out.println("Incorrect PIN.");
            return;
        }

        if (bank.isAccountFrozen(accountIndex)) {
            System.out.println("This account is frozen.");
            return;
        }

        System.out.print("How much would you like to withdraw: ");
        double withdrawAmount = keyboardInput.nextDouble();

        try {
            bank.withdrawFromAccount(accountIndex, withdrawAmount);
            System.out.println("Withdrawal successful.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid operation. Please try again.");
        }
    }

    public void createAccount() {
        bank.createAccount();
        System.out.println("New account " + bank.getNumberOfAccounts() + " created!");
    }

    public void closeAccount() {
        System.out.print("Which account would you like to close: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;

        if (!verifyPinForAccount(accountIndex)) {
            System.out.println("Incorrect PIN.");
            return;
        }

        try {
            bank.closeAccount(accountIndex);
            System.out.println("Account closed.");
        } catch (IllegalStateException e) {
            System.out.println("You cannot close the only remaining account.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid operation. Please try again.");
        }
    }

    public void performTransfer() {
        double transferAmount = 0.0;

        System.out.print("Which account would you like to transfer from: ");
        int fromIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;

        if (!verifyPinForAccount(fromIndex)) {
            System.out.println("Incorrect PIN.");
            return;
        }

        if (bank.isAccountFrozen(fromIndex)) {
            System.out.println("This account is frozen.");
            return;
        }

        System.out.print("Which account would you like to transfer to: ");
        int toIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;

        System.out.print("How much would you like to transfer: ");
        transferAmount = keyboardInput.nextDouble();

        try {
            bank.transfer(fromIndex, toIndex, transferAmount);
            System.out.println("Transfer successful.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid operation. Please try again.");
        }
    }  

    public void run() {
        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            if (selection != EXIT_SELECTION) {
                processInput(selection);
            }
        }
    }

    public void addInterest() {
        System.out.print("Which account would you like to add interest to: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;
        System.out.print("Enter interest rate: ");
        double rate = keyboardInput.nextDouble();
        try {
            bank.addInterest(accountIndex, rate);
            System.out.println("Interest added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid operation. Please try again.");
        }
    }

    public void checkBalance() {
        System.out.print("Which account would you like to check: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;

        if (!verifyPinForAccount(accountIndex)) {
            System.out.println("Incorrect PIN.");
            return;
        }

        double balance = bank.getBalance(accountIndex);
        System.out.println("Current balance: $" + balance);
    }

    public void setAccountPin() {
        System.out.print("Which account would you like to set the PIN for: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;
        System.out.print("Enter a 4-digit PIN: ");
        String pin = keyboardInput.next();
        boolean success = bank.setAccountPin(accountIndex, pin);
        if (success) {
            System.out.println("PIN set successfully.");
        } else {
            System.out.println("Invalid PIN. Please enter a 4-digit number.");
        }
    }

    public void collectFee() {
        System.out.print(" Which account would you like to collect a fee from: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;
        System.out.print("Enter fee amount: ");
        double fee = keyboardInput.nextDouble();
        boolean success = bank.collectFeeFromAccount(accountIndex, fee);
        if (success) {
            System.out.println(" Fee collected successfully. ");
        } else {
            System.out.println(" Failed to collect fee. ");
        }
    }

    public void viewAccountSummary() {
        System.out.print("Which account would you like to view: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;
        BankAccount account = bank.getAccount(accountIndex);
        System.out.println(account.getSummary());
    }

    public void viewTransactionHistory() {
        System.out.print("Which account would you like to view: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;
        BankAccount account = bank.getAccount(accountIndex);
        for (String entry : account.getTransactionHistory()) {
            System.out.println(entry);
        }
        if (account.getTransactionHistory().isEmpty()) {
            System.out.println("No transaction history available.");
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

    public int getNumberOfAccounts() {
        return bank.getNumberOfAccounts();
    }

    public BankAccount getAccount(int index) {
        return bank.getAccount(index);
    }

    
    public boolean verifyPinForAccount(int accountIndex) {
        System.out.print("Enter PIN for this account: ");
        String pin = keyboardInput.next();
        return bank.verifyAccountPin(accountIndex, pin);
    }

    public void freezeAccount() {
        System.out.print("Which account would you like to freeze: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;
        bank.freezeAccount(accountIndex);
        System.out.println("Account frozen.");
    }

    public void unfreezeAccount() {
        System.out.print("Which account would you like to unfreeze: ");
        int accountIndex = getUserSelection(bank.getNumberOfAccounts()) - 1;
        bank.unfreezeAccount(accountIndex);
        System.out.println("Account unfrozen.");
    }
}