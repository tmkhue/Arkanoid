package org.example.game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Levels {
    protected int BrickCols = 11;
    protected int BrickRows = 5;

    private ArrayList<Brick> bricks = new ArrayList<>();

    public void Level1(Pane gamePane) {
        for (int i = 0; i < BrickRows; i++) {
            for (int j = 0; j < BrickCols; j++) {
                Brick brick = Brick.create(
                        ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                        ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5));
                bricks.add(brick);
                brick.draw("");
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
                    bricks.add(brick);
                    gamePane.getChildren().add(brick.draw(""));
                } else {
                    Brick brick = Brick.create(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5));
                    bricks.add(brick);
                    gamePane.getChildren().add(brick.draw(""));
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
                    bricks.add(brick);
                    gamePane.getChildren().add(brick.draw(""));
                } else if (j != 3 && j != 7 && j < 9 || (j > 8 && i < 3)) {
                    Brick brick = Brick.create(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5),
                            "strong");
                    bricks.add(brick);
                    gamePane.getChildren().add(brick.draw(""));
                } else {
                    Brick brick = Brick.create(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5),
                            "unbreakable");
                    bricks.add(brick);
                    gamePane.getChildren().add(brick.draw(""));
                }
            }
        }
    }

    public boolean isAllBricksDestroyed() {
        for (Brick brick : bricks) {
            if (!brick.isDestroyed()) return false;
        }
        return true;
    }

    public void removeDestroyedBricks(Pane gamePane) {
        bricks.removeIf(brick -> {
            if (brick.isDestroyed()) {
                gamePane.getChildren().remove(brick);
                return true;
            }
            return false;
        });
    }
}