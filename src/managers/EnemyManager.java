package managers;

import enemies.*;
import helpz.LoadSave;
import objects.PathPoint;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;
import static helpz.Constants.Enemies.*;

public class EnemyManager {
  private final Playing playing;
  private final BufferedImage[] enemyImages;
  private final ArrayList<Enemy> enemies = new ArrayList<>();

  private final PathPoint start, end;
  private float HPbarWidth = 20f;

  public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
    this.playing = playing;
    this.start = start;
    this.end = end;
    enemyImages = new BufferedImage[4];
    addNewEnemy(ORC);
    addNewEnemy(BAT);
    addNewEnemy(KNIGHT);
    addNewEnemy(WOLF);

    loadEnemyImages();
  }

  public void addNewEnemy(int type) {
    int x = start.getxCord() * 32;
    int y = start.getyCord() * 32;

    switch (type) {
      case ORC -> enemies.add(new Orc(x, y,0));
      case BAT -> enemies.add(new Bat(x, y,0));
      case KNIGHT -> enemies.add(new Knight(x, y,0));
      case WOLF -> enemies.add(new Wolf(x, y,0));
    }
  }

  private void loadEnemyImages() {
    BufferedImage atlas = LoadSave.getSpriteAtlas();

    for (int i = 0; i < 4; i++) {
      enemyImages[i] = atlas.getSubimage(i * 32, 32, 32, 32);
    }
  }

  public void update() {
    for (Enemy enemy : enemies) {
      if (enemy.isAlive())
        updateEnemyMove(enemy);
    }
  }

  private void updateEnemyMove(Enemy enemy) {
    if (enemy.getLastDirection() == -1) setNewDirectionAndMove(enemy);

    int newX = (int) (enemy.getX() + getSpeedAndWidth(enemy.getLastDirection(), enemy.getEnemyType()));
    int newY = (int) (enemy.getY() + getSpeedAndHeight(enemy.getLastDirection(), enemy.getEnemyType()));

    if (getTileType(newX, newY) == WATER_TILE) {
      enemy.move(GetEnemySpeed(enemy.getEnemyType()), enemy.getLastDirection());
    } else if (isAtEnd(enemy)) {

    } else {
      setNewDirectionAndMove(enemy);
    }
  }

  private void setNewDirectionAndMove(Enemy enemy) {
    int direction = enemy.getLastDirection();

    int xCord = (int) (enemy.getX() / 32);
    int yCord = (int) (enemy.getY() / 32);

    fixEnemyOffsetTile(enemy, direction, xCord, yCord);

    if (isAtEnd(enemy)) return;

    if (direction == LEFT || direction == RIGHT) {
      int newY = (int) (enemy.getY() + getSpeedAndHeight(UP, enemy.getEnemyType()));
      if (getTileType((int) enemy.getX(), newY) == WATER_TILE)
        enemy.move(GetEnemySpeed(enemy.getEnemyType()), UP);
      else
        enemy.move(GetEnemySpeed(enemy.getEnemyType()), DOWN);
    } else {
      int newX = (int) (enemy.getX() + getSpeedAndWidth(RIGHT, enemy.getEnemyType()));
      if (getTileType(newX, (int) enemy.getY()) == WATER_TILE)
        enemy.move(GetEnemySpeed(enemy.getEnemyType()), RIGHT);
      else
        enemy.move(GetEnemySpeed(enemy.getEnemyType()), LEFT);

    }
  }

  private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
    switch (dir) {
      case RIGHT:
        if (xCord < 19)
          xCord++;
        break;
      case DOWN:
        if (yCord < 14)
          yCord++;
        break;
    }

    e.setPosition(xCord * 32, yCord * 32);

  }

  private boolean isAtEnd(Enemy enemy) {
    if (enemy.getX() == end.getxCord() * 32 && enemy.getY() == end.getyCord() * 32) {
      return true;
    }
    return false;
  }

  private int getTileType(int x, int y) {
    return playing.getTileType(x, y);
  }

  private float getSpeedAndWidth(int direction, int enemyType) {
    if (direction == LEFT) {
      return -GetEnemySpeed(enemyType);
    } else if (direction == RIGHT) {
      return GetEnemySpeed(enemyType) + 32;
    }
    return 0;
  }

  private float getSpeedAndHeight(int direction, int enemyType) {
    if (direction == UP) {
      return -GetEnemySpeed(enemyType);
    } else if (direction == DOWN) {
      return GetEnemySpeed(enemyType);
    }
    return 0;
  }

  public void draw(Graphics g) {
    for (Enemy enemy : enemies) {
      if (enemy.isAlive()) {
        drawEnemy(enemy, g);
        drawHealthBar(enemy, g);
      }
    }
  }

  private void drawHealthBar(Enemy enemy, Graphics g) {
    g.setColor(Color.RED);
    float barWidth = (int) getNewBarWidth(enemy);
    int x = (int) (enemy.getX() + 16 - (barWidth / 2));
    int y = (int) (enemy.getY() - 8);
    g.fillRect(x, y, (int) barWidth, 3);
  }

  private float getNewBarWidth(Enemy enemy) {
    return HPbarWidth * enemy.getHealthBar();
  }

  private void drawEnemy(Enemy enemy, Graphics g) {
    g.drawImage(enemyImages[enemy.getEnemyType()], (int) enemy.getX(), (int) enemy.getY(), null);
  }

  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }
}
