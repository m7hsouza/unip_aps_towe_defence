package inputs;

import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static main.GameStates.*;

public class KeyboardListener implements KeyListener {

  public KeyboardListener(Game game) {
    this.game = game;
  }

  private final Game game;

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (gameStates) {
      case EDITING -> game.getEditing().keyPressed(e);
      case PLAYING -> game.getPlaying().keyPressed(e);
      case MENU, SETTINGS -> {}
      default -> throw new IllegalArgumentException("Unexpected value: " + gameStates);
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
