package org.example.game;

import javafx.scene.layout.Pane;

import java.util.List;

public class PowerUpFactory {
    public static PowerUp createPowerUp(double x, double y, Pane gamePane, List<Ball> balls, Paddle paddle, PaddleResizer paddleResizer) {
        if (Math.random() < 1.0 / 6.0) {
            return new FastBallPowerUp(x, y, 15);
        } else if (Math.random() < 2.0 / 6.0) {
            return new TripleBallPowerUp(x, y, 15, gamePane, balls);
        } else if (Math.random() < 3.0 / 6.0) {
            return new StrongBallPowerUp(x, y, 15);
        } else if (Math.random() < 4.0 / 6.0) {
            return new WidenPaddlePowerUp(x, y, 10, paddleResizer);
        } else {
            return new ExtraLifePowerUp(x, y);
        }
    }
}