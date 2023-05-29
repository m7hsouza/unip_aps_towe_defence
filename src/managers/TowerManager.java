package managers;

import enemies.Enemy;
import helpz.LoadSave;
import helpz.Utilz;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.App.SIZE_TILE;

public class TowerManager {
  private Playing playing;
  private BufferedImage[] towerImages;
  private ArrayList<Tower> towers = new ArrayList<>();
  private int towerAmount = 0;
  public TowerManager(Playing playing) {
    this.playing = playing;

    loadTowerImages();
  }

  private void loadTowerImages() {
    BufferedImage atlas = LoadSave.getSpriteAtlas();
    towerImages = new BufferedImage[2];
    for (int i = 0; i < 2; i++) {
      towerImages[i] = atlas.getSubimage((4 + i) * SIZE_TILE, SIZE_TILE, SIZE_TILE, SIZE_TILE);
    }
  }

  public void draw(Graphics g) {
    for (Tower tower : towers) {
      BufferedImage sprite = towerImages[tower.getTowerType()];
      g.drawImage(sprite, tower.getX(), tower.getY(), null);
    }
  }

  public void update() {
    for (Tower tower : towers) {
      tower.update();
      attackEnemyIfClose(tower);
    }

  }

  private void attackEnemyIfClose(Tower tower) {
    ArrayList<Enemy> enemies = playing.getEnemyManager().getEnemies();
    for (Enemy enemy : enemies) {
      if (enemy.isAlive())
        if (isEnemyInRange(tower, enemy)) {
          if (tower.isCoolDownOver()) {
            playing.shootEnemy(tower, enemy);
            tower.resetCoolDown();
          }
        }
    }
  }

  private boolean isEnemyInRange(Tower tower, Enemy enemy) {
    Point origin = new Point(tower.getX(), tower.getY());
    Point target = new Point((int) enemy.getX(), (int) enemy.getY());
    float distance = Utilz.GetDistance(origin, target);
    return distance < tower.getRange();
  }

  public BufferedImage[] getTowerImages() {
    return towerImages;
  }

  public void addTower(Tower tower, int x, int y) {
    towers.add(new Tower(x, y, towerAmount++, tower.getTowerType()));
  }

  public Tower getTowerAt(int x, int y) {
    for (Tower tower : towers) {
      if (tower.getX() == x && tower.getY() == y) return tower;
    }
    return null;
  }
}
