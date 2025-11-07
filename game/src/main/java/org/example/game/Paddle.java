package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Paddle extends Rectangle{
    private int dx;
    private int speed;
    public boolean leftPressed = false;
    public boolean rightPressed = false;
    private boolean mouseControl = false;
    private double targetX;

    public Paddle() {
        super(150, 23);
        setX(ArkanoidGame.WIDTH - getWidth());
        setY(ArkanoidGame.HEIGHT - 40);

        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Paddle.png")));
            if (img.isError()) {
                throw new Exception("Image loading error");
            }
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load paddle image: " + e.getMessage());
            setFill(javafx.scene.paint.Color.BLUE);
        }
    }

    public void move(){
        if (mouseControl) {
            double currentX = getX();
            double newX = currentX + (targetX - currentX) * 0.3;
            setX(newX);
        } else {
            if (leftPressed && getX() > ArkanoidGame.LEFT_BORDER) {
                setX(getX() - 5);
            }
            if (rightPressed && getX() < ArkanoidGame.WIDTH + ArkanoidGame.LEFT_BORDER - getWidth()) {
                setX(getX() + 5);
            }
        }
    }
    public void setMouseTarget(double mouseX) {
        this.mouseControl = true;
        double paddleHalfWidth = getWidth() / 2;
        double leftBoundary = ArkanoidGame.LEFT_BORDER + paddleHalfWidth;
        double rightBoundary = ArkanoidGame.WIDTH - paddleHalfWidth + 20;

        this.targetX = Math.max(leftBoundary, Math.min(mouseX, rightBoundary)) - paddleHalfWidth;
    }

    public void setKeyboardControl() {
        this.mouseControl = false;
    }
}