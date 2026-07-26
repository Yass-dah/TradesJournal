package fx.tradesjournal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FxController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onTradeButtonClick() {
        welcomeText.setText("Welcome to your trades journal!");
    }
}