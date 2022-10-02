package cz.comkop.bankexercise.bank;


import java.util.ArrayList;
import java.util.List;

public class Account {
    private int number;
    private long balance;

    public enum AccountType {
        SAVING, PERSONAL, COMPANY
    }

    private AccountType type;
    private double interestRate;
    private Owner owner;
    private List<BankOrder> historyOfBankOrders;


    public Account(int number, long balance, AccountType type, Owner owner) {
        this.number = number;
        this.balance = balance;
        this.type = type;
        this.owner = owner;
        interestRate = setInterestRate(type);
        historyOfBankOrders = new ArrayList<>();
    }

    private double setInterestRate(AccountType type) {
        return switch (type) {
            case SAVING -> 6.2d;
            case COMPANY -> 2.6d;
            case PERSONAL -> 0.5d;
        };
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public double getYearInterest() {
        return ((double) balance / 100.00) * interestRate;
    }

    public int getNumber() {
        return number;
    }


    @Override
    public String toString() {
        return String.format("Account number: %s, balance: %d Kč, type of account: %s, interest rate percentage of account: %s ", number, balance, type, interestRate);
    }

    public Owner getOwner() {
        return owner;
    }

    public List<BankOrder> getHistoryOfPayments() {
        return historyOfBankOrders;
    }
}
