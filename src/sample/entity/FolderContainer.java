package sample.entity;

import javafx.scene.image.Image;

public class FolderContainer extends Container{

    public FolderContainer() {
        icon = new Image(getClass().getResourceAsStream("/sample/view/img/folder.png"));
    }

    @Override
    public String getTypeContainer() {
        return "folder";
    }

    public FolderContainer(String path) {
        super(path);
        icon = new Image(getClass().getResourceAsStream("/sample/view/img/folder.png"));
    }
}
