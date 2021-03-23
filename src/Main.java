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

	int gameDiff = 3;// max difficulty is going to be 7
	boolean toggle = true;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Player b = new Player(gameDiff);
		 
		Wall attacker = new Wall(gameDiff);
		
		Pane pane = new Pane();

		pane.getChildren().addAll(attacker.getGraphic());
		pane.getChildren().add(b.getGraphic());

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setTitle("Platformer");
		primaryStage.setScene(scene);
		primaryStage.show();

		// this well happen every frame
		EventHandler<ActionEvent> step = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				// everything here happens every frame

				b.setBoundary(scene.getHeight());// determines ball state
				
				b.move(scene.getHeight() * 0.00025); // move the ball
				if(attacker.getX() >= scene.getWidth()) b.setColor(attacker);
				// wall movement
				
				attacker.setHeights(scene);
					attacker.Update(b);
					attacker.display();
					
					attacker.setSpd(scene.getWidth() * 0.01);
					if(attacker.collide(b)){
						//you got hit
						b.graphic.setFill(Color.BLACK);
					}
			}

		};

		// 1 frame
		Timeline s = new Timeline(new KeyFrame(Duration.millis(16.67), (step)));
		s.setCycleCount(Timeline.INDEFINITE);
		s.play();

		pane.requestFocus();
		pane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
				b.VyUp();
			} else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
				b.VyDown();
			}
			if(e.getCode() == KeyCode.SPACE) {
				b.flipVy();
			}
			// testing code !NOT apart of the game!
			if (e.getCode() == KeyCode.A) {
				if(gameDiff < 7 ) gameDiff++;
				attacker.setDifficulty(gameDiff);
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
