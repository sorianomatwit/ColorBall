
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player extends ColorPicker {
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

	public Circle getGraphic() {

		return graphic;

	}

	public void setColor(Wall w) {
		c = (int) rand.nextInt(gameDiff);
		chosenColors = w.getchosenColors();
		graphic.setFill(w.getchosenColors().get(c));
	}

	public void setBoundary(double y) {
		maxY = y;

	}

	public void move(double dt) {
		// update x and y (solution to a differential equation)
		y = y + vy * dt;
		// top/bottom walls
		if (y + radius > maxY || y - radius <= 0) {
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
		return c;
	}
}
