import javafx.scene.Scene;

public interface Activator {
	public boolean isActive();
	public void setActive(boolean activate);
	public void reset(int a, Scene s);
	
}
