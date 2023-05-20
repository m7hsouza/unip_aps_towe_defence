package scenes;

import java.awt.Graphics;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

public class Menu extends GameScene implements SceneMethods {
  private MyButton bPlaying, bEdit, bSettings, bQuit;
  public Menu(Game game) {
     super(game);
     initButtons();
  }

  private void initButtons() {

    int width = 150;
    int height = width / 3;
    int x = 640 / 2 - width / 2;
    int y = 150;
    int yOffset = 100;

    bPlaying = new MyButton("Play", x, y, width, height);
    bEdit = new MyButton("Edit", x, y + yOffset, width, height);
    bSettings = new MyButton("Settings", x, y + yOffset * 2, width, height);
    bQuit = new MyButton("Quit", x, y + yOffset * 3, width, height);
  }

  private void drawButton(Graphics g) {
    bPlaying.draw(g);
    bEdit.draw(g);
    bSettings.draw(g);
    bQuit.draw(g);
  }

  @Override
  public void render(Graphics g) {
    drawButton(g);
  }

  @Override
  public void mouseClicked(int x, int y) {
    if (bPlaying.getBounds().contains(x, y)) {
      setGameState(PLAYING);
    } else if (bEdit.getBounds().contains(x, y)) {
      setGameState(EDITING);
    } else if (bSettings.getBounds().contains(x, y)) {
      setGameState(SETTINGS);
    } else if (bQuit.getBounds().contains(x, y)) {
      System.exit(0);
    }
   }

  @Override
  public void mouseMoved(int x, int y) {
    bPlaying.setMouseOver(false);
    bSettings.setMouseOver(false);
    bQuit.setMouseOver(false);
    bEdit.setMouseOver(false);
    if (bPlaying.getBounds().contains(x, y)) {
      bPlaying.setMouseOver(true);
    } else if (bEdit.getBounds().contains(x, y)) {
      bEdit.setMouseOver(true);
    } else if (bSettings.getBounds().contains(x, y)) {
      bSettings.setMouseOver(true);
    } else if (bQuit.getBounds().contains(x, y)) {
      bQuit.setMouseOver(true);
    }
  }

  @Override
  public void mousePressed(int x, int y) {
    if (bPlaying.getBounds().contains(x, y)) {
      bPlaying.setMousePressed(true);
    } else if (bEdit.getBounds().contains(x, y)) {
      bEdit.setMousePressed(true);
    } else if (bSettings.getBounds().contains(x, y)) {
      bSettings.setMousePressed(true);
    } else if (bQuit.getBounds().contains(x, y)) {
      bQuit.setMousePressed(true);
    }
  }
  @Override
  public void mouseReleased(int x, int y) {
    resetButtons();
  }

  @Override
  public void mouseDragged(int x, int y) {

  }

  private void resetButtons() {
    bPlaying.resetBooleans();
    bSettings.resetBooleans();
    bQuit.resetBooleans();
    bEdit.resetBooleans();
  }

}
