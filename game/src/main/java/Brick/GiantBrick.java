package Brick;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GiantBrick extends StrongBrick {
    public GiantBrick() {
        super();
    }

    public GiantBrick(double x, double y, double angle,
                      double minX, double maxX, double minY, double maxY) {
        super(x, y, angle, minX, maxX, minY, maxY);
        this.setType("G");
        this.setHeight((BRICK_HEIGHT) * BrickFactory.getSizeMultiplier('G')+5*3);
        this.setWidth((BRICK_WIDTH) * BrickFactory.getSizeMultiplier('G')+5*3);
    }

    @Override
    public void removeBrick(Pane gamePane) {
        List<Brick> brickList = new ArrayList<>();
        this.setTranslateX(this.getX());
        this.setTranslateY(this.getY());
        double xB = this.getX();
        double yB = this.getY();
        gamePane.getChildren().remove(this);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                double x = xB + (BRICK_WIDTH + 5) * j;
                double y = yB + (BRICK_HEIGHT + 5) * i;
                Brick b = BrickFactory.createBrick('R', x, y, 0, 0, 0, 0, 0);
                if (b== null){
                    b = BrickFactory.createBrick('N', x, y, 0, 0, 0, 0, 0);
                }
                brickList.add(b);
            }
        }
        List<Integer> list = new ArrayList<>();
        while (list.size() < 8) {
            int index = (int) (Math.random() * brickList.size());
            if (!list.contains(index)) {
                list.add(index);
            }
        }
        for (int index : list) {
            Brick b = brickList.get(index);
            Brick.bricks.add(b);
            b.applyTexture("", gamePane);

            double targetX = b.getX();
            double targetY = b.getY();

            b.setX(getX() + getWidth() / 2.0);
            b.setY(getY() + getHeight() / 2.0);

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    double dx = targetX - b.getX();
                    double dy = targetY - b.getY();

                    b.setX(b.getX() + dx / 20.0);
                    b.setY(b.getY() + dy / 20.0);

                    if (Math.abs(dx) < 1 && Math.abs(dy) < 1) {
                        b.setX(targetX);
                        b.setY(targetY);
                        stop();
                    }
                }
            };
            timer.start();
        }
        Platform.runLater(() -> gamePane.requestFocus());
    }
}