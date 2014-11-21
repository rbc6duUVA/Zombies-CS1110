// Ben Canty (rbc6du) and Hunter Burrell (hxb5te)

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * SimpleCanvas.java
 * 
 * The SimpleCanvas class contains the drawing methods needed to manage the
 * ZombieSurvival. Many of the methods in this class will not be called by the
 * programmer - instead, they will be automatically called when something
 * occurs. For instance, mouseClicked is called when someone clicks on the
 * ZombieSurvival.
 * 
 * @author Jason Lawrence and Mark Sherriff
 * 
 */
@SuppressWarnings("serial")
public class SimpleCanvas extends JPanel implements MouseListener, MouseMotionListener {

	// width and height of the window
	private int width;
	private int height;

	// lastTime that the screen was refreshed
	private long lastTime;

	// a link back to the ZombieSurvival for updating it
	private ZombieSurvival simulator;

	// BufferedImages to handle the sprite graphics
	private BufferedImage[][] zombieSprites;
	private BufferedImage[][] humanSprites;
	private BufferedImage boomSprite;
	private BufferedImage[] powerUp_Bomb;
	private BufferedImage[] powerUp_Speed;
	private BufferedImage[] powerUp_Invincible;

	/**
	 * Constructor for the SimpleCanvas
	 * 
	 * @param width_
	 *            width of the window
	 * @param height_
	 *            height of the window
	 * @param simulator_
	 *            link back to the ZombieSurvival
	 */
	public SimpleCanvas(int width_, int height_, ZombieSurvival simulator_) {
		width = width_;
		height = height_;
		simulator = simulator_;
		lastTime = -1L;

		humanSprites = loadHumanSprites("sprite.png");
		zombieSprites = loadZombieSprites("zombie.png");
		powerUp_Bomb = loadPowerUp_BombSprites("grenade.png");
		powerUp_Speed = loadPowerUp_Speed("Shoes.png");
		powerUp_Invincible = loadPowerUp_Invincible("Potion.png");
		try {
			boomSprite = ImageIO.read(new File("boom.png"));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}

	}
	
	public BufferedImage[] loadPowerUp_Invincible(String filename) {
		BufferedImage[] spriteArray = new BufferedImage[5];
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = ImageIO.read(new File(filename));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}
		
		spriteArray[0] = spriteSheet.getSubimage(0,0,31,25);
		spriteArray[1] = spriteSheet.getSubimage(1*31,0,31,25);
		spriteArray[2] = spriteSheet.getSubimage(2*31,0,31,25);
		spriteArray[3] = spriteSheet.getSubimage(3*31,0,31,25);
		spriteArray[4] = spriteSheet.getSubimage(4*31,0,31,25);
		
		return spriteArray;
	}
	
	public BufferedImage[] loadPowerUp_Speed(String filename) {
		BufferedImage[] spriteArray = new BufferedImage[6];
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = ImageIO.read(new File(filename));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}
		
		spriteArray[0] = spriteSheet.getSubimage(0,0,18,18);
		spriteArray[1] = spriteSheet.getSubimage(18,0,18,18);
		spriteArray[2] = spriteSheet.getSubimage(2*18,0,18,18);
		spriteArray[3] = spriteSheet.getSubimage(3*18,0,18,18);
		spriteArray[4] = spriteSheet.getSubimage(4*18,0,18,18);
		spriteArray[5] = spriteSheet.getSubimage(5*18,0,18,18);
		
		return spriteArray;
	}
	
	public BufferedImage[] loadPowerUp_BombSprites(String filename) {
		
		BufferedImage[] spriteArray = new BufferedImage[4];
		BufferedImage spriteSheet = null;
		
		try {
			spriteSheet = ImageIO.read(new File(filename));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}
		
		spriteArray[0] = spriteSheet.getSubimage(0, 0, 22, 23);
		spriteArray[1] = spriteSheet.getSubimage(22, 0, 22, 23);
		spriteArray[2] = spriteSheet.getSubimage(44, 0, 22, 23);
		spriteArray[3] = spriteSheet.getSubimage(66, 0, 22, 23);
		
		return spriteArray;
	}
	
	/**
	 * Loads the images needed to draw the human character. Add code here to do
	 * animation or to use different sprites.
	 * 
	 * @param filename
	 *            name of file to load
	 * @return 2D array of sprites
	 */
	public BufferedImage[][] loadHumanSprites(String filename) {

		BufferedImage[][] spriteArray = new BufferedImage[10][8];
		BufferedImage spriteSheet = null;

		try {
			spriteSheet = ImageIO.read(new File(filename));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}

		// load right facing // updated
		spriteArray[0][0] = spriteSheet.getSubimage(5, 80, 38, 74);
		spriteArray[0][1] = spriteSheet.getSubimage(53, 80, 92-53, 74);
		spriteArray[0][2] = spriteSheet.getSubimage(103, 80, 142-103, 74);
		spriteArray[0][3] = spriteSheet.getSubimage(152, 77, 190-152, 74);
		spriteArray[0][4] = spriteSheet.getSubimage(195, 77, 234-195, 74);
		spriteArray[0][5] = spriteSheet.getSubimage(247, 80, 286-247, 74);
		spriteArray[0][6] = spriteSheet.getSubimage(293, 80, 332-293, 74);
		spriteArray[0][7] = spriteSheet.getSubimage(341, 80, 380-341, 74);
		
		// load left facing // updated
		spriteArray[1][0] = getFlippedImage(spriteSheet.getSubimage(5, 79, 38, 74));
		spriteArray[1][1] = getFlippedImage(spriteSheet.getSubimage(53, 79, 92-53, 74));
		spriteArray[1][2] = getFlippedImage(spriteSheet.getSubimage(103, 79, 142-103, 74));
		spriteArray[1][3] = getFlippedImage(spriteSheet.getSubimage(151, 77, 190-152, 74));
		spriteArray[1][4] = getFlippedImage(spriteSheet.getSubimage(195, 77, 234-195, 74));
		spriteArray[1][5] = getFlippedImage(spriteSheet.getSubimage(247, 79, 286-247, 74));
		spriteArray[1][6] = getFlippedImage(spriteSheet.getSubimage(293, 79, 332-293, 74));
		spriteArray[1][7] = getFlippedImage(spriteSheet.getSubimage(341, 79, 380-341, 74));
		
		// load up facing // updated
		spriteArray[2][0] = spriteSheet.getSubimage(7, 237, 39, 75);
		spriteArray[2][1] = spriteSheet.getSubimage(55, 237, 39, 75);
		spriteArray[2][2] = spriteSheet.getSubimage(101, 237, 39, 75);
		spriteArray[2][3] = spriteSheet.getSubimage(151, 237, 39, 75);
		spriteArray[2][4] = spriteSheet.getSubimage(197, 237, 39, 75);
		spriteArray[2][5] = spriteSheet.getSubimage(245, 237, 39, 75);
		spriteArray[2][6] = spriteSheet.getSubimage(291, 237, 39, 75);
		spriteArray[2][7] = spriteSheet.getSubimage(341, 237, 39, 75);
		
		// load down facing // updated
		spriteArray[3][0] = spriteSheet.getSubimage(7, 159, 39, 75);
		spriteArray[3][1] = spriteSheet.getSubimage(55, 159, 39, 75);
		spriteArray[3][2] = spriteSheet.getSubimage(101, 159, 39, 75);
		spriteArray[3][3] = spriteSheet.getSubimage(151, 161, 39, 75);
		spriteArray[3][4] = spriteSheet.getSubimage(197, 159, 39, 75);
		spriteArray[3][5] = spriteSheet.getSubimage(245, 159, 39, 75);
		spriteArray[3][6] = spriteSheet.getSubimage(291, 159, 39, 75);
		spriteArray[3][7] = spriteSheet.getSubimage(341, 161, 39, 75);
		
		//Stationary
		spriteArray[4][0] = spriteSheet.getSubimage(7, 159, 39, 75);
		spriteArray[4][1] = spriteSheet.getSubimage(7, 159, 39, 75);
		spriteArray[4][2] = spriteSheet.getSubimage(7, 159, 39, 75);
		spriteArray[4][3] = spriteSheet.getSubimage(7, 159, 39, 75);
		spriteArray[4][4] = spriteSheet.getSubimage(7, 159, 39, 75);
		spriteArray[4][5] = spriteSheet.getSubimage(7, 159, 39, 75);
		spriteArray[4][6] = spriteSheet.getSubimage(7, 159, 39, 75);
		spriteArray[4][7] = spriteSheet.getSubimage(7, 159, 39, 75);

		//Shift down +234 to get from 80 to 314, GhostGirl is same plate, just below on the spritesheet.
		
		// load right facing // updated
		spriteArray[5][0] = spriteSheet.getSubimage(5, 80+234, 38, 74);
		spriteArray[5][1] = spriteSheet.getSubimage(53, 80+234, 92-53, 74);
		spriteArray[5][2] = spriteSheet.getSubimage(103, 80+234, 142-103, 74);
		spriteArray[5][3] = spriteSheet.getSubimage(152, 77+234, 190-152, 74);
		spriteArray[5][4] = spriteSheet.getSubimage(195, 77+234, 234-195, 74);
		spriteArray[5][5] = spriteSheet.getSubimage(247, 80+234, 286-247, 74);
		spriteArray[5][6] = spriteSheet.getSubimage(293, 80+234, 332-293, 74);
		spriteArray[5][7] = spriteSheet.getSubimage(341, 80+234, 380-341, 74);
				
		// load left facing // updated
		spriteArray[6][0] = getFlippedImage(spriteSheet.getSubimage(5, 79+234, 38, 74));
		spriteArray[6][1] = getFlippedImage(spriteSheet.getSubimage(53, 79+234, 92-53, 74));
		spriteArray[6][2] = getFlippedImage(spriteSheet.getSubimage(103, 79+234, 142-103, 74));
		spriteArray[6][3] = getFlippedImage(spriteSheet.getSubimage(151, 77+234, 190-152, 74));
		spriteArray[6][4] = getFlippedImage(spriteSheet.getSubimage(195, 77+234, 234-195, 74));
		spriteArray[6][5] = getFlippedImage(spriteSheet.getSubimage(247, 79+234, 286-247, 74));
		spriteArray[6][6] = getFlippedImage(spriteSheet.getSubimage(293, 79+234, 332-293, 74));
		spriteArray[6][7] = getFlippedImage(spriteSheet.getSubimage(341, 79+234, 380-341, 74));
				
		// load up facing // updated
		spriteArray[7][0] = spriteSheet.getSubimage(7, 237+234, 39, 75);
		spriteArray[7][1] = spriteSheet.getSubimage(55, 237+234, 39, 75);
		spriteArray[7][2] = spriteSheet.getSubimage(101, 237+234, 39, 75);
		spriteArray[7][3] = spriteSheet.getSubimage(151, 237+234, 39, 75);
		spriteArray[7][4] = spriteSheet.getSubimage(197, 237+234, 39, 75);
		spriteArray[7][5] = spriteSheet.getSubimage(245, 237+234, 39, 75);
		spriteArray[7][6] = spriteSheet.getSubimage(291, 237+234, 39, 75);
		spriteArray[7][7] = spriteSheet.getSubimage(341, 237+234, 39, 75);
				
		// load down facing // updated
		spriteArray[8][0] = spriteSheet.getSubimage(7, 159+234, 39, 75);
		spriteArray[8][1] = spriteSheet.getSubimage(55, 159+234, 39, 75);
		spriteArray[8][2] = spriteSheet.getSubimage(101, 159+234, 39, 75);
		spriteArray[8][3] = spriteSheet.getSubimage(151, 161+234, 39, 75);
		spriteArray[8][4] = spriteSheet.getSubimage(197, 159+234, 39, 75);
		spriteArray[8][5] = spriteSheet.getSubimage(245, 159+234, 39, 75);
		spriteArray[8][6] = spriteSheet.getSubimage(291, 159+234, 39, 75);
		spriteArray[8][7] = spriteSheet.getSubimage(341, 161+234, 39, 75);
				
		//Stationary
		spriteArray[9][0] = spriteSheet.getSubimage(7, 159+234, 39, 75);
		spriteArray[9][1] = spriteSheet.getSubimage(7, 159+234, 39, 75);
		spriteArray[9][2] = spriteSheet.getSubimage(7, 159+234, 39, 75);
		spriteArray[9][3] = spriteSheet.getSubimage(7, 159+234, 39, 75);
		spriteArray[9][4] = spriteSheet.getSubimage(7, 159+234, 39, 75);
		spriteArray[9][5] = spriteSheet.getSubimage(7, 159+234, 39, 75);
		spriteArray[9][6] = spriteSheet.getSubimage(7, 159+234, 39, 75);
		spriteArray[9][7] = spriteSheet.getSubimage(7, 159+234, 39, 75);
	
		
		
		return spriteArray;
	}

	/**
	 * Loads the images needed to draw the zombie character. Add code here to do
	 * animation or to use different sprites.
	 * 
	 * @param filename
	 *            name of file to load
	 * @return 2D array of sprites
	 */
	public BufferedImage[][] loadZombieSprites(String filename) {

		BufferedImage[][] spriteArray = new BufferedImage[4][3];
		BufferedImage spriteSheet = null;

		try {
			spriteSheet = ImageIO.read(new File(filename));
		} catch (Exception e) {
			System.err.println("Cannot load images!");
		}

		// load right facing // updated
		spriteArray[0][0] = spriteSheet.getSubimage(18, 111, 61-18, 192-111);
		spriteArray[0][1] = spriteSheet.getSubimage(98, 108, 133-98, 189-108);
		spriteArray[0][2] = spriteSheet.getSubimage(165, 111, 208-165, 192-111);

		// load left facing // updated
		spriteArray[1][0] = getFlippedImage(spriteSheet.getSubimage(18, 111, 61-18, 192-111));
		spriteArray[1][1] = getFlippedImage(spriteSheet.getSubimage(98, 108, 133-98, 189-108));
		spriteArray[1][2] = getFlippedImage(spriteSheet.getSubimage(165, 111, 208-165, 192-111));
		
		// load up facing // updated
		spriteArray[2][0] = spriteSheet.getSubimage(16, 16, 59-16, 94-16);
		spriteArray[2][1] = spriteSheet.getSubimage(89, 13, 133-89, 91-13);
		spriteArray[2][2] = spriteSheet.getSubimage(163, 16, 207-163, 94-16);

		// load down facing // 
		spriteArray[3][0] = spriteSheet.getSubimage(16, 212, 59-16, 290-212);
		spriteArray[3][1] = spriteSheet.getSubimage(89, 209, 133-89, 287-209);
		spriteArray[3][2] = spriteSheet.getSubimage(163, 212, 207-163, 290-212);
		
		return spriteArray;
	}

	/**
	 * Called to start the game
	 */
	public void setupAndDisplay() {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JScrollPane(this));
		f.setSize(width, height);
		f.setLocation(100, 100);
		f.setVisible(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	/**
	 * This method is NEVER called by the programmer. This method is called
	 * whenever the screen refreshes. Consequently, it calls the draw() method
	 * in ZombieSurvival, telling it to update its components.
	 */
	protected void paintComponent(Graphics g) {
		boolean first = (lastTime == -1L);
		long elapsedTime = System.nanoTime() - lastTime;
		lastTime = System.nanoTime();
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.white);
		if(simulator.draw((Graphics2D) g, (first ? 0.0f : (float) elapsedTime / 1e9f))) {
			repaint();
		} else {
			return;
		}
		
	}

	/**
	 * drawDot does just what it says - it puts a dot on the screen.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param x
	 *            x coordinate of dot
	 * @param y
	 *            y coordinate of dot
	 * @param color
	 *            Color instance of color of dot
	 */
	public void drawDot(Graphics2D g, double x, double y, Color color) {
		g.setColor(color);
		g.fillOval((int) (x + 0.5f), (int) (y + 0.5f), 8, 8);
	}
	
	
	
	/**
	 * Given an obstacle Rectangle, this will draw it to the screen
	 * as a white rectangle.  You may change this or add to this method
	 * if you want to use other colors or images.
	 * @param g Graphics engine passed from paintComponent() into ZombieSurvival.draw()
	 * @param o obstacle Rectangle
	 */
	public void drawObstacle(Graphics2D g, Rectangle o) {
		g.setColor(Color.white);
		g.fill(o);
	}

	/**
	 * This method loads the proper graphic from the BufferedImage 2D array and
	 * draws it on the screen. Change the code here to make the character point
	 * in the correct direction.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param z
	 *            Zombie to draw
	 */
	public void drawZombie(Graphics2D g, Zombie z, int direction, int phase) {
		g.drawImage(zombieSprites[direction][phase], (int) z.getX(), (int) z.getY(), null);
	}

	/**
	 * This method loads the proper graphic from the BufferedImage 2D array and
	 * draws it on the screen. Change the code here to make the character point
	 * in the correct direction.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param h
	 *            Human to draw
	 */
	public void drawHuman(Graphics2D g, Human h, int direction, int phase) {
		g.drawImage(humanSprites[direction][phase], (int) h.getX(), (int) h.getY(), null);
	}

	public void drawPowerUp(Graphics2D g, int x, int y, int id, int phase) {
		switch (id) {
		case 0: 
			g.drawImage(powerUp_Bomb[phase], x, y, null);
			break;
		case 1:
			g.drawImage(powerUp_Speed[phase], x, y, null);
			break;
		case 2:
			g.drawImage(powerUp_Invincible[phase], x, y, null);
			break;
		default:
			System.out.println("Error drawing power up");
			break;
		}
		
	}
	
	/**
	 * This method should draw the explosion graphic on the screen on top of the
	 * Human character.
	 * 
	 * @param g
	 *            Graphics engine passed from paintComponent() into
	 *            SurivalField.draw()
	 * @param x
	 *            x coordinate to draw
	 * @param y
	 *            y coordinate to draw
	 */
	public void drawBoom(Graphics2D g, Human h) {
		g.drawImage(boomSprite, (int) (h.getX() - 50), (int) (h.getY() - 50), null);
	}

	/**
	 * Flips a BufferedImage. If you need it.
	 * 
	 * @param bi
	 *            image to flip
	 * @return flipped image
	 */
	public BufferedImage getFlippedImage(BufferedImage bi) {
		BufferedImage flipped = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		AffineTransform tran = AffineTransform.getTranslateInstance(bi.getWidth(), 0);
		AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
		tran.concatenate(flip);

		Graphics2D g = flipped.createGraphics();
		g.setTransform(tran);
		g.drawImage(bi, 0, 0, null);
		g.dispose();

		return flipped;
	}

	/**
	 * Whenever the mouse is moved on the ZombieSurvival, this method gets
	 * called.
	 */
	public void mouseMoved(MouseEvent e) {
		simulator.mouseAction((float) e.getX(), (float) e.getY(), -1);
	}

	/**
	 * Whenever the mouse is clicked on the ZombieSurvival, this method gets
	 * called.
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Whenever the mouse enters the ZombieSurvival, this method gets called.
	 */
	public void mouseEntered(MouseEvent e) {
		
	}

	/**
	 * Whenever the mouse leaves the ZombieSurvival, this method gets called.
	 */
	public void mouseExited(MouseEvent e) {
		simulator.mouseAction((float) e.getX(), (float) e.getY(), e.getButton());
	}

	/**
	 * Whenever the mouse is pressed (yes, it's just barely different than
	 * clicked) on the ZombieSurvival, this method gets called.
	 */
	public void mousePressed(MouseEvent e) {
		simulator.mouseAction((float) e.getX(), (float) e.getY(), e.getButton());
	}

	/**
	 * Whenever the mouse press is released on the ZombieSurvival, this method
	 * gets called.
	 */
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * Whenever the mouse clicked and dragged on the ZombieSurvival, this method
	 * gets called.
	 */
	public void mouseDragged(MouseEvent e) {
		simulator.mouseAction((float) e.getX(), (float) e.getY(), e.getButton());
	}

}