package fx.tradesjournal.model;

import java.util.ArrayList;

public class Journal {
    private String name;

    private double initCapital = 0;

    private Currency currency;

    private Leverage leverage;

    private double actualCapital;

    private ArrayList<Trade> trades;

    public Journal() {}

    public Journal(String name, double initCapital, Currency currency, Leverage leverage) {
        this.name = name;
        this.currency = currency;
        this.leverage = leverage;
        this.initCapital = initCapital;
        this.actualCapital = initCapital;
    }

    public Journal(String name, double initCapital, Currency currency, Leverage leverage, ArrayList<Trade> trades) {
        this.name = name;
        this.initCapital = initCapital;
        this.currency = currency;
        this.leverage = leverage;
        this.trades = trades;
        this.actualCapital = initCapital;
    }

    public String getName() {
        return name;
    }

    public double getInitCapital() {
        return initCapital;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Leverage getLeverage() {
        return leverage;
    }

    public double getActualCapital() {
        return actualCapital;
    }
}