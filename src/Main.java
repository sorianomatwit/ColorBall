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
	
	int game_diff = 3;
	boolean toggle = true;
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ColorPicker c = new ColorPicker(game_diff);
		Player b = new Player(20,100,100);
		b.setBoundary(500, 500);
		ArrayList<Wallpiece> wall = new ArrayList<Wallpiece>();
		for(int i = 0; i < c.numOfColors;i++) {
			wall.add(new Wallpiece(c.chosenColors.get(i),i,100));
		}
		
		Rectangle r = new Rectangle(100,100,100,100);
		r.setFill(c.chosenColors.get(0));
		
		
		Pane pane = new Pane();
		 //determines ball state
		
		pane.getChildren().addAll(wall);
		pane.getChildren().add(b.getGraphic());

		Scene scene = new Scene(pane,500,500);
		primaryStage.setTitle("Platformer");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		double heights = scene.getHeight()/c.numOfColors;
		for(int i = 0; i < wall.size();i++) {
			Wallpiece w = wall.get(i);
			w.Update(scene.getWidth(),(0+ i*heights),heights);
		}
		
		
		//this well happen every frame
		EventHandler<ActionEvent> step = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//everything here happens every frame
				// wall movement
				for(int i = 0; i < wall.size();i++) {
					Wallpiece w = wall.get(i);
					w.display();
					if(w.getX() < 0) {
						w.Update(scene.getWidth(),(0+ i*heights),heights);
						c.reSelect();
						w.setColor(c.chosenColors.get(i));
					}
					
				}// wall loop closed
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
