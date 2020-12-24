package app.supportclasses;

import java.awt.geom.Point2D;

public class Directions {


    public static Point2D.Double getPercentFromLocations(double meX, double meY, double otherX, double otherY) {
        double dY = (otherY - meY);
        double dX = (otherX - meX);
        return getPercentFromVelocities(dX, dY);
    }

    public static Point2D.Double normalize(Point2D.Double velocity) {
        return getPercentFromVelocities(velocity.x, velocity.y);
    }

    public static Point2D.Double getPercentFromVelocities(double xChange, double yChange) {
        double theta;
        if (xChange == 0) {
            theta = Integer.MAX_VALUE;
        } else {
            theta = Math.atan(yChange/xChange);
        }

        double xVel = Math.signum(xChange) * Math.cos(theta);
        double yVel = ((xChange<0)? -1:1) * Math.sin(theta);
        return new Point2D.Double(xVel, yVel);
    }

}
