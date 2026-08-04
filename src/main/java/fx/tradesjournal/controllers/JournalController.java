package fx.tradesjournal.controllers;

import fx.tradesjournal.FxApplication;
import fx.tradesjournal.model.Journal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class JournalController {
    private Journal activeJournal;

    @FXML
    private HBox titleBar;

    @FXML
    private Label journalIdentification;

    @FXML
    private Label initialCapital;

    @FXML
    private Label actualCapital;

    // Setters
    public void setJournal(Journal journal) {
        this.activeJournal = journal;
        if(this.activeJournal != null) {
            journalIdentification.textProperty().bind(activeJournal.nameProperty());
            initialCapital.textProperty().bind(activeJournal.initCapitalProperty().asString());
            actualCapital.textProperty().bind(activeJournal.actualCapitalProperty().asString());
        }
    }

    @FXML
    private void handleClose(){
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAddTrade(){
        try {
            FXMLLoader loader = new FXMLLoader(FxApplication.class.getResource("trade-form-view.fxml"));
            Parent addTradeRoot = loader.load();
            TradeController tradeController = loader.getController();

            Stage modalStage = new Stage();
            Stage mainStage = (Stage) titleBar.getScene().getWindow();

            modalStage.initOwner(mainStage);
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.setScene(new Scene(addTradeRoot));
            modalStage.showAndWait();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
