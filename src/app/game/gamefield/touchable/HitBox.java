package app.game.gamefield.touchable;

import java.awt.geom.Point2D;
/**
 * HitBox
 */
public class HitBox {
    private Point2D.Double percentSize;
    private Point2D.Double percentOffset;

    public HitBox() {
        percentSize = new Point2D.Double(1, 1);
        percentOffset = new Point2D.Double(0, 0);
    }

    public HitBox(double xPercentSize, double yPercentSize, double xPercentOffset, double yPercentOffset) {
        percentSize = new Point2D.Double(xPercentSize, yPercentSize);
        percentOffset = new Point2D.Double(xPercentOffset, yPercentOffset);
    }

    public double getLeftOfHitBox(double xPos, double xSizeInBlocks) {
        return xPos-xSizeInBlocks*(percentSize.getX()/2.0 - percentOffset.getX());
    }

    public double getRightOfHitBox(double xPos, double xSizeInBlocks) {
        return xPos+xSizeInBlocks*(percentSize.getX()/2.0 + percentOffset.getX());
    }

    public double getTopOfHitBox(double yPos, double ySizeInBlocks) {
        return yPos-ySizeInBlocks*(percentSize.getY()/2.0 - percentOffset.getY());
    }

    public double getBottomOfHitBox(double yPos, double ySizeInBlocks) {
        return yPos+ySizeInBlocks*(percentSize.getY()/2.0 + percentOffset.getY());
    }

    public Point2D.Double getCenterOfHitBox(Point2D.Double location, Point2D.Double sizeInBlocks) {
        double newX = location.getX()+(percentOffset.getX()*sizeInBlocks.getX());
        double newY = location.getY()+(percentOffset.getY()*sizeInBlocks.getY());
        return new Point2D.Double(newX, newY);
    }

    public Point2D.Double getHitBoxSize(Point2D.Double sizeInBlocks) {
        double xSize = percentSize.getX()*sizeInBlocks.getX();
        double ySize = percentSize.getY()*sizeInBlocks.getY();
        return new Point2D.Double(xSize, ySize);
    }
}