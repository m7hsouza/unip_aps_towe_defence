package objects;

import java.awt.geom.Point2D;

public class Projectile {
  private final int ID, type;

  private final float speedX, speedY, rotate, damage;

  private Point2D.Float position;

  private boolean active;

  public Projectile(float x, float y, float speedX, float speedY, float damage, float rotate, int ID, int type) {
    this.position = new Point2D.Float(x, y);
    this.speedX = speedX;
    this.speedY = speedY;
    this.damage = damage;
    this.rotate = rotate;
    this.ID = ID;
    this.type = type;
    this.active = true;
  }

  public void move() {
    position.x += speedX;
    position.y += speedY;
  }

  public Point2D.Float getPosition() {
    return position;
  }

  public int getID() {
    return ID;
  }

  public int getType() {
    return type;
  }

  public boolean isActive() {
    return active;
  }

  public float getDamage() {
    return damage;
  }

  public float getRotate() {
    return rotate;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
