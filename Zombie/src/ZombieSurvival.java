// Ben Canty (rbc6du) and Hunter Burrell (hxb5te)

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
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
	private int boomTick;
	private int phaseH;
	private int phaseZ;
	private int phasePUB;
	private int phasePUS;
	private int phasePUI;
	private boolean isInvincible;
	boolean gameover;
	private int playerDrawMode;
	
	private PowerUp thePowerUp;
	
	private boolean bombExploded;
	private boolean powerUpActive;
	private boolean wasActivated;
	

	public ZombieSurvival() throws Exception {
		canvas = new SimpleCanvas(BOARDWIDTH, BOARDHEIGHT, this);
		output = new InfoFrame(this);
		player = new Human();
		zombies = new ArrayList<Zombie>();
			zombies.add(new Zombie(350,-500));
		obstacles = new ArrayList<Rectangle>();
		thePowerUp = new PowerUp(-50,-50,0);
		score = 0;
		playerDrawMode = 0;
		isInvincible = false;
		
		ticks = 0;
		boomTick = 0;
		
		phaseH = 0;
		phaseZ = 0;
		phasePUB = 0;
		phasePUS = 0;
		phasePUI = 0;
		
		gameover = false;
		
		bombExploded = false;
		powerUpActive = false;
		wasActivated = false;
		
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
		if (button == -1) {
			player.setTry(x, y);
		}
		if(gameover==false) {
		if (button == 1) {
				if(player.getNumOfBombs() > 0) {
					player.addNumOfBombs(-1);
					output.println("BOOM!\tYou have "+player.getNumOfBombs()+" bombs remaining");
					detonateBomb();
					bombExploded = true;
				} else {
				output.println("Out of Bombs!");
				}
			}
		}
		if (button == 3) {
			if(player.getNumOfBombs() > 0) {
				player.addNumOfBombs(-1);
				output.println("BOOM!\tYou have "+player.getNumOfBombs()+" bombs remaining");
				detonateBomb();
				bombExploded = true;
			} else {
			output.println("Out of Bombs!");
			}
		}
	}

	public void detonateBomb() {
		int numZombKilled=0;
		Rectangle bomb = new Rectangle((int) (player.getX()-100) , (int) (player.getY()-100) , 300 , 300 );
		for(int i=0; i<zombies.size(); i++) {
			if(bomb.intersects(zombies.get(i).getCollisionBox())) {
				zombies.remove(i);
				numZombKilled++;
			}
		}
		score = (int) (score + numZombKilled * ( (int) (Math.sqrt(numZombKilled) + 0.5)) );
		output.println("[Killed "+ numZombKilled +" zombies]\t("+numZombKilled * ((int) (Math.sqrt(numZombKilled) +0.5))+"pts)");
	}

	public boolean draw(Graphics2D g, float elapsedTime) {

		//Keep track of the number of draw cycles
		ticks++;

		//Obstacle actions go here
		for(int i=0; i<obstacles.size(); i++) {
			canvas.drawObstacle(g, obstacles.get(i));
			player.getCollision(obstacles.get(i));
			for(int j=0;j<zombies.size(); j++) { zombies.get(j).getObstacleCollision(obstacles.get(i)); }
		}
		
		//Drawing and moving the player
		player.move(elapsedTime);	//Must occur after "getCollision()" is called -- how the Human class works.
		canvas.drawHuman(g, player, playerDrawMode+player.getDirection(), phaseH);

		////////////////////////////////////////////////////////////////////
		//Time sensitive events
		if(ticks%100 == 0) { 
			score++; 
			phaseH++;
			if(phaseH==8) { phaseH=0; }
			phasePUB++;
			if(phasePUB==4) { phasePUB=0; }
			phasePUS++;
			if(phasePUS==6) { phasePUS=0; }
			phasePUI++;
			if(phasePUI==5) { phasePUI=0; }
		}
		if(ticks%200 == 0) {
			phaseZ++;
			if(phaseZ==3) { phaseZ=0; }
		}				
		if(ticks%5000 == 0) {
			int zx = (int) (Math.random()*(BOARDWIDTH-45));
			int zy = (int) (Math.random()*(BOARDHEIGHT-81));
			boolean conflict = false;
			
			Confliction1:
			for(int i=0; i<obstacles.size(); i++) {
				if(new Rectangle(zx,zy,50,90).intersects(obstacles.get(i))) {
					conflict = true;
					break Confliction1;
				}
			}
			
			Confliction2:
			for(int i=0; i<zombies.size(); i++){
				if(new Rectangle(zx,zy,50,90).intersects(zombies.get(i).getCollisionBox())) {
					conflict = true;
					break Confliction2;
				}
			}
			
			if(new Rectangle(zx,zy,90,130).intersects(player.getSpawnbox())) { conflict = true; }
			
			if(!conflict) { zombies.add(new Zombie(zx,zy)); }
		}		
		if(ticks%50000 == 0) {
			player.addNumOfBombs(1);
			output.println("New Bomb Acquired! ("+player.getNumOfBombs()+")");
		}
		if(ticks%35000 == 0) {
			powerUpActive = true;
			wasActivated = true;
		}
		if(ticks == 100001) 		{ ticks = 1; }
		////////////////////////////////////////////////////////////////////
		
		//Zombie actions go here
		
			for(int i=0; i<zombies.size(); i++) {
				canvas.drawZombie(g, zombies.get(i), zombies.get(i).getDirection(), phaseZ);
				zombies.get(i).setTry(player.getX(),player.getY());
				
				if(!isInvincible) {
					if(zombies.get(i).playerCollision(player.getHitbox())) { gameover = true; }
				}
				for(int j=0; j<zombies.size(); j++) {
					if(i != j) { zombies.get(i).getObstacleCollision(zombies.get(j).getCollisionBox()); }
					}
				zombies.get(i).move(elapsedTime);
			}
		
		////////////////////////////////////////////////////////////////////
		// Boolean sprite animations (explosions and powerups)
		if(bombExploded) {
			boomTick++;
			canvas.drawBoom(g,player);
			if(boomTick==500) {
				boomTick=0;
				bombExploded=false;
			}
		}
		
		////////////////////////////////////////////////////////////////////
		int puX = (int) (Math.random()*BOARDWIDTH);
		int puY = (int) (Math.random()*BOARDHEIGHT);
		int powerUpChooser = (int) (Math.random()*3);
		//
		if(powerUpActive) {
			if(wasActivated == true) {	
				thePowerUp = new PowerUp(puX,puY,powerUpChooser);
				wasActivated = false;
			}
			if(thePowerUp.getID() == 0) {
				canvas.drawPowerUp(g, thePowerUp.getX(), thePowerUp.getY(), thePowerUp.getID(), phasePUB);
				if(player.getHitbox().intersects(thePowerUp.getHitbox())) {
					player.addNumOfBombs(1);
					output.println("Power UP!  Extra Bomb! ("+ player.getNumOfBombs() +")");
					thePowerUp = new PowerUp(-50,-50,0);
				}
			}
			if(thePowerUp.getID() == 1) {
				canvas.drawPowerUp(g, thePowerUp.getX(), thePowerUp.getY(), thePowerUp.getID(), phasePUS);
				if(player.getHitbox().intersects(thePowerUp.getHitbox())) {
					player.setSpeed(player.getSpeed() + 7);
					output.println("Power UP!  Speed Boost!");
					thePowerUp = new PowerUp(-50,-50,0);
				}
			}
			if(thePowerUp.getID() == 2) {
				canvas.drawPowerUp(g, thePowerUp.getX(), thePowerUp.getY(), thePowerUp.getID(), phasePUI);
				if(player.getHitbox().intersects(thePowerUp.getHitbox())) {
					playerDrawMode = 5;
					isInvincible = true;
					output.println("Power UP!  Invincibility!");
					thePowerUp = new PowerUp(-50,-50,0);
					thePowerUp.setLifespan(34999-17530);
				}
			}
			
			//Take care of stuff when the powerup's effect wears up
			powerUpActive = thePowerUp.isAlive();
			if(!thePowerUp.isAlive()) {
				if(thePowerUp.getID() == 2) {
					playerDrawMode = 0;
					isInvincible = false;
				}
			}
		}

		////////////////////////////////////////////////////////////////////
		
		
		
		////////////////////////////////////////////////////////////////////
		if(gameover) {
			output.println("GAME OVER");
			output.println("Your Score: "+score);
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		ZombieSurvival simulator = new ZombieSurvival();
		simulator.play();
	}

	public void play() {
		canvas.setupAndDisplay();
	}
}