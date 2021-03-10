import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Step implements EventHandler<ActionEvent> {
	private int framerate = 0;
	@Override
	public void handle(ActionEvent arg0) {
		framerate++;
		System.out.printf("%d%n",framerate);
	}

}
