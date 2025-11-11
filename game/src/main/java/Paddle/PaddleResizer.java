package Paddle;

public interface PaddleResizer {
    void increaseLength(Paddle paddle, double amount);
    void decreaseLength(Paddle paddle, double amount);
    void resetLength(Paddle paddle);
}
