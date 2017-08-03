package sample.service;

import javafx.scene.control.TreeItem;
import sample.entity.Container;

public interface ServiceUpdateUi {
    void deleteItem(TreeItem<Container> treeItem);
    void addItem(TreeItem<Container> treeItem, Container container);
    void changeItem(TreeItem<Container> selected, Container container);
}
