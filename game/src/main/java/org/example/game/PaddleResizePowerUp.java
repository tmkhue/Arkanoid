package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.Objects;

public class PaddleResizePowerUp extends PowerUp {
    private PaddleResizer resizer = new DefaultPaddleResizer();
    private static final double RESIZE_STEP = 1.1;
    private static final double MAX_PADDLE_WIDTH_FACTOR = 1.5;

    private double targetWidth;
    private boolean resizedToTarget = false;

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

        targetWidth = DefaultPaddleResizer.INITIAL_PADDLE_WIDTH * MAX_PADDLE_WIDTH_FACTOR;
        if (targetWidth > DefaultPaddleResizer.MAX_PADDLE_WIDTH) {
            targetWidth = DefaultPaddleResizer.MAX_PADDLE_WIDTH;
        }
        resizedToTarget = false;
        System.out.println("Applying Paddle Resize PowerUp. Target width: " + targetWidth);
    }

    public void updateEffect(Paddle paddle) {
        if (!resizedToTarget) {
            if (paddle.getWidth() < targetWidth) {
                resizer.increaseLength(paddle, RESIZE_STEP);
                if (paddle.getWidth() >= targetWidth) {
                    paddle.setWidth(targetWidth);
                    resizedToTarget = true;
                    System.out.println("Paddle reached target width: " + paddle.getWidth());
                }
            } else {
                resizedToTarget = true;
            }
        }

    }

    @Override
    public void removeEffect(Paddle paddle, Ball ball) {
        resizer.resetLength(paddle); // Đặt lại kích thước bdau
        System.out.println("Removing Paddle Resize PowerUp. Paddle reset to original game size.");
    }
}