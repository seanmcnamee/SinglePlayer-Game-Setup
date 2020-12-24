package app.supportclasses;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * BufferedImageLoader
 */
public class BufferedImageLoader {
    private BufferedImage image;

    public BufferedImageLoader(String path) {
        image = loadImage(path);
    }

    public BufferedImage loadImage(String path) {
        BufferedImage img = null;
		try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Can't find picture at: " + path + " - Path should start from main folder location!");
            e.printStackTrace();
        }
		return img;
    }

    public BufferedImage getImage() {
        return image;
    }

}