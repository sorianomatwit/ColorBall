
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends ColorPicker implements Activator{
	private boolean isFlashing = false;
	private double radius;
	// position
	private double x;
	private double y;
	// movement
	private double vy;
	// bounds of the screen
	private double maxY;
	int c;
	int gameDiff;
	// graphic
	Circle graphic = new Circle();
	double gradient = 1.0;
	double changeby = 0.01;
	// player lives
	private int lives = 3;
	boolean alive = true;
	private boolean active = false;
	
	// random number generator
	private Random rand = new Random();
	
	
	// constructor
	public Player(int difficulty) {
		super(difficulty);
		gameDiff = difficulty;
		this.radius = 20;
		this.x = 200;
		this.y = 100;
		vy = 50;

		graphic = new Circle(x, y, radius);
	}

	public void move(double dt) {
		// update x and y (solution to a differential equation)
		y = y + vy * dt;
		// top/bottom walls
		if (y + radius > maxY || y - radius <= 0) {
			if (y + radius > maxY)
				y = maxY - radius;
			if (y - radius <= 0)
				y = radius;
			// do the bounce
			vy = -vy;

		}
		updateGraphic();

		// life stuff
		if (lives <= 0) {
			alive = false;
		} else {
			alive = true;
		}
	}

	private void updateGraphic() {
		graphic.setStroke(Color.BLACK);
		graphic.setCenterX(x);
		graphic.setCenterY(y);
		graphic.setOpacity(gradient);
		if(!active) {
			graphic.setVisible(true);
		}
	}
	/**
	 * 
	 * @param spd cannot be 0 or negative
	 */
	public void flashing(int spd) {
		isFlashing = true;
		if(spd <= 0) {
			spd = 1;
		}
		for (int i = 0; i <= spd; i++) {
			if (gradient <= 0.3 || gradient >= 1.0) {
				changeby = -changeby;
			}
			gradient += changeby;
			
		}
		//gradient =1;
	}
	
	// getters
	public boolean isFlashing() {
		
		return isFlashing;
	}
	public void stopFlashing() {
		isFlashing = false;
	}
	public double getX() {
		return x;
	}

	public double getY() {
		return y;

	}

	public double getRadius() {
		return radius;
	}

	public int getColor() {
		return chosenColors.indexOf(graphic.getFill());
	}

	public Circle getGraphic() {
		return graphic;
	}

	public int getLives() {
		return lives;
	}

	public boolean isAlive() {
		return alive;
	}
	
	public boolean isActive() {
		return active;
	}
	
	// setters
	public void setActive(boolean activate) {
		active = activate;
	}
	
	public void setColor(Wall w) {
		c = (int) rand.nextInt(gameDiff);
		copyList(w.getchosenColors());
		graphic.setFill(chosenColors.get(c));
		gradient = 1;
	}

	public void setBoundary(double y) {
		maxY = y;
	}
	
	public void setLives(int l){
		lives = l;
	}
	
	// collision action;
	public void gotHit() {
		lives--;
	}

	// controller whetehr the player wants to move up or down or just change
	// directions
	public void VyDown() {
		vy = Math.abs(vy);
	}

	public void VyUp() {
		vy = -Math.abs(vy);
	}

	public void flipVy() {
		vy = -vy;
	}
	
	// player abilities
	public void invisible() {
		graphic.setVisible(false);
		active = true;
	}

	@Override
	public void reset(int a, Scene s) {
		gameDiff = a;
		setY(s);
		move(0);
		//reSelect(a);
	}

	public void setY(Scene s) {
		y = s.getHeight()/2;
		graphic.setCenterY(y);
		
	}
}
