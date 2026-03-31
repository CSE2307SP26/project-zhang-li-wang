package main;

import java.util.Scanner;

public class MainMenu {

    private static final int EXIT_SELECTION = 2;
    private static final int MAX_SELECTION = 2;

    private final Bank bank;
    private final BankAccount userAccount;
    private final Scanner keyboardInput;

    public MainMenu() {
        this(new Bank(), new BankAccount(), new Scanner(System.in));
    }

    public MainMenu(Bank bank, BankAccount userAccount, Scanner keyboardInput) {
        this.bank = bank;
        this.userAccount = userAccount;
        this.keyboardInput = keyboardInput;
    }

    public void displayOptions() {
        System.out.println("Welcome to the 237 Bank App!");

        System.out.println("1. Make a deposit");
        System.out.println("2. Exit the app");
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
        }
    }

    public void performDeposit() {
        double depositAmount = -1;
        while (depositAmount < 0) {
            System.out.print("How much would you like to deposit: ");
            depositAmount = keyboardInput.nextDouble();
        }
        bank.deposit(userAccount, depositAmount);
    }

    public void run() {
        int selection = -1;
        while (selection != EXIT_SELECTION) {
            displayOptions();
            selection = getUserSelection(MAX_SELECTION);
            processInput(selection);
        }
    }

    public static void main(String[] args) {
        MainMenu bankApp = new MainMenu();
        bankApp.run();
    }

}
