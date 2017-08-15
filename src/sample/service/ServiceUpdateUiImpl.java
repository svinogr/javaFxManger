package sample.service;

import javafx.animation.KeyFrame;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import sample.entity.Container;
import sample.task.TaskGetChildren;

public class ServiceUpdateUiImpl implements ServiceUpdateUi {
    @Override
    public void deleteItem(TreeItem<Container> treeItem) {
        TimelineBuilder.create().keyFrames(
                new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        treeItem.getParent().getChildren().remove(treeItem);
                    }
                })).build().play();
    }

    @Override
    public void addItem(TreeItem<Container> treeItem, Container container) {
        TreeItem<Container> newItem = new TreeItem<>(container, new ImageView(container.getIcon()));
        TimelineBuilder.create().keyFrames(
                new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        treeItem.getChildren().add(newItem);
                        treeItem.setExpanded(true);
                    }
                })).build().play();
    }

    @Override
    public void changeItem(TreeItem<Container> selected, Container container) {
        TreeItem<Container> parent = selected.getParent();
        int indexOf = parent.getChildren().indexOf(selected);
        TreeItem<Container> containerTreeItem = new TreeItem<>(container, new ImageView(container.getIcon()));
        TimelineBuilder.create().keyFrames(
                new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                        parent.getChildren().set(indexOf,containerTreeItem);
                    }
                })).build().play();

    }

    @Override
    public void addItemsToNode(Object node) {
            TaskGetChildren task  = new TaskGetChildren(node);
            Thread thread = new Thread(task);
            thread.start();
        }

}
