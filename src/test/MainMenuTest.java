package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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

    @Test
    public void testProcessInputCalculatesLoanPayment() {
        MainMenu menu = new MainMenu(new Bank(), new Scanner("10000 6 36"));
        assertDoesNotThrow(() -> menu.processInput(13));
    }

    @Test
    public void testProcessInputEstimatesSavingsGoalTimeline() {
        MainMenu menu = new MainMenu(new Bank(), new Scanner("1000 200 6 5000"));
        assertDoesNotThrow(() -> menu.processInput(14));
    }

    @Test
    public void testProcessInputSchedulesRecurringBillPayment() {
        Bank bank = new Bank();
        bank.setAccountPin(0, "1234");
        MainMenu menu = new MainMenu(bank, new Scanner("1 1234\nUtilities\n75 20"));

        assertDoesNotThrow(() -> menu.processInput(15));
        assertEquals(1, bank.getAccount(0).getScheduledBillPayments().size());
    }
}
