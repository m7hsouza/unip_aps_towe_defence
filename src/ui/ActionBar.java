package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import objects.Tower;
import scenes.Playing;


import static main.GameStates.MENU;
import static main.GameStates.setGameState;
import static helpz.Constants.Towers.GetName;
import static helpz.Constants.App.SIZE_TILE;

public class ActionBar extends Bar {
  private final Playing playing;
  private MyButton bMenu;

  private MyButton[] towerButtons;

  private Tower selectedTower, displayedTower;

  private DecimalFormat formatter;

  private int gold = 100;

  private boolean showTowerPrice;
  private int towerPriceType;

  public ActionBar(int x, int y, int width, int height, Playing playing) {
    super(x, y, width, height);
    this.playing = playing;

    formatter = new DecimalFormat("0.0");

    initButtons();
  }

  private void initButtons() {
    bMenu = new MyButton("Menu", 8, 488 ,75 ,25);
    towerButtons = new MyButton[2];

    int width = 50;
    int height = 50;
    int xStart = 140;
    int yStart = 500;
    int xOffset = (int) (width * 1.5f);

    for (int i = 0; i < towerButtons.length; i++) {
      towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, width, height, i);
    }
  }

  public void draw(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(x, y, width, height);

    drawButtons(g);
    drawDisplayedTower(g);
    drawWaveInfo(g);
    drawGoldAmount(g);

    if (showTowerPrice) drawTowerPrice(g);
  }

  private void drawTowerPrice(Graphics g) {
    Point cardPosition = new Point(10, 565);
    int cardWidth = 130;
    int cardHeight = 60;
    int stringWidth;
    Tower tower = new Tower(0, 0, 0, towerPriceType);

    String towerPriceLabel = String.format("Valor: %dg", tower.getPrice());

    g.setColor(Color.GRAY);
    g.fillRect(cardPosition.x, cardPosition.y, cardWidth, cardHeight);
    g.setColor(Color.BLACK);
    g.drawRect(cardPosition.x, cardPosition.y, cardWidth, cardHeight);


    g.setFont(new Font(Font.DIALOG, Font.ITALIC, 16));
    stringWidth = g.getFontMetrics().stringWidth(tower.getName());
    g.drawString(tower.getName(), cardPosition.x + cardWidth / 2 - stringWidth / 2, cardPosition.y + 24);
    g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
    stringWidth = g.getFontMetrics().stringWidth(towerPriceLabel);
    g.drawString(towerPriceLabel, cardPosition.x + cardWidth / 2 - stringWidth / 2, cardPosition.y + 44);
  }

  private void drawDisplayedTower(Graphics g) {
    if (displayedTower == null) return;

    int towerType = displayedTower.getTowerType();
    BufferedImage sprite = playing.getTowerManager().getTowerImages()[towerType];
    g.setColor(Color.GRAY);
    g.fillRect(440, 490, 170, 75);
    g.setColor(Color.DARK_GRAY);
    g.drawRect(449, 499, 52, 52);
    g.drawImage(sprite, 450, 500, 50, 50, null);
    g.setColor(Color.BLACK);
    g.drawRect(440, 490, 170, 75);
    g.setFont(new Font("LucidaSans", Font.BOLD, 14));
    g.drawString("ID: " + displayedTower.getID(), 510, 510);
    g.drawString(GetName(towerType), 510, 525);

    drawDisplayedTowerBorder(g);
    drawDisplayedTowerRange(g);
  }

  private void drawDisplayedTowerRange(Graphics g) {
    g.setColor(Color.WHITE);
    int range = (int) displayedTower.getRange() * 2;
    int origin = range / 2;
    int x = displayedTower.getX() + 16 - origin;
    int y = displayedTower.getY() + 16 - origin;
    g.drawOval(x, y, range, range );
  }

  private void drawDisplayedTowerBorder(Graphics g) {
    g.setColor(Color.CYAN);
    g.drawRect(displayedTower.getX(), displayedTower.getY(), SIZE_TILE, SIZE_TILE);
  }

  private void drawButtons(Graphics g) {
    bMenu.draw(g);
    for (MyButton button : towerButtons) {
      g.setColor(Color.lightGray);
      g.fillRect(button.x, button.y, button.width, button.height);
      BufferedImage image = playing.getTowerManager().getTowerImages()[button.id];
      g.drawImage(image, button.x, button.y, button.width, button.height, null);
      drawButtonFeedback(g, button);
    }
  }

  @Override
  protected void drawButtonFeedback(Graphics g, MyButton button) {
    Tower tower = new Tower(0, 0, -1, towerPriceType);
    if (tower.getPrice() > this.gold) {
      g.setColor(button.isMouseOver() ? Color.RED : Color.GRAY);
  
      g.drawRect(button.x, button.y, button.width, button.height);
      g.drawRect(button.x - 1, button.y - 1, button.width + 2, button.height + 2);
      return;
    }
    super.drawButtonFeedback(g, button);
  }

  private void drawWaveInfo(Graphics g) {
    g.setColor(Color.black);
		g.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 18));
		drawWaveTimerInfo(g);
		drawEnemiesLeftInfo(g);
		drawWavesLeftInfo(g);
	}

  private void drawWavesLeftInfo(Graphics g) {
		int current = playing.getWaveManager().getWaveIndex();
		int size = playing.getWaveManager().getWaves().size();
		g.drawString("Wave " + (current + 1) + " / " + size, 450, 585);
	}

  private void drawEnemiesLeftInfo(Graphics g) {
		int remaining = playing.getEnemyManager().getAmountOfAliveEnemies();
		g.drawString("Enemies Left: " + remaining, 450, 605);
	}

  private void drawWaveTimerInfo(Graphics g) {
		if (playing.getWaveManager().isWaveTimerStarted()) {

			float timeLeft = playing.getWaveManager().getTimeLeft();
			String formattedText = formatter.format(timeLeft);
			g.drawString("Time Left: " + formattedText, 450, 625);
		}
	}

  private void drawGoldAmount(Graphics g) {
    g.setFont(new Font(Font.DIALOG, Font.TYPE1_FONT, 16));
    g.drawString("Gold: " + gold + "g", 10, 545);
  }

  public void mouseClicked(int x, int y) {
    if (bMenu.getBounds().contains(x, y)) {
      setGameState(MENU);
    } else {
      for (MyButton button : towerButtons)
        if (button.getBounds().contains(x, y)) {
          selectedTower = new Tower(0, 0, -1, button.id);
          if (selectedTower.getPrice() > this.gold) {
            selectedTower = null;
            return;
          }
          playing.setSelectedTower(selectedTower);
          return;
        }
    }
  }

  public void mouseMoved(int x, int y) {
    bMenu.setMouseOver(false);
    showTowerPrice = false;
    for (MyButton button : towerButtons) button.setMouseOver(false);
    if (bMenu.getBounds().contains(x, y)) {
      bMenu.setMouseOver(true);
    } else {
      for (MyButton button : towerButtons)
        if (button.getBounds().contains(x, y)) {
          button.setMouseOver(true);
          showTowerPrice = true;
          towerPriceType = button.id;
        }
    }
  }

  public void mousePressed(int x, int y) {
    if (bMenu.getBounds().contains(x, y)) {
      bMenu.setMousePressed(true);
    } else {
      for (MyButton button : towerButtons)
        if (button.getBounds().contains(x, y)) button.setMousePressed(true);
    }
  }

  public void mouseReleased(int x, int y) {
    bMenu.resetBooleans();
    for (MyButton button : towerButtons) button.resetBooleans();
  }

  public void displayTower(Tower tower) {
    displayedTower = tower;
  }

  public void payForTower(Tower tower) {
    this.gold -= tower.getPrice();
  }

  public void incrementGold(int gold) {
    this.gold += gold;
  }
}
