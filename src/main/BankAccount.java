package main;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.YearMonth;

public class BankAccount {

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
    private final List<String> transactionHistory = new ArrayList<>();
    private final List<RecurringBillPayment> scheduledBillPayments = new ArrayList<>();

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
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException();
        }
        if (amount > balance) {
            throw new IllegalArgumentException();
        }
        balance -= amount;
        transactionHistory.add("Withdrawal: $" + amount);
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
        isFrozen = true;
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
                if (balance >= payment.amount) {
                    balance -= payment.amount;
                    transactionHistory.add("Bill payment: $" + payment.amount + " to " + payment.payee + " on " + processingDate);
                    processedPayments++;
                } else {
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
}
