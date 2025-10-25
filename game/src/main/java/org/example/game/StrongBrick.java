package org.example.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class StrongBrick extends Brick{
    public StrongBrick(){
        super();
        this.setType("S");
        this.setHitPoints(3);
    }
    public StrongBrick(double x, double y){
        super(x, y,"S");
        this.setHitPoints(3);
    }
    @Override
    public void applyTexture(String path) {
        setFill(Color.LIME);
    }
}