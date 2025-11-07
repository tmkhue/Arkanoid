package org.example.game;

import javafx.scene.layout.Pane;

public class Levels {
    protected int BrickCols = 11;
    protected int BrickRows = 5;
    private int level = 1;

    public int getLevel() {
        return level;
    }

    public void next() {
        level++;
    }

    public void start(Pane gamePane, Ball ball) {
        switch (level) {
            case 0 -> Level0(gamePane);
            case 1 -> Level1(gamePane);
            case 2 -> Level2(gamePane);
            case 3 -> Level3(gamePane);
            default -> Level0(gamePane);
        }
    }

    public void Level0(Pane gamePane){
        Brick brick = new Flower(350, 300, 300);
        brick.applyTexture("", gamePane);
        Brick.bricks.add(brick);
//        Brick brick=new StrongBrick(200, 150);
//        Brick.bricks.add(brick);
//        brick.applyTexture("", gamePane);
    }

    public void Level1(Pane gamePane) {
        for (int row = 0; row < BrickRows; row++) {
            for (int col = 0; col < BrickCols; col++) {
                double x = ArkanoidGame.LEFT_BORDER + 20 + col * (Brick.BRICK_WIDTH + 5);
                double y = ArkanoidGame.TOP_BORDER + 30 + row * (Brick.BRICK_HEIGHT + 5);
                if (x + Brick.BRICK_WIDTH < ArkanoidGame.LEFT_BORDER + ArkanoidGame.WIDTH) {
                    System.out.println("vào if");
                    Brick brick = new NormalBrick(x, y);
                    Brick.bricks.add(brick);
                    brick.applyTexture("", gamePane);
                } else break;
            }
        }
    }

    public void Level2(Pane gamePane) {
        for (int i = 0; i < BrickRows; i++) {
            for (int j = 0; j < BrickCols; j++) {
                double x = ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5);
                double y = ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5);
                if(!(x + Brick.BRICK_WIDTH < ArkanoidGame.LEFT_BORDER + ArkanoidGame.WIDTH)){
                    break;
                }
                if (j >= 3 && j <= 7 && i == BrickRows - 1) {
                    Brick brick = new UnbreakableBrick(x, y);
                    Brick.bricks.add(brick);
                    brick.applyTexture("", gamePane);
                } else {
                    Brick brick = new NormalBrick(x, y);
                    Brick.bricks.add(brick);
                    brick.applyTexture("", gamePane);
                }
            }
        }
    }

    public void Level3(Pane gamePane) {
        for (int i = 0; i < BrickRows; i++) {
            for (int j = 0; j < BrickCols; j++) {
                double x = ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + 5);
                double y = ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + 5);
                if(!(x + Brick.BRICK_WIDTH < ArkanoidGame.LEFT_BORDER + ArkanoidGame.WIDTH)){
                    break;
                }
                if (((j == 1 || j == 5) && i > 0 && i < 4)
                        || (j == 9 && i == 1)) {
                    Brick brick = new StrongBrick(x, y);
                    Brick.bricks.add(brick);
                    brick.applyTexture("", gamePane);
                } else if (j != 3 && j != 7 && j < 9 || (j > 8 && i < 3)) {
                    Brick brick = new NormalBrick(x, y);
                    Brick.bricks.add(brick);
                    brick.applyTexture("", gamePane);
                } else {
                    Brick brick = new UnbreakableBrick(x, y);
                    Brick.bricks.add(brick);
                    brick.applyTexture("", gamePane);
                }
            }
        }
    }
}