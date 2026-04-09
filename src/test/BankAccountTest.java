package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    public void testSetValidPin() {
        BankAccount account = new BankAccount();
        boolean result = account.setPin("1234");
        assertTrue(result);
        assertEquals("1234", account.getPin());
    }

    @Test
    public void testSetInvalidPinWithLetters() {
        BankAccount account = new BankAccount();
        boolean result = account.setPin("12a4");
        assertFalse(result);
        assertNull(account.getPin());
    }
    
    @Test
    public void testCollectValidFee() {
        BankAccount account = new BankAccount();
        account.deposit(100.0);
        boolean result = account.collectFee(20.0);
        assertTrue(result);
        assertEquals(80.0, account.getBalance(), 0.001);
    }

    @Test
    public void testCollectFeeTooLarge() {
        BankAccount account = new BankAccount();
        account.deposit(50.0);
        boolean result = account.collectFee(100.0);
        assertFalse(result);
        assertEquals(50.0, account.getBalance(), 0.001);
    }
}
    @Test
    public void testSetInvalidPinWithWrongLength() {
        BankAccount account = new BankAccount();
        boolean result = account.setPin("12345");
        assertFalse(result);
        assertNull(account.getPin());
    }

    
