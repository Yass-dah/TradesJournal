package fx.tradesjournal.controllers;

import fx.tradesjournal.model.DefaultSymbols;
import fx.tradesjournal.model.Journal;
import fx.tradesjournal.services.CalculationService;
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
    private Journal activeJournal;

    @FXML ComboBox<String> symbolField;
    @FXML TextField riskField;
    @FXML TextField slPipsField;
    @FXML Label symbolLabel;
    @FXML Label symbolPriceLabel;
    @FXML Label lotResultLabel;

    // Setter
    public void setJournal(Journal journal) {
        this.activeJournal = journal;
    }

    // Initializer
    @FXML
    public void initialize() {
        TradeController.populateSymbols(symbolField);
        symbolField.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                symbolLabel.setText("Loading...");
                fetchPrice(newValue);
                CalculationService.PositionSizeResult lotSize = CalculationService.calculateSize(
                        activeJournal.getCurrency().getLabel(),
                        activeJournal.getActualCapital(),
                        Double.parseDouble(riskField.getText() == null || riskField.getText().isEmpty() ? "0.0" : riskField.getText()),
                        Double.parseDouble(slPipsField.getText() == null || slPipsField.getText().isEmpty() ? "0.0" : slPipsField.getText()),
                        new CalculationService.SymbolInfo(
                                newValue,
                                DefaultSymbols.getCategoryForSymbol(newValue),
                                DefaultSymbols.getCurrencyForSymbol(newValue)),
                        marketDataService.fetchPriceBySymbol(newValue),
                        activeJournal.getLeverage().getValue());
                lotResultLabel.setText(String.format("%.2f", lotSize.positionSize()));
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
