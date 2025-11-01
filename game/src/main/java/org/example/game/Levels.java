package org.example.game;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Levels {
    protected int BrickCols = 11;
    protected int BrickRows = 5;
    private int level = 1;

    public void start(Pane gamePane, Ball ball) {
        Level3(gamePane);
    }

    public void Level0(Pane gamePane){
        Brick brick=new UnbreakableBrick(350, 300);
        Brick.bricks.add(brick);
        brick.applyTexture("");
        gamePane.getChildren().add(brick);
    }

    public void Level1(Pane gamePane) {
        for (int row = 0; row < BrickRows; row++) {
            for (int col = 0; col < BrickCols; col++) {
                Brick brick = new NormalBrick(
                        ArkanoidGame.LEFT_BORDER + 20 + col * (Brick.BRICK_WIDTH + 5),
                        ArkanoidGame.TOP_BORDER + 30 + row * (Brick.BRICK_HEIGHT + 5));
                Brick.bricks.add(brick);
                brick.applyTexture("");
                gamePane.getChildren().add(brick);
            }
        }
    }

    public void Level2(Pane gamePane) {
        for (int i = 0; i < BrickRows; i++) {
            for (int j = 0; j < BrickCols; j++) {
                if (j >= 3 && j <= 7 && i == BrickRows - 1) {
                    Brick brick = new UnbreakableBrick(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5)
                    );
                    Brick.bricks.add(brick);
                    brick.applyTexture("");
                    gamePane.getChildren().add(brick);
                } else {
                    Brick brick = new NormalBrick(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5));
                    Brick.bricks.add(brick);
                    brick.applyTexture("");
                    gamePane.getChildren().add(brick);
                }
            }
        }
    }

    public void Level3(Pane gamePane) {
        for (int i = 0; i < BrickRows; i++) {
            for (int j = 0; j < BrickCols; j++) {
                if (((j == 1 || j == 5) && i > 0 && i < 4)
                        || (j == 9 && i == 1)) {
                    Brick brick = new StrongBrick(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5));
                    Brick.bricks.add(brick);
                    brick.applyTexture("");
                    gamePane.getChildren().add(brick);
                } else if (j != 3 && j != 7 && j < 9 || (j > 8 && i < 3)) {
                    Brick brick = new NormalBrick(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5));
                    Brick.bricks.add(brick);
                    brick.applyTexture("");
                    gamePane.getChildren().add(brick);
                } else {
                    Brick brick = new UnbreakableBrick(
                            ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5),
                            ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5));
                    Brick.bricks.add(brick);
                    brick.applyTexture("");
                    gamePane.getChildren().add(brick);
                }
            }
        }
    }
}