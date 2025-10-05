package org.example.game;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Objects;

public class Ball extends Circle{
    private double speed;
    private double directionX = 3;
    private double directionY = -3;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirectionX() {
        return directionX;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }

    public Ball() {
        super(30);
        setCenterX(300);
        setCenterY(250);

        try {
            Image img = new Image(getClass().getResourceAsStream("/org/example/game/Image/normalBall.png"));
            setFill(new ImagePattern(img,0,0,1,1,true));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load ball image. Using default color.");
            setStyle("-fx-fill: white;");
        }
    }

    public void move(){
        this.setCenterX(this.getCenterX() + directionX);
        this.setCenterY(this.getCenterY() + directionY);

        if (getCenterX() - getRadius() <= ArkanoidGame.LEFT_BORDER || getCenterX() + getRadius() >= ArkanoidGame.WIDTH + ArkanoidGame.LEFT_BORDER) {
            directionX *= -1;
        }
        if (getCenterY() - getDirectionX() <= ArkanoidGame.TOP_BORDER) {
            directionY *= -1;
        }
    }
}