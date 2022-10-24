package cz.comkop.exercises.bankexercise.main;


import cz.comkop.exercises.bankexercise.bank.Bank;

public class Main {
    private static final Bank bank = new Bank();
    private static final Time time = new Time();
    private static final BankOrderGenerator bankOrderGenerator = new BankOrderGenerator();
    private static UI ui = new UI();

    public static void main(String[] args) {
        time.setInstances(bank,ui);
        bank.setInstances(time,ui);
        ui.setInstances(bank);
        bankOrderGenerator.setInstances(bank,time);
        bankOrderGenerator.start();
        bank.start();
        Thread t = new Thread(time);
        t.start();
        ui.start();
    }
}
