package fx.tradesjournal.controllers;

import fx.tradesjournal.model.DefaultSymbols;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SizeCalculatorController {
    @FXML ComboBox<String> symbolField;
    @FXML TextField sideRiskField;
    @FXML TextField sideSlPipsField;
    @FXML Label sideLotResultLabel;

    // Initializer
    @FXML
    public void initialize() {
        symbolField.getItems().addAll(DefaultSymbols.getAllSymbols());
    }
}
