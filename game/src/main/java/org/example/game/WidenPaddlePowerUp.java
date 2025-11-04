package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.util.Objects;

public class WidenPaddlePowerUp extends PowerUp {
    private PaddleResizer paddleResizer;
    private AnimationTimer widenTimer;
    private double widenAmountPerFrame = 12; // Amount to widen by per frame

    public WidenPaddlePowerUp(double x, double y, double duration, PaddleResizer paddleResizer) {
        super("Widen Paddle", duration, x, y);
        this.paddleResizer = paddleResizer;
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/PaddleFrame.png")));
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load WidenPaddlePowerUp image: " + e.getMessage());
            setFill(javafx.scene.paint.Color.PURPLE);
        }
    }

    @Override
    public void applyEffect(Paddle paddle, Ball ball) {
        // Prevent multiple instances of this power-up from stacking
        for (ActiveEffect ae : ArkanoidGame.activeEffects) {
            if (ae.powerUp instanceof WidenPaddlePowerUp) {
                System.out.println("Widen Paddle effect already active.");
                return;
            }
        }
        System.out.println("Applying Widen Paddle effect.");

        widenTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddleResizer.increaseLength(paddle, widenAmountPerFrame);
            }
        };
        widenTimer.start();
    }

    @Override
    public void removeEffect(Paddle paddle, Ball ball) {
        if (widenTimer != null) {
            widenTimer.stop();
        }
        paddleResizer.resetLength(paddle); // Reset to initial size
        System.out.println("Removing Widen Paddle effect and resetting paddle size.");
    }
}