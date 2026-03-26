package test;

import main.BankAccount;
import main.MainMenu;

import static org.junit.Assert.assertEquals;
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
    public void testCreateAdditionalAccount() {
    MainMenu menu = new MainMenu();
    int before = menu.getNumberOfAccounts();
    menu.createAccount();
    assertEquals(before + 1, menu.getNumberOfAccounts());

    @Test
    public void testAddInterest() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        account.addInterest(0.05); 
        assertEquals(105, account.getBalance(), 0.01);
    }
} 
    public void testCheckBalance() {
        BankAccount account = new BankAccount();
        account.deposit(100);
        double balance = account.getBalance();
        assertEquals(100, balance, 0.01);
    }

    @Test
    public void testTransfer() {
    MainMenu menu = new MainMenu();
    menu.createAccount();

    menu.getAccount(0).deposit(100);

    double beforeFirst = menu.getAccount(0).getBalance();
    double beforeSecond = menu.getAccount(1).getBalance();

    menu.transfer(0, 1, 50);

    assertEquals(beforeFirst - 50, menu.getAccount(0).getBalance(), 0.01);
    assertEquals(beforeSecond + 50, menu.getAccount(1).getBalance(), 0.01);
}
}
