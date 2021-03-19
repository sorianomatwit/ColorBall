import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends ColorPicker {
	private double x;
	private double offset;
	private int dist = 0;
	private double width = 25;
	private double height;
	private double start;
	private double spd;
	private int maxdifficulty = 7;
	
	private ArrayList<Rectangle> graphic;
	private ArrayList<Double> yPos;

	public Wall(int difficulty,int offset) {
		super(difficulty);
		dist = offset;
		graphic = new ArrayList<Rectangle>();
		yPos = new ArrayList<Double>();
		setup();

	}

	private void setup() {
		for (int i = 0; i < maxdifficulty; i++) {
			graphic.add(new Rectangle(25, 100));
		}
	}

	public void display() {
		
		for (int k = 0; k < graphic.size(); k++) {
			Rectangle r = graphic.get(k);
			if (k < numOfColors) {
				r.setFill(chosenColors.get(k));
				r.setX(x+offset);
				r.setY(yPos.get(k));
				r.setHeight(height);
			} else {
				r.setX(-100);
				r.setY(-100);
			}
		}
	}

	public void Update() {
		if (x+offset > -width) {
			x += -spd;
		} else {
			x = start+offset;
			reSelect();
		}
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
		offset = (start/3)*dist;
//		for(int i = 0; i < yPos.size();i++) {
//			System.out.printf("ypos: %f%nypos size: %d%n",yPos.get(i),yPos.size());;
//		}
	}

	public void setSpd(double a) {
		spd = a;
	}

	public void setDifficulty(int a) {
		numOfColors = a;
		for (int i = 0; i < a - graphic.size(); i++) {
			graphic.add(new Rectangle(25, 100));
		}
		reSelect();
		for (int i = 0; i < chosenColors.size(); i++) {
			System.out.printf("Color: " + chosenColors.get(i) + "%nchosenColors size: %d%n", chosenColors.size());
			;
		}
	}

	// getters

	public ArrayList<Rectangle> getGraphic() {
		return graphic;
	}

}
