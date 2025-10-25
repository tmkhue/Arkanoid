package gameObject;

public class UnbreakableBrick extends Brick{
    public UnbreakableBrick(){
        this.setType("Unbreakable");
    }
    @Override
    public boolean isDestroyed(){
        return false;
    }
}