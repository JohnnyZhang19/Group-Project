package snake;



import java.util.ArrayList;
import java.util.List;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
		Canvas aCanvas = new Canvas(rowNum * nodeSize, colNum * nodeSize);
		GraphicsContext graphic = aCanvas.getGraphicsContext2D();

		root.getChildren().addAll(aCanvas);
		
		new AnimationTimer() {
			long then = 0;

			public void handle(long now) {
	
		//  Different levels for snake:
		//  Level 0: easy, level 1: normal; level 2: hard
		//  We can create as many level as we want depending on the speed
				
				userChooseLevel = 2;
				if (userChooseLevel == 0) speed = 5;
				if (userChooseLevel == 1) speed = 15;
				if (userChooseLevel == 2) speed = 25;
				if (now - then > 1000000000 / speed) {
					then = now;
					screen(graphic);
				}
			}
			

		}.start();

		
		
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
		graphic.fillText("Score:" + frame.getScore(), rowNum * (nodeSize - 10), colNum * (nodeSize - 10));
		
		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0,  rowNum * nodeSize, colNum * nodeSize);
		
		graphic.setFill(Color.YELLOW);
		graphic.fillOval(eggX * nodeSize, eggY * nodeSize, nodeSize, nodeSize);
		
		for (Node aCanvas : snake) {
			graphic.setFill(Color.AQUA);
			graphic.fillRect(aCanvas.x * nodeSize, aCanvas.y * nodeSize, nodeSize, nodeSize);
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

