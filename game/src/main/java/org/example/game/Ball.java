package org.example.game;

import javafx.animation.FadeTransition;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Ball extends Circle {
    private double directionX = 1;
    private double directionY = -1;
    private double speed = 3;

    private boolean isStrong = false;

    public boolean isStrong() {
        return isStrong;
    }

    public void setStrong(boolean strong) {
        isStrong = strong;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private List<Circle> shadowList = new ArrayList<>();

    private Pane gamePane;

    public void setGamePane(Pane gamePane) {
        this.gamePane = gamePane;
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

    private static Image getRoundedImage(Image image, double radius) {
        Circle clip = new Circle(image.getWidth() / 2, image.getHeight() / 2, radius);
        ImageView imageView = new ImageView(image);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        return imageView.snapshot(parameters, null);
    }

    public Ball() {
        super(15);
        setCenterX(300);
        setCenterY(250);
        try {
            double diameter = getRadius() * 4.5;
            Image img = new Image(getClass().getResourceAsStream("/org/example/game/Image/normalBall.png"), diameter, diameter, true, true);
            Image roundedImg = getRoundedImage(img, this.getRadius());
            setFill(new ImagePattern(roundedImg));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load ball image. Using default color.");
            setStyle("-fx-fill: white;");
        }
    }

    public void move() {
        double nextX = this.getCenterX() + directionX * speed;
        double nextY = this.getCenterY() + directionY * speed;

        shadowEffect(this);

        if (nextX - getRadius() <= ArkanoidGame.LEFT_BORDER || nextX + getRadius() >= ArkanoidGame.WIDTH + ArkanoidGame.LEFT_BORDER) {
            directionX *= -1;
            this.setCenterX(this.getCenterX() + directionX * speed);
        } else {
            this.setCenterX(this.getCenterX() + directionX * speed);
        }
        if (getCenterY() - getDirectionX() <= ArkanoidGame.TOP_BORDER) {
            directionY *= -1;
            this.setCenterY(this.getCenterY() + directionY * speed);
        } else {
            this.setCenterY(this.getCenterY() + directionY * speed);
        }
    }

    public void shadowEffect(Ball ball) {
        if (gamePane == null) {
            return;
        }
        Circle shadow = new Circle(getRadius());
        shadow.setCenterX(getCenterX());
        shadow.setCenterY(getCenterY());
        shadow.setFill(getFill());
        shadow.setOpacity(0.1);
        gamePane.getChildren().add(1, shadow);
        shadow.toFront();
        shadowList.add(shadow);

        FadeTransition fade = new FadeTransition(Duration.millis(150), shadow);
        fade.setFromValue(0.1);
        fade.setToValue(0);
        fade.setOnFinished(e -> {
            gamePane.getChildren().remove(shadow);
            shadowList.remove(shadow);
        });
        fade.play();
    }
}