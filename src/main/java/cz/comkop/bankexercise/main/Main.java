package cz.comkop.bankexercise.main;


import cz.comkop.bankexercise.bank.Bank;
import cz.comkop.bankexercise.main.ui.UI;

public class Main {
    public static boolean debug = false;
    private static Bank bank = new Bank();
    private static Time time = new Time();
    private static BankOrderGenerator bankOrderGenerator = new BankOrderGenerator();
    private static UI ui = new UI();

    public static void main(String[] args) {
        time.setAll(bank,ui);
        bank.setAll(time,ui);
        bankOrderGenerator.setGenerator(bank,time);
        bankOrderGenerator.start();
        bank.start();
        Thread t = new Thread(time);
        t.start();
        ui.start();
    }
}
