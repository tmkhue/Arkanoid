package Brick;

import Ball.Ball;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class BoomBrick extends Brick {

    public BoomBrick() {
        super();
        this.setHitPoints(1);
        this.setType("B");
    }

    public BoomBrick(double x, double y, double angle,
                     double minX, double maxX, double maxY, double minY) {
        super(x, y, "B", angle, minX, maxX, minY, maxY);
        this.setHitPoints(1);

    }

    public void explode(Ball ball, Pane gamePane) {
        double explosionRadius = 10;
        List<Brick> toRemove = new ArrayList<>();
        for(Brick b:bricks) {
            if (b == this) continue;
            if (b instanceof Flower) continue;
            double dx=0, dy=0;
            if (b.getX()+ b.getWidth()< this.getX()) {
                dx = this.getX() - (b.getX()+ b.getWidth());
            } else if(this.getX()+ this.getWidth()< b.getX()) {
                dx = b.getX() - (this.getX()+ this.getWidth());
            }
            if (b.getY()+ b.getHeight()< this.getY()) {
                dy = this.getY() - (b.getY()+ b.getHeight());
            } else if(this.getY()+ this.getHeight()< b.getY()) {
                dy = b.getY() - (this.getY()+ this.getHeight());
            }
            if (Math.sqrt(dx*dx+dy*dy) <= explosionRadius) {
                b.takeHit(ball, gamePane);
            }
            if(b.isDestroyed()){
                b.setHidden(true);
                toRemove.add(b);
            }
        }
        for(Brick b:toRemove){
            gamePane.getChildren().remove(b);
        }
    }

    @Override
    public void applyTexture(String path, Pane gamePane) {
        path = "/org/example/game/Image/BoomBrick.jpg";
        super.applyTexture(path, gamePane);
    }
}