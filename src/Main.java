
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Main extends Application {
	public static int startingLives = 100;//10; // used to define lives
	public static int gameDiff = 3;// max difficulty is going to be 7
	public static double sec;
	public static int timeAlive = 0;
	public static int begin = 0;
	public boolean net;
	public static boolean gameEnd = false;
	public static boolean getReady = true;
	public static boolean gameStart = false;
	public static boolean nameEntered = false;
	public static boolean ability = false;
	
	public static ArrayList<Node> mainmenu = new ArrayList<Node>();
	public static ArrayList<Node> children = new ArrayList<Node>(); // game nodes so the player and wall andf health  
	public static ArrayList<String> names = new ArrayList<String>();
	
	// so this is used to save al the main game stuff
	// clear the pane to remove the game
	// and then add the childdren to the pane to add teh game
	// abbility variables
	
	public static double startcount[] = new double[6];// the numebr here equalts the number of timers
	public static boolean isInvince = false;

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// loading assets
		FileInputStream fonts = new FileInputStream("assets/04B_30__.TTF");
		FileInputStream imagefile = new FileInputStream("assets/icon.png");
		Font.loadFont(fonts, 14);
		Font hero = new Font("04B", 15);
		Media song = new Media(new File("assets/GameMusic.mp3").toURI().toString());
		MediaPlayer gameMusic = new MediaPlayer(song);
		Image icon = new Image(imagefile);
//		gameMusic.setAutoPlay(true);
//		gameMusic.setCycleCount(MediaPlayer.INDEFINITE);
		Media hit = new Media(new File("assets/hit.mp3").toURI().toString());
		MediaPlayer hitEF = new MediaPlayer(hit);
		gameMusic.setVolume(.09);
		hitEF.setVolume(0.05);
		TextField PlayerName = new TextField();
		Player ball = new Player(gameDiff);
		ball.setLives(startingLives);

		Wall attacker = new Wall(gameDiff);
		Pane pane = new Pane();

		children.addAll(attacker.getGraphic());
		children.add(ball.getGraphic());

		Scene scene = new Scene(pane, 500, 500);
		primaryStage.getIcons().add(icon);
		primaryStage.setTitle("Colorball");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
		// main menu stuff
		// create and set text
		Text title = new Text();
		Text[] options = new Text[3];
		Text instruct = new Text();
		
		
		// options stuff
		for (int i = 0; i < options.length; i++) {
			options[i] = new Text();
			options[i].setX(scene.getWidth() / 2 - 91);
			options[i].setFont(hero);
			options[i].setY(110 + 40 * (i + 1));
			options[i].setScaleX(2);
			options[i].setScaleY(2);
			options[i].setTextAlignment(TextAlignment.CENTER);
		}

		options[0].setText("   Start");
		options[1].setText("View High Scores");
		options[2].setText("   Quit");
		options[2].setTranslateX(5);
		title.setText("ColorBall");

		// title stuff
		title.setScaleX(3);
		title.setScaleY(3);
		title.setFont(hero);
		title.setX(scene.getWidth() / 2 - 40.5);
		title.setY(100);
		title.setTextAlignment(TextAlignment.CENTER);
		
		//instruction
		
		instruct.setFont(hero);
		instruct.setX(scene.getWidth() / 2 - 200);
		instruct.setY(275);
		instruct.setScaleX(1.05);
		instruct.setScaleY(1.05);
		instruct.setTextAlignment(TextAlignment.CENTER);
		instruct.setText("Space: change direction of the Ball\nW or Up: move the Ball up\nS or Down: move Ball down");
		
		mainmenu.add(instruct);
		mainmenu.add(title);
		mainmenu.add(options[0]);
		mainmenu.add(options[1]);
		mainmenu.add(options[2]);

		Text hp = new Text(); // hp
		Text timer = new Text(); // time

		// game screen
		hp.setX(200);
		hp.setY(30);
		timer.setX(200);
		timer.setY(50);

		hp.setFont(hero);
		timer.setFont(hero);
		String a = String.valueOf(startingLives);
		hp.setText("Health: " + a);

		attacker.setHeights(scene);
		attacker.setSpd(scene.getWidth() * 0.01);

		children.add(hp);
		children.add(timer);

		if (!gameStart) {
			pane.getChildren().addAll(mainmenu);
		}
		gameMusic.setStartTime(Duration.seconds(1));
		gameMusic.setStopTime(Duration.seconds(80));
		gameMusic.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				gameMusic.stop();
			}
			
		});
		// this will happen every frame
		EventHandler<ActionEvent> step = new EventHandler<ActionEvent>() {

			private int gameProg = 0;
			// use to detect amount of time player got hit
			private int count = 0;

			@Override

			public void handle(ActionEvent arg0) {
				// everything here happens every frame

				if (getReady) {
					attacker.display();
					ball.setY(scene);
					attacker.setX(scene.getWidth() - attacker.getWidth());
					if (begin == 0) {
						ball.setColor(attacker);
						begin++;
					}

				}
				if (gameStart) {
					gameMusic.play();
					ball.setBoundary(scene.getHeight());// determines ball state
					attacker.Update(ball);
					attacker.display();
					ball.move(scene.getHeight() * 0.00025); // move the ball

//					//normal color switch
					if (attacker.getX() >= scene.getWidth()) {
						ball.setColor(attacker);
					}
					if (attacker.getX() <= 0) {
						ball.setColor(attacker);
					}

					// game difficulty stuff
					if (ball.isAlive()) {
						timeAlive++;
						if (!net)
							gameProg++; // Currently Unused
					}

					sec = timeAlive / 60.0;
					String c = String.format("%.0f", sec);
					timer.setText("Time: " + c);

					// collsion
					if (!isInvince) {
						if (attacker.collide(ball)) {

							// lose a life
							if (count < 1) {
								// you got hit
								if (hitEF.getCurrentTime().toSeconds() >= hitEF.getStopTime().toSeconds()) {
									hitEF.stop();
								}
								hitEF.play();
								startcount[4] = sec + 2;
								ball.gotHit();
								count++;
							}
							pane.getChildren().remove(hp);
							String a = String.valueOf(ball.getLives());
							hp.setText("Health: " + a);
							pane.getChildren().add(hp);

						} else
							count = 0;
					} else {
						ball.getGraphic().setFill(Color.WHITE);
					}

					// ability will spawn at random
					Random rand = new Random();
					ability =  true; // for testing abilities uncomment this line
					if (startcount[5] < sec) {
						if (rand.nextInt(240) == 1 && !ability) {
							spawnAbility(ball, attacker);
							ability = true;
						}
					}
					
					// any ability that activate will stop here
					// int order to active an abiulty set startcount to sec + the length u want the
					// ability to stay active
					if (sec > startcount[0] && ball.isActive()) {// invisibility
						ball.setActive(false);
						ability = false;
					}
					if (sec > startcount[1] && attacker.isActive()) {// wal bounce
						attacker.setActive(false);
						ability = false;
					}
					if (sec > startcount[2] && isInvince) {
						isInvince = false;
						ability = false;
					}
					if (sec > startcount[3] && attacker.getSpd() < 0) {
						attacker.setSpd(scene.getWidth() * 0.01);
						ability = false;
					}

					// DifficultyScaleSystem
					if (gameProg >= 600) {
						net = true;
						gameProg = 0;
					}
					if (net && gameDiff < 6 && attacker.getX() <= 0) { // currently caps at 5, but you can raise it to 7
						// DiffScale();
						gameDiff++;
						attacker.setDifficulty(gameDiff);
						attacker.setHeights(scene);
						net = false;
					}

					// game end
					if (!ball.isAlive()) {
						gameMusic.stop();
						gameEnd = true;
						gameStart = false;
						timer.setText("Time: " + 0);
						hp.setText("Health: " + startingLives);
						pane.getChildren().clear();
						Text endScreen = new Text();
						endScreen.setFont(hero);
						endScreen.setScaleX(1);
						endScreen.setScaleY(1);
						endScreen.setText(String.format("Lol, you died\nYou were alive for"
								+ " %.3f second%nEnter your name to save your score",sec));
						endScreen.setX(55);
						endScreen.setY(100);

						endScreen.setTextAlignment(TextAlignment.CENTER);
						// high score
						//PlayerName.setFont(hero);
						//PlayerName
						PlayerName.setMaxSize(100, 30);
						PlayerName.setMinSize(100, 30);
						PlayerName.setTranslateX(250 - PlayerName.getMaxWidth() / 2);
						PlayerName.setTranslateY(150);
						PlayerName.setText("Name");
						// option stuff
						options[0].setX(175);
						options[1].setX(153);
						options[2].setX(175);
						for (int i = 0; i < options.length; i++) {
							options[i].setFont(hero);
							options[i].setY(180 + 20 * (i + 1));
							options[i].setScaleX(1);
							options[i].setScaleY(1);
							options[i].setTextAlignment(TextAlignment.CENTER);
							pane.getChildren().add(options[i]);
						}

						pane.getChildren().add(PlayerName);
						pane.getChildren().add(endScreen);

					}
				}
			}
		};

		// 1 frame

		Timeline s = new Timeline(new KeyFrame(Duration.millis(16.67), (step)));
		s.setCycleCount(Timeline.INDEFINITE);
		s.play();

		pane.requestFocus();

		/// action code
		
		// start menu code
		for (int i = 0; i < options.length; i++) {
			Text t = options[i];
			t.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					t.setFill(Color.DARKRED);
				}
			});
			t.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					t.setFill(Color.BLACK);
				}
			});
		}
		// START
		options[0].setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// gameStart = true;

				if (gameEnd) {
					reset(ball, attacker, pane, scene);
				}
				pane.getChildren().clear();
				pane.getChildren().addAll(children);
				startcount[5] = 3;
				if (getReady) {

					getReady = false;
				}

			}
		});
		// highscore list MUST DO!!!!!!!!!!!!!!
		options[1].setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (!getReady) {
					gameStart = true;
				}
				
				if (gameEnd) {
					reset(ball, attacker, pane, scene);
					pane.getChildren().clear();
					for (int i = 0; i < options.length; i++) {
						options[i].setX(scene.getWidth() / 2 - 61);
						options[i].setFont(hero);
						options[i].setY(120 + 40 * (i + 1));
						options[i].setScaleX(2);
						options[i].setScaleY(2);
						options[i].setTextAlignment(TextAlignment.CENTER);
					}
					pane.getChildren().addAll(mainmenu);

				}
				pane.getChildren().clear();
				Text listofscores = new Text();
				listofscores.setFont(hero);
				listofscores.setTextAlignment(TextAlignment.RIGHT);

				listofscores.setTranslateY(100);
				if (names.size() == 0) {
					String ms = "There are no highscores";
					listofscores.setText(ms);
					listofscores.setTranslateX(scene.getWidth() / 2 - 115);
				} else {
					String s = "";
					Collections.sort(names, new Comparator<String>() {

						@Override
						public int compare(String o1, String o2) {
							int k = 0, l = 0;
							Integer a = new Integer(0);
							Integer b = new Integer(0);

							for (int i = o1.length() - 1; i > 0; i--) {
								int charint = Character.getNumericValue(o1.charAt(i));
								if (charint >= 0 && charint <= 9) {
									a += (int) (charint * Math.pow(10, k));
									k++;
								}
							}

							for (int i = o2.length() - 1; i > 0; i--) {
								int charint = Character.getNumericValue(o2.charAt(i));
								if (charint >= 0 && charint <= 9) {
									b += (int) (charint * Math.pow(10, l));
									l++;
								}
							}
							//System.out.printf("%d compareto %d %n", b, a);
							return Integer.valueOf(b.compareTo(a));
						}
					});
					for (int i = 0; i < names.size(); i++) {
						if (i < 10) {
							s += String.format("%s\n", names.get(i));
						}
						listofscores.setText(s);
						listofscores.setTranslateX(scene.getWidth() / 2 - 50);
					}
				}
				// options code
				options[0].setScaleY(1);
				options[0].setScaleX(1);
				options[2].setScaleY(1);
				options[2].setScaleX(1);

				options[0].setY(450);
				options[0].setX(0);
				options[2].setY(450);
				options[2].setX(400);
				pane.getChildren().add(options[0]);
				pane.getChildren().add(options[2]);
				pane.getChildren().add(listofscores);
			}
		});

		// QUit
		options[2].setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.close();
			}
		});

		// end game menu
		PlayerName.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				PlayerName.selectAll();
			}
			
		});
		PlayerName.setOnKeyPressed(e -> {

			Text t = new Text();
			t.setFont(hero);
			t.setX(260 - 130);
			t.setY(50);
			t.setText("Your score has been saved!");
			if (e.getCode() == KeyCode.ENTER) {
				if (!nameEntered) {
					String adder = String.format("%s: %.3f", PlayerName.getText(), sec);
					names.add(adder);
					nameEntered = true;
					//System.out.printf("I added %s%n", adder);
					pane.getChildren().add(t);
				}

				pane.requestFocus();
			}

		});
		// control code
		pane.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				pane.requestFocus();
			}
		});
		pane.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
				ball.VyUp();
				if (!getReady) {
					gameStart = true;
				}
			} else if (e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
				ball.VyDown();
				if (!getReady) {
					gameStart = true;
				}
			}
			if (e.getCode() == KeyCode.SPACE) {
				if (gameStart) {
					ball.flipVy();
				}
				if (!getReady) {
					gameStart = true;
				}

//				if (gameEnd) {
//					reset(ball, attacker, pane, scene);
//					pane.getChildren().clear();
//					for (int i = 0; i < options.length; i++) {
//						options[i].setX(scene.getWidth() / 2 - 61);
//						options[i].setFont(hero);
//						options[i].setY(120 + 40 * (i + 1));
//						options[i].setScaleX(2);
//						options[i].setScaleY(2);
//						options[i].setTextAlignment(TextAlignment.CENTER);
//					}
//					pane.getChildren().addAll(mainmenu);
//				}
			}

			// testing code !NOT apart of the game!
//			if (e.getCode() == KeyCode.Z) {
//				System.out.println("GameDff");
//				if (gameDiff < 6)
//					gameDiff++;
//				attacker.setDifficulty(gameDiff);
//				attacker.setHeights(scene);
//			}

			// Ball Visibility (Keyboard Control)
//			if (e.getCode() == KeyCode.X) {
//				System.out.println("invisible");
//				ball.invisible();
//				startcount[0] = sec + 3;
//			}

			// bounce
//			if (e.getCode() == KeyCode.C) {
//				System.out.println("bounce");
//				attacker.setActive(true);
//				startcount[1] = sec + 5;
//			}

			// ivincibilty
//			if (e.getCode() == KeyCode.V) {
//				System.out.println("invincible");
//				isInvince = true;
//				startcount[2] = 10 + sec;
//			}
//			if (e.getCode() == KeyCode.B) {
//				System.out.println("game restart");
//				if (gameEnd) {// game restart
//					reset(ball, attacker, pane, scene);
//				}
//			}
			if (e.getCode() == KeyCode.N) {
				System.out.println("reverse");
				attacker.reverse();
				startcount[3] = 10 + sec;
			}
//			if (e.getCode() == KeyCode.M) {
//				System.out.println("flash");
//				startcount[4] = 10 + sec;
//			}
		});
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent arg0) {
				s.stop();
			}
		});
	}

	public static void reset(Player p, Wall w, Pane r, Scene s) {
		// turning off all abilities
		w.setSpd(s.getWidth() * 0.01);
		isInvince = false;
		p.setActive(false);
		w.setActive(false);
		p.stopFlashing();
		// rest;
		p.setLives(startingLives);
		sec = 0;
		timeAlive = 0;
		gameEnd = false;
		gameDiff = 3;
		gameStart = false;
		getReady = true;
		nameEntered = false;
		ability = false;
		p.reset(gameDiff,s);
		w.reset(gameDiff,s);
		p.setColor(w);
		begin = 0;
		for(int i = 0; i < startcount.length;i++) {
			startcount[i] = 0;
		}

	}

	public static void spawnAbility(Player p, Wall w) {
		Random rand = new Random();
		int prob = rand.nextInt(1001);

		if (prob < 73) {
			//System.out.println("invisible");
			p.invisible();
			startcount[0] = sec + 1;
		} else if (prob < 653) {
			//System.out.println("bounce");
			w.setActive(true);
			startcount[1] = sec + 10;
		} else if (prob < 877) {
			//System.out.println("reverse");
			w.reverse();
			startcount[3] = 6 + sec;
		} else {
			//System.out.println("invincible");
			isInvince = true;
			startcount[2] = 6 + sec;
		}
	}
}
