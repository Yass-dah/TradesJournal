package fx.tradesjournal.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Journal {
    private SimpleStringProperty name;

    private SimpleDoubleProperty initCapital = new SimpleDoubleProperty(0);

    private Currency currency;

    private Leverage leverage;

    private SimpleDoubleProperty actualCapital;

    private ObservableList<Trade> trades;

    public Journal() {}

    public Journal(String name, double initCapital, Currency currency, Leverage leverage) {
        this.name = new SimpleStringProperty(name);
        this.currency = currency;
        this.leverage = leverage;
        this.initCapital = new SimpleDoubleProperty(initCapital);
        this.actualCapital = this.initCapital;
    }

    public Journal(String name, double initCapital, Currency currency, Leverage leverage, ArrayList<Trade> trades) {
        this.name = new SimpleStringProperty(name);
        this.initCapital = new SimpleDoubleProperty(initCapital);
        this.currency = currency;
        this.leverage = leverage;
        this.trades = FXCollections.observableArrayList(trades);
        this.actualCapital = new SimpleDoubleProperty(initCapital);
    }

    // Getters
    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public double getInitCapital() {
        return initCapital.get();
    }

    public SimpleDoubleProperty initCapitalProperty() {
        return initCapital;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Leverage getLeverage() {
        return leverage;
    }

    public double getActualCapital() {
        return actualCapital.get();
    }

    public SimpleDoubleProperty actualCapitalProperty() {
        return actualCapital;
    }
}