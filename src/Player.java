
import java.util.Random;

import javafx.scene.shape.Circle;


public class Player {
	private double radius;
	//position 
	private double x;
	private double y;
	//movement 
	private double vy;
	//bounds of the screen
	private double maxX;
	private double maxY;
	
	//graphic 
	Circle graphic = null;
	
	//random number generator
	private Random rand = new Random();
	
	//constructor
	public Player(double radius, double x, double y) {
		this.radius = radius;
		this.x = x;
		this.y = y;
		
		vy=50;
		
		graphic = new Circle(x,y,radius);
	}
	
	public Circle getGraphic() {
		return graphic;
	}
	
	public void setBoundary(double x, double y) {
		maxY=y;
		
	}
	
	public void move(double dt) {
		//update x and y (solution to a differential equation)
		y=y+vy*dt;
		//top/bottom walls
		if(y+radius>maxY || y-radius<=0) {
			//do the bounce
			vy=-vy;
			
		}
		
		updateGraphic();
	}
	
	private void updateGraphic() {
		graphic.setCenterX(x);
		graphic.setCenterY(y);
	}
}
