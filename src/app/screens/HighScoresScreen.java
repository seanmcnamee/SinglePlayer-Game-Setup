package app.screens;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.io.IOException;

import app.supportclasses.BufferedImageLoader;
import app.supportclasses.Button;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;
import app.supportclasses.HighScoreController;

public class HighScoresScreen extends DisplayScreen {

    private GameValues gameValues;
    private DisplayScreen titleScreen;
    private Button btnToTitle;
    private BufferedImageLoader background;
    private HighScoreController highScores;

    public HighScoresScreen(JFrame frame, GameValues gameValues) {
        super(frame);
        background = new BufferedImageLoader(gameValues.HIGHSCORES_FILE);
        BufferedImageLoader backToTitle = new BufferedImageLoader(gameValues.MAIN_MENU_BUTTON_FILE);

        btnToTitle = new Button(backToTitle.getImage(), new Point(
                (int) (gameValues.MAIN_MENU_BUTTON_LOCATION.x * gameValues.WIDTH_SCALE_1 * gameValues.gameScale),
                (int) (gameValues.MAIN_MENU_BUTTON_LOCATION.y * gameValues.HEIGHT_SCALE_1 * gameValues.gameScale)),
                gameValues);

        this.gameValues = gameValues;
        highScores = new HighScoreController(gameValues.HIGH_SCORES_DOCUMENT);
    }

    public void setTitleScreen(DisplayScreen titleScreen) {
        this.titleScreen = titleScreen;
    }

    public void addMostRecentScore() {
        highScores.addScore(gameValues.score);
        try {
            highScores.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(background.getImage(), 0, 0, (int) (gameValues.WIDTH_SCALE_1 * gameValues.gameScale),
                (int) (gameValues.HEIGHT_SCALE_1 * gameValues.gameScale), null);

        btnToTitle.render(g);

        g.setColor(gameValues.INGAME_TEXT_COLOR);
        g.setFont(gameValues.gameFont);
        this.gameValues.fontSize = 1.7*15.1522 * gameValues.gameScale - .4976;
        g.setFont(new Font(gameValues.gameFont.getFontName(), Font.BOLD, (int)gameValues.fontSize));

        double highScoresXStart, highScoresYStart;

        highScoresXStart = gameValues.fieldXSize*(gameValues.SCORES_START.x);
        highScoresYStart = gameValues.fieldXSize*(gameValues.SCORES_START.y);

        for (int i = 0; i < Math.min(highScores.getScores().size(), 10); i++) {
            g.drawString((i+1) + ": " + highScores.getScores().get(i), (int)highScoresXStart, (int)(highScoresYStart+i*gameValues.fontSize));
        }

        // g.drawString("START", mainGUI.getContentPane().getWidth()/2 - 60,
        // (int)(mainGUI.getContentPane().getHeight()*.75));
    }

    public void mouseClicked(MouseEvent e) {
        if (btnToTitle.contains(e.getPoint())) {
            backToTitle();
        }
    }

    public void mouseMoved(MouseEvent e) {
        // Set hovering effect for the following buttons...
        // btnToTitle
        if (!btnToTitle.isHovering() && btnToTitle.contains(e.getPoint())) {
            btnToTitle.setHovering(true);
        } else if (btnToTitle.isHovering() && !btnToTitle.contains(e.getPoint())) {
            btnToTitle.setHovering(false);
        }
    }

    private void backToTitle() {
        gameValues.currentScreen = titleScreen;
    }
}
