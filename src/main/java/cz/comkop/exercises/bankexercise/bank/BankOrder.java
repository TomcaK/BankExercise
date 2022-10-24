package cz.comkop.exercises.bankexercise.bank;

import java.time.LocalDateTime;

import static cz.comkop.exercises.bankexercise.main.Time.DATE_TIME_FORMATTER;

public class BankOrder {
    public enum OrderType {
        WITHDRAW, DEPOSIT, PAYMENT
    }

    private final OrderType type;
    private final int id;
    private final int amount;
    private final Account from;
    private final Account to;
    private LocalDateTime time;

    private boolean processed;

    public BankOrder(int id, int amount, Account from, Account to, LocalDateTime time) {
        this.id = id;
        this.amount = amount;
        this.from = from;
        this.to = to;
        if (from == null) {
            type = OrderType.DEPOSIT;
        } else if (to == null) {
            type = OrderType.WITHDRAW;
        } else {
            type = OrderType.PAYMENT;
        }
        this.time = time;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getAmount() {
        return amount;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public OrderType getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }


    @Override
    public String toString() {
        String from = "";
        String to = "";
        if (this.from != null) {
            from = "from " + this.from.getOwner().getName();
        }
        if (this.to != null) {
            to = "to " + this.to.getOwner().getName();
        }
        return String.format("%s, id: %s,type: %s, amount: %d Kč %s %s", time.format(DATE_TIME_FORMATTER), id, type, amount, from, to);
    }
    public static BankOrderBuilder builder(){
        return new BankOrderBuilder();
    }

    public static class BankOrderBuilder {

        private int id;
        private int amount;
        private Account from;
        private Account to;
        private LocalDateTime time;



        public BankOrderBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public BankOrderBuilder setAmount(int amount) {
            this.amount = amount;
            return this;
        }

        public BankOrderBuilder setFrom(Account from) {
            this.from = from;
            return this;
        }

        public BankOrderBuilder setTo(Account to) {
            this.to = to;
            return this;
        }

        public BankOrderBuilder setTime(LocalDateTime time) {
            this.time = time;
            return this;
        }

        public BankOrder build() {
            return new BankOrder(id, amount, from, to,time);
        }
    }


}
