package org.example.game;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameOverMenu implements Initializable {
    @FXML
    private Button restartGame;
    @FXML
    private Button exitButton;
    @FXML
    private Button backToMenu;
    @FXML
    private Label HighScore;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupScoreText(ArkanoidGame.getInstance());
        setButton(exitButton, "/org/example/game/Image/gameExit.png", "/org/example/game/Image/gameExitOnHover.png", 190, 70);
        setButton(restartGame, "/org/example/game/Image/restart.png", "/org/example/game/Image/restartOnHover.png", 190, 70);
        setButton(backToMenu, "/org/example/game/Image/backToMenu.png", "/org/example/game/Image/backToMenuHover.png", 80, 80);
    }

    @FXML
    private void setupScoreText(ArkanoidGame game) {
        Font gameFont = Font.loadFont(getClass().getResourceAsStream("/org/example/game/Font/Black_Stuff_Bold.ttf"), 50);
        HighScore.setFont(gameFont);
        HighScore.setTextFill(Color.rgb(147,39,143));
        HighScore.setLayoutX(370);
        HighScore.setText(String.valueOf(game.getScore()));
    }

    private void setButton(Button button, String normal, String onHover, double w, double h) {
        Image Img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(normal)));
        Image OnHover = new Image(Objects.requireNonNull(getClass().getResourceAsStream(onHover)));

        ImageView View = new ImageView(Img);
        View.setFitWidth(w);
        View.setFitHeight(h);
        button.setGraphic(View);
        button.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        button.setOnMouseEntered(e -> View.setImage(OnHover));
        button.setOnMouseExited(e -> View.setImage(Img));
    }

    @FXML
    private void exit() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void restart(ActionEvent event) {
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
    private void backToMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            URL fxmlUrl = getClass().getResource("/org/example/game/Menu.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot find Menu.fxml");
            }

            Parent menuRoot = FXMLLoader.load(fxmlUrl);
            Scene menuScene = new Scene(menuRoot);

            stage.setScene(menuScene);
            stage.setTitle("Arkanoid Game - Menu");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading menu scene: " + e.getMessage());
        }
    }
}
