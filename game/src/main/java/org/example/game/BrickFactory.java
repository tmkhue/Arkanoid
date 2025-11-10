package org.example.game;

import java.util.List;

public class BrickFactory {

    private static final char[] RANDOMS_TYPES = new char[]{'N', 'S', 'U', 'B'};
    public static Brick createBrick(char type,  double angle,double x, double y,
                                    double minX, double maxX, double minY, double maxY){
        Brick brick = null;
        switch (type) {
            case 'N':
                brick = new NormalBrick(x, y, angle, minX, maxX, minY, maxY);
                break;
            case 'S':
                brick = new StrongBrick(x, y, angle, minX, maxX, minY, maxY);
                break;
            case 'U':
                brick = new UnbreakableBrick(x, y, angle, minX, maxX, minY, maxY);
                break;
            case 'B':
                brick = new BoomBrick(x, y, angle, minX, maxX, minY, maxY);
                break;
            case 'F':
                brick = new Flower(x, y, angle, minX, maxX, minY, maxY);
                break;
            case 'G':
                brick = new GiantBrick(x, y, angle, minX, maxX, minY, maxY);
                break;
            case 'R':
                int i = (int) (Math.random()*RANDOMS_TYPES.length);
                brick = createBrick(RANDOMS_TYPES[i], x, y, angle, minX, maxX, minY, maxY);
                break;
            default:
                break;
        }
        return brick;
    }
    public static int getSizeMultiplier(char type){
        if (type == 'G'){
            return 4;
        }
        return 1;
    }
}