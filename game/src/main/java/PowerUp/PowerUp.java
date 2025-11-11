package PowerUp;

import Ball.Ball;
import Paddle.Paddle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public abstract class PowerUp extends Rectangle {
    protected String type;
    protected double duration;
    protected double speed = 5;

    public PowerUp(String type, double duration, double x, double y, String imagePath) {
        super(x, y, 60, 60);
        this.type = type;
        this.duration = duration;
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load powerUp image: " + e.getMessage());
            setFill(javafx.scene.paint.Color.BLUE); // Use fill instead of style
        }
    }

    public String getType() {
        return type;
    }

    public double getDuration() {
        return duration;
    }

    public void move() {
        setY(getY() + speed);
    }

    public abstract void applyEffect(Paddle paddle, Ball ball);

    public abstract void removeEffect(Paddle paddle, Ball ball);
}