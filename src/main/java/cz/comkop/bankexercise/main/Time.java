package cz.comkop.bankexercise.main;



import cz.comkop.bankexercise.bank.Bank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time implements Runnable {
    Bank bank;


    private int hour = 2000;
    private LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), 00));
    private long millis = System.currentTimeMillis();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
    private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("H:mm d.M.y");

    @Override
    public void run() {
        System.out.println(time.format(formatter2));
        while (true) {
            if (System.currentTimeMillis() > millis + hour) {
                time = time.plusHours(1);
                millis = System.currentTimeMillis();
                if (time.getHour() == 0) {
                    System.out.println(time.format(formatter2));
                } else {
                    System.out.println(time.format(formatter));
                }
                if (time.getHour() >= 7 && time.getHour() <= 18) {
                    if (!Bank.isOpened()) {
                        System.out.println("****Good Morning!!!****");
                        System.out.println("****BANK OPENED****");
                    }
                    bank.setOpen(true);
                } else {
                    if (Bank.isOpened()) {
                        System.out.println("****Have a nice evening!!!****");
                        System.out.println("****BANK CLOSED****");
                        bank.dailyReport(time);
                    }
                    bank.setOpen(false);
                }
            }
        }
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
