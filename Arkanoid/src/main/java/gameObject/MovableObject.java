package gameObject;

public class MovableObject {
    protected double dx;
    protected double dy;

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public MovableObject(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    public void move() {

    }
}
