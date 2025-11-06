package org.example.game;

import javafx.scene.layout.Pane;

public class PetalPiece extends Ball {
    public PetalPiece() {
    }

    public PetalPiece(double r, double x, double y, double dx, double dy) {
        this.setRadius(r);
        this.setCenterX(x);
        this.setCenterY(y);
        this.setDirectionX(dx);
        this.setDirectionY(dy);
    }

    //kiểm tra petalpiece có chạm cạnh ko
    public boolean check() {
//        if (this.getBoundsInParent().intersects(paddle.getBoundsInParent())){
//            return true;
//        }
        if (this.getCenterY() >= 600) {
            return true;
        }
        return false;
    }

    public void resolve(Pane gamePane) {
        gamePane.getChildren().remove(this);
    }
}