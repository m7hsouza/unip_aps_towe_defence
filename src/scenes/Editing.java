package scenes;

import helpz.LoadSave;
import main.Game;
import objects.PathPoint;
import objects.Tile;
import ui.ToolBar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Tiles.*;
import static helpz.Constants.App;
import static helpz.Constants.App.SIZE_TILE;

public class Editing extends GameScene {
  private int[][] level;
  private Tile selectedTile;
  private int mouseX, mouseY, lastTileX, lastTileY;
  private boolean drawSelected;
  private final ToolBar toolBar;
  private PathPoint start, end;

  public Editing(Game game) {
    super(game);

    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    this.toolBar = new ToolBar(0, toolBarY, App.WIDTH, App.BOTTOM_BAR_HEIGHT, this);
    loadDefaultLevel();
  }

  @Override
  public void render(Graphics g) {
    drawLevel(g);
    toolBar.draw(g) ;
    drawSelectedTile(g);
    drawPathPoints(g);
  }

  private void drawPathPoints(Graphics g) {
    if (start != null) {
      g.drawImage(toolBar.getPathStart(), start.getxCord() * SIZE_TILE, start.getyCord() * SIZE_TILE, SIZE_TILE, SIZE_TILE, null);
    }

    if (end != null) {
      g.drawImage(toolBar.getPathEnd(), end.getxCord() * SIZE_TILE, end.getyCord() * SIZE_TILE, SIZE_TILE, SIZE_TILE, null);
    }
  }

  @Override
  public void mouseClicked(int x, int y) {
    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    if(y > toolBarY) {
      toolBar.mouseClicked(x, y);
    } else {
      changeTile(mouseX, mouseY);
    }
  }

  @Override
  public void mouseMoved(int x, int y) {
    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    if(y > toolBarY) {
      toolBar.mouseMoved(x, y);
      drawSelected = false;
    } else {
      drawSelected = true;
      mouseX = (x / SIZE_TILE) * SIZE_TILE;
      mouseY = (y / SIZE_TILE) * SIZE_TILE;
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
    int toolBarY = App.HEIGHT - App.BOTTOM_BAR_HEIGHT;
    if (y <= toolBarY) {
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
    LoadSave.SaveLevel("new_level", level, start, end);
    game.getPlaying().setLevel(level);
  }

  public void update() {
    updateTick();
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
      for (int x = 0; x < level[0].length; x++) {
        int id = level[y][x];
        if (isAnimation(id)) {
          g.drawImage(getSpriteById(id, animationIndex), x * SIZE_TILE, y * SIZE_TILE, SIZE_TILE, SIZE_TILE, null);
        } else
          g.drawImage(getSpriteById(id), x * SIZE_TILE, y * SIZE_TILE, SIZE_TILE, SIZE_TILE, null);
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
      g.drawImage(selectedTile.getSprite(), mouseX, mouseY, SIZE_TILE, SIZE_TILE, null);
    }
  }

  private void changeTile(int x, int y) {
    if (selectedTile != null) {
      int tileX = x / SIZE_TILE;
      int tileY = y / SIZE_TILE;

      if (selectedTile.getId() >= 0) {
        if (lastTileX == tileX && lastTileY == tileY) return;
        lastTileX = tileX;
        lastTileY = tileY;
        level[tileY][tileX] = selectedTile.getId();
      } else {
         int id = level[tileY][tileX];
         if (game.getTileManager().getTile(id).getType() == WATER_TILE) {
           if (selectedTile.getType() == -1) {
             start = new PathPoint(tileX, tileY);
           } else {
             end = new PathPoint(tileX, tileY);
           }
         }
      }

    }
  }
}
