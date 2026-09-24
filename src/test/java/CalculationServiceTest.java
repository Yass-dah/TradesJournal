import fx.tradesjournal.model.Trade;
import fx.tradesjournal.services.CalculationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculationServiceTest {
    @Test
    @DisplayName("Added profitable trade with fees = capital + Net")
    void addedProfitableTrade() {
        double initialCapital = 1000.0;
        Trade trade = new Trade();
        trade.setProfitLoss(150.0);
        trade.setFees(10.0);
        double result = CalculationService.addedTrade(initialCapital, trade);

        assertEquals(1140.0, result);
    }

    @Test
    @DisplayName("Added still not closed trade with fees = capital - fees")
    void addedTrade_NullProfitLoss_TreatsAsZero() {
        double initialCapital = 1000.0;
        Trade trade = new Trade();
        trade.setProfitLoss(null);
        trade.setFees(5.0);
        double result = CalculationService.addedTrade(initialCapital, trade);

        assertEquals(995.0, result);
    }

    @Test
    @DisplayName("Edited trade PL & fees = capital - oldNet + newNet")
    void editedTrade_ModifiesCapitalCorrectly() {
        double currentCapital = 1140.0;

        Trade oldTrade = new Trade();
        oldTrade.setProfitLoss(150.0);
        oldTrade.setFees(10.0);

        Trade newTrade = new Trade();
        newTrade.setProfitLoss(200.0);
        newTrade.setFees(15.0);
        double result = CalculationService.editedTrade(currentCapital, oldTrade, newTrade);

        assertEquals(1185.0, result);
    }

    @Test
    @DisplayName("Deleted trade with fees = capital - Net")
    void deletedTrade_RestoresPreviousCapital() {
        double currentCapital = 1140.0;
        Trade tradeToDelete = new Trade();
        tradeToDelete.setProfitLoss(150.0);
        tradeToDelete.setFees(10.0);
        double result = CalculationService.deletedTrade(currentCapital, tradeToDelete);

        assertEquals(1000.0, result);
    }
}