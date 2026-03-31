package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

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
        try {
            testAccount.deposit(-50);
            fail();
        } catch (IllegalArgumentException e) {
            //do nothing, test passes
        }
    }

    @Test
    public void testAddInterest() {
        BankAccount account = new BankAccount(0.05);
        account.deposit(100);
        account.addInterest();
        assertEquals(105, account.getBalance(), 0.01);
    }

    @Test
    public void testCheckBalance() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        double balance = account.getBalance();
        assertEquals(100, balance, 0.01);
    }

    @Test
    public void testInvalidWithdraw() {
        BankAccount account = new BankAccount();
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(10));
    }
}
