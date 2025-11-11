
package PowerUp;

public class ActiveEffect {
    public PowerUp powerUp;
    public long startTime;
    public double duration;

    public ActiveEffect(PowerUp powerUp, long startTime, double duration) {
        this.powerUp = powerUp;
        this.startTime = startTime;
        this.duration = duration;
    }
}