package managers;

import enemies.Enemy;
import helpz.LoadSave;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;

public class EnemyManager {
  private final Playing playing;
  private final BufferedImage[] enemyImages;
  private final ArrayList<Enemy> enemies = new ArrayList<>();
  private final float speed = 0.5f;

  public EnemyManager(Playing playing) {
    this.playing = playing;
    enemyImages = new BufferedImage[4];
    addNewEnemy(3 * 32, 9 * 32);

    loadEnemyImages();
  }

  public void addNewEnemy(int x, int y) {
    enemies.add(new Enemy(x, y, 0, 0));
  }

  private void loadEnemyImages() {
    BufferedImage atlas = LoadSave.getSpriteAtlas();
    enemyImages[0] = atlas.getSubimage(0, 32, 32, 32);
    enemyImages[1] = atlas.getSubimage(32, 32, 32, 32);
    enemyImages[2] = atlas.getSubimage(2 * 32, 32, 32, 32);
    enemyImages[3] = atlas.getSubimage(3 * 32, 32, 32, 32);
  }

  public void update() {
    for (Enemy enemy : enemies) {
      if (isNextTileRoad(enemy)) {
        //TODO: Change this. The return is pointless here.
        // Brain farts sometimes when you code and record at the
        // same time.
      }
    }
  }

  private boolean isNextTileRoad(Enemy e) {
    int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDirection()));
    int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDirection()));

    if (getTileType(newX, newY) == ROAD_TILE) {
      e.move(speed, e.getLastDirection());
    } else if (isAtEnd(e)) {

    } else {
      setNewDirectionAndMove(e);
    }
    return false;
  }

  private void setNewDirectionAndMove(Enemy e) {
    int direction = e.getLastDirection();

    int xCord = (int) (e.getX() / 32);
    int yCord = (int) (e.getY() / 32);

    fixEnemyOffsetTile(e, direction, xCord, yCord);

    if (direction == LEFT || direction == RIGHT) {
      int newY = (int) (e.getY() + getSpeedAndHeight(UP));
      if (getTileType((int) e.getX(), newY) == ROAD_TILE)
        e.move(speed, UP);
      else
        e.move(speed, DOWN);
    } else {
      int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT));
      if (getTileType(newX, (int) e.getY()) == ROAD_TILE)
        e.move(speed, RIGHT);
      else
        e.move(speed, LEFT);

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
    return false;
  }

  private int getTileType(int x, int y) {
    return playing.getTileType(x, y);
  }

  private float getSpeedAndWidth(int direction) {
    if (direction == LEFT) {
      return -speed;
    } else if (direction == RIGHT) {
      return speed + 32;
    }
    return 0;
  }

  private float getSpeedAndHeight(int direction) {
    if (direction == UP) {
      return -speed;
    } else if (direction == DOWN) {
      return speed;
    }
    return 0;
  }

  public void draw(Graphics g) {
    for (Enemy enemy : enemies) {
      drawEnemy(enemy, g);
    }
  }

  private void drawEnemy(Enemy enemy, Graphics g) {
    g.drawImage(enemyImages[0], (int) enemy.getX(), (int) enemy.getY(), null);
  }
}
