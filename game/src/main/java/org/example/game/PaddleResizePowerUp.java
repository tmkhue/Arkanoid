package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Objects;

public class PaddleResizePowerUp extends PowerUp {
    private PaddleResizer resizer = new DefaultPaddleResizer();
    private static final double RESIZE_AMOUNT = 10;
    private double currentResizeAmount = 0;

    public PaddleResizePowerUp(double x, double y, double duration) {
        super("Paddle Resize", duration, x, y);
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/PaddleFrame.png")));
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load PaddleFrame image: " + e.getMessage());
            setFill(javafx.scene.paint.Color.DARKGREEN);
        }
    }

    @Override
    public void applyEffect(Paddle paddle, Ball ball) {
        for (ActiveEffect ae : ArkanoidGame.activeEffects) {
            if (ae.powerUp instanceof PaddleResizePowerUp) {
                ae.startTime = System.currentTimeMillis();
                return;
            }
        }
        currentResizeAmount = 0;
        System.out.println("Applying Paddle Resize PowerUp");
    }

    public void updateEffect(Paddle paddle) {
        //tăng kích thước
        if (paddle.getWidth() < DefaultPaddleResizer.MAX_PADDLE_WIDTH) {
            resizer.increaseLength(paddle, RESIZE_AMOUNT);
            currentResizeAmount += RESIZE_AMOUNT;
            System.out.println("Paddle resized to: " + paddle.getWidth());
        }
    }

    @Override
    public void removeEffect(Paddle paddle, Ball ball) {
        resizer.resetLength(paddle); // Đặt lại kích thước
        System.out.println("Removing Paddle Resize PowerUp. Paddle reset to original size.");
    }
}