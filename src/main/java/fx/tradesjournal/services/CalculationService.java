package fx.tradesjournal.services;

import fx.tradesjournal.model.Trade;

public class CalculationService {
    private static double getProfitLossValue(Double value) {
        return value == null ? 0.0 : value;
    }

    public static double addedTrade(double capital, Trade trade) {
        return capital + (getProfitLossValue(trade.getProfitLoss()) - trade.getFees());
    }

    public static double editedTrade(double capital, Trade oldTrade, Trade newTrade) {
        return addedTrade(deletedTrade(capital, oldTrade), newTrade);
    }

    public static double deletedTrade(double capital, Trade trade) {
        return capital - (getProfitLossValue(trade.getProfitLoss()) + trade.getFees());
    }
}
