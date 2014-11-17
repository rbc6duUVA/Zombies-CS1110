import java.awt.Rectangle;


public class PowerUp {

	private int x;
	private int y;
	private int id;
	private int age;
	private int phase;
	private Rectangle hitbox;
	
	public PowerUp(int X, int Y, int ID) {
		this.x=X;
		this.y=Y;
		this.id=ID;
		phase=0;
		age=0;
		hitbox = new Rectangle(x,y,25,25);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getID() {
		return id;
	}
	public int getPhase() {
		return phase;
	}
	public int getAge() {
		return age;
	}
	public boolean isAlive() {
		age++;
		if(age<=15000) return true;
		if(age>15000) return false;
		return true;
	}
}
