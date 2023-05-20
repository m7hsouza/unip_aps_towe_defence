package objects;

import java.awt.image.BufferedImage;

public class Tile {
  public Tile(BufferedImage[] sprite, int id, int type) {
    this.sprite = sprite;
    this.id = id;
    this.type = type;
  }

  public Tile(BufferedImage sprite, int id, int type) {
    this.sprite = new BufferedImage[1];
    this.sprite[0] = sprite;
    this.id = id;
    this.type = type;
  }

  private final BufferedImage[] sprite;
  private final int id, type;

  public BufferedImage getSprite() {
    return sprite[0];
  }

  public BufferedImage getSprite(int animationIndex) {
    return sprite[animationIndex];
  }

  public int getId() {
    return id;
  }

  public boolean isAnimation() {
    return sprite.length > 1;
  }

  public int getType() {
    return type;
  }
}
