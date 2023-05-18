package main;

import java.awt.*;

public class Render {
  private final Game game;
  public Render(Game game) {
    this.game = game;
  }

  public void render(Graphics g) {

    switch (GameStates.gameStates) {
      case MENU:
        game.getMenu().render(g);
        break;

      case SETTINGS:
        game.getSettings().render(g);
        break;

      case PLAYING:
        game.getPlaying().render(g);
        break;

      case EDITING:
        game.getEdit().render(g);
        break;
    }

  }
}
