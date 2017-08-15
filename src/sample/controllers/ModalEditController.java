package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
import sample.entity.Container;
import sample.entity.FileContainer;
import sample.entity.FolderContainer;
import sample.service.ServiceMenu;
import sample.service.ServiceMenuImpl;
import sample.service.ServiceUpdateUi;
import sample.service.ServiceUpdateUiImpl;
import sample.view.windows.ModalMessageWindow;

import java.io.File;

public class ModalEditController {
    private static final String MESSAGE = "Невозможно произвести операцию. Проверьте имя!";
    @FXML
    TextField textField;
    @FXML
    Button btnCancel;
    @FXML
    Button btnSave;
    private TreeItem<Container> selectedItem;
    private boolean editMode;
    private boolean createOrChangeDir;

    void setSelectedItem(TreeItem<Container> selectedItem) {
        this.selectedItem = selectedItem;

    }

    void setEditMode(boolean editMode) {
        this.editMode = editMode;
        if (editMode) {
            textField.setText(selectedItem.getValue().getName());
        }

    }

    @FXML
    public void save() {
        Container selectedContainer = selectedItem.getValue();
        String location;
        String name = File.separator + textField.getText();
        ServiceUpdateUi serviceUpdateUi = new ServiceUpdateUiImpl();
        boolean isFile = false;

        if (selectedContainer.isDirectory()) {
            location = selectedContainer.getUrl();

        } else {
            location = selectedItem.getParent().getValue().getUrl();
            isFile = true;
        }

        String fullOPath = location + name;
        ServiceMenu serviceMenu = new ServiceMenuImpl();
        if (createOrChangeDir) {
            if (editMode) {
                if (serviceMenu.editName(selectedContainer.getUrl(), selectedItem.getParent().getValue().getUrl() + name)) {
                    Container container = new FolderContainer(selectedItem.getParent().getValue().getUrl() + name);
                    serviceUpdateUi.changeItem(selectedItem, container);
                    createOrChangeDir = false;
                    cancel();

                } else errorMessage();

            } else if (serviceMenu.createFolder(fullOPath)) {
                Container container = new FolderContainer(fullOPath);
                if(isFile){
                    selectedItem = selectedItem.getParent();
                }
                serviceUpdateUi.addItem(selectedItem, container);
                createOrChangeDir = false;
                cancel();
            } else errorMessage();
        } else if (editMode) {
            if (serviceMenu.editName(selectedContainer.getUrl(), fullOPath)) {
                Container container = new FileContainer(fullOPath);
                serviceUpdateUi.changeItem(selectedItem, container);
                cancel();

            } else errorMessage();
        } else if (serviceMenu.createFile(fullOPath)) {
            Container container = new FileContainer(fullOPath);
            if(isFile){
                selectedItem = selectedItem.getParent();
            }
            serviceUpdateUi.addItem(selectedItem, container);
            cancel();
        } else {
            errorMessage();
        }
    }

    private void errorMessage() {
        ModalMessageWindow modalMessageWindow = new ModalMessageWindow();
        modalMessageWindow.init(textField.getScene().getWindow());
        ModalMessageController controller = modalMessageWindow.getLoader().getController();
        controller.label.setText(MESSAGE);
        modalMessageWindow.show();
    }

    public void cancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
    }

    public boolean isCreateOrChangeDir() {
        return createOrChangeDir;
    }

    void setCreateOrChangeDir(boolean createOrChangeDir) {
        this.createOrChangeDir = createOrChangeDir;
    }
}
