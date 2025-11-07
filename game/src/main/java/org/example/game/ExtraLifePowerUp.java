package org.example.game;

public class ExtraLifePowerUp extends PowerUp {
    public ExtraLifePowerUp(double x, double y) {
        super("ExtraLife", 0, x, y, "/org/example/game/Image/Hearts.png");
    }

    public void applyEffect(Paddle paddle, Ball ball) {
        ArkanoidGame game = ArkanoidGame.getInstance();
        if (game != null) {
            game.addLife();
        }
    }

    public void removeEffect(Paddle paddle, Ball ball) {

    }
}
