package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Paddle extends Rectangle {
    private int keyboardSpeed = 5;
    public boolean leftPressed = false;
    public boolean rightPressed = false;

    private double mouseTargetX;
    private double keyboardTargetX;

    private boolean keyboardControlled = false;

    public Paddle() {
        super(150, 23);
        setX(ArkanoidGame.WIDTH / 2 - getWidth() / 2 + ArkanoidGame.LEFT_BORDER);
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

        double initialCenterX = getX() + getWidth() / 2;
        this.mouseTargetX = initialCenterX;
        this.keyboardTargetX = initialCenterX;
    }

    public void move() {
        double currentCenterX = getX() + getWidth() / 2;
        double finalTargetX;

        boolean usingKeyboard = leftPressed || rightPressed;

        if (usingKeyboard) {
            if (!keyboardControlled) {
                keyboardTargetX = currentCenterX;
                keyboardControlled = true;
            }
            if (leftPressed) {
                keyboardTargetX -= keyboardSpeed;
            } else if (rightPressed) {
                keyboardTargetX += keyboardSpeed;
            }
            finalTargetX = keyboardTargetX;
        } else {
            if (keyboardControlled) {
                mouseTargetX = currentCenterX;
                keyboardControlled = false; // Đặt cờ là không điều khiển bằng bàn phím nữa
            }
            finalTargetX = mouseTargetX; // Paddle đi theo target của chuột
        }

        double paddleHalfWidth = getWidth() / 2;
        double minAllowedCenterX = ArkanoidGame.LEFT_BORDER + paddleHalfWidth;
        double maxAllowedCenterX = ArkanoidGame.WIDTH + ArkanoidGame.LEFT_BORDER - paddleHalfWidth;

        keyboardTargetX = Math.max(minAllowedCenterX, Math.min(keyboardTargetX, maxAllowedCenterX));
        mouseTargetX = Math.max(minAllowedCenterX, Math.min(mouseTargetX, maxAllowedCenterX));
        finalTargetX = Math.max(minAllowedCenterX, Math.min(finalTargetX, maxAllowedCenterX));

        double newX = getX() + (finalTargetX - currentCenterX) * 0.3;

        double minX = ArkanoidGame.LEFT_BORDER;
        double maxX = ArkanoidGame.WIDTH + ArkanoidGame.LEFT_BORDER - getWidth();

        setX(Math.max(minX, Math.min(newX, maxX)));
    }

    public void setMouseTargetX(double mouseX) {
        double paddleHalfWidth = getWidth() / 2;
        double leftBoundary = ArkanoidGame.LEFT_BORDER + paddleHalfWidth;
        double rightBoundary = ArkanoidGame.WIDTH - paddleHalfWidth + ArkanoidGame.LEFT_BORDER;

        this.mouseTargetX = Math.max(leftBoundary, Math.min(mouseX, rightBoundary));
    }

    public void resetControlStates() {
        leftPressed = false;
        rightPressed = false;
        keyboardControlled = false;

        double initialCenterX = ArkanoidGame.WIDTH / 2 - getWidth() / 2 + ArkanoidGame.LEFT_BORDER + getWidth()/2;
        this.mouseTargetX = initialCenterX;
        this.keyboardTargetX = initialCenterX;
    }
}