import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane pane = new Pane();
		
		
		
		primaryStage.setTitle("Platformer");
		primaryStage.setScene(new Scene(pane,200,200));
		primaryStage.show();
		
		// 1 frame
		Timeline s = new Timeline(new KeyFrame(Duration.millis(16.7), (new Step())));
		s.setCycleCount(Timeline.INDEFINITE);
		s.play();

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				s.stop();

			}
		});

	}

}
