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
    private ImageView face;

    @FXML
    private Button MusicButton;
    private ImageView Sound;
    private Image soundOnImage;
    private Image soundOffImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGameName();
        startSpinningAnimation();
        moving();

        //tao anh cho nut EXIT
        Image Exit = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Exit.png")));
        Image ExitOnHover = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Exit_on_hover.png")));

        ImageView ExitView = new ImageView(Exit);
        ExitView.setFitWidth(BUTTON_W);
        ExitView.setFitHeight(BUTTON_H);
        ExitButton.setLayoutX(800/2 - BUTTON_W/2);
        ExitButton.setGraphic(ExitView);
        ExitButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        ExitButton.setOnMouseEntered(e -> ExitView.setImage(ExitOnHover));
        ExitButton.setOnMouseExited(e -> ExitView.setImage(Exit));

        //tạo ảnh cho nút start
        Image Start = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Start.png")));
        Image StartOnHover = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Start_on_hover.png")));

        ImageView StartView = new ImageView(Start);
        StartView.setFitWidth(BUTTON_W);
        StartView.setFitHeight(BUTTON_H);
        StartButton.setLayoutX(800/2 - BUTTON_W/2);
        StartButton.setGraphic(StartView);
        StartButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        StartButton.setOnMouseEntered(e -> StartView.setImage(StartOnHover));
        StartButton.setOnMouseExited(e -> StartView.setImage(Start));

        //tạo âm thanh
        soundOnImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Music.png")));
        soundOffImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Music_on_hover.png")));

        Sound = new ImageView(soundOffImage);
        Sound.setFitWidth(BUTTON_W);
        Sound.setFitHeight(BUTTON_H);

        Sound.setLayoutX(800/2 - BUTTON_W/2);

        MusicButton.setGraphic(Sound);

        MusicButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
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
    private void toggleMusic() {
        MusicManager.toggleMusic();

        if (MusicManager.isPlaying()) {
            Sound.setImage(soundOnImage);
        } else {
            Sound.setImage(soundOffImage);
        }
    }
}