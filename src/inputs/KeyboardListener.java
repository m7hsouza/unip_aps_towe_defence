package inputs;

import main.Game;
import scenes.GameScene;

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
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }
}
