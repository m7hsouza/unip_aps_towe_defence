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
    waterS.add(new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), builtSprite, 0), id++, WATER_TILE));
    waterS.add(new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), builtSprite, 90), id++, WATER_TILE));

    corners.add(new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), getSprite(4, 0), 0), id++, WATER_TILE));
    corners.add(new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), getSprite(4, 0), 90), id++, WATER_TILE));
    corners.add(new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), getSprite(4, 0), 180), id++, WATER_TILE));
    corners.add(new Tile(ImgFix.getBuildRotateImage(getAnimationSpite(0, 0), getSprite(4, 0), 270), id++, WATER_TILE));

    buildSprites[0] = getSprite(9, 0);
    buildSprites[1] = getSprite(6, 0);
    builtSprite = ImgFix.buildImages(buildSprites);
    roadsC.add(new Tile(builtSprite, id++, ROAD_TILE));
    roadsC.add(new Tile(ImgFix.getRotateImage(builtSprite, 90), id++, ROAD_TILE));
    roadsC.add(new Tile(ImgFix.getRotateImage(builtSprite, 180), id++, ROAD_TILE));
    roadsC.add(new Tile(ImgFix.getRotateImage(builtSprite, 270), id++, ROAD_TILE));

    buildSprites[1] = getSprite(7, 0);
    builtSprite = ImgFix.buildImages(buildSprites);
    beaches.add(new Tile(builtSprite, id++, ROAD_TILE));
    beaches.add(new Tile(ImgFix.getRotateImage(builtSprite, 90), id++, ROAD_TILE));
    beaches.add(new Tile(ImgFix.getRotateImage(builtSprite, 180), id++, ROAD_TILE));
    beaches.add(new Tile(ImgFix.getRotateImage(builtSprite, 270), id++, ROAD_TILE));

    tiles.addAll(waterS);
    tiles.addAll(corners);
    tiles.addAll(roadsC);
    tiles.addAll(beaches);
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


