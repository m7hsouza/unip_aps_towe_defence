package helpz;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImgFix {
  public static BufferedImage getRotateImage(BufferedImage image, int angle) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage newImage = new BufferedImage(width, height, image.getType());
    Graphics2D g2d = newImage.createGraphics();
    g2d.rotate(Math.toRadians(angle), (double) width / 2, (double) height / 2);
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();
    return newImage;
  }

  public static BufferedImage buildImages(BufferedImage[] images) {
    int width = images[0].getWidth();
    int height = images[0].getHeight();
    BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
    Graphics2D g2d = newImage.createGraphics();
    for (BufferedImage image : images) {
      g2d.drawImage(image, 0, 0, null);
    }
    g2d.dispose();
    return newImage;
  }

  public static BufferedImage getBuildRotateImage(BufferedImage[] images, int angle, int rotateAtIndex) {
    int width = images[0].getWidth();
    int height = images[0].getHeight();
    BufferedImage newImage = new BufferedImage(width, height, images[0].getType());
    Graphics2D g2d = newImage.createGraphics();
    for (int i = 0; i < images.length; i++) {
      if (rotateAtIndex == i) {
        g2d.rotate(Math.toRadians(angle), (double) width / 2, (double) height / 2);
      }
      g2d.drawImage(images[i], 0, 0, null);
      if (rotateAtIndex == i) {
        g2d.rotate(Math.toRadians(-angle), (double) width / 2, (double) height / 2);
      }
    }
    g2d.dispose();
    return newImage;
  }

}
