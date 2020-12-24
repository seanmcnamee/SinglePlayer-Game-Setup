package app.game.gamefield.movable.enemies;

import java.awt.Point;
import java.awt.geom.Point2D;

import app.game.gamefield.touchable.Touchable;
import app.supportclasses.Directions;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

public class Zombie extends Enemy {

    public Zombie(GameValues gameValues, Point2D.Double location) {
        super(gameValues, location, gameValues.ZOMBIE_COLOR);
    }

    @Override
    protected void setImageAndSize(SpriteSheet ss) {
        this.image = ss.grabImage(gameValues.SS_ZOMBIE_LOCATION, gameValues.SS_ZOMBIE_SIZE, gameValues.SINGLE_BOX_SIZE);
        this.sizeInBlocks = gameValues.ZOMBIE_SIZE;
    }

    @Override
    public void accelerate(Touchable target) {
        double xChange = target.getLocation().x - this.location.x;
        double yChange = target.getLocation().y - this.location.y;

        Point2D.Double normalizedDirection = Directions.getPercentFromVelocities(xChange, yChange);
        int xDir = (int) (Math.signum(normalizedDirection.x) * ((Math.abs(normalizedDirection.x) > .4)? 1:0));
        int yDir = (int) (Math.signum(normalizedDirection.y) * ((Math.abs(normalizedDirection.y) > .4)? 1:0));
        this.percentAcceleration = new Point(xDir, yDir);
    }

    @Override
    protected void setMaxSpeedAccelRateAndFrinction() {
        this.accelerationRate = gameValues.ZOMBIE_ACCELERATION_RATE;
        this.maxSpeed = gameValues.ZOMBIE_MAX_SPEED;
    }
}
