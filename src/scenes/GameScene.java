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
  public void render(Graphics g) {
    throw new UnsupportedOperationException("Unimplemented method 'render'");
  }

  @Override
  public void mouseClicked(int x, int y) {
    throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
  }

  @Override
  public void mouseMoved(int x, int y) {
    throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
  }

  @Override
  public void mousePressed(int x, int y) {
    throw new UnsupportedOperationException("Unimplemented method 'mousePressed'");
  }

  @Override
  public void mouseReleased(int x, int y) {
    throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
  }

  @Override
  public void mouseDragged(int x, int y) {
    throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
  }
}
