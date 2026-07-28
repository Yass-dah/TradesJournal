package fx.tradesjournal.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TradesController {
    @FXML
    private HBox titleBar;

    @FXML
    private void handleClose(){
        Stage stage = (Stage)titleBar.getScene().getWindow();
        stage.close();
    }
}
