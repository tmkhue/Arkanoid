package org.example.game;

import javafx.scene.layout.Pane;

import java.util.List;

public class PowerUpFactory {
    public static PowerUp createPowerUp(double x, double y, Pane gamePane, List<Ball> balls, Paddle paddle) {
        if (Math.random() < 0.33) {
            return new FastBallPowerUp(x, y, 15);
        } else if (Math.random() < 0.66) {
            return new TripleBallPowerUp(x, y, 15, gamePane, balls);
        } else {
            return new StrongBallPowerUp(x, y, 15);
        }
    }
}