import java.awt.Rectangle;

/**
 * Human.java
 * 
 * The player's character. This class should have fields that can represent the
 * players' current location, the location to which it is trying to go, a
 * relative speed, and a number of bombs, and a Rectangle representing the
 * player's hitbox. Other fields for the size of the player and the hitbox may
 * be useful, along with frame information if you do animation. You should
 * create methods for the following: 1. checking to see if the player's hitbox
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
	private int numOfBombs;
	Rectangle hitbox;
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public Human() {
		x=300;
		y=300;
		tryX=x;
		tryY=y;
		dx=0;
		dy=0;
		numOfBombs = 3;
		hitbox = new Rectangle( (int) x , (int) y , 38 , 74 );

				
	}
	

}