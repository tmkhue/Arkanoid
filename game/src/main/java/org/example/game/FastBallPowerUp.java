package org.example.game;

import static org.example.game.ArkanoidGame.activeEffects;

public class FastBallPowerUp extends PowerUp {
    private double originalSpeed;

    public FastBallPowerUp(double x, double y, double duration) {
        super("FastBall", duration, x, y, "/org/example/game/Image/fastBall.png");
    }

    public void applyEffect(Paddle paddle, Ball ball) {
        for (ActiveEffect ae : activeEffects) {
            if (ae.powerUp instanceof FastBallPowerUp) {
                return;
            }
        }
        originalSpeed = ball.getSpeed();
        ball.setSpeed(originalSpeed * 1.5);
        System.out.println("Speed x 1.5");
    }

    public void removeEffect(Paddle paddle, Ball ball) {
        ball.setSpeed(originalSpeed);
        System.out.println("Original Speed");
    }
}