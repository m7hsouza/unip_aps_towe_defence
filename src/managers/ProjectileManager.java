package managers;

import enemies.Enemy;
import helpz.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static helpz.Constants.Towers.*;
import static helpz.Constants.Projectiles.*;
import static helpz.Constants.App.SIZE_TILE;

public class ProjectileManager {
  private Playing playing;

  private ArrayList<Projectile> projectiles = new ArrayList<>();
  private ArrayList<Explosion> explosions = new ArrayList<>();

  private BufferedImage[] projectileSprites, explosionSprites;

  private int amount = 0;

  public ProjectileManager(Playing playing) {
    this.playing = playing;
    loadProjectileSprites();
  }

  private void loadProjectileSprites() {
    BufferedImage atlas = LoadSave.getSpriteAtlas();
    projectileSprites = new BufferedImage[3];
    for (int i = 0; i < 3; i++) {
      projectileSprites[i] = atlas.getSubimage((7 + i) * SIZE_TILE, SIZE_TILE, SIZE_TILE, SIZE_TILE);
    }
    loadExplosionSprites(atlas);
  }

  private void loadExplosionSprites(BufferedImage atlas) {
    explosionSprites = new BufferedImage[7];
    for (int i = 0; i < 7; i++) {
      explosionSprites[i] = atlas.getSubimage(i * SIZE_TILE, 64, SIZE_TILE, SIZE_TILE);
    }
  }


  public void update() {
    for (Projectile projectile : projectiles) {
      if (projectile.isActive()) {
        projectile.move();
        if (isHittingEnemy(projectile)) {
          projectile.setActive(false);
          if (projectile.getType() == BOMB) {
            explosions.add(new Explosion(projectile.getPosition()));
            explosionOnEnemy(projectile);
          }
        }
      }
    }

    for (Explosion explosion : explosions)
      if (explosion.getIndex() < 7) explosion.update();
  }

  private void explosionOnEnemy(Projectile projectile) {
    for (Enemy enemy : playing.getEnemyManager().getEnemies()) {
      if (enemy.isAlive()) {
        float radius = 40.0f;

        float xDist = Math.abs(projectile.getPosition().x - enemy.getX());
        float yDist = Math.abs(projectile.getPosition().y - enemy.getY());

        float dist = (float) Math.hypot(xDist, yDist);

        if (dist <= radius) {
          enemy.hurt(projectile.getDamage());
        }
      }
    }
  }

  private boolean isHittingEnemy(Projectile projectile) {
    for (Enemy enemy : playing.getEnemyManager().getEnemies()) {
      if (enemy.isAlive()) {
      
        if (enemy.getBounds().contains(projectile.getPosition())) {
          enemy.hurt(projectile.getDamage());
          return true;
        }
      }
    }
    return false;
  }

  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    for (Projectile projectile : projectiles) {
      if (projectile.isActive()) {
        BufferedImage sprite = projectileSprites[projectile.getType()];
        int x = (int) projectile.getPosition().x;
        int y = (int) projectile.getPosition().y;
        if (projectile.getType() == ARROW) {
          g2d.translate(x, y);
          g2d.rotate(Math.toRadians(projectile.getRotate()));
          g2d.drawImage(sprite,-16 ,-16, null);
          g2d.rotate(-Math.toRadians(projectile.getRotate()));
          g2d.translate(-x, -y);
        } else {
          g2d.drawImage(sprite, x - 16, y - 16, null);
        }
      }
    }
    drawExplosion(g2d);
  }

  private void drawExplosion(Graphics g) {
    for (Explosion explosion : explosions) {
      if (explosion.getIndex() < 7) {
        BufferedImage currentSprite = explosionSprites[explosion.getIndex()];
        int x = (int) explosion.getPosition().x - SIZE_TILE / 2;
        int y = (int) explosion.getPosition().y - SIZE_TILE / 2;
        g.drawImage(currentSprite, x, y, null);
      }
    }
  }

  public void newProjectile(Tower tower, Enemy enemy) {
    int type = getProjectileType(tower);

    int xDist = (int) (tower.getX() - enemy.getX());
    int yDist = (int) (tower.getY() - enemy.getY());
    int totalDist = Math.abs(xDist) + Math.abs(yDist);

    float xPer = (float) Math.abs(xDist) / totalDist;

    float speed = helpz.Constants.Projectiles.GetSpeed(type);
    float xSpeed = xPer * speed;
    float ySpeed = speed - xSpeed;

    if (tower.getX() > enemy.getX())
      xSpeed *= -1;
    if (tower.getY() > enemy.getY())
      ySpeed *= -1;

    float rotate = 0;
    if (type == ARROW) {
      float arcValue = (float) Math.atan(yDist / (float) xDist);
      rotate = (float) Math.toDegrees(arcValue);
      if (xDist < 0) rotate += 180;
    }
    projectiles.add(new Projectile(tower.getX() + 16, tower.getY() + 16, xSpeed, ySpeed, tower.getDamage(), rotate, amount++, type));
  }

  private int getProjectileType(Tower tower) {
    int type = 0;
    switch (tower.getTowerType()) {
      case ARCHER -> type = ARROW;
      case WIZARD -> type = CHAINS;
      case CANNON -> type = BOMB;
    }
    return type;
  }
}

class Explosion {
  private Point2D.Float position;
  private int explosionTick = 0, explosionIndex = 0;

  public Explosion(Point2D.Float position) {
    this.position = position;
  }

  public void update() {
    explosionTick++;
    if (explosionTick >= 12) {
      explosionTick = 0;
      explosionIndex++;
    }
  }

  public Point2D.Float getPosition() {
    return this.position;
  }

  public int getIndex() {
    return this.explosionIndex;
  }

}
