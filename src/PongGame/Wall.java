package PongGame;


import java.awt.Rectangle;

public class Wall implements HitBoxObject{

    private final int x, y;
    private final int width, height;
    
    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public Rectangle getHitBox() {
        return new Rectangle(x,y,width,height);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public boolean hasCollisionWith(HitBoxObject hitBoxObject) {
        return this.getHitBox().intersects(hitBoxObject.getHitBox());
    }
    
}
