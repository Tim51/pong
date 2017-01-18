package PongGame;

import java.awt.Rectangle;

class Ball implements HitBoxObject {
    
    private double xPos, yPos, radius;
    private double spin;
    private double speed, direction;

    public Ball(double xPos, double yPos, double radius) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.speed = 0;
        this.direction = 0;
        this.spin = 0;
    }
    
    public void move() {
        xPos += getXVel();
        yPos += getYVel();
        direction += spin*Math.PI*speed;
        this.spin /= speed;
    }
    
    public void setRandomVelocity() {
        speed = 3;
        direction = (Math.floor(2*Math.random()))*Math.PI;
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public void setDirection(double direction) {
        this.direction = direction;
    }
    
    public double getDirection() {
        return direction;
    }
    
    public double getSpin() {
        return spin;
    }
    
    public void setSpin(double amount) {
        spin = amount;
    }

    @Override
    public Rectangle getHitBox() {
        Rectangle hitBox = new Rectangle();
        hitBox.setRect(xPos-radius,yPos-radius,2*radius,2*radius);
        return hitBox;
    }
    
    @Override
    public boolean hasCollisionWith(HitBoxObject hitBoxObject) {
        return this.getHitBox().intersects(hitBoxObject.getHitBox());
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
        return speed*Math.cos(direction);
    }
    
    public double getYVel() {
        return speed*Math.sin(direction);
    }
    
}
