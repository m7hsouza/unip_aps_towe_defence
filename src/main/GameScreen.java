package main;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Dimension;

public class GameScreen extends JPanel {

  public GameScreen(Game game) {
    this.game = game;
    setPanelSize();
  }
  private final Game game;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    game.getRender().render(g);
  }

  private void setPanelSize() {
    Dimension size = new Dimension(640, 580);
    setMinimumSize(size);
    setPreferredSize(size) ;
    setMaximumSize(size);
  }

  public void initInputs() {
    KeyboardListener keyboardListener = new KeyboardListener(game);
    MyMouseListener mouseListener = new MyMouseListener(game);

    addMouseListener(mouseListener);
    addMouseMotionListener(mouseListener);
    addKeyListener(keyboardListener);

    requestFocus();
  }
}
