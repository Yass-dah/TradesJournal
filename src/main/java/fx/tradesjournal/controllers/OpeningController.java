package fx.tradesjournal.controllers;

import fx.tradesjournal.FxApplication;
import fx.tradesjournal.model.Currency;
import fx.tradesjournal.model.Leverage;
import fx.tradesjournal.persistence.FilePersistenceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class OpeningController {
    private FxApplication app;

    @FXML
    private VBox root;

    @FXML
    private HBox titleBar;

    @FXML
    private Label headerTitleLabel;

    @FXML
    private Button newJournalBtn;

    @FXML
    private VBox selectBox;

    @FXML
    private VBox createBox;

    @FXML
    private ComboBox<String> journals;

    @FXML
    private TextField journalNameField;

    @FXML
    private TextField initialCapitalField;

    @FXML
    private ComboBox<Currency> currency;

    @FXML
    private ComboBox<Leverage> leverage;

    // Setters
    public void setApp(FxApplication app) {
        this.app = app;
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void initialize() {
        journals.getItems().addAll(FilePersistenceManager.getJournalFiles());
        currency.getItems().addAll(Currency.values());
        leverage.getItems().addAll(Leverage.values());
    }

    @FXML
    private void showCreateMode() {
        selectBox.setVisible(false);
        selectBox.setManaged(false);
        newJournalBtn.setVisible(false);

        root.getScene().getWindow().setHeight(328.0);
        createBox.setVisible(true);
        createBox.setManaged(true);

        headerTitleLabel.setText("Create New Journal");
    }

    @FXML
    private void showSelectMode() {
        journalNameField.clear();
        initialCapitalField.clear();

        root.getScene().getWindow().setHeight(232.0);
        createBox.setVisible(false);
        createBox.setManaged(false);

        selectBox.setVisible(true);
        selectBox.setManaged(true);
        newJournalBtn.setVisible(true);

        headerTitleLabel.setText("Welcome Back!");
    }

    @FXML
    private void createJournal(){
        try{
            app.journal();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void submitJournal(){
        try{
            app.journal();
        } catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
}