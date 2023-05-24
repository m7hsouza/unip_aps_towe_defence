package managers;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static helpz.Constants.Tiles.*;

public class TileManager {
  public TileManager() {
    loadAtlas();
    createTiles();
  }

//  private Tile GRASS, WATER, ROAD;
  private Tile WATER_LR, WATER_TB;
  private Tile BL_WATER_CORNER, BR_WATER_CORNER, TL_WATER_CORNER, TR_WATER_CORNER;
  private Tile T_ROAD, L_ROAD, B_ROAD, R_ROAD ,ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R, T_WATER, R_WATER, B_WATER, L_WATER, TL_ISLE,
      BR_ISLE, BL_ISLE;
  public BufferedImage atlas;
  public ArrayList<Tile> tiles = new ArrayList<>();

  public ArrayList<Tile> roadsC = new ArrayList<>();
  public ArrayList<Tile> beaches = new ArrayList<>();
  public ArrayList<Tile> corners = new ArrayList<>();
  public ArrayList<Tile> waterS = new ArrayList<>();

  private void createTiles() {

    int id = 0;

    tiles.add(new Tile(getSprite(8, 0), id++, GRASS_TILE));
    tiles.add(new Tile(getAnimationSpite(0, 0), id++, WATER_TILE));
    tiles.add(new Tile(getSprite(9, 0), id++, ROAD_TILE));

    BufferedImage[] buildSprites = new BufferedImage[2];
    buildSprites[0] = getSprite(5, 0);
    buildSprites[1] = ImgFix.getRotateImage(buildSprites[0], 180);
    BufferedImage builtSprite = ImgFix.buildImages(buildSprites);
    waterS.add(WATER_TB = new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), builtSprite, 0), id++, WATER_TILE));
    waterS.add(WATER_LR = new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), builtSprite, 90), id++, WATER_TILE));

    corners.add(BL_WATER_CORNER = new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), getSprite(4, 0), 0), id++, WATER_TILE));
    corners.add(TL_WATER_CORNER = new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), getSprite(4, 0), 90), id++, WATER_TILE));
    corners.add(TR_WATER_CORNER = new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), getSprite(4, 0), 180), id++, WATER_TILE));
    corners.add(BR_WATER_CORNER = new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), getSprite(4, 0), 270), id++, WATER_TILE));

    buildSprites[0] = getSprite(9, 0);
    buildSprites[1] = getSprite(6, 0);
    builtSprite = ImgFix.buildImages(buildSprites);
    roadsC.add(ROAD_B_TO_R = new Tile(builtSprite, id++, ROAD_TILE));
    roadsC.add(ROAD_L_TO_B = new Tile(ImgFix.getRotateImage(builtSprite, 90), id++, ROAD_TILE));
    roadsC.add(ROAD_L_TO_T = new Tile(ImgFix.getRotateImage(builtSprite, 180), id++, ROAD_TILE));
    roadsC.add(ROAD_T_TO_R = new Tile(ImgFix.getRotateImage(builtSprite, 270), id++, ROAD_TILE));

    buildSprites[1] = getSprite(7, 0);
    builtSprite = ImgFix.buildImages(buildSprites);
    beaches.add(T_ROAD = new Tile(builtSprite, id++, ROAD_TILE));
    beaches.add(R_ROAD = new Tile(ImgFix.getRotateImage(builtSprite, 90), id++, ROAD_TILE));
    beaches.add(B_ROAD = new Tile(ImgFix.getRotateImage(builtSprite, 180), id++, ROAD_TILE));
    beaches.add(L_ROAD = new Tile(ImgFix.getRotateImage(builtSprite, 270), id++, ROAD_TILE));

    tiles.addAll(waterS);
    tiles.addAll(corners);
    tiles.addAll(roadsC);
    tiles.addAll(beaches);
  }

  private BufferedImage[] getImages(int firstX, int firstY, int secondX, int secondY) {
    return new BufferedImage[] {
        getSprite(firstX, firstY),
        getSprite(secondX, secondY)
    };
  }

  private void loadAtlas() {
    atlas = LoadSave.getSpriteAtlas();
  }

  private BufferedImage getSprite(int xCord, int yCord) {
    return atlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
  }

  private BufferedImage[] getAnimationSpite(int xCord, int yCord) {
    BufferedImage[] sprites = new BufferedImage[4];
    for (int i = 0; i < 4; i++) {
      sprites[i] = getSprite(xCord + i, yCord);
    }
    return sprites;
  }

  public BufferedImage getSprite(int id) {
    return tiles.get(id).getSprite();
  }

  public BufferedImage getAnimationSprite(int id, int animationIndex) {
    return tiles.get(id).getSprite(animationIndex);
  }

  public Tile getTile(int id) {
    return this.tiles.get(id);
  }

  public ArrayList<Tile> getWaterS() {
    return waterS;
  }

  public ArrayList<Tile> getRoadsC() {
    return roadsC;
  }

  public ArrayList<Tile> getBeaches() {
    return beaches;
  }

  public ArrayList<Tile> getCorners() {
    return corners;
  }

  public boolean isSpriteAnimation(int spriteID) {
    return tiles.get(spriteID).isAnimation();
  }
}


