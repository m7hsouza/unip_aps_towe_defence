package helpz;

import objects.PathPoint;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {

  public static BufferedImage getSpriteAtlas() {
    BufferedImage img = null;
    InputStream stream = ImgFix.class.getResourceAsStream("/sprite-atlas.png");
    try {
      assert stream != null;
      img = ImageIO.read(stream);
    } catch (IOException error) {
      error.printStackTrace();
    }
    return img;
  }

  public static void CreateLevel(String filename, int[] idArr) {
    File newLevel = new File(String.format("resources/%s.txt", filename));
    if (newLevel.exists()) {
      System.out.printf("File: %s already exists.", filename);
      return;
    }
    try {
      newLevel.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    WriteToFile(newLevel, idArr, new PathPoint(0, 0), new PathPoint(0, 0));
  }

  public static void SaveLevel(String filename, int[][] idArr, PathPoint start, PathPoint end) {
    File leveFile = new File(String.format("resources/%s.txt", filename));
    if (leveFile.exists()) {
      WriteToFile(leveFile, Utilz.TwoDToArrayList(idArr), start, end);
    } else {
      System.out.printf("File: %s does not exists! ");
      return;
    }
  }

  private static void WriteToFile(File file, int[] idArr, PathPoint start, PathPoint end) {
    try {
      PrintWriter pw = new PrintWriter(file);
      for (Integer id : idArr) {
        pw.println(id);
      }
      pw.println(start.getxCord());
      pw.println(start.getyCord());
      pw.println(end.getxCord());
      pw.println(end.getyCord());
      pw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static int[][] GetLevelData(String filename) {
    File levelFile = new File(String.format("resources/%s.txt", filename));
    if (levelFile.exists()) {
      ArrayList<Integer> list = ReadFromFile(levelFile);
      return Utilz.ArrayListTo2DInt(list, 20, 15);
    } else {
      System.out.printf("File: %s does not exists! ", filename);
      return null;
    }
  }

  private static ArrayList<Integer> ReadFromFile(File file) {
    ArrayList<Integer> list = new ArrayList<>();
    try {
      Scanner sc = new Scanner(file);
      while (sc.hasNextLine()) {
        list.add(Integer.parseInt(sc.nextLine()));
      }
//      sc.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return list;
  }

  public static ArrayList<PathPoint> GetLevelPathPoints(String filename) {
    File levelFile = new File(String.format("resources/%s.txt", filename));
    if (levelFile.exists()) {
      ArrayList<Integer> list = ReadFromFile(levelFile);
      ArrayList<PathPoint> points = new ArrayList<>();
      points.add(new PathPoint(list.get(300), list.get(301)));
      points.add(new PathPoint(list.get(302), list.get(303)));
      return points;
    } else {
      System.out.printf("File: %s does not exists! ", filename);
      return null;
    }
  }

}
