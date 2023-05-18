package inputs;

import main.Game;
import main.GameStates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

  public KeyboardListener(Game game) {
    this.game = game;
  }

  private Game game;

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(GameStates.gameStates == GameStates.EDITING) {
      game.getEdit().keyPressed(e);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
