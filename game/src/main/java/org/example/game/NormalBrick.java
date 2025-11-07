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
    public NormalBrick(double x, double y, boolean movable){
        super(x, y,"N", movable);
        this.setHitPoints(1);
    }

    @Override
    public void applyTexture(String path, Pane gamePane) {
        path="/org/example/game/Image/normalBrick.png";
        super.applyTexture(path, gamePane);
    }
}