package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import static javafx.scene.paint.Color.RED;

public class Brick extends Rectangle {
    protected int hitPoints=1;
    protected String type;
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
            System.err.println("Cannot load "+ type +" image, using default color");
            setFill(RED);
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

    public void takeHit(Ball ball){
        hitPoints--;
    }

    public boolean checkCollision(Ball ball, Pane gamePane) {
        Iterator<Brick> it = bricks.iterator();
        while (it.hasNext()) {
            Brick brick = it.next();
            if (brick.isHit(ball)) {
                brick.takeHit(ball);
                if(brick.isDestroyed()) {
                    it.remove();
                    gamePane.getChildren().remove(brick);
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
                    if(!ball.isStrong() || brick instanceof UnbreakableBrick){
                        ball.setDirectionX(ball.getDirectionX() * (-1));
                    }
                    return true;
                }
                // Xử lý quả bóng chui vào trong brick từ cạnh trên/dưới
                if (ball.getCenterY() < brick.getY()) {
                    System.out.println("day tren");
                    ball.setCenterY(brick.getY() - ball.getRadius());
                } else {
                    System.out.println("day duoi");
                    ball.setCenterY(brick.getY() + BRICK_HEIGHT + ball.getRadius());
                }
                if(!ball.isStrong() || brick instanceof UnbreakableBrick){
                    ball.setDirectionY(ball.getDirectionY() * (-1));
                }
                return true;
            }
        }
        return false;
    }

    public boolean isDestroyed() {
        System.out.println("hitpoints: " + hitPoints);
        return hitPoints <= 0;
    }
}
