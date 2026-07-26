module fx.tradesjournal {
    requires javafx.controls;
    requires javafx.fxml;


    opens fx.tradesjournal to javafx.fxml;
    exports fx.tradesjournal;
}