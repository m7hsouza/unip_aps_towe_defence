package scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import helpz.LoadSave;
import main.Game;
import ui.ActionBar;

public class Playing extends GameScene implements SceneMethods {
  public Playing(Game game) {
    super(game);

    loadDefaultLevel();
    actionBar = new ActionBar(0, 480, 640, 100, this);

  }

  private int[][] level;
  private ActionBar actionBar;
  private int mouseX, mouseY;



  private void loadDefaultLevel() {
    level = LoadSave.GetLevelData("new_level");
  }

  @Override
  public void render(Graphics g) {
    for (int y = 0; y < level.length; y++) {
      for (int x = 0; x < level[y].length; x++) {
        int id = level[y][x];
        g.drawImage(getSpriteById(id), x * 32, y * 32, null);
      }
    }

    actionBar.draw(g);
  }

  private BufferedImage getSpriteById(int id) {
    return getGame().getTileManager().getSprite(id);
  }

  @Override
  public void mouseClicked(int x, int y) {
    if(y > 480) {
      actionBar.mouseClicked(x, y);
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
}