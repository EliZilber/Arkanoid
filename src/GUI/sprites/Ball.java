package GUI.sprites;

import biuoop.DrawSurface;
import collisions.listeners.HitListener;
import collisions.listeners.HitNotifier;
import GUI.geometry.Line;
import GUI.geometry.Point;
import collisions.CollisionInfo;
import other.GameLevel;
import collections.GameEnvironment;
import other.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Ball - This is the GUI.sprites.Ball class.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class Ball implements Sprite, HitNotifier {
    //fields
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity = null; //null until being set
    private Line trajectory = null; //null until being set
    private GameEnvironment gameEnvironment; //the game environment for this ball
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * Constructor -- works with point as location parameter.
     *
     * @param center the center of the ball.
     * @param r      radius.
     * @param color  color.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
    }

    /**
     * Constructor -- works with x,y as location parameters.
     *
     * @param x     the x val of the center of ball.
     * @param y     the y val of the center of ball.
     * @param r     radius.
     * @param color color.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = new Point(x, y); //set center point with setValues() method
        this.r = r;
        this.color = color;
    }

    /**
     * Constructor -- include a gameEnvironment param. works with point as location parameter.
     *
     * @param gameEnvironment the gameEnvironment for this ball.
     * @param center          the center of the ball.
     * @param r               radius.
     * @param color           color.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * setVelocity -- insert velocity as parameter.
     *
     * @param v velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * setVelocity -- insert dx, dy as parameters.
     *
     * @param dx x velocity
     * @param dy y velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * setGameEnvironment -- insert collections.GameEnvironment as field.
     *
     * @param environment the gameEnvironment of this ball.
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.gameEnvironment = environment;
    }
    // getters

    /**
     * getX -- accessor.
     *
     * @return Returns the x value of center of ball.
     */
    public int getX() {
        return (int) Math.round(this.center.getX());
    }

    /**
     * getY -- accessor.
     *
     * @return Returns the y value of center of ball.
     */
    public int getY() {
        return (int) Math.round(this.center.getY());
    }

    /**
     * getSize -- accessor.
     *
     * @return Returns the radius of this ball.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * getColor -- accessor.
     *
     * @return Returns the color of this ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * getVelocity -- accessor.
     *
     * @return Returns the velocity of this ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * getTrajectory -- accessor.
     *
     * @return the current trajectory of this ball.
     */
    public Line getTrajectory() {
        return this.trajectory;
    }
    /**
     * drawOn -- draw the ball on the given DrawSurface.
     *
     * @param surface the surface to draw on.
     */
    /**
     * timePassed - implemented for GUI.sprites.Sprite interface.
     * on every call, move one step.
     */
    public void timePassed() {
        moveOneStep();
    }

    /**
     * drawOn- draws this object on a given surface.
     *
     * @param surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.r);
        surface.setColor(Color.lightGray);
        surface.drawCircle(this.getX(), this.getY(), this.r);
    }

    /**
     * moveOneStep -- moves the ball center-GUI.geometry.Point one step according to this.velocity.
     * if ball is touching a collidable - flip v direction
     */
    public void moveOneStep() {
        if (gameEnvironment.getPaddle() != null) { //make sure paddle was set inside gameEnvironment.
            if (gameEnvironment.getPaddle().insidePaddle(this.center) != null) { //if ball is inside paddle
                this.center = gameEnvironment.getPaddle().insidePaddle(this.center);
                return;
            }
        }
        updateTrajectory(); //update the trajectory for this step.
        //local variables to simplify code
        Point hitPoint = null;
        CollisionInfo info = collisionInfo();
        if (info != null) { //make sure there is info about a collision to prevent error
            hitPoint = info.collisionPoint();
        }
        if (hitPoint == null) { //no collision - move regularly
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            //update center value
            moveToAlmostHitPoint(hitPoint);
            //update the velocity to the new velocity returned by the hit() method.
            this.setVelocity(collisionInfo().collisionObject().hit(this, hitPoint, this.velocity));
        }
    }

    /**
     * moveToAlmostHitPoint -- move the ball an epsilon before the hit point.
     *
     * @param hitPoint the hit point for this step.
     */
    private void moveToAlmostHitPoint(Point hitPoint) {
        double epsilonX = 1, epsilonY = 1;
        if (this.velocity.getDx() < 0) { //if v is negative, flip epsilon's value.
            epsilonX = -epsilonX;
        }
        if (this.velocity.getDy() < 0) {
            epsilonY = -epsilonY;
        }
        Point almostHitPoint = new Point(hitPoint.getX() - epsilonX, hitPoint.getY() - epsilonY);
        this.center = almostHitPoint;
    }

    /**
     * updateTrajectory -- define a trajectory according to the current velocity.
     * use it to update the trajectory on moveOneStep().
     */
    private void updateTrajectory() {
        if (this.velocity == null) {
            throw new RuntimeException("try to set trajectory in ball before ball.velocity set");
        }
        //the destination point is (x+dx,y+dy)
        Point destPoint = new Point(this.center.getX() + this.velocity.getDx(),
                this.center.getY() + this.velocity.getDy());
        this.trajectory = new Line(this.center, destPoint);
    }

    /**
     * collisionInfo -- Get the info about collisions.
     *
     * @return the closest collision's info. null if there isn't collision
     */
    private CollisionInfo collisionInfo() {
        return this.gameEnvironment.getClosestCollision(trajectory);
    }

    /**
     * addToGame - Add this ball to the given game g.
     * @param g game.
     */
    public void addToGame(GameLevel g) {
        g.getSprites().addSprite(this);
    }

    /**
     * remove this ball from the given Game class.
     * @param gameLevel .
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
}