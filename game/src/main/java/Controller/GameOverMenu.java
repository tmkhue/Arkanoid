package Controller;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private Label currentScoreLabel;
    @FXML
    private Label highScore1Label;
    @FXML
    private Label highScore2Label;
    @FXML
    private Label highScore3Label;
    @FXML
    private Label highScore4Label;
    @FXML
    private Label highScore5Label;

    private static final String HIGH_SCORES_FILE = "highscores.txt";
    private static final int MAX_HIGH_SCORES_TO_SAVE = 10;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int currentScore = ArkanoidGame.getInstance().getScore();
        saveAndLoadHighScores(currentScore);

        setButton(exitButton, "/org/example/game/Image/gameExit.png", "/org/example/game/Image/gameExitOnHover.png", 190, 70);
        setButton(restartGame, "/org/example/game/Image/restart.png", "/org/example/game/Image/restartOnHover.png", 190, 70);
        setButton(backToMenu, "/org/example/game/Image/backToMenu.png", "/org/example/game/Image/backToMenuHover.png", 80, 80);
    }

    private void saveAndLoadHighScores(int currentScore) {
        List<Integer> highScores = loadHighScores();
        highScores.add(currentScore);
        Collections.sort(highScores, Collections.reverseOrder());

        if (highScores.size() > MAX_HIGH_SCORES_TO_SAVE) {
            highScores = highScores.subList(0, MAX_HIGH_SCORES_TO_SAVE);
        }

        saveHighScores(highScores);
        updateScoreLabels(currentScore, highScores);
    }

    private List<Integer> loadHighScores() {
        List<Integer> scores = new ArrayList<>();
        File file = new File(HIGH_SCORES_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        scores.add(Integer.parseInt(line.trim()));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid score format in highscores.txt: " + line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading high scores: " + e.getMessage());
            }
        }
        return scores;
    }

    private void saveHighScores(List<Integer> scores) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORES_FILE))) {
            for (int score : scores) {
                writer.write(String.valueOf(score));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }

    private void updateScoreLabels(int currentScore, List<Integer> highScores) {
        Font gameFont = Font.loadFont(getClass().getResourceAsStream("/org/example/game/Font/Black_Stuff_Bold.ttf"), 35);
        currentScoreLabel.setFont(gameFont);
        currentScoreLabel.setTextFill(Color.rgb(255, 192, 203)); // Màu hồng nhạt
        currentScoreLabel.setStyle("-fx-text-fill: #FFC0CB; -fx-stroke: #93278F; -fx-stroke-width: 1.5;"); // Fill hồng, stroke tím đậm
        currentScoreLabel.setText(String.valueOf(currentScore));
        Label[] highScoresLabels = {highScore1Label, highScore2Label, highScore3Label, highScore4Label, highScore5Label};

        for (int i = 0; i < highScoresLabels.length; i++) {
            Label label = highScoresLabels[i];
            if (label != null) {
                label.setFont(gameFont);
                label.setTextFill(Color.rgb(147, 39, 143)); // Màu tím gốc
                if (highScores.size() > i) {
                    label.setText(String.valueOf(highScores.get(i)));
                } else {
                    label.setText("---");
                }
            }
        }
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