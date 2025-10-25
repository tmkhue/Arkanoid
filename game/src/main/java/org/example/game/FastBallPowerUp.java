package org.example.game;

public class FastBallPowerUp extends PowerUp{
    private double originalSpeed;

    public FastBallPowerUp(double x, double y, double duration) {
        super("FastBall", duration, x, y);
    }

    public void applyEffect(Paddle paddle, Ball ball) {
        originalSpeed = ball.getSpeed();
        ball.setSpeed(originalSpeed * 1.5);
        System.out.println("Speed x 1.5");
    }

    public void removeEffect(Paddle paddle, Ball ball) {
        ball.setSpeed(originalSpeed);
        System.out.println("Original Speed");
    }
}