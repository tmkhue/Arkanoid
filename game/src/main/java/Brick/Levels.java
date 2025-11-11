package Brick;

import Ball.*;
import Controller.ArkanoidGame;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Brick.Brick.bricks;

public class Levels {
    public static int countStar = 0;
    public static final int COUNT_STARS = 5;
    protected static int level = 0;
    public static final int LEVEL = 20;

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
                char type = data[i][j].charAt(0);
                int distance = 5;
                double x = ArkanoidGame.LEFT_BORDER + 20 + j * (Brick.BRICK_WIDTH + distance);
                double y = ArkanoidGame.TOP_BORDER + 30 + i * (Brick.BRICK_HEIGHT + distance);
                if (!(x + Brick.BRICK_WIDTH < ArkanoidGame.LEFT_BORDER + ArkanoidGame.WIDTH)) {
                    break;
                }
                double minY = y, maxY = y;
                double maxX = x, minX = x;
                double angle = 0;
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
                brick = BrickFactory.createBrick(type, x, y, angle,minX, maxX, minY, maxY);
                if (brick == null) continue;
                bricks.add(brick);
                brick.applyTexture("", gamePane);
            }
        }
    }

    public void drawBoss(Pane gamePane) {
        List<PetalPiece> petalPieces = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            double y = Math.random() * 150 + 100;
            double x = Math.random() * 700 + 50;
            PetalPiece p = new PetalPiece(30, x, y, 0, 1);
            p.setFill(new ImagePattern(Flower.petalPiece, 0, 0, 1, 1, true));
            petalPieces.add(p);
        }
        for (int i = 0; i < 20; i++) {
            PauseTransition pause = new PauseTransition(Duration.seconds(Math.random() * 50));
            PetalPiece p = petalPieces.get(i);
            int finalI = i;
            pause.setOnFinished(event -> {
                gamePane.getChildren().add(p);
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                        if (p.checkCollisionWithPaddle()) {
                            if( finalI== 5) {
                                Levels.countStar++;
                                ArkanoidGame.getInstance().updatecountStarText();
                                System.out.println("CountStars = "+countStar);
                            }
                            p.resolve(gamePane);
                            this.stop();
                        } else {
                            p.setCenterY(p.getCenterY()+1);
                        }
                    }
                };
                timer.start();
                Platform.runLater(() -> gamePane.requestFocus());
            });
            pause.play();
        }
        if (level == LEVEL) {
            System.out.println("DRAW BOSS");
            Brick flower = new Flower(200, 150, 300);
            bricks.add(flower);
            flower.applyTexture("", gamePane);
        }
    }
}
