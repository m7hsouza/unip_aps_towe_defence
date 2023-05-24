package enemies;

import helpz.Constants;

import java.awt.Rectangle;

import static helpz.Constants.Direction.*;

public abstract class Enemy {
  protected float x, y;
  protected Rectangle bounds;
  protected float health, maxHealth;
  protected final int ID;
  protected  int enemyType;
  protected int lastDirection;
  protected boolean alive = true;

  public Enemy(float x, float y, int ID, int enemyType) {
    this.x = x;
    this.y = y;
    this.ID = ID;
    this.enemyType = enemyType;
    this.bounds = new Rectangle((int) x,(int) y, 32, 32);
    lastDirection = -1;
    setStartHealth();
  }

  public void move(float speed, int direction) {
    lastDirection = direction;
    switch (direction) {
      case LEFT -> this.x -= speed;
      case UP -> this.y -= speed;
      case RIGHT -> this.x += speed;
      case DOWN -> this.y += speed;
    }
    updateHitBox();
  }

  private void updateHitBox() {
    bounds.x = (int) x;
    bounds.y = (int) y;
  }

  protected void setStartHealth() {
    health = Constants.Enemies.GetStartHealth(enemyType);
    maxHealth = health;
  }

  public float getHealthBar() {
    return health / maxHealth;
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

  public float getHealth() {
    return health;
  }

  public int getID() {
    return ID;
  }

  public int getEnemyType() {
    return enemyType;
  }

  public int getLastDirection() { return lastDirection; }

  public boolean isAlive() {
    return alive;
  }

  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void hurt(float damage) {
    this.health -= damage;

    if (health <= 0) alive = false;
  }
}
