package fx.tradesjournal.controllers;

import fx.tradesjournal.FxApplication;
import fx.tradesjournal.model.Journal;
import fx.tradesjournal.model.Trade;
import fx.tradesjournal.persistence.FilePersistenceManager;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Label profitLossPercent;

    @FXML
    private Label actualCapital;

    @FXML
    private Label pipsQt;


    public void populateTradesTable() {
        if (activeJournal.getTrades() != null) {
            tradesTableView.setItems(FXCollections.observableArrayList(activeJournal.getTrades()));
            tradesTableView.refresh();
        } else tradesTableView.getItems().clear();
    }

    // Setters
    public void setJournal(Journal journal) {
        this.activeJournal = journal;
        if(this.activeJournal != null) {
            populateTradesTable();
            journalIdentification.textProperty().bind(activeJournal.nameProperty());
            initialCapital.textProperty().bind(activeJournal.initCapitalProperty().asString());
            actualCapital.textProperty().bind(activeJournal.actualCapitalProperty().asString());
            profitLossPercent.textProperty().bind(
                    activeJournal.actualCapitalProperty()
                            .subtract(activeJournal.initCapitalProperty())
                            .divide(activeJournal.initCapitalProperty())
                            .multiply(100)
                            .asString("%.2f%%")
            );
        }
    }

    private void setupTableContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Delete Trade");
        deleteItem.setOnAction(event -> {
            Trade selectedTrade = tradesTableView.getSelectionModel().getSelectedItem();
            if (selectedTrade != null)
                handleDeleteTrade(selectedTrade);
        });

        MenuItem editItem = new MenuItem("Edit Trade");
        editItem.setOnAction(event -> {
            Trade selectedTrade = tradesTableView.getSelectionModel().getSelectedItem();
            if (selectedTrade != null)
                handleEditTrade(selectedTrade);
        });

        contextMenu.getItems().add(editItem);
        contextMenu.getItems().add(deleteItem);
        tradesTableView.setRowFactory(tv -> {
            TableRow<Trade> row = new TableRow<>();
            row.contextMenuProperty().bind(javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            return row;
        });
    }

    private void handleDeleteTrade(Trade trade) {
        activeJournal.getTrades().remove(trade);
        activeJournal.setActualCapital(activeJournal.getActualCapital()-trade.getProfitLoss());
        FilePersistenceManager.saveJournal(activeJournal);
        populateTradesTable();
    }

    private void handleEditTrade(Trade trade) {
        try {
            FXMLLoader loader = new FXMLLoader(FxApplication.class.getResource("trade-form-view.fxml"));
            Parent addTradeRoot = loader.load();

            TradeController tradeController = loader.getController();
            tradeController.setJournalAndTrade(activeJournal, trade);

            Stage modalStage = new Stage();
            Stage mainStage = (Stage) titleBar.getScene().getWindow();

            modalStage.initOwner(mainStage);
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.setScene(new Scene(addTradeRoot));
            modalStage.showAndWait();
            refreshTradeTable();
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
        setupTableContextMenu();
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
            refreshTradeTable();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void refreshTradeTable() {
        tradesTableView.setItems(FXCollections.observableArrayList(activeJournal.getTrades()));
        tradesTableView.refresh();
    }
}
