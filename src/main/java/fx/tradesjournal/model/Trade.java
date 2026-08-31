package fx.tradesjournal.model;

public class Trade {
    public enum Action { LONG, SHORT }
    public enum Status { OPEN, CLOSED }

    private String symbol;
    private double size;
    private Action action;

    private double entryPrice;
    private Double closePrice;
    private Double profitLoss;

    private Double stopLoss;
    private Double takeProfit;

    private String dateTime;

    private double fees;
    private Status status;

    private String notes;

    public Trade(Status status, String symbol, Action action, String dateTime, double entryPrice, double size, double fees) {
        this.status = Status.OPEN;
        this.symbol = symbol;
        this.action = action;
        this.dateTime = dateTime;
        this.entryPrice = entryPrice;
        this.size = size;
        this.fees = fees;
    }

    public Trade(Status status, String symbol, Action action, String dateTime, double entryPrice, Double closePrice, double size, double fees, Double stopLoss, Double takeProfit) {
        this.status = Status.OPEN;
        this.symbol = symbol;
        this.action = action;
        this.dateTime = dateTime;
        this.entryPrice = entryPrice;
        this.closePrice = closePrice;
        this.size = size;
        this.fees = fees;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

    public Trade(Status status, String symbol, Action action, String dateTime, double entryPrice, Double closePrice, Double profitLoss, double size, double fees, Double stopLoss, Double takeProfit, String notes) {
        this.status = status;
        this.symbol = symbol;
        this.action = action;
        this.dateTime = dateTime;
        this.entryPrice = entryPrice;
        this.closePrice = closePrice;
        this.profitLoss = profitLoss;
        this.size = size;
        this.fees = fees;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
        this.notes = notes;
    }

    // Getters
    public String getSymbol() {
        return symbol;
    }

    public double getSize() {
        return size;
    }

    public Action getType() {
        return action;
    }

    public double getEntryPrice() { return entryPrice; }

    public Double getClosePrice() { return closePrice; }

    public Double getProfitLoss() {
        return profitLoss;
    }

    public Double getStopLoss() {
        return stopLoss;
    }

    public Double getTakeProfit() {
        return takeProfit;
    }

    public String getDateTime() {
        return dateTime;
    }

    public double getFees() {
        return fees;
    }

    public Status getStatus() { return status; }

    public String getNotes() {
        return notes;
    }
}