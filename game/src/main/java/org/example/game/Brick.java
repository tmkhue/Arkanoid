package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import java.io.File;

import static javafx.scene.paint.Color.*;

public class Brick extends GameObject {
    protected int hitPoints;
    protected String type;
    static final double BRICK_WIDTH = 45;
    static final double BRICK_HEIGHT = 20;

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

    public Rectangle draw(String path) {
        Rectangle rect = new Rectangle((int) x, (int) y,
                (int) BRICK_WIDTH, (int) BRICK_HEIGHT);

        try {
            Image image = new Image(new File(path).toURI().toString());
            rect.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.err.println("Can not load image: " + path);
            rect.setFill(LAVENDER);
        }
        return rect;
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
        if (distance <= ball.getWidth() / 2 && hitPoints > 0) {
            hitPoints--;
        }
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }
}