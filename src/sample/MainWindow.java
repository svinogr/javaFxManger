package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow {
    private static final String title="File Manger";
    private static final String fxml = "sample.fxml";

    public void init(Stage stage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            stage.setTitle(title);
            stage.setWidth(1200);
            stage.setHeight(800);
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {stage.setIconified(true);System.exit(0);});
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
