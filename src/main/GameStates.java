package main;

public enum GameStates {
  PLAYING, MENU, SETTINGS, EDITING, GAMEOVER;

  public static GameStates gameStates = MENU;

  public static void setGameState(GameStates state) {
    gameStates = state;
  }
}
