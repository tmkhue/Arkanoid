package Paddle;

public class DefaultPaddleResizer implements PaddleResizer {
    public static final double MAX_PADDLE_WIDTH = 250;
    public static final double MIN_PADDLE_WIDTH = 50;
    public static final double INITIAL_PADDLE_WIDTH = 150;

    @Override
    public void increaseLength(Paddle paddle, double amount) {
        double oldWidth = paddle.getWidth();
        double newWidth = Math.min(paddle.getWidth() + amount, MAX_PADDLE_WIDTH);
        paddle.setWidth(newWidth);

        paddle.setX(paddle.getX() - (newWidth - oldWidth) / 2);
    }

    @Override
    public void decreaseLength(Paddle paddle, double amount) {
        double oldWidth = paddle.getWidth();
        double newWidth = Math.max(paddle.getWidth() - amount, MIN_PADDLE_WIDTH);
        paddle.setWidth(newWidth);
        paddle.setX(paddle.getX() - (newWidth - oldWidth) / 2);
    }

    @Override
    public void resetLength(Paddle paddle) {
        double oldWidth = paddle.getWidth();
        paddle.setWidth(INITIAL_PADDLE_WIDTH);
        paddle.setX(paddle.getX() - (INITIAL_PADDLE_WIDTH - oldWidth) / 2);
    }
}