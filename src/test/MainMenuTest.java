package test;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import main.Bank;
import main.MainMenu;

public class MainMenuTest {

    @Test
    public void testProcessInputCreatesAccount() {
        MainMenu menu = new MainMenu(new Bank(), new Scanner("8"));
        int before = menu.getNumberOfAccounts();

        menu.processInput(4);

        assertEquals(before + 1, menu.getNumberOfAccounts());
    }
}
