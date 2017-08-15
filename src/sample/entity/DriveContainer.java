package sample.entity;

import javafx.scene.image.Image;

public class DriveContainer extends Container {
    public DriveContainer() {
        icon = new Image(getClass().getResourceAsStream("/sample/view/img/harddrive.png"));
    }

    @Override
    public String getTypeContainer() {
        return "drive";
    }
}
