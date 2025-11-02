package org.example.game;

public class FastBallPowerUp extends PowerUp {
    private double originalDirectionX;
    private double originalDirectionY;

    public FastBallPowerUp(double x, double y, double duration) {
        super("FastBall", duration, x, y, "/org/example/game/Image/fastBall.png");
    }

    public void applyEffect(Paddle paddle, Ball ball) {
        originalDirectionX = ball.getDirectionX();
        originalDirectionY = ball.getDirectionY();
        ball.setDirectionX(originalDirectionX * 1.5);
        ball.setDirectionY(originalDirectionY * 1.5);
        System.out.println("Inscrease speed");
    }

    public void removeEffect(Paddle paddle, Ball ball) {
        ball.setDirectionX(originalDirectionX);
        ball.setDirectionY(originalDirectionY);
        System.out.println("Original Speed");
    }
}