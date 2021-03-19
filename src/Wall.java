import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends ColorPicker {
	private double x;
	private double height;
	private double start;
	private double spd = 3;
	private ArrayList<Rectangle> graphic;
	private ArrayList<Double> yPos;

	public Wall(int difficulty) {
		super(difficulty);

		graphic = new ArrayList<Rectangle>();
		yPos = new ArrayList<Double>();
		setup();

	}
	
	private void setup() {
		graphic.clear();
		for (int i = 0; i < numOfColors; i++) {
			graphic.add(new Rectangle(25, 100));
		}
	}
	public void display() {

		for (int k = 0; k < graphic.size(); k++) {
			Rectangle r = graphic.get(k);
			r.setFill(chosenColors.get(k));
			r.setX(x);
			r.setY(yPos.get(k));
			r.setHeight(height);
		}
	}

	public void Update() {
		if(x < 0) {
			x += -spd;
		} else {
			x = start;
		}
	}

	// setters
	public void setHeights(Scene s) {
		height = s.getHeight()/numOfColors;
		yPos.clear();
		yPos.add(0.0);
		for(int i = 1; i < numOfColors - 1;i++) {
			yPos.add(height*i);
		}
		start = s.getWidth();
		for(int i = 0; i < yPos.size();i++) {
			System.out.printf("ypos: %f",yPos.get(i));;
		}
	}

	public void setSpd(double a) {
		spd = a;
	}

	public void setDifficulty(int a) {
		numOfColors = a;
		reSelect();
		setup();
	}
	
	//getters
	
	public ArrayList<Rectangle> getGraphic(){
		return graphic;
	}

}
