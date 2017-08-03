package sample.entity;

import javafx.scene.image.Image;

public class FileContainer extends Container {

    public FileContainer() {
        this.icon =new Image(getClass().getResourceAsStream("/sample/view/img/file.png"));
    }

    public FileContainer(String path) {
        super(path);
        this.icon =new Image(getClass().getResourceAsStream("/sample/view/img/file.png"));
    }

    @Override
    public String getTypeContainer() {
        return "file";
    }
}
