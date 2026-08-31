package fx.tradesjournal.controllers;

import fx.tradesjournal.model.*;
import fx.tradesjournal.persistence.FilePersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TradeController {
    private Journal journal;

    @FXML
    private VBox root;

    @FXML
    private HBox titleBar;

    @FXML
    private Label statusLabel;

    @FXML
    private ComboBox<String> symbolComboBox;

    @FXML
    private ComboBox<Trade.Action> typeComboBox;

    @FXML
    private DatePicker entryDatePicker;

    @FXML
    private TextField entryTimeField;

    @FXML
    private TextField entryPriceField;

    @FXML
    private TextField exitPriceField;

    @FXML
    private TextField sizeField;

    @FXML
    private TextField feesField;

    @FXML
    private TextField stopLossField;

    @FXML
    private TextField takeProfitField;

    @FXML
    private TextField profitLossField;

    @FXML
    private TextArea notesArea;

    @FXML
    private Label errorLabel;

    public void setJournal(Journal journal){
        this.journal = journal;
    }

    private boolean checkMandatoryFields(String symbol, Trade.Action type, LocalDate entryDate, String entryTime,
                                         String entryPrice, String size, String fees) {
        if (symbol == null) {
            errorLabel.setText("Please enter a symbol");
            return false;
        }
        if (type == null) {
            errorLabel.setText("Please enter a type");
            return false;
        }
        if (entryDate == null) {
            errorLabel.setText("Please enter a date");
            return false;
        }
        if (entryTime == null || entryTime.trim().isEmpty()) {
            errorLabel.setText("Please enter a time");
            return false;
        }
        if (entryPrice == null || entryPrice.trim().isEmpty()) {
            errorLabel.setText("Please enter a price");
            return false;
        }
        if (size == null || size.trim().isEmpty()) {
            errorLabel.setText("Please enter a size");
            return false;
        }
        if (fees == null || fees.trim().isEmpty()) {
            errorLabel.setText("Please enter fees");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private boolean checkClosingFields(String exitPrice, String profitLoss) {
        boolean exitPriceSet = exitPrice == null || !exitPrice.trim().isEmpty(),
                profitLossSet = profitLoss == null || !profitLoss.trim().isEmpty();
        if(exitPriceSet && !profitLossSet) {
            errorLabel.setText("Please provide also a profit/loss value with the exit price");
            return false;
        }
        if(!exitPriceSet && profitLossSet) {
            errorLabel.setText("Please provide also an exit price value with the profit/loss");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private void updateStatus(String text) {
        if (text != null && !text.trim().isEmpty()) {
            statusLabel.setText("CLOSED");
            statusLabel.setStyle("-fx-text-fill: #ffb74d; -fx-font-weight: bold;");
        } else {
            statusLabel.setText("OPEN");
            statusLabel.setStyle("-fx-text-fill: #00e676; -fx-font-weight: bold;");
        }
    }

    @FXML
    public void initialize() {
        symbolComboBox.getItems().addAll(DefaultSymbols.getAllSymbols());
        typeComboBox.getItems().addAll(Trade.Action.LONG, Trade.Action.SHORT);
        exitPriceField.textProperty().addListener((obs, oldValue, newValue) -> {
            updateStatus(newValue);
        });
    }

    @FXML
    private void handleCancel(){
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSave(){
        Trade.Status status = statusLabel.getText().equals("CLOSED") ? Trade.Status.CLOSED : Trade.Status.OPEN;
        String symbol = symbolComboBox.getValue();
        Trade.Action type = typeComboBox.getValue();
        LocalDate entryDate = entryDatePicker.getValue();
        String entryTime = entryTimeField.getText();
        String entryPrice = entryPriceField.getText();
        String exitPrice = exitPriceField.getText();
        String size = sizeField.getText();
        String fees = feesField.getText();
        String stopLoss = stopLossField.getText();
        String takeProfit = takeProfitField.getText();
        String profitLoss = profitLossField.getText();
        String notes = notesArea.getText();

        if(!checkMandatoryFields(symbol, type, entryDate, entryTime, entryPrice, size, fees))
            return;

        if(!checkClosingFields(exitPrice, profitLoss))
            return;

        String formattedDate = entryDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + entryTime;
        double exitPriceClean = exitPrice == null || exitPrice.isEmpty() ? 0.0 : Double.parseDouble(exitPrice);
        double profitLossClean = profitLoss == null || profitLoss.isEmpty() ? 0.0 : Double.parseDouble(profitLoss);
        double stopLossClean = stopLoss == null || stopLoss.isEmpty() ? 0.0 : Double.parseDouble(stopLoss);
        double takeProfitClean = takeProfit == null || takeProfit.isEmpty() ? 0.0 : Double.parseDouble(takeProfit);
        Trade trade = new Trade(status,
                symbol,
                type,
                formattedDate,
                Double.parseDouble(entryPrice),
                exitPriceClean,
                profitLossClean,
                Double.parseDouble(size),
                Double.parseDouble(fees),
                stopLossClean,
                takeProfitClean,
                notes);

        journal.getTrades().add(trade);
        FilePersistenceManager.saveJournal(journal);
        ((Stage)titleBar.getScene().getWindow()).close();
    }
}
