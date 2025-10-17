package gameObject;

public class PowerUp extends GameObject {
    protected String type;
    protected double duration;

    public String getType() {
        return type;
    }

    public void setType(Stringg type) {
        this.type = type;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public PowerUp(double x, double y, double width, double height, String type, double duration) {
        super(x, y, width, height);
        this.type = type;
        this.duration = duration;
    }

    public void applyEffect() {

    }

    public void removeEffect() {

    }
}
