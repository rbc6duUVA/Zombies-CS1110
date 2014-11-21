import java.awt.Rectangle;



public class Zombie {
	private double x;
	private double y;
	private double tryX;
	private double tryY;
	private double dx;
	private double dy;
	private double speed;
	Rectangle hitbox;
	private int width;
	private int height;
	Rectangle killUP, killDOWN, killLEFT, killRIGHT;
	
	public void setTry(double tX, double tY) {
		tryX = tX;
		tryY = tY;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
	public Rectangle getCollisionBox() {
		Rectangle temp = new Rectangle(hitbox);
		temp.setSize((int) (hitbox.getWidth()*2.5) , (int) (hitbox.getHeight()*1.7));
		return temp;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}

	public Zombie(int XX, int YY) {
		this.x=XX;
		this.y=YY;
		tryX=0;
		tryY=0;
		dx=1;
		dy=1;
		speed=20;
		width = 24;
		height = 44;
		hitbox = 	new Rectangle( (int) x+(width/2)		, (int) y+(height/2)	, width 			, height 			);
		killUP = 	new Rectangle( (int) x 					, (int) y-2				, (int) 1.7*width 	, 2					);
		killDOWN = 	new Rectangle( (int) x 					, (int) (y+2*height+2)	, (int) 1.7*width 	, 2					);
		killLEFT = 	new Rectangle( (int) x-2 				, (int) y 				, 2 				, (int) 2*height	);
		killRIGHT = new Rectangle( (int) (x+1.7*width+2)	, (int) y 				, 2 				, (int) 2*height	);
	}

	public boolean getObstacleCollision(Rectangle target) {
		if( (target.intersects(killUP)) && (dy < 0) ) {dy = 0;}
		if( (target.intersects(killDOWN)) && (dy > 0) ) {dy = 0;}
		if( (target.intersects(killLEFT)) && (dx < 0) ) {dx = 0;}
		if( (target.intersects(killRIGHT)) && (dx > 0) ) {dx = 0;}
		return target.intersects(this.hitbox);
	}
	public boolean playerCollision(Rectangle target) {
		return target.intersects(this.hitbox);
	}
	public void homePlayer(){
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
		hitbox.setLocation( 	(int) x 				, (int) y					);
		killUP.setLocation( 	(int) x 				, (int) y-1					);
		killDOWN.setLocation( 	(int) x 				, (int) (y+2*height+1)		);
		killLEFT.setLocation( 	(int) x-1				, (int) y 					);
		killRIGHT.setLocation( 	(int) (x+1.7*width+1)	, (int) y 					);
		homePlayer();
	}
	public int getDirection() {
		// 0 - right
		// 1 - left
		// 2 - up
		// 3 - down
		if(Math.abs(dx) > Math.abs(dy)) {
			if(dx >= 0) return 0;
			if(dx < 0) return 1;
		}
		if(Math.abs(dx) <= Math.abs(dy)) {
			if(dy >= 0) return 3;
			if(dy < 0) return 2;
		}
		//Should never be called
		return 0;	//Change to a stationary is possible
	}
}
