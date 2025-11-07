package org.example.game;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static javafx.scene.paint.Color.RED;

public class Brick extends Rectangle {
    protected int hitPoints;
    protected String type;
    protected double brickHeight;
    protected double brickWidth;
    // các thuộc tính của brick di chuyển được
    private int directionX = 1, directionY = 1;
    double angle = 90;
    private double minY, minX;
    private double maxY, maxX;
    static boolean isPaused = false;
    static final double BRICK_WIDTH = 60;
    static final double BRICK_HEIGHT = 40;

    public static ArrayList<Brick> bricks = new ArrayList<>();

    public Brick() {
        super();
    }

    public Brick(double x, double y) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
    }

    public Brick(double x, double y, String type) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        this.type = type;
    }

    public Brick(double x, double y, double brickWidth, double brickHeight) {
        super(x, y, brickWidth, brickHeight);
    }

    public Brick(double x, double y, String type, double angle,
                 double minX, double maxX, double minY, double maxY) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        this.type = type;
        this.angle = angle;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void applyTexture(String path, Pane gamePane) {
        try {
            Image img = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(path)));
            if (img.isError()) {
                throw new Exception("Image loading error");
            }
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("Cannot load "+ type +" image, using default color");
            setFill(RED);
        }
        if (!gamePane.getChildren().contains(this)) {
            gamePane.getChildren().add(this);
        }
    }

    public void moveBrick(double speed) {
        if (minY == maxY && maxX == minX) {
            return;
        }
        double dx = Math.cos(Math.toRadians(angle)) * speed * directionX;
        double dy = Math.sin(Math.toRadians(angle)) * speed * directionY;
        if (maxY != minY) {
            if (getY() >= this.maxY) {
                directionY *= -1;
                this.setY(this.getY() - 10);
            }
            if (getY() <= this.minY) {
                directionY *= -1;
                this.setY(this.getY() + 10);
            }
        }
        if (minX != maxX) {
            if (getX() >= this.maxX) {
                directionX *= -1;
                this.setX(this.getX() - 10);
            }
            if (getX() <= this.minX) {
                directionX *= -1;
                this.setX(this.getX() + 10);
            }
        }
        this.setY(this.getY() + dy);
        this.setX(this.getX() + dx);
        for (Brick brick : bricks) {
            if (this.equals(brick)) {
                continue;
            }
            if (this.getBoundsInParent().intersects(brick.getBoundsInParent())) {
                directionX *= -1;
                System.out.println("VA CHAM");
                break;
            }
        }

    }
    public boolean isHit(Ball ball) {
        double xA, yA; //tọa độ điểm gần tâm ball nhất
        xA = ball.getCenterX();
        yA = ball.getCenterY();

        if (ball.getCenterX() <= getX()) {
            xA = getX();
        } else if (ball.getCenterX() > getX() + getWidth()) {
            xA = getX() + getWidth();
        }
        if (ball.getCenterY() <= getY()) {
            yA = getY();
        } else if (ball.getCenterY() > getY() + getHeight()) {
            yA = getY() + getHeight();
        }

        double distanceS = Math.pow(xA - ball.getCenterX(), 2)
                + Math.pow(yA - ball.getCenterY(), 2);
        return distanceS <= Math.pow(ball.getRadius(),2);
    }

    public void takeHit(Ball ball, Pane gamePane) {
        hitPoints--;
    }

    public boolean resolveCollision(Ball ball, Pane gamePane) {

        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            if (!isPaused && Brick.bricks.stream().noneMatch(
                    b -> b instanceof NormalBrick || b instanceof StrongBrick)) {
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(event -> {
                        System.out.println("Tạm dừng");
                    });
                    pause.play();
                    isPaused = true;
            }
            if (brick.isHit(ball)) {
                brick.takeHit(ball, gamePane);
                if(brick.isDestroyed()) {
                    it.remove();
                    gamePane.getChildren().remove(brick);
                    if (brick instanceof Flower) {
                        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                        pause.setOnFinished(event -> {
                            gamePane.getChildren().remove(((Flower) brick).flowerCenter);
                        });
                        pause.play();
                    }
                }
                if (brick instanceof Flower) {
                    double dx = ball.getCenterX() - ((Flower) brick).flowerCenter.getCenterX();
                    double dy = ball.getCenterY() - ((Flower) brick).flowerCenter.getCenterY();
                    double sumR = ball.getRadius() +
                            ((Flower) brick).flowerCenter.getRadius();
                    double overlapX = ball.getCenterX() -
                            ((Flower) brick).flowerCenter.getCenterX();
                    double overlapY = ball.getCenterY() -
                            ((Flower) brick).flowerCenter.getCenterY();
                    if (Math.abs(overlapX) > Math.abs(overlapY)) {
                        ball.setCenterX(ball.getCenterX() + Math.sqrt(dx*dx+dy*dy) -sumR);
                        ball.setDirectionX(ball.getDirectionX() * (-1));
                        return true;
                    }
                    ball.setDirectionY(ball.getDirectionY() * (-1));
                    ball.setCenterY(ball.getCenterY() + Math.sqrt(dx*dx+dy*dy) - sumR);
                    return true;
                }
                // Tính độ chồng (quả bóng chồng lên brick) theo hai trục
                double overlapX = Math.min(ball.getCenterX() + ball.getRadius() - brick.getX(),
                        brick.getX() + brick.getWidth() - (ball.getCenterX() - ball.getRadius()));
                double overlapY = Math.min(ball.getCenterY() + ball.getRadius() - brick.getY(),
                        brick.getY() + brick.getHeight() - (ball.getCenterY() - ball.getRadius()));
                if (overlapX < overlapY) {
//                     Xử lý quả bóng chui vào trong brick từ 2 cạnh bên
                    if (overlapX == ball.getCenterX() + ball.getRadius() - brick.getX()) {
                        ball.setCenterX(brick.getX() - ball.getRadius());
                    } else {
                        ball.setCenterX(brick.getX() + BRICK_WIDTH + ball.getRadius());
                    }
                    if(!ball.isStrong() || brick instanceof UnbreakableBrick)
                        ball.setDirectionX(ball.getDirectionX() * (-1));
                    return true;
                }
                // Xử lý quả bóng chui vào trong brick từ cạnh trên/dưới
                if (ball.getCenterY() < brick.getY()) {
                    ball.setCenterY(brick.getY() - ball.getRadius());
                } else {
                    ball.setCenterY(brick.getY() + BRICK_HEIGHT + ball.getRadius());
                }
                if(!ball.isStrong() || brick instanceof UnbreakableBrick)
                    ball.setDirectionY(ball.getDirectionY() * (-1));
                return true;
            }
        }
        return false;
    }

    public boolean isDestroyed() {
        System.out.println("hitpoints: " + hitPoints);
        return hitPoints <= 0;
    }

    public boolean isCleared() {
        return bricks.isEmpty();
    }
}