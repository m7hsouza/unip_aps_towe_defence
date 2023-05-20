package scenes;

import helpz.LoadSave;
import main.Game;
import objects.Tile;
import ui.ToolBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Editing extends GameScene implements SceneMethods {
  private int[][] level;
  private Tile selectedTile;
  private int mouseX, mouseY, lastTileX, lastTileY;
  private boolean drawSelected;
  private final ToolBar toolBar;

  public Editing(Game game) {
    super(game);

    this.toolBar = new ToolBar(0, 480, 640, 100, this);
    loadDefaultLevel();
  }

  @Override
  public void render(Graphics g) {
    drawLevel(g);
    toolBar.draw(g) ;
    drawSelectedTile(g);
  }

  @Override
  public void mouseClicked(int x, int y) {
    if(y > 480) {
      toolBar.mouseClicked(x, y);
    } else {
      changeTile(mouseX, mouseY);
    }
  }

  @Override
  public void mouseMoved(int x, int y) {
    if(y > 480) {
      toolBar.mouseMoved(x, y);
      drawSelected = false;
    } else {
      drawSelected = true;
      mouseX = (x / 32) * 32;
      mouseY = (y / 32) * 32;
    }
  }

  @Override
  public void mousePressed(int x, int y) {
    if (y >= 640)
      toolBar.mousePressed(x, y);
  }

  @Override
  public void mouseReleased(int x, int y) {
    toolBar.mouseReleased(x, y);
  }

  @Override
  public void mouseDragged(int x, int y) {
    if (y <= 480) {
      changeTile(x, y);
    }
  }

  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == KeyEvent.VK_R)
      toolBar.rotateSprite();
  }

  public void setSelectedTile(Tile selectedTile) {
    this.selectedTile = selectedTile;
    drawSelected = true;
  }

  public void saveLevel() {
    LoadSave.SaveLevel("new_level", level);
    game.getPlaying().setLevel(level);
  }

  public void update() {
    updateTick();
  }

  private void loadDefaultLevel() {
    level = LoadSave.GetLevelData("new_level");
  }

  private void drawLevel(Graphics g) {
    for (int y = 0; y < level.length; y++) {
      for (int x = 0; x < level[y].length; x++) {
        int id = level[y][x];
        if (isAnimation(id)) {
          g.drawImage(getSpriteById(id, animationIndex), x * 32, y * 32, null);
        } else
          g.drawImage(getSpriteById(id), x * 32, y * 32, null);
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

  private void drawSelectedTile(Graphics g) {
    if (selectedTile != null && drawSelected) {
      g.drawImage(selectedTile.getSprite(), mouseX, mouseY, 32, 32, null);
    }
  }

  private void changeTile(int x, int y) {
    if (selectedTile != null) {
      int tileX = x / 32;
      int tileY = y / 32;

      if (lastTileX == tileX && lastTileY == tileY) return;

      lastTileX = tileX;
      lastTileY = tileY;

      level[tileY][tileX] = selectedTile.getId();
    }
  }
}
