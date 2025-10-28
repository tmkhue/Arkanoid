package org.example.game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

public class Levels {
    protected int BrickCols = 16;
    protected int BrickRows = 5;
    private int level = 1;

    public void start(Pane gamePane, Ball ball){
        Level1(gamePane);
    }

    public void Level1(Pane gamePane) {
        Color c = Color.web("rgb(1,53,250)");
        for (int row = 0; row < BrickRows; row++) {
            for (int col = 0; col < BrickCols; col++) {
                Brick brick = new Brick(
                        ArkanoidGame.LEFT_BORDER + col * Brick.BRICK_WIDTH + 5 + 20,
                        ArkanoidGame.TOP_BORDER + row * (Brick.BRICK_HEIGHT + 5) + 30,
                        Brick.BRICK_WIDTH - 5,
                        Brick.BRICK_HEIGHT
                );
                brick.setFill(c);

                Brick.bricks.add(brick);
                gamePane.getChildren().add(brick);
            }
        }
    }

    public void Level2(Pane gamePane) {
        for (int i = 0; i < BrickRows; i++) {
            for (int j = 0; j < BrickCols; j++) {
                if (j >= 4 && j <= 8 && i == BrickRows - 1) {
                    Brick brick = Brick.create(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5),
                            "unbreakable");
                    Brick.bricks.add(brick);
                    gamePane.getChildren().add(brick);
                } else {
                    Brick brick = Brick.create(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5));
                    Brick.bricks.add(brick);
                    gamePane.getChildren().add(brick);
                }
            }
        }
    }

    public void Level3(Pane gamePane) {
        for (int i = 0; i < BrickRows; i++) {
            for (int j = 0; j < BrickCols; j++) {
                if (((j == 1 || j == 5) && i > 0 && i < 4) || (j == 9 && i == 1)) {
                    Brick brick = Brick.create(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5));
                    Brick.bricks.add(brick);
                    gamePane.getChildren().add(brick);
                } else if (j != 3 && j != 7 && j < 9 || (j > 8 && i < 3)) {
                    Brick brick = Brick.create(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5),
                            "strong");
                    Brick.bricks.add(brick);
                    gamePane.getChildren().add(brick);
                } else {
                    Brick brick = Brick.create(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5),
                            "unbreakable");
                    Brick.bricks.add(brick);
                    gamePane.getChildren().add(brick);
                }
            }
        }
    }

    public boolean isAllBricksDestroyed() {
        for (Brick brick : Brick.bricks) {
            if (!brick.isDestroyed()) return false;
        }
        return true;
    }

    public void removeDestroyedBricks(Pane gamePane) {
        Brick.bricks.removeIf(brick -> {
            if (brick.isDestroyed()) {
                gamePane.getChildren().remove(brick);
                return true;
            }
            return false;
        });
    }
}