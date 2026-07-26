module fx.tradesjournal {
    requires javafx.controls;
    requires javafx.fxml;


    opens fx.tradesjournal to javafx.fxml;
    exports fx.tradesjournal;
    exports fx.tradesjournal.controllers;
    opens fx.tradesjournal.controllers to javafx.fxml;
}