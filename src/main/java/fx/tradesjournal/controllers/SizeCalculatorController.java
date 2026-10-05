package fx.tradesjournal.controllers;

import fx.tradesjournal.model.DefaultSymbols;
import fx.tradesjournal.services.MarketDataService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class SizeCalculatorController {
    private final MarketDataService marketDataService = new MarketDataService();

    @FXML ComboBox<String> symbolField;
    @FXML TextField sideRiskField;
    @FXML TextField sideSlPipsField;
    @FXML Label symbolLabel;
    @FXML Label symbolPriceLabel;
    @FXML Label sideLotResultLabel;

    // Initializer
    @FXML
    public void initialize() {
        TradeController.populateSymbols(symbolField);
        symbolField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                symbolLabel.setText("Loading...");
                fetchPrice(newValue);
            }
        });
    }

    public void fetchPrice(String symbol) {
        new Thread(() -> {
            Double finalPrice = marketDataService.fetchPriceBySymbol(symbol);
            Platform.runLater(() -> {
                if(finalPrice == null) {
                    symbolLabel.setTextFill(Color.RED);
                    symbolLabel.setText("Can't reach server, try again later.");
                    return;
                }
                symbolLabel.setTextFill(Paint.valueOf("#a0a0a0"));
                symbolLabel.setText(symbol + " price:");
                symbolPriceLabel.setText(finalPrice.toString() + " " + DefaultSymbols.getCurrencyForSymbol(symbol));
            });
        }).start();
    }
}
