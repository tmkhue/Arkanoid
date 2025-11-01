package org.example.game;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private ImageView flower;

    @FXML
    private ImageView stars;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startSpinningAnimation();
        moving();
    }

    private void startSpinningAnimation() {
        if (flower != null) {
            RotateTransition rt = new RotateTransition(Duration.seconds(10), flower);
            rt.setByAngle(360);
            rt.setCycleCount(RotateTransition.INDEFINITE);
            rt.setAutoReverse(false);

            rt.play();
        } else {
            System.err.println("Error: flower was not loaded from FXML.");
        }
    }

    private void moving(){
        if(stars != null) {
            TranslateTransition tt = new TranslateTransition(Duration.seconds(3), stars);
            tt.setByY(20);
            tt.setCycleCount(TranslateTransition.INDEFINITE);
            tt.setAutoReverse(true);

            tt.play();
        } else {
            System.err.println("Error: No stars detected");
        }
    }

    @FXML
    private void startGame(ActionEvent event) {
        try {
            // Get the current Stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the Arkanoid game FXML
            URL fxmlUrl = getClass().getResource("/org/example/game/arkanoid.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find arkanoid.fxml");
            }

            Parent gameRoot = FXMLLoader.load(fxmlUrl);
            Scene gameScene = new Scene(gameRoot);

            // Switch the scene on the current stage
            stage.setScene(gameScene);
            stage.setTitle("Arkanoid Game - In Progress");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading game scene: " + e.getMessage());
        }
    }

    @FXML
    private void exit() {
        Platform.exit();
        System.exit(0);
    }
}