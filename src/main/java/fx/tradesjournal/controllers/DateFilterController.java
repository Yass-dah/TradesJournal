package fx.tradesjournal.controllers;

import fx.tradesjournal.model.Trade;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class DateFilterController {
    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;

    private FilteredList<Trade> filteredTrades;

    // Setter
    public void setFilteredTrades(FilteredList<Trade> filteredTrades) {
        this.filteredTrades = filteredTrades;
    }

    // Handlers
    @FXML
    protected void handleDateFilterChange() {
        if (filteredTrades == null) return;
        LocalDate fromSelected = fromDate.getValue();
        LocalDate toSelected = toDate.getValue();
        filteredTrades.setPredicate(trade -> {
            if (fromSelected == null && toSelected == null)
                return true;

            if (trade.getDateTime() == null || trade.getDateTime().trim().isEmpty())
                return false;

            LocalDate tradeDate;
            try {
                String datePart = trade.getDateTime().split(" ")[0];
                tradeDate = LocalDate.parse(datePart);
            } catch (Exception e) {
                return false;
            }

            boolean afterOrEqualFrom = (fromSelected == null) || !tradeDate.isBefore(fromSelected);
            boolean beforeOrEqualTo = (toSelected == null) || !tradeDate.isAfter(toSelected);
            return afterOrEqualFrom && beforeOrEqualTo;
        });
    }

    @FXML
    private void clearDatePickers(){
        fromDate.setValue(null);
        toDate.setValue(null);
        handleDateFilterChange();
    }
}
