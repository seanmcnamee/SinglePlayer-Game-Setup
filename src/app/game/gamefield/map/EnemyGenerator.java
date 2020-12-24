package app.game.gamefield.map;

import java.awt.geom.Point2D;

import app.game.gamefield.movable.enemies.Enemy;
import app.game.gamefield.movable.enemies.Zombie;
import app.game.gamefield.touchable.Touchable;
import app.supportclasses.GameValues;

public class EnemyGenerator {
    private Map gameMap;
    private double spawnRateCounter;
    private double spawnsPerTick;
    private GameValues gameValues;

    public EnemyGenerator(GameValues gameValues, Map map) {
        this.gameValues = gameValues;
        this.gameMap = map;
        this.spawnsPerTick = gameValues.spawnsPerSecond / gameValues.goalTicksPerSecond;
        this.spawnRateCounter = 0;
    }

    public void tick(Touchable target) {
        this.spawnRateCounter += spawnsPerTick;
        //Each time the time to make a new enemy comes
        if (spawnRateCounter >= 1) {
            //Decrease the time until the next one, and add the enemy
            if (spawnsPerTick < .5) {
                this.spawnsPerTick *= gameValues.SPAWN_RATE_MULTIPLIER;
            }
            while (spawnRateCounter >=1) {
                addEnemy(target);
                spawnRateCounter--;
            }
        }
    }

    private void addEnemy(Touchable target) {
        //Create an enemy in Guam (so it can use its size)
        Enemy newEnemy = new Zombie(gameValues, new Point2D.Double(Integer.MAX_VALUE, Integer.MAX_VALUE));

        //Figure out a location that works
        Point2D.Double enemyLocation;
        do {
            double angle = getRandomAngle();
            enemyLocation = getLocation(target, angle, gameValues.ENEMY_SPAWN_RADIUS);
        } while (!isValidLocation(newEnemy, enemyLocation));
        newEnemy.setLocation(enemyLocation);
        gameMap.addMovable(newEnemy);
    }

    /**
     * Returns angle between 0 and 2pi
     */
    private double getRandomAngle() {
        return Math.random()*Math.PI*2;
    }

    private Point2D.Double getLocation(Touchable target, double theta, double distance) {
        double xLoc = distance*Math.cos(theta) + target.getLocation().x;
        double yLoc = distance*Math.sin(theta) + target.getLocation().y;
        return new Point2D.Double(xLoc, yLoc);
    }

    private boolean isValidLocation(Enemy newEnemy, Point2D.Double enemyLocation) {
        boolean withinMap = enemyLocation.x > 0 && enemyLocation.x < gameValues.MAPSIZE.x && enemyLocation.y > 0 && enemyLocation.y < gameValues.MAPSIZE.y;
        if (!withinMap) {
            return false;
        }
        if (gameMap.collisionWith(newEnemy, enemyLocation) != null) {
            return false;
        }
        return true;
    }


}
