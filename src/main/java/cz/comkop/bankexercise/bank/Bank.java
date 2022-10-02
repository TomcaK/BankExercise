package cz.comkop.bankexercise.bank;



import cz.comkop.bankexercise.main.Time;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

public class Bank extends Thread {
    private BankServer server = new BankServer();
    private List<Account> accounts = new ArrayList<>();
    private static boolean opened;
    private Time time;


    public Bank() {
        accounts.add(new Account(326714, 29000, Account.AccountType.PERSONAL, new Person("Tomas", "Kopulety", LocalDate.of(1991, 11, 14))));
        accounts.add(new Account(145978, 9640000, Account.AccountType.COMPANY, new Company("Rene Kollar since 2001 s.r.o.", 1991)));
        accounts.add(new Account(957145, 5000, Account.AccountType.SAVING, new Person("Andrej", "Mazak", LocalDate.of(1973, 5, 3))));
        accounts.add(new Account(788254, 84500, Account.AccountType.PERSONAL, new Person("Jiri", "Zmeskal", LocalDate.of(1991, 6, 13))));
        accounts.add(new Account(428714, 43000, Account.AccountType.SAVING, new Person("Iveta", "Kopackova", LocalDate.of(1993, 11, 30))));
        accounts.add(new Account(328514, 236000, Account.AccountType.PERSONAL, new Person("Jakub", "Machu", LocalDate.of(1993, 5, 4))));
        accounts.add(new Account(147247, 54000, Account.AccountType.SAVING, new Person("Martina", "Fialova", LocalDate.of(1885, 1, 1))));
        accounts.add(new Account(364782, 3000, Account.AccountType.COMPANY, new Company("Verdammte Sägen", 2022)));
        accounts.add(new Account(589316, 36741, Account.AccountType.PERSONAL, new Person("Milos", "Docekal", LocalDate.of(1993, 5, 4))));
        accounts.add(new Account(432967, 150000, Account.AccountType.SAVING, new Person("Radka", "Hojgrova", LocalDate.of(1990, 2, 7))));
        accounts.add(new Account(693214, 96300, Account.AccountType.PERSONAL, new Person("Zdenka", "Havelkova", LocalDate.of(1984, 12, 24))));
        accounts.add(new Account(249531, 2350000, Account.AccountType.SAVING, new Person("Marcel", "Buday", LocalDate.of(1820, 3, 8))));
        accounts.add(new Account(136974, 13000, Account.AccountType.PERSONAL, new Person("Nikola", "Mrazova", LocalDate.of(1984, 12, 24))));
        accounts.add(new Account(785496, 145000, Account.AccountType.PERSONAL, new Person("Matus", "Bernart", LocalDate.of(1987, 4, 4))));
        accounts.add(new Account(167589, 94000, Account.AccountType.COMPANY, new Company("Raděj umřít než TAM pracovat s.r.o.", 2018)));
        accounts.add(new Account(659287, 54000, Account.AccountType.COMPANY, new Company("I hate my job inc.", 2018)));
        accounts.add(new Account(769231, 21000, Account.AccountType.COMPANY, new Company("Musím co nejdřív vypadnout z toho pekla a.s.", 2022)));
    }

    public long getSumOfBalance() {
        return accounts.stream().map(Account::getBalance).reduce(0L, Long::sum);
    }


    public List<Account> getAccounts() {
        return accounts;
    }

    public void createPayment(Account from, Account to) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert amount of payment");
        int amount = scanner.nextInt();
        if (amount <= from.getBalance()) {
            //  paymentOperation(new bank.BankOrder(amount, from, to));
            System.out.println("bank.BankOrder successful");
        } else {
            System.out.println("bank.BankOrder unsuccessful");
        }

    }

    public void createPayment(int fromAccNumber, int toAccNumber) {
        int fromAccIndex = 0, toAccIndex = 0;
        for (int i = 0; i < accounts.size(); i++) {
            if (fromAccNumber == accounts.get(i).getNumber()) {
                fromAccIndex = i;
            }
            if (toAccNumber == accounts.get(i).getNumber()) {
                toAccIndex = i;
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert amount of payment");
        int amount = scanner.nextInt();
        if (amount <= accounts.get(fromAccIndex).getBalance()) {
            orderOperations(new BankOrder
                    .BankOrderCreator()
                    .setAmount(amount)
                    .setFrom(accounts.get(fromAccIndex))
                    .setTo(accounts.get(toAccIndex))
                    .createBankOrder());
            System.out.println("bank.BankOrder successful");
        } else {
            System.out.println("bank.BankOrder unsuccessful");
        }
    }

    private boolean checkBalance(BankOrder bankOrder) {
        if (bankOrder.getType().equals(BankOrder.OrderType.WITHDRAW) || bankOrder.getType().equals(BankOrder.OrderType.PAYMENT)) {
            return bankOrder.getAmount() <= bankOrder.getFrom().getBalance();
        }
        return true;
    }


    private boolean orderOperations(BankOrder bankOrder) {
        Owner owner;
        bankOrder.setTime(time.getTime());
        switch (bankOrder.getType()) {
            case DEPOSIT -> {
                long balanceBefore = bankOrder.getTo().getBalance();
                increaseBalance(bankOrder);
                owner = bankOrder.getTo().getOwner();
                System.out.printf("%s %s deposits %s Kč to account n.%s. Account balance before order: %s Kč. New account balance: %s Kč\n", owner.getClass().getSimpleName(), owner.getName(), bankOrder.getAmount(), bankOrder.getTo().getNumber(), balanceBefore, bankOrder.getTo().getBalance());
            }
            case PAYMENT -> {
                long balanceBefore = bankOrder.getFrom().getBalance();
                decreaseBalance(bankOrder);
                increaseBalance(bankOrder);
                owner = bankOrder.getFrom().getOwner();
                Owner owner2 = bankOrder.getTo().getOwner();
                System.out.printf("%s %s sends %s Kč from account n.%s to %s, account n.%s. Account balance before order: %s Kč. New account balance: %s Kč\n", owner.getClass().getSimpleName(), owner.getName(), bankOrder.getAmount(), bankOrder.getFrom().getNumber(), owner2.getName(), bankOrder.getTo().getNumber(), balanceBefore, bankOrder.getFrom().getBalance());

            }
            case WITHDRAW -> {
                long balanceBefore = bankOrder.getFrom().getBalance();
                decreaseBalance(bankOrder);
                owner = bankOrder.getFrom().getOwner();
                System.out.printf("%s %s withdraws %s Kč from account n.%s. Account balance before order: %s Kč. New account balance: %s Kč\n", owner.getClass().getSimpleName(), owner.getName(), bankOrder.getAmount(), bankOrder.getFrom().getNumber(), balanceBefore, bankOrder.getFrom().getBalance());
            }
        }
        return true;
    }

    public void decreaseBalance(BankOrder bankOrder) {
        bankOrder.getFrom().getHistoryOfPayments().add(bankOrder);
        bankOrder.getFrom().setBalance(bankOrder.getFrom().getBalance() - bankOrder.getAmount());
    }

    public void increaseBalance(BankOrder bankOrder) {
        bankOrder.getTo().getHistoryOfPayments().add(bankOrder);
        bankOrder.getTo().setBalance(bankOrder.getTo().getBalance() + bankOrder.getAmount());
    }

    public void setOpen(boolean o) {
        opened = o;
    }


    public static boolean isOpened() {
        return opened;
    }


    @Override
    public void run() {
        while (true) {
            if (opened) {
                paymentListener();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void dailyReport(LocalDateTime dateTime) {
        String name = String.format("%s.txt", dateTime.format(DateTimeFormatter.ofPattern("d.M.y")));
        String separator = System.getProperty("file.separator");
        List<Path> pathStream;
        try {
            pathStream = Files.find(Path.of(System.getProperty("user.dir")), Integer.MAX_VALUE, (p, basicFileAttributes) ->
                    p.getFileName().toString().equals("java")).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String javaFile = !separator.equals("\\") ? pathStream.toString().replaceAll(Matcher.quoteReplacement("\\"), separator) : pathStream.toString();
        String packagePath = !separator.equals("\\") ? this.getClass().getPackage().getName().replaceAll("\\.", separator) : this.getClass().getPackage().getName().replaceAll("\\.", Matcher.quoteReplacement("\\"));
        javaFile = javaFile.replaceAll("\\[|\\]", "").concat(separator);
        Path directoryPath = Path.of(javaFile + packagePath + separator + "reports");
        File file = new File(directoryPath.toString(), name);
        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectory(directoryPath);
            }
            if (!file.createNewFile()) {
                if (file.delete()) {
                    file.createNewFile();
                }
            }
            System.out.println("****Daily report " + name + " created in " + directoryPath + "****");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        accounts.forEach(account -> {
            try {
                Files.write(file.toPath(), (account.getOwner().getClass().getSimpleName() + " " + account.getOwner().getName() + "\n" + account + "\n****Today's orders****\n").getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            account.getHistoryOfPayments().stream().filter(bankOrder -> bankOrder.getTime().toLocalDate() == dateTime.toLocalDate()).forEach(bankOrder -> {
                try {
                    Files.write(file.toPath(), (bankOrder + "\n").getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                Files.write(file.toPath(), ("-----------------------------------------------------------------------\n\n\n\n").getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            if (!server.isEmpty()) {
                Files.write(file.toPath(), ("\n\n\n\nXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX\n Awaiting orders \n").getBytes(), StandardOpenOption.APPEND);
                for (int i = 0; i < server.size(); i++) {
                    Files.write(file.toPath(), (server.get(i) + "\n").getBytes(), StandardOpenOption.APPEND);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void paymentListener() {
        if (!server.isEmpty()) {
            int size = server.size();
            System.out.printf("Awaiting orders: %s\n", size);
            int count = 0;
            for (int i = 0; i < server.size(); i++) {
                if (checkBalance(server.get(i)) && orderOperations(server.get(i))) {
                    server.operationComplete(i);
                    i--;
                    count++;
                }
            }
            System.out.printf("Orders processed: %s\n\n", count);
        }
    }

    public BankServer getServer() {
        return server;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}


