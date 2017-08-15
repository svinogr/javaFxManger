package sample.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ServiceMenuImpl implements ServiceMenu {
    @Override
    public boolean createFolder(String path) {
        File file = new File(path);
        return file.mkdir();

    }

    @Override
    public boolean createFile(String path) {
        File file = new File(path);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String path) {
        File file = new File(path);
        File[] files = file.listFiles();

        if (files != null) {
            for (File file1 : files) {
                delete(file1.getAbsolutePath());
            }
        }
        return file.delete();

    }

    @Override
    public boolean editName(String oldName, String newName) {
        File file = new File(oldName);
        if (file.exists()) {
            return file.renameTo(new File(newName));

        }
        return false;
    }
}
