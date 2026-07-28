package fx.tradesjournal.controllers;

import fx.tradesjournal.persistence.FilePersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class OpeningController {
    @FXML
    private HBox titleBar;

    @FXML
    private ComboBox<String> journals;

    @FXML
    private void handleClose() {
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        journals.getItems().addAll(FilePersistenceManager.getJournalFiles());
    }
}