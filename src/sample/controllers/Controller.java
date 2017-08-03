package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import sample.ModalEditWindow;
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
        System.out.println("init");
        File[] rootFiles = File.listRoots();
        ServiceFile serviceFile = new ServiceFileImpl();
        System.out.println(rootFiles[0].getAbsolutePath());
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
                ServiceGuiImpl serviceGui = new ServiceGuiImpl();
                serviceGui.addItemsToNode(newValue);
            }
        });
    }


    public void initMenu() {
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
        sizeL.setText(String.valueOf(selectedNode.getValue().getSize()) + " " + BYTE);
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
                    //TODO обработать ошибку
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
                System.out.println("menuCreateFolder");
                modalEditWindow = new ModalEditWindow();
                modalEditWindow.init(treeView.getScene().getWindow());
                modalEditController = modalEditWindow.getLoader().getController();
                modalEditController.setSelectedItem(selectedItem);
                modalEditController.setCreateOrChangeDir(true);
                modalEditController.setEditMode(editMode);
                modalEditWindow.show();
                break;
            case "menuEditItem":
                System.out.println("menuEditItem");
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
