package Ball;

import Brick.Levels;
import Controller.ArkanoidGame;
import Paddle.Paddle;
import javafx.scene.layout.Pane;

public class PetalPiece extends Ball {
    private final ArkanoidGame arkanoidGame= ArkanoidGame.getInstance();
    private Levels level;
    public PetalPiece(double r, double x, double y, double dx, double dy) {
        super(r, x, y, dx, dy);
    }

    //kiểm tra petalpiece có chạm paddle ko
    public boolean checkCollisionWithPaddle() {
        if ( arkanoidGame != null) {
            Paddle paddle =  arkanoidGame.getPaddle();
            boolean check =true;
            if (check&&paddle != null
                    && this.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
                check = false;
                return true;
            }
        }
        return false;
    }

    public void resolve(Pane gamePane) {
        if (checkCollisionWithPaddle()){
            Levels.countStar++;
            arkanoidGame.increaseScore(5);
            gamePane.getChildren().remove(this);
        }
    }
}