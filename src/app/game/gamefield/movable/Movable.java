package app.game.gamefield.movable;

import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.Color;

import app.game.gamefield.map.Map;
import app.game.gamefield.touchable.Touchable;
import app.supportclasses.GameValues;

public abstract class Movable extends Touchable {
    protected Point2D.Double percentVelocity;
    protected Point percentAcceleration;
	protected double accelerationRate;
    protected double maxSpeed;
    protected double friction;

    public Movable(GameValues gameValues, Point2D.Double location, Point2D.Double startingVelocity, BufferedImage image, Point2D.Double sizeinBlocks) {
        super(gameValues, location, image, sizeinBlocks);
        this.percentVelocity = startingVelocity;
        this.percentAcceleration = new Point();
        this.friction = gameValues.friction;
        this.health = 1;
    }

    public Movable(GameValues gameValues, Point2D.Double location, Color c) {
        super(gameValues, location, c);
        this.percentVelocity = new Point2D.Double();
        this.percentAcceleration = new Point();
        this.friction = gameValues.friction;
        this.health = 1;
    }

    public void accelerate(boolean up, boolean down, boolean left, boolean right) {
        accelerate(up?-1:0, down?1:0, left?-1:0, right?1:0);
    }

    public void accelerate(int up, int down, int left, int right) {
        accelerate(up+down, left+right);
    }

    public void accelerate(int yAcc, int xAcc) {
        percentAcceleration.x = (int)Math.signum(xAcc);
        percentAcceleration.y = (int)Math.signum(yAcc);
    }

    public abstract void accelerate(Touchable target);

    public void updateVelocity() {
        //Converts from blocks/second to blocks/tick
        final double acceleration = accelerationRate/gameValues.goalTicksPerSecond;
        final double friction = this.friction/gameValues.goalTicksPerSecond;
        final double changeWhenFull = .1;

        Point2D.Double tempPercentVelocity = (Point2D.Double) this.percentVelocity.clone();
        //Add friction and acceleration to the current velocity
        tempPercentVelocity.x += -Math.signum(tempPercentVelocity.x)*friction + acceleration*percentAcceleration.x;
        tempPercentVelocity.y += -Math.signum(tempPercentVelocity.y)*friction + acceleration*percentAcceleration.y;

        //Considers static and kinetic friction to be equal
        if (Math.abs(tempPercentVelocity.x) <= friction && Math.abs(tempPercentVelocity.y) <= friction) {
            tempPercentVelocity.x = 0;
            tempPercentVelocity.y = 0;
        }

        //When the resultant of x and y are too large, lower them both 
        //this will result in the initial direction always being slightly more than the other
        
        double resultant = Math.sqrt((Math.pow(tempPercentVelocity.x, 2) + Math.pow(tempPercentVelocity.y, 2)));
        if (resultant > 1+changeWhenFull) {
            //Lower both
            tempPercentVelocity.x *= (1-changeWhenFull);
            tempPercentVelocity.y *= (1-changeWhenFull);
        }

        this.percentVelocity = tempPercentVelocity;
    }

    public void centerScreen() {
        gameValues.fieldXZeroOffset = this.location.x - gameValues.FIELD_X_SPACES/2.0;
        gameValues.fieldYZeroOffset = this.location.y - gameValues.FIELD_Y_SPACES/2.0;
    }

    public double maxSpeedPerTick() {
        return this.maxSpeed / gameValues.goalTicksPerSecond;
    }

    public Point2D.Double getNextLocation() {
        return calculateNextLocation();
    }

    private Point2D.Double calculateNextLocation() {
        return new Point2D.Double(this.location.x + maxSpeedPerTick()*this.percentVelocity.x, this.location.y + maxSpeedPerTick()*this.percentVelocity.y);
    }

    public void updateLocation(Map m) {
        this.location = calculateNextLocation();
    }

    public void updateFromCollision(Touchable t, Map m) {
        Point2D.Double nextLocation = getNextLocation();

        Point2D.Double noYChange = new Point2D.Double(nextLocation.x, location.y);
        boolean isCollisionWithNoY = (m.collisionWith(this, noYChange) != null);
        if (!isCollisionWithNoY) {
            this.percentVelocity.y = 0;
            this.location = noYChange;
            return;
        }

        Point2D.Double noXChange = new Point2D.Double(location.x, nextLocation.y);
        boolean isCollisionWithNoX = (m.collisionWith(this, noXChange) != null);
        if (!isCollisionWithNoX) {
            this.percentVelocity.x = 0;
            this.location = noXChange;
            return;
        }
        
        this.percentVelocity = new Point2D.Double(0, 0);
    }

    @Override
    public void gotHit(Touchable m, Map map) {
        lowerHealth();
        if (isDestroyed()) {
            map.removeMovable(this);
        }
    }
}
