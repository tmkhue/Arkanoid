package org.example.game;

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
        return false;
    }

    public void applyTexture(String path) {
        setFill(Color.GRAY);
    }
}