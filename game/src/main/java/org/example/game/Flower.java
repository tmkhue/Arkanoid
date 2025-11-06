package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.RED;

public class Flower extends Brick {
    // kích thước bông hoa
    public static final double FLOWER_WIDTH = 261.0528;
    public static final double FLOWER_HEIGHT = 253.6244;
    public static final double FLOWER_RADIUS = 80.2517 / 2.0;
    public static final double PETAL_WIDTH = 78.1547 + 5;
    public static final double PETAL_HEIGHT = 113.8707;
    public static final int HIT_POINTS = 6;

    public static List<Image> centers;
    public static List<Group> petals;
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

    public Circle getFlowerCenter() {
        return flowerCenter;
    }

    public void setFlowerCenter(Circle flowerCenter) {
        this.flowerCenter = flowerCenter;
    }

    @Override
    public boolean isHit(Ball ball) {
        double dx = ball.getCenterX() - flowerCenter.getCenterX();
        double dy = ball.getCenterY() - flowerCenter.getCenterY();
        return dx * dx + dy * dy - 1 <= Math.pow(ball.getRadius() + flowerCenter.getRadius(), 2);
    }

    @Override
    public void takeHit(Ball ball, Pane gamePane) {
        hitPoints--;
        move(gamePane, hitPoints);
    }

    @Override
    public void applyTexture(String path, Pane gamePane) {
        path = "C:\\Users\\dinh_tuyet_anh\\Downloads\\CanhHoa_1.png";
//        Image img = new Image(Objects.requireNonNull(
//                getClass().getResourceAsStream(path)));
        try {
            Image img = new Image("file:" + path);
            if (img.isError()) {
                throw new Exception("Image loading error!");
            }
            double centerX = flowerCenter.getCenterX();
            double centerY = flowerCenter.getCenterY();
            double radius = flowerCenter.getRadius();
            // hình chữ nhật quanh cánh hoa
            double height = brickHeight / 2.0;
            double width = PETAL_WIDTH * height / PETAL_HEIGHT;
            for (int i = HIT_POINTS - 1; i >= 0; i--) {
                Rectangle petalRect = new Rectangle(centerX - width / 2, centerY - 20 - height, width, height);
                petalRect.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
                // phép xoay
                Rotate rotate = new Rotate();
                rotate.setPivotX(centerX);
                rotate.setPivotY(centerY);
                rotate.setAngle(i * 360.0 / HIT_POINTS);
                petalRect.getTransforms().addAll(rotate);
                System.out.println("VẼ HOA");
                if (hitPoints == HIT_POINTS) {
                    Flower.petals.get(i).getChildren().add(petalRect);
                }
            }
            for (int i = 0; i < petals.size(); i++) {
                gamePane.getChildren().add(Flower.petals.get(i));
            }
        } catch (Exception e) {
            System.err.println("Cannot load image, using default color");
            setFill(RED);
        }
        ImagePattern pattern = new ImagePattern(centers.getFirst(), 0, 0, 1, 1, true);
        flowerCenter.setFill(pattern);
        gamePane.getChildren().remove(flowerCenter);
        gamePane.getChildren().add(flowerCenter);
        flowerCenter.toFront();
    }

    public void move(Pane gamePane, int i) {
        double speed = 10; // tốc độ rơi (px mỗi frame)
        Rectangle rect = (Rectangle) petals.get(i).getChildren().getFirst();
        gamePane.getChildren().remove(petals.get(i));
        petals.get(i).toFront();
        List<PetalPiece> root = new ArrayList<>();
        String path = "C:\\Users\\dinh_tuyet_anh\\Downloads\\CanhHoa_1.png";
        try {
            Image img = new Image("file:" + path);
            if (img.isError()) {
                throw new Exception("Image loading error!");
            }
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
                r.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
                root.add(r);
                gamePane.getChildren().add(r);
            }
        } catch (Exception e) {
            System.err.println("Cannot load image, using default color");
            setFill(RED);
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
        Platform.runLater(() -> gamePane.requestFocus());
    }

    static {
        petals = new ArrayList<>();
        for (int i = 0; i < HIT_POINTS; i++) {
            petals.add(new Group());
        }
        centers = new ArrayList<>();
        try {
            Image center = new Image("file:C:\\Users\\dinh_tuyet_anh\\Downloads\\Two_faces.png");
            double centerWidth = center.getWidth() / 2;
            double centerHeight = center.getHeight();

            PixelReader reader = center.getPixelReader();

            for (int x = 0; x < 2; x++) {
                WritableImage frame = new WritableImage(reader,
                        (int) (x * centerWidth), 0,
                        (int) centerWidth, (int) centerHeight);
                centers.add(frame);
            }
        } catch (Exception e) {
            System.err.println("Cannot load image");
        }
    }
}