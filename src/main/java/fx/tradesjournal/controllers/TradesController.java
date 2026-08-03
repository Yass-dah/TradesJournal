package fx.tradesjournal.controllers;

import fx.tradesjournal.model.Journal;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TradesController {
    private Journal activeJournal;

    @FXML
    private HBox titleBar;

    @FXML
    private Label journalIdentification;

    // Setters
    public void setJournal(Journal journal) {
        this.activeJournal = journal;
        if(this.activeJournal != null)
            journalIdentification.textProperty().bind(activeJournal.nameProperty());
    }

    @FXML
    private void handleClose(){
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }
}
