package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javafx.scene.paint.Color.RED;

public class StrongBrick extends Brick{
    private static List<WritableImage> frames;
    public StrongBrick(){
        super();
        this.setType("S");
        this.setHitPoints(5);
    }
    public StrongBrick(double x, double y){
        super(x, y,"S");
        this.setHitPoints(5);
    }
    @Override
    public void applyTexture(String path, Pane gamePane) {
        int index = 5 - hitPoints;
        Image img = null;
        if (index >= 0 && index < frames.size()) {
            img = frames.get(index);
            System.out.println("frame: " + index);
        }
        if (img != null) {
            ImagePattern pattern = new ImagePattern(img, 0, 0, 1, 1, true);
            setFill(pattern);
        } else {
            System.err.println("Cannot load " + type + " image, using default color");
            setFill(RED);
        }
        gamePane.getChildren().add(this);
    }

    @Override
    public void takeHit(Ball ball, Pane gamePane) {
        if (--hitPoints> 0) {
            applyTexture("", gamePane);
        }
    }

    static {
        Image spriteSheet = new Image(Objects.requireNonNull(StrongBrick.class.getResourceAsStream("/org/example/game/Image/StrongBrick.png")));
        frames = new ArrayList<>();
        int rows = 5;
        double frameWidth = spriteSheet.getWidth();
        double frameHeight = spriteSheet.getHeight() / rows;

        PixelReader reader = spriteSheet.getPixelReader();

        for (int x = 0; x < rows; x++) {
            WritableImage frame = new WritableImage(reader,
                    0, (int) (x * frameHeight),
                    (int) frameWidth, (int) frameHeight);
            frames.add(frame);
        }

        System.out.println("Đã lấy " + frames.size() + " phần ảnh từ sprite sheet.");
    }
}