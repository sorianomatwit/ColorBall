import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends ColorPicker {
	private double x;
	private double width = 25;
	private double height;
	private double start;
	private double spd = 3;
	private ArrayList<Rectangle> graphic;
	private ArrayList<Double> yPos;

	public Wall(int difficulty) {
		super(difficulty);

		graphic = new ArrayList<Rectangle>();
		yPos = new ArrayList<Double>();
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
		if(x > -25) {
			x += -spd;
		} else {
			x = start;
			reSelect();
		}
	}

	// setters
	public void setHeights(Scene s) {
		height = s.getHeight()/numOfColors;
		yPos.clear();
		yPos.add(0.0);
		for(int i = 1; i < numOfColors;i++) {
			yPos.add(height*i);
		}
		start = s.getWidth();
//		for(int i = 0; i < yPos.size();i++) {
//			System.out.printf("ypos: %f%nypos size: %d%n",yPos.get(i),yPos.size());;
//		}
	}

	public void setSpd(double a) {
		spd = a;
	}

	public void setDifficulty(int a) {
		numOfColors = a;
		for(int i = 0; i < a - graphic.size();i++) {
			graphic.add(new Rectangle(25,100));
		}
		reSelect();
		for(int i = 0; i < chosenColors.size();i++) {
			System.out.printf("Color: "+chosenColors.get(i)+"%nchosenColors size: %d%n",chosenColors.size());;
		}
	}
	
	//getters
	
	public ArrayList<Rectangle> getGraphic(){
		return graphic;
	}

}
