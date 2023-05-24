package ui;

import helpz.LoadSave;
import objects.Tile;
import scenes.Editing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static main.GameStates.MENU;
import static main.GameStates.setGameState;

public class ToolBar extends Bar {
  private Editing editing;
  private MyButton bMenu, bSave;
  private Map<MyButton, ArrayList<Tile>> map = new HashMap<MyButton, ArrayList<Tile>>();
  private MyButton bGrass, bWater, bRoad;
  private MyButton bPathStart, bPathEnd;
  private BufferedImage pathStart, pathEnd;
  private MyButton currentButton;
  private int currentIndex = 0;
  private Tile selectedTile;
   public ToolBar(int x, int y, int width, int height, Editing editing) {
    super(x, y, width, height);

    initPathImages();
    this.editing = editing;
    initButtons();
  }

  private void initPathImages() {
     BufferedImage atlas = LoadSave.getSpriteAtlas();
     pathStart = atlas.getSubimage(7 * 32, 2 * 32, 32, 32);
     pathEnd = atlas.getSubimage(8 * 32, 2 * 32, 32 ,32);
  }

  private void initButtons() {
    bMenu = new MyButton("Menu", 8, 488 ,75 ,25);
    bSave = new MyButton("Save", 8, 535 ,75 ,25);

    int width = 50;
    int height = 50;
    int xStart = 110;
    int yStart = 500;
    int xOffset = (int) (width * 1.1f);
    int i = 0;

    bGrass = new MyButton("Grass", xStart, yStart, width, height, i++);
    bWater = new MyButton("Water", xStart + xOffset, yStart, width, height, i++);
    bRoad = new MyButton("Road", xStart + xOffset * 2, yStart, width, height, i++);

    initMapButton(editing.getGame().getTileManager().getWaterS(), xStart, yStart, xOffset, width, height, i++);
    initMapButton(editing.getGame().getTileManager().getCorners(), xStart, yStart, xOffset, width, height, i++);
    initMapButton(editing.getGame().getTileManager().getRoadsC(), xStart, yStart, xOffset, width, height, i++);
    initMapButton(editing.getGame().getTileManager().getBeaches(), xStart, yStart, xOffset, width, height, i++);

    bPathStart = new MyButton("Path Start", xStart, yStart + xOffset, width, height, i++);
    bPathEnd = new MyButton("Path End", xStart + xOffset, yStart + xOffset, width, height, i++);
  }

  private void initMapButton(ArrayList<Tile> list, int x, int y, int xOff, int w, int h, int id) {
    MyButton b = new MyButton("", x + xOff * id, y, w, h, id);
    map.put(b, list);
  }

  public void draw(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(x, y, width, height);

    drawButtons(g);
  }

  private void drawButtons(Graphics g) {
    bMenu.draw(g);
    bSave.draw(g);

    drawNormalButton(g, bGrass);
    drawNormalButton(g, bWater);
    drawNormalButton(g, bRoad);
//    drawTileButtons(g);
    drawSelectedTile(g);

    drawMapButtons(g);
    drawPathButton(g, bPathStart, pathStart);
    drawPathButton(g, bPathEnd, pathEnd);
  }

  private void drawPathButton(Graphics g, MyButton button, BufferedImage image) {
     g.drawImage(image, button.x, button.y, button.width, button.height, null);
     drawButtonFeedback(g, button);
  }

  private void drawNormalButton(Graphics g, MyButton b) {
    g.drawImage(getButtonImage(b), b.x, b.y, b.width, b.height, null);
    drawButtonFeedback(g, b);
  }

  private void drawMapButtons(Graphics g) {
    for (Map.Entry<MyButton, ArrayList<Tile>> entry : map.entrySet()) {
      MyButton b = entry.getKey();
      BufferedImage img = entry.getValue().get(0).getSprite();

      g.drawImage(img, b.x, b.y, b.width, b.height, null);
      drawButtonFeedback(g, b);
    }

  }

  private BufferedImage getButtonImage(MyButton button) {
    int id = button.id;
    return editing.getGame().getTileManager().getSprite(id);
  }

  private void drawSelectedTile(Graphics g) {
    if (selectedTile != null) {
      g.drawImage(selectedTile.getSprite(), 570, 525, 50, 50, null);
    }
  }

  public BufferedImage getPathStart() {
    return pathStart;
  }

  public BufferedImage getPathEnd() {
    return pathEnd;
  }

  public void mouseClicked(int x, int y) {
    if (bMenu.getBounds().contains(x, y)) {
      setGameState(MENU);
    } else if (bSave.getBounds().contains(x, y)) {
      saveLevel();
    } else if (bWater.getBounds().contains(x, y)) {
      selectedTile = editing.getGame().getTileManager().getTile(bWater.id);
      editing.setSelectedTile(selectedTile);
    } else if (bGrass.getBounds().contains(x, y)) {
      selectedTile = editing.getGame().getTileManager().getTile(bGrass.id);
      editing.setSelectedTile(selectedTile);
    }  else if (bRoad.getBounds().contains(x, y)) {
      selectedTile = editing.getGame().getTileManager().getTile(bRoad.id);
      editing.setSelectedTile(selectedTile);
    } else if (bPathStart.getBounds().contains(x, y)) {
      selectedTile = new Tile(pathStart, -1, -1);
      editing.setSelectedTile(selectedTile);
    } else if (bPathEnd.getBounds().contains(x, y)) {
      selectedTile = new Tile(pathEnd, -2, -2);
      editing.setSelectedTile(selectedTile);
    } else {
      for (MyButton b : map.keySet())
        if (b.getBounds().contains(x, y)) {
          selectedTile = map.get(b).get(0);
          editing.setSelectedTile(selectedTile);
          currentButton = b;
          currentIndex = 0;
          return;
        }
    }
  }

  public void mouseMoved(int x, int y) {
    bMenu.setMouseOver(false);
    bSave.setMouseOver(false);
    bRoad.setMouseOver(false);
    bWater.setMouseOver(false);
    bGrass.setMouseOver(false);
    bPathStart.setMouseOver(false);
    bPathEnd.setMouseOver(false);
    for (MyButton b : map.keySet())
      b.setMouseOver(false);

    if (bMenu.getBounds().contains(x, y))
      bMenu.setMouseOver(true);
    else if (bSave.getBounds().contains(x, y))
      bSave.setMouseOver(true);
    else if (bWater.getBounds().contains(x, y))
      bWater.setMouseOver(true);
    else if (bGrass.getBounds().contains(x, y))
      bGrass.setMouseOver(true);
    else if (bRoad.getBounds().contains(x, y))
      bRoad.setMouseOver(true);
    else if (bPathStart.getBounds().contains(x, y))
      bPathStart.setMouseOver(true);
    else if (bPathEnd.getBounds().contains(x, y))
      bPathEnd.setMouseOver(true);
    else {
      for (MyButton b : map.keySet())
        if (b.getBounds().contains(x, y)) {
          b.setMouseOver(true);
          return;
        }
    }

  }

  public void mousePressed(int x, int y) {
    if (bMenu.getBounds().contains(x, y))
      bMenu.setMousePressed(true);
    else if (bSave.getBounds().contains(x, y))
      bSave.setMousePressed(true);
    else if (bWater.getBounds().contains(x, y))
      bWater.setMousePressed(true);
    else if (bGrass.getBounds().contains(x, y))
      bGrass.setMousePressed(true);
    else if (bRoad.getBounds().contains(x, y))
      bRoad.setMousePressed(true);
    else if (bPathStart.getBounds().contains(x, y))
      bPathStart.setMousePressed(true);
    else if (bPathEnd.getBounds().contains(x, y))
      bPathEnd.setMousePressed(true);
    else {
      for (MyButton b : map.keySet())
        if (b.getBounds().contains(x, y)) {
          b.setMousePressed(true);
          return;
        }
    }

  }

  public void mouseReleased(int x, int y) {
    bMenu.resetBooleans();
    bSave.resetBooleans();
    bGrass.resetBooleans();
    bRoad.resetBooleans();
    bWater.resetBooleans();
    bPathStart.resetBooleans();
    bPathEnd.resetBooleans();
    for (MyButton b : map.keySet())
      b.resetBooleans();

  }

  private void saveLevel() {
    editing.saveLevel();
  }

  public void rotateSprite() {
    currentIndex++;
    if (currentIndex >= map.get(currentButton).size())
      currentIndex = 0;
    selectedTile = map.get(currentButton).get(currentIndex);
    editing.setSelectedTile(selectedTile);
  }

}
