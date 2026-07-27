package fx.tradesjournal.controllers;

import fx.tradesjournal.persistence.PersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class OpeningController {
    @FXML
    private HBox titleBar;

    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox<String> journals;

    @FXML
    protected void onTradeButtonClick() {
        welcomeText.setText("Welcome to your trades journal!");
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        journals.getItems().addAll(PersistenceManager.getJournalFiles());
    }
}