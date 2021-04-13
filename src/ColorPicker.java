import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;

abstract public class ColorPicker {

	protected Random rand;
	// list of colors to choose from
	protected Color[] ColorList = { Color.AQUA, Color.HOTPINK, Color.PURPLE, Color.LIGHTGRAY, Color.LIME, Color.RED,
			Color.ORANGE, Color.OLIVE };

	// colors of which will be used
	ArrayList<Color> chosenColors = new ArrayList<Color>();

	// numbers of colors used
	protected int numOfColors;

	protected ColorPicker(int num) {
		// initialize random
		rand = new Random();
		// select colors
		reSelect(num);
	}
	/**
	 * wanting to reset the amount of colros used and pick new colors
	 * 
	 * @param newNumOf number of colors you want to use
	 */
	protected void reSelect(int newNumOf) {
		chosenColors.clear();
		numOfColors = newNumOf;
		for (int i = 0; i < numOfColors; i++) {
			chosenColors.add(ColorList[rand.nextInt(ColorList.length)]);
		}
	}

	public ArrayList<Color> getchosenColors() {
		return chosenColors;
	}

}
