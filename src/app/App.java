package app;


import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * Highest level Game logic for the game
 * Deals with the differing scenes (mainscreen, settings, etc).
 */
public class App extends Canvas implements Runnable {

    private static final long serialVersionUID = 5430513214412853815L;
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
    private DisplayScreen game;
    private DisplayScreen titleScreen;
    
    /**
     * Creates all the main components of the Application
     */
    public App() {
        //General setup
        gameValues = new GameValues();
        setupGUI();
        inputSetup();
        
        //Different Screens setup
        game = new Game(frame);
        titleScreen = new TitleScreen(frame);

        //Start displaying/updating everything
        gameValues.currentScreen = titleScreen;
        gameValues.gameState = GameState.RUNNING;
        new Thread(this).start();
    }

    /**
     * Sets up GUI stuff (JFrame)
     */
    private void setupGUI() {
        // Most of this stuff does what it says... If you want to see what it does,
		// mess around with it a bit (but put it back when you're done)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        System.out.println(screenSize.getWidth() + " x " + screenSize.getHeight() + " : Monitor Size");
		//System.out.println(screenSize.getWidth() / constants.SCALE + ", " + screenSize.getHeight() / constants.SCALE + " :: Yuh");

		setMinimumSize(new Dimension((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2));
		setMaximumSize(new Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight()));
		setPreferredSize(new Dimension((int)screenSize.getWidth()/2, (int)screenSize.getHeight()/2));

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
        frame.setVisible(true);

        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
    }

    /**
     * Sets up the input (KeyListeners, MouseListeners, etc)
     */
    private void inputSetup(){
        requestFocus();
        gameInputs = new Input(gameValues);
        addKeyListener(gameInputs);
        addMouseListener(gameInputs);
        addMouseWheelListener(gameInputs);
    }

    @Override
    /**
     * The big loop that allows the game to update its game logic and render
     */
    public void run() {

        System.out.println("In the game!");

        //Sets the specified FPS according to gameValues.nanoSecondsPerTick
        long previousNano = System.nanoTime();
        double totalNano = 0;

        //Keep track of tps and fps
        long previousMillis = System.currentTimeMillis();

        //Only update the game if its running
        while (gameValues.gameState == GameState.RUNNING) {

            //Only worry about updating game logic when playing the game
            if (gameValues.currentScreen == game)   {
                long currentNano = System.nanoTime();
                totalNano += (currentNano - previousNano);
                previousNano = currentNano;

                //Each tick updates the game logic
                if (totalNano >= gameValues.nanoSecondsPerTick) {
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
            if (currentMillis - previousMillis >= gameValues.oneSecondInMillis) {
                System.out.println("FPS: " + gameValues.framesPerSecond + ", TPS: " + gameValues.ticksPerSeconds);
                previousMillis = currentMillis;
                gameValues.framesPerSecond = gameValues.ticksPerSeconds = 0;
            }
        }
    }

    /**
     * Handles all the graphics of the game, and calls the current DisplayScreens to do their part
     */
    public void render() {
        // Printing to screen
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2); // TODO Change to triple buffer if needed
			return;
		}
		// Everything drawn will go through Graphics or some type of Graphics
        Graphics g = bs.getDrawGraphics();
        
        //Print whatever has to be to the screen
        gameValues.currentScreen.render(g);

        //Closes the graphics and shows the screen
        g.dispose();
		bs.show();
    }


}