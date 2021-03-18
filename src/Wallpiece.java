import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wallpiece extends Rectangle{
	Rectangle graphic;
	Color color;
	int label;
	double x;
	double y;
	double height;
	public Wallpiece(Color c,int index,double h) {
		color = c;
		label = index;
		x = 100;
		y = 100;
		height = h;
	}
	
	public void display() {
		x += -3;
		setFill(color);
		setX(x);
		setY(y);
		setHeight(height);
		System.out.printf("%.2f%.2f%n",getX(),getY());
	}
	public void Update(double x, double y,double height) {
		this.x = x;
		this.y = y;
		this.height = height;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
}
