
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {
	int startingLives = 5; // used to define lives
	int gameDiff = 2;// max difficulty is going to be 7
	int sec;
	int gameProg;
	boolean net;
	int startcount;
	public boolean bounce = false;
	boolean toggle = true;

	public static void main(String[] args) {
		launch(args);

	}
	
	//public void DiffScale() {
	//	gameDiff=gameDiff++;
	//}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Player b = new Player(gameDiff, startingLives);

		Wall attacker = new Wall(gameDiff);

		Pane pane = new Pane();

		pane.getChildren().addAll(attacker.getGraphic());
		pane.getChildren().add(b.getGraphic());

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setTitle("Platformer");
		primaryStage.setScene(scene);
		primaryStage.show();
		Text A = new Text(); // hp
		Text C = new Text(); // time
		A.setX(410);
		A.setY(30);
		C.setX(410);
		C.setY(50);
		String a = String.valueOf(startingLives);
		A.setText("Health: " + a);
		pane.getChildren().add(A);
		pane.getChildren().add(C);
		// this will happen every frame
		// this.lives=3;
		attacker.setHeights(scene);
		EventHandler<ActionEvent> step = new EventHandler<ActionEvent>() {

			private int timeAlive = 0;
			private int gameProg = 0;
			private int count = 0;
		
			@Override
			
			public void handle(ActionEvent arg0) {

				// everything here happens every frame
				b.setBoundary(scene.getHeight());// determines ball state

				b.move(scene.getHeight() * 0.00025); // move the ball
				if (attacker.getX() >= scene.getWidth())
					b.setColor(attacker);
				// wall movement

				
				attacker.Update(b);
				attacker.display();

				attacker.setSpd(scene.getWidth() * 0.01);
				// collsion
				if (attacker.collide(b)) {
					// you got hit
					b.graphic.setFill(Color.BLACK);
					// lose a life
					if (count < 1) {
						b.gotHit();
						count++;
					}

					pane.getChildren().remove(A);
					String a = String.valueOf(b.getLives());
					A.setText("Health: " + a);
					pane.getChildren().add(A);
				} else
					count = 0;

				if (!b.isAlive()) {
					pane.getChildren().clear();
					Text B = new Text();
					B.setX(100);
					B.setY(200);
					B.setText("Lol, you died\nYou were alive for " + sec + " seconds");
					pane.getChildren().add(B);
				}
				if (b.isAlive()) {
					timeAlive++;
					if (!net) gameProg++; //Currently Unused
				}
				sec = timeAlive / 60;
				String c = String.valueOf(sec);
				C.setText("Time: " + c);
				// any ability that activate will stop here
				// int order to active an abiulty set startcount to sec + the length u want the ability to stay active
				if(sec >= startcount) {
					b.setActive(false);
					if(bounce) {
						attacker.bounce();
					}
				} else {
					bounce = false;
				}
				
				//DifficultyScaleSystem
				if (gameProg >= 600) {
					net = true;
					gameProg = 0;
				}
				if (net && gameDiff < 5 && attacker.getX()==0) { //currently caps at 5, but you can raise it to 7
					//DiffScale();
					gameDiff++;
					attacker.setDifficulty(gameDiff);
					net=false;
				} //**/
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
			if (e.getCode() == KeyCode.SPACE) {
				b.flipVy();
			}

			// Ball Visibility (Keyboard Control)
			if (e.getCode() == KeyCode.I) {
				b.invisible();
				startcount = sec + 5;
			}
			
			if(e.getCode() == KeyCode.B) {
				bounce = true;
				startcount = sec + 5;
			}

			// testing code !NOT apart of the game!
			if (e.getCode() == KeyCode.A) {
				if (gameDiff < 7)
					gameDiff++;
				attacker.setDifficulty(gameDiff);
				attacker.setHeights(scene);
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
