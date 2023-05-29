package scenes;

import java.awt.Graphics;

import main.Game;

public class GameScene implements SceneMethods {
  protected Game game;

  protected int animationIndex;
  protected int tick;

  public GameScene(Game game) {
    this.game = game;
  }

  protected void updateTick() {
    tick++;
    if (tick >= helpz.Constants.App.ANIMATION_SPEED) {
      tick = 0;
      animationIndex++;
      if (animationIndex >= 4) {
        animationIndex = 0;
      }
    }
  }

  public Game getGame() {
    return this.game;
  }

  @Override
  public void render(Graphics g) {}

  @Override
  public void mouseClicked(int x, int y) {}

  @Override
  public void mouseMoved(int x, int y) {}

  @Override
  public void mousePressed(int x, int y) {}

  @Override
  public void mouseReleased(int x, int y) {}

  @Override
  public void mouseDragged(int x, int y) {}
}
