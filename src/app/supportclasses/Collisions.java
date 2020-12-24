package app.supportclasses;

import java.awt.geom.Point2D;

public class Collisions {
    
    public static boolean contains(double top, double bottom, double left, double right, Point2D.Double other) {
        return Collisions.leftHandSideTest(left, top, left, bottom, other.getX(), other.getY()) &&
            Collisions.leftHandSideTest(left, bottom, right, bottom, other.getX(), other.getY()) &&
            Collisions.leftHandSideTest(right, bottom, right, top, other.getX(), other.getY()) &&
            Collisions.leftHandSideTest(right, top, left, top, other.getX(), other.getY());
    }

    public static boolean leftHandSideTest(double x1, double y1, double x2, double y2, double xTEST, double yTEST) {
        double D = xTEST*(y1-y2) + x1*(y2-yTEST) + x2*(yTEST-y1);
        //System.out.println("Collision checking side: " + x1 + ", " + y1 + " to " + x2 + ", " + y2 + " for: " + xTEST + ", " + yTEST);
        if (D <= 0) {
            //System.out.println("On the correct side");
            return true;
        }   else {
            //System.out.println("Wrong side!");
            return false;
        }
    }

    public static boolean within(Point2D.Double mainLocation, Point2D.Double otherLocation, double radius) {
        double distX = Math.abs(mainLocation.x - otherLocation.x);
        double distY = Math.abs(mainLocation.y - otherLocation.y);
        return distX < radius || distY < radius;
    }
}
