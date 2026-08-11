module fx.tradesjournal {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens fx.tradesjournal to javafx.fxml;
    opens fx.tradesjournal.model to com.google.gson, javafx.base;

    exports fx.tradesjournal;
    exports fx.tradesjournal.controllers;
    opens fx.tradesjournal.controllers to javafx.fxml;
}