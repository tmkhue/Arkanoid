package org.example.game;

import javafx.scene.layout.Pane;

public class NormalBrick extends Brick {
    public NormalBrick(){
        super();
        this.setType("N");
        this.setHitPoints(1);
    }
    public NormalBrick(double x, double y){
        super(x, y,"N");
        this.setHitPoints(1);
    }

    public NormalBrick(double x, double y, double angle,
                       double minX, double maxX,double maxY, double minY){
        super(x, y, "N", angle, minX, maxX, minY, maxY);
        this.setHitPoints(1);
    }

    @Override
    public void applyTexture(String path, Pane gamePane) {
        path="/org/example/game/Image/normalBrick.png";
        super.applyTexture(path, gamePane);
    }
}