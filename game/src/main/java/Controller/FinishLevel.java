package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class FinishLevel implements Initializable {
    String[] done = {"Yay! You finished this level!", "Giỏi quá!", "Tuyệt vời!", "Congratulation!"};

    @FXML
    private Text finish;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setFont(finish);
    }

    private void setFont(Text text){
        Random random = new Random();
        int bound = 3;
        int randomInt = random.nextInt(bound);
        text.setText(done[randomInt]);
        String fontPath = getClass().getResource("/org/example/game/Font/Black_Stuff_Bold.ttf").toExternalForm();
        Font customFont = Font.loadFont(fontPath, 50);
        if (customFont != null) {
            text.setFont(customFont);
        } else {
            System.err.println("Failed to load custom font: Black_Stuff_Bold.ttf");
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
