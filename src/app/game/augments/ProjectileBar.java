package app.game.augments;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import app.game.gamefield.movable.player.Player;
import app.game.gamefield.movable.player.ProjectileHandler;
import app.supportclasses.GameValues;

public class ProjectileBar {
    private GameValues gameValues;
    private ProjectileHandler projectiles;
    
    public ProjectileBar(GameValues gameValues, ProjectileHandler p) {
        this.gameValues = gameValues;
        this.projectiles = p;
    }

	public void render(Graphics g, double xStart, double yStart, double size, double seperationSize) {
		// Print out all player projectiles
		BufferedImage pImg = this.projectiles.getProjectileImage();

		//Minmapable elements
		for (int i = 0; i < this.projectiles.getProjectilesLeft(); i++) {
			g.drawImage(pImg, (int)xStart, (int)(yStart - (i*(seperationSize+size))), (int)size, (int)size, null);
		}
    }
}
