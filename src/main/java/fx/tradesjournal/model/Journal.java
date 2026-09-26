package fx.tradesjournal.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Journal {
    private String name;
    private double initCapital;
    private Currency currency;
    private Leverage leverage;
    private double actualCapital;
    private List<Trade> trades = new ArrayList<>();

    private transient StringProperty nameProperty;
    private transient DoubleProperty initCapitalProperty;
    private transient DoubleProperty actualCapitalProperty;
    private transient ObservableList<Trade> observableTrades;

    // Constructors
    public Journal() {}

    public Journal(String name, double initCapital, Currency currency, Leverage leverage) {
        this.name = name;
        this.initCapital = roundCapital(initCapital);
        this.actualCapital = initCapital;
        this.currency = currency;
        this.leverage = leverage;
        this.trades = new ArrayList<>();
    }

    public Journal(String name, double initCapital, Currency currency, Leverage leverage, List<Trade> trades) {
        this.name = name;
        this.initCapital = roundCapital(initCapital);
        this.actualCapital = initCapital;
        this.currency = currency;
        this.leverage = leverage;
        this.trades = trades != null ? trades : new ArrayList<>();
    }

    // Round method
    private double roundCapital(double capital) {
        return Math.round(capital * 100.0) / 100.0;
    }

    // Getters & Setters
    public String getName() {
        return nameProperty != null ? nameProperty.get() : name;
    }

    public void setName(String name) {
        this.name = name;
        if (this.nameProperty != null)
            this.nameProperty.set(name);
    }

    public double getInitCapital() {
        return initCapitalProperty != null ? initCapitalProperty.get() : initCapital;
    }

    public double getActualCapital() {
        return actualCapitalProperty != null ? actualCapitalProperty.get() : actualCapital;
    }

    public void setActualCapital(double actualCapital) {
        this.actualCapital = roundCapital(actualCapital);
        if (this.actualCapitalProperty != null)
            this.actualCapitalProperty.set(actualCapital);
    }

    public Currency getCurrency() {
        return currency;
    }

    public Leverage getLeverage() {
        return leverage;
    }

    public List<Trade> getTrades() {
        return trades;
    }

    // Properties
    public StringProperty nameProperty() {
        if(nameProperty == null)
            nameProperty = new SimpleStringProperty(name);
        return nameProperty;
    }

    public DoubleProperty initCapitalProperty() {
        if(initCapitalProperty == null)
            initCapitalProperty = new SimpleDoubleProperty(initCapital);
        return initCapitalProperty;
    }

    public DoubleProperty actualCapitalProperty() {
        if(actualCapitalProperty == null)
            actualCapitalProperty = new SimpleDoubleProperty(actualCapital);
        return actualCapitalProperty;
    }

    public ObservableList<Trade> getObservableTrades() {
        if (observableTrades == null) {
            if (trades == null)
                trades = new ArrayList<>();
            observableTrades = FXCollections.observableList(trades);
        }
        return observableTrades;
    }
}