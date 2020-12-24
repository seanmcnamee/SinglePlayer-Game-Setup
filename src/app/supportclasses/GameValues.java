package app.supportclasses;

import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Font;

import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * GameValues for the game
 */
public class GameValues {

    public enum GameState {
        NOTSTARTED, RUNNING, WON, LOST, QUIT, PAUSED;
    }

    public static Font getFont() {
        String GAME_FONT_FILE = "res//Wide Latin Regular.ttf";
        Font returningFont = null;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream(GAME_FONT_FILE));
            Font temp = Font.createFont(Font.TRUETYPE_FONT, myStream);
            returningFont = temp.deriveFont(Font.PLAIN, 50);          
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        return returningFont;
    }

    //Overall Application Values
    public double gameScale = 1;
    
    public final int HEIGHT_SCALE_1 = 590;//WIDTH_SCALE_1 / 12 * 9;
    public final int WIDTH_SCALE_1 = (int) (HEIGHT_SCALE_1 * (1+8.0/9.0));
    public double frameWidth = WIDTH_SCALE_1;
    public double frameHeight = HEIGHT_SCALE_1;
    
    
    public final double goalTicksPerSecond = 60.0;
    public final double NANO_SECONDS_PER_TICK = 1000000000d / goalTicksPerSecond; // (NanoSeconds/1 seconds) * (1 second/nanoseconds in 1 tick)
    public final int ONE_SEC_IN_MILLIS = 1000;
    public int ticksPerSeconds = 0;
    public int framesPerSecond = 0;

    public final String NAME = "Battle Arena - Sean McNamee";
    public final Font gameFont = getFont();
    public double fontSize = 0;
    public final Color INGAME_TEXT_COLOR = Color.RED;

    public GameState gameState = GameState.NOTSTARTED;
    public DisplayScreen currentScreen;

    public boolean debugMode = false;

    //Button value
    public final float LIGHTEN_DARKEN_AMOUNT = 50;

    //TitleScreen values
    public final double START_BUTTON_Y = .5;
    public final double START_BUTTON_X = .5;
    public final String MAIN_MENU_FILE = "res//menu_page.png";
    public final String PLAY_BUTTON_FILE = "res//play_button.png";
    public final Point2D.Double PLAY_BUTTON_LOCATION = new Point2D.Double(.2, .35);
    public final String HIGH_SCORES_BUTTON_FILE = "res//high_scores_button.png";
    public final Point2D.Double HIGH_SCORES_BUTTON_LOCATION = new Point2D.Double(.29, .6);


    //HighScores Screen values
    public final String HIGHSCORES_FILE = "res//high_scores_page.png";
    public final String MAIN_MENU_BUTTON_FILE = "res//title_screen_button.png";
    public final Point2D.Double MAIN_MENU_BUTTON_LOCATION = new Point2D.Double(.22, .89);
    public final String HIGH_SCORES_DOCUMENT = "res//highscores.txt";
    public final Point2D.Double SCORES_START = new Point2D.Double(.5, .1);


    /////Game Values
    public int score = 0;
    public final int missScore = -1;
    public final int hitScore = 3;
    
    //GameField Sizing
    public final Point MAPSIZE = new Point(50, 50);
    public final int FIELD_X_SPACES = 40;
    public final int FIELD_Y_SPACES = HEIGHT_SCALE_1*FIELD_X_SPACES/WIDTH_SCALE_1;  
    public final int WALL_THICKNESS = 1;

    public double fieldXStart = 0;//For entire field display
    public double fieldYStart = 0;
    public double fieldXSize = 0;
    public double fieldYSize = 0;
    public double fieldXZeroOffset = 0;//For in game location representation //TODO allow change for screen scrolling (large room)
    public double fieldYZeroOffset = 0;
    public double singleSquareX = 0;
    public double singleSquareY = 0;

    //SpriteSheet Info
    public final String SPRITE_SHEET = "res//sprite_sheet.png";
    public final int SINGLE_BOX_SIZE = 8;

    public final Point SS_INNER_TILE_LOCATION = new Point(0, 0);
    public final Point SS_WALL_TILE_LOCATION = new Point(1, 0);
    public final Point SS_OUTTER_TILE_LOCATION = new Point(2, 0);
    public final Point SS_TILE_SIZE = new Point(1, 1);

    public final Point SS_PLAYER_LOCATION = new Point(0, 1);
    public final Point SS_PLAYER_SIZE = new Point(2, 2);

    public final Point SS_PLAYER_PROJECTILE_LOCATION = new Point(4, 0);
    public final Point SS_PLAYER_PROJECTILE_SIZE = new Point(1, 1);

    public final Point SS_ZOMBIE_LOCATION = new Point(0, 3);
    public final Point SS_ZOMBIE_SIZE = new Point(2, 2);

    //Object Sizing
    public final Point2D.Double INGAME_TILE_SIZE = new Point2D.Double(1, 1);
    public final Point2D.Double PLAYER_SIZE = new Point2D.Double(1.9, 1.9);
    public final Point2D.Double PROJECTILE_SIZE = new Point2D.Double(.25, .25);
    public final Point2D.Double ZOMBIE_SIZE = new Point2D.Double(.9, .9);

    //Player
    public final int MAX_POSSIBLE_PLAYER_HEALTH = 1; //Should be divisible by 4 (2 to make it full hearts, 2 more for equal rows for display)
    public final double PLAYER_ACCELERATION_RATE = 10.0;
    public final double PLAYER_MAX_SPEED = 10.0;
    public final double RUN_FACTOR = 1.4;

    public final int PLAYER_PROJECTILE_MAX = 20;
    public final double MAX_PROJECTILE_SPEED = 10;
    public final int PROJECTILE_RECHARGE_TIME = 1000;

    //enemies
    public double spawnsPerSecond = 1/7.0; //1.0/30;
    public final double SPAWN_RATE_MULTIPLIER = 1.05;
    public final double ENEMY_SPAWN_RADIUS = FIELD_X_SPACES/1.5;

    public final double ZOMBIE_ACCELERATION_RATE = 8.0;
    public final double ZOMBIE_MAX_SPEED = 8.0;

    //User inputs
    public int moveUpKey = KeyEvent.VK_W;
    public int moveDownKey = KeyEvent.VK_S;
    public int moveLeftKey = KeyEvent.VK_A;
    public int moveRightKey = KeyEvent.VK_D;
    public int runKey = KeyEvent.VK_SHIFT;

    //Animation
    public final int TICKS_PER_PICTURE_STEP = 3;

    //General Movement
    public double friction = 6.0; //In blocks per second
    public final double iceAccelerationChange = 1.0/6.0;
    public final double sludgeAccelerationChange = 1.0/3.0;
    public final double projectileFriction = 1.0/5.0;
    
    //HUD
    public final double HUD_EDGE_SEPERATOR = 1.1;

    //MiniMap
    public final double MINIMAP_SIZE = 1.0 / 8.0;
    public final Color MINIMAP_COLOR = new Color(0, 0, 0, 100);
    public final Color PLAYER_COLOR = Color.GREEN;
    public final Color ZOMBIE_COLOR = Color.RED;

    //ProjectileBar
    public final double PROJECTILEBAR_SIZE = (1-MINIMAP_SIZE)*(1.0/(PLAYER_PROJECTILE_MAX*3));
    public final double PROJECTILE_SEPERATION = .2;
}
