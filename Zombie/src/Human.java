import java.awt.Rectangle;
import java.util.Vector;

/**
 * Human.java
 * 
 * 1. checking to see if the player's hitbox
 * Rectangle is colliding with any other hitbox Rectangle (just obstacle
 * Rectangles - Zombies can handle Human collision) 2. movement (normal movement
 * and what to do if there is a collision) 3. constructors 4. getters/setters 5.
 * bomb counting 6. anything else you may need.
 * 
 * @authors
 * @compids
 * @lab
 * 
 */
public class Human {

	private double x;
	private double y;
	private double tryX;
	private double tryY;
	private double dx;
	private double dy;
	private double speed;
	private int numOfBombs;
	Rectangle hitbox;
	private int width;
	private int height;
	private int[][] spriteImage;
	
	Rectangle killUP, killDOWN, killLEFT, killRIGHT;
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getDX() {
		return dx;
	}
	public double getDY() {
		return dy;
	}
	public void setTry(double tX, double tY) {
		tryX = tX;
		tryY = tY;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public int getNumOfBombs() {
		return numOfBombs;
	}
	public void addNumOfBombs(int increment) {
		numOfBombs += increment;
	}
	
	public Human() {
		x=300;
		y=300;
		tryX=0;
		tryY=0;
		dx=1;
		dy=1;
		speed=40;
		numOfBombs = 3;
		width = 37;
		height = 73;
		hitbox = new Rectangle( (int) x , (int) y , width , height );
		
		killUP = 	new Rectangle( (int) x 				, (int) y-1 				, width , 1);
		killDOWN = 	new Rectangle( (int) x 				, (int) (y + height + 1)	, width , 1);
		killLEFT = 	new Rectangle( (int) (x-1) 			, (int) y 					, 1 	, height);
		killRIGHT = new Rectangle( (int) (x + width + 1), (int) y 					, 1 	, height);
		
	}

	/**
	 * MUST RUN BEFORE HUMAN.MOVE(time)!!!
	 * @param target
	 * @return
	 */
	public boolean getCollision(Rectangle target) {
		if( (target.intersects(killUP)) && (dy < 0) ) {dy = 0;}
		if( (target.intersects(killDOWN)) && (dy > 0) ) {dy = 0;}
		if( (target.intersects(killLEFT)) && (dx < 0) ) {dx = 0;}
		if( (target.intersects(killRIGHT)) && (dy < 0) ) {dx = 0;}
		return target.intersects(this.hitbox);
	}
	public void homeMouse(){
		//calculate the unit vector of of Human to Desired Position;
		double deltaX = tryX - x;
		double deltaY = tryY - y;
		double xWard = deltaX / Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));
		double yWard = deltaY / Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));
		
		//Scale unit vector to speed of Human
		dx = speed*xWard;
		dy = speed*yWard;
	}
	public void move(double ellapsedTime) {
		x += dx*ellapsedTime;
		y += dy*ellapsedTime;
		hitbox.translate((int) (dx*ellapsedTime), (int) (dy*ellapsedTime));
		killUP.translate((int) (dx*ellapsedTime), (int) (dy*ellapsedTime));
		killDOWN.translate((int) (dx*ellapsedTime), (int) (dy*ellapsedTime));
		killRIGHT.translate((int) (dx*ellapsedTime), (int) (dy*ellapsedTime));
		killLEFT.translate((int) (dx*ellapsedTime), (int) (dy*ellapsedTime));
		homeMouse();
	}

		
}
