package org.example.game;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class TripleBallPowerUp extends PowerUp {
    private List<Ball> balls = new ArrayList<>();
    private List<Ball> extra = new ArrayList<>();
    private Pane gamePane;

    public TripleBallPowerUp(double x, double y, double duration, Pane gamePane, List<Ball> balls) {
        super("Triple Ball", duration, x, y);
        this.balls = balls;
        this.gamePane = gamePane;
    }

    public void applyEffect(Paddle paddle, Ball mainBall) {
        for (int i = 0; i< 2; i++) {
            Ball newBall = new Ball();
            newBall.setCenterX(mainBall.getCenterX());
            newBall.setCenterY(mainBall.getCenterY());
            newBall.setDirectionX(mainBall.getDirectionX());
            newBall.setDirectionY(mainBall.getDirectionY());
            newBall.setGamePane(gamePane);
            gamePane.getChildren().add(newBall);
            balls.add(newBall);
            extra.add(newBall);
        }
        System.out.println("Ball x 3");
    }

    public void removeEffect(Paddle paddle, Ball mainBall) {
        for (Ball b : extra) {
            gamePane.getChildren().remove(b);
            balls.remove(b);
        }
        extra.clear();
        System.out.println("Ball x 1");
    }
}
