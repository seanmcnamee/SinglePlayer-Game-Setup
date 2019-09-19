package app;

enum GameState {
    NOTSTARTED, RUNNING, WON, LOST;
}
/**
 * GameValues for the game
 */
public class GameValues {

    /**
     * TODO Figure out exact usage of SCALE
     */
    public final int SCALE = 4;
    /**
     * The numerator the the number of nanoseconds in 1 second
     * The denomiator is the Ticks Per Second
     */
    public final double nanoSecondsPerTick = 1000000000d / 60d;
    public final int oneSecondInMillis = 1000;
    public final String NAME = "General SinglePlayer Game Setup";


    public GameState gameState = GameState.NOTSTARTED;
    public DisplayScreen currentScreen;

    public int ticksPerSeconds = 0;
    public int framesPerSecond = 0;
    //public final int WIDTH = 300;
    //public final int HEIGHT = WIDTH / 12 * 9;

}
