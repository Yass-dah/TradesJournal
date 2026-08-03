package fx.tradesjournal;

import fx.tradesjournal.controllers.OpeningController;
import fx.tradesjournal.controllers.TradesController;
import fx.tradesjournal.model.Journal;
import fx.tradesjournal.persistence.FilePersistenceManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class FxApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FxApplication.class.getResource("opening-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage = stage;
        OpeningController openingController = fxmlLoader.getController();
        openingController.setApp(this);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("TradesJournal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void journal(String journal) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FxApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        TradesController tradesController = fxmlLoader.getController();
        tradesController.setJournal(FilePersistenceManager.loadJournal(journal));
        primaryStage.setTitle("TradesJournal");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}