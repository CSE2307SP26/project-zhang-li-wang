package main;

import java.util.LinkedList;
import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 3;
	private static final int MAX_SELECTION = 3;

	private LinkedList<BankAccount> userAccounts;
    private Scanner keyboardInput;

    public MainMenu() {
        this.userAccounts = new LinkedList<>();
        this.userAccounts.add(new BankAccount());
        this.keyboardInput = new Scanner(System.in);
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");
        
        System.out.println("1. Make a deposit");
        System.out.println("2. Create a new account");
        System.out.println("3. Exit the app");
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

    public void run() {
        int selection = -1;
        while(selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

    public int getNumberOfAccounts() {
    return userAccounts.size();
}

}
