package ui;

import java.awt.*;

public class Bar {
  protected final int x, y, width, height;

  public Bar(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  protected void drawButtonFeedback(Graphics g, MyButton button) {
    g.setColor(button.isMouseOver() ? Color.BLACK : Color.GRAY);

    g.drawRect(button.x, button.y, button.width, button.height);
    g.drawRect(button.x - 1, button.y - 1, button.width + 2, button.height + 2);

    if (button.isMousePressed()) {
      g.drawRect(button.x + 1, button.y + 1, button.width - 2, button.height - 2);
      g.drawRect(button.x + 2, button.y + 2, button.width - 4, button.height - 4);
    }
  }

}
