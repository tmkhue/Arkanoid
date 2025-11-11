package PowerUp;

import Ball.Ball;
import Paddle.Paddle;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class TripleBallPowerUp extends PowerUp {
    private List<Ball> balls = new ArrayList<>();
    private List<Ball> extra = new ArrayList<>();
    private Pane gamePane;

    public TripleBallPowerUp(double x, double y, double duration, Pane gamePane, List<Ball> balls) {
        super("Triple Ball", duration, x, y, "/org/example/game/Image/Ballx3.png");
        this.balls = balls;
        this.gamePane = gamePane;
    }

    public void applyEffect(Paddle paddle, Ball mainBall) {
        for (int i = 0; i < 2; i++) {
            Ball newBall = new Ball();
            newBall.setCenterX(mainBall.getCenterX());
            newBall.setCenterY(mainBall.getCenterY());
            double angleOffset = (i == 0) ? Math.toRadians(-20) : Math.toRadians(20);
            double newDirectionX = mainBall.getDirectionX() * Math.cos(angleOffset)
                    - mainBall.getDirectionY() * Math.sin(angleOffset);
            double newDirectionY = mainBall.getDirectionX() * Math.cos(angleOffset)
                    + mainBall.getDirectionY() * Math.sin(angleOffset);
            newBall.setDirectionX(newDirectionX);
            newBall.setDirectionY(newDirectionY);
            newBall.setSpeed(mainBall.getSpeed());
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