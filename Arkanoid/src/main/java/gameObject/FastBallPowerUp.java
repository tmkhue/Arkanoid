package gameObject;

public class FastBallPowerUp extends PowerUp {
    private final double speedMultiplier = 1.5;

    public FastBallPowerUp(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public void applyEffect(Ball ball) {
        ball.setSpeed(ball.getSpeed() * speedMultiplier);
    }

    public void removeEffect(Ball ball) {
        ball.setSpeed(ball.getSpeed() / speedMultiplier);
    }
}
