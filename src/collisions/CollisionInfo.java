package collisions;

import GUI.geometry.Point;

/**
 * collision_detection.CollisionInfo - this class objects are storing info about a collision.
 *
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class CollisionInfo {
    //fields
    private Collidable collidable;
    private Point hitPoint;
    private double distFromStart;

    /**
     * constructor with the collision point.
     *
     * @param c the collidable to store its info
     * @param hitPoint the collision point.
     */
    public CollisionInfo(Point hitPoint, Collidable c) {
        this.collidable = c;
        this.hitPoint = hitPoint;
    }
    /**
     *  getter - get the collidable stored on this collision_detection.CollisionInfo object.
     * @return collidable
     */
    public Collidable collisionObject() { //could be named "getCollisionObject()". (name determined by inst).
        return this.collidable;
    }

    /**
     * collisionPoint -- the point at which the collision occurs, the closest to start of line.
     *
     * @return the collision point. if there aren't intersections at all - null.
     */
    public Point collisionPoint() {
        return this.hitPoint;
    }
}