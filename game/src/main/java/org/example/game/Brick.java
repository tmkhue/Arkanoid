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
    private int directionY = 1;
    protected boolean movable = false;
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

    public Brick(double x, double y, double brickHeight, double brickWidth, boolean movable) {
        super(x, y, brickWidth, brickHeight);
        this.movable = movable;
    }

    public Brick(double x, double y, String type, boolean movable) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        this.type = type;
        this.movable = movable;
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

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
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

    public void moveBrick(double speed, double angle) {
        double dx = Math.cos(Math.toRadians(angle)) * speed;
        double dy = Math.sin(Math.toRadians(angle)) * speed * directionY;
        if (getY() >= ArkanoidGame.HEIGHT / 2.0) {
            directionY *= -1;
            this.setY(this.getY() - 10);
        }
        if (getY() <= 70) {
            directionY *= -1;
            this.setY(this.getY() + 10);
        }

        this.setY(this.getY() + dy);
        this.setX(this.getX() + dx);

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
                    double overlapX = Math.abs(ball.getCenterX() -
                            ((Flower) brick).flowerCenter.getCenterX()) ;
                    double overlapY = Math.abs(ball.getCenterY() -
                            ((Flower) brick).flowerCenter.getCenterY());
                    if (overlapX > overlapY) {
                        ball.setDirectionX(ball.getDirectionX() * (-1));
                        return true;
                    }
                    ball.setDirectionY(ball.getDirectionY() * (-1));
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
                        System.out.println("day trai");
                        ball.setCenterX(brick.getX() - ball.getRadius());
                    } else {
                        System.out.println("day phai");
                        ball.setCenterX(brick.getX() + BRICK_WIDTH + ball.getRadius());
                    }
                    ball.setDirectionX(ball.getDirectionX() * (-1));
                    return;
                }
                // Xử lý quả bóng chui vào trong brick từ cạnh trên/dưới
                if (ball.getCenterY() < brick.getY()) {
                    System.out.println("day tren");
                    ball.setCenterY(brick.getY() - ball.getRadius());
                } else {
                    System.out.println("day duoi");
                    ball.setCenterY(brick.getY() + BRICK_HEIGHT + ball.getRadius());
                }
                ball.setDirectionY(ball.getDirectionY() * (-1));
                return;
            }
        }
    }

    public boolean isDestroyed() {
        System.out.println("hitpoints: " + hitPoints);
        return hitPoints <= 0;
    }
}
