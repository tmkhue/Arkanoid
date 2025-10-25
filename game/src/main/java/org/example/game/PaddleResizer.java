package org.example.game;

public interface PaddleResizer {
    void increaseLength(Paddle paddle, double amount);
    void decreaseLength(Paddle paddle, double amount);
    void resetLength(Paddle paddle);
}
