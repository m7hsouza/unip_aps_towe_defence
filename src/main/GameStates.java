package main;

public enum GameStates {
  PLAYING, MENU, SETTINGS, EDITING;

  public static GameStates gameStates = MENU;

  public static void setGameState(GameStates state) {
    gameStates = state;
  }
}
