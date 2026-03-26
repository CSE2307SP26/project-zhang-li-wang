package main;

import java.util.LinkedList;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 8;
	private static final int MAX_SELECTION = 8;

	private LinkedList<BankAccount> userAccounts;
    private Scanner keyboardInput;

    public MainMenu() {
        //use linked list to store accounts
        this.userAccounts = new LinkedList<>();
        this.userAccounts.add(new BankAccount());
        this.keyboardInput = new Scanner(System.in);
    }

    // display options to the user
    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        System.out.println("1. Deposit into an existing account");
        System.out.println("2. Withdraw from an account");
        System.out.println("3. Check account balance");
        System.out.println("4. Create an additional account");
        System.out.println("5. Close an existing account");
        System.out.println("6. Transfer money between accounts");
        System.out.println("7. Add interest payment (Admin)");
        System.out.println("8. Exit the app");
}
    }

    public int getUserSelection(int max) {
        int selection = -1;
        while(selection < 1 || selection > max) {
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

        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        int accountIndex = 0;
        while(depositAmount < 0) {
            System.out.print("Which account would you like to deposit to: ");
            accountIndex = getUserSelection(userAccounts.size()) - 1;
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextInt();
        }
        this.userAccounts.get(accountIndex).deposit(depositAmount);
    }

    public void createAccount() {
    userAccounts.add(new BankAccount());
    System.out.println("New account " + userAccounts.size() + " created!");
    }   

    public void performTransfer() {
        double transferAmount = 0.0;
        System.out.print("Which account would you like to transfer from: ");
        int fromIndex = getUserSelection(userAccounts.size()) - 1;
        System.out.print("Which account would you like to transfer to: ");
        int toIndex = getUserSelection(userAccounts.size()) - 1;
        System.out.print("How much would you like to transfer: ");
        transferAmount = keyboardInput.nextInt();
        transfer(fromIndex, toIndex, transferAmount);
    }

    public void transfer(int fromIndex, int toIndex, double amount) {
    userAccounts.get(fromIndex).withdraw(amount);
    userAccounts.get(toIndex).deposit(amount);
    }

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    private void addInterest() {
        System.out.print("Enter interest rate: ");
        double rate = keyboardInput.nextDouble();
        userAccount.addInterest(rate);
    }
    
    
    private void checkBalance() {
        double balance = userAccount.getBalance();
        System.out.println("Current balance: $" + balance);
    }
    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

    // helper functions for testing
    public int getNumberOfAccounts() {
    return userAccounts.size();
    }

    public BankAccount getAccount(int index) {
    return userAccounts.get(index);
    }   
}
