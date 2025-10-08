package gameObject;

public class Brick extends GameObject {
    private int hitPoints;
    private String type;

    public Brick() {
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Brick(double x, double y, double width, double height, String type) {
        super(x, y, width, height);
        this.type = type;
    }

    public void takeHit(Ball ball) {
        double xA, yA; //tọa độ điểm gần tâm ball nhất
        xA = ball.getCenterX();
        yA = ball.getCenterY();

        if (ball.getCenterX() < getX()) {
            xA = getX();
        } else if (ball.getCenterX() > getX() + getWidth()) {
            xA = getX() + getWidth();
        }
        if (ball.getCenterY() < getY()) {
            yA = getY();
        } else if (ball.getCenterY() > getY() + getHeight()) {
            yA = getY() + getHeight();
        }

        double distance = Math.sqrt(Math.pow(xA - ball.getCenterX(), 2)
                + Math.pow(yA - ball.getCenterY(), 2));
        if (distance <= ball.getWidth() / 2) {
            hitPoints--;
        }
    }

    public boolean isDestroyed() {
        return hitPoints <= 0;
    }
}