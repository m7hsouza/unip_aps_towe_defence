package ui;

import java.awt.*;

public class Bar {
  public Bar(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  protected int x, y, width, height;

  protected void drawButtonFeedback(Graphics g, MyButton b) {
    // MouseOver
    g.setColor(b.isMouseOver() ? Color.BLACK : Color.GRAY);

    // Border
    g.drawRect(b.x, b.y, b.width, b.height);
    g.drawRect(b.x - 1, b.y - 1, b.width + 2, b.height + 2);

    // MousePressed
    if (b.isMousePressed()) {
      g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
      g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
    }
  }

}
