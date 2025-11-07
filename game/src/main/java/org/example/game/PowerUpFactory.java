package org.example.game;

import javafx.scene.layout.Pane;

import java.util.List;

public class PowerUpFactory {
    public static PowerUp createPowerUp(double x, double y, Pane gamePane, List<Ball> balls, Paddle paddle) {
        if (Math.random() < 0.25) {
            return new FastBallPowerUp(x, y, 15);
        } else if (Math.random() < 0.5) {
            return new TripleBallPowerUp(x, y, 15, gamePane, balls);
        } else if (Math.random() < 0.75) {
            return new StrongBallPowerUp(x, y, 15);
        } else {
            return new ExtraLifePowerUp(x, y);
        }
    }
}