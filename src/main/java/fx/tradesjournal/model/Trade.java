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

    public Trade() {
        this.status = Status.OPEN;
    }

    public Trade(String symbol, Action action, String openDateTime, double entryPrice, double size, double fees) {
        super();
        this.symbol = symbol;
        this.action = action;
        this.openDateTime = openDateTime;
        this.entryPrice = entryPrice;
        this.size = size;
        this.fees = fees;
    }

    public Trade(String symbol, Action action, String openDateTime, double entryPrice, double closePrice, double size, double fees, double stopLoss, double takeProfit) {
        super();
        this.symbol = symbol;
        this.action = action;
        this.openDateTime = openDateTime;
        this.entryPrice = entryPrice;
        this.closePrice = closePrice;
        this.size = size;
        this.fees = fees;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

    public Trade(String symbol, Action action, String openDateTime, String closeDateTime, double entryPrice, double closePrice, double size, double fees, double stopLoss, double takeProfit, String strategy, String emotion, String notes) {
        super();
        this.symbol = symbol;
        this.action = action;
        this.openDateTime = openDateTime;
        this.closeDateTime = closeDateTime;
        this.entryPrice = entryPrice;
        this.closePrice = closePrice;
        this.size = size;
        this.fees = fees;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.strategy = strategy;
        this.emotion = emotion;
        this.notes = notes;
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