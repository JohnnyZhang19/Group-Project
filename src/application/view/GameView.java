package application.view;

import static application.FrameWork.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import application.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import snake.Egg;
import snake.Frame;
import snake.Obstacles;
import snake.Snake;

public class GameView extends View{
	static Dir direction = Dir.left;
	static int eggX;
	static int eggY;
	static Random rand = new Random();
	public static int actualRowNum = 28;
	public static int actualColNum = 28;
	public static int rowNum = actualRowNum+2;
	public static int colNum = actualColNum+2;
	static List<Node> snake = new ArrayList<>();
	public static int nodeSize = 20;
//	public Snake aSnake = new Snake();
//	public Egg egg = new Egg();
//	public Obstacles obs = new Obstacles();
	static int score = 0;
static //	int listLength = 10;
	int speed;
//	Node[] obsList = new Node[listLength];
//	static List<Node> obstacle = new ArrayList<>();
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
	public void onLaunch() {
		startGame();
		Canvas aCanvas = new Canvas(rowNum * nodeSize, colNum * nodeSize);
		VBox root = new VBox(aCanvas);
		GraphicsContext graphic = aCanvas.getGraphicsContext2D();
		
		

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
				
				userChooseLevel = 0;
				if (userChooseLevel == 0) speed = 5;
				if (userChooseLevel == 1) speed = 10;
				if (userChooseLevel == 2) speed = 15;
				if (now - lastSceen > 1000000000 / speed) {
					lastSceen = now;
					screen(graphic);
				}
			}
			

		}.start();

		Scene scene = app.getScene();
		
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
		app.setScene(scene);
		getChildren().addAll(root);
	}
	
	public static void screen(GraphicsContext graphic) {
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
			score ++;
			startGame();
		}

		// self destroy
		for (int i = 1; i < snake.size(); i++) {
			if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
				gameOver = true;
			}
		}
		
		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0,  rowNum * nodeSize, colNum * nodeSize);

		graphic.setFill(Color.YELLOW);
		graphic.fillOval(eggX * nodeSize, eggY * nodeSize, nodeSize, nodeSize);

		for (Node aCanvas : snake) {
			
			graphic.setFill(Color.AQUA);
			graphic.fillRect(aCanvas.x * nodeSize, aCanvas.y * nodeSize, nodeSize, nodeSize);
		}
		
		graphic.setFill(Color.GOLD);
		graphic.setFont(new Font("", 30));
		graphic.fillText("Score: " + score, actualRowNum * (nodeSize - 18), actualColNum * (nodeSize - 18));
	}
	public static void startGame() {
		start: while (true) {
			eggX = rand.nextInt(actualRowNum);
			eggY = rand.nextInt(actualColNum);
			for (Node aCanvas : snake) {
				if (aCanvas.x == eggX && aCanvas.y == eggY) {
					continue start;
				}
			}

			break;
		}
	}
}






















