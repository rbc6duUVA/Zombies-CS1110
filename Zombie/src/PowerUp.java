import java.awt.Graphics2D;
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
	
	public void drawPowerUp(SimpleCanvas canvas, Graphics2D g) {
		age++;
		switch (id) {
			case 0: 
				phase++;
				if(phase>=4) {phase=0;}
				if(id==0) {
					canvas.drawPowerUp_Bomb(g, x, y, phase);
				} break;
			
			case 1: 
				phase++;
				if(phase>=4) {phase=0;}
				if(id==0) {
					canvas.drawPowerUp_Bomb(g, x, y, phase);
				} break;
				
			default:
				phase++;
				break;
		}
		
	}
	
	public boolean isAlive() {
		if(age<=5000) return true;
		if(age>5000) return false;
		return true;
	}
}
