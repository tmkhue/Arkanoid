package org.example.game;

import javafx.scene.layout.Pane;

import java.util.List;

public class PowerUpFactory {
    public static PowerUp createPowerUp(double x, double y, Pane gamePane, List<Ball> balls, Paddle paddle) {
        double rand = Math.random();
        if (rand < 0.25) { // 25% cơ hội cho FastBallPowerUp
            return new FastBallPowerUp(x, y, 15);
        } else if (rand < 0.50) { // 25% cơ hội cho TripleBallPowerUp
            return new TripleBallPowerUp(x, y, 15, gamePane, balls);
        } else if (rand < 0.75) { // 25% cơ hội cho StrongBallPowerUp
            return new StrongBallPowerUp(x, y, 15);
        } else { // 25% cơ hội cho PaddleResizePowerUp
            return new PaddleResizePowerUp(x, y, 15); // Thời gian hiệu ứng 15 giây
        }
    }
}