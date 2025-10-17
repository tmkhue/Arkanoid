package org.example.game;

import gameObject.Brick;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UnbreakableBrick extends Brick {
    public UnbreakableBrick() {
        super();
    }

    public UnbreakableBrick(double x, double y) {
        super(x, y);
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    public Rectangle draw(String path) {
        Rectangle rect = new Rectangle((int) this.x, (int) this.y,
                (int) BRICK_WIDTH, (int) BRICK_HEIGHT);
        rect.setFill(Color.GRAY);
        return rect;
    }
}