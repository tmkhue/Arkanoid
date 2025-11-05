package org.example.game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class UnbreakableBrick extends Brick {
    public UnbreakableBrick() {
        super();
        this.setType("U");
        this.setHitPoints(Integer.MAX_VALUE);
    }

    public UnbreakableBrick(double x, double y) {
        super(x, y,"U");
        this.setHitPoints(Integer.MAX_VALUE);
    }

    @Override
    public boolean isDestroyed() {
        System.out.println("Ham isDestroyed trong U");
        return false;
    }

    public void applyTexture(String path, Pane gamePane) {
        path="/org/example/game/Image/UnbreakableBrick.png";
        super.applyTexture(path, gamePane);
    }
}