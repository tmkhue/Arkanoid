package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
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
    public static List<Group> petals;

    Circle flowerCenter = new Circle();

    /**
     * constructor.
     */
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

    /**
     * Override.
     */
    @Override
    public boolean isHit(Ball ball) {
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
//        hitPoints--;
//        move(gamePane, hitPoints);
    }

    @Override
    public void applyTexture(String path, Pane gamePane) {
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
                rotate.setAngle(i * 360.0 / HIT_POINTS);
                petalRect.getTransforms().addAll(rotate);
                if (hitPoints == HIT_POINTS) {
                    Flower.petals.get(i).getChildren().add(petalRect);
                }
            }
            for (int i = 0; i < petals.size(); i++) {
                gamePane.getChildren().add(Flower.petals.get(i));
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
                    flowerCenter.toFront();
                }
            }
        };
        timer.start();
        Platform.runLater(gamePane::requestFocus);
    }

    public void move(Pane gamePane, int i) {
        double speed = 10; // tốc độ rơi (px mỗi frame)
        Rectangle rect = (Rectangle) Flower.petals.get(i).getChildren().getFirst();
        gamePane.getChildren().remove(Flower.petals.get(i));
        Flower.petals.get(i).toFront();
        List<PetalPiece> root = new ArrayList<>();

            for (int j = 0; j < 10; j++) {
                double x = rect.getX() + Math.random() * rect.getWidth();
                double y = rect.getY() + Math.random() * rect.getHeight();
                double angle = Math.random() * 2 * Math.PI;
                double directionX = Math.cos(angle) * speed * Math.random();
                double directionY = Math.sin(angle) * speed * Math.random();
                if (directionX==0&&directionY==0){
                    directionY +=3;
                }
                double radius = Math.random() * 10 + 3;
                PetalPiece r = new PetalPiece(radius, x, y, directionX, directionY + 3);
                r.setFill(new ImagePattern(Flower.petal, 0, 0, 1, 1, true));
                root.add(r);
                gamePane.getChildren().add(r);
            }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double newY = rect.getTranslateY() + speed;
                if (root.isEmpty()) {
                    this.stop();
                }
                for (int j = root.size() - 1; j >= 0; j--) {
                    if (root.get(j).check()) {
                        root.get(j).resolve(gamePane);
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

    static {
        petals = new ArrayList<>();
        for (int i = 0; i < HIT_POINTS; i++) {
            petals.add(new Group());
        }

        try {
            String path = "/org/example/game/Image/CanhHoa_1.png";
            petal = new Image(Objects.requireNonNull(
                Flower.class.getResourceAsStream(path)));
            if (petal.isError()) {
                throw new Exception("Image loading error");
            }
            path = "/org/example/game/Image/face1.png";
            face1 = new Image(Objects.requireNonNull(
                    Flower.class.getResourceAsStream(path)));
            if (Flower.face1.isError()) {
                throw new Exception("Image loading error!");
            }
            path = "/org/example/game/Image/face2.png";
            face2 = new Image(Objects.requireNonNull(
                    Flower.class.getResourceAsStream(path)));
            if (Flower.face2.isError()) {
                throw new Exception("Image loading error!");
            }
        } catch (Exception e) {
            System.err.println("Cannot load images");
        }
    }
}