package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import sample.view.windows.ModalEditWindow;
import sample.entity.Container;
import sample.entity.DriveContainer;
import sample.service.*;

import java.io.File;
import java.util.Date;
import java.util.List;

public class Controller {
    private final String BYTE = "byte";
    private final String Label = "root";

    @FXML
    Label sizeL;
    @FXML
    Label pathL;
    @FXML
    Label dateL;
    @FXML
    TreeView treeView;
    @FXML
    MenuItem menuCreateFolder;
    @FXML
    MenuItem menuCreateFile;
    @FXML
    MenuItem menuDeleteItem;
    @FXML
    MenuItem menuEditItem;

    @FXML
    void initialize() {
        File[] rootFiles = File.listRoots();
        ServiceFile serviceFile = new ServiceFileImpl();
        List<Container> fileContainers = serviceFile.getDisk(rootFiles);
        initTreeViewRoot(fileContainers);
        initMenu();
    }

    private void initTreeViewRoot(List<Container> fileContainers) {
        DriveContainer rootDrive = new DriveContainer();
        rootDrive.setName("root");
        rootDrive.setDate(new Date());

        TreeItem<Container> root = new TreeItem(rootDrive, new ImageView(rootDrive.getIcon()));

        for (Container container : fileContainers) {
            DriveContainer driveContainer = (DriveContainer) container;
            driveContainer.setName(driveContainer.getUrl());
            root.getChildren().add(new TreeItem<>(driveContainer, new ImageView(driveContainer.getIcon())));
        }
        root.setExpanded(true);
        treeView.setRoot(root);

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<Container> selectedNode = (TreeItem<Container>) newValue;
            initPropertyContainer(selectedNode);
            if (selectedNode.getValue().isDirectory()) {
                ServiceUpdateUi serviceGui = new ServiceUpdateUiImpl();
                serviceGui.addItemsToNode(newValue);
            }
        });
    }


    private void initMenu() {
        menuCreateFolder.setOnAction(event -> {
            menuAction(event);
        });
        menuCreateFile.setOnAction(event -> {

            menuAction(event);
        });
        menuDeleteItem.setOnAction(event -> {
            menuAction(event);
        });
        menuEditItem.setOnAction(event -> {
            menuAction(event);
        });
    }

    private void initPropertyContainer(TreeItem<Container> selectedNode) {
        if(selectedNode.getValue().isDirectory()){
            sizeL.setText("DIR");
        } else sizeL.setText(String.valueOf(selectedNode.getValue().getSize()) + " " + BYTE);
        pathL.setText(selectedNode.getValue().getUrl());
        dateL.setText(String.valueOf(selectedNode.getValue().getDate()));
    }

    private void menuAction(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        TreeItem<Container> selectedItem = (TreeItem<Container>) treeView.getSelectionModel().getSelectedItem();
        ModalEditWindow modalEditWindow;
        ModalEditController modalEditController;
        boolean editMode = false;
        switch (menuItem.getId()) {
            case "menuDeleteItem":
                ServiceMenu serviceMenu = new ServiceMenuImpl();
                if(serviceMenu.delete(selectedItem.getValue().getUrl())){
                    ServiceUpdateUi serviceUpdateUi = new ServiceUpdateUiImpl();
                    serviceUpdateUi.deleteItem(selectedItem);
                }
                break;
            case "menuCreateFile":
                modalEditWindow = new ModalEditWindow();
                modalEditWindow.init(treeView.getScene().getWindow());
                modalEditController = modalEditWindow.getLoader().getController();
                modalEditController.setSelectedItem(selectedItem);
                modalEditController.setCreateOrChangeDir(false);
                modalEditController.setEditMode(editMode);
                modalEditWindow.show();
                break;
            case "menuCreateFolder":
                modalEditWindow = new ModalEditWindow();
                modalEditWindow.init(treeView.getScene().getWindow());
                modalEditController = modalEditWindow.getLoader().getController();
                modalEditController.setSelectedItem(selectedItem);
                modalEditController.setCreateOrChangeDir(true);
                modalEditController.setEditMode(editMode);
                modalEditWindow.show();
                break;
            case "menuEditItem":
                editMode = true;
                modalEditWindow = new ModalEditWindow();
                modalEditWindow.init(treeView.getScene().getWindow());
                modalEditController = modalEditWindow.getLoader().getController();
                modalEditController.setSelectedItem(selectedItem);
                if(selectedItem.getValue().isDirectory()) modalEditController.setCreateOrChangeDir(true);
                modalEditController.setEditMode(editMode);
                modalEditWindow.show();
                break;
        }

    }

}
