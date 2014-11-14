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
	
	//General Fields
	private Human player;
	private ArrayList<Rectangle> obstacles;
	private ArrayList<Zombie> zombies;
	private int score;
	private int ticks;
	boolean gameover;
	
	
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

		player = new Human();
		zombies = new ArrayList<Zombie>();
		obstacles = new ArrayList<Rectangle>();
		score = 0;
		ticks = 0;
		gameover = false;
		
		loadObstacles("course.csv");
	}
	
	/**
	 * This method takes a file name that contains information about obstacles
	 * in the game.  It should be formatted: x,y,width,height
	 * @param filename Name of the file
	 * @throws Exception
	 */
	public void loadObstacles(String filename) throws Exception {
		Scanner s = new Scanner(new File(filename));
		while(s.hasNextLine()) {
			String[] temp = s.nextLine().split(",");
			obstacles.add(new Rectangle( Integer.parseInt(temp[0]) , Integer.parseInt(temp[1]) , Integer.parseInt(temp[2]) , Integer.parseInt(temp[3]) ));
		}
		s.close();
	}

	/**
	 * This method should control all of your mouse actions. The mouse activity
	 * is picked up by the SimpleCanvas and then it should call this method,
	 * passing either the button that was pressed or some other flag.
	 */
	public void mouseAction(float x, float y, int button) {
		// TODO: Change this method to help the player move!
		if (button == -1) {
			player.setTry(x, y);
		}

		if (button == 1) {
			output.println("BOMB!");
			player.addNumOfBombs(-1);
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
		ticks++;
		
		//updates all the obstacles and checks for collisions
		for(int i=0; i<obstacles.size(); i++) {
			canvas.drawObstacle(g, obstacles.get(i));
			player.getCollision(obstacles.get(i));
			//for(int j=0;j<zombies.size(); j++) { zombies.get(j).getCollision(obstacles.get(i); }
		}
		
		player.move(elapsedTime);
		canvas.drawHuman(g, player);

		if(ticks%100 == 0) { score++; }
		if(ticks%50000 == 0) { player.addNumOfBombs(1); }
		
		//Every 5000 calls of draw, a zombie is made
		if(ticks%5000 == 0) { zombies.add(new Zombie()); }		//needs a way to ensure that the new zombie wont spawn inside an obstacle
		for(int i=0; i<zombies.size(); i++) {
			canvas.drawZombie(g, zombies.get(i));
		}
		
		// This is how your draw an obstacle, replacing the new Rectangle with
		// one from your list of obstacles


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
