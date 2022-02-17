package collisions;

import GUI.geometry.Point;
import GUI.geometry.Rectangle;
import other.Velocity;
import GUI.sprites.Ball;


/**
 * collision_detection.Collidable - This is an interface for collidable items (etc blocks).
 *
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public interface Collidable {
    /**
     * getCollisionRectangle -- Return the "collision shape" of the object.
     * @return rectangle - because all collidable item are rects.
     */
    Rectangle getCollisionRectangle();
    /**
     * hit -- Notify the object that we collided with it at collisionPoint with a given velocity.
     * @param hitter the ball hit that collidable.
     * @param collisionPoint - the collisionPoint.
     * @param currentVelocity the v before the collision.
     * @return other.Velocity - the new velocity expected after the hit (based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}