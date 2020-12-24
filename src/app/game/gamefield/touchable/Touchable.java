package app.game.gamefield.touchable;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;

import app.game.augments.MiniMapable;
import app.game.gamefield.drawable.Drawable;
import app.game.gamefield.drawable.DrawingCalculator;
import app.game.gamefield.map.Map;
import app.supportclasses.Collisions;
import app.supportclasses.GameValues;

public class Touchable extends Drawable implements MiniMapable, Destroyable {
    protected HitBox hitbox;
    protected boolean shouldMap;
    protected Color colorToMap;
    protected int health;

    public Touchable(GameValues gameValues, double xPos, double yPos, BufferedImage image, Point2D.Double sizeInBlocks) {
        this(gameValues, new Point2D.Double(xPos, yPos), image, sizeInBlocks);
    }

    public Touchable(GameValues gameValues, Point2D.Double location, BufferedImage image, Point2D.Double sizeInBlocks) {
        super(gameValues, location, image, sizeInBlocks);
        this.image = image;
        this.shouldMap = false;
        this.hitbox = new HitBox();
        this.health = Integer.MAX_VALUE;
    }

    public Touchable(GameValues gameValues, double xPos, double yPos, Color color) {
        this(gameValues, new Point2D.Double(xPos, yPos), color);
    }

    public Touchable(GameValues gameValues, Point2D.Double location, Color color) {
        super(gameValues, location);
        this.shouldMap = true;
        this.colorToMap = color;
        this.health = 1;
    }



    public void render(Graphics g) {
        super.render(g);
        if (gameValues.debugMode) {
            g.drawRect(
                    DrawingCalculator.findPixelLocation(getHitBoxLocation().getX(), getHitBoxSizeInBlocks().getX(),
                            gameValues.fieldXZeroOffset, gameValues.singleSquareX),
                    DrawingCalculator.findPixelLocation(getHitBoxLocation().getY(), getHitBoxSizeInBlocks().getY(),
                            gameValues.fieldYZeroOffset, gameValues.singleSquareY),
                    DrawingCalculator.findPixelSize(getHitBoxSizeInBlocks().getX(), gameValues.singleSquareX),
                    DrawingCalculator.findPixelSize(getHitBoxSizeInBlocks().getY(), gameValues.singleSquareY));
        }
    }

    // TODO allow for a dual hitbox to check against a dual hitbox
    public boolean contains(Point2D.Double testingLocation, Touchable other) {
        /*
         * double leftMost = getHitBox().getLeftOfHitBox(testingLocation.getX(),
         * sizeInBlocks.getX()); double topMost =
         * getHitBox().getTopOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
         * double rightMost = getHitBox().getRightOfHitBox(testingLocation.getX(),
         * sizeInBlocks.getX()); double bottomMost =
         * getHitBox().getBottomOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
         */

        // System.out.println(".Contains for " + this + " vs " + other);
        // System.out.println(other.location);
        // System.out.println(other.sizeInBlocks);

        double otherLeftMost = other.getHitBox().getLeftOfHitBox(other.location.getX(), other.sizeInBlocks.getX());
        double otherTopMost = other.getHitBox().getTopOfHitBox(other.location.getY(), other.sizeInBlocks.getY());
        double otherRightMost = other.getHitBox().getRightOfHitBox(other.location.getX(), other.sizeInBlocks.getX());
        double otherBottomMost = other.getHitBox().getBottomOfHitBox(other.location.getY(), other.sizeInBlocks.getY());

        double leftMost = getHitBox().getLeftOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double topMost = getHitBox().getTopOfHitBox(testingLocation.getY(), sizeInBlocks.getY());
        double rightMost = getHitBox().getRightOfHitBox(testingLocation.getX(), sizeInBlocks.getX());
        double bottomMost = getHitBox().getBottomOfHitBox(testingLocation.getY(), sizeInBlocks.getY());

        Point2D.Double center = getHitBox().getCenterOfHitBox(testingLocation, sizeInBlocks);

        // TODO add more statements for each corner when checking...
        // System.out.println("Collision checking bounds: " + leftMost + ", " + topMost
        // + " to " + rightMost + ", " + bottomMost);

        return Collisions.leftHandSideTest(otherLeftMost, otherTopMost, otherLeftMost, otherBottomMost, rightMost,
                center.getY()) && // Left side of other
                Collisions.leftHandSideTest(otherLeftMost, otherBottomMost, otherRightMost, otherBottomMost,
                        center.getX(), topMost)
                && // Bottom of other
                Collisions.leftHandSideTest(otherRightMost, otherBottomMost, otherRightMost, otherTopMost, leftMost,
                        center.getY())
                && // Right side of other
                Collisions.leftHandSideTest(otherRightMost, otherTopMost, otherLeftMost, otherTopMost, center.getX(),
                        bottomMost); // Top side of other
    }

    public void setHitBox(HitBox hitbox) {
        this.hitbox = hitbox;
    }

    protected HitBox getHitBox() {
        return hitbox;
    }

    protected Point2D.Double getHitBoxLocation() {
        return getHitBox().getCenterOfHitBox(location, sizeInBlocks);
    }

    protected Point2D.Double getHitBoxSizeInBlocks() {
        return getHitBox().getHitBoxSize(sizeInBlocks);
    }

    @Override
    public Color getColor() {
        return this.colorToMap;
    }

    @Override
    public boolean isDisplaying() {
        return this.shouldMap;
    }

    @Override
    public void gotHit(Touchable m, Map map) {
        if (isDestroyed()) {
            map.removeTouchable(this);
        }
    }

    protected void lowerHealth() {
        this.health--;
    }

    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }
}
