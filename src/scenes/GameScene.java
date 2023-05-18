package scenes;

import main.Game;

public class GameScene {
  public GameScene(Game game) {
    this.game = game;
  }

  protected Game game;

  public Game getGame() {
    return this.game;
  }

}
