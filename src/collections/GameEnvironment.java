package collections;

import collisions.Collidable;
import collisions.CollisionInfo;
import GUI.geometry.Line;
import GUI.geometry.Point;
import GUI.geometry.Rectangle;
import GUI.sprites.Paddle;

import java.util.LinkedList;
import java.util.List;
/**
 * collections.GameEnvironment - Class that store and handle all the collidables in game.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class GameEnvironment {
    //fields
    private List<Collidable> collidables = new LinkedList<>(); //list of all collidables in game environment
    private Paddle paddle;

    /**
     * addCollidable -- add the given collidable to the environment.
     * @param c collidable.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * remove the given collidable from the environment.
     * @param c .
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * set the paddle field. made this to be able to get the paddle easily.
     * @param p paddle of this GE.
     */
    public void setPaddle(Paddle p) {
        this.paddle = p;
    }

    /**
     * getter.
     * @return this gameEnvironment paddle
     */
    public Paddle getPaddle() {
        return paddle;
    }

    /**
     * getter.
     * @return the collidables list.
     */
    public List<Collidable> getCollidables() {
        return collidables;
    }
    /**
     * getClosestCollision -- return the information about the closest collision that is going to occur.
     * <p>Assume an object moving from line.start() to line.end().
     * check collision with all collidables in this collection
     * finally return the collisionInfo of the closest collision-point to line.start()</p>
     * @param trajectory .
     * @return null if there isn't collision. else return collisionInfo of closest collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        double prevDist = 0;
        CollisionInfo closestC = null;
        for (Collidable c:collidables) { //loop to find the closest collision between all collisions.
            if (c.getCollisionRectangle().intersectionPoints(trajectory).size() != 0) { //if there are intersections
                Point hitPoint = collisionPoint(c, trajectory);
                CollisionInfo info = new CollisionInfo(hitPoint, c);
                double dist = trajectory.start().distance(hitPoint);
                if (dist > prevDist) { //this is the current closest collision - save in closestC and update dist.
                    prevDist = dist;
                    closestC = info;
                }
            }
        }
        return closestC;
    }
    /** collisionPoint -- the point at which the collision occurs, the closest to start of line.
     * @param c collision_detection.Collidable
     * @param trajectory .
     * @return the collision point. if there aren't intersections at all - null.
     */
    private Point collisionPoint(Collidable c, Line trajectory) {
        Rectangle rectC = c.getCollisionRectangle(); //to simplify
        return trajectory.closestIntersectionToStartOfLine(rectC);
    }
}