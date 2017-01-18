package PongGame;

import java.awt.Rectangle;

public class Paddle implements HitBoxObject{

    private double xPos, yPos, width, height;
    private double xVel, yVel;
    
    public Paddle(double xPos, double yPos, double width, double height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.xVel = 0;
        this.yVel = 0;
    }
    
    public void move() {
        this.xPos += xVel;
        this.yPos += yVel;
    }
    
    @Override
    public boolean hasCollisionWith(HitBoxObject hitBoxObject) {
        return this.getHitBox().intersects(hitBoxObject.getHitBox());
    }
    
    @Override
    public Rectangle getHitBox() {
        Rectangle hitBox = new Rectangle();
        hitBox.setRect(xPos-width/2,yPos-height/2,width,2*height);
        return hitBox;
    }

    @Override
    public double getX() {
        return xPos;
    }

    @Override
    public double getY() {
        return yPos;
    }
    
    public double getXVel() {
        return xVel;
    }
    
    public double getYVel() {
        return yVel;
    }
    
    public void setXVel(double vel) {
        xVel = vel;
    }
    
    public void setYVel(double vel) {
        yVel = vel;
    }
    
    public void setX(double xPos) {
        this.xPos = xPos;
    }
    
    public void setY(double yPos) {
        this.yPos = yPos;
    }
    
}
