package cz.comkop.bankexercise.main;




import cz.comkop.bankexercise.bank.Account;
import cz.comkop.bankexercise.bank.Bank;
import cz.comkop.bankexercise.bank.BankOrder;
import cz.comkop.bankexercise.bank.BankServer;

import java.util.Random;


public class BankOrderGenerator extends Thread {
    Bank bank;
    Time time;
    BankServer server;
    int randomTypes;
    int id = 1;

    @Override
    public void run() {
        Random random = new Random();

        while (true) {
            try {
                sleep(random.nextInt(500, 6000));
                if (Main.debug) {
                    System.out.println("bank.Bank Generator Thread sleeps");
                }
            } catch (InterruptedException e) {
                //nic se nedeje, tak pospim o neco mene
               // throw new RuntimeException(e);
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
            BankOrder order = new BankOrder
                    .BankOrderCreator()
                    .setId(id)
                    .setAmount(random.nextInt(1, 20000))
                    .setFrom(from)
                    .setTo(to)
                    .setTime(time.getTime())
                    .createBankOrder();
            server.receive(order);
            id++;
            if (Main.debug) {
                System.out.println("Event created");
                System.out.println(order);
            }
        }
    }


    public void setGenerator(Bank bank, Time time) {
        this.bank = bank;
        this.server = bank.getServer();
        this.time = time;
    }
}
