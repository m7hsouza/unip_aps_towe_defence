package helpz;

import java.awt.*;
import java.util.ArrayList;

public class Utilz {

  public static int[][] ArrayListTo2DInt(ArrayList<Integer> list, int xSize, int ySize) {
    int[][] newArr = new int[ySize][xSize];
    for (int y = 0; y < newArr.length; y++) {
      for (int x = 0; x < newArr[y].length; x++) {
        int index = y * ySize + x;
        newArr[y][x] = list.get(index);
      }
    }
    return newArr;
  }

  public static int[] TwoDToArrayList(int[][] twoArr) {
    int[] oneArr = new int[twoArr.length * twoArr[0].length];
    for (int y = 0; y < twoArr.length; y++) {
      for (int x = 0; x < twoArr[y].length; x++) {
        int index = y * twoArr.length + x;
        oneArr[index] = twoArr[y][x];
      }
    }
    return oneArr;
  }

  public static float GetDistance(Point origin, Point target) {
    float xDiff = Math.abs(target.x - origin.y);
    float yDiff = Math.abs(target.y - origin.y);
    return (float) Math.hypot(xDiff, yDiff);
  }

}
