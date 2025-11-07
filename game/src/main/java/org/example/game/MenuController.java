package org.example.game;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private static final int BUTTON_W = 120;
    private static final int BUTTON_H = 50;

    @FXML
    private ImageView flower;

    @FXML
    private ImageView stars;

    @FXML
    private Text GameName = new Text("ARKANOID");

    @FXML
    private Button ExitButton = new Button();

    @FXML
    private Button StartButton = new Button();

    @FXML
    private Button MusicButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MusicManager.startBackgroundMusic();
        setGameName();
        startSpinningAnimation();
        moving();

        //tao anh cho cac nut
        setButton(ExitButton, "/org/example/game/Image/Exit.png", "/org/example/game/Image/Exit_on_hover.png");
        setButton(StartButton, "/org/example/game/Image/Start.png", "/org/example/game/Image/Start_on_hover.png");
        setButton(MusicButton, "/org/example/game/Image/Music.png", "/org/example/game/Image/Music_on_hover.png");
    }

    private void setButton(Button button, String normal, String onHover){
        Image Img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(normal)));
        Image OnHover = new Image(Objects.requireNonNull(getClass().getResourceAsStream(onHover)));

        ImageView View = new ImageView(Img);
        View.setFitWidth(BUTTON_W);
        View.setFitHeight(BUTTON_H);
        button.setLayoutX(800/2 - BUTTON_W/2);
        button.setGraphic(View);
        button.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        button.setOnMouseEntered(e -> View.setImage(OnHover));
        button.setOnMouseExited(e -> View.setImage(Img));
    }

    private void setGameName(){
        String fontPath = getClass().getResource("/org/example/game/Font/Black_Stuff_Bold.ttf").toExternalForm();
        Font customFont = Font.loadFont(fontPath, 170);
        if (customFont != null) {
            GameName.setFont(customFont);
        } else {
            System.err.println("Failed to load custom font: SVN-Black Stuff Bold.ttf");
        }
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            URL fxmlUrl = getClass().getResource("/org/example/game/arkanoid.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find arkanoid.fxml");
            }

            Parent gameRoot = FXMLLoader.load(fxmlUrl);
            Scene gameScene = new Scene(gameRoot);

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

    @FXML
    private void openMusicSettings(ActionEvent event) {
        try {
            MusicManager.startBackgroundMusic();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            URL fxmlUrl = getClass().getResource("/org/example/game/music_settings.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find music-settings.fxml");
            }

            Parent musicRoot = FXMLLoader.load(fxmlUrl);
            Scene musicScene = new Scene(musicRoot);

            stage.setScene(musicScene);
            stage.setTitle("Arkanoid Game - Music Settings");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading music settings scene: " + e.getMessage());
        }
    }
}