import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

public class Wall extends ColorPicker implements Activator{
	private double x;
	private double width = 25;
	private double height;
	private double hspd;
	private double vspd = 4;
	private ArrayList<Double> yPos;
	
	private double start;
	
	private int maxdifficulty = 7;
	private double threshold = -width;
	private ArrayList<Rectangle> graphic;
	
	private Scene game;
	
	private boolean active = false;
	public Wall(int difficulty) {
		super(difficulty);
		graphic = new ArrayList<Rectangle>();
		yPos = new ArrayList<Double>();
		setup();

	}

	private void setup() {
		graphic.clear();
		for (int i = 0; i < maxdifficulty; i++) {
			graphic.add(new Rectangle(25, 100));
		}
	}

	public void display() {
		for (int k = 0; k < graphic.size(); k++) {
			Rectangle r = graphic.get(k);
			if (k < numOfColors) {

				r.setFill(chosenColors.get(k));
				r.setX(x);
				r.setY(yPos.get(k));
				r.setHeight(height);
				if(k == 0|| k== 1) {
					r.setHeight(height);
				}
			} else {
				r.setX(-100);
				r.setY(-100);
			}
		}
	}

	public void Update(Player p) {
		if (x > -(width)) {
			//x += -hspd;
			bounce();
		} else {
			x = start;
			reSelect(numOfColors);
			p.reSelect(chosenColors.size());
		}

	}
	// Check collision

	public boolean collide(Player p) {

		for (int k = 0; k < numOfColors; k++) {
			Rectangle r = graphic.get(k);
			// check if hitting a rectangle
			if (x < p.getX() + p.getRadius() && x > p.getX() - p.getRadius() && yPos.get(k) < p.getY() + p.getRadius()
					&& yPos.get(k) + height > p.getY() - p.getRadius()) {
				// checks if the they are the same color
				if (p.getColor() != chosenColors.indexOf(r.getFill())) {
					return true;
				}
			}
		}
		return false;
	}

	// setters
	public void setHeights(Scene s) {
		height = s.getHeight() / numOfColors;
		yPos.clear();
		yPos.add(0.0);
		for (int i = 1; i < numOfColors; i++) {
			yPos.add(height * i);
		}
		start = s.getWidth();
		game = s;
		// setup();
//		for(int i = 0; i < yPos.size();i++) {
//			System.out.printf("ypos: %f%nypos size: %d%n",yPos.get(i),yPos.size());;
//		}
	}

	public void setSpd(double a) {
		hspd = a;
	}

	public void setDifficulty(int a) {
		numOfColors = a;
		for (int i = 0; i < a - graphic.size(); i++) {
			graphic.add(new Rectangle(25, 100));
		}
		reSelect(numOfColors);
		for (int i = 0; i < chosenColors.size(); i++) {
			System.out.printf("Color: " + chosenColors.get(i) + "%nchosenColors size: %d%n", chosenColors.size());
			;
		}
	}

	public void setX(double a) {
		x = a;
	}
	// getters

	public ArrayList<Rectangle> getGraphic() {
		return graphic;
	}

	public double getX() {
		return x;
	}

	public double getThreshold() {
		return threshold;
	}
	//abilties

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean activate) {
		active = activate;	
	}
	// wall moving up and down
	public void bounce() {
		active = true;
		double min_boundary = -40;
		double max_boundary = game.getHeight()+40;
		for(int d = 0;d < yPos.size();d++) {
			double temp = yPos.get(d) + vspd;
			System.out.printf("ypos(%d): %.2f temp: %.2f%n",d,yPos.get(d),temp);
			yPos.set(d, temp);
		}
		System.out.println(max_boundary);;
		if(yPos.get(0) < min_boundary || yPos.get(yPos.size() -1) + height > max_boundary) {
			vspd = -vspd;
		}
	}

}
