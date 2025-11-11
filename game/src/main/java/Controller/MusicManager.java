package Controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class MusicManager {
    private static MediaPlayer mediaPlayer;
    private static boolean isPlaying = true;

    public static final String TRACK_DEFAULT = "/org/example/game/Music/Default.mp3";
    public static final String TRACK_GURENGE = "/org/example/game/Music/Gurenge.mp3";
    public static final String TRACK_TABC = "/org/example/game/Music/TinhAnhBanChieu.mp3";
    public static final String TRACK_PNL = "/org/example/game/Music/PeaceNLove.mp3";
    public static final String TRACK_FIRE = "/org/example/game/Music/Fire.mp3";

    private static String currentTrackPath = TRACK_DEFAULT;

    public static void toggleMusic() {
        if(mediaPlayer == null){
            startBackgroundMusic(currentTrackPath);
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

    public static void startBackgroundMusic(String trackPath){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }

        currentTrackPath = trackPath;

        String musicPath = Objects.requireNonNull(
                MusicManager.class.getResource(trackPath)
        ).toExternalForm();

        Media media = new Media(musicPath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(getVolume());

        mediaPlayer.setOnReady(() -> {
            if (isPlaying) {
                mediaPlayer.play();
            }
        });
    }

    public static void startBackgroundMusic() {
        if (mediaPlayer == null) {
            startBackgroundMusic(currentTrackPath);
        }
    }

    public static void changeTrack(String newTrackPath) {
        if (!newTrackPath.equals(currentTrackPath)) {
            startBackgroundMusic(newTrackPath);
        }
    }

    public static boolean isPlaying() {
        return isPlaying;
    }

    public static double getVolume() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVolume();
        }
        return 0.5;
    }

    public static String getCurrentTrackPath() {
        return currentTrackPath;
    }

    public static void setVolume(double volume) {
        if (mediaPlayer != null) {
            if (volume >= 0.0 && volume <= 1.0) {
                mediaPlayer.setVolume(volume);
            }
        }
    }
}