package GUI.sprites;

import biuoop.DrawSurface;
import collisions.listeners.HitListener;
import collisions.listeners.HitNotifier;
import GUI.geometry.Point;
import GUI.geometry.Rectangle;
import collisions.Collidable;
import collisions.CollisionInfo;
import other.GameLevel;
import other.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI.sprites.Block - Class of obstacles in shape of rectangle.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class Block implements Collidable, Sprite, HitNotifier {
    //fields
    private Rectangle rect; //the rect is the shape of the block
    private CollisionInfo lastCollision;
    private java.awt.Color color = Color.black; //black is default color.
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * Constructor with parameters of rect shape.
     * @param upperLeft  point.
     * @param width .
     * @param height .
     */
    public Block(Point upperLeft, double width, double height) {
        this.rect = new Rectangle(upperLeft, width, height);
    }
    /**
     * Constructor for an existing rect.
     * @param rect point.
     */
    public Block(Rectangle rect) {
        this.rect = rect;
    }
    //getters
    /**
     * getColor -- return this bolck's color.
     * @return color.
     */
    public Color getColor() {
        return this.color;
    }
    /**
     * getLastCollision -- return this bolck's info about the last collision happend.
     * @return lastCollision.
     */
    public CollisionInfo getLastCollision() {
        return this.lastCollision;
    }
    /**
     * getRect -- return this bolck's rect.
     * @return rect.
     */
    public Rectangle getRect() {
        return this.rect;
    }
    /**
     * setColor -- set the color of this block.
     * @param c color.
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /**
     * Notify all collisions.listeners about a hit event with this block.
     * @param hitter the hitter Object.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all collisions.listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * timePassed - implemented for GUI.sprites.Sprite interface.
     *  <p>For the block, currently we do nothing.
     *  If we wanted to have animated blocks (for example, blocks that change their color over time,
     *  or have a different graphics effect</p>
     */
    public void timePassed() {

    }
    /** drawOn -- draw the GUI.sprites.Block (rect) on the given DrawSurface.
     * @param surface the surface to draw on.
     */
    public void drawOn(DrawSurface surface) {
        int x = (int) Math.round(this.rect.getUpperLeft().getX());
        int y = (int) Math.round(this.rect.getUpperLeft().getY());
        surface.setColor(this.color);
        surface.fillRectangle(x, y, (int) this.rect.getWidth(), (int) this.rect.getHeight());
        surface.setColor(Color.black);
        surface.drawRectangle(x, y, (int) this.rect.getWidth(), (int) this.rect.getHeight());
    }
    /**
         * getCollisionRectangle -- Return the "collision shape" of the object - a rect.
         * @return rectangle
         */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }
    /**
     * hit -- Notify this block that we collided with it at collisionPoint with a given velocity.
     * <p> i made 5 cases to make sure the ball is coming from outside of rect. if ball is
     * coming from inside - let him go out and don't change velocity on border</p>
     * @param collisionPoint - the collisionPoint.
     * @param currentV the v before the collision.
     * @return other.Velocity - the new velocity expected after the hit (based on the force the object inflicted on us).
     * if shouldn't be a hit - return same velocity (currentV).
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentV) {
        Integer ribIndex = this.rect.ribNumber(collisionPoint);
        if (ribIndex == null) { //if ribIndex is null, there isn't hit and return current v without change.
            return currentV;
        }
        this.notifyHit(hitter); //notify all collisions.listeners about the hit. maybe: want to exclude this line from else case.
        CollisionInfo tempLast = lastCollision; //for case there isn't collision - save it before update
        lastCollision = new CollisionInfo(collisionPoint, this);
        if (ribIndex == 0 && currentV.getDy() > 0.0) { //to touch the upper rib you must go down, y positive direction.
            //horizontal obstacle, flip y direction
            return new Velocity(currentV.getDx(), -currentV.getDy());
        } else if (ribIndex == 1 && currentV.getDx() < 0.0) { //to touch the right rib, dx must be > 0
            //vertical obstacle, flip x direction
            return new Velocity(-currentV.getDx(), currentV.getDy());
        } else if (ribIndex == 2 && currentV.getDy() < 0.0) {
            //horizontal obstacle, flip y direction
            return new Velocity(currentV.getDx(), -currentV.getDy());
        } else if (ribIndex == 3 && currentV.getDx() > 0.0) {
            //vertical obstacle, flip x direction
            return new Velocity(-currentV.getDx(), currentV.getDy());
        } else {
            lastCollision = tempLast; //there wasn't collision
            return currentV; //the ball is inside a block (not good). anyway,let the ball out and don't change v.
        }
    }

    /**
     * addToGame - Add this paddle to the given game g.
     * add to the collidable list, GUI.sprites list.
     * @param g game.
     */
    public void addToGame(GameLevel g) {
        g.getEnvironment().addCollidable(this);
        g.getSprites().addSprite(this);
    }

    /**
     * remove this block from the given Game class.
     * @param gameLevel .
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
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
