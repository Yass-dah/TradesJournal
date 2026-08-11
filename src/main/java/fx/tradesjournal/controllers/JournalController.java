package fx.tradesjournal.controllers;

import fx.tradesjournal.FxApplication;
import fx.tradesjournal.model.Journal;
import fx.tradesjournal.model.Trade;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class JournalController {
    @FXML private TableView<Trade> tradesTableView;
    @FXML private TableColumn<Trade, String> symbolColumn;
    @FXML private TableColumn<Trade, Double> sizeColumn;
    @FXML private TableColumn<Trade, String> actionColumn;
    @FXML private TableColumn<Trade, Double> openPriceColumn;
    @FXML private TableColumn<Trade, Double> stopLossColumn;
    @FXML private TableColumn<Trade, Double> takeProfitColumn;
    @FXML private TableColumn<Trade, String> statusColumn;

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
    public void populateTradesTable() {
        if (activeJournal.getTrades() != null) {
            tradesTableView.setItems(FXCollections.observableArrayList(activeJournal.getTrades()));
            tradesTableView.refresh();
        } else tradesTableView.getItems().clear();
    }

    public void setJournal(Journal journal) {
        this.activeJournal = journal;
        if(this.activeJournal != null) {
            populateTradesTable();
            journalIdentification.textProperty().bind(activeJournal.nameProperty());
            initialCapital.textProperty().bind(activeJournal.initCapitalProperty().asString());
            actualCapital.textProperty().bind(activeJournal.actualCapitalProperty().asString());
        }
    }

    @FXML
    public void initialize() {
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        openPriceColumn.setCellValueFactory(new PropertyValueFactory<>("entryPrice"));
        stopLossColumn.setCellValueFactory(new PropertyValueFactory<>("stopLoss"));
        takeProfitColumn.setCellValueFactory(new PropertyValueFactory<>("takeProfit"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
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
            tradeController.setJournal(activeJournal);

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
