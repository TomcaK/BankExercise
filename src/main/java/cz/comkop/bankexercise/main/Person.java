package cz.comkop.bankexercise.main;


import cz.comkop.bankexercise.bank.Owner;

import java.time.LocalDate;

public class Person extends Owner {
    private String name, surname;
    private LocalDate dateOfBirth;

    public Person(String name, String surname, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    @Override
    public String getName() {
        return name + " " + surname;
    }
}
