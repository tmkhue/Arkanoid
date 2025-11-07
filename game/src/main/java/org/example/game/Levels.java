package org.example.game;

import javafx.scene.layout.Pane;

import static org.example.game.Brick.bricks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Levels {
    protected int BrickCols = 11;
    protected int BrickRows = 5;
    private int level = 1;
    public static final int LEVEL = 3;

    public int getLevel() {
        return level;
    }

    public void next() {
        level++;
    }

    public void start(Pane gamePane, Ball ball) {
        drawNormalLevel(gamePane);
        drawBoss(gamePane);

        switch (level) {
            case 0 -> Level0(gamePane);
            case 1 -> Level1(gamePane);
            case 2 -> Level2(gamePane);
            case 3 -> Level3(gamePane);
            default -> Level0(gamePane);
        }
    }

    public String[][] loadLevel() {
        String path = "/org/example/game/Levels/levels.txt";
        List<String[]> lines = new ArrayList<>();
        String line;
        boolean isFound = false;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(path))))) {
            while ((line = br.readLine()) != null) {
                if (line.trim().equalsIgnoreCase("level " + level)) {
                    isFound = true;
                    continue;
                }
                if (!isFound) continue;
                if (line.trim().equals("----")) {
                    break;
                }
                System.out.println(line);
                lines.add(line.split("\\s+"));
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines.toArray(new String[0][]);
    }

    public void drawNormalLevel(Pane gamePane) {

//        Brick flower = new Flower(200, 150, 300);
//        bricks.add(flower);
//        flower.applyTexture("", gamePane);
//        flower.toBack();
//        ((Flower) flower).getFlowerCenter().toBack();
        String[][] data = loadLevel();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                int distance = 5;
                double x = ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + distance);
                double y = ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + distance);
                if (!(x + Brick.BRICK_WIDTH < ArkanoidGame.LEFT_BORDER + ArkanoidGame.WIDTH)) {
                    break;
                }
                double minY = y, maxY = y;
                double maxX = x, minX = x;
                double angle = 90;
                Brick brick = new Brick();
                if (data[i][j].contains("m")) {
                    String[] parts = data[i][j].split("-");
                    try {
                        angle = Double.parseDouble(parts[1]);
                        if (angle != 90) {
                            minX = Double.parseDouble(parts[2]);
                            maxX = Double.parseDouble(parts[3]);
                        }
                        if (angle != 180) {
                            minY = Double.parseDouble(parts[4]);
                            maxY = Double.parseDouble(parts[5]);
                        }
                        System.out.printf("MINX = %.1f, MAX Y = %.1f, MINX = %.1f, MAX Y = %.1f\n",
                                minX, maxX, minY, maxY);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid string");
                    }
                }
                switch (data[i][j].charAt(0)) {
                    case 'N':
                        brick = new NormalBrick(x, y, angle, minX, maxX, minY, maxY);
                        break;
                    case 'S':
                        brick = new StrongBrick(x, y, angle, minX, maxX, minY, maxY);
                        break;
                    case 'U':
                        brick = new UnbreakableBrick(x, y, angle, minX, maxX, minY, maxY);
                        break;
                    default:
                        continue;
                }

                bricks.add(brick);
                brick.applyTexture("", gamePane);
            }
        }
//        if (Brick.bricks.stream().noneMatch(
//                b -> b instanceof NormalBrick || b instanceof StrongBrick)) {
//            drawBoss(gamePane);
//        }
    }

    public void drawBoss(Pane gamePane) {
//        bricks.clear();

        if (level == LEVEL) {
            System.out.println("DRAW BOSS");
            Brick flower = new Flower(200, 150, 300);
            bricks.add(flower);
            flower.applyTexture("", gamePane);
        }
    }

    public void Level0(Pane gamePane) {
        Brick brick = new Flower(350, 300, 300);
        brick.applyTexture("", gamePane);
        bricks.add(brick);
    }

    public void Level1(Pane gamePane) {
        for (int row = 0; row < BrickRows; row++) {
            for (int col = 0; col < BrickCols; col++) {
                double x = ArkanoidGame.LEFT_BORDER + 20 + col * (Brick.BRICK_WIDTH + 5);
                double y = ArkanoidGame.TOP_BORDER + 30 + row * (Brick.BRICK_HEIGHT + 5);
                if (x + Brick.BRICK_WIDTH < ArkanoidGame.LEFT_BORDER + ArkanoidGame.WIDTH) {
                    System.out.println("vào if");
                    Brick brick = new NormalBrick(x, y);
                    bricks.add(brick);
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
                if (!(x + Brick.BRICK_WIDTH < ArkanoidGame.LEFT_BORDER + ArkanoidGame.WIDTH)) {
                    break;
                }
                if (j >= 3 && j <= 7 && i == BrickRows - 1) {
                    Brick brick = new UnbreakableBrick(x, y);
                    bricks.add(brick);
                    brick.applyTexture("", gamePane);
                } else {
                    Brick brick = new NormalBrick(x, y);
                    bricks.add(brick);
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
                if (!(x + Brick.BRICK_WIDTH < ArkanoidGame.LEFT_BORDER + ArkanoidGame.WIDTH)) {
                    break;
                }
                if (((j == 1 || j == 5) && i > 0 && i < 4)
                        || (j == 9 && i == 1)) {
                    Brick brick = new StrongBrick(x, y);
                    bricks.add(brick);
                    brick.applyTexture("", gamePane);
                } else if (j != 3 && j != 7 && j < 9 || (j > 8 && i < 3)) {
                    Brick brick = new NormalBrick(x, y);
                    bricks.add(brick);
                    brick.applyTexture("", gamePane);
                } else {
                    Brick brick = new UnbreakableBrick(x, y);
                    bricks.add(brick);
                    brick.applyTexture("", gamePane);
                }
            }
        }
    }
}