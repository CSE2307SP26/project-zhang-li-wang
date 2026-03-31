package test;

import main.Bank;
import main.BankAccount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BankTest {

    @Test
    public void testTransferMovesMoneyBetweenAccounts() {
        Bank bank = new Bank();
        BankAccount sourceAccount = new BankAccount();
        BankAccount destinationAccount = new BankAccount();

        sourceAccount.deposit(80);
        bank.transfer(sourceAccount, destinationAccount, 30);

        assertEquals(50, sourceAccount.getBalance(), 0.01);
        assertEquals(30, destinationAccount.getBalance(), 0.01);
    }

    @Test
    public void testCloseAccountPreventsFutureTransactions() {
        Bank bank = new Bank();
        BankAccount account = bank.openAccount();

        account.deposit(25);
        bank.closeAccount(account);

        assertTrue(account.isClosed());
        assertEquals(0, account.getBalance(), 0.01);
        assertThrows(IllegalStateException.class, () -> bank.deposit(account, 10));
    }
}
