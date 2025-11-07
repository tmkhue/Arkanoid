package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Arrow extends Rectangle {
    private double speed = 8;
    private Pane gamePane;
    private ArkanoidGame arkanoidGame;

    public Arrow(double x, double y, Pane gamePane, ArkanoidGame arkanoidGame) {
        super(x, y, 20, 40);
        this.gamePane = gamePane;
        this.arkanoidGame = arkanoidGame;

        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Arrow.png")));
            if (img.isError()) {
                throw new Exception("Lỗi tải hình ảnh Arrow");
            }
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Không thể tải hình ảnh Arrow: " + e.getMessage());
            setFill(javafx.scene.paint.Color.YELLOW);
        }
    }

    public void move() {
        setY(getY() - speed);
    }

    public boolean checkCollisionWithBricks(List<Brick> bricks, Pane gamePane) {
        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            if (this.getBoundsInParent().intersects(brick.getBoundsInParent())) {
                brick.takeHit(null, gamePane);
                if (brick.isDestroyed()) {
                    it.remove();
                    gamePane.getChildren().remove(brick);
                    if (arkanoidGame != null) {
                        arkanoidGame.increaseScore(10);
                    }
                    if (brick instanceof Flower) {
                        gamePane.getChildren().remove(((Flower) brick).flowerCenter);
                    }
                }
                return true;
            }
        }
        return false;
    }
}