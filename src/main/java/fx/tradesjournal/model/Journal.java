package fx.tradesjournal.model;

import java.util.ArrayList;

public class Journal {
    private String name;

    private ArrayList<Trade> trades;

    public Journal() {}

    public Journal(String name) {
        this.name = name;
    }

    public Journal(String name, ArrayList<Trade> trades) {
        this.name = name;
        this.trades = trades;
    }

    public String getName() {
        return name;
    }
}