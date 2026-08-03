package fx.tradesjournal.controllers;

import fx.tradesjournal.model.Journal;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TradesController {
    private Journal activeJournal;

    @FXML
    private HBox titleBar;

    // Setters
    public void setJournal(Journal journal) {
        this.activeJournal = journal;
    }

    @FXML
    private void handleClose(){
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }
}
