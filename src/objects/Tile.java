package objects;

import java.awt.image.BufferedImage;

public class Tile {
  private final BufferedImage[] sprites;

  private final int id, type;

  public Tile(BufferedImage[] sprites, int id, int type) {
    this.sprites = sprites;
    this.id = id;
    this.type = type;
  }

  public Tile(BufferedImage sprite, int id, int type) {
    this.sprites = new BufferedImage[1];
    this.sprites[0] = sprite;
    this.id = id;
    this.type = type;
  }

  public BufferedImage getSprite() {
    return sprites[0];
  }

  public BufferedImage getSprite(int animationIndex) {
    return sprites[animationIndex];
  }

  public int getId() {
    return id;
  }

  public boolean isAnimation() {
    return sprites.length > 1;
  }

  public int getType() {
    return type;
  }
}
