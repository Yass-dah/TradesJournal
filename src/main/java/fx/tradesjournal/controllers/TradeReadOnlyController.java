package fx.tradesjournal.controllers;

import fx.tradesjournal.model.Trade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class TradeReadOnlyController {
    private Trade tradeToView;

    @FXML private HBox titleBar;
    @FXML private Label symbolLabel;
    @FXML private Label typeLabel;
    @FXML private Label statusLabel;
    @FXML private Label entryDateTimeLabel;
    @FXML private Label entryPriceLabel;
    @FXML private Label exitPriceLabel;
    @FXML private Label sizeLabel;
    @FXML private Label feesLabel;
    @FXML private Label stopLossLabel;
    @FXML private Label takeProfitLabel;
    @FXML private Label profitLossLabel;
    @FXML private Label notesLabel;

    // Setters
    public void setTradeToView(Trade tradeToView) {
        this.tradeToView = tradeToView;
        if(tradeToView != null)
            populateFields();
    }

    private void populateFields(){
        statusLabel.setText(tradeToView.getStatus().name());
        statusLabel.setTextFill(statusLabel.getText().equals(Trade.Status.CLOSED.toString()) ? Color.web("#ffb74d") : Color.web("#0d6efd"));
        symbolLabel.setText(tradeToView.getSymbol());
        typeLabel.setText(tradeToView.getType().name());
        entryDateTimeLabel.setText(tradeToView.getDateTime());
        entryPriceLabel.setText(String.valueOf(tradeToView.getEntryPrice()));
        sizeLabel.setText(String.valueOf(tradeToView.getSize()));
        feesLabel.setText(String.valueOf(tradeToView.getFees()));
        exitPriceLabel.setText(tradeToView.getClosePrice() != null ? String.valueOf(tradeToView.getClosePrice()) : "");
        profitLossLabel.setText(tradeToView.getProfitLoss() != null ? String.valueOf(tradeToView.getProfitLoss()) : "");
        if(tradeToView.getProfitLoss() != null) {
            if (tradeToView.getProfitLoss() > 0)
                profitLossLabel.setTextFill(Color.web("#28a745"));
            else
                profitLossLabel.setTextFill(tradeToView.getProfitLoss() == 0 ? Color.BLACK : Color.web("#dc3545"));
        }
        stopLossLabel.setText(tradeToView.getStopLoss() != null ? String.valueOf(tradeToView.getStopLoss()) : "");
        takeProfitLabel.setText(tradeToView.getTakeProfit() != null ? String.valueOf(tradeToView.getTakeProfit()) : "");
        notesLabel.setText(tradeToView.getNotes() != null ? tradeToView.getNotes() : "");
    }

    // FXML handlers
    @FXML
    private void handleClose(){
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }
}
