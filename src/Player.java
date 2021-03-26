
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends ColorPicker implements Abilities{
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

	// random number generator
	private Random rand = new Random();

	// constructor
	public Player(int difficulty) {
		super(difficulty);
		gameDiff = difficulty;
		this.radius = 20;
		this.x = 100;
		this.y = 100;

		vy = 50;

		graphic = new Circle(x, y, radius);
	}

	

	public void move(double dt) {
		// update x and y (solution to a differential equation)
		y = y + vy * dt;
		// top/bottom walls
		if (y + radius > maxY || y - radius <= 0) {
			if(y + radius > maxY) y = maxY -  radius;
			if(y - radius <= 0) y = radius;
			// do the bounce
			vy = -vy;

		}
		updateGraphic();
	}

	private void updateGraphic() {
		graphic.setCenterX(x);
		graphic.setCenterY(y);
		
	}

	// getters
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
	
	//setters
	public void setColor(Wall w) {
		
		c = (int) rand.nextInt(gameDiff);
		chosenColors = w.getchosenColors();
		graphic.setFill(w.getchosenColors().get(c));
	}

	public void setBoundary(double y) {
		maxY = y;

	}
	
	//controller whetehr the player wants to move up or down or just change directions
	public void VyDown() {
		vy = Math.abs(vy);
	}
	public void VyUp() {
		vy = -Math.abs(vy);
	}
	public void flipVy() {
		vy = -vy;
	}


	// abbilities
	@Override
	public void invisibilty() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void inverseControls() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void colorSwitch() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void change() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void invincibilty() {
		// TODO Auto-generated method stub
		
	}
}
