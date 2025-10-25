package org.example.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StrongBrick extends Brick{
    public StrongBrick(){
        super();
        this.setHitPoints(3);
    }
    public StrongBrick(double x, double y){
        super(x, y);
        this.setHitPoints(3);
    }
    public Rectangle draw(String path){
        Rectangle rect = new Rectangle((int) this.getX(), (int) this.getY(),
                (int) BRICK_WIDTH, (int) BRICK_HEIGHT);
        rect.setFill(Color.LIME);
        return rect;
    }

    @Override
    public void applyTexture() {
        setFill(Color.LIME);
    }
}