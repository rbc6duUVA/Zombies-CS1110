import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * ZombieSurvival.java
 * 
 * The ZombieSurvival is the field of play for the game. It passes messages
 * between the Human and the Zombies. It also picks up the commands from the
 * mouse and does the appropriate action. This is the class that will have the
 * main method to start the game.
 * 
 * @authors
 * @compid
 * @lab
 */
public class ZombieSurvival {

	// The SurvivalField needs a canvas to draw on
	private SimpleCanvas canvas;
	
	// The InfoFrame that you use for output instead of System.out
	private InfoFrame output;
	
	// Default board size
	private final int BOARDHEIGHT = 700;
	private final int BOARDWIDTH = 700;
	
	// --------------------------------------------------------
	// Fields
	// You should setup fields to keep up with:
	// - a whole bunch of Zombies
	// - a single Human
	// - a whole bunch of obstacles, represented as Rectangles
	// - some way to know if the game is over
	// - a way to keep track of the score
	// - how many zombies you should start with
	// --------------------------------------------------------

	// -----------------------------------------
	// Methods

	/**
	 * The Constructor - This method should instantiate a new canvas, create a
	 * new player character, and create the first four zombies in random
	 * locations around the board.
	 */
	public ZombieSurvival() throws Exception {
		// Create canvas object with 500x500 spatial dimensions.
		canvas = new SimpleCanvas(BOARDWIDTH, BOARDHEIGHT, this);

		// Initialize your output frame
		output = new InfoFrame(this);

		// TODO: Here is where you should create your initial zombies and your Human
		// 20 is a good speed for the human - 10 for the zombie, but experiment!
		// You should also load your course file here to get the obstacles
		// on screen.

	}
	
	/**
	 * This method takes a file name that contains information about obstacles
	 * in the game.  It should be formatted: x,y,width,height
	 * @param filename Name of the file
	 * @throws Exception
	 */
	public void loadObstacles(String filename) throws Exception {
		// TODO: fill in this method to read the csv file and 
		// populate a list of obstacle Rectangles
	}

	/**
	 * This method should control all of your mouse actions. The mouse activity
	 * is picked up by the SimpleCanvas and then it should call this method,
	 * passing either the button that was pressed or some other flag.
	 */
	public void mouseAction(float x, float y, int button) {
		// TODO: Change this method to help the player move!
		if (button == -1) {
			output.println("Mouse: " + x + "," + y);
			
		}

		if (button == 1) {
			output.println("You clicked the left mouse button!");
		}

		if (button == 3) {
			output.println("You clicked the right mouse button!");
		}

	}

	/**
	 * This method controls the bomb action. It should check to see if the
	 * player has any bombs. If so, that count should be decremented by one.
	 * Then every zombie within a 50 pixel radius of the player should be
	 * eliminated.
	 */
	public void detonateBomb() {
		// TODO: fill in this method to kill zombies near the human!
		
	}

	/**
	 * This is the main drawing function that is automatically called whenever
	 * the canvas is ready to be redrawn. The 'elapsedTime' argument is the
	 * time, in seconds, since the last time this function was called.
	 * 
	 * Other things this method should do: 1. draw the zombies, obstacles, and the human on
	 * the screen 2. tell the zombies and human to move 3. check to see if the
	 * game is over after they move 4. halt the game if the game is over 5. update
	 * the score for every cycle that the user is alive 6. add a new zombie every
	 * 5000 or so cycles 7. add a new bomb every 50000 or so cycles
	 * 
	 * 
	 */
	public void draw(Graphics2D g, float elapsedTime) {
		// TODO: Nearly ALL your game logic will go here!  This is called on 
		// every refresh of the screen and is the "main game loop".
		
		// This is how you draw the Human, replacing the null with the human
		// object
		canvas.drawHuman(g, null);

		// This is how you draw the Zombies, replacing the null with a zombie
		// object
		canvas.drawZombie(g, null);
		
		// This is how your draw an obstacle, replacing the new Rectangle with
		// one from your list of obstacles
		canvas.drawObstacle(g, new Rectangle(400,100,20,300));

	}

	/**
	 * Your standard main method
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		ZombieSurvival simulator = new ZombieSurvival();
		simulator.play();
	}

	/**
	 * This method starts the game.
	 */
	public void play() {
		canvas.setupAndDisplay();
	}
}
