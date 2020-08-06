
package application;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import application.View;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameView extends View{
	static Dir direction = Dir.left;
	static int eggX;
	static int eggY;
	static int obsX;
	static int obsY;
	public static int actualRowNum = 28;
	public static int actualColNum = 28;
	public static int rowNum = actualRowNum+2;
	public static int colNum = actualColNum+2;
	static LinkedList<Node> snake = new LinkedList<>();
	public static int nodeSize = 20;
	static int score = 0;
	static int listLength = 10;
	static int speed;
	static List<Node> obstacle = new ArrayList<>();
	static boolean gameOver = false;
	static int userChooseLevel;
	

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
        ///*
		VBox root = new VBox();
		Canvas aCanvas = new Canvas(rowNum * nodeSize, colNum * nodeSize);
	
		root.getChildren().addAll(aCanvas);
                System.out.println("in finishe select menu..");
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
				
				//userChooseLevel = 0;
				if (userChooseLevel == 0) speed = 5;
				if (userChooseLevel == 1) speed = 10;
				if (userChooseLevel == 2) speed = 15;
				if (now - lastSceen > 1000000000 / speed) {
					lastSceen = now;
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
        //*/
	}
	
	public static void screen(GraphicsContext graphic) {
		Frame frame = new Frame();
		if (gameOver) {
			graphic.setFill(Color.CRIMSON);
			graphic.setFont(new Font("", 50));
			graphic.fillText("GAME OVER", 100, 250);
			return;
		}
		
		
		/**
		 * for snake move, when snake move, replace the node to the next node
		 */
		for (int i = snake.size() - 1; i >= 1; i--) {
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}
		
		/**
		 * for the direction, because the left up corner is (0,0), the right bottom is (rowNum, colNum), both x and y are positive.
		 * when the snake move down, y decreasing, x is not changing;
		 * when the snake move up, y increasing, x is not changing;
		 * when the snake move left, x decreasing, y is not changing;
		 * when the snake move right, x increasing, y is not changing.
		 */
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
			snake.addLast(new Node(-1, -1, 3));
			obstacle.add(new Node(obsX, obsY, 4));
			score ++;
			speed ++;
			startGame();
		}

		// self destroy and destory when hit the obstacles
		for (int i = 1; i < snake.size(); i++) {
			if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
				gameOver = true;
			}
			for(int j = 0; j < obstacle.size(); j ++) {
				if(snake.get(i).x == obstacle.get(j).x && snake.get(i).y == obstacle.get(j).y) {
					gameOver = true;
				}
			}
		}
		
		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0,  rowNum * nodeSize, colNum * nodeSize);

		graphic.setFill(Color.YELLOW);
		graphic.fillOval(eggX * nodeSize, eggY * nodeSize, nodeSize, nodeSize);
		
		graphic.setFill(Color.RED);
		graphic.fillOval(snake.get(0).x * nodeSize, snake.get(0).y * nodeSize, nodeSize, nodeSize);

		for (int i = 1; i < snake.size(); i ++) {
			graphic.setFill(Color.CORNFLOWERBLUE);
			graphic.fillOval(snake.get(i).x * nodeSize, snake.get(i).y * nodeSize, nodeSize, nodeSize);
		}
		
		for(Node obs : obstacle) {
			graphic.setFill(Color.DARKGRAY);
			graphic.fillRect(obs.x * nodeSize, obs.y * nodeSize, nodeSize, nodeSize);
		}
		
		graphic.setFill(Color.GOLD);
		graphic.setFont(new Font("", 20));
		graphic.fillText("Score: " + score, actualRowNum * (nodeSize - 10), actualColNum * (nodeSize - 10));
	}
		
		
	public static void startGame() {
		start: while (true) {
			eggX = (int)(Math.random()*(rowNum-2)+2);
			eggY = (int)(Math.random()*(colNum-2)+2);
			obsX = (int)(Math.random()*(rowNum-2)+2);
			obsY = (int)(Math.random()*(colNum-2)+2);
			for (Node aCanvas : snake) {
				if (aCanvas.x == eggX && aCanvas.y == eggY) {
					continue start;
				}
			}
			
			break;
	}
	}	

	
}
