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
	public void setTryX(double tX, double tY) {
		tryX = tX;
		tryY = tY;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public Human() {
		x=300;
		y=300;
		tryX=0;
		tryY=0;
		dx=0;
		dy=0;
		speed=20;
		numOfBombs = 3;
		width = 38;
		height = 74;
		hitbox = new Rectangle( (int) x , (int) y , width , height );	
	}
	/*
	2. movement (normal movement
 	 * and what to do if there is a collision) 3. constructors 4. getters/setters 5.
 	 * bomb counting 6. anything else you may need.
	 */
	public boolean getCollision(Rectangle target) {
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
		
		
		
}
