package sample.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ModalWindow {
    private final String fxml="edit.fxml";
    String targetUrl;
    Node owner;

    public ModalWindow(Node owner) {
        this.owner = owner;
    }

    public void init(){
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("edit.fxml"));
            stage.setTitle("Hello World");
            stage.setScene(new Scene(root, 700, 700));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
