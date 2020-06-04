package ru.nsu.android.drinkwithme.model;

public class DrinkLiter {
    private long id;
    private String name;
    private int percent;
    private double liter;

    public DrinkLiter(String name, int percent, double liter) {
        this.name = name;
        this.percent = percent;
        this.liter = liter;
    }

    public DrinkLiter(long id, String name, int percent, double liter) {
        this.id = id;
        this.name = name;
        this.percent = percent;
        this.liter = liter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public double getLiter() {
        return liter;
    }

    public void setLiter(double liter) {
        this.liter = liter;
    }
}
