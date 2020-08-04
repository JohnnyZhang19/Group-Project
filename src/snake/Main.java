package snake;

import java.util.ArrayList;
import java.util.List;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.Random;


public class Main extends Application{
	static Dir direction = Dir.left;
	static int eggX = 0;
	static int eggY = 0;
	static Random rand = new Random();
	public static int actualRowNum = 30;
	public static int actualColNum = 30;
	public static int rowNum = actualRowNum+2;
	public static int colNum = actualColNum+2;
	static List<Node> snake = new ArrayList<>();
	public static int nodeSize = 15;
	public Snake aSnake = new Snake();
	public Egg egg = new Egg();
	public Obstacles obs = new Obstacles();
	int score = 0;
	int listLength = 10;
	static int speed = 5;
	Node[] obsList = new Node[listLength];
	static boolean gameOver = false;
	
	public enum Dir {
		left, right, up, down
	}
	
	public static class Node {
		int x;
		int y;
		int type;

		public Node(int x, int y, int type) {
			this.x = x;
			this.y = y;
			this.type = type;
		}

	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		startGame();
		VBox root = new VBox();
		Canvas aCanvas = new Canvas(rowNum * nodeSize, colNum * nodeSize);
		GraphicsContext graphic = aCanvas.getGraphicsContext2D();

		ObservableList<String> options = 
			    FXCollections.observableArrayList(
			        "Easy",
			        "Medium",
			        "Difficult"
			    );
		root.getChildren().addAll(aCanvas ,new Label("Select level"), new ComboBox(options));
		
		new AnimationTimer() {
			long lastSceen = 0;

			public void handle(long now) {
				if (lastSceen == 0) {
					lastSceen = now;
					screen(graphic);
					return;
				}
				if (now - lastSceen > 1000000000 / speed) {
					lastSceen = now;
					screen(graphic);
				}
			}
			

		}.start();
/**		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
          public void handle(ActionEvent e) 
          { 
              if(level.getValue().equals("Easy")) {
 //           	  Frame.setListLength(10);
              }
              if(level.getValue().equals("Medium")) {
            	  //Frame.setListLength(20);
              } 
              if(level.getValue().equals("Difficult")) {
            	  //Frame.setListLength(30);
              }
          }
		};

	*/	
		

		
		
		Scene scene = new Scene(root, rowNum * nodeSize, colNum * nodeSize);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
				if (key.getCode() == KeyCode.W) {
					direction = Dir.up;
				}
				if (key.getCode() == KeyCode.A) {
					direction = Dir.left;
				}
				if (key.getCode() == KeyCode.S) {
					direction = Dir.down;
				}
				if (key.getCode() == KeyCode.D) {
					direction = Dir.right;
				}

			});
		
		snake.add(new Node(rowNum/2,colNum/2,5));
		snake.add(new Node(rowNum/2,colNum/2 - 1,3));
		primaryStage.setScene(scene);
		primaryStage.setTitle("SNAKE FRENZY");
		primaryStage.show();	
	}
	
	public static void screen(GraphicsContext graphic) {
		Frame frame = new Frame();
		if (gameOver) {
			graphic.setFill(Color.CRIMSON);
			graphic.setFont(new Font("", 50));
			graphic.fillText("GAME OVER", 100, 250);
			return;
		}
		
		for (int i = snake.size() - 1; i >= 1; i--) {
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}
		switch (direction) {
		case up:
			snake.get(0).y--;
			if (snake.get(0).y < 0) {
				gameOver = true;
			}
			break;
		case down:
			snake.get(0).y++;
			if (snake.get(0).y > colNum) {
				gameOver = true;
			}
			break;
		case left:
			snake.get(0).x--;
			if (snake.get(0).x < 0) {
				gameOver = true;
			}
			break;
		case right:
			snake.get(0).x++;
			if (snake.get(0).x > rowNum) {
				gameOver = true;
			}
			break;

		}

		// eat food
		if (eggX == snake.get(0).x && eggY == snake.get(0).y) {
			snake.add(new Node(-1, -1, 3));
			startGame();
		}

		// self destroy
		for (int i = 1; i < snake.size(); i++) {
			if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
				gameOver = true;
			}
		}
		
		graphic.setFill(Color.GOLD);
		graphic.setFont(new Font("", 30));
		graphic.fillText("Score:" + frame.score, rowNum * (nodeSize - 10), colNum * (nodeSize - 10));
		
		graphic.setFill(Color.DARKGREEN);
		graphic.fillRect(0, 0,  rowNum * nodeSize, colNum * nodeSize);
		
		graphic.setFill(Color.FUCHSIA);
		graphic.fillOval(eggX * nodeSize, eggY * nodeSize, nodeSize, nodeSize);
		
		for (Node aCanvas : snake) {
			graphic.setFill(Color.CORAL);
			graphic.fillOval(aCanvas.x * nodeSize, aCanvas.y * nodeSize, nodeSize, nodeSize);
		}
	}
		
	public static void startGame() {
	start: while (true) {
		eggX = rand.nextInt(rowNum);
		eggY = rand.nextInt(colNum);
		
		for (Node aCanvas : snake) {
			if (aCanvas.x == eggX && aCanvas.y == eggY) {
				continue start;
			}
		}

		break;
	}

		
	}	
	public static void main(String[] arg) {
		//Frame frame = new Frame();
		//frame.run();
		launch(arg);
	}
	

	}

