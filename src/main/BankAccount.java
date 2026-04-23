package main;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.YearMonth;

public class BankAccount {
    private static final double LOW_BALANCE_THRESHOLD = 100.0;
    private static final double LARGE_TRANSACTION_THRESHOLD = 1000.0;
    private static final double OVERDRAFT_WARNING_RATIO = 0.8;

    private static class RecurringBillPayment {
        private final String payee;
        private final double amount;
        private final int dayOfMonth;
        private YearMonth lastProcessedMonth;

        private RecurringBillPayment(String payee, double amount, int dayOfMonth) {
            this.payee = payee;
            this.amount = amount;
            this.dayOfMonth = dayOfMonth;
        }
    }

    private double balance;
    private double interestRate;
    private String pin;
   private boolean isFrozen = false;
    private boolean overdraftProtectionEnabled = false;
    private double overdraftLimit = 0.0;
    private double overdraftFee = 0.0;
    private final List<String> transactionHistory = new ArrayList<>();
    private final List<RecurringBillPayment> scheduledBillPayments = new ArrayList<>();
    private final List<String> alerts = new ArrayList<>();
    private int nextUnreadAlertIndex = 0;

    public BankAccount() {
        this(0.0);
    }

    public BankAccount(double interestRate) {
        this.balance = 0;
        setInterestRate(interestRate);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            transactionHistory.add("Deposit: $" + amount);
            createLargeTransactionAlertIfNeeded("deposit", amount);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        processDebitWithOverdraft("Withdrawal: $" + amount, "withdrawal", amount);
        createLargeTransactionAlertIfNeeded("withdrawal", amount);
        createLowBalanceAlertIfNeeded();
    }

    public double getBalance() {
        return this.balance;
    }

    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException();
        }
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void addInterest() {
        double interest = balance * interestRate;
        balance += interest;
    }
    
    public boolean setPin(String pin) {
        if (pin != null && pin.matches("\\d{4}")) {
            this.pin = pin;
            return true;
        }
        return false;
    }

    public String getPin() {
        return pin;
    }

    public boolean checkPin(String pin) {
        return this.pin != null && this.pin.equals(pin);
    }

    public boolean collectFee(double fee) {
        if (fee > 0 && fee <= balance) {
            balance -= fee;
            createLowBalanceAlertIfNeeded();
            return true;
        }
        return false;
    }  

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public String getSummary() {
        String pinStatus = (pin != null) ? "PIN set" : "No PIN set";
        return "Balance: $" + balance + ", Interest Rate: " + interestRate + ", " + pinStatus;
    }
    public void freezeAccount() {
        if (!isFrozen) {
            isFrozen = true;
            alerts.add("Account has been frozen. Contact the bank if this was unexpected.");
        }
    }

    public void unfreezeAccount() {
        isFrozen = false;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void scheduleRecurringBillPayment(String payee, double amount, int dayOfMonth) {
        if (payee == null || payee.trim().isEmpty() || amount <= 0 || dayOfMonth < 1 || dayOfMonth > 31) {
            throw new IllegalArgumentException();
        }

        scheduledBillPayments.add(new RecurringBillPayment(payee.trim(), amount, dayOfMonth));
        transactionHistory.add("Scheduled recurring bill payment: $" + amount + " to " + payee.trim() + " on day " + dayOfMonth);
    }

    public int processScheduledPayments(LocalDate processingDate) {
        if (processingDate == null) {
            throw new IllegalArgumentException();
        }

        int processedPayments = 0;
        YearMonth currentMonth = YearMonth.from(processingDate);

        for (RecurringBillPayment payment : scheduledBillPayments) {
            // If the app was not opened exactly on the due date, still process once for that month.
            if (processingDate.getDayOfMonth() >= payment.dayOfMonth && !currentMonth.equals(payment.lastProcessedMonth)) {
                try {
                    processDebitWithOverdraft(
                        "Bill payment: $" + payment.amount + " to " + payment.payee + " on " + processingDate,
                        "bill payment",
                        payment.amount
                    );
                    createLargeTransactionAlertIfNeeded("bill payment", payment.amount);
                    createLowBalanceAlertIfNeeded();
                    processedPayments++;
                } catch (IllegalArgumentException e) {
                    transactionHistory.add("Bill payment skipped (insufficient funds): $" + payment.amount + " to " + payment.payee + " on " + processingDate);
                }
                payment.lastProcessedMonth = currentMonth;
            }
        }

        return processedPayments;
    }

    public List<String> getScheduledBillPayments() {
        List<String> paymentDescriptions = new ArrayList<>();
        for (RecurringBillPayment payment : scheduledBillPayments) {
            paymentDescriptions.add(payment.payee + ": $" + payment.amount + " on day " + payment.dayOfMonth);
        }
        return paymentDescriptions;
    }

    public List<String> getUnreadAlerts() {
        List<String> unreadAlerts = new ArrayList<>();
        for (int i = nextUnreadAlertIndex; i < alerts.size(); i++) {
            unreadAlerts.add(alerts.get(i));
        }
        nextUnreadAlertIndex = alerts.size();
        return unreadAlerts;
    }

    public List<String> getAllAlerts() {
        return new ArrayList<>(alerts);
    }

    public void enableOverdraftProtection(double limit, double fee) {
        if (limit <= 0 || fee < 0) {
            throw new IllegalArgumentException();
        }
        overdraftProtectionEnabled = true;
        overdraftLimit = limit;
        overdraftFee = fee;
    }

    public boolean isOverdraftProtectionEnabled() {
        return overdraftProtectionEnabled;
    }

    private void createLowBalanceAlertIfNeeded() {
        // A low-balance alert is generated after money leaves the account.
        if (balance < LOW_BALANCE_THRESHOLD) {
            alerts.add("Low balance alert: Your balance is $" + balance + ".");
        }
    }

    private void createLargeTransactionAlertIfNeeded(String transactionType, double amount) {
        if (amount >= LARGE_TRANSACTION_THRESHOLD) {
            alerts.add("Large transaction alert: A " + transactionType + " of $" + amount + " was processed.");
        }
    }

    private void processDebitWithOverdraft(String transactionEntry, String transactionType, double amount) {
        double projectedBalance = balance - amount;
        boolean usesOverdraft = projectedBalance < 0;

        if (usesOverdraft && !overdraftProtectionEnabled) {
            throw new IllegalArgumentException();
        }

        double finalProjectedBalance = projectedBalance;
        if (usesOverdraft) {
            finalProjectedBalance -= overdraftFee;
        }

        if (finalProjectedBalance < -overdraftLimit) {
            throw new IllegalArgumentException();
        }

        balance = projectedBalance;
        transactionHistory.add(transactionEntry);

        if (usesOverdraft && overdraftFee > 0) {
            // Fee is applied immediately when overdraft is used.
            balance -= overdraftFee;
            transactionHistory.add("Overdraft fee: $" + overdraftFee);
            alerts.add("Overdraft fee charged: $" + overdraftFee + " after " + transactionType + ".");
        }

        createOverdraftWarningIfNeeded();
    }

    private void createOverdraftWarningIfNeeded() {
        if (!overdraftProtectionEnabled || balance >= 0) {
            return;
        }

        double usedAmount = Math.abs(balance);
        if (usedAmount >= overdraftLimit * OVERDRAFT_WARNING_RATIO) {
            alerts.add("Overdraft warning: You are close to your limit. Used $" + usedAmount + " of $" + overdraftLimit + ".");
        }
    }
}
