package org.example.game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.Objects;

public class MusicManager {
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = true;

    public static void toggleMusic() {
        if(mediaPlayer == null){
            startBackgroundMusic();
            return;
        }
        if (isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        } else {
            mediaPlayer.play();
            isPlaying = true;
        }
    }
    public static void startBackgroundMusic(){
        if (mediaPlayer == null) {
            String musicPath = Objects.requireNonNull(
                    MusicManager.class.getResource("/org/example/game/Music/Gurenge.mp3")
            ).toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5);

            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
                isPlaying = true;
            });
        }
    }

    public static boolean isPlaying() {
        return isPlaying;
    }
}
