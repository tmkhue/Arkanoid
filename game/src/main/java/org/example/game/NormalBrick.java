package org.example.game;


//import gameObject.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class NormalBrick extends Brick {
    public NormalBrick(){
        super();
        this.setHitPoints(1);
    }
    public NormalBrick(double x, double y){
        super(x, y);
        this.setHitPoints(1);
    }

    @Override
    public void applyTexture() {
        try {
            Image img = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/org/example/game/Image/normalBrick.png")));
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("Cannot load normal brick image");
            setFill(javafx.scene.paint.Color.LAVENDER);
        }
    }
}