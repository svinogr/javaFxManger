package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.view.windows.MainWindow;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MainWindow mainWindow = new MainWindow();
        mainWindow.init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
