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
  private Game game;
  private Dimension size;
  private KeyboardListener keyboardListener;
  private MyMouseListener mouseListener;

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    game.getRender().render(g);
  }

  private void setPanelSize() {
    size = new Dimension(640, 580);
    setMinimumSize(size);
    setPreferredSize(size) ;
    setMaximumSize(size);
  }

  public void initInputs() {
    keyboardListener = new KeyboardListener(game);
    mouseListener = new MyMouseListener(game);

    addMouseListener(mouseListener);
    addMouseMotionListener(mouseListener);
    addKeyListener(keyboardListener);

    requestFocus();
  }
}
