package sample.view.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ModalEditWindow {
    private Parent root;
    private FXMLLoader loader;
    private Stage stage;

    public void init(Window p) {
        stage = new Stage();
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("edit.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(p.getScene().getWindow());
            scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if(event.getCode()== KeyCode.ESCAPE)
                    stage.close();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void show(){
        stage.show();
    }

    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(FXMLLoader loader) {
        this.loader = loader;
    }

}
