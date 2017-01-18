package PongGame;

import java.awt.Rectangle;

public interface HitBoxObject {
    
    Rectangle getHitBox();
    
    double getX();

    double getY();
    
    boolean hasCollisionWith(HitBoxObject hitBoxObject);
}
