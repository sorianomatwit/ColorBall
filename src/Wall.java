import java.util.ArrayList;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

public class Wall extends ColorPicker{
	
	private int difficulty = 1;
	private double x;
	private double hsp = -3;
	private double heights;
	private double maxHeight;
	ArrayList<Rectangle> area = new ArrayList<Rectangle>();
	
	
	public Wall() {
		super(3);
	}
	
	public int getDif() {
		return difficulty;
	}
	
	public void CreateWall(Scene room) {
		maxHeight = room.getHeight();
		heights = maxHeight/numOfColors;// height of each rectangle 
		area.clear();
		x = room.getWidth();
		
		for(int i = 0; i < numOfColors;i++) {
			area.add(new Rectangle());
		}
	}
	
	public void showWall() {
		x += hsp;
		for(int i = 0; i < area.size();i++) {
			Rectangle r = area.get(i);
			r.setX(x);
			r.setY((i* heights) - maxHeight);
			r.setFill(chosenColors.get(i));
			r.setHeight(heights);
			r.setWidth(25);
		}
	}
}
