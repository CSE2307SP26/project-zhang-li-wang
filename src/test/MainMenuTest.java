package test;

import main.Bank;
import main.BankAccount;
import main.MainMenu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class MainMenuTest {

    @Test
    public void testGetUserSelectionSkipsInvalidInput() {
        MainMenu mainMenu = new MainMenu(
            new Bank(),
            new BankAccount(),
            new Scanner(new ByteArrayInputStream("0\n2\n".getBytes()))
        );

        assertEquals(2, mainMenu.getUserSelection(2));
    }

    @Test
    public void testProcessInputHandlesDeposit() {
        BankAccount userAccount = new BankAccount();
        MainMenu mainMenu = new MainMenu(
            new Bank(),
            userAccount,
            new Scanner(new ByteArrayInputStream("40\n".getBytes()))
        );

        mainMenu.processInput(1);

        assertEquals(40, userAccount.getBalance(), 0.01);
    }
}
