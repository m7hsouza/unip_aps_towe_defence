package enemies;

import java.awt.Graphics;
import java.awt.Rectangle;

import static helpz.Constants.Direction.*;

public class Enemy {
  private float x, y;
  private Rectangle bounds;
  private int health;
  private final int ID;
  private final int enemyType;
  private int lastDirection;

  public Enemy(float x, float y, int ID, int enemyType) {
    this.x = x;
    this.y = y;
    this.ID = ID;
    this.enemyType = enemyType;
    this.bounds = new Rectangle((int) x,(int) y, 32, 32);
    lastDirection = RIGHT;
  }

  public void move(float speed, int direction) {
    lastDirection = direction;
    switch (direction) {
      case RIGHT -> {
        this.x += speed;
      }
      case LEFT -> {
        this.x -= speed;
      }
      case UP -> {
        this.y -= speed;
      }
      case DOWN -> {
        this.y += speed;
      }
    }
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public Rectangle getBounds() {
    return bounds;
  }

  public int getHealth() {
    return health;
  }

  public int getID() {
    return ID;
  }

  public int getEnemyType() {
    return enemyType;
  }

  public int getLastDirection() { return lastDirection; }

  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
