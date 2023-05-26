package helpz;

public abstract class Constants {

  public static abstract class App {
    public static int SIZE_TILE = 32;
    public static int QUANTITY_OF_COLUMNS = 20;
    public static int QUANTITY_OF_ROWS = 20;
  
    public static int BOTTOM_BAR_HEIGHT = 140;

    public static int AMOUNT_OF_TILES = QUANTITY_OF_COLUMNS * QUANTITY_OF_ROWS;
    public static int WIDTH = 640;
    public static int HEIGHT = 620;

    public static float ANIMATION_SPEED = 16;
  }
  public static abstract class Projectiles {
    public static final int ARROW = 0;
    public static final int CHAINS = 1;
    public static final int BOMB = 2;

    public static float GetSpeed(int type) {
      float speed = 0;
      switch (type) {
        case ARROW -> speed = 8f;
        case BOMB -> speed = 4f;
        case CHAINS -> speed = 6f;
      }
      return speed;
    }
  }
  public static abstract class Direction {
    public static final int LEFT = 0;
    public static final int UP = 1;
    public static final int RIGHT = 2;
    public static final int DOWN = 3;
  }
  public static abstract class Tiles {
    public static final int GRASS_TILE = 0;
    public static final int ROAD_TILE = 1;
    public static final int WATER_TILE = 2;
  }
  public static abstract class Enemies {
    public static final int GARBAGE_BAG = 2;
    public static final int PLASTIC_BOTTLE = 0;
    public static final int GLASS_BOTTLE = 1;

    public static float GetEnemySpeed(int enemyType) {
      float speed = 0;
      switch (enemyType) {
        case GARBAGE_BAG -> speed =  0.5f;
        case PLASTIC_BOTTLE -> speed = 0.65f;
        case GLASS_BOTTLE -> speed = 0.60f;
      }
      return speed;
    }

    public static float GetStartHealth(int enemyType) {
      float health = 0;
      switch (enemyType) {
        case GARBAGE_BAG -> health =  250;
        case PLASTIC_BOTTLE -> health = 150;
        case GLASS_BOTTLE -> health = 100;
      }
      return health;
    }
  }
  public static abstract class Towers {
    public static String GetName(int type) {
      String name = "";
      switch (type) {
        case CANNON -> name = "Cannon";
        case ARCHER -> name = "Archer";
        case WIZARD -> name = "Wizard";
      }
      return name;
    }
    public static float GetStartDamage(int type) {
      float damge = 0;
      switch (type) {
        case CANNON -> damge = 10f;
        case ARCHER -> damge = 5f;
        case WIZARD -> damge = 2.5f;
      }
      return damge;
    }
    public static float GetStartRange(int type) {
      float range = 0;
      switch (type) {
        case CANNON -> range = 100;
        case ARCHER -> range = 100;
        case WIZARD -> range = 100;
      }
      return range;
    }
    public static float GetStartCoolDown(int type) {
      float coolDown = 0;
      switch (type) {
        case CANNON -> coolDown = 60;
        case ARCHER -> coolDown = 25;
        case WIZARD -> coolDown = 40;
      }
      return coolDown;
    }
    public static final int CANNON = 0;
    public static final int ARCHER = 1;
    public static final int WIZARD = 2;
  }
}
