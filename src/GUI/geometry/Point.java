package GUI.geometry; //209132489

import GUI.defines.MyDefines;

/**
 * GUI.geometry.Point - This is the GUI.geometry.Point class.
 *
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class Point {
    // Fields
    private double x;
    private double y;
    private double epsilon = MyDefines.EPSILON;

    /**
     * Constructor. Works with x,y parameters.
     *
     * @param x x.
     * @param y y.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Constructor. get point and insert its value independently.
     * @param point .
     */
    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }
    /**
     * distance -- return the distance of this point to the other point.
     * created otherX, otherY vars to simplify the calc line
     * @param other - other point to calculate it's distance from this point.
     * @return distance of this point to the other point.
     */
    public double distance(Point other) {
        double otherX = other.getX();
        double otherY = other.getY();
        double distance = Math.sqrt(((otherX - this.x) * (otherX - this.x)) + ((otherY - this.y) * (otherY - this.y)));
        return distance;
    }

    /**
     * equals -- return true if the points are equal, false otherwise.
     * @param other - other point to compare with this point.
     * @return true if the points are equal
     */
    public boolean equals(Point other) {
        return (equalsAssist(other.getX(), this.x) && equalsAssist(other.getY(), this.y));
    }
    /**
     * equalsAssist -- help to check if double are equals until epsilon.
     * @param x1 .
     * @param x2 .
     * @return true if the points are equal
     */
    public boolean equalsAssist(double x1, double x2) {
       boolean equals = Math.max(x1, x2) - Math.min(x1, x2) <= epsilon;
        return equals;
    }
    /**
     * getX.
     * @return the x value of this point.
     */
    public double getX() {
        return this.x;
    }
    /**
     * getY.
     * @return the y value of this point.
     */
    public double getY() {
        return this.y;
    }
    /**
     * setValues -- this method set new values to this point, via args x,y.
     * @param newX the x value to set.
     * @param newY the y value to set.
     */
    public void setValues(double newX, double newY) {
        this.x = newX;
        this.y = newY;
    }
    /**
     * printPoint -- prints the point inside paranteces.
     */
    public void printPoint() {
        System.out.println("(" + this.getX() + "," + this.getY() + ")");
    }
}