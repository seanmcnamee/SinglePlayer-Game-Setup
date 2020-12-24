package app.game.gamefield.movable.enemies;

import java.awt.geom.Point2D;
import java.awt.Color;

import app.game.gamefield.map.Map;
import app.game.gamefield.movable.Movable;
import app.game.gamefield.movable.player.Player;
import app.game.gamefield.movable.projectile.Projectile;
import app.game.gamefield.touchable.HitBox;
import app.game.gamefield.touchable.Touchable;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

public abstract class Enemy extends Movable {
    public Enemy(GameValues gameValues, Point2D.Double location, Color miniMapColor) {
        super(gameValues, location, miniMapColor);
        // TODO Auto-generated constructor stub
        SpriteSheet ss = new SpriteSheet(gameValues.SPRITE_SHEET);
        this.hitbox = new HitBox();
        setImageAndSize(ss);
        setMaxSpeedAccelRateAndFrinction();
    }

    protected abstract void setImageAndSize(SpriteSheet ss);
    protected abstract void setMaxSpeedAccelRateAndFrinction();

    public void setLocation(Point2D.Double location) {
        this.location = location;
    }

    @Override
    public void updateFromCollision(Touchable t, Map m) {
        if (t.getClass().equals(Player.class)) {
            //Damage just the player
            t.gotHit(this, m);
        } else if (t.getClass().equals(Projectile.class)) {
            //Die if its a Projectile
            t.gotHit(this, m);
            this.gotHit(t, m);
        } else {
            //Otherwise regularly update
            super.updateFromCollision(t, m);
        }
    }
    
}
