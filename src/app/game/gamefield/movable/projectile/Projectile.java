package app.game.gamefield.movable.projectile;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import app.game.gamefield.map.Map;
import app.game.gamefield.movable.Movable;
import app.game.gamefield.movable.enemies.Enemy;
import app.game.gamefield.movable.player.Player;
import app.game.gamefield.touchable.Touchable;
import app.supportclasses.GameValues;

public class Projectile extends Movable {
    private Touchable owner;

    public Projectile(Touchable owner, GameValues gameValues, Point2D.Double location, Point2D.Double velocity,
            double maxSpeed, BufferedImage image) {
        super(gameValues, location, velocity, image, gameValues.PROJECTILE_SIZE);
        this.owner = owner;
        this.friction = gameValues.projectileFriction;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void updateFromCollision(Touchable t, Map m) {
        // If you're touching the person who fired you, its okay
        if (t.equals(owner)) {
            this.updateLocation(m);
        } else {
            // Damage the touchable. The projectile lowers its health as well
            t.gotHit(this, m);
            this.gotHit(t, m);
        }
    }

    @Override
    public void gotHit(Touchable m, Map map) {
        if (isOwnersProjectile()) {
            if ((m instanceof Enemy) && (owner instanceof Player)) {
                this.gameValues.score += gameValues.hitScore;
            } else {
                this.gameValues.score += gameValues.missScore;
            }
        }
        super.gotHit(m, map);
    }

    @Override
    public void updateLocation(Map m) {
        super.updateLocation(m);
        if (isStopped()) {
            if (isOwnersProjectile()) {
                this.gameValues.score += gameValues.missScore;
            }
            m.removeMovable(this);
        }
    }

    public boolean isStopped() {
        return this.percentVelocity.x == 0 && this.percentVelocity.y == 0;
    }

    public void accelerate(Touchable target) {
        // Regular Projectiles don't track.
    }

    private boolean isOwnersProjectile() {
        return owner instanceof Player;
    }
}
