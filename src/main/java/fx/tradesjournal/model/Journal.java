package fx.tradesjournal.model;

import java.util.ArrayList;

public class Journal {
    private String name;

    private double initCapital = 0;

    private String currency;

    private double actualCapital;

    private ArrayList<Trade> trades;

    public Journal() {}

    public Journal(String name, double initCapital, String currency) {
        this.name = name;
        this.currency = currency;
        this.initCapital = initCapital;
        this.actualCapital = initCapital;
    }

    public Journal(String name, double initCapital, String currency, ArrayList<Trade> trades) {
        this.name = name;
        this.initCapital = initCapital;
        this.currency = currency;
        this.trades = trades;
        this.actualCapital = initCapital;
    }

    public String getName() {
        return name;
    }

    public double getInitCapital() {
        return initCapital;
    }

    public String getCurrency() {
        return currency;
    }

    public double getActualCapital() {
        return actualCapital;
    }
}