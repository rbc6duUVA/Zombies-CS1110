// Ben Canty (rbc6du) and Hunter Burrell (hxb5te)

import java.awt.Rectangle;

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
	Rectangle killUP, killDOWN, killLEFT, killRIGHT;
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
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
		speed=50;
		numOfBombs = 3;
		width = 37;
		height = 73;
		hitbox = 	new Rectangle( (int) x 			, (int) y 			, width 	, height 	);
		killUP = 	new Rectangle( (int) x 			, (int) y-1			, width 	, 1			);
		killDOWN = 	new Rectangle( (int) x 			, (int) (y+height+1), width 	, 1			);
		killLEFT = 	new Rectangle( (int) x-1 		, (int) y 			, 1 		, height	);
		killRIGHT = new Rectangle( (int) (x+width+1), (int) y 			, 1 		, height	);
	}

	public boolean getCollision(Rectangle target) {
		if( (target.intersects(killUP)) && (dy < 0) ) {dy = 0;}
		if( (target.intersects(killDOWN)) && (dy > 0) ) {dy = 0;}
		if( (target.intersects(killLEFT)) && (dx < 0) ) {dx = 0;}
		if( (target.intersects(killRIGHT)) && (dx > 0) ) {dx = 0;}
		return target.intersects(this.hitbox);
	}
	public void homeMouse(){
		//calculate the unit vector of of Human to Desired Position;
		double deltaX = tryX - x;
		double deltaY = tryY - y;
		double xWard = deltaX / Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));
		double yWard = deltaY / Math.sqrt((deltaX*deltaX) + (deltaY*deltaY));
		
		//Scale unit vector to speed of Human
		dx = (int) (speed*xWard);
		dy = (int) (speed*yWard);
		
		if( Math.abs(deltaX) < Math.abs(xWard) ) {dx=0;}
		if( Math.abs(deltaY) < Math.abs(yWard) ) {dy=0;}
	}
	public void move(double ellapsedTime) {
		x += (int) dx*ellapsedTime;
		y += (int) dy*ellapsedTime;
		hitbox.setLocation( 	(int) x 			, (int) y				);
		killUP.setLocation( 	(int) x 			, (int) y-1				);
		killDOWN.setLocation( 	(int) x 			, (int) (y+height+1)	);
		killLEFT.setLocation( 	(int) x-1			, (int) y 				);
		killRIGHT.setLocation( 	(int) (x+width+1)	, (int) y 				);
		homeMouse();
	}
	public int getDirection() {
		// 0 - right
		// 1 - left
		// 2 - up
		// 3 - down
		// 4 - stationary **REQUIRES MODIFICATION TO "SimpleCanvas"
		if( (dx == 0) && (dy == 0) ) {
			return 4;
		}
		if(Math.abs(dx) > Math.abs(dy)) {
			if(dx >= 0) return 0;
			if(dx < 0) return 1;
		}
		if(Math.abs(dx) <= Math.abs(dy)) {
			if(dy >= 0) return 3;
			if(dy < 0) return 2;
		}
		//Should never be called
		return 4;	//Change to a stationary is possible
	}
}
