package objects;

import java.awt.image.BufferedImage;

public class Tile {
  public Tile(BufferedImage sprite, int id, String name) {
    this.sprite = sprite;
    this.id = id;
    this.name = name;
  }

  private BufferedImage sprite;
  private int id;
  private String name;

  public BufferedImage getSprite() {
    return sprite;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
