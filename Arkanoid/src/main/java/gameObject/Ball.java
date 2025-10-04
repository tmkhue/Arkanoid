package gameObject;

import javafx.geometry.Bounds;

public class Ball extends MovableObject{
    private double speed;
    private double directionX;
    private double directionY;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDirectionX() {
        return directionX;
    }

    public void setDirectionX(double directionX) {
        this.directionX = directionX;
    }

    public double getDirectionY() {
        return directionY;
    }

    public void setDirectionY(double directionY) {
        this.directionY = directionY;
    }

    public Ball(double x, double y, double width, double height) {
        super (x, y, width, height);
    }

    /**
     * xử lý hướng nảy bằng độ chênh lệch tâm.
     * Note: dùng tọa độ logic có thể sẽ lệch với scene.
     * @param other
     */
    public void bounceOff(GameObject other) {
        Bounds a = this.getBoundsInParent(); //lấy vị trí thật trong scene
        Bounds b = this.getBoundsInParent();

        double CenterOfA_posX = a.getMinX() + a.getWidth() / 2;
        double CenterOfA_posY = a.getMinY() + a.getHeight() / 2;
        double CenterOfB_posX = b.getMinX() + b.getWidth() / 2;
        double CenterOfB_posY = b.getMinY() + b.getHeight() / 2;

        double xDiff = CenterOfA_posX - CenterOfB_posX;
        double yDiff = CenterOfA_posY - CenterOfB_posY;

        double minDiffX = a.getWidth() / 2 + b.getWidth() /2;
        double minDiffY = a.getHeight() / 2 + b.getHeight() / 2;

        double overlapX = minDiffX - Math.abs(xDiff);
        double overlapY = minDiffY - Math.abs(yDiff);

        if (overlapX < overlapY) { //nảy lên, xuống hoặc nảy chéo nếu dx và dy đều khác 0
            this.dx = -this.dx;
            //Xử dính nhau
            if (xDiff > 0) {
                this.x += overlapX;
            } else {
                this.x -= overlapX;
            }
        } else {
            this.dy = -this.dy;
            if (yDiff > 0) {
                this.y +=overlapY;
            } else {
                this.y -=overlapY;
            }
        }

        //cập nhật trong scene
        view.setLayout(x);
        view.setLayout(y);
    }

    public void checkCollision(GameObject other) {
        Bounds ballBounds = this.getBoundsInParent();
        if (ballBounds.intersects(other.getBoundsInParent())) {
            bounceOff();
        }
    }
}
