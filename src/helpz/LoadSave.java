package helpz;

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
    WriteToFile(newLevel, idArr);
  }

  public static void SaveLevel(String filename, int[][] idArr) {
    File leveFile = new File(String.format("resources/%s.txt", filename));
    if (leveFile.exists()) {
      WriteToFile(leveFile, Utilz.TwoDToArrayList(idArr));
    } else {
      System.out.printf("File: %s does not exists! ");
      return;
    }
  }

  private static void WriteToFile(File file, int[] idArr) {
    try {
      PrintWriter pw = new PrintWriter(file);
      for (Integer id : idArr) {
        pw.println(id);
      }
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
}
