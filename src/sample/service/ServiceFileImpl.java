package sample.service;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import sample.entity.Container;
import sample.entity.DriveContainer;
import sample.entity.FileContainer;
import sample.entity.FolderContainer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceFileImpl implements ServiceFile {
    public ServiceFileImpl() {
    }

    @Override
    public List<Container> getContainers(File[] files) {
        List<Container> containers = null;
        System.out.println(files.length+" "+ files);
        if (files.length != 0) {
            containers = new ArrayList<>();
            for (File file : files) {
                if (file.exists()) {
                    Container container;
                    if (file.isDirectory()) {
                        container = new FolderContainer();
                    } else container = new FileContainer();
                    getContainer(container, file);
                    containers.add(container);
                }
            }
        }
        return containers;
    }

    @Override
    public List<Container> getDisk(File[] files) {
        List<Container> containers = null;
        if (files.length != 0) {
            containers = new ArrayList<>();
            for (File file : files) {
                if (file.exists()) {
                    Container container = new DriveContainer();
                    getContainer(container, file);
                    containers.add(container);
                }

            }
        }

        return containers;
    }

    private void getContainer(Container container, File file) {
        container.setName(file.getName());
        container.setUrl(file.getAbsolutePath());
        //container.setUrl(file.getPath());
        container.setSize(file.length());
        container.setDate(new Date(file.lastModified()));
        container.setDirectory(file.isDirectory());
    }

    @Override
    public boolean hasNodeContains(ObservableList<TreeItem<Container>> children, TreeItem<Container> item) {
        if (item == null) {
            return true;
        }

        if (children != null) {
            for (TreeItem<Container> childrenItem : children) {
                if (childrenItem.getValue().equals(item.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

}
