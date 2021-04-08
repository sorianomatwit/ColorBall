
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {
	int startingLives = 25; //used to define lives
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
		Text A = new Text(); //hp
		Text C = new Text(); //time
		A.setX(410);
		A.setY(30);
		C.setX(410);
		C.setY(50);
		String a = String.valueOf(startingLives);
		A.setText("Health: " + a);
		pane.getChildren().add(A);
		pane.getChildren().add(C);
		// this will happen every frame
	//	this.lives=3;
		EventHandler<ActionEvent> step = new EventHandler<ActionEvent>() {
			
			private int lives = startingLives; //pulls form top
			private int timeAlive = 0;
			private boolean alive = true; //if game is active. Switches to false when player loses
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
						//lose a life
						lives--;
						pane.getChildren().remove(A);
						String a = String.valueOf(lives);
						A.setText("Health: " + a);
						pane.getChildren().add(A);
					}
					if (lives<=0) {
						pane.getChildren().clear();
						Text B = new Text();
						B.setX(100);
						B.setY(200);
						alive=false;
						B.setText("Lol, you died\nYou were alive for " + timeAlive/20 + " seconds");
						pane.getChildren().add(B);
					}
					if (alive)
					timeAlive++;
					String c = String.valueOf(timeAlive/20);
					C.setText("Time: " + c);
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
			
			//Ball Visibility (Keyboard Control)
			Boolean iPress = false;
			KeyCode iCode = e.getCode();
			if(iCode == KeyCode.I && !iPress) {
				iPress = true;
				(b.getGraphic()).setVisible(false);
			}
			else if (iPress && iCode != KeyCode.I){
				iPress = false;
				(b.getGraphic()).setVisible(true);
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
