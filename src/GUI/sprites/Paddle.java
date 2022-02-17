package GUI.sprites;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import GUI.defines.MyColors;
import GUI.defines.MyDefines;
import GUI.geometry.Point;
import GUI.geometry.Rectangle;
import collisions.Collidable;
import collisions.CollisionInfo;
import other.GameLevel;
import other.Velocity;

/**
 * class of paddle, implements GUI.sprites.Sprite and collision_detection.Collidable interfaces.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class Paddle implements Sprite, Collidable {
    private Block delegate; //delegate. create the block on constructor with same rect.
    private CollisionInfo lastCollision; //saves info of ball's latest collision
    //fields
    private Velocity v;
    private biuoop.KeyboardSensor keyboard;
    private GameLevel gameLevel;
    /**
     * Constructor.
     * @param rect shape of paddle.
     * @param dx the x velocity for the paddle.
     * @param key keyboard sensor.
     */
    public Paddle(Rectangle rect, float dx, KeyboardSensor key) {
        this.v = new Velocity(dx, 0); //paddle never moves on y axis
        if (this.v.getDx() < 0) { //working with size of speed, no meaning to direction. keep dx positive.
            this.v = new Velocity(-this.v.getDx(), 0);
        }
        //create keyboardSensor
        this.keyboard = key;
        this.delegate = new Block(rect);
        this.delegate.setColor(MyColors.ORANGE); //set color before drawing
    }
    /**
     * moves the paddle left if user press left. in case we touch left border - don't move.
     */
    public void moveLeft() {
        Point uL = this.delegate.getRect().getUpperLeft(); //to simplify
        if (uL.getX() - v.getDx() <= GameLevel.BORDER_THICKNESS) {
            this.delegate.getRect().setUpperLeft(new Point(GameLevel.BORDER_THICKNESS, uL.getY()));
        } else { //no collision, move regularly.
            this.delegate.getRect().setUpperLeft(new Point(uL.getX()
                    - this.v.getDx(), uL.getY())); //-v.dx cause moving left
        }
    }
    /**
     * moves the paddle right if user press right. in case we touch right border - don't move.
     */
    public void moveRight() {
        Point uL = this.delegate.getRect().getUpperLeft(); //to simplify
        double rectW = this.delegate.getRect().getWidth();
        if (uL.getX() + rectW + v.getDx() >= MyDefines.GUI_WIDTH - GameLevel.BORDER_THICKNESS) {
            double x = MyDefines.GUI_WIDTH - GameLevel.BORDER_THICKNESS - rectW;
            this.delegate.getRect().setUpperLeft(new Point(x, uL.getY()));
        } else { //no collision, move regularly.
            this.delegate.getRect().setUpperLeft(new Point(uL.getX()
                    + this.v.getDx(), uL.getY())); //-v.dx cause moving left
        }
    }
    /**
     * move left or right according to keyboard input. if input isn't right or left - do nothing.
     */
    private void moveOneStep() {
        if (keyboard.isPressed(keyboard.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(keyboard.RIGHT_KEY)) {
            moveRight();
        }
    }


    /**
     * timePassed - tell the GUI.sprites.Paddle what to do on time passed. part of sprite interface.
     */
    public void timePassed() {
        moveOneStep();
    }
    /**
     * draws the paddle. I use drawOn() from GUI.sprites.Block via a delegate.
     * @param d surface to draw on.
     */
    public void drawOn(DrawSurface d) {
        delegate.drawOn(d);
    }

    /**
     * getter.
     * @return this paddle's rect.
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.delegate.getRect();
    }

    /**
     * hit - Notify this paddle that we collided with it at collisionPoint with a given velocity.
     * @param hitter ball.
     * @param collisionPoint - the collisionPoint.
     * @param currentV the v before the collision.
     * @return the velocity the ball should have after colliding with this paddle.
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentV) {
        int ribIndex = this.delegate.getRect().ribNumber(collisionPoint);
        CollisionInfo tempLast = this.lastCollision; //for case there isn't collision - save it before update
        this.lastCollision = new CollisionInfo(collisionPoint, this);
        //separate cases to rect's 4 ribs
        if (ribIndex == 0 && currentV.getDy() > 0.0) { //to touch the upper rib you must go down, y positive direction.
            return funPaddleUpperRibHit(collisionPoint, currentV);
        } else if (this.delegate.getRect().ribNumber(collisionPoint) == 2) {
            //down rib - flip dy direction. (good also for extreme case that ball came into the paddle accidentally)
            return new Velocity(currentV.getDx(), -currentV.getDy());
        } else if (ribIndex == 1 || ribIndex == 3) {
            //same response like in block.hit(), use delegate
            return this.delegate.hit(hitter, collisionPoint, currentV);
        } else {
            this.lastCollision = tempLast; //there wasn't collision
            return currentV; //the ball is inside a block (not good). anyway,let the ball out and don't change v.
        }
    }

    /**
     * funPaddleUpperRibHit - assitance method for hit, for the casr ball hit the upper rib.
     * divide the upper rib to 5 cases that give other velocities for each case.
     * @param collisionPoint .
     * @param currentV .
     * @return velocity according to hit point.
     */
    private Velocity funPaddleUpperRibHit(Point collisionPoint, Velocity currentV) {
        //calculate case index
        double w =  this.delegate.getRect().getWidth(); //simplify
        double startX =  this.delegate.getRect().getUpperLeft().getX();
        double hitX =  collisionPoint.getX();
        double position = (hitX - startX);
        int index = (int) (((hitX - startX) / w) * 5); //java rounds the number down, the index is exact
        double speed = Math.sqrt(Math.pow(currentV.getDx(), 2) + Math.pow(currentV.getDy(), 2)); //v size
        switch (index) {
            case 0:
                return Velocity.fromAngleAndSpeed(300, speed);
            case 1:
                return Velocity.fromAngleAndSpeed(330, speed);
            case 3:
                return Velocity.fromAngleAndSpeed(30, speed);
            case 4:
                return Velocity.fromAngleAndSpeed(60, speed);
            default: //include the case for index = 2
                return new Velocity(currentV.getDx(), -currentV.getDy());
        }
    }

    /**
     * check if a given point is inside the paddle.
     * @param point to check if is inside this paddle.
     * @return the point with a new y, outside of the paddle. if not inside return null.
     */
    public Point insidePaddle(Point point) {
        double y = point.getY();
        double x = point.getX();
        Point uL =  this.delegate.getRect().getUpperLeft();
        //if the point is inside the paddle:
        if (y > uL.getY()) {
            if (x > uL.getX() && x < uL.getX() + this.delegate.getRect().getWidth()) {
                return new Point(point.getX(), uL.getY() - 1); //point above of paddle
            }
        }
        return null; //not inside, return null.
    }
    /**
     * Add this paddle to the given game g.
     * @param g game.
     */
    public void addToGame(GameLevel g) {
        this.gameLevel = g;
        g.getEnvironment().addCollidable(this);
        g.getSprites().addSprite(this);
        g.getEnvironment().setPaddle(this);
    }
}