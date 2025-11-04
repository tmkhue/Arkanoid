package org.example.game;

import javafx.scene.layout.Pane;

import java.util.List;

public class PowerUpFactory {
    public static PowerUp createPowerUp(double x, double y, Pane gamePane, List<Ball> balls, Paddle paddle, PaddleResizer paddleResizer) {
        if (Math.random() < 0.2) {
            return new FastBallPowerUp(x, y, 15);
        } else if (Math.random() < 0.4) {
            return new TripleBallPowerUp(x, y, 15, gamePane, balls);
        } else if (Math.random() < 0.6) {
            return new StrongBallPowerUp(x, y, 15);
        } else {
            return new WidenPaddlePowerUp(x, y, 10, paddleResizer);
        }
    }
}