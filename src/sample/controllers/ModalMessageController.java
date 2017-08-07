package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ModalMessageController {
    @FXML
    Button btnOk;
    @FXML
    Label label;
    @FXML
    void cancel(){
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }
    void setLabel(String message){
        label.setText(message);
    }


}
