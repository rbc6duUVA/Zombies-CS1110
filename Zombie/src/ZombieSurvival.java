// Ben Canty (rbc6du) and Hunter Burrell (hxb5te)

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

	private SimpleCanvas canvas;
	
	private InfoFrame output; 	// The InfoFrame that you use for output instead of System.out
	
	private final int BOARDHEIGHT = 700;
	private final int BOARDWIDTH = 700;
	
	private Human player;
	private ArrayList<Rectangle> obstacles;
	private ArrayList<Zombie> zombies;
	private int score;
	private int ticks;
	boolean gameover;
	

	public ZombieSurvival() throws Exception {
		canvas = new SimpleCanvas(BOARDWIDTH, BOARDHEIGHT, this);
		output = new InfoFrame(this);
		player = new Human();
		zombies = new ArrayList<Zombie>();
		obstacles = new ArrayList<Rectangle>();
		score = 0;
		ticks = 0;
		gameover = false;
		loadObstacles("course.csv");
	}
	
	public void loadObstacles(String filename) throws Exception {
		Scanner s = new Scanner(new File(filename));
		while(s.hasNextLine()) {
			String[] temp = s.nextLine().split(",");
			obstacles.add(new Rectangle( Integer.parseInt(temp[0]) , Integer.parseInt(temp[1]) , Integer.parseInt(temp[2]) , Integer.parseInt(temp[3]) ));
		}
		s.close();
	}

	public void mouseAction(float x, float y, int button) {
		// TODO: Change this method to help the player move!
		if (button == -1) {
			player.setTry(x, y);
		}
		if (button == 1) {
			output.println("BOMB!");
			player.addNumOfBombs(-1);
			detonateBomb();
		}
		if (button == 3) {
			output.println("You clicked the right mouse button!");
		}
	}

	public void detonateBomb() {
		/*
		 * This method controls the bomb action. It should check to see if the
		 * player has any bombs. If so, that count should be decremented by one.
		 * Then every zombie within a 50 pixel radius of the player should be
		 * eliminated.
		 */
	}

	public void draw(Graphics2D g, float elapsedTime) {
		//Keep track of the number of draw cycles
		ticks++;
		
		//Obstacle actions go here
		for(int i=0; i<obstacles.size(); i++) {
			canvas.drawObstacle(g, obstacles.get(i));
			player.getCollision(obstacles.get(i));
			//for(int j=0;j<zombies.size(); j++) { zombies.get(j).getCollision(obstacles.get(i); }
		}
		
		//Drawing and moving the player
		player.move(elapsedTime);	//Must occur after "getCollision()" is called -- how the Human class works.
		canvas.drawHuman(g, player);

		//Time sensitive events
		if(ticks%100 == 0) 		{ score++; }
		if(ticks%5000 == 0) 	{ zombies.add(new Zombie()); }		//needs a way to ensure that the new zombie wont spawn inside an obstacle
		if(ticks%50000 == 0) 	{ player.addNumOfBombs(1); }
		if(ticks == 50001) 		{ticks = 1;}
		
		//Zombie actions go here
		for(int i=0; i<zombies.size(); i++) {
			canvas.drawZombie(g, zombies.get(i));
			//Check for zombie collision with player here, if collision set gameover=true;
		}
		
		//Using return (in void) to terminate the game is gameover occurs
		if(gameover) {
			return;
		}

	}

	public static void main(String[] args) throws Exception {
		ZombieSurvival simulator = new ZombieSurvival();
		simulator.play();
	}

	public void play() {
		canvas.setupAndDisplay();
	}
}
