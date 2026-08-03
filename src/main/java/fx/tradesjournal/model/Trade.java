package fx.tradesjournal.model;

public class Trade {
    public enum Action { LONG, SHORT }
    public enum Status { OPEN, CLOSED }

    private String symbol;
    private double size;
    private Action action;

    private double entryPrice;
    private double closePrice;

    private double stopLoss;
    private double takeProfit;

    private String openDateTime;
    private String closeDateTime;

    private double fees;
    private Status status;

    private String strategy;
    private String emotion;
    private String notes;

    public Trade() {}

    public Trade(String symbol, double entryPrice) {
        this.symbol = symbol;
        this.entryPrice = entryPrice;
    }

    public Trade(String symbol, double entryPrice, double stopLoss, double takeProfit) {
        this.symbol = symbol;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public double getSize() {
        return size;
    }

    public Action getAction() {
        return action;
    }

    public double getEntryPrice() {
        return entryPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public double getTakeProfit() {
        return takeProfit;
    }

    public String getOpenDateTime() {
        return openDateTime;
    }

    public String getCloseDateTime() {
        return closeDateTime;
    }

    public double getFees() {
        return fees;
    }

    public Status getStatus() {
        return status;
    }

    public String getStrategy() {
        return strategy;
    }

    public String getEmotion() {
        return emotion;
    }

    public String getNotes() {
        return notes;
    }
}