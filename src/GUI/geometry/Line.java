package GUI.geometry; //209132489

import GUI.defines.MyDefines;

/**
 * GUI.geometry.Line - This is the GUI.geometry.Line class.
 *
 * @author  Eli Zilberstine
 * @version 1.0
 * @since   2021
 */
public class Line {
    // Fields
    private Point start;
    private Point end;
    private Point middle;
    // constructors
    /**
     * Constructor. Works with points as parameters.
     *
     * @param start start point of the line.
     * @param end end point of the line.
     */
    public Line(Point start, Point end) {
        this.start =  new Point(start.getX(), start.getY());
        this.end =  new Point(end.getX(), end.getY());
    }
    /**
     * Constructor. Works with x,y parameters.
     *
     * @param x1 x of point start.
     * @param y1 y of point start.
     * @param x2 x of point end.
     * @param y2 y of point end.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start =  new Point(x1, y1);
        this.end =  new Point(x2, y2);
    }
    /**
     * length -- Return the length of this line.
     * calculate with distance method of point start. calc distance between start to end points.
     * @return length of this line
     */
    public double length() {
        return start.distance(end);
    }

    /*** middle -- Returns the middle point of the line.
     *
     * @return middle point of this line
     */
    public Point middle() {
        if (this.middle == null) { //if middle hasn't calculated yet - do it now
            double midX = ((start.getX() + end.getX()) / 2);
            double midY = ((start.getY() + end.getY()) / 2);
            this.middle = new Point(midX, midY);
        }
        return middle; //return after you made sure the middle calculated
    }

    /**
     * start -- Returns the start point of the line.
     *
     * @return start point of this line
     */
    public Point start() {
        return start;
    }

    /**
     * end -- Returns the end point of the line.
     *
     * @return end point of this line.
     */
    public Point end() {
       return end;
    }

    /**
     * isIntersecting -- Returns true if the lines intersect, false otherwise.
     *
     * @param other the other line.
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return (intersectionWith(other) != null);
    }

    /**
     * intersectionWith -- Returns the intersection point if the lines intersect.
     *
     * I made partition to 3 main cases - divided by slopes
     * assistance methods: sameSlopesIntersect, regularIntersect, verticalIntersect
     * @param other the other line.
     * @return Returns the intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        Double otherM = slopeM(other);
        Double thisM = slopeM(this);
        //first case - both lines are vertical
        if (otherM == null && thisM == null) {
            return sameSlopesIntersect(other);
        //second case - both lines aren't vertical
        } else if (otherM != null && thisM != null) {
            if (otherM.equals(thisM)) { //case otherM == thisM
                return sameSlopesIntersect(other);
            } else {
                return regularIntersect(other);
            }
        //third case - only one of the lines is vertical
        } else {
            return verticalIntersect(other);
        }
    }
    /**
     * equals -- return true if the lines are equal, false otherwise.
     *
     * @param other the other line.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        boolean equal = (this.start.equals(other.start) && this.end.equals(other.end));
        //there isn't importance to which point is start/end - include the reverse case
        boolean reverse = (this.start.equals(other.end) && this.end.equals(other.start));
        return (equal || reverse);
    }
    /**
     * verticalIntersect -- finds intersection point in case that only one of the lines is vertical.
     * Assistance method inside intersectionWith().
     * math exp: if one of lines is vertical - we cant use the regular method because m==null. So created this one.
     * we check that x value of the vertical is between the x values of the other line
     * then we find the y value ot regular the line and make sure its between the y values of the vert. line
     * @param other the other line.
     * @return the intersection point if exist or null if not.
     */
    private Point verticalIntersect(Line other) {
        //variables
        Line vertical;
        Line regular;
        //define which line is ver and which is reg to simplify next calculations
        if (slopeM(other) == null) {
            vertical = other;
            regular = this;
        } else if (slopeM(this) == null) {
            vertical = this;
            regular = other;
        } else {
            throw new RuntimeException("verticalIntersect() called while none of the lines is vertical");
        }
        double verX = vertical.start.getX();
        boolean smallerThanXMax = verX <= Math.max(regular.start.getX(), regular.end.getX());
        boolean biggerThanXMin = verX >= Math.min(regular.start.getX(), regular.end.getX());
        double regY = (slopeM(regular) * verX) + interceptY(regular); //y value of the potential intersection point
        boolean smallerThanYMax = regY <= Math.max(vertical.start.getY(), vertical.end.getY());
        boolean biggerThanYMin = regY >= Math.min(vertical.start.getY(), vertical.end.getY());
        //if all booleans are true - there is an interPoint-return it. else return null.
        if (smallerThanXMax && biggerThanXMin && biggerThanYMin && smallerThanYMax) {
            return new Point(verX, regY);
        } else {
            return null;
        }
    }
    /**
     * sameSlopesIntersect -- finds intersection point in case that the lines may be merging (m1==m2).
     * Assistance method inside intersectionWith().
     * math exp: if the lines are merging they don't have an inter-point. so i check if they are one above the other
     * by comparing the min and max y values of the lines.
     * @param other the other line.
     * @return the intersection point if exist or null if lines are merging.
     */
    private Point sameSlopesIntersect(Line other) {
        Double otherYMax  = Math.max(other.start.getY(), other.end.getY());
        Double otherYMin  = Math.min(other.start.getY(), other.end.getY());
        Double thisYMax  = Math.max(this.start.getY(), this.end.getY());
        Double thisYMin  = Math.min(this.start.getY(), this.end.getY());
        if (slopeM(other) == null) {
            return sameSlopesAreNullIntersect(other);
        } else if (slopeM(other).equals(0.0) || slopeM(other).equals(-0.0)) {
            return sameSlopesAreZeroIntersect(other);
        } else {
            if (otherYMax.equals(thisYMin)) {
                double x = (otherYMax - interceptY(other)) / slopeM(other);
                return new Point(x, otherYMax);
            } else if (thisYMax.equals(otherYMin)) {
                double x = (otherYMin - interceptY(other)) / slopeM(other);
                return new Point(x, otherYMin);
            } else { //merging
                return null;
            }
        }
    }
    /**
     * sameSlopesAreZeroIntersect -- finds intersection point in case that the lines may be merging (m1==m2==0).
     * Assistance method inside sameSlopesIntersect().
     * math exp: if the lines are merging they don't have an inter-point. so i check if they are nearby.
     * @param other the other line.
     * @return the intersection point if exist or null if lines are merging.
     */
    private Point sameSlopesAreZeroIntersect(Line other) {
        //if the lines are on different y values they can't intersect.
        if (other.start.getY() != this.start.getY()) {
            return null;
        }
        Double otherXMax  = Math.max(other.start.getX(), other.end.getX());
        Double otherXMin  = Math.min(other.start.getX(), other.end.getX());
        Double thisXMax  = Math.max(this.start.getX(), this.end.getX());
        Double thisXMin  = Math.min(this.start.getX(), this.end.getX());
        if (otherXMax.equals(thisXMin)) {
            return new Point(otherXMax, this.start.getY());
        } else if (thisXMax.equals(otherXMin)) {
            return new Point(otherXMin, this.start.getY());
        } else { //merging
            return null;
        }
    }
    /**
     * sameSlopesAreNullIntersect -- finds intersection point in case that the lines may be merging (m1==m2==null).
     * Assistance method inside sameSlopesIntersect().
     * @param other the other line.
     * @return the intersection point if exist or null if lines are merging or not intersecting at all.
     */
    private Point sameSlopesAreNullIntersect(Line other) {
        //if the lines are on different x values they can't intersect.
        if (other.start.getX() != this.start.getX()) {
            return null;
        }
        Double otherYMax  = Math.max(other.start.getY(), other.end.getY());
        Double otherYMin  = Math.min(other.start.getY(), other.end.getY());
        Double thisYMax  = Math.max(this.start.getY(), this.end.getY());
        Double thisYMin  = Math.min(this.start.getY(), this.end.getY());
        if (otherYMax.equals(thisYMin)) {
            //because slope = null, the line is parallel to YAxis I can simply return start.GetX().
                return new Point(this.start.getX(), otherYMax);
        } else if (thisYMax.equals(otherYMin)) {
                return new Point(this.start.getX(), otherYMin);
        } else { //merging
            return null;
        }
    }
    /**
     * regularIntersect -- look for intersection point between not-vertical lines.
     * Assistance method inside intersectionWith().
     * math exp: first we find the x of inter. point: m1*(x)+b1=m2*(x)+b2 +=> x = (b2-b1)/(m1-m2)
     * y = m1*x+b1
     * @param other the other line.
     * @return Returns the intersection point if the lines intersect. null if they aren't
     */
    private Point regularIntersect(Line other) {
        Double thisM = slopeM(this);
        Double otherM = slopeM(other);
        //problematic case (both slopes are equal or equal to null)
        if (thisM.equals(otherM) || thisM == null || otherM == null) {
            throw new RuntimeException("intersectionPoint() called while m1==m2. call mergeOrIntersect instead");
        }
        double x = ((interceptY(other) - interceptY(this)) / (thisM - otherM));
        double y = (thisM * x + interceptY(this));
        Point interPoint = new Point(x, y);
        //make sure the intersection point belongs to both lines and return it if yes. else return null.
        if (belong(other, interPoint) && belong(this, interPoint)) {
            return interPoint;
        } else {
            return null;
        }
    }
    /**
     * belong -- returns true if the point belongs to the line.
     * <p>math exp: we make sure the x and y values are between the max and min value of Points start, end.
     * using epsilon on double equating.</p>
     * @param line the line we need to check.
     * @param point the point to check.
     * @return true if the point belongs to the line
     */
    public boolean belong(Line line, Point point) {
        boolean upperX  = point.getX() <= Math.max(line.start.getX(), line.end.getX()) + MyDefines.EPSILON;
        boolean lowerX = point.getX() >= Math.min(line.start.getX(), line.end.getX()) - MyDefines.EPSILON;
        boolean upperY  = point.getY() <= Math.max(line.start.getY(), line.end.getY()) + MyDefines.EPSILON;
        boolean lowerY = point.getY() >= Math.min(line.start.getY(), line.end.getY()) - MyDefines.EPSILON;
        //return true only if: x,y retrieve true for all conditions
        return (upperX && upperY && lowerX && lowerY);
        }
    /**
     * interceptY -- returns the y value of interception with the y axis.
     * explanation: general equation is: y = m(x − Px) + Py. we need the value for x=0 => y=-mPx + Py
     * @param line the line we need to return it's interception with Y axis.
     * @return the y value of interception with the y axis.
     */
    public double interceptY(Line line) {
        return (-slopeM(line) * line.start.getX() + line.start.getY());
    }

    /**
     * slopeM -- returns slope (m) of given line. if line is vertical returns null
     *
     * math exp: m = y1-y2/x1-x2
     * @param line the line we need to return it's slope.
     * @return slope (m) of line parameter, or null if doesn't exist.
     */
    public Double slopeM(Line line) {
        if ((line.start.getX() - line.end.getX()) == 0d) {
        return null;
        } else {
            return ((line.start.getY() - line.end.getY()) / (line.start.getX() - line.end.getX()));
        }
    }
    /**
     * closestIntersectionToStartOfLine -- find the closest inter-point with the rect to this.start.
     *
     * @param rect the rect to check it's intersection
     * @return the point mentioned or null if this line does not intersect with the rectangle.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> list = rect.intersectionPoints(this);
        if (list == null) {
            return null;
        } else {
            double minDist = this.end.distance(start); //the largest possible dist from start. help me in calcs
            Point minPt = null;
            for (Point pt: list) { //loop for the pts in list and compare their distances from this.start
                if (pt != null) {
                    double dist = this.start.distance(pt);
                    if (dist <= minDist) { //<= (and not <) for case that there is one inter point on end of line.
                        minDist = dist;
                        minPt = pt;
                    }
                }
            }
            return minPt;
        }
    }
}