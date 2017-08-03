package sample.service;

import sample.task.TaskGetChildren;

public class ServiceGuiImpl implements ServiceGui {

    @Override
    public void addItemsToNode(Object node) {
        TaskGetChildren task  = new TaskGetChildren(node);
        Thread thread = new Thread(task);
        thread.start();
    }
}
