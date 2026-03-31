package test;

import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

    @Test
    public void testDeposit() {
        BankAccount testAccount = new BankAccount();
        testAccount.deposit(50);
        assertEquals(50, testAccount.getBalance(), 0.01);
    }

    @Test
    public void testInvalidDeposit() {
        BankAccount testAccount = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> testAccount.deposit(-50));
    }

    @Test
    public void testCloseAccount() {
        BankAccount testAccount = new BankAccount(0.02);
        testAccount.deposit(50);
        testAccount.close();

        assertTrue(testAccount.isClosed());
        assertEquals(0, testAccount.getBalance(), 0.01);
        assertEquals(0.02, testAccount.getInterestRate(), 0.001);
        assertThrows(IllegalStateException.class, () -> testAccount.deposit(10));
    }
}
