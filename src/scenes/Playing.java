package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {
  private int[][] level;
  private final ActionBar actionBar;
  private int mouseX, mouseY;
  private final EnemyManager enemyManager;

  public Playing(Game game) {
    super(game);

    loadDefaultLevel();
    actionBar = new ActionBar(0, 480, 640, 100, this);

    enemyManager = new EnemyManager(this);
  }
  @Override
  public void render(Graphics g) {
    drawLevel(g);
    actionBar.draw(g);
    enemyManager.draw(g);
  }

  @Override
  public void mouseClicked(int x, int y) {
    if(y >= 480) {
      actionBar.mouseClicked(x, y);
    } else {
      enemyManager.addNewEnemy(x, y);
    }
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
    enemyManager.update();
  }

  private void loadDefaultLevel() {
    level = LoadSave.GetLevelData("new_level");
  }

  private void drawLevel(Graphics g) {
    for (int y = 0; y < level.length; y++) {
      for (int x = 0; x < level[y].length; x++) {
        int id = level[y][x];
        g.drawImage(getSpriteById(id), x * 32, y * 32, null);
      }
    }
  }

  private BufferedImage getSpriteById(int id) {
    return getGame().getTileManager().getSprite(id);
  }

  public int getTileType(int x, int y) {
    int xCord = x / 32;
    int yCord = y / 32;
    if (xCord < 0 || xCord > 19) return 0;
    if (yCord < 0 || yCord > 14) return 0;

    int id = level[y / 32][x / 32];
    return game.getTileManager().getTile(id).getType();
  }
}