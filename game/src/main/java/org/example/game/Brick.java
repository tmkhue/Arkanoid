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
    protected int hitPoints=1;
    protected String type;
    static final double BRICK_WIDTH = 65;
    static final double BRICK_HEIGHT = 30;

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


    public void applyTexture(String path) {
        try {
            Image img = new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream(path)));
            if (img.isError()) {
                throw new Exception("Image loading error");
            }
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("Cannot load brick image, using default color");
            setFill(RED);
        }
    }

    public void takeHit(Ball ball) {
        double xA, yA; //tọa độ điểm gần tâm ball nhất
        xA = ball.getCenterX();
        yA = ball.getCenterY();

        if (xA < getX()) {
            xA = getX();
        } else if (xA > getX() + getWidth()) {
            xA = getX() + getWidth();
        }
        if (yA < getY()) {
            yA = getY();
        } else if (yA > getY() + getHeight()) {
            yA = getY() + getHeight();
        }
        double dx= xA-ball.getCenterX();
        double dy=yA-ball.getCenterY();
        if (dx*dx+dy*dy <= Math.pow(ball.getRadius(),2) && hitPoints > 0) {
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
                if(brick.isDestroyed()) {
                    it.remove();
                    gamePane.getChildren().remove(brick);
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