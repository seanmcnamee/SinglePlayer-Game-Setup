package app.game.gamefield.drawable;

import app.supportclasses.GameValues;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

/**
 * BTNode
 * 
 *
 */
public class Drawable  {
    protected Point2D.Double location;
    protected BufferedImage image;
    protected Point2D.Double sizeInBlocks;
    
    protected GameValues gameValues;

    public Drawable(GameValues gameValues, double x, double y, BufferedImage image, Point2D.Double sizeInBlocks) {
        this(gameValues, new Point2D.Double(x, y), image, sizeInBlocks);
    }

    public Drawable(GameValues gameValues, Point2D.Double location, BufferedImage image, Point2D.Double sizeInBlocks) {
        super();
        this.gameValues = gameValues;
        this.location = location;
        this.image = image;
        this.sizeInBlocks = sizeInBlocks;
    }

    public Drawable(GameValues gameValues, Point2D.Double location) {
        super();
        this.gameValues = gameValues;
        this.location = location;
    }

    public void render(Graphics g) {
        g.drawImage(getImage(), DrawingCalculator.findPixelLocation(getLocation().getX(), getSizeInBlocks().getX(), gameValues.fieldXZeroOffset, gameValues.singleSquareX), 
                        DrawingCalculator.findPixelLocation(getLocation().getY(), getSizeInBlocks().getY(), gameValues.fieldYZeroOffset, gameValues.singleSquareY), 
                        DrawingCalculator.findPixelSize(getSizeInBlocks().getX(), gameValues.singleSquareX), 
                        DrawingCalculator.findPixelSize(getSizeInBlocks().getY(), gameValues.singleSquareY), 
                                null);
    }

    protected Point2D.Double getSizeInBlocks() {
        return sizeInBlocks;
    }

    protected BufferedImage getImage() {
        return image;
    }

    protected void printDebug(String toPrint) {
        if (gameValues.debugMode) {
            System.out.println(toPrint);
        }
    }

    public Point2D.Double getLocation() {
        return (Point2D.Double) this.location.clone();
    }

}