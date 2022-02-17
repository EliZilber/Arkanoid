package other;

import GUI.geometry.Point;

/**
 * Velocity - This is the other.Velocity class.
 * specifies the change in position on the `x` and the `y` axes.
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class Velocity {
    //fields
    private double dx;
    private double dy;
    private double angle;
    /**
     * Constructor -- inserts velocity ingredients.
     * @param dx the x val of v.
     * @param dy the y val of v.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        double calc = Math.toDegrees(Math.atan2(dy, dx)) + 90;
        if (calc < 0) { //if degree is negative - make it positive by adding pie
          this.angle = 360 + calc;
        } else  {
            this.angle = calc;
        }
    }
    /**
     * Static instantiating -- gets angle and velocity size and creates a new instance of other.Velocity.
     * gets angle and velocity size and converts them into dx, dy
     * Did it with static method because:
     * angle and speed are two doubles, and we already have a constructor
     * taking two doubles (dx and dy)
     * Notice: You don't need to create a new instance to use it. just call the class and this method.
     * @param angle of speed.
     * @param speed size.
     * @return other.Velocity v
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double angleInRadians = Math.toRadians(angle);; /*sin and cos methods work with radians,
        our angle's positive direction is the opposite to the regular one so we also doing 360 - degree
         radians direction: https://sites.google.com/site/trigbookprojecttttttttttttt/4-2-degrees-and-radians*/
        double dx = speed * Math.sin(angleInRadians);
        double dy = -speed * Math.cos(angleInRadians); // y-axis positive direction in pixels is opposite
        return new Velocity(dx, dy);
    }
    // accessors
    /**
     * getDx -- accessor.
     * @return Returns the dx value.
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * getDy -- accessor.
     * @return Returns the dy value.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * getter.
     * @return v angle on degrees.
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * getter.
     * @return v angle on radians.
     */
    public double getAngleInRadians() {
        return Math.toRadians(this.angle);
    }
    /**
     * applyToPoint -- Take a point with position (x,y) and return a new point.
     * with position (x+dx, y+dy)
     * @param p takes p and renew it.
     * @return GUI.geometry.Point of the new position.
     */
    public Point applyToPoint(Point p) {
        double newX = p.getX() + this.dx;
        double newY = p.getY() + this.dy;
        return new Point(newX, newY);
    }
}