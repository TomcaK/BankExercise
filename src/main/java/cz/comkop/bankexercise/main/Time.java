package cz.comkop.bankexercise.main;


import cz.comkop.bankexercise.bank.Bank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time implements Runnable {
    private Bank bank;
    private UI ui;
    private int hour = 2000;
    private LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), 00));
    private long millis = System.currentTimeMillis();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm d.M.y");

    @Override
    public void run() {
        System.out.println(time.format(formatter2));
        while (true) {
            ui.setTimeLabel(time.format(formatter2));
            try {
                Thread.sleep(hour);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            time = time.plusHours(1);
            if (time.getHour() == 0) {
                System.out.println(time.format(formatter2));
            } else {
                System.out.println(time.format(formatter));
            }
            if (time.getHour() >= 7 && time.getHour() <= 18) {
                ui.setOpenCloseLabel("OPENED");
                if (!Bank.isOpened()) {
                    System.out.println("****Good Morning!!!****");
                    System.out.println("****BANK OPENED****");
                }
                bank.setOpen(true);
            } else {
                ui.setOpenCloseLabel("CLOSED");
                if (Bank.isOpened()) {
                    System.out.println("****Have a nice evening!!!****");
                    System.out.println("****BANK CLOSED****");
                    bank.dailyReport(time);
                }
                bank.setOpen(false);
            }
        }
    }

    public void setInstances(Bank bank, UI ui) {
        this.bank = bank;
        this.ui = ui;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
