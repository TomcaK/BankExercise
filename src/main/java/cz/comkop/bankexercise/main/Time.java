package cz.comkop.bankexercise.main;


import cz.comkop.bankexercise.bank.Bank;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Time implements Runnable {
    private Bank bank;
    private UI ui;
    public final static int HOUR = 2000;
    private LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.of(LocalTime.now().getHour(), 00));
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm d.M.y");

    @Override
    public void run() {
        System.out.println(time.format(DATE_TIME_FORMATTER));
        while (true) {
            ui.setClockText(time.format(DATE_TIME_FORMATTER));
            try {
                Thread.sleep(HOUR);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            time = time.plusHours(1);
            if (time.getHour() == 0) {
                System.out.println(time.format(DATE_TIME_FORMATTER));
            } else {
                System.out.println(time.format(TIME_FORMATTER));
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
