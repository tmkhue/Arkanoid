package Brick;

import javafx.scene.layout.Pane;

public class UnbreakableBrick extends Brick {
    public UnbreakableBrick() {
        super();
        this.setHitPoints(Integer.MAX_VALUE);
    }

    public UnbreakableBrick(double x, double y) {
        super(x, y, "U");
        this.setHitPoints(Integer.MAX_VALUE);
    }
    public UnbreakableBrick(double x, double y, double angle,
                            double minX, double maxX,double maxY, double minY){
        super(x, y, "U", angle, minX, maxX, minY, maxY);
        this.setHitPoints(Integer.MAX_VALUE);
    }
    @Override
    public boolean isDestroyed() {
        if (Brick.bricks.stream().noneMatch(
                b -> b instanceof NormalBrick || b instanceof StrongBrick)) {
            return true;
        }
        return false;
    }

    public void applyTexture(String path, Pane gamePane) {
        path="/org/example/game/Image/UnbreakableBrick.png";
        super.applyTexture(path, gamePane);
    }
}