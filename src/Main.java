import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {
	
	int gameDiff = 3;
	boolean toggle = true;
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Player b = new Player(20,100,100);
		Wall attacker = new Wall(gameDiff);
		
		
		
		
		
		Pane pane = new Pane();
		 //determines ball state
		
		pane.getChildren().addAll(attacker.getGraphic());
		pane.getChildren().add(b.getGraphic());

		Scene scene = new Scene(pane,500,500);
		primaryStage.setTitle("Platformer");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		b.setBoundary(scene.getWidth(), scene.getHeight());
		
		
		
		//this well happen every frame
		EventHandler<ActionEvent> step = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				//everything here happens every frame
				// wall movement
				attacker.setHeights(scene);
				//attacker.display();
				attacker.Update();
				if (toggle) b.move(0.125); // move the ball 
			}
			
		};
		
		// 1 frame
		Timeline s = new Timeline(new KeyFrame(Duration.millis(16.67), (step)));
		s.setCycleCount(Timeline.INDEFINITE);
		s.play();


		pane.requestFocus();			 		
		pane.setOnKeyPressed(e -> { 
			if (e.getCode() == KeyCode.SPACE) {
				toggle = !toggle;
				if(toggle) { toggle = false;}
				else {toggle = true;}
			}

		});
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				s.stop();

			}
		});

	}

}
