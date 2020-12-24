package app;

import app.supportclasses.Input;
import app.screens.Game;
import app.screens.HighScoresScreen;
import app.screens.TitleScreen;
import app.screens.DeadScreen;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;
import app.supportclasses.GameValues.GameState;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Highest level Game logic for the game
 * Deals with the differing scenes (mainscreen, settings, etc).
 * @author Sean McNamee
 */
public class App extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
    /**
     * Starts everything.
     */
    public static void main(String[] args) throws Exception {
        new App();
    }

    //Overall app variables
    private JFrame frame;
    private GameValues gameValues;
    private Input gameInputs;

    //Screen specific variables
    private DisplayScreen titleScreen, game, deadScreen, highScores;
    
    /**
     * Creates all the main components of the Application
     */
    public App() {
        //General setup
        gameValues = new GameValues();
        setupGUI();
        inputSetup();
        this.createBufferStrategy(2); // Sets the canvas buffer count TODO Change to triple buffer if rendering is choppy
        
        //Different Screens setup
        deadScreen = new DeadScreen(frame, gameValues);
        game = new Game(frame, gameValues, deadScreen);
        highScores = new HighScoresScreen(frame, gameValues);
        titleScreen = new TitleScreen(frame, gameValues, game, highScores);
        ((HighScoresScreen)highScores).setTitleScreen(titleScreen);
        ((DeadScreen)deadScreen).setHighScores((HighScoresScreen)highScores);

        //Start displaying/updating everything
        gameValues.currentScreen = titleScreen;
        gameValues.gameState = GameState.RUNNING;
        new Thread(this).start();
        frame.setVisible(true);
    }

    /**
     * Sets up GUI stuff (JFrame)
     */
    private void setupGUI() {
        // Most of this stuff does what it says... If you want to see what it does,
		// mess around with it a bit (but put it back when you're done)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        System.out.println(screenSize.getWidth() + " x " + screenSize.getHeight() + " : Monitor Size");

		setMinimumSize(new Dimension((int)gameValues.WIDTH_SCALE_1, (int)gameValues.HEIGHT_SCALE_1));
		setMaximumSize(new Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight()));
		setPreferredSize(new Dimension((int)gameValues.WIDTH_SCALE_1, (int)gameValues.HEIGHT_SCALE_1));

		// Create the GUI itself
		frame = new JFrame(gameValues.NAME);

		// Allow for trapclose (X button)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(this, BorderLayout.CENTER);
		frame.pack();

		frame.setResizable(true);
		frame.setLocationRelativeTo(null);

		frame.setLocation((int)(screenSize.getWidth() - frame.getWidth())/2, (int)(screenSize.getHeight() - frame.getHeight())/2);
        
        System.out.println("frame size: " + frame.getContentPane().getWidth() + ", " + frame.getContentPane().getHeight());
    }

    /**
     * Sets up the input (KeyListeners, MouseListeners, etc)
     */
    private void inputSetup(){
        requestFocus();
        gameInputs = new Input(gameValues, frame);
        addKeyListener(gameInputs);
        addMouseListener(gameInputs);
        addMouseMotionListener(gameInputs);
        addMouseWheelListener(gameInputs);
        frame.addComponentListener(gameInputs);
    }

    @Override
    /**
     * The big loop that allows the game to update its game logic and render
     */
    public void run() {

        System.out.println("In the game!");

        //Sets the specified FPS according to gameValues.NANO_SECONDS_PER_TICK
        long previousNano = System.nanoTime();
        double totalNano = 0;

        //Keep track of tps and fps
        long previousMillis = System.currentTimeMillis();

        //Only update/render the application if its running
        while (gameValues.gameState == GameState.RUNNING) {

            //Only worry about updating game logic when playing the game
            if (gameValues.currentScreen == game)   {
                long currentNano = System.nanoTime();
                totalNano += (currentNano - previousNano);
                previousNano = currentNano;

                //Each tick updates the game logic
                if (totalNano >= gameValues.NANO_SECONDS_PER_TICK) {
                    totalNano = 0;
                    gameValues.ticksPerSeconds++;
                    ((Game)game).tick();
                }
            }

            //Update the screen as often as possible
            gameValues.framesPerSecond++;
            render();
            
            //Once a second, show the fps and tps of application loop
            long currentMillis = System.currentTimeMillis();
            double secondsBetween = 5.0;
            if (currentMillis - previousMillis >= secondsBetween*gameValues.ONE_SEC_IN_MILLIS) {
                System.out.println("FPS: " + (gameValues.framesPerSecond/secondsBetween) + ", TPS: " + (gameValues.ticksPerSeconds/secondsBetween) +
                    ((gameValues.debugMode)? ", ScreenScale: " + gameValues.gameScale:""));
                previousMillis = currentMillis;
                gameValues.framesPerSecond = gameValues.ticksPerSeconds = 0;
            }
        }
    }

    /**
     * Handles all the graphics of the application, and calls the current DisplayScreens to do their part
     */
    public void render() {
        // Everything drawn will go through Graphics or some type of Graphics
		BufferStrategy bs = getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        
        //Print whatever has to be to the screen (default black screen behind)
        g.setColor(Color.black);
        g.fillRect(0, 0, frame.getContentPane().getWidth(), frame.getContentPane().getHeight());

        this.gameValues.fieldXSize = gameValues.WIDTH_SCALE_1*(gameValues.gameScale);
        this.gameValues.fieldYSize = gameValues.HEIGHT_SCALE_1*(gameValues.gameScale);
        double excessWidth = gameValues.frameWidth-(gameValues.WIDTH_SCALE_1*gameValues.gameScale);
        double excessHeight = gameValues.frameHeight-(gameValues.HEIGHT_SCALE_1*gameValues.gameScale);
        this.gameValues.fieldXStart = excessWidth/2.0;
        this.gameValues.fieldYStart = excessHeight/2.0;

        gameValues.currentScreen.render(g);

        //Closes the graphics and shows the screen
        g.dispose();
		bs.show();
    }
}
