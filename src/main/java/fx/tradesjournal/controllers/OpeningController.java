package fx.tradesjournal.controllers;

import fx.tradesjournal.FxApplication;
import fx.tradesjournal.model.Currency;
import fx.tradesjournal.model.Journal;
import fx.tradesjournal.model.Leverage;
import fx.tradesjournal.persistence.FilePersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OpeningController {
    private FxApplication app;

    @FXML
    private VBox root;

    @FXML
    private HBox titleBar;

    @FXML
    private Label headerTitleLabel;

    @FXML
    private Button newJournalBtn;

    @FXML
    private VBox selectBox;

    @FXML
    private VBox createBox;

    @FXML
    private ComboBox<String> journals;

    @FXML
    private TextField journalNameField;

    @FXML
    private TextField initialCapitalField;

    @FXML
    private ComboBox<Currency> currencyField;

    @FXML
    private ComboBox<Leverage> leverageField;

    // Setters
    public void setApp(FxApplication app) {
        this.app = app;
    }

    // Error triggers
    private void displayError(TextField tf, String error){
        if(tf == null) return;
        tf.setStyle("-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: red; -fx-border-radius: 3; -fx-prompt-text-fill: #ff4b4b;");
        tf.clear();
        tf.setPromptText(error);
    }

    // Checkers
    private boolean checkJournalFields(String name, String capital, Currency currency, Leverage leverage){
        if (name == null || name.trim().isEmpty()) {
            displayError(journalNameField, "Journal's name can't be empty.");
            return false;
        }

        if (capital == null || capital.trim().isEmpty()) {
            displayError(initialCapitalField, "Initial capital can't be empty.");
            return false;
        }

        if (currency == null) {
            currencyField.setStyle("-fx-background-color: #ff6363; -fx-border-color: red; -fx-border-radius: 1; -fx-text-fill: white; -fx-cursor: hand");
            return false;
        }

        if(leverage == null){
            leverageField.setStyle("-fx-background-color: #ff6363; -fx-border-color: red; -fx-border-radius: 1; -fx-text-fill: white; -fx-cursor: hand");
            return false;
        }

        try {
            double capitalSize = Double.parseDouble(capital.trim());
            if (capitalSize <= 0) {
                displayError(initialCapitalField, "Initial capital have to be > 0.");
                return false;
            }
        } catch (NumberFormatException e) {
            displayError(initialCapitalField, "Initial capital have to be a valid number (e.g. 1000 or 1500.50).");
            return false;
        }
        return true;
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        List<String> journalList = FilePersistenceManager.getJournalFiles();
        journals.getItems().addAll(journalList != null ? journalList : new ArrayList<>());
        currencyField.getItems().addAll(Currency.values());
        leverageField.getItems().addAll(Leverage.values());
    }

    @FXML
    private void showCreateMode() {
        selectBox.setVisible(false);
        selectBox.setManaged(false);
        newJournalBtn.setVisible(false);

        root.getScene().getWindow().setHeight(328.0);
        createBox.setVisible(true);
        createBox.setManaged(true);

        headerTitleLabel.setText("Create New Journal");
    }

    @FXML
    private void showSelectMode() {
        journalNameField.clear();
        initialCapitalField.clear();
        currencyField.getSelectionModel().clearSelection();
        currencyField.setPromptText("Select Currency");
        leverageField.getSelectionModel().clearSelection();
        leverageField.setPromptText("Select Leverage");
        journals.getItems().clear();
        journals.getItems().addAll(FilePersistenceManager.getJournalFiles() != null ?
                FilePersistenceManager.getJournalFiles() : new ArrayList<>());

        root.getScene().getWindow().setHeight(232.0);
        createBox.setVisible(false);
        createBox.setManaged(false);

        selectBox.setVisible(true);
        selectBox.setManaged(true);
        newJournalBtn.setVisible(true);

        headerTitleLabel.setText("Welcome Back!");
    }

    @FXML
    private void createJournal(){
        if(checkJournalFields(journalNameField.getText(),initialCapitalField.getText(),currencyField.getValue(),leverageField.getValue())) {
            Journal journal = new Journal(journalNameField.getText(), Double.parseDouble(initialCapitalField.getText().trim()), currencyField.getValue(), leverageField.getValue());
            File aux = FilePersistenceManager.createJournalFile(journal);
            if(aux == null) {
                displayError(journalNameField, "This name is already used for another journal.");
                return;
            }
            showSelectMode();
        }
    }

    @FXML
    private void submitJournal(){
        if(journals.getValue() != null) {
            try{
                app.journal(journals.getValue().trim());
            } catch(IOException e){
                System.out.println(e.getMessage());
            }
        } else journals.setStyle("-fx-background-color: #ff6363; -fx-border-color: red; -fx-border-radius: 1; -fx-text-fill: white; -fx-cursor: hand");
    }
}