import java.util.Random;

public class Wall extends ColorPicker{
	
	private int difficulty = 1;
	
	public Wall() {
		super(3);
	}
	
	public int getDif() {
		return difficulty;
	}
}
