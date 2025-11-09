package org.example.game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MusicSettingsController implements Initializable {
    @FXML
    private Button Play;

    @FXML
    private Slider volumeSlider;

    @FXML
    private Text MusicSetting = new Text("MUSIC SETTINGS");

    @FXML
    private ToggleGroup trackToggleGroup;

    @FXML
    private ToggleButton defaultTrack;
    @FXML
    private ToggleButton track1Button;
    @FXML
    private ToggleButton track2Button;
    @FXML
    private ToggleButton track3Button;
    @FXML
    private ToggleButton track4Button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setMusicSetting();
        Image playImgage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/playMusic.png")));
        ImageView playView = new ImageView(playImgage);
        playView.setFitHeight(60);
        playView.setFitWidth(60);
        Play.setGraphic(playView);
        Play.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

        //tao anh cho cac track nhac
        SetTrackButton(defaultTrack, "/org/example/game/Image/defaultMusic.png");
        SetTrackButton(track1Button, "/org/example/game/Image/Track1.png");
        SetTrackButton(track2Button, "/org/example/game/Image/Track2.png");
        SetTrackButton(track3Button, "/org/example/game/Image/Track3.png");
        SetTrackButton(track4Button, "/org/example/game/Image/Track4.png");

        if (volumeSlider != null) {
            volumeSlider.setMin(0);
            volumeSlider.setMax(100);

            double currentVolumePercent = MusicManager.getVolume() * 100.0;
            volumeSlider.setValue(currentVolumePercent);

            volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                MusicManager.setVolume(newValue.doubleValue() / 100.0);
                System.out.println("Volume set to: " + newValue.intValue() + "%");
            });
        }

        if (trackToggleGroup != null) {
            track1Button.setUserData(MusicManager.TRACK_GURENGE);
            track2Button.setUserData(MusicManager.TRACK_MOITRAU);
            track3Button.setUserData(MusicManager.TRACK_PNL);
            track4Button.setUserData(MusicManager.TRACK_FIRE);

            String currentPath = MusicManager.getCurrentTrackPath();
            if(defaultTrack.getUserData().equals(currentPath))
                defaultTrack.setSelected(true);
            else if (track1Button.getUserData().equals(currentPath)) {
                track1Button.setSelected(true);
            } else if (track2Button.getUserData().equals(currentPath)) {
                track2Button.setSelected(true);
            } else if (track3Button.getUserData().equals(currentPath))
                track3Button.setSelected(true);
            else if(track4Button.getUserData().equals(currentPath))
                track4Button.setSelected(true);

            trackToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    String newTrackPath = (String) newValue.getUserData();
                    MusicManager.changeTrack(newTrackPath);
                    System.out.println("Changed track to: " + newTrackPath);
                }
            });
        }
    }

    private void SetTrackButton(ToggleButton button, String path){
        Image Img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        ImageView TrackView = new ImageView(Img);
        TrackView.setFitHeight(40);
        TrackView.setFitWidth(250);
        button.setGraphic(TrackView);
        button.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
    }

    private void setMusicSetting(){
        String fontPath = getClass().getResource("/org/example/game/Font/Black_Stuff_Bold.ttf").toExternalForm();
        Font customFont = Font.loadFont(fontPath, 80);
        if (customFont != null) {
            MusicSetting.setFont(customFont);
        } else {
            System.err.println("Failed to load custom font: SVN-Black Stuff Bold.ttf");
        }
    }

    @FXML
    private void toggleMusic() {
        MusicManager.toggleMusic();

        if (MusicManager.isPlaying()) {
            Image playImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/playMusic.png")));
            ImageView playView = new ImageView(playImg);
            playView.setFitHeight(60);
            playView.setFitWidth(60);
            Play.setGraphic(playView);
            Play.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        } else {
            Image playImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/pauseMusic.png")));
            ImageView playView = new ImageView(playImg);
            playView.setFitHeight(60);
            playView.setFitWidth(60);
            Play.setGraphic(playView);
            Play.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
        }
    }

    @FXML
    private void setDefaultTrack(){
        MusicManager.changeTrack(MusicManager.TRACK_DEFAULT);
    }

    @FXML
    private void setTrackMoiTrau(){
        MusicManager.changeTrack(MusicManager.TRACK_MOITRAU);
    }

    @FXML
    public void setTrackGure() {
        MusicManager.changeTrack(MusicManager.TRACK_GURENGE);
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
    public void setTrackPNL() {
        MusicManager.changeTrack(MusicManager.TRACK_PNL);
    }

    @FXML
    public void setTrackFire(){
        MusicManager.changeTrack(MusicManager.TRACK_FIRE);
    }
}