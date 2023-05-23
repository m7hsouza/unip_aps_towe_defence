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

public class ProjectileManager {
  private Playing playing;
  private ArrayList<Projectile> projectiles = new ArrayList<>();
  private BufferedImage[] projectileSprites, explosionSprites;
  private int amount = 0;
  private boolean drawExplosion;
  private int explosionTick, explosionIndex;
  private Point2D.Float explosionPosition;

  public ProjectileManager(Playing playing) {
    this.playing = playing;
    loadProjectileSprites();
  }

  private void loadProjectileSprites() {
    BufferedImage atlas = LoadSave.getSpriteAtlas();
    projectileSprites = new BufferedImage[3];
    for (int i = 0; i < 3; i++) {
      projectileSprites[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
    }
    loadExplosionSprites(atlas);
  }

  private void loadExplosionSprites(BufferedImage atlas) {
    explosionSprites = new BufferedImage[7];
    for (int i = 0; i < 7; i++) {
      explosionSprites[i] = atlas.getSubimage(i * 32, 64, 32, 32);
    }
  }


  public void update() {
    for (Projectile projectile : projectiles) {
      if (projectile.isActive()) {
        projectile.move();
        if (isHittingEnemy(projectile)) {
          projectile.setActive(false);
          if (projectile.getType() == BOMB) {
            drawExplosion = true;
            explosionPosition = projectile.getPosition();
          }
        }
      }
    }
    if (drawExplosion) {
      explosionTick++;
      if (explosionTick >= 12) {
        explosionTick = 0;
        explosionIndex++;
        if (explosionIndex >= 7) {
          explosionIndex = 0;
          drawExplosion = false;
        }
      }
    }
  }

  private boolean isHittingEnemy(Projectile projectile) {
    for (Enemy enemy : playing.getEnemyManager().getEnemies()) {
      if (enemy.getBounds().contains(projectile.getPosition())) {
        enemy.hurt(projectile.getDamage());
        return true;
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
    if (drawExplosion) {
      BufferedImage currentSprite = explosionSprites[explosionIndex];
      g.drawImage(currentSprite, (int) explosionPosition.x, (int) explosionPosition.y, null);
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
