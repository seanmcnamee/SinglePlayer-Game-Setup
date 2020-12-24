package app.supportclasses;

import java.awt.image.BufferedImage;
import java.awt.Point;

/**
 * SpriteSheet, deals with all the pictures for the game
 */
public class SpriteSheet {
    private BufferedImageLoader imageLoader;

    public SpriteSheet(String path) {
        imageLoader = new BufferedImageLoader(path);
    }

    public SpriteSheet() {
        imageLoader = null;
    }

    public void loadSpriteSheet(String path) {
        imageLoader = new BufferedImageLoader(path);
    }

    public BufferedImage grabImage(Point location, Point dimensions, int size) {
        return grabImage(location.x, location.y, dimensions.x, dimensions.y, size);
    }

    /**
     * returns an image of designated size and at certain position within the
     * spritesheet. All start with 0,0
     * 
     * @param col    - x position
     * @param row    - y position
     * @param width  - width of image
     * @param height - height of image
     * @param SIZE   - number of pixels defined by '1' unit
     */
    public BufferedImage grabImage(int col, int row, int width, int height, int size) {
        /**
         * 'size' is the size of a single box in the spreadsheet. Each box in the
         * spreadsheet is seperated by a 1 thick line (unless its the same image). So
         * square should technically be the smallest size of a picture you use in your
         * game, But other things should be multiples of that size. The top left image
         * is at 0,0 and moves like an array does in rows and collums
         */

        // Location
        int x = col * (size + 1);
        int y = row * (size + 1);
        // Size
        int xSize = width * (size + 1) - 1;
        int ySize = height * (size + 1) - 1;

        BufferedImage img = imageLoader.getImage().getSubimage(x, y, xSize, ySize);
        return img;
    }

    /**
     * Creates and returns the smallest image containing all non-transparents pixels
     * 
     * @param image
     */
    public BufferedImage shrink(BufferedImage image) {
        int leftMost = -1;
        int rightMost = -1;
        int topMost = -1;
        int bottomMost = -1;

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        // Go through array and find corners of smaller inside picture
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {

                int pixel = image.getRGB(x, y);
                if (((pixel >> 24) & 0xff) > 0) {
                    if (leftMost == -1 || leftMost > x) {
                        leftMost = x;
                    }
                    if (rightMost < x) {
                        rightMost = x;
                    }

                    if (topMost == -1 || topMost > y) {
                        topMost = y;
                    }
                    if (bottomMost < y) {
                        bottomMost = y;
                    }
                }
            }
        }

        // Create new, smaller BufferedImage
        int width = rightMost - leftMost;
        int height = bottomMost - topMost;
        BufferedImage smallerImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                smallerImage.setRGB(x, y, image.getRGB(x + leftMost, y + topMost));
            }
        }

        return smallerImage;
    }

    public static BufferedImage flipLeftRight(BufferedImage image) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        
        BufferedImage flippedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        //System.out.println("Image: " + imgWidth + ", " + imgHeight);

        //Go through array and flip pixels
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                int oldRGB = image.getRGB(imgWidth-x-1, y);
                flippedImage.setRGB(x, y, oldRGB);
            }
        }

        return flippedImage;
    }

    public static BufferedImage flipTopBottom(BufferedImage image) {
        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();
        
        BufferedImage flippedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        //System.out.println("Image: " + imgWidth + ", " + imgHeight);

        //Go through array and flip pixels
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                int oldRGB = image.getRGB(x, imgHeight-y-1);
                flippedImage.setRGB(x, y, oldRGB);
            }
        }

        return flippedImage;
    }

    public static BufferedImage rotateCounter90(BufferedImage image) {
        int imgHeight = image.getWidth();
        int imgWidth = image.getHeight();
        
        BufferedImage rotatedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        //System.out.println("Image: " + imgWidth + ", " + imgHeight);

        //Go through array and flip pixels
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                int oldRGB = image.getRGB(imgHeight-y-1, x);
                rotatedImage.setRGB(x, y, oldRGB);
            }
        }

        return rotatedImage;
    }

    public static BufferedImage rotateClock90(BufferedImage image) {
        int imgHeight = image.getWidth();
        int imgWidth = image.getHeight();
        
        BufferedImage rotatedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        //System.out.println("Image: " + imgWidth + ", " + imgHeight);

        //Go through array and flip pixels
        for (int y = 0; y < imgHeight; y++) {
            for (int x = 0; x < imgWidth; x++) {
                int oldRGB = image.getRGB(y, imgWidth-1-x);
                rotatedImage.setRGB(x, y, oldRGB);
            }
        }

        return rotatedImage;
    }

}