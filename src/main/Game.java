package main;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Game extends JFrame {

  private GameScreen gameScreen;
  private BufferedImage image;
  public Game() {
    importImage();

    setSize(640, 640);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    gameScreen = new GameScreen(image);
    add(gameScreen);

    setVisible(true);
  }

  private void importImage() {
    InputStream stream = getClass().getResourceAsStream("/sprite-atlas.png");
    try {
      image = ImageIO.read(stream);
    } catch (IOException error) {
      error.printStackTrace();
    }
  }

  public static void main(String[] arguments) {
    Game game = new Game();
  }

}
