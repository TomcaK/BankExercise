package cz.comkop.bankexercise.main;


import cz.comkop.bankexercise.bank.Bank;

public class Main {
    public static boolean debug = false;

    public static void main(String[] args) {
        Bank bank = new Bank();
        Time time = new Time();
        time.setBank(bank);
        bank.setTime(time);
        BankOrderGenerator bankOrderGenerator = new BankOrderGenerator();
        bankOrderGenerator.setGenerator(bank,time);
        bankOrderGenerator.start();
        bank.start();
        Thread t = new Thread(time);
        t.start();
    }
}
