package cz.comkop.exercises.bankexercise.bank;

public class Company implements Owner {
    private String name;
    private int yearOfFoundation;

    public Company(String name, int yearOfFoundation) {
        this.name = name;
        this.yearOfFoundation = yearOfFoundation;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", yearOfFoundation=" + yearOfFoundation +
                '}';
    }

    @Override
    public String getName() {
        return name;
    }
}
