package org.example.game;


import gameObject.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class NormalBrick extends Brick {
    public NormalBrick(){
        super();
        this.setHitPoints(1);
    }
    public NormalBrick(double x, double y){
        super(x, y);
        this.setHitPoints(1);
    }
    @Override
    public Rectangle draw(String path){
        path="/Image/normalBrick.png";
        return super.draw(path);
    }
}