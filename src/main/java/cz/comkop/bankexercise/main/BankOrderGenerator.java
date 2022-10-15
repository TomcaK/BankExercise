package cz.comkop.bankexercise.main;


import cz.comkop.bankexercise.bank.Account;
import cz.comkop.bankexercise.bank.Bank;
import cz.comkop.bankexercise.bank.BankOrder;
import cz.comkop.bankexercise.bank.BankServer;

import java.util.Random;


public class BankOrderGenerator extends Thread {
    private Bank bank;
    private Time time;
    private BankServer server = BankServer.getInstance();
    private int randomTypes;
    private int id = 1;

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            try {
                sleep(random.nextInt(500, 6000));
            } catch (InterruptedException e) {
            }
            randomTypes = random.nextInt(1, 4);
            Account from;
            Account to;
            do {
                from = bank.getAccounts().get(random.nextInt(bank.getAccounts().size()));
                to = bank.getAccounts().get(random.nextInt(bank.getAccounts().size()));
            }
            while (from.equals(to));

            switch (randomTypes) {
                case 1 -> from = null;
                case 2 -> to = null;
            }
            server.receive(BankOrder.builder()
                    .setId(id++)
                    .setAmount(random.nextInt(1, 20000))
                    .setFrom(from)
                    .setTo(to)
                    .setTime(time.getTime())
                    .build());
        }
    }


    public void setInstances(Bank bank, Time time) {
        this.bank = bank;
        this.time = time;
    }
}
