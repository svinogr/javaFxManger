package sample.service;

public interface ServiceMenu {
    boolean createFolder(String path);
    boolean createFile(String path);
    boolean delete(String path);
    boolean editName(String newName, String oldName);
}
