package eu.mrndesign.matned;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainScreen.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Blob painting system");
        primaryStage.setScene(new Scene(root, 1165, 860));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
