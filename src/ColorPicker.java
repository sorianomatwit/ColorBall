import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

abstract public class ColorPicker {

	protected Random rand;
	// list of colors to choose from
	protected Color[] ColorList = { Color.AQUA, Color.HOTPINK, Color.PURPLE, Color.LIGHTGRAY, Color.LIME, Color.RED,
			Color.ORANGE, Color.YELLOW };

	// colors of which will be used
	ArrayList<Color> chosenColors = new ArrayList<Color>();

	// numbers of colors used
	protected int numOfColors;

	protected ColorPicker(int num) {
		numOfColors = num;
		// initialize random
		rand = new Random();
		// select colors
		reSelect();
	}
	
	/**
	 * add colors to chosencolors in order to know what colors to use and select
	 * colors at random
	 */
	protected void reSelect() {
		chosenColors.clear();
		while(chosenColors.size() != numOfColors) {
			for (int i = 0; i < numOfColors; i++) {
				chosenColors.add(ColorList[rand.nextInt(ColorList.length)]);
			}
			for (int i = 0; i < chosenColors.size(); i++) {
				Color c = chosenColors.get(i);
				for (int k = 0; k < chosenColors.size(); k++) {
					Color c1 = chosenColors.get(k);
					if (c == c1 && i != k) {
						chosenColors.remove(k);
					}
				}
			}
		}
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

}
