package app.game.gamefield.movable.player;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import app.game.gamefield.drawable.DrawingCalculator;
import app.game.gamefield.map.Map;
import app.game.gamefield.movable.projectile.Projectile;
import app.game.gamefield.touchable.Touchable;
import app.supportclasses.Directions;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

public class ProjectileHandler {
    private GameValues gameValues;
    private ArrayList<Projectile> projectiles;
    private int maxProjectiles;
    private int projectilesLeft;
    private BufferedImage projectileImage;
    
    public ProjectileHandler(GameValues gameValues, int maxBullets) {
        this.projectiles = new ArrayList<Projectile>();
        this.gameValues = gameValues;
        this.projectilesLeft = this.maxProjectiles = maxBullets;
        setImage();
    }

    private void setImage() {
        SpriteSheet ss = new SpriteSheet(gameValues.SPRITE_SHEET);
        this.projectileImage = ss.grabImage(gameValues.SS_PLAYER_PROJECTILE_LOCATION, gameValues.SS_PLAYER_PROJECTILE_SIZE, gameValues.SINGLE_BOX_SIZE);
    }

    public boolean canShoot() {
        return projectilesLeft > 0;
    }

    //This makes sure the Player's bullet arraylist is cleaned out
    public void checkRemoveLongestLivingProjectile(Map m) {
        if (this.projectiles.size() > 0 && !m.doesDrawableExist(this.projectiles.get(0))) {
            this.projectiles.remove(0);
        }
    }

    public boolean contains(Touchable t) {
        return this.projectiles.contains(t);
    }

    public Projectile createAndReturnProjectile(MouseEvent e, Player p, Point2D.Double location, Point2D.Double percentVelocity, double maxSpeed) {
        projectilesLeft--;
        double mouseX = DrawingCalculator.estimateLocationFromPixel(e.getX(), gameValues.fieldXZeroOffset, gameValues.singleSquareX);
        double mouseY = DrawingCalculator.estimateLocationFromPixel(e.getY(), gameValues.fieldYZeroOffset, gameValues.singleSquareY);

        Point2D.Double rawVelocityPercents = Directions.getPercentFromLocations(location.x, location.y, mouseX, mouseY);

        double projectileMaxSpeed = gameValues.MAX_PROJECTILE_SPEED+maxSpeed;

        double yPercentVel = (rawVelocityPercents.y*projectileMaxSpeed + percentVelocity.y*maxSpeed)/(projectileMaxSpeed+maxSpeed);
        double xPercentVel = (rawVelocityPercents.x*projectileMaxSpeed + percentVelocity.x*maxSpeed)/(projectileMaxSpeed+maxSpeed);
        Point2D.Double projectileVelocity = new Point2D.Double(xPercentVel, yPercentVel);


        Projectile ball = new Projectile(p, gameValues, location, projectileVelocity, projectileMaxSpeed, projectileImage);

        this.projectiles.add(ball);
        return ball;
    }

    public boolean projectilesFull() {
        return this.projectilesLeft >= this.maxProjectiles;
    }

    public void rechargeProjectile() {
        projectilesLeft++;
    }

    public int getProjectilesLeft() {
        return this.projectilesLeft;
    }

    public BufferedImage getProjectileImage() {
        return this.projectileImage;
    }
}
