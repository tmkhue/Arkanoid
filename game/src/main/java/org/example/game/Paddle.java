package org.example.game;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Paddle extends Rectangle {
    private int speed = 5; // Tốc độ di chuyển khi dùng bàn phím
    public boolean leftPressed = false;
    public boolean rightPressed = false;

    // Biến để lưu trữ vị trí chuột gần nhất
    private double lastMouseX = -1; // -1 biểu thị chưa có input chuột

    // Constructor và các getter/setter khác giữ nguyên
    public Paddle() {
        super(150, 23);
        setX(ArkanoidGame.WIDTH / 2 - getWidth() / 2); // Khởi tạo ở giữa màn hình
        setY(ArkanoidGame.HEIGHT - 40);

        try {
            Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/game/Image/Paddle.png")));
            if (img.isError()) {
                throw new Exception("Image loading error");
            }
            setFill(new ImagePattern(img));
        } catch (Exception e) {
            System.err.println("⚠️ Could not load paddle image: " + e.getMessage());
            setFill(javafx.scene.paint.Color.BLUE);
        }
    }

    // Phương thức này chỉ cập nhật vị trí chuột cuối cùng
    public void setMouseTarget(double mouseX) {
        this.lastMouseX = mouseX;
    }

    // Không cần setKeyboardControl() nữa

    public void move() {
        double currentX = getX();
        double newX = currentX;

        // Tính toán sự di chuyển từ bàn phím
        if (leftPressed) {
            newX -= speed;
        }
        if (rightPressed) {
            newX += speed;
        }

        // Tính toán sự di chuyển từ chuột (nếu có input chuột)
        if (lastMouseX != -1) {
            double paddleCenter = currentX + getWidth() / 2;
            double mouseDelta = lastMouseX - paddleCenter;

            newX += mouseDelta * 0.1; // Hệ số 0.1 để chuột không quá nhạy
        }

        // Giới hạn vị trí của Paddle trong biên game
        double minX = ArkanoidGame.LEFT_BORDER;
        double maxX = ArkanoidGame.WIDTH + ArkanoidGame.LEFT_BORDER - getWidth();
        setX(Math.max(minX, Math.min(newX, maxX)));

        // Reset lastMouseX sau khi xử lý để chuột chỉ tác động khi có sự kiện di chuyển mới
        // Hoặc giữ nguyên nếu muốn chuột override vị trí của bàn phím khi di chuyển
        lastMouseX = -1;
    }
}