package inputs;

import main.Game;
import main.GameStates;
import scenes.GameScene;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener implements MouseListener, MouseMotionListener {
  private GameScene currentScene;
  public MyMouseListener(Game game) {
    this.game = game;
  }

  private final Game game;

  @Override
  public void mouseClicked(MouseEvent e) {
    if(e.getButton() == MouseEvent.BUTTON1) {
      switch (GameStates.gameStates) {
        case MENU -> currentScene = game.getMenu();
        case PLAYING -> currentScene = game.getPlaying();
        case SETTINGS -> currentScene = game.getSettings();
        case EDITING -> currentScene = game.getEditing();
      }
      currentScene.mouseClicked(e.getX(), e.getY());
    }

  }

  @Override
  public void mousePressed(MouseEvent e) {
    switch (GameStates.gameStates) {
      case MENU -> currentScene = game.getMenu();
      case PLAYING -> currentScene = game.getPlaying();
      case SETTINGS -> currentScene = game.getSettings();
      case EDITING -> currentScene = game.getEditing();
    }
    currentScene.mousePressed(e.getX(), e.getY());
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    switch (GameStates.gameStates) {
      case MENU -> currentScene = game.getMenu();
      case PLAYING -> currentScene = game.getPlaying();
      case SETTINGS -> currentScene = game.getSettings();
      case EDITING -> currentScene = game.getEditing();
    }
    currentScene.mouseReleased(e.getX(), e.getY());
  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  @Override
  public void mouseDragged(MouseEvent e) {
    switch (GameStates.gameStates) {
      case MENU -> currentScene = game.getMenu();
      case PLAYING -> currentScene = game.getPlaying();
      case SETTINGS -> currentScene = game.getSettings();
      case EDITING -> currentScene = game.getEditing();
    }
    currentScene.mouseDragged(e.getX(), e.getY());
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    switch (GameStates.gameStates) {
      case MENU -> currentScene = game.getMenu();
      case PLAYING -> currentScene = game.getPlaying();
      case SETTINGS -> currentScene = game.getSettings();
      case EDITING -> currentScene = game.getEditing();
    }
    currentScene.mouseMoved(e.getX(), e.getY());
  }
}
