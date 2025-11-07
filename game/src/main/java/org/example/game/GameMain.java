package org.example.game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class GameMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlUrl = getClass().getResource("/org/example/game/Menu.fxml");
        if (fxmlUrl == null) {
            throw new Exception("Cannot find mainMenu.fxml in the resources folder.");
        }

        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Arkanoid - Main Menu"); // Set initial title
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}