package sample.task;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import sample.entity.Container;
import sample.service.ServiceFile;
import sample.service.ServiceFileImpl;

import java.io.File;
import java.util.List;

public class TaskGetChildren extends Task<Void> {
    private TreeItem<Container> selectedNode;
    private ProgressBar progressIndicator;
    private Node graphic;

    public TaskGetChildren(Object newValue) {
        System.out.println(newValue);
        selectedNode = (TreeItem<Container>) newValue;
        progressIndicator = new ProgressBar();
        progressIndicator.setPrefSize(16, 16);
        progressIndicator.setProgress(0);
        graphic = selectedNode.getGraphic();
        selectedNode.setGraphic(progressIndicator);
    }

    @Override
    protected void updateProgress(double workDone, double max) {
        progressIndicator.setProgress((double) workDone / (double) max);
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        selectedNode.setGraphic(graphic);
        selectedNode.setExpanded(true);
    }

    @Override
    protected void failed() {
        super.failed();
        selectedNode.setGraphic(graphic);
    }

    @Override
    protected Void call() throws Exception {
        for (int i = 0; i <= 3; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateProgress(i, 20);
        }
        Container selectedNodeValue = selectedNode.getValue();
        ObservableList<TreeItem<Container>> children = selectedNode.getChildren();
        String urlFile = selectedNodeValue.getUrl();
        File[] fileOfNode = new File(urlFile).listFiles();
        if (fileOfNode != null && fileOfNode.length == 0) {
            return null;
        }
        ServiceFile serviceFile = new ServiceFileImpl();
        List<Container> containerList = serviceFile.getContainers(fileOfNode);
        for (Container newContainer : containerList) {
            TreeItem<Container> item = new TreeItem<>(newContainer, new ImageView(newContainer.getIcon()));
            if (!serviceFile.hasNodeContains(children,item)) {
                children.add(item);
            }
        }
        return null;
    }

}
