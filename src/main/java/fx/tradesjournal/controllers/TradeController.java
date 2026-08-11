package fx.tradesjournal.controllers;

import fx.tradesjournal.model.Currency;
import fx.tradesjournal.model.DefaultSymbols;
import fx.tradesjournal.model.Leverage;
import fx.tradesjournal.model.Trade;
import fx.tradesjournal.persistence.FilePersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TradeController {
    @FXML
    private VBox root;

    @FXML
    private HBox titleBar;

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
    private TextArea notesArea;

    @FXML
    private Label errorLabel;

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

    @FXML
    public void initialize() {
        symbolComboBox.getItems().addAll(DefaultSymbols.getAllSymbols());
        typeComboBox.getItems().addAll(Trade.Action.LONG, Trade.Action.SHORT);
    }

    @FXML
    private void handleCancel(){
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSave(){
        String symbol = symbolComboBox.getValue();
        Trade.Action type = typeComboBox.getValue();
        LocalDate entryDate = entryDatePicker.getValue();
        String entryTime = entryTimeField.getText();
        String entryPrice = entryPriceField.getText();
        String size = sizeField.getText();
        String fees = feesField.getText();

        if(!checkMandatoryFields(symbol, type, entryDate, entryTime, entryPrice, size, fees))
            return;

        String formattedDate = entryDate.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + entryTime;

        Trade trade = new Trade(symbol, type, formattedDate, Double.parseDouble(entryTime), Double.parseDouble(size), Double.parseDouble(fees));


    }
}
