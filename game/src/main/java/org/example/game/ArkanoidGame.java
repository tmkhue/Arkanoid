package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button; // Import Button
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    private PaddleResizer paddleResizer = new DefaultPaddleResizer();

    private boolean ballAttached = true;

    private List<PowerUp> activePowerUps = new ArrayList<>();
    public static List<ActiveEffect> activeEffects = new ArrayList<>();

    private List<Ball> balls = new ArrayList<>();

    private int score = 0;
    private Text scoreText;

    private Text levelText;

    private int lives = 3;
    private List<ImageView> liveList = new ArrayList<>();

    private static ArkanoidGame instance;
    private AnimationTimer gameTimer; // Lưu trữ gameTimer để có thể dừng nó

    //Thêm GameOver
    private ImageView gameOverImageView;
    private Label finalScoreLabel;
    private Button restartButton; // Nút chơi lại
    private Button menuButton;    // Nút về menu

    public static ArkanoidGame getInstance() {
        return instance;
    }

    @FXML
    public void initialize() {
        instance = this;
        setBackground();

        setLives();

        bricks = new Brick();
        level = new Levels();
        setupScoreText();
        setupLevelText();
        ball.setCenterX(paddle.getX() - paddle.getWidth() / 2);
        ball.setCenterY(paddle.getY() - ball.getRadius());
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
        gameTimer = new AnimationTimer() { // Gán vào gameTimer
            @Override
            public void handle(long now) {
                update();
            }
        };
        gameTimer.start(); // Bắt đầu timer
        Platform.runLater(() -> gamePane.requestFocus());
    }

    private void setupLevelText() {
        try {
            Font gameFont = Font.loadFont(getClass().getResourceAsStream("/org/example/game/Font/Black_Stuff_Bold.ttf"), 50);
            levelText = new Text("LEVEL " + level.getLevel());
            levelText.setFont(gameFont);
        } catch (Exception e) {
            System.err.println("Could not load font, using default.");
            levelText = new Text("LEVEL " + level.getLevel());
            levelText.setFont(Font.font(30));
        }
        levelText.setFill(Color.BLUE);
        levelText.setX(WIDTH/2.5);
        levelText.setY(50);
        gamePane.getChildren().add(levelText);
    }

    public void increaseScore(int amount) {
        score += amount;
        updateScoreText();
    } //cộng điểm cho tính năng của Paddle

    private void setupScoreText() {
        try {
            Font gameFont = Font.loadFont(getClass().getResourceAsStream("/org/example/game/Font/Black_Stuff_Bold.ttf"), 50);
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
                live.setFitWidth(40);
                live.setFitHeight(40);
                live.setX(WIDTH - 150 + i * 45);
                live.setY(15);
                liveList.add(live);
                gamePane.getChildren().add(live);
            }
        } catch (Exception e) {
            System.err.println("Could not lead live image");
        }
    }

    public void addLife() {
        Platform.runLater(() -> {
            if (lives < 3) {
                lives++;
                try {
                    Image liveImg = new Image(getClass().getResourceAsStream("/org/example/game/Image/Hearts.png"));
                    ImageView live = new ImageView(liveImg);
                    live.setFitWidth(40);
                    live.setFitHeight(40);
                    live.setX(WIDTH - 150 + liveList.size() * 45);
                    live.setY(15);
                    liveList.add(live);
                    gamePane.getChildren().add(live);
                } catch (Exception e) {
                    System.err.println("Không thể thêm mạng");
                }
            } else {
                System.out.println("Mạng đã đầy");
            }
        });
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
        ball.toFront();
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
            if (bricks.resolveCollision(b, gamePane)) {
                //sinh PowerUp
                score += 10;
                updateScoreText();

                if (Math.random() < 0.5) {
                    PowerUp p = PowerUpFactory.createPowerUp(b.getCenterX(), b.getCenterY(), gamePane, balls, paddle, paddleResizer, this);
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
        if (bricks.isCleared()) {
            nextLevel();
        }
    }

    private void updateLevelText() {
        levelText.setText("LEVEL " + level.getLevel());
    }

    private void nextLevel() {
        gamePane.getChildren().removeIf(node -> node instanceof Brick
                || node instanceof PowerUp || node instanceof Ball);
        level.next();
        updateLevelText();
        level.start(gamePane, ball);
        resetBall();
    }

    private void loseLife() {
        if (lives > 0) {
            lives--;
            if (!liveList.isEmpty()) {
                ImageView liveToRemove = liveList.remove(liveList.size() - 1);
                gamePane.getChildren().remove(liveToRemove);
            }
            if (lives > 0) {
                resetBall();
            } else {
                gameTimer.stop(); // Dừng game loop
                showGameOverScreen();
            }
        }
    }

    private void updateScoreText() {
        scoreText.setText(String.valueOf(score));
    }

    private void resetBall() {
        balls.clear();
        ballAttached = true;
        Ball newBall = new Ball();
        newBall.setCenterX(paddle.getX() + paddle.getWidth() / 2);
        newBall.setCenterY(paddle.getY() - newBall.getRadius());
        newBall.setDirectionX(0);
        newBall.setDirectionY(-1);
        newBall.setSpeed(3);
        newBall.setGamePane(gamePane);
        balls.add(newBall);
        gamePane.getChildren().add(newBall);
        paddle.setX((WIDTH - paddle.getWidth()) / 2);
    }

    private void showGameOverScreen() {
        gamePane.getChildren().clear(); // Xóa tất cả các phần tử trò chơi

        try {
            Image gameOverImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/EndGame.png")));
            gameOverImageView = new ImageView(gameOverImage);
            gameOverImageView.setFitWidth(800);
            gameOverImageView.setFitHeight(800);
            gameOverImageView.setPreserveRatio(false);

            gameOverImageView.setX((ArkanoidGame.WIDTH - 800) / 2 + ArkanoidGame.LEFT_BORDER);
            gameOverImageView.setY((ArkanoidGame.HEIGHT - 800) / 2 + ArkanoidGame.TOP_BORDER);


            gamePane.getChildren().add(gameOverImageView);

            finalScoreLabel = new Label("" + score);
            try {
                Font gameFont = Font.loadFont(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Font/Black_Stuff_Bold.ttf")), 60);
                finalScoreLabel.setFont(gameFont);
            } catch (Exception e) {
                System.err.println("Không thể tải font cho điểm cuối cùng, sử dụng font mặc định.");
                finalScoreLabel.setFont(Font.font("Arial", 40));
            }
            finalScoreLabel.setTextFill(Color.PINK);
            finalScoreLabel.setLayoutX(WIDTH / 2 );
            finalScoreLabel.setLayoutY(HEIGHT / 2 + 15);

            gamePane.getChildren().add(finalScoreLabel);

            // Thêm nút Chơi lại
            restartButton = new Button("Chơi lại");
            restartButton.setStyle("-fx-font-size: 20px; -fx-background-color: #8A2BE2; -fx-text-fill: white; -fx-padding: 10 20;");
            restartButton.setLayoutX(WIDTH / 2 - restartButton.prefWidth(-1) / 2 - 150);
            restartButton.setLayoutY(HEIGHT / 2 + 100);
            restartButton.setOnAction(event -> restartGame());
            gamePane.getChildren().add(restartButton);

            // Thêm nút Về Menu
            menuButton = new Button("Về Menu");
            menuButton.setStyle("-fx-font-size: 20px; -fx-background-color: #8A2BE2; -fx-text-fill: white; -fx-padding: 10 20;");
            menuButton.setLayoutX(WIDTH / 2 - menuButton.prefWidth(-1) / 2 + 75); // Đặt nút bên phải
            menuButton.setLayoutY(HEIGHT / 2 + 100);
            menuButton.setOnAction(event -> returnToMenu());
            gamePane.getChildren().add(menuButton);

        } catch (Exception e) {
            System.err.println("⚠️ Không thể tải hình ảnh EndGame hoặc font: " + e.getMessage());
        }
    }

    // khởi động lại trò chơi
    private void restartGame() {
        Platform.runLater(() -> {
            gamePane.getChildren().clear(); // Xóa mọi thứ
            // Đặt lại các trạng thái trò chơi
            score = 0;
            lives = 3;
            ballAttached = true;
            balls.clear();
            activePowerUps.clear();
            activeEffects.clear();

            // Khởi tạo lại tất cả
            setBackground();
            setLives();
            level = new Levels(); // Bắt đầu lại từ Level 1
            setupScoreText();
            setupLevelText();

            paddle = new Paddle();
            paddle.setX((WIDTH - paddle.getWidth()) / 2);
            paddle.setY(HEIGHT - 40);
            gamePane.getChildren().add(paddle);

            // Đặt lại bóng và thanh đỡ
            ball = new Ball();
            ball.setCenterX(paddle.getX() + paddle.getWidth() / 2);
            ball.setCenterY(paddle.getY() - ball.getRadius());
            ball.setDirectionX(0);
            ball.setDirectionY(-1);
            ball.setSpeed(3);
            ball.setGamePane(gamePane);
            balls.add(ball);

            gamePane.getChildren().add(ball);
            level.start(gamePane, ball); // Bắt đầu cấp độ 1

            gameTimer.start(); // Khởi động lại game loop
            gamePane.requestFocus();
        });
    }

    //về menu chính
    private void returnToMenu() {
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) gamePane.getScene().getWindow();

                URL fxmlUrl = getClass().getResource("/org/example/game/Menu.fxml");
                if (fxmlUrl == null) {
                    throw new IOException("Cannot find Menu.fxml");
                }
                FXMLLoader loader = new FXMLLoader(fxmlUrl);
                Parent menuRoot = loader.load();
                Scene menuScene = new Scene(menuRoot);

                stage.setScene(menuScene);
                stage.setTitle("Arkanoid - Main Menu");
                stage.show();

            } catch (IOException e) {
                System.err.println("Error returning to menu: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void resetGame() {
        System.out.println("resetGame() called, but it might be redundant now.");
    }
}