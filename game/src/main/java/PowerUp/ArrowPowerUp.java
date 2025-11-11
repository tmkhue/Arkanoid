package PowerUp;

import Ball.Ball;
import Brick.Brick;
import Controller.ArkanoidGame;
import Paddle.Paddle;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import java.util.Objects;
import javafx.scene.layout.Pane;
import Paddle.Arrow;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ArrowPowerUp extends PowerUp {
    private AnimationTimer shootingTimer;
    private Pane gamePane;
    private List<Arrow> activeArrows = new ArrayList<>();
    private ArkanoidGame arkanoidGame; // Thêm tham chiếu đến ArkanoidGame

    public ArrowPowerUp(double x, double y, double duration, Pane gamePane) { // Cập nhật constructor
        ArkanoidGame arkanoidGame = ArkanoidGame.getInstance();
        super("Sức mạnh Mũi tên", duration, x, y, "/org/example/game/Image/ArrowPower.png");
        this.gamePane = gamePane;
        this.arkanoidGame = arkanoidGame;
    }

    @Override
    public void applyEffect(Paddle paddle, Ball ball) {
        for (ActiveEffect ae : ArkanoidGame.activeEffects) {
            if (ae.powerUp instanceof ArrowPowerUp) {
                System.out.println("Hiệu ứng Sức mạnh Mũi tên đã kích hoạt.");
                return;
            }
        }
        System.out.println("Áp dụng hiệu ứng Sức mạnh Mũi tên.");

        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/ArrowPaddle.png")));
            paddle.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Không thể tải hình ảnh ArrowPaddle: " + e.getMessage());
            paddle.setFill(javafx.scene.paint.Color.DARKGREEN);
        }

        shootingTimer = new AnimationTimer() {
            private long lastShotTime = 0;
            private final long SHOOT_INTERVAL = 500_000_000L;

            @Override
            public void handle(long now) {
                if (now - lastShotTime >= SHOOT_INTERVAL) {
                    Arrow arrow1 = new Arrow(paddle.getX() + 10, paddle.getY() - 20, gamePane, arkanoidGame); // Truyền arkanoidGame
                    Arrow arrow2 = new Arrow(paddle.getX() + paddle.getWidth() - 30, paddle.getY() - 20, gamePane, arkanoidGame); // Truyền arkanoidGame
                    gamePane.getChildren().addAll(arrow1, arrow2);
                    activeArrows.add(arrow1);
                    activeArrows.add(arrow2);
                    lastShotTime = now;
                }

                Iterator<Arrow> arrowIterator = activeArrows.iterator();
                while(arrowIterator.hasNext()){
                    Arrow arrow = arrowIterator.next();
                    arrow.move();
                    if(arrow.getY() < ArkanoidGame.TOP_BORDER || arrow.checkCollisionWithBricks(Brick.bricks, gamePane)){
                        gamePane.getChildren().remove(arrow);
                        arrowIterator.remove();
                    }
                }
            }
        };
        shootingTimer.start();
    }

    @Override
    public void removeEffect(Paddle paddle, Ball ball) {
        if (shootingTimer != null) {
            shootingTimer.stop();
        }
        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Paddle.png")));
            paddle.setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Không thể tải hình ảnh Paddle gốc: " + e.getMessage());
            paddle.setFill(javafx.scene.paint.Color.BLUE);
        }

        for(Arrow arrow : activeArrows){
            gamePane.getChildren().remove(arrow);
        }
        activeArrows.clear();

        System.out.println("Gỡ bỏ hiệu ứng Sức mạnh Mũi tên và khôi phục thanh trượt.");
    }
}