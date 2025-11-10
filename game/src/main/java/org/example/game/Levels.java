package org.example.game;

import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.game.Brick.bricks;

public class Levels {
    protected static int countStar = 0;
    public static final int COUNT_STARS = 5;
    protected static int level = 8;
    public static final int LEVEL = 10;

    public int getLevel() {
        return level;
    }

    public void next() {
        level = (level + 1) % (LEVEL + 1);
    }

    public void start(Pane gamePane, Ball ball) {
        bricks.clear();
        gamePane.getChildren().removeIf(n -> n instanceof Brick);

        drawNormalLevel(gamePane);
        drawBoss(gamePane);
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines.toArray(new String[0][]);
    }

    public void drawNormalLevel(Pane gamePane) {
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
                double angle = 180;
                Brick brick = new Brick();
                if (data[i][j].contains("m")) {
                    String[] parts = data[i][j].split("-");
                    try {
                        angle = Double.parseDouble(parts[1]);
                        if (angle % 180 != 90) {
                            minX = Double.parseDouble(parts[2]);
                            maxX = Double.parseDouble(parts[3]);
                        }
                        if (angle % 180 != 0) {
                            minY = Double.parseDouble(parts[4]);
                            maxY = Double.parseDouble(parts[5]);
                        }
                        System.out.printf("MINX = %.1f, MAXX = %.1f, MINY = %.1f, MAX Y = %.1f\n",
                                minX, maxX, minY, maxY);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid string");
                    }
                }
                brick = BrickFactory.createBrick(data[i][j].charAt(0), angle, x, y, minX, maxX, minY, maxY);
                if (brick == null) continue;
                bricks.add(brick);
                brick.applyTexture("", gamePane);
            }
        }
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
}
