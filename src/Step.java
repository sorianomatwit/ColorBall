import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Step implements EventHandler<ActionEvent> {
	private int framerate = 0;// keeps track of every frame of the game


	@Override
	public void handle(ActionEvent arg0) {
		//whatever goes in here will happen the 
		framerate++;
		System.out.printf("%d%n",framerate);
	}

}


