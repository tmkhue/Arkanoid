package org.example.game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Brick extends Rectangle {
    private int BrickRows = 5;
    private int BrickCol = 14; //số cột nhiều nhất có thể
    private static final int BRICK_WIDTH = 50;
    private static final int BRICK_HEIGHT = 20;

    private List<Rectangle> bricks = new ArrayList<>();

    public void Level1(Pane gamePane) {
        Color c = Color.web("rgb(1,53,250)");
        for (int row = 0; row < BrickRows; row++) {
            for (int col = 0; col < BrickCol; col++) {
                Rectangle brick = new Rectangle(
                        ArkanoidGame.LEFT_BORDER + col * BRICK_WIDTH + 5 + 20,
                        ArkanoidGame.TOP_BORDER + row * (BRICK_HEIGHT + 5) + 30,
                        BRICK_WIDTH - 5,
                        BRICK_HEIGHT
                );
                brick.setFill(c);
                bricks.add(brick);
                gamePane.getChildren().add(brick);
            }
        }
    }

    public boolean checkCollision(Ball ball) {
        Iterator<Rectangle> it = bricks.iterator();
        while (it.hasNext()) {
            Rectangle brick = it.next();
            if (brick.getBoundsInParent().intersects(ball.getBoundsInParent())) {
                it.remove();
                ((Pane) brick.getParent()).getChildren().remove(brick);
                ball.setDirectionY(ball.getDirectionY() * (-1));
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return bricks.isEmpty();
    }

    public void clear(Pane gamePane) {
        for (Rectangle brick : bricks) {
            gamePane.getChildren().remove(brick);
        }
        bricks.clear();
    }
}