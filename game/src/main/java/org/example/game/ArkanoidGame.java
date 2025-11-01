// ArkanoidGame.java không cần thay đổi gì thêm.
// Các event listener cho chuột và bàn phím hoạt động độc lập và chỉ cập nhật các biến
// trong Paddle. Lớp Paddle sẽ quyết định cách sử dụng chúng.

package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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

    private Brick bricks;
    private Levels level;

    private PaddleResizer paddleResizer;

    private List<PowerUp> activePowerUps = new ArrayList<>();
    public static List<ActiveEffect> activeEffects = new ArrayList<>();

    private List<Ball> balls = new ArrayList<>();

    @FXML
    public void initialize() {
        setBackground();
        bricks = new Brick();
        level = new Levels();
        level.start(gamePane, ball);
        balls.add(ball);

        gamePane.setFocusTraversable(true);

        gamePane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnZoom(event -> gamePane.requestFocus());
            }
        });

        // Xử lý chuột
        gamePane.setOnMouseMoved(e -> {
            paddle.setMouseTargetX(e.getX());
        });
        gamePane.setOnMouseDragged(e -> {
            paddle.setMouseTargetX(e.getX());
        });


        // Xử lý bàn phím
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
            Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/background.png")));

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

        Iterator<Ball> ballIt = balls.iterator();
        while (ballIt.hasNext()) {
            Ball b = ballIt.next();
            b.move();
            if (b.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
                double hitPos = (b.getCenterX() - paddle.getX()) / paddle.getWidth();
                double bounceAngle = (hitPos - 0.5) * 2;
                b.setDirectionX(bounceAngle * 2);
                b.setDirectionY(-Math.abs(b.getDirectionY()));
            }
            if (bricks.checkCollision(b, gamePane)) {
                //sinh PowerUp
                if (Math.random() < 0.2) {
                    PowerUp p = PowerUpFactory.createPowerUp(b.getCenterX(), b.getCenterY(), gamePane, balls, paddle);
                    activePowerUps.add(p);
                    gamePane.getChildren().add(p);
                }
            }
            if (b.getCenterY() - b.getRadius() > HEIGHT) {
                gamePane.getChildren().remove(b);
                ballIt.remove();
            }
        }

        if (balls.isEmpty()) {
            resetGame();
        }

        Iterator<PowerUp> it = activePowerUps.iterator();
        while (it.hasNext()) {
            PowerUp p = it.next();
            p.move();
            if (p.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
                p.applyEffect(paddle, balls.get(0));
                activeEffects.add(new ActiveEffect(p, System.currentTimeMillis(), p.getDuration()));
                gamePane.getChildren().remove(p);
                it.remove();
            } else if (p.getY() > HEIGHT) {
                gamePane.getChildren().remove(p);
                it.remove();
            }
        }
        long now = System.currentTimeMillis();

        List<ActiveEffect> effectsToRemove = new ArrayList<>();
        List<ActiveEffect> effectsToUpdate = new ArrayList<>();

        for (ActiveEffect effect : activeEffects) {
            if (now - effect.startTime >= effect.duration * 1000) {
                effect.powerUp.removeEffect(paddle, balls.get(0));
                effectsToRemove.add(effect);
            } else {
                // Nếu hiệu ứng là PaddleResizePowerUp, gọi updateEffect()
                if (effect.powerUp instanceof PaddleResizePowerUp) {
                    ((PaddleResizePowerUp) effect.powerUp).updateEffect(paddle);
                }
            }
        }

        activeEffects.removeAll(effectsToRemove); // Xóa các hiệu ứng đã hết hạn

    }

    private void resetGame() {
        balls.clear();
        Ball newBall = new Ball();
        newBall.setCenterX(WIDTH / 2);
        newBall.setCenterY(HEIGHT / 2);
        newBall.setDirectionX(3);
        newBall.setDirectionY(-3);
        balls.add(newBall);
        gamePane.getChildren().add(newBall);
        paddle.setX((WIDTH - paddle.getWidth()) / 2);
    }
}