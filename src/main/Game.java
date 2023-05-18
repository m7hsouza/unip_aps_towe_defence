package main;

import helpz.LoadSave;
import managers.TileManager;
import scenes.*;

import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
  public Game() {
    initClasses();
    createDefaultLevel();

    add(gameScreen);
    pack();

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
//    setResizable(false);
    setVisible(true);
  }
  private GameScreen gameScreen;
  private Thread gameThread;

  private final double FPS_SET = 120.0;
  private final double UPS_SET = 60.0;

  // Classes
  private Render render;
  private Menu menu;
  private Playing playing;
  private Settings settings;
  private Editing edit;

  private TileManager tileManager;



  private void start() {
    gameThread = new Thread(this){};
    gameThread.start();
  }

  private void initClasses() {
    tileManager = new TileManager();
    menu = new Menu(this);
    render = new Render(this);
    playing = new Playing(this);
    gameScreen = new GameScreen(this);
    settings = new Settings(this);
    edit = new Editing(this);
  }

  private void updateGame() {
  }

  private void createDefaultLevel() {
    int[] arr = new int[300];
    for (int i : arr) {
      arr[i] = 0;
    }
    LoadSave.CreateLevel("new_level", arr);
  }

  @Override
  public void run() {
    long lastTimeCheck = System.currentTimeMillis();

    double timePerFrame = 1000000000 / FPS_SET;
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

  public Editing getEdit() {
    return edit;
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
