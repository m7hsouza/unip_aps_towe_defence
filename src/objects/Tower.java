package objects;

import static helpz.Constants.Towers;
public class Tower {
  private final int x;
  private final int y;
  private final int ID;
  private final int towerType;
  private float range, damage, coolDown;
  private int coolDownTick;

  public Tower(int x, int y, int ID, int towerType) {
    this.x = x;
    this.y = y;
    this.ID = ID;
    this.towerType = towerType;
    setDefaultDamage();
    setDefaultRange();
    setDefaultCoolDown();

  }

  private void setDefaultDamage() {
    damage = Towers.GetStartDamage(towerType);
  }

  private void setDefaultRange() {
    range = Towers.GetStartRange(towerType);
  }

  private void setDefaultCoolDown() {
    coolDown = Towers.GetStartCoolDown(towerType);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getID() {
    return ID;
  }

  public int getTowerType() {
    return towerType;
  }

  public float getRange() {
    return range;
  }

  public float getDamage() {
    return damage;
  }

  public float getCoolDown() {
    return coolDown;
  }

  public void setRange(float range) {
    this.range = range;
  }

  public void setDamage(float damage) {
    this.damage = damage;
  }

  public void setCoolDown(float coolDown) {
    this.coolDown = coolDown;
  }

  public boolean isCoolDownOver() {
    return coolDownTick >= coolDown;
  }

  public void resetCoolDown() {
    coolDownTick = 0;
  }

  public void update() {
    coolDownTick++;
  }
}
