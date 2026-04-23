package test;

import main.BankAccount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

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

    @Test
    public void testSetInvalidPinWithWrongLength() {
        BankAccount account = new BankAccount();
        boolean result = account.setPin("12345");
        assertFalse(result);
        assertNull(account.getPin());
    }

    @Test
    public void testTransactionHistoryRecordsDepositAndWithdrawal() {
        BankAccount account = new BankAccount();
        account.deposit(100.0);
        account.withdraw(25.0);

        assertEquals(2, account.getTransactionHistory().size());
        assertEquals("Deposit: $100.0", account.getTransactionHistory().get(0));
        assertEquals("Withdrawal: $25.0", account.getTransactionHistory().get(1));
    }

    @Test
    public void testTransactionHistoryReturnsCopy() {
        BankAccount account = new BankAccount();
        account.deposit(20.0);
        java.util.List<String> history = account.getTransactionHistory();
        history.add("Tamper");

        assertEquals(1, account.getTransactionHistory().size());
        assertEquals("Deposit: $20.0", account.getTransactionHistory().get(0));
    }
    public void testCheckCorrectPin() {
        BankAccount account = new BankAccount();
        account.setPin("1234");
        assertTrue(account.checkPin("1234"));
    }

    @Test
    public void testCheckWrongPin() {
        BankAccount account = new BankAccount();
        account.setPin("1234");
        assertFalse(account.checkPin("0000"));
    }

    @Test
    public void testFreezeAccount() {
        BankAccount account = new BankAccount();
        account.freezeAccount();
        assertTrue(account.isFrozen());
    }

    @Test
    public void testUnfreezeAccount() {
        BankAccount account = new BankAccount();
        account.freezeAccount();
        account.unfreezeAccount();
        assertFalse(account.isFrozen());
    }

    @Test
    public void testScheduleRecurringBillPaymentAndProcessOnDueDate() {
        BankAccount account = new BankAccount();
        account.deposit(1000.0);
        account.scheduleRecurringBillPayment("Utilities", 150.0, 10);

        int processed = account.processScheduledPayments(LocalDate.of(2026, 4, 10));

        assertEquals(1, processed);
        assertEquals(850.0, account.getBalance(), 0.001);
        assertEquals(3, account.getTransactionHistory().size());
    }

    @Test
    public void testRecurringPaymentProcessesOnlyOncePerMonth() {
        BankAccount account = new BankAccount();
        account.deposit(500.0);
        account.scheduleRecurringBillPayment("Rent", 200.0, 5);

        account.processScheduledPayments(LocalDate.of(2026, 4, 5));
        int secondAttempt = account.processScheduledPayments(LocalDate.of(2026, 4, 20));

        assertEquals(0, secondAttempt);
        assertEquals(300.0, account.getBalance(), 0.001);
    }

    @Test
    public void testRecurringPaymentSkippedWhenInsufficientFunds() {
        BankAccount account = new BankAccount();
        account.deposit(50.0);
        account.scheduleRecurringBillPayment("Internet", 80.0, 15);

        int processed = account.processScheduledPayments(LocalDate.of(2026, 4, 15));

        assertEquals(0, processed);
        assertEquals(50.0, account.getBalance(), 0.001);
        assertTrue(account.getTransactionHistory().get(2).contains("insufficient funds"));
    }

    @Test
    public void testLowBalanceAlertAfterWithdrawal() {
        BankAccount account = new BankAccount();
        account.deposit(150.0);
        account.withdraw(75.0);

        List<String> alerts = account.getUnreadAlerts();
        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).contains("Low balance alert"));
    }

    @Test
    public void testLargeTransactionAlertForDeposit() {
        BankAccount account = new BankAccount();
        account.deposit(1200.0);

        List<String> alerts = account.getUnreadAlerts();
        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).contains("Large transaction alert"));
        assertTrue(alerts.get(0).contains("deposit"));
    }

    @Test
    public void testAccountFreezeAlert() {
        BankAccount account = new BankAccount();
        account.freezeAccount();

        List<String> alerts = account.getUnreadAlerts();
        assertEquals(1, alerts.size());
        assertTrue(alerts.get(0).contains("frozen"));
    }

    @Test
    public void testUnreadAlertsReturnedOnlyOnce() {
        BankAccount account = new BankAccount();
        account.deposit(1200.0);

        assertEquals(1, account.getUnreadAlerts().size());
        assertTrue(account.getUnreadAlerts().isEmpty());
    }
}
