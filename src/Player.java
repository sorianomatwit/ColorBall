
import java.util.Random;

import javafx.scene.shape.Circle;


public class Player {
	private double radius;
	//position 
	private double x;
	private double y;
	//movement 
	private double vx;
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
		
		vx=100; //pixels
		vy=50;
		
		graphic = new Circle(x,y,radius);
	}
	
	public Circle getGraphic() {
		return graphic;
	}
	
	public void setBoundary(double x, double y) {
		maxX=x;
		maxY=y;
		
	}
	
	public void move(double dt) {
		//update x and y (solution to a differential equation)
		x=x+vx*dt;
		y=y+vy*dt;
		
		//left/right walls
		if(x+radius>=maxX || x-radius<=0) {
			//do the bounce
			vx=-vx;
		}
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
	
	public void kick() {
		double kickX = (rand.nextDouble()-0.5)*500;
		double kickY = (rand.nextDouble()-0.5)*500;
		vx=vx+kickX;
		vy+=kickY;
	}
	
	public void stop() {
		vx=0;
		vy=0;
		
	}
}
