package app.game.augments;

import java.awt.Font;
import java.awt.Graphics;

import app.game.gamefield.movable.player.Player;
import app.supportclasses.GameValues;

/**
 * Will show player state and score info
 */
public class PlayerData {
	private GameValues gameValues;
	private Player player; 
    
    public PlayerData(GameValues gameValues, Player player) {
		this.gameValues = gameValues;
		this.player = player;
	}

	public void render(Graphics g, double xStart, double yStart, double fontSize, double seperationSize) {
		g.setColor(gameValues.INGAME_TEXT_COLOR);
		g.setFont(new Font(gameValues.gameFont.getFontName(), Font.BOLD, (int)fontSize)); //(int)fontSize
		g.drawString("State: " + player.getState(), (int)xStart, (int)(yStart+fontSize));
		g.drawString("Score: " + gameValues.score, (int)xStart, (int)(yStart+2*fontSize));

    }
}
