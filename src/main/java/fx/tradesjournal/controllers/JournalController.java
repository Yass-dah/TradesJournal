package fx.tradesjournal.controllers;

import fx.tradesjournal.FxApplication;
import fx.tradesjournal.model.Journal;
import fx.tradesjournal.model.Trade;
import fx.tradesjournal.persistence.FilePersistenceManager;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.LocalDate;

public class JournalController {
    @FXML private TableView<Trade> tradesTableView;
    @FXML private TableColumn<Trade, String> symbolColumn;
    @FXML private TableColumn<Trade, Double> sizeColumn;
    @FXML private TableColumn<Trade, String> actionColumn;
    @FXML private TableColumn<Trade, Double> openPriceColumn;
    @FXML private TableColumn<Trade, Double> closePriceColumn;
    @FXML private TableColumn<Trade, Double> profitLossColumn;
    @FXML private TableColumn<Trade, String> statusColumn;
    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private Label mouseSectionLabel;

    private Journal activeJournal;
    private FilteredList<Trade> filteredTrades;

    @FXML
    private HBox titleBar;

    @FXML
    private AnchorPane tradesTable;

    @FXML
    private AnchorPane journalStats;

    @FXML
    private HBox statusBar;

    @FXML
    private Label journalIdentification;

    @FXML
    private Label initialCapital;

    @FXML
    private Label profitLossPercent;

    @FXML
    private Label actualCapital;

    @FXML
    private Label tradesQt;

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
            setupFilteredTable();
            journalIdentification.textProperty().bind(activeJournal.nameProperty());
            initialCapital.textProperty().bind(activeJournal.initCapitalProperty().asString("%.2f €"));
            actualCapital.textProperty().bind(activeJournal.actualCapitalProperty().asString("%.2f €"));
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

        MenuItem viewItem = new MenuItem("See Trade details");
        viewItem.setOnAction(event -> {
            Trade selectedTrade = tradesTableView.getSelectionModel().getSelectedItem();
            if (selectedTrade != null)
                handleViewTrade(selectedTrade);
        });

        contextMenu.getItems().add(editItem);
        contextMenu.getItems().add(deleteItem);
        contextMenu.getItems().add(viewItem);
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
        if(trade.getProfitLoss() != null)
            activeJournal.setActualCapital(activeJournal.getActualCapital()-(trade.getProfitLoss()-trade.getFees()));
        activeJournal.getObservableTrades().remove(trade);
        FilePersistenceManager.saveJournal(activeJournal);
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
            handleDateFilterChange();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleViewTrade(Trade trade){
        try {
            FXMLLoader loader = new FXMLLoader(FxApplication.class.getResource("trade-details-view.fxml"));
            Parent addTradeRoot = loader.load();

            TradeReadOnlyController tradeRoController = loader.getController();
            tradeRoController.setTradeToView(trade);

            Stage modalStage = new Stage();
            Stage mainStage = (Stage) titleBar.getScene().getWindow();

            modalStage.initOwner(mainStage);
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initStyle(StageStyle.UNDECORATED);
            modalStage.setScene(new Scene(addTradeRoot));
            modalStage.showAndWait();
            handleDateFilterChange();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setupFilteredTable() {
        if (activeJournal == null || activeJournal.getTrades() == null) {
            tradesTableView.getItems().clear();
            return;
        }

        filteredTrades = new FilteredList<>(activeJournal.getObservableTrades(), p -> true);
        SortedList<Trade> sortedTrades = new SortedList<>(filteredTrades);
        sortedTrades.comparatorProperty().bind(tradesTableView.comparatorProperty());
        tradesTableView.setItems(sortedTrades);
        tradesQt.textProperty().bind(Bindings.size(filteredTrades).asString());
    }

    private void setupSectionHoverTracker() {
        registerSectionHover(titleBar, "Title Bar");
        registerSectionHover(tradesTable, "Trades Table");
        registerSectionHover(journalStats, "Stats & Filters");
        registerSectionHover(statusBar, "Status Bar");
    }

    private void registerSectionHover(Node node, String sectionName) {
        if (node != null && mouseSectionLabel != null) {
            node.hoverProperty().addListener((observable, oldValue, isHovered) -> {
                if (isHovered)
                    mouseSectionLabel.setText(sectionName);
            });
        }
    }

    @FXML
    public void initialize() {
        setupSectionHoverTracker();
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        openPriceColumn.setCellValueFactory(new PropertyValueFactory<>("entryPrice"));
        closePriceColumn.setCellValueFactory(new PropertyValueFactory<>("closePrice"));
        profitLossColumn.setCellValueFactory(new PropertyValueFactory<>("profitLoss"));
        profitLossColumn.setCellFactory(col -> new TableCell<Trade, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                    return;
                }
                setText(String.format("%.2f €", item));
                setStyle(item >= 0 ? "-fx-text-fill: #28a745;-fx-font-weight: bold;" : "-fx-text-fill: #dc3545;-fx-font-weight: bold;");
            }
        });
        statusColumn.setCellValueFactory(cdf -> new SimpleStringProperty(String.valueOf(cdf.getValue().getStatus())));
        statusColumn.setCellFactory(col -> new TableCell<Trade, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                    return;
                }

                setText(item);
                Trade t = getTableRow().getItem();
                if (t == null) return;

                if ("OPEN".equalsIgnoreCase(item))
                    setStyle("-fx-text-fill: #0d6efd;-fx-font-weight: bold;");
                else
                    setStyle(t.getProfitLoss() >= 0 ? "-fx-text-fill: #28a745;-fx-font-weight: bold;" : "-fx-text-fill: #dc3545;-fx-font-weight: bold;");
            }
        });
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
            handleDateFilterChange();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleDateFilterChange() {
        if (filteredTrades == null) return;
        LocalDate fromSelected = fromDate.getValue();
        LocalDate toSelected = toDate.getValue();
        filteredTrades.setPredicate(trade -> {
            if (fromSelected == null && toSelected == null)
                return true;

            if (trade.getDateTime() == null || trade.getDateTime().trim().isEmpty())
                return false;

            LocalDate tradeDate;
            try {
                String datePart = trade.getDateTime().split(" ")[0];
                tradeDate = LocalDate.parse(datePart);
            } catch (Exception e) {
                return false;
            }

            boolean afterOrEqualFrom = (fromSelected == null) || !tradeDate.isBefore(fromSelected);
            boolean beforeOrEqualTo = (toSelected == null) || !tradeDate.isAfter(toSelected);
            return afterOrEqualFrom && beforeOrEqualTo;
        });
    }

    @FXML
    private void clearDatePickers(){
        fromDate.setValue(null);
        toDate.setValue(null);
        handleDateFilterChange();
    }

    private void refreshTradeTable() {
        tradesTableView.setItems(FXCollections.observableArrayList(activeJournal.getTrades()));
        tradesTableView.refresh();
    }
}
