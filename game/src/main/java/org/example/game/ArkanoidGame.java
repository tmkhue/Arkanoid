package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    private PaddleResizer paddleResizer; // Declare the resizer

    private boolean ballAttached = true;

    private List<PowerUp> activePowerUps = new ArrayList<>();
    public static List<ActiveEffect> activeEffects = new ArrayList<>();

    private List<Ball> balls = new ArrayList<>();

    private int score = 0;
    private Text scoreText;

    private int lives = 3;
    private List<ImageView> liveList = new ArrayList<>();

    @FXML
    public void initialize() {
        setBackground();
        setupScoreText();
        setLives();

        bricks = new Brick();
        level = new Levels();
        ball.setGamePane(gamePane);
        level.start(gamePane, ball);
        balls.add(ball);

        gamePane.setFocusTraversable(true);

        gamePane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnZoom(event -> gamePane.requestFocus());
            }
        });

        //Xử lí chuột
        gamePane.setOnMouseMoved(e -> {
            paddle.setMouseTarget(e.getX());
        });
        gamePane.setOnMouseDragged(e -> {
            paddle.setMouseTarget(e.getX());
        });

        // Xử lí bàn phím
        gamePane.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.leftPressed = true;
                case RIGHT -> paddle.rightPressed = true;
                case SPACE -> {
                    if (ballAttached && !balls.isEmpty()) {
                        Ball b = balls.get(0);
                        b.setDirectionX(0);
                        b.setDirectionY(-3);
                        ballAttached = false;
                    }
                }
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

    private void setupScoreText() {
        try {
            Font gameFont = Font.loadFont(getClass().getResourceAsStream("/org/example/game/Font/Black_Stuff.otf"), 50);
            scoreText = new Text("0");
            scoreText.setFont(gameFont);
        } catch (Exception e) {
            System.err.println("Could not load font, using default.");
            scoreText = new Text("0");
            scoreText.setFont(Font.font(30));
        }
        scoreText.setFill(Color.BLUE);
        scoreText.setX(190);
        scoreText.setY(745);
        gamePane.getChildren().add(scoreText);
    }

    private void setLives() {
        try {
            Image liveImg = new Image(getClass().getResourceAsStream("/org/example/game/Image/Hearts.png"));
            for (int i = 0; i < lives; i++) {
                ImageView live = new ImageView(liveImg);
                live.setFitWidth(30);
                live.setFitHeight(30);
                live.setX(WIDTH - 150 + i * 35);
                live.setY(20);
                liveList.add(live);
                gamePane.getChildren().add(live);
            }
        } catch (Exception e) {
            System.err.println("Could not lead live image");
        }
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

        List<Ball> ballsToRemove = new ArrayList<>();

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
                score += 10;
                updateScoreText();

                if (Math.random() < 0.05) {
                    PowerUp p = PowerUpFactory.createPowerUp(b.getCenterX(), b.getCenterY(), gamePane, balls, paddle);
                    activePowerUps.add(p);
                    gamePane.getChildren().add(p);
                }
            }
            if (b.getCenterY() - b.getRadius() > HEIGHT) {
                ballsToRemove.add(b);
                ballIt.remove();
            }
        }

        for (Ball b : ballsToRemove) {
            gamePane.getChildren().remove(b);
            balls.remove(b);
        }

        if (balls.isEmpty()) {
            loseLife();
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
        Iterator<ActiveEffect> effectIt = activeEffects.iterator();
        while (effectIt.hasNext()) {
            ActiveEffect effect = effectIt.next();
            if (now - effect.startTime >= effect.duration * 1000) {
                effect.powerUp.removeEffect(paddle, balls.get(0));
                effectIt.remove();
            }
        }
    }

    private void loseLife() {
        if (lives > 0) {
            lives--;
            if (!liveList.isEmpty()) {
                ImageView liveToRemove = liveList.remove(liveList.size() - 1);
                gamePane.getChildren().remove(liveToRemove);
            }
            if (lives > 0) {
                resetAfterLifeLost();
            } else {
                resetGame();
            }
        }
    }

    private void updateScoreText() {
        scoreText.setText(String.valueOf(score));
    }

    private void resetAfterLifeLost() {
        balls.clear();
        ballAttached = true;
        Ball newBall = new Ball();
        newBall.setCenterX(paddle.getX() + paddle.getWidth() / 2);
        newBall.setDirectionX(0);
        newBall.setDirectionY(-1);
        newBall.setSpeed(3);
        newBall.setGamePane(gamePane);
        balls.add(newBall);
        gamePane.getChildren().add(newBall);
        paddle.setX((WIDTH - paddle.getWidth()) / 2);
    }

    private void resetGame() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Platform.runLater(() -> {
                    gamePane.getChildren().removeIf(node -> node instanceof Brick
                            || node instanceof PowerUp || node instanceof Ball);
                    balls.clear();
                    ballAttached = true;
                    score = 0;
                    updateScoreText();
                    for (ImageView live : liveList) {
                        gamePane.getChildren().remove(live);
                    }
                    liveList.clear();
                    lives = 3;
                    setLives();
                    ball = new Ball();
                    ball.setGamePane(gamePane);
                    level.start(gamePane, ball);
                    balls.add(ball);
                    gamePane.getChildren().add(ball);
                    paddle.setX((WIDTH - paddle.getWidth()) / 2);
                });
            } catch (InterruptedException ignored) {}
        }).start();
    }
}