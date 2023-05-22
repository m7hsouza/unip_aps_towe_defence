package objects;

public class PathPoint {
  public PathPoint(int xCord, int yCord) {
    this.xCord = xCord;
    this.yCord = yCord;
  }

  private int xCord, yCord;

  public int getxCord() {
    return xCord;
  }

  public int getyCord() {
    return yCord;
  }
}
