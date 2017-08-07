package sample.entity;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Date;

public abstract class Container  implements Containerable {
    protected Image icon;
    private String name;
    private String url;
    private long size;
    private Date date;

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }

    private boolean directory;

    public Container() {
    }

    public Container(String path) {
        File file = new File(path);
            this.name = file.getName();
            this.url = file.getAbsolutePath();
            //this.url = file.getPath();
            this.size = file.length();
            this.date = new Date(file.lastModified());
            this.directory = file.isDirectory();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Container that = (Container) o;

        return url.equals(that.url);

    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public String toString() {
        return  name;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    @Override
    public String getTypeContainer() {
        return null;
    }

}
