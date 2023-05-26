package scenes;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.Constants.App;
import helpz.LoadSave;

import static helpz.Constants.Tiles.ROAD_TILE;
import static helpz.Constants.App.SIZE_TILE;

import main.Game;
import enemies.Enemy;
import ui.ActionBar;

import objects.Tower;
import objects.PathPoint;

import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;

public class Playing extends GameScene {

  public Playing(Game game) {
    super(game);

    loadDefaultLevel();
    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    actionBar = new ActionBar(0, toolBarY, App.WIDTH, App.BOTTOM_BAR_HEIGHT, this);

    enemyManager = new EnemyManager(this, start, end);
    towerManager = new TowerManager(this);
    projectileManager = new ProjectileManager(this);
    waveManager = new WaveManager(this);
  }

  private int[][] level;
  
  private int mouseX, mouseY;
  
  private final ActionBar actionBar;
  
  private PathPoint start, end;
  
  private Tower selectedTower;

  private final EnemyManager enemyManager;
  private final TowerManager towerManager;
  private final ProjectileManager projectileManager;
  private final WaveManager waveManager;

  
  private void drawHighlight(Graphics g) {
    g.setColor(Color.PINK);
    g.drawRect(mouseX, mouseY, SIZE_TILE, SIZE_TILE);
  }

  private void drawSelectedTower(Graphics g) {
    if (selectedTower != null) {
      BufferedImage sprite = towerManager.getTowerImages()[selectedTower.getTowerType()];
      g.drawImage(sprite, mouseX, mouseY, null);
    }
  }

  private Tower getTowerAt() {
    return towerManager.getTowerAt(mouseX, mouseY);
  }

  private boolean isTileRoad() {
    int id = level[mouseY / SIZE_TILE][mouseX / SIZE_TILE];
    int type = game.getTileManager().getTile(id).getType();
    return type == ROAD_TILE;
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
            g.drawImage(getSpriteById(id, animationIndex), x * SIZE_TILE, y * SIZE_TILE, null);
          } else
            g.drawImage(getSpriteById(id), x * SIZE_TILE, y * SIZE_TILE, null);
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

  @Override
  public void mouseClicked(int x, int y) {
    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    if(y >= toolBarY) {
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

  @Override
  public void mouseMoved(int x, int y) {
    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    if(y >= toolBarY) {
      actionBar.mouseMoved(x, y);
    } else {
       mouseX = (x / SIZE_TILE) * SIZE_TILE;
      mouseY = (y / SIZE_TILE) * SIZE_TILE;
    }
  }

  @Override
  public void mousePressed(int x, int y) {
    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    if(y >= toolBarY) {
      actionBar.mousePressed(x, y);
    }
  }
  @Override
  public void mouseReleased(int x, int y) {
    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    if(y >= toolBarY) {
      actionBar.mouseReleased(x, y);
    }
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      selectedTower = null;
    }
  }

  public void update() {
    updateTick();
    waveManager.update();

    if (isAllEnemiesDead()) {
      if (waveManager.isThereMoreWaves()) {
        waveManager.setStartWaveTimer();

        if (waveManager.isWaveTimerOver())
          waveManager.increaseWaveIndex();
          waveManager.resetEnemyIndex();
          enemyManager.getEnemies().clear();
      }
    }

    if (isTimeForNewEnemy()) spawnEnemy();

    enemyManager.update();
    towerManager.update();
    projectileManager.update();
  }

  
  private boolean isAllEnemiesDead() {
    if (waveManager.isThereMoreEnemiesInWave()) return false;
    
    for (Enemy enemy : enemyManager.getEnemies()) {
      if (enemy.isAlive()) return false;
    }
    return true;
  }

  private void spawnEnemy() {
    enemyManager.spawnEnemy(waveManager.getNextEnemy());
  }

  private boolean isTimeForNewEnemy() {
    return waveManager.isTimeForNewEnemy()
      && waveManager.isThereMoreEnemiesInWave();
  }

  public void shootEnemy(Tower tower, Enemy enemy) {
    projectileManager.newProjectile(tower, enemy);
  }

  public int getTileType(int x, int y) {
    int xCord = x / SIZE_TILE;
    int yCord = y / SIZE_TILE;
    if (xCord < 0 || xCord > 19) return 0;
    if (yCord < 0 || yCord > 14) return 0;

    int id = level[y / SIZE_TILE][x / SIZE_TILE];
    return game.getTileManager().getTile(id).getType();
  }

  public EnemyManager getEnemyManager() {
    return enemyManager;
  }

  public TowerManager getTowerManager() {
    return towerManager;
  }

  public WaveManager getWaveManager() {
    return this.waveManager;
  }

  public void setLevel(int[][] level) {
    this.level = level;
  }

  public void setSelectedTower(Tower tower) {
    this.selectedTower = tower;
  }
}