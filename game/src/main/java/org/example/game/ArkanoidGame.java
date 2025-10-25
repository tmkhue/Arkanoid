package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

public class ArkanoidGame {
    public static final int WIDTH = 750;
    public static final int HEIGHT = 600;
    public static final int LEFT_BORDER = 25;
    public static final int TOP_BORDER = 80;

    @FXML
    private Pane gamePane;
    @FXML
    private Paddle paddle;
    @FXML
    private Ball ball;

    private Levels levels;

    @FXML
    public void initialize() {
        setBackground();
        levels = new Levels();
        levels.Level1(gamePane);

        gamePane.setFocusTraversable(true);

        gamePane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnZoom(event -> gamePane.requestFocus());
            }
        });

        gamePane.setOnMouseMoved(e -> {
            paddle.setMouseTarget(e.getX());
        });
        gamePane.setOnMouseDragged(e -> {
            paddle.setMouseTarget(e.getX());
        });

        gamePane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.leftPressed = true;
                case RIGHT -> paddle.rightPressed = true;
            }
        });

        gamePane.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.leftPressed = false;
                case RIGHT -> paddle.rightPressed = false;
            }
        });

        // Game loop
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        Platform.runLater(() -> gamePane.requestFocus());
    }

    private void setBackground(){
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream("/org/example/game/Image/background.png"));

            // tạo hình nền
            BackgroundImage bgImage = new BackgroundImage(
                    backgroundImage,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
            );

            gamePane.setBackground(new Background(bgImage));

        } catch (Exception e) {
            System.err.println("⚠️ Could not load background image: " + e.getMessage());
            gamePane.setStyle("-fx-background-color: black;");
        }
    }

    private void update() {
        paddle.move();
        ball.move();

        if (ball.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
            double hitPos = (ball.getCenterX() - paddle.getX()) / paddle.getWidth();
            double bounceAngle = (hitPos - 0.5) * 2;
            ball.setDirectionX(bounceAngle * 2);
            ball.setDirectionY(-Math.abs(ball.getDirectionY()));
        }

        for (Brick brick : Brick.bricks) {
            brick.checkCollision(ball, gamePane);
        }
        levels.removeDestroyedBricks(gamePane);


        if (ball.getCenterY() - ball.getRadius() > HEIGHT) {
            resetGame();
        }
    }

    private void resetGame() {
        ball.setCenterX(WIDTH / 2);
        ball.setCenterY(HEIGHT / 2);
        ball.setDirectionX(3);
        ball.setDirectionY(-3);
        paddle.setX((WIDTH - paddle.getWidth()) / 2);
    }
}