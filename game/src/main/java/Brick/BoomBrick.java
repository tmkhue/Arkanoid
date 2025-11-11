package Brick;

import Ball.Ball;
import javafx.scene.layout.Pane;

public class BoomBrick extends Brick {

    public BoomBrick() {
        super();
    }

    public BoomBrick(double x, double y, double angle,
                     double minX, double maxX, double maxY, double minY) {
        super(x, y, "B", angle, minX, maxX, minY, maxY);
        this.setHitPoints(1);

    }

    public void explode(Ball ball, Pane gamePane) {
        double explosionRadius = BRICK_HEIGHT*BRICK_HEIGHT+BRICK_WIDTH*BRICK_WIDTH+1500;
        for(Brick b:bricks) {
            if (b == this) continue;
            if (b instanceof Flower) continue;
            double dx = b.getX() - this.getX();
            double dy = b.getY() - this.getY();
            if (dx*dx+dy*dy <= explosionRadius) {
                b.takeHit(ball, gamePane);
            }
            if(b.isDestroyed()){
                b.setHidden(true);
                gamePane.getChildren().remove(b);
            }
        }
    }

    @Override
    public void applyTexture(String path, Pane gamePane) {
        path = "/org/example/game/Image/BoomBrick.jpg";
        super.applyTexture(path, gamePane);
    }
}