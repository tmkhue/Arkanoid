package Brick;

import Ball.Ball;
import Ball.PetalPiece;
import Controller.ArkanoidGame;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Flower extends Brick {
    // kích thước bông hoa
    public static final double FLOWER_WIDTH = 261.0528;
    public static final double FLOWER_HEIGHT = 253.6244;
    public static final double FLOWER_RADIUS = 80.2517 / 2.0;
    public static final double PETAL_WIDTH = 78.1547 + 5;
    public static final double PETAL_HEIGHT = 113.8707;
    public static final int HIT_POINTS = 5;

    public static Image face1;
    public static Image face2;
    public static Image petal;
    public static Image petalPiece;
    public static Image ball;
    public List<Node> petals;

    private ArkanoidGame arkanoidGame = ArkanoidGame.getInstance();

    Circle flowerCenter = new Circle();

    public Flower() {
        super();
        this.setHitPoints(HIT_POINTS);
    }

    public Flower(double x, double y, double brickHeight) {
        double scaleY = brickHeight / FLOWER_HEIGHT;
        double brickWidth = scaleY * FLOWER_WIDTH;
        double radius = scaleY * FLOWER_RADIUS;
        super(x, y, brickWidth, brickHeight);
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.setHitPoints(HIT_POINTS);
        flowerCenter = new Circle(x + brickWidth / 2.0, y + brickHeight / 2.0, radius);
    }
    public Flower(double x, double y,double angle,
                  double minX, double maxX, double minY, double maxY)  {
        super(x, y, "F", angle, minX, maxX, minY, maxY);
    }

    public Circle getFlowerCenter() {
        return flowerCenter;
    }

    public void setFlowerCenter(Circle flowerCenter) {
        this.flowerCenter = flowerCenter;
    }

    @Override
    public boolean isHit(Ball ball) {
        if (Levels.countStar< Levels.COUNT_STARS){
            return false;
        }
        if (Brick.bricks.stream().noneMatch(
                b -> b instanceof NormalBrick || b instanceof StrongBrick)) {
            double dx = ball.getCenterX() - flowerCenter.getCenterX();
            double dy = ball.getCenterY() - flowerCenter.getCenterY();
            return dx * dx + dy * dy <= Math.pow(ball.getRadius() + flowerCenter.getRadius(), 2);
        }
        return false;
    }

    @Override
    public void takeHit(Ball ball, Pane gamePane) {
        if (Brick.bricks.stream().noneMatch(
                b -> b instanceof NormalBrick || b instanceof StrongBrick)) {
            hitPoints--;
            move(gamePane, hitPoints);
        }
    }

    @Override
    public void applyTexture(String path, Pane gamePane) {
        petals = new ArrayList<>();
        double centerX = flowerCenter.getCenterX();
        double centerY = flowerCenter.getCenterY();
        double radius = flowerCenter.getRadius();
        // hình chữ nhật quanh cánh hoa
        double height = brickHeight / 2.0;
        double width = PETAL_WIDTH * height / PETAL_HEIGHT;
        for (int i = HIT_POINTS - 1; i >= 0; i--) {
            Rectangle petalRect = new Rectangle(centerX - width / 2, centerY - 20 - height, width, height);
            petalRect.setFill(new ImagePattern(Flower.petal, 0, 0, 1, 1, true));
            // phép xoay
            Rotate rotate = new Rotate();
            rotate.setPivotX(centerX);
            rotate.setPivotY(centerY);
            rotate.setAngle(i * 360.0 / HIT_POINTS+90);
            petalRect.getTransforms().addAll(rotate);
            if (hitPoints == HIT_POINTS) {
                petals.add(petalRect);
            }
        }
        for (int i = 0; i < petals.size(); i++) {
            if (!gamePane.getChildren().contains(petals.get(i))) {
                gamePane.getChildren().add(petals.get(i));
            }
        }
        drawFlowerCenter(gamePane);
    }

    public void drawFlowerCenter(Pane gamePane) {
        ImagePattern pattern1 = new ImagePattern(face1, 0, 0, 1, 1, true);
        ImagePattern pattern2 = new ImagePattern(face2, 0, 0, 1, 1, true);
        if (!gamePane.getChildren().contains(flowerCenter)) {
            gamePane.getChildren().add(flowerCenter);
        }

        final long interval = 500_000_000;
        final long[] lastSwitch = {0};
        final boolean[] toggle = {false};

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastSwitch[0] >= interval) {
                    toggle[0] = !toggle[0];
                    lastSwitch[0] = now;

                    if (toggle[0]) {
                        flowerCenter.setFill(pattern1);
                    } else {
                        flowerCenter.setFill(pattern2);
                    }
                }
            }
        };
        timer.start();
        Platform.runLater(gamePane::requestFocus);
    }

    public void move(Pane gamePane, int i) {
        double speed = 7; // tốc độ rơi (px mỗi frame)
        Node rect = petals.get(i);
        List<PetalPiece> root = new ArrayList<>();
        Bounds bounds = rect.getBoundsInParent();
        for (int j = 0; j < 10; j++) {
            double x = bounds.getMinX() + Math.random() * bounds.getWidth();
            double y = bounds.getMinY() + Math.random() * bounds.getHeight();
            double angle = Math.random() * 2 * Math.PI;
            double directionX = Math.cos(angle) * speed * Math.random();
            double directionY = Math.sin(angle) * speed * Math.random();
            if (directionX==0&&directionY==0){
                directionY +=3;
            }
            double radius = Math.random() * 10 + 15;
            PetalPiece r = new PetalPiece(radius, x, y, directionX, directionY + 3);
            if(r==null){
                System.err.println("ERROR:flower.move()");
                return;
            }
            r.setFill(new ImagePattern(petalPiece, 0, 0, 1, 1, true));
            root.add(r);
            gamePane.getChildren().add(r);
        }
        gamePane.getChildren().remove(rect);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (root.isEmpty()) {
                    this.stop();
                }
                for (int j = root.size() - 1; j >= 0; j--) {
                    if (root.get(j).checkCollisionWithPaddle()
                            || root.get(j).getCenterY()>=600) {
                        root.get(j).resolve(gamePane);
                        gamePane.getChildren().remove(root.get(j));
                        root.remove(j);
                    } else {
                        root.get(j).move();
                    }
                }
            }
        };
        timer.start();
        Platform.runLater(gamePane::requestFocus);
    }
    @Override
    public void removeBrick(Pane gamePane) {
            List<PetalPiece> pp = burstIntoBalls(gamePane);
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (pp.isEmpty()) {
                        this.stop();
                        return;
                    }
                    List<PetalPiece> removedPieces = new ArrayList<>();
                    Iterator<PetalPiece> it = pp.iterator();
                    while (it.hasNext()) {
                        PetalPiece piece = it.next();
                        boolean removed = false;
                        if (!removed && piece.checkCollisionWithPaddle()) {
                            piece.resolve(gamePane);
                            gamePane.getChildren().remove(piece);
                            removedPieces.add(piece);
                            removed = true;
                        }
                        if (!removed && piece.getCenterY() >= 600) {
                            piece.removePiece(gamePane);
                            removedPieces.add(piece);
                            removed = true;
                        }
                        if (!removed)
                            piece.move();

                    }
                    for (int i = 0; i < removedPieces.size(); i++) {
                        pp.remove(removedPieces.get(i));
                    }
                }
            };
            timer.start();
            Platform.runLater(gamePane::requestFocus);
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(event -> {
            gamePane.getChildren().remove(flowerCenter);
        });
        pause.play();
        Brick b = BrickFactory.createBrick('G', 200, 100, 60, 50, 500, 80, 200);
        Brick.bricks.add(b);
        b.applyTexture("", gamePane);
    }

    public List<PetalPiece> burstIntoBalls(Pane gamePane) {
        if (!isDestroyed()) {
            return null;
        }
        List<PetalPiece> pp = new ArrayList<>();
        double radius = flowerCenter.getRadius();
        double centerX = flowerCenter.getCenterX();
        double centerY = flowerCenter.getCenterY();
        double speed = 1;

        for (int i = 0; i < 3; i++) {
            double x = Math.random() * 2.0 * radius + centerX - radius;
            double y = Math.random() * 2.0 * radius + centerY - radius;
            double angle = Math.random() * 2 * Math.PI;
            double directionX = Math.cos(angle) * speed * Math.random();
            double directionY = Math.sin(angle) * speed * Math.random() + 1;
            if (Math.abs(directionX) < 0.2 && Math.abs(directionY) < 0.2) {
                directionY = 2;
            }
            PetalPiece p = new PetalPiece(30, x, y, directionX, directionY + 1);
            p.setFill(new ImagePattern(ball, 0, 0, 1, 1, true));
            pp.add(p);
            gamePane.getChildren().add(p);
        }
        return pp;
    }
    @Override
    public void bounceOff(Ball ball){
        double dx = ball.getCenterX() - flowerCenter.getCenterX();
        double dy = ball.getCenterY() - flowerCenter.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double sumR = ball.getRadius() + flowerCenter.getRadius();

        if (distance == 0) return;

        // Vector đơn vị hướng từ flowerCenter → ball
        double nx = dx / distance;
        double ny = dy / distance;

        double overlap = sumR - distance;
        if (overlap > 0) {
            ball.setCenterX(ball.getCenterX() + nx * overlap);
            ball.setCenterY(ball.getCenterY() + ny * overlap);
        }

        double vn = ball.getDirectionX() * nx + ball.getDirectionY() * ny;
        ball.setDirectionX(ball.getDirectionX() - 2 * vn * nx);
        ball.setDirectionY(ball.getDirectionY() - 2 * vn * ny + 0.5);
    }
    static {
        try {
            String path = "/org/example/game/Image/CanhHoa_1.png";
            petal = new Image(Objects.requireNonNull(
                    Flower.class.getResourceAsStream(path)));
            if (petal.isError()) {
                throw new Exception("petal loading error");
            }
            path = "/org/example/game/Image/stars.png";
            petalPiece = new Image(Objects.requireNonNull(
                    Flower.class.getResourceAsStream(path)));
            if (petal.isError()) {
                throw new Exception("petalPiece loading error");
            }
            path = "/org/example/game/Image/face1.png";
            face1 = new Image(Objects.requireNonNull(
                    Flower.class.getResourceAsStream(path)));
            if (Flower.face1.isError()) {
                throw new Exception("face1 loading error!");
            }
            path = "/org/example/game/Image/face2.png";
            face2 = new Image(Objects.requireNonNull(
                    Flower.class.getResourceAsStream(path)));
            if (Flower.face2.isError()) {
                throw new Exception("face2 loading error!");
            }
            path = "/org/example/game/Image/normalBall.png";
            ball = new Image(Objects.requireNonNull(
                    Flower.class.getResourceAsStream(path)));
            if (ball.isError()) {
                throw new Exception("ball loading error");
            }
        } catch (Exception e) {
            System.err.println("Cannot load images");
        }
    }
}