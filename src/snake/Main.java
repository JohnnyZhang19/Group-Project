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

	static List<Node> node = new ArrayList<>();
	public static int nodeSize = 15;
	public Snake aSnake = new Snake();
	public Egg egg = new Egg();
	public Obstacles obs = new Obstacles();
	int score = 0;
	int listLength = 10;
	int speed;
	Node[] obsList = new Node[listLength];
	static List<Node> obstacle = new ArrayList<>();
	static boolean gameOver = false;
	int userChooseLevel;
	
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
		Canvas aCanvas = new Canvas(Frame.rowNum * nodeSize, Frame.colNum * nodeSize);
		GraphicsContext graphic = aCanvas.getGraphicsContext2D();

		root.getChildren().addAll(aCanvas);
		
		new AnimationTimer() {
			long lastSceen = 0;

			public void handle(long now) {
				if (lastSceen == 0) {
					lastSceen = now;
					screen(graphic);
					return;
				}
				
		//  Different levels for snake:
		//  Level 0: easy, level 1: normal; level 2: hard
		//  We can create as many level as we want depending on the speed
				
				userChooseLevel = 2;
				if (userChooseLevel == 0) speed = 5;
				if (userChooseLevel == 1) speed = 10;
				if (userChooseLevel == 2) speed = 15;
				if (now - lastSceen > 1000000000 / speed) {
					lastSceen = now;
					screen(graphic);
				}
			}
			

		}.start();

		
		
		Scene scene = new Scene(root, Frame.rowNum * nodeSize, Frame.colNum * nodeSize);
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
		

		node.add(new Node(Frame.rowNum/2,Frame.colNum/2,5));
		node.add(new Node(Frame.rowNum/2,Frame.colNum/2 - 1,3));
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
		
		
		for (int i = node.size() - 1; i >= 1; i--) {
			node.get(i).x = node.get(i - 1).x;
			node.get(i).y = node.get(i - 1).y;
		}
		switch (direction) {
		case up:
			node.get(0).y--;
			if (node.get(0).y < 0) {
				gameOver = true;
			}
			break;
		case down:
			node.get(0).y++;
			if (node.get(0).y > Frame.colNum) {
				gameOver = true;
			}
			break;
		case left:
			node.get(0).x--;
			if (node.get(0).x < 0) {
				gameOver = true;
			}
			break;
		case right:
			node.get(0).x++;
			if (node.get(0).x > Frame.rowNum) {
				gameOver = true;
			}
			break;

		}

		// eat food
		if (eggX == node.get(0).x && eggY == node.get(0).y) {
			node.add(new Node(-1, -1, 3));
			startGame();
		}

		// self destroy
		for (int i = 1; i < node.size(); i++) {
			if (node.get(0).x == node.get(i).x && node.get(0).y == node.get(i).y) {
				gameOver = true;
			}
		}
		
				
		graphic.setFill(Color.GOLD);
		graphic.setFont(new Font("", 30));
		graphic.fillText("Score:" + frame.score, Frame.rowNum * (nodeSize - 10), Frame.colNum * (nodeSize - 10));
		
		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0,  Frame.rowNum * nodeSize, Frame.colNum * nodeSize);
		
		graphic.setFill(Color.YELLOW);
		graphic.fillOval(eggX * nodeSize, eggY * nodeSize, nodeSize, nodeSize);
		
		for (Node aCanvas : node) {
			graphic.setFill(Color.AQUA);
			graphic.fillRect(aCanvas.x * nodeSize, aCanvas.y * nodeSize, nodeSize, nodeSize);
		}
	}
		
		
	public static void startGame() {
	start: while (true) {
		eggX = rand.nextInt(Frame.rowNum);
		eggY = rand.nextInt(Frame.colNum);
		for (Node aCanvas : node) {
			if (aCanvas.x == eggX && aCanvas.y == eggY) {
				continue start;
			}
		}

		break;
	}

		
	}	
	
	
 /**
     public class Main extends Application{
  
    	
	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox (10);
		Frame frame = new Frame(Frame.rowNum, Frame.colNum);
		root.setPadding(new Insets(10));
		frame.addSnake(new Snake(new Node(Frame.rowNum/2,Frame.colNum/2,5),new Node(Frame.rowNum/2,Frame.colNum/2 - 1,3)));
		

		
		AnimationTimer timer = new AnimationTimer() {
		//	long then = 0;
			@Override
			public void handle(long now) {
		//		if (now - then > 1000000000 / 10) {
		//			then = now;

				}
				
			
			
		};
		timer.start();
		
		
		root.getChildren().add(frame);
		Scene scene = new Scene(root);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SNAKE FRENZY");
		
		primaryStage.show();
	}
*/
	public static void main(String[] arg) {
		//Frame frame = new Frame();
		//frame.run();
		launch(arg);
	}
	}

