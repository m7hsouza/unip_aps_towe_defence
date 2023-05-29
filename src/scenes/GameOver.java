package scenes;

import java.awt.Graphics;

import main.Game;
import ui.MyButton;

public class GameOver extends GameScene {

	private MyButton bQuit, bRestart;

	public GameOver(Game game) {
		super(game);
		initButtons();
	}

	private void initButtons() {

    int width = 150;
    int height = width / 3;
    int x = 640 / 2 - width / 2;
    int y = 150;
    int yOffset = 100;

    bRestart = new MyButton("Reiniciar", x, y, width, height);
    bQuit = new MyButton("Sair", x, y + yOffset, width, height);
  }

	private void drawButton(Graphics g) {
    bRestart.draw(g);
    bQuit.draw(g);
  }

	@Override
	public void render(Graphics g) {
		drawButton(g);
	}
	
}
