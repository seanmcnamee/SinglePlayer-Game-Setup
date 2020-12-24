package app.game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.Color;

import app.game.augments.HUD;
import app.game.gamefield.movable.player.Player;
import app.game.gamefield.map.*;
import app.supportclasses.GameValues;

public class GameField {
    private GameValues gameValues;
    private Map gameMap;
    private HUD hud;
    private Player player;
    
    public GameField(GameValues gameValues, Player player) {
        this.gameValues = gameValues;
        this.player = player;
        this.gameMap = new Map(gameValues);
        this.hud = new HUD(this.gameValues, this.gameMap, player);
        gameMap.addMovable(player);
    }

    public void tick() {
        gameMap.tick(player);
    }


    public void render(Graphics g) {


        //The 0,0 is at the center of the top left tile (accounting for wall thickness and half the tile size)
        //final double halfABlock = .5;
        //this.gameValues.fieldXZero = gameValues.fieldXStart+(gameValues.singleSquareX*(gameValues.WALL_THICKNESS+halfABlock));
        //this.gameValues.fieldYZero = gameValues.fieldYStart+(gameValues.singleSquareY*(gameValues.WALL_THICKNESS+halfABlock));
		
		gameMap.render(g);
		hud.render(g);

		g.setColor(Color.GRAY);
        //g.fillRect(100, 10, 500, 500);
        g.fillRect((int)(0), (int)(0), (int)(gameValues.frameWidth), (int)(gameValues.fieldYStart));
		g.fillRect((int)(0), (int)(0), (int)(gameValues.fieldXStart), (int)(gameValues.frameHeight));
		g.fillRect((int)(this.gameValues.fieldXStart+gameValues.fieldXSize), (int)(0), (int)(this.gameValues.fieldXStart), (int)(gameValues.frameHeight));
		g.fillRect((int)(0), (int)(gameValues.fieldYStart+gameValues.fieldYSize), (int)(gameValues.frameWidth), (int)(gameValues.fieldYStart));


        //this.gameValues.fontSize = 15.1522*gameValues.gameScale - .4976;

        /*
        if (gameValues.debugMode) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("TimesRoman", Font.BOLD, (int)gameValues.fontSize));
            DecimalFormat df = new DecimalFormat("#,###,##0.00");
            g.drawString("(" + df.format(player.getX()) + ", " + df.format(player.getY()) + ")", (int)(gameValues.fieldXStart), (int)(gameValues.fieldYStart+.25*gameValues.singleSquareX));
        }*/

                
	}
	
	public void mousePressed(MouseEvent e){
        player.mousePressed(e, gameMap);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
	}

	public boolean isPlayerDead() {
		return player.isDead();
	}

    /*
    	// When a key is preesed down, this is called
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_E)	{
			player.getInv().setOpen(!player.getInv().isOpen());
		}
		
		//Different things happen when the inventory is open!!
		if (player.getInv().isOpen())	{
			if (key == KeyEvent.VK_ENTER)	{
				player.getInv().switchItemWithHand();
				//player.getInv()
			}
		} 	else	{
			if (key == KeyEvent.VK_F3) {
				DEBUG = !DEBUG;
				// gameMap.removeEntity(player);
			} else if (key == KeyEvent.VK_F4) {
				player.setExplore((player.getExplore() + 1) % 3);
			}	else if(key == KeyEvent.VK_F7)	{
				player.setRockBound(!player.isRockBound());
				System.out.println("Casting Spell");
			}	else if(key == KeyEvent.VK_SHIFT)	{
				player.setRun(true);
			} else {
				setMovement(e, true);
			}

			for (int i = 0; i < player.getInv().getHotBarSize(); i++)	{
				if (key == i+KeyEvent.VK_1)	{
					player.getInv().setSelected(i);
				}
			}
		}
		
		
		
	}

	// When the key it finished being pressed, this is called
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SHIFT)
		{
			player.setRun(false);
		}	else
		{
			setMovement(e, false);
		}
	}

	private void setMovement(KeyEvent e, boolean b) {
		// TODO
		// At the top, figure out what player is playing so it can change the correct
		// one.
		// 'player' is the player running this instance of the Game
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			player.setUp(b);
		}
		if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			player.setDown(b);
		}
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			player.setLeft(b);
		}
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			player.setRight(b);
		}
	}

	public void mouseRotated(MouseWheelEvent e)	{
		player.getInv().setSelected(player.getInv().getSelected()+e.getWheelRotation());
    }
    */
}
