package helpz;

public abstract class Constants {

  public static abstract class Projectiles {
    public static final int ARROW = 0;
    public static final int CHAINS = 1;
    public static final int BOMB = 2;

    public static float GetSpeed(int type) {
      float speed = 0;
      switch (type) {
        case ARROW -> speed = 2f;
        case BOMB -> speed = 0.5f;
        case CHAINS -> speed = 1f;
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
    public static final int WATER_TILE = 0;
    public static final int GRASS_TILE = 1;
    public static final int ROAD_TILE = 2;
  }
  public static abstract class Enemies {
    public static final int ORC = 0;
    public static final int BAT = 1;
    public static final int KNIGHT = 2;
    public static final int WOLF = 3;

    public static float GetEnemySpeed(int enemyType) {
      float speed = 0.0f;
      switch (enemyType) {
        case ORC -> speed =  0.5f;
        case BAT -> speed = 0.65f;
        case KNIGHT -> speed = 0.3f;
        case WOLF -> speed = 0.75f;
      }
      return speed;
    }

    public static float GetStartHealth(int enemyType) {
      float health = 0;
      switch (enemyType) {
        case ORC -> health =  100;
        case BAT -> health = 60;
        case KNIGHT -> health = 250;
        case WOLF -> health = 90;
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
        case CANNON -> damge = 0.5f;
        case ARCHER -> damge = 1f;
        case WIZARD -> damge = 1.8f;
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
        case CANNON -> coolDown = 20;
        case ARCHER -> coolDown = 12;
        case WIZARD -> coolDown = 10;
      }
      return coolDown;
    }
    public static final int CANNON = 0;
    public static final int ARCHER = 1;
    public static final int WIZARD = 2;
  }
}
