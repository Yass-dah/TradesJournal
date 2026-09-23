package fx.tradesjournal.controllers;

import fx.tradesjournal.model.*;
import fx.tradesjournal.persistence.FilePersistenceManager;

import fx.tradesjournal.services.CalculationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TradeController {
    private Journal journal;
    private Trade tradeToEdit;

    @FXML
    private VBox root;

    @FXML
    private HBox titleBar;

    @FXML
    private Label formTitleLabel;

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
        this.tradeToEdit = null;
    }

    public void setJournalAndTrade(Journal journal, Trade tradeToEdit){
        this.journal = journal;
        this.tradeToEdit = tradeToEdit;
        if(tradeToEdit != null)
            populateFields();
    }

    private void populateFields(){
        statusLabel.setText(tradeToEdit.getStatus().name());
        symbolComboBox.setValue(tradeToEdit.getSymbol());
        typeComboBox.setValue(tradeToEdit.getType());

        if (tradeToEdit.getDateTime() != null) {
            String[] parts = tradeToEdit.getDateTime().split(" ");
            entryDatePicker.setValue(LocalDate.parse(parts[0]));
            if (parts.length > 1) entryTimeField.setText(parts[1]);
        }

        entryPriceField.setText(String.valueOf(tradeToEdit.getEntryPrice()));
        sizeField.setText(String.valueOf(tradeToEdit.getSize()));
        feesField.setText(String.valueOf(tradeToEdit.getFees()));
        exitPriceField.setText(tradeToEdit.getClosePrice() != null ? String.valueOf(tradeToEdit.getClosePrice()) : "");
        profitLossField.setText(tradeToEdit.getProfitLoss() != null ? String.valueOf(tradeToEdit.getProfitLoss()) : "");
        stopLossField.setText(tradeToEdit.getStopLoss() != null ? String.valueOf(tradeToEdit.getStopLoss()) : "");
        takeProfitField.setText(tradeToEdit.getTakeProfit() != null ? String.valueOf(tradeToEdit.getTakeProfit()) : "");
        notesArea.setText(tradeToEdit.getNotes() != null ? tradeToEdit.getNotes() : "");
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
            statusLabel.setStyle("-fx-text-fill: #0d6efd; -fx-font-weight: bold;");
        }
    }

    private Double getCleanValue(String value) {
        return value == null || value.isEmpty() ? null : Double.parseDouble(value);
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
        Double exitPriceClean = getCleanValue(exitPrice);
        Double profitLossClean = getCleanValue(profitLoss);
        Double stopLossClean = getCleanValue(stopLoss);
        Double takeProfitClean = getCleanValue(takeProfit);

        if(tradeToEdit != null){
            Trade oldTrade = tradeToEdit;
            tradeToEdit.setStatus(status);
            tradeToEdit.setSymbol(symbol);
            tradeToEdit.setAction(type);
            tradeToEdit.setDateTime(formattedDate);
            tradeToEdit.setEntryPrice(Double.parseDouble(entryPrice));
            tradeToEdit.setClosePrice(exitPriceClean);
            tradeToEdit.setProfitLoss(profitLossClean);
            tradeToEdit.setSize(Double.parseDouble(size));
            tradeToEdit.setFees(Double.parseDouble(fees));
            tradeToEdit.setStopLoss(stopLossClean);
            tradeToEdit.setTakeProfit(takeProfitClean);
            tradeToEdit.setNotes(notes);
            journal.setActualCapital(CalculationService.editedTrade(journal.getActualCapital(), oldTrade, tradeToEdit));
        } else {
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
            journal.setActualCapital(CalculationService.addedTrade(journal.getActualCapital(), trade));
        }
        FilePersistenceManager.saveJournal(journal);
        ((Stage)titleBar.getScene().getWindow()).close();
    }
}
