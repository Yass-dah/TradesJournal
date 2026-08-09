package fx.tradesjournal.controllers;

import fx.tradesjournal.model.Currency;
import fx.tradesjournal.model.DefaultSymbols;
import fx.tradesjournal.model.Leverage;
import fx.tradesjournal.persistence.FilePersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TradeController {
    @FXML
    private VBox root;

    @FXML
    private HBox titleBar;

    @FXML
    private ComboBox<String> symbolComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

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

    @FXML
    public void initialize() {
        symbolComboBox.getItems().addAll(DefaultSymbols.getAllSymbols());
        typeComboBox.getItems().addAll("BUY", "SELL");
    }

    @FXML
    private void handleCancel(){
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSave(){

    }
}
