import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

public abstract class ColorPicker {
	
	private Random rand;
	//list of colors to choose from
	protected Color[] ColorList = {Color.AQUA,Color.HOTPINK,Color.PURPLE,
			Color.LIGHTGRAY,Color.LIME,Color.RED,Color.ORANGE,Color.YELLOW};
	
	// colors of which will be used
	ArrayList<Color> chosenColors = new ArrayList<Color>();
	
	//numbers of colors used
	protected int numOfColors;
	
	protected ColorPicker(int numof) {
		//initialize random
		rand = new Random();
		numOfColors = numof;
		//select colors
		reSelect();
	}
	
	
	/**
	 * add colors to chosencolors in order to know what 
	 * colors to use and select colors at random
	 */
	protected void reSelect() {
		chosenColors.clear();
		for(int i = 0;i < numOfColors;i++) {
			chosenColors.add(ColorList[rand.nextInt(ColorList.length)]);
		}
	}
	/**
	 * wanting to reset the amount of colros used and pick new colors
	 * @param newNumOf number of colors you want to use
	 */
	protected void reSelect(int newNumOf) {
		chosenColors.clear();
		numOfColors = newNumOf;
		for(int i = 0;i < numOfColors;i++) {
			chosenColors.add(ColorList[rand.nextInt(ColorList.length)]);
		}
	}
	
	/**
	 * also can use chosenColors.size()
	 * @return (int) number of colors 
	 */
	protected int getNumOfColors() {
		return numOfColors;
	}
	
	
}
