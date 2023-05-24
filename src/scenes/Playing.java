package scenes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;
import static helpz.Constants.Tiles.ROAD_TILE;

public class Playing extends GameScene implements SceneMethods {
  private int[][] level;
  private final ActionBar actionBar;
  private int mouseX, mouseY;
  private final EnemyManager enemyManager;
  private final TowerManager towerManager;
  private final ProjectileManager projectileManager;
  private PathPoint start, end;
  private Tower selectedTower;

  public Playing(Game game) {
    super(game);

    loadDefaultLevel();
    actionBar = new ActionBar(0, 480, 640, 100, this);

    enemyManager = new EnemyManager(this, start, end);
    towerManager = new TowerManager(this);
    projectileManager = new ProjectileManager(this);
  }
  @Override
  public void render(Graphics g) {
    drawLevel(g);
    actionBar.draw(g);
    enemyManager.draw(g);
    towerManager.draw(g);
    projectileManager.draw(g);
    drawSelectedTower(g);
    drawHighlight(g);
  }

  private void drawHighlight(Graphics g) {
    g.setColor(Color.PINK);
    g.drawRect(mouseX, mouseY, 32, 32);
  }

  private void drawSelectedTower(Graphics g) {
    if (selectedTower != null) {
      BufferedImage sprite = towerManager.getTowerImages()[selectedTower.getTowerType()];
      g.drawImage(sprite, mouseX, mouseY, null);
    }
  }

  @Override
  public void mouseClicked(int x, int y) {
    if(y >= 480) {
      actionBar.mouseClicked(x, y);
    } else
      if (selectedTower != null) {
        if (isTileRoad() && getTowerAt() == null) {
          towerManager.addTower(selectedTower, mouseX, mouseY);
          selectedTower = null;
        }
      } else {
        Tower tower = getTowerAt();
        actionBar.displayTower(tower);
      }
  }

  private Tower getTowerAt() {
    return towerManager.getTowerAt(mouseX, mouseY);
  }

  private boolean isTileRoad() {
    int id = level[mouseY / 32][mouseX / 32];
    int type = game.getTileManager().getTile(id).getType();
    return type == ROAD_TILE;
  }

  @Override
  public void mouseMoved(int x, int y) {
    if(y > 480) {
      actionBar.mouseMoved(x, y);
    } else {
       mouseX = (x / 32) * 32;
      mouseY = (y / 32) * 32;
    }
  }

  @Override
  public void mousePressed(int x, int y) {
    if(y > 480) {
      actionBar.mousePressed(x, y);
    }
  }
  @Override
  public void mouseReleased(int x, int y) {
    if(y > 480) {
      actionBar.mouseReleased(x, y);
    }
  }

  @Override
  public void mouseDragged(int x, int y) {

  }

  public void setLevel(int[][] level) {
    this.level = level;
  }

  public void update() {
    updateTick();
    enemyManager.update();
    towerManager.update();
    projectileManager.update();
  }

  private void loadDefaultLevel() {
    level = LoadSave.GetLevelData("new_level");
    ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("new_level");
    assert points != null;
    start = points.get(0);
    end = points.get(1);
  }

  private void drawLevel(Graphics g) {
    for (int y = 0; y < level.length; y++) {
      for (int x = 0; x < level[y].length; x++) {
        if (y < 15) {
          int id = level[y][x];
          if (isAnimation(id)) {
            g.drawImage(getSpriteById(id, animationIndex), x * 32, y * 32, null);
          } else
            g.drawImage(getSpriteById(id), x * 32, y * 32, null);
        }
      }
    }
  }

  private boolean isAnimation(int spriteID) {
    return game.getTileManager().isSpriteAnimation(spriteID);
  }

  private BufferedImage getSpriteById(int id) {
    return getGame().getTileManager().getSprite(id);
  }
  private BufferedImage getSpriteById(int id, int animationIndex) {
    return getGame().getTileManager().getAnimationSprite(id, animationIndex);
  }

  public int getTileType(int x, int y) {
    int xCord = x / 32;
    int yCord = y / 32;
    if (xCord < 0 || xCord > 19) return 0;
    if (yCord < 0 || yCord > 14) return 0;

    int id = level[y / 32][x / 32];
    return game.getTileManager().getTile(id).getType();
  }

  public TowerManager getTowerManager() {
    return towerManager;
  }

  public void setSelectedTower(Tower tower) {
    this.selectedTower = tower;
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      selectedTower = null;
    }
  }

  public EnemyManager getEnemyManager() {
    return enemyManager;
  }

  public void shootEnemy(Tower tower, Enemy enemy) {
    projectileManager.newProjectile(tower, enemy);
  }
}