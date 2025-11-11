package Controller;

import Ball.Ball;
import Paddle.Paddle;
import PowerUp.PowerUp;
import Paddle.PaddleResizer;
import Paddle.DefaultPaddleResizer;
import PowerUp.ActiveEffect;
import PowerUp.PowerUpFactory;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import PowerUp.TripleBallPowerUp;
import Brick.*;

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

    private PaddleResizer paddleResizer = new DefaultPaddleResizer(); // Declare the resizer

    private boolean ballAttached = true;

    private List<PowerUp> activePowerUps = new ArrayList<>();
    public static List<ActiveEffect> activeEffects = new ArrayList<>();

    protected List<Ball> balls = new ArrayList<>();

    private int score = 0;

    public int getScore() {
        return score;
    }
    public Paddle getPaddle(){
        return paddle;
    }

    public Levels getLevel() {
        return level;
    }

    private Text scoreText;

    private Text comboText;

    private Text levelText;

    private int lives = 3;
    private List<ImageView> liveList = new ArrayList<>();

    private static ArkanoidGame instance;
    private boolean levelChanging = false;


    @FXML
    private Button pauseButton;
    private AnimationTimer gameTimer;
    private boolean isPaused;
    private ImageView handView;
    private Line connect;

    @FXML
    private Button giveUp;

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

        setupPauseImage();
        setupGiveUpButton();

        //Xử lí chuột
        gamePane.setOnMouseMoved(e -> {
            if (!isPaused) {
                paddle.setMouseTarget(e.getX());
            }
            gamePane.requestFocus();
        });
        gamePane.setOnMouseDragged(e -> {
            if (!isPaused) {
                paddle.setMouseTarget(e.getX());
            }
            gamePane.requestFocus();
        });

        // Xử lí bàn phím
        gamePane.setOnKeyPressed(e -> {

            switch (e.getCode()) {
                case LEFT -> paddle.leftPressed = true;
                case RIGHT -> paddle.rightPressed = true;
                case ESCAPE -> {
                    showGameOverScreen();
                    isPaused = true;
                }
                case SPACE -> handlePauseButton();
                case UP -> {
                    if (ballAttached && !balls.isEmpty()) {
                        Ball b = balls.get(0);
                        b.setDirectionX(0);
                        b.setDirectionY(-3);
                        ballAttached = false;
                    }
                }
            }
            gamePane.requestFocus();
        });

        gamePane.setOnKeyReleased(e -> {
            if (isPaused) return; // Không xử lý phím khi tạm dừng

            switch (e.getCode()) {
                case LEFT -> paddle.leftPressed = false;
                case RIGHT -> paddle.rightPressed = false;
            }
            gamePane.requestFocus();
        });

        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

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

    private void setupGiveUpButton() {
        Image Img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/backToMenu.png")));
        ImageView View = new ImageView(Img);
        View.setFitHeight(50);
        View.setFitWidth(50);
        giveUp.setGraphic(View);
        giveUp.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
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
        levelText.setX(WIDTH / 2.5);
        levelText.setY(50);
        gamePane.getChildren().add(levelText);
    }

    public void increaseScore(int amount) {
        score += amount;
        updateScoreText();
    } //cộng điểm cho tính năng của Paddle

    public void setupComboText(int combo) {
        if (comboText == null) {
            try {
                Font gameFont = Font.loadFont(getClass().getResourceAsStream("/org/example/game/Font/Black_Stuff_Bold.ttf"), 50);
                comboText = new Text("COMBO X " + combo);
                comboText.setFont(gameFont);
            } catch (Exception e) {
                System.err.println("Could not load font, using default.");
                comboText = new Text("COMBO X " + combo);
                scoreText.setFont(Font.font(30));
            }
            comboText.setFill(Color.BLUE);
            comboText.setX(400);
            comboText.setY(745);
            comboText.setVisible(false);
            gamePane.getChildren().add(comboText);
        }
        comboText.setText("COMBO X" + combo);
        comboText.setVisible(true);
        PauseTransition pt = new PauseTransition(Duration.seconds(2));
        pt.setOnFinished(e -> comboText.setVisible(false));
        pt.play();
    }

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
        scoreText.setX(150-scoreText.getLayoutX()/2);
        scoreText.setY(760);
        //scoreText.setLayoutX(150);

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

    private void setBackground() {
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

    @FXML
    private void handlePauseButton() {
        isPaused = !isPaused;
        if (isPaused) {
            handView.setLayoutX(balls.get(0).getCenterX() - 20);
            handView.setLayoutY(balls.get(0).getCenterY() - 20);
        } else {
            handView.setLayoutX(590);
            handView.setLayoutY(660);
        }
        connect.setEndX(handView.getLayoutX() + 20);
        connect.setEndY(handView.getLayoutY() + 50);
        handView.toFront();
        balls.get(0).toFront();
    }

    private void setupPauseImage() {
        try {
            Image Img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/pause.png")));
            ImageView View = new ImageView(Img);
            View.setFitHeight(128);
            View.setFitWidth(150);
            pauseButton.setGraphic(View);
            pauseButton.setStyle("-fx-background-color: transparent; -fx-padding: 0;");

            Image icon = new Image(getClass().getResourceAsStream("/org/example/game/Image/hand.png"));
            handView = new ImageView(icon);
            handView.setFitWidth(40);
            handView.setFitHeight(50);
            handView.setLayoutX(590);
            handView.setLayoutY(660);

            connect = new Line(pauseButton.getLayoutX(), pauseButton.getLayoutY() + 128 / 2, handView.getLayoutX() + 20, handView.getLayoutY() + 50);
            connect.setStroke(Color.rgb(110, 39, 113));
            connect.setStrokeWidth(3);

            gamePane.getChildren().add(handView);
            gamePane.getChildren().add(connect);
        } catch (Exception e) {
            System.err.println("Could not load Pause Icon Image. Skipping moving icon feature.");
            handView = null;
        }
    }

    private void update() {
        if (isPaused) return;

        ball.toFront();
        paddle.move();

        if (ballAttached && !balls.isEmpty()) {
            Ball attachedBall = balls.get(0);
            attachedBall.setCenterX(paddle.getX() + paddle.getWidth() / 2);
            attachedBall.setCenterY(paddle.getY() - attachedBall.getRadius());
        }

        for (Brick brick : Brick.bricks) {
            brick.moveBrick(2);
            if (!(brick instanceof Flower)) {
                brick.toFront();
            }
        }
        List<Ball> ballsToRemove = new ArrayList<>();
        Iterator<Ball> ballIt = balls.iterator();
        while (ballIt.hasNext()) {
            Ball b = ballIt.next();
            b.move();
            if (b.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
                b.resetCombo();
                double hitPos = (b.getCenterX() - paddle.getX()) / paddle.getWidth();
                double bounceAngle = (hitPos - 0.5) * 2;
                b.setDirectionX(bounceAngle * 2);
                b.setDirectionY(-Math.abs(b.getDirectionY()));
            }

            if (bricks.resolveCollision(b, gamePane)) {
                if (Math.random() < 0.05) {
                    PowerUp p = PowerUpFactory.createPowerUp(b.getCenterX(), b.getCenterY(), gamePane, balls, paddle, paddleResizer);
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
            boolean replaced = false;
            for (ActiveEffect ae : activeEffects) {
                if (ae.powerUp instanceof TripleBallPowerUp) {
                    TripleBallPowerUp tpb = (TripleBallPowerUp) ae.powerUp;
                    Ball newMain = tpb.promoteNewMainBall(b);
                    if (newMain != null) {
                        balls.add(newMain);
                        gamePane.getChildren().add(newMain);
                        replaced = true;
                        break;
                    }
                }
            }
            if (!replaced) {
                balls.remove(b);
                gamePane.getChildren().remove(b);
            }
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
            if (now - effect.startTime >= effect.duration * 1000 && balls.size() > 0) {
                effect.powerUp.removeEffect(paddle, balls.get(0));
                effectIt.remove();
            }
        }
        if (bricks.isCleared() && !levelChanging) {
            levelChanging = true;
            nextLevel();
        }
    }

    private void updateLevelText() {
        levelText.setText("LEVEL " + level.getLevel());
    }

    private void nextLevel() {
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            gamePane.getChildren().removeIf(node -> node instanceof Brick
                    || node instanceof PowerUp || node instanceof Ball);
            level.next();
            updateLevelText();
            level.start(gamePane, ball);
            resetBall();
            levelChanging = false;
        });
        pause.play();
    }

    public void loseLife() {
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

    @FXML
    private void showGameOverScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/org/example/game/GameOver.fxml"));
            Parent gameOverRoot = loader.load();
            Scene gameOverScene = new Scene(gameOverRoot);
            Stage primaryStage = (Stage) gamePane.getScene().getWindow();
            primaryStage.setScene(gameOverScene);
            primaryStage.setTitle("Game Over!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateScoreText() {
        scoreText.setText(String.valueOf(score));
    }

    private void resetBall() {
        for (Ball b : balls) {
            gamePane.getChildren().remove(b);
        }
        balls.clear();

        ballAttached = true;

        Ball newBall = new Ball();
        newBall.setGamePane(gamePane);

        paddle.setX((WIDTH - paddle.getWidth()) / 2);

        newBall.setCenterX(paddle.getX() + paddle.getWidth() / 2);
        newBall.setCenterY(paddle.getY() - newBall.getRadius());

        newBall.setDirectionX(0);
        newBall.setDirectionY(0);
        System.out.println("toc do moi" + newBall.getSpeed());

        balls.add(newBall);
        gamePane.getChildren().add(newBall);
    }

}