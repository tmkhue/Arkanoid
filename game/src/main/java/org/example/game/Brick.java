package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static javafx.scene.paint.Color.*;

public class Brick extends Rectangle {
    protected int hitPoints;
    protected String type;
    static final double BRICK_WIDTH = 45;
    static final double BRICK_HEIGHT = 20;

    public static ArrayList<Brick> bricks = new ArrayList<>();

    public Brick() {
        super(BRICK_WIDTH, BRICK_HEIGHT);
        this.hitPoints = 1;
        this.type = "normal";
    }

    public Brick(double x, double y) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        this.hitPoints = 1;
        this.type = "normal";
    }

    public Brick(double x, double y, String type) {
        super(x, y, BRICK_WIDTH, BRICK_HEIGHT);
        this.type = type;
        this.hitPoints = 1;
    }

    public Brick(double x, double y, double brickWidth, double brickHeight) {
        super(x, y, brickWidth, brickHeight);
        this.hitPoints = 1;
        this.type = "normal";
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

    public static Brick create(double x, double y, String type) {
        return switch (type) {
            case "strong" -> new StrongBrick(x, y);
            case "unbreakable" -> new UnbreakableBrick(x, y);
            default -> new Brick(x, y, type);
        };
    }

    public static Brick create(double x, double y) {
        return create(x, y, "normal");
    }

    public void applyTexture() {
        try {
            Image img = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/org/example/game/Image/normalBrick.png")));
            if (img.isError()) {
                throw new Exception("Image loading error");
            }
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("Cannot load brick image, using default color");
            setFill(LAVENDER);
        }
    }

    public void takeHit(Ball ball) {
        double xA, yA; //tọa độ điểm gần tâm ball nhất
        xA = ball.getCenterX();
        yA = ball.getCenterY();

        if (ball.getCenterX() < getX()) {
            xA = getX();
        } else if (ball.getCenterX() > getX() + getWidth()) {
            xA = getX() + getWidth();
        }
        if (ball.getCenterY() < getY()) {
            yA = getY();
        } else if (ball.getCenterY() > getY() + getHeight()) {
            yA = getY() + getHeight();
        }

        double distance = Math.sqrt(Math.pow(xA - ball.getCenterX(), 2)
                + Math.pow(yA - ball.getCenterY(), 2));
        if (distance <= ball.getRadius() / 2 && hitPoints > 0) {
            hitPoints--;
            System.out.println("hitted " + hitPoints);
        }
    }

    public void checkCollision(Ball ball, Pane gamePane) {
        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            if (brick.getBoundsInParent().intersects(ball.getBoundsInParent())) {
                brick.takeHit(ball);
                if(!brick.isDestroyed()) {
                    it.remove();
                    gamePane.getChildren().remove(brick);
                }
                ball.setDirectionY(ball.getDirectionY() * (-1));
                return;
            }
        }
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }
}