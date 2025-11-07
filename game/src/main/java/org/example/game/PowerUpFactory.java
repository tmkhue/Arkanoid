package org.example.game;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.List;

public class PowerUpFactory {
    public static PowerUp createPowerUp(double x, double y, Pane gamePane, List<Ball> balls, Paddle paddle, PaddleResizer paddleResizer, ArkanoidGame arkanoidGame) {
        if (Math.random() < 0.1) {
            return new FastBallPowerUp(x, y, 15);
        } else if (Math.random() < 0.2) {
            return new TripleBallPowerUp(x, y, 15, gamePane, balls);
        } else if (Math.random() < 0.3) {
            return new StrongBallPowerUp(x, y, 15);
        } else if (Math.random() < 0.4) {
            return new WidenPaddlePowerUp(x, y, 10, paddleResizer);
        } else {
            return new ArrowPowerUp(x, y, 15, gamePane, arkanoidGame);
        }
    }
}