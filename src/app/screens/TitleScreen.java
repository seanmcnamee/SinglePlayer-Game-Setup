package app.screens;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;


import app.supportclasses.BufferedImageLoader;
import app.supportclasses.Button;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;



import java.awt.event.MouseEvent;

/**
 * MainScreen of the App
 */
public class TitleScreen extends DisplayScreen {

    //private final BufferedImageLoader background;
    private Button btnStart, btnHighScores;
    private BufferedImageLoader background;
    private GameValues gameValues;
    private DisplayScreen gameScreen, highScores;

    public TitleScreen(JFrame frame, GameValues gameValues, DisplayScreen game, DisplayScreen highScores) {
        super(frame);
        background = new BufferedImageLoader(gameValues.MAIN_MENU_FILE);
        BufferedImageLoader playImg = new BufferedImageLoader(gameValues.PLAY_BUTTON_FILE);
        BufferedImageLoader highscoresImg = new BufferedImageLoader(gameValues.HIGH_SCORES_BUTTON_FILE);
        
        btnStart = new Button(playImg.getImage(), new Point((int)(gameValues.PLAY_BUTTON_LOCATION.x*gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.PLAY_BUTTON_LOCATION.y*gameValues.HEIGHT_SCALE_1*gameValues.gameScale)), gameValues);
        btnHighScores = new Button(highscoresImg.getImage(), new Point((int)(gameValues.HIGH_SCORES_BUTTON_LOCATION.x*gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HIGH_SCORES_BUTTON_LOCATION.y*gameValues.HEIGHT_SCALE_1*gameValues.gameScale)), gameValues);

        
        this.gameValues = gameValues;
        this.gameScreen = game;
        this.highScores = highScores;

        //font = setFont(gameValues);
    }

    /*
    private Font setFont(GameValues gameValues) {
        Font returningFont = null;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream(gameValues.gameFontFile));
            Font temp = Font.createFont(Font.TRUETYPE_FONT, myStream);
            returningFont = temp.deriveFont(Font.PLAIN, 50);          
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        return returningFont;
    }
    */

    @Override
    public void render(Graphics g) {

        g.drawImage(background.getImage(), 0, 0, (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale), null);
        //g.setColor(Color.BLACK);
        //g.drawRect(0, 0, (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale));
        btnStart.render(g);
        btnHighScores.render(g);

        //g.setFont(font);
        //g.drawString("START", mainGUI.getContentPane().getWidth()/2 - 60, (int)(mainGUI.getContentPane().getHeight()*.75));
    }

    public void mouseClicked(MouseEvent e){
        if (btnStart.contains(e.getPoint())) {
            startGame();
        } else if (btnHighScores.contains(e.getPoint())) {
            highScores();
        }
        System.out.println("Mouse clicked at: " +e.getPoint());
        
    }

    public void mouseMoved(MouseEvent e) {
        //Set hovering effect for the following buttons...
        //btnStart
        if (!btnStart.isHovering() && btnStart.contains(e.getPoint())) {
            btnStart.setHovering(true);
        }   else if (btnStart.isHovering() && !btnStart.contains(e.getPoint())) {
            btnStart.setHovering(false);
        //btnHighScores
        } else if (!btnHighScores.isHovering() && btnHighScores.contains(e.getPoint())) {
            btnHighScores.setHovering(true);
        }   else if (btnHighScores.isHovering() && !btnHighScores.contains(e.getPoint())) {
            btnHighScores.setHovering(false);
        }
    }

    private void startGame() {
        System.out.println("Starting Game");
        System.out.println("Setting currentScreen to 'game'");
        System.out.println("Game: " + gameScreen);
        ((Game)gameScreen).initialize();
        gameValues.currentScreen = gameScreen;
    }

    private void highScores() {
        gameValues.currentScreen = highScores;
    }

    private void exitGame() {
        gameValues.gameState = GameValues.GameState.QUIT;
        System.exit(0);
    }

}