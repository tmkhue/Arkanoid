package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class StrongBallPowerUp extends PowerUp {
    public StrongBallPowerUp(double x, double y, double duration) {
        super("Strong Ball",duration, x, y);
    }

    public void applyEffect(Paddle paddle, Ball ball) {
        //Nếu đã có thì không áp dụng hiệu ứng nữa
        for (ActiveEffect ae : ArkanoidGame.activeEffects) {
            if (ae.powerUp instanceof StrongBallPowerUp) {
                return;
            }
        }
        try {
            double diameter = ball.getRadius() * 4.5;
            Image strongImg = new Image(getClass().getResourceAsStream("/org/example/game/Image/StrongBall.png"), diameter, diameter, true, true);
            ball.setFill(new ImagePattern(strongImg));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load ball image. Using default color.");
            setStyle("-fx-fill: white;");
        }
        System.out.println("Strong Ball");
    }

    public void removeEffect(Paddle paddle, Ball ball) {
        if (ball == null) {
            return;
        }
        try {
            double diameter = ball.getRadius() * 4.5;
            Image img = new Image(getClass().getResourceAsStream("/org/example/game/Image/normalBall.png"), diameter, diameter, true, true);
            ball.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load ball image. Using default color.");
            setStyle("-fx-fill: white;");
        }
        System.out.println("Normal Ball");
    }
}