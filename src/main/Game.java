package main;

import helpz.LoadSave;
import managers.TileManager;
import scenes.*;

import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
  private GameScreen gameScreen;

  private Render render;
    
  private Menu menu;
  private Playing playing;
  private Settings settings;
  private Editing editing;

  private TileManager tileManager;
  
  public Game() {
    createDefaultLevel();
    initClasses();

    add(gameScreen);
    pack();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    setVisible(true);
  }

  @Override
  public void run() {
    long lastTimeCheck = System.currentTimeMillis();

    double FPS_SET = 120.0;
    double timePerFrame = 1000000000 / FPS_SET;
    double UPS_SET = 60.0;
    double timePerUpdate = 1000000000 / UPS_SET;

    long lastFrame = System.nanoTime();
    long lastUpdate = System.nanoTime();

    int frames = 0;
    int updates = 0;

    long now;

    while (true) {
      now = System.nanoTime();
      if (now - lastFrame >= timePerFrame) {
        repaint();
        lastFrame = now;
        frames++;
      }

      if (now - lastUpdate >= timePerUpdate) {
        updateGame();
        lastUpdate = now;
        updates++;
      }

      if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
        System.out.println("FPS: " + frames + " | UPS: " + updates);
        frames = 0;
        updates = 0;
        lastTimeCheck = System.currentTimeMillis();
      }
    }
  }

  private void start() {
    Thread gameThread = new Thread(this) {
    };
    gameThread.start();
  }

  private void initClasses() {
    tileManager = new TileManager();
    menu = new Menu(this);
    render = new Render(this);
    playing = new Playing(this);
    gameScreen = new GameScreen(this);
    settings = new Settings(this);
    editing = new Editing(this);
  }

  private void updateGame() {
    switch (GameStates.gameStates) {
      case PLAYING -> playing.update();
      case EDITING -> editing.update();
      case MENU, SETTINGS -> {}
      default -> throw new IllegalArgumentException("Unexpected value: " + GameStates.gameStates);
    }
  }

  private void createDefaultLevel() {
    int[] arr = new int[helpz.Constants.App.AMOUNT_OF_TILES];
    for (int i : arr) {
      arr[i] = 0;
    }
    LoadSave.CreateLevel("new_level", arr);
  }

  // Get and Set
  public Render getRender() {
    return this.render;
  }

  public Menu getMenu() {
    return menu;
  }

  public Settings getSettings() {
    return settings;
  }

  public Playing getPlaying() {
    return playing;
  }

  public Editing getEditing() {
    return editing;
  }

  public TileManager getTileManager() {
    return tileManager;
  }

  // Main
  public static void main(String[] arguments) {
    Game game = new Game();
    game.gameScreen.initInputs();
    game.start();
  }

}
