package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.Bank;
import main.BankAccount;

public class BankTest {

    @Test
    public void testCreateAdditionalAccount() {
        Bank bank = new Bank();
        int before = bank.getNumberOfAccounts();

        bank.createAccount();

        assertEquals(before + 1, bank.getNumberOfAccounts());
    }

    @Test
    public void testCloseAccount() {
        Bank bank = new Bank();
        bank.createAccount();

        bank.closeAccount(1);

        assertEquals(1, bank.getNumberOfAccounts());
    }

    @Test
    public void testCannotCloseLastAccount() {
        Bank bank = new Bank();

        assertThrows(IllegalStateException.class, () -> bank.closeAccount(0));
    }

    @Test
    public void testTransfer() {
        Bank bank = new Bank();
        bank.createAccount();
        bank.depositToAccount(0, 100);

        bank.transfer(0, 1, 50);

        assertEquals(50, bank.getBalance(0), 0.01);
        assertEquals(50, bank.getBalance(1), 0.01);
    }

    @Test
    public void testAccountSummaryShowsBalanceInterestAndPinStatus() {
        BankAccount account = new BankAccount(0.05);
        account.deposit(100.0);

        String summary = account.getSummary();

        assertTrue(summary.contains("100.0"));
        assertTrue(summary.contains("0.05"));
        assertTrue(summary.contains("No PIN set"));
    }


    @Test
    public void testVerifyAccountPin() {
        Bank bank = new Bank();
        bank.setAccountPin(0, "1234");
        assertTrue(bank.verifyAccountPin(0, "1234"));
    }

    @Test
    public void testVerifyAccountPinWrong() {
        Bank bank = new Bank();
        bank.setAccountPin(0, "1234");
        assertFalse(bank.verifyAccountPin(0, "9999"));
    }
}
