package PowerUp;

import Ball.Ball;
import Controller.ArkanoidGame;
import Paddle.Paddle;
import javafx.animation.AnimationTimer;
import Paddle.PaddleResizer;

public class WidenPaddlePowerUp extends PowerUp {
    private PaddleResizer paddleResizer;
    private AnimationTimer widenTimer;
    private double widenAmountPerFrame = 12;

    public WidenPaddlePowerUp(double x, double y, double duration, PaddleResizer paddleResizer) {

        super("Widen Paddle", duration, x, y, "/org/example/game/Image/WidePaddle.png");
        this.paddleResizer = paddleResizer;
    }

    @Override
    public void applyEffect(Paddle paddle, Ball ball) {

        for (ActiveEffect ae : ArkanoidGame.activeEffects) {
            if (ae.powerUp instanceof WidenPaddlePowerUp) {
                System.out.println("Widen Paddle effect already active.");
                return;
            }
        }
        System.out.println("Applying Widen Paddle effect.");

        widenTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddleResizer.increaseLength(paddle, widenAmountPerFrame);
            }
        };
        widenTimer.start();
    }

    @Override
    public void removeEffect(Paddle paddle, Ball ball) {
        if (widenTimer != null) {
            widenTimer.stop();
        }
        paddleResizer.resetLength(paddle);
        System.out.println("Removing Widen Paddle effect and resetting paddle size.");
    }
}