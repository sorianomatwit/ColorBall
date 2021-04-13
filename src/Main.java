
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
	public static int startingLives = 25; // used to define lives
	public static int gameDiff = 3;// max difficulty is going to be 7
	public static int sec;
	public static int timeAlive = 0;
	public boolean net;
	public static boolean gameEnd = false;
	public static ArrayList<Node> children = new ArrayList<Node>();
	// abbility variables
	public int startcount[] = new int[5];// the numebr here equalts the number of abilities
	public boolean isInvince = false;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Player ball = new Player(gameDiff);
		ball.setLives(startingLives);

		Wall attacker = new Wall(gameDiff);
		Pane pane = new Pane();

		children.addAll(attacker.getGraphic());
		children.add(ball.getGraphic());

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setTitle("Platformer");
		primaryStage.setScene(scene);
		primaryStage.show();

		Text hp = new Text(); // hp
		Text timer = new Text(); // time
		children.add(hp);
		children.add(timer);
		hp.setX(410);
		hp.setY(30);
		timer.setX(410);
		timer.setY(50);
		String a = String.valueOf(startingLives);
		hp.setText("Health: " + a);

		attacker.setHeights(scene);
		attacker.setSpd(scene.getWidth() * 0.01);
		pane.getChildren().addAll(children);

		// this will happen every frame
		EventHandler<ActionEvent> step = new EventHandler<ActionEvent>() {

			private int gameProg = 0;
			// use to detect amount of time player got hit
			private int count = 0;

			@Override

			public void handle(ActionEvent arg0) {
				// everything here happens every frame
				ball.setBoundary(scene.getHeight());// determines ball state

				ball.move(scene.getHeight() * 0.00025); // move the ball
				if (attacker.getX() >= scene.getWidth())
					ball.setColor(attacker);
				// wall movement
				attacker.Update(ball);
				attacker.display();
				
				
				// collsion
				if (!isInvince) {
					if (attacker.collide(ball)) {
						// you got hit
						ball.graphic.setFill(Color.BLACK);
						// lose a life
						if (count < 1) {
							ball.gotHit();
							count++;
						}
						pane.getChildren().remove(hp);
						String a = String.valueOf(ball.getLives());
						hp.setText("Health: " + a);
						pane.getChildren().add(hp);
					} else
						count = 0;
				}

				// game difficulty stuff
				if (ball.isAlive()) {
					timeAlive++;
					if (!net)
						gameProg++; // Currently Unused
				}
				sec = timeAlive / 60;
				String c = String.valueOf(sec);
				timer.setText("Time: " + c);
				// any ability that activate will stop here
				// int order to active an abiulty set startcount to sec + the length u want the
				// ability to stay active
				if (sec > startcount[0] && ball.isActive()) {// invisibility
					ball.setActive(false);
				}
				if (sec > startcount[1] && attacker.isActive()) {// wal bounce
					attacker.setActive(false);
				}
				if (sec > startcount[2] && isInvince) {
					isInvince = false;
				}
				if(sec > startcount[3]) {
					attacker.setSpd(scene.getWidth() * 0.01);
				}
				if(startcount[4] > sec) {
					ball.flashing(5);
				} else {
					ball.stopFlashing();
				}
				// DifficultyScaleSystem
				if (gameProg >= 600) {
					net = true;
					gameProg = 0;
				}
				if (net && gameDiff < 7 && attacker.getX() == 0) { // currently caps at 5, but you can raise it to 7
					// DiffScale();
					gameDiff++;
					attacker.setDifficulty(gameDiff);
					attacker.setHeights(scene);
					net = false;
				}
				// game end
				if (!ball.isAlive()) {
					gameEnd = true;
					pane.getChildren().clear();
					Text endScreen = new Text();
					endScreen.setX(100);
					endScreen.setY(200);
					endScreen.setText("Lol, you died\nYou were alive for " + sec + " seconds");
					pane.getChildren().add(endScreen);
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
				ball.VyUp();
			} else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
				ball.VyDown();
			}
			if (e.getCode() == KeyCode.SPACE) {
				ball.flipVy();
			}

			// testing code !NOT apart of the game!
			if (e.getCode() == KeyCode.Z) {
				System.out.println("GameDff");
				if (gameDiff < 7)
					gameDiff++;
				attacker.setDifficulty(gameDiff);
				attacker.setHeights(scene);
			}

			// Ball Visibility (Keyboard Control)
			if (e.getCode() == KeyCode.X) {
				System.out.println("invisible");
				ball.invisible();
				startcount[0] = sec + 3;
			}

			// bounce
			if (e.getCode() == KeyCode.C) {
				System.out.println("bounce");
				attacker.setActive(true);
				startcount[1] = sec + 5;
			}

			// ivincibilty
			if (e.getCode() == KeyCode.V) {
				System.out.println("invincible");
				isInvince = true;
				startcount[2] = 10 + sec;
			}
			if (e.getCode() == KeyCode.B) {
				System.out.println("game restart");
				if (gameEnd) {// game restart
					reset(ball, attacker, pane, scene);
				}
			}
			if(e.getCode() == KeyCode.N) {
				System.out.println("reverse");
				attacker.reverse();
				startcount[3] = 10 + sec;
			}
			if(e.getCode() == KeyCode.M) {
				System.out.println("flash");
				startcount[4] = 10 + sec;
			}
		});
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				s.stop(); 
			}
		});
	}

	public static void reset(Player p, Wall w, Pane r, Scene s) {
		r.getChildren().clear();
		r.getChildren().addAll(children);
		p.setLives(startingLives);
		sec = 0;
		timeAlive = 0;
		gameEnd = false;
		gameDiff = 3;
		p.reset(gameDiff);
		w.reset(gameDiff);
		w.setHeights(s);
	}

}
