package sample.service;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import sample.entity.Container;

import java.io.File;
import java.util.List;

public interface ServiceFile  {
    List<Container> getContainers(File[] files);
    List<Container> getDisk(File[] files);
    boolean hasNodeContains(ObservableList<TreeItem<Container>> children, TreeItem<Container> item);
}
