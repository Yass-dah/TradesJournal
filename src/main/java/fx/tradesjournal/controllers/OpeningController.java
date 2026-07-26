package fx.tradesjournal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class OpeningController {
    @FXML
    private HBox titleBar;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onTradeButtonClick() {
        welcomeText.setText("Welcome to your trades journal!");
    }

    @FXML
    private void handleClose() {
        // Chiude l'applicazione quando si clicca la X
        Stage stage = (Stage) titleBar.getScene().getWindow();
        stage.close();
    }
}