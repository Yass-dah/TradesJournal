package fx.tradesjournal.model;

import java.util.ArrayList;

public class Journal {
    private String name;

    private double initCapital = 0;

    private double actualCapital;

    private ArrayList<Trade> trades;

    public Journal() {}

    public Journal(String name, double initCapital) {
        this.name = name;
        this.initCapital = initCapital;
        this.actualCapital = initCapital;
    }

    public Journal(String name, double initCapital, ArrayList<Trade> trades) {
        this.name = name;
        this.initCapital = initCapital;
        this.trades = trades;
        this.actualCapital = initCapital;
    }

    public String getName() {
        return name;
    }

    public double getInitCapital() {
        return initCapital;
    }

    public double getActualCapital() {
        return actualCapital;
    }
}