package org.example.game;

public class ActiveEffect {
    PowerUp powerUp;
    long startTime;
    double duration;

    public ActiveEffect(PowerUp powerUp, long startTime, double duration) {
        this.powerUp = powerUp;
        this.startTime = startTime;
        this.duration = duration;
    }
}
