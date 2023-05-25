package ui;

import java.awt.*;

public class MyButton {
  public final int x, y, width, height, id;

  private String text;
  
  private Rectangle bounds;

  private boolean mouseOver, mousePressed;

  public MyButton(String text, int x, int y, int width, int height) {
    this.text = text;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.id = -1;

    initBounds();
  }

  public MyButton(String text, int x, int y, int width, int height, int id) {
    this.text = text;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.id = id;

    initBounds();
  }

  private void initBounds() {
    this.bounds = new Rectangle(x, y, width, height);
  }

  public void draw(Graphics g) {
    drawBody(g);
    drawBorders(g);
    drawText(g);
  }

  private void drawBody(Graphics g) {
    g.setColor(mouseOver ? Color.gray : Color.white);
    g.fillRect(x, y, width, height);
  }

  private void drawBorders(Graphics g) {
    g.setColor(Color.black);
    g.drawRect(x, y, width, height);
    if (mousePressed) {
      g.drawRect(x + 1, y + 1, width - 2, height - 2);
      g.drawRect(x + 2, y + 2, width - 4, height - 4);
    }
  }

  private void drawText(Graphics g) {
    int stringWidth = g.getFontMetrics().stringWidth(text);
    int stringHeight = g.getFontMetrics().getHeight();
    int x = this.x - stringWidth / 2 + width / 2;
    int y = this.y + stringHeight / 2 + height / 2;
    g.drawString(text, x, y);
  }

  public void resetBooleans() {
    mousePressed = false;
    mouseOver = false;
  }

  public Rectangle getBounds() {
    return bounds;
  }

  public boolean isMouseOver() {
    return mouseOver;
  }

  public boolean isMousePressed() {
    return mousePressed;
  }

  public void setMouseOver(boolean mouseOver) {
    this.mouseOver = mouseOver;
  }

  public void setMousePressed(boolean mousePressed) {
    this.mousePressed = mousePressed;
  }

}
