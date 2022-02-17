package GUI.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI.geometry.Rectangle - This is the GUI.geometry.Rectangle class.
 *
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class Rectangle {
    //fields
    private Point upperLeft;
    private double width;
    private double height;
    //more fields that are calculated in the constructor
    private Point upperRight;
    private Point downLeft;
    private Point downRight;
    private Line[] ribs = new Line[4]; //even indexes for horizontal ribs (up=0,dn=2), odd for vertical (rt=1,lt=3)
    /**
     * Constructor.
     *
     * @param upperLeft  point.
     * @param width .
     * @param height .
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft);
        this.width = width;
        this.height = height;
        this.upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        this.downLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
        this.downRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
        setRibs(); //set ribs on construction
        }

    //todo - extreme cases like equal slopes. handle exceptions (corners intersection)
    /**
     * intersectionPoints -- create a list of intersection points with the specified line.
     * @param line to find it's intersections.
     * @return List of intersection points with the specified line. if there arent points return an empty list.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        //calc and insert intersection points of all 4 lines to list
        List<Point> list = new ArrayList<>();
        for (Line ln: ribs) {
            if (ln.intersectionWith(line) != null) {
                list.add(ln.intersectionWith(line));
            }
        }
            return list;
    }
    /**
     * ribNumber -- Get a point that is intersect with this rect (if it isn't return null)
       and return a number that indicates to which rib it belongs to.
     * <p>Extreme cases: when collide the corner - simply act like it hit vert or horizon rib,
     * and can return any line of the two</p>
     * @param intersectP intersection point.
     * @return 0-up, 1-rt, 2-dn, 3-lt. if the point isn't belong to this rectangle return null.
     */
    public Integer ribNumber(Point intersectP) {
        for (int i = 0; i < this.ribs.length; i++) {
            if (this.ribs[0].belong(this.ribs[i], intersectP)) {
                return (i); //return the proper index for that rib in ribs[]
            }
        }
        return null;
    }
    /**
     * getWidth -- return this rect width.
     * @return this rect width
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * getHeight -- return this rect height.
     * @return this rect height
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * getUpperLeft -- return this rect UpperLeft GUI.geometry.Point.
     * @return this rect UpperLeft GUI.geometry.Point.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * setUpperLeft -- update the value of this rect UpperLeft GUI.geometry.Point, and update the rest of the
     * rect fields that need to be updated on movement. (ribs, rest of corners).
     * @param uL upperLeft.
     */
    public void setUpperLeft(Point uL) {
        this.upperLeft = uL;
        //important to recalculate the new values of other corners, they are updating accordingly.
        this.upperRight = new Point(uL.getX() + width, uL.getY());
        this.downLeft = new Point(uL.getX(), uL.getY() + height);
        this.downRight = new Point(uL.getX() + width, uL.getY() + height);
        this.setRibs();
    }
    /**
     * setRibs -- use it in the constructor. created this to keep code simple.
       set the ribs of this rect inside the field ribs[].
     */
    private void setRibs() {
        //calculate the ribs
        this.ribs[0] = new Line(this.upperLeft, this.upperRight); //up
        this.ribs[1] = new Line(this.upperRight, this.downRight); //right
        this.ribs[2] = new Line(this.downLeft, this.downRight); //down
        this.ribs[3] = new Line(this.upperLeft, this.downLeft); //left
        //make sure all lines are OK
        for (Line ln : ribs) {
            Double slope = ln.slopeM(ln);
            if (slope != null && slope != 0) { //make sure ribs OK
                throw new RuntimeException("rect ribs slopes aren't vertical or horizontal");
            }
        }
    }
}