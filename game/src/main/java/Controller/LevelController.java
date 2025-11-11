package Controller;

import Brick.Levels;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LevelController implements Initializable {
    @FXML
    private GridPane gridPane;

    Boolean[] unlocked = new Boolean[20];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < 20; i++){
            Button b = (Button) gridPane.getChildren().get(i);
            setFont(b);

            if(i + 1 <= Levels.levelUnlocked) {
                setUnlockedButton(b);
                unlocked[i] = true;
                b.setOnAction(this::startLevel);
            } else {
                setLockedButton(b);
                unlocked[i] = false;
                b.setOnAction(this::showLockedMessage);
            }
        }
    }

    private void setFont(Button button){
        String fontPath = getClass().getResource("/org/example/game/Font/Black_Stuff_Bold.ttf").toExternalForm();
        Font customFont = Font.loadFont(fontPath, 28);
        if (customFont != null) {
            button.setFont(customFont);
        } else {
            System.err.println("Failed to load custom font: Black_Stuff_Bold.ttf");
        }
    }

    private void setUnlockedButton(Button button){
        button.setTextFill(Color.DARKMAGENTA);
        button.setStyle("-fx-background-color: lavender; -fx-border-color: darkmagenta; -fx-border-width: 3; -fx-cursor: hand;");
    }

    private void setLockedButton(Button button){
        button.setTextFill(Color.GRAY);
        button.setDisable(true);
        button.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #b0b0b0; -fx-border-width: 3; -fx-opacity: 0.6; -fx-cursor: default;");
    }

    private void showLockedMessage(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        System.out.println("Level " + clickedButton.getText().replaceAll("[^0-9]", "") + " chưa được mở khóa!");
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

    @FXML
    private void startLevel(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String levelText = clickedButton.getText();

        try {
            String numberString = levelText.replaceAll("[^0-9]", "");
            int levelNumber = Integer.parseInt(numberString);
            Levels.selectLevel(levelNumber);

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

        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Không thể trích xuất số level từ văn bản: " + levelText);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading game scene: " + e.getMessage());
        }
    }
}