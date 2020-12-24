package app.supportclasses;

import java.awt.image.BufferedImage;

import java.awt.Point;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.RescaleOp;
import java.awt.Color;

/**
 * Button
 */
public class Button {
    /**
     *
     */
    private BufferedImage image;
    private Point2D.Double percentSize;
    private Color color;
    private boolean isHovering;
    private Point pictureCenterLocation;
    private GameValues gameValues;

    /**
     * This point will be the CENTER of the button (so other things don't have to figure it out)
     */
    public Button(BufferedImage i, Point location, GameValues gameValues) {
        init(location, gameValues);
        image = i;
    }

    public Button(BufferedImage i, int x, int y, GameValues gameValues) {
        this(i, new Point(x, y), gameValues);
    }

    public Button(Point2D.Double size, Point location, Color color, GameValues gameValues) {
        init(location, gameValues);
        this.color = color;
        this.percentSize = size;
    }

    public Button(double widthPercent, double heightPercent, int x, int y, Color color, GameValues gameValues) {
        this(new Point2D.Double(widthPercent, heightPercent), new Point(x, y), color, gameValues);
    }

    private void init(Point p, GameValues gameValues) {
        isHovering = false;
        this.gameValues = gameValues;
        pictureCenterLocation = p;
    }

    public void render(Graphics g) {

        double leftMost = pictureCenterLocation.getX()-getXSize()/2.0;
        double topMost = pictureCenterLocation.getY()-getYSize()/2.0;
        if (image != null) {
            g.drawImage(image, (int)(leftMost*gameValues.gameScale), (int)(topMost*gameValues.gameScale), (int)(image.getWidth()*gameValues.gameScale), (int)(image.getHeight()*gameValues.gameScale), null);
        }   else {
            g.setColor(this.color);
            g.fillRect((int)(leftMost*gameValues.gameScale), (int)(topMost*gameValues.gameScale), (int)(getXSize()*gameValues.gameScale), (int)(getYSize()*gameValues.gameScale));
        }
    }

    /**
     * 
     * @param other : The point to be checked
     * @return true only if 'other' is within the rectangle created by the corners of the picture
     * Uses the left Hand Side test (would work for any convex polygon)
     */
    public boolean contains(Point other) {
        

        double leftMost = gameValues.gameScale*(pictureCenterLocation.getX()-getXSize()/2.0);
        double topMost = gameValues.gameScale*(pictureCenterLocation.getY()-getYSize()/2.0);
        double rightMost = gameValues.gameScale*(pictureCenterLocation.getX()+getXSize()/2.0);
        double bottomMost = gameValues.gameScale*(pictureCenterLocation.getY()+getYSize()/2.0);

        //System.out.println("Collision checking bounds: " + leftMost + ", " + topMost + " to " + rightMost + ", " + bottomMost);
        return Collisions.leftHandSideTest(leftMost, topMost, leftMost, bottomMost, other.getX(), other.getY()) &&
            Collisions.leftHandSideTest(leftMost, bottomMost, rightMost, bottomMost, other.getX(), other.getY()) &&
            Collisions.leftHandSideTest(rightMost, bottomMost, rightMost, topMost, other.getX(), other.getY()) &&
            Collisions.leftHandSideTest(rightMost, topMost, leftMost, topMost, other.getX(), other.getY());
        
    }

    public void setHovering(boolean b) {
        if (b != isHovering) {
            if (b) {
                //Make image darker
                if (this.image != null) {
                    RescaleOp op = new RescaleOp(1, gameValues.LIGHTEN_DARKEN_AMOUNT, null);
                    image = op.filter(image, null);
                } else {
                    this.color = Color.DARK_GRAY;
                    //this.color = new Color(Math.min(255,gameValues.DARKEN_VALUE*color.getRed()), Math.min(255,gameValues.DARKEN_VALUE*color.getGreen()), Math.min(255,gameValues.DARKEN_VALUE*color.getBlue()));
                }
            }   else {
                //Make image brighter
                if (this.image != null) {
                    RescaleOp op = new RescaleOp(1, -gameValues.LIGHTEN_DARKEN_AMOUNT, null);
                    image = op.filter(image, null);
                } else {
                    this.color = Color.GRAY;
                    //this.color = new Color(gameValues.LIGHTEN_VALUE*color.getRed(), gameValues.LIGHTEN_VALUE*color.getGreen(), gameValues.LIGHTEN_VALUE*color.getBlue());
                }
                
            }
            isHovering = b;
        }
        
    }

    public double getXSize() {
        if (image != null) {
            return image.getWidth();
        }
        return gameValues.WIDTH_SCALE_1*percentSize.x;
    }

    public double getYSize() {
        if (image != null) {
            return image.getHeight();
        }
        return gameValues.WIDTH_SCALE_1*percentSize.y;
    }

    public boolean isHovering() {
        return isHovering;
    }
}