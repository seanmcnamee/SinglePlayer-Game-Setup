package app.screens;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;

import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;

public class DeadScreen extends DisplayScreen {

    private HighScoresScreen highScores;
    private GameValues gameValues;
    private long firstDisplayTime;

    public DeadScreen(JFrame frame, GameValues gameValues, HighScoresScreen highScores) {
        super(frame);
        this.gameValues = gameValues;
        this.highScores = highScores;
    }

    public DeadScreen(JFrame frame, GameValues gameValues) {
        super(frame);
        this.gameValues = gameValues;
    }

    public void setHighScores(HighScoresScreen highScores) {
        this.highScores = highScores;
    }

    @Override
    public void render(Graphics g) {
        if (firstDisplayTime == 0) {
            firstDisplayTime = System.currentTimeMillis();
            highScores.addMostRecentScore();
        }
        
        this.gameValues.fontSize = 3 * (15.1522*gameValues.gameScale - .4976);
        //g.setFont
        g.setColor(Color.RED);
        g.setFont(new Font(gameValues.gameFont.getFontName(), Font.BOLD, (int)this.gameValues.fontSize));

        double centerOfScreenX = .5 * gameValues.WIDTH_SCALE_1*gameValues.gameScale;
        double thirdOfScreenY = (1/3.0) * gameValues.HEIGHT_SCALE_1*gameValues.gameScale;

        String[] displays = {"You lost.", "Score: " + gameValues.score, "Press any key to continue"};

        for (int i = 0; i < displays.length; i++) {
            g.drawString(displays[i], (int)(centerOfScreenX - (displays[i].length()/2 * gameValues.fontSize)), (int)(thirdOfScreenY+i*gameValues.fontSize));
            
        }

    }

    public void mousePressed(MouseEvent e){
        if (switchCoolDown()) { 
            switchToHighScoreScreen();
        }
    }

    @Override
    public void keyPressed(KeyEvent e){
        if (switchCoolDown()) { 
            switchToHighScoreScreen();
        }
    }

    private boolean switchCoolDown() {
        return (System.currentTimeMillis() - this.firstDisplayTime > 1000);
    }

    private void switchToHighScoreScreen() {
        this.gameValues.currentScreen = highScores;
    }
    
}
