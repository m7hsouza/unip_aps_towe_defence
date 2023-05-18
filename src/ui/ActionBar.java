package ui;

import objects.Tile;
import scenes.Playing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static main.GameStates.MENU;
import static main.GameStates.setGameState;

public class ActionBar extends Bar {
  public ActionBar(int x, int y, int width, int height, Playing playing) {
    super(x, y, width, height);
    this.playing = playing;

    initButtons();
  }

  private Playing playing;
  private MyButton bMenu;

  public void draw(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(x, y, width, height);

    drawButtons(g);
  }

  private void initButtons() {
    bMenu = new MyButton("Menu", 8, 488 ,75 ,25);
  }

  private void drawButtons(Graphics g) {
    bMenu.draw(g);
  }
  public void mouseClicked(int x, int y) {
    if (bMenu.getBounds().contains(x, y)) {
      setGameState(MENU);
    }
  }

  public void mouseMoved(int x, int y) {
    bMenu.setMouseOver(false);
    if (bMenu.getBounds().contains(x, y)) {
      bMenu.setMouseOver(true);
    }
  }

  public void mousePressed(int x, int y) {
    if (bMenu.getBounds().contains(x, y)) {
      bMenu.setMousePressed(true);
    }
  }

  public void mouseReleased(int x, int y) {
    bMenu.resetBooleans();
  }
}
