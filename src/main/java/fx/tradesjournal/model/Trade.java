package fx.tradesjournal.model;

public class Trade {
    private String asset;

    private double entryPrice;

    private double closePrice;

    private double stopLoss;

    private double takeProfit;

    public Trade() {}

    public Trade(String asset, double entryPrice) {
        this.asset = asset;
        this.entryPrice = entryPrice;
    }

    public Trade(String asset, double entryPrice, double stopLoss, double takeProfit) {
        this.asset = asset;
        this.entryPrice = entryPrice;
        this.stopLoss = stopLoss;
        this.takeProfit = takeProfit;
    }

    public String getAsset() {
        return asset;
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
}