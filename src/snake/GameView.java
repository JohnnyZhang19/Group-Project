
package snake;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class GameView extends Application{
	static Dir direction = Dir.left;
	static int egg1X;
	static int egg1Y;
	static int egg2X;
	static int egg2Y;
	static int obsX;
	static int obsY;
	public static int actualRowNum = 28;
	public static int actualColNum = 28;
	public static int rowNum = actualRowNum+2;
	public static int colNum = actualColNum+2;
	static LinkedList<Node> snake = new LinkedList<>();
	public static int nodeSize = 20;
	static int score = 0;
	static int speed;
	static List<Node> obstacle = new ArrayList<>();
	static boolean gameOver = false;
	static int userChooseLevel;
	static List<Node> nodes = new ArrayList<Node>();
	static int level = 1;
	static int snakeLengh = 2;
	boolean pause = false; 

	public enum Dir {
		left, right, up, down
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

		AnimationTimer timer = new AnimationTimer() {
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
				
				if (now - lastSceen > 1000000000 / speed) {
					lastSceen = now;
					screen(graphic);
				}

			}

		};
		
			timer.start();
		
		Scene scene = new Scene(root, rowNum * nodeSize, colNum * nodeSize);
		scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
				if (key.getCode() == KeyCode.UP) {
					direction = Dir.up;
				}
				if (key.getCode() == KeyCode.LEFT) {
					direction = Dir.left;
				}
				if (key.getCode() == KeyCode.DOWN) {
					direction = Dir.down;
				}
				if (key.getCode() == KeyCode.RIGHT) {
					direction = Dir.right;
				}
				//  Pause the game
				if (key.getCode() == KeyCode.SPACE) {
					if (pause) {
					pause = false;
					timer.start();	
					}
						
				//  Resume the game
				else {
					pause = true;
					timer.stop();
					graphic.setFill(Color.CRIMSON);
					graphic.setFont(new Font("", 80));
					graphic.fillText("PAUSE", 185, 330); 
					
				}}
			});
		/**
		 * add initial snake
		 */
		snake.add(new Node(rowNum/2,colNum/2,5));
		snake.add(new Node(rowNum/2,colNum/2 - 1,3));


		primaryStage.setScene(scene);
		primaryStage.setTitle("SNAKE FRENZY");
		primaryStage.show();	
        //*/
	}
	
	public static void screen(GraphicsContext graphic) {
		if (gameOver) {
			/**
			 * if game over, will show a big red GAME OVER text in the center of the map.
			 */
			graphic.setFill(Color.CRIMSON);
			graphic.setFont(new Font("", 50));
			graphic.fillText("GAME OVER", 150, 300); 
//			String GameOver = "src/snake/GameOver.mp3";
//			Media over = new Media(new File(GameOver).toURI().toString());  
//			MediaPlayer mediaPlayer = new MediaPlayer(over);  
//			mediaPlayer.setAutoPlay(true);
			return;
		}
		
		
		/**
		 * for snake move, when snake move, replace the node to the index-1 node
		 */
		for (int i = snake.size() - 1; i >= 1; i--) {
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}
		/**
		 * avoid eggs and obstacle appear on snake body.
		 */
		for (int i = 0; i < snake.size(); i++) {
			if((obsX == snake.element().x && obsY == snake.element().y) || (obsX == egg1X && obsY == egg1Y)
					|| (obsX == egg2X && obsY == egg2Y)) {
				obsX = (int)(Math.random()*(rowNum-2)+2);
				obsY = (int)(Math.random()*(colNum-2)+2);
			}else if ((egg1X == snake.element().x && egg1Y == snake.element().y) || (egg1X == egg2X && egg1Y == egg2Y)) {
				egg1X = (int)(Math.random()*(rowNum-2)+2);
				egg1Y = (int)(Math.random()*(colNum-2)+2);
			}else if (egg2X == snake.element().x && egg2Y == snake.element().y) {
				egg2X = (int)(Math.random()*(rowNum-2)+2);
				egg2Y = (int)(Math.random()*(colNum-2)+2);
			}
		}
		
		/**
		 * for the direction, because the left up corner is (0,0), the right bottom is (rowNum, colNum), both x and y are positive.
		 * when the snake move down, y decreasing, x is not changing;
		 * when the snake move up, y increasing, x is not changing;
		 * when the snake move left, x decreasing, y is not changing;
		 * when the snake move right, x increasing, y is not changing.
		 * if 0 < y < height of map, 0 < x < width of map, game over.
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

		// eat Egg, eat one egg score add one, speed increase one, and add one new random obstacle in the map
		if (egg1X == snake.get(0).x && egg1Y == snake.get(0).y) {
			snake.addLast(new Node(-1, -1, 3));
			obstacle.add(new Node(obsX, obsY, 4));
			score ++;
			speed ++;
			snakeLengh ++;
			startGame();
	        String eatEgg = "src/snake/eatEgg.mp3";
			Media eat = new Media(new File(eatEgg).toURI().toString());  
		    MediaPlayer mediaPlayer = new MediaPlayer(eat);  
		    mediaPlayer.setAutoPlay(true); 

			if (score % 5 == 0) {
				level ++;
			}

		}
		if (egg2X == snake.get(0).x && egg2Y == snake.get(0).y) {
			snake.removeLast();
			snake.removeLast();
			obstacle.add(new Node(obsX, obsY, 4));
			obstacle.add(new Node(obsX, obsY, 4));
			speed -= 2;
			snakeLengh -= 2;
			startGame();
	        String eatBomb = "src/snake/eatBomb.mp3";
			Media bomb = new Media(new File(eatBomb).toURI().toString());  
		    MediaPlayer mediaPlayer = new MediaPlayer(bomb);  
		    mediaPlayer.setAutoPlay(true); 
		}

		// self destroy and destroy when hit the obstacles
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
		if (snake.size() < 2) {
			gameOver = true;
		}
		/**
		 * set the background color
		 */
		graphic.setFill(Color.BLACK);
		graphic.fillRect(0, 0,  rowNum * nodeSize, colNum * nodeSize);
//		String BC = "src/snake/GameViewBC.jpg";
//		Image background = new Image(new File(BC).toURI().toString());
//        graphic.drawImage(background,0,0,rowNum * nodeSize, colNum * nodeSize);		
		/**
		 * show the color, location of egg in the map
		 */
        String coin = "src/snake/egg.png";
        Image eggCoin = new Image(new File(coin).toURI().toString());
        graphic.drawImage(eggCoin,egg1X * nodeSize, egg1Y * nodeSize);	
		
        String bomb = "src/snake/bomb.png";
        Image eggBomb = new Image(new File(bomb).toURI().toString());
        graphic.drawImage(eggBomb,egg2X * nodeSize, egg2Y * nodeSize);
		
		/**
		 * set the snake head color
		 */
        if (snake.get(0).x == snake.get(1).x && snake.get(0).y < snake.get(1).y) {
        	String snakeUp = "src/snake/snakeUp.png";
        	Image up = new Image(new File(snakeUp).toURI().toString());
        	graphic.drawImage(up,snake.get(0).x * nodeSize, snake.get(0).y * nodeSize, nodeSize, nodeSize);	
		}
        if (snake.get(0).x == snake.get(1).x && snake.get(0).y > snake.get(1).y) {
        	String snakeDown = "src/snake/snakeDown.png";
	        Image down = new Image(new File(snakeDown).toURI().toString());
	        graphic.drawImage(down,snake.get(0).x * nodeSize, snake.get(0).y * nodeSize, nodeSize, nodeSize);
		}
        if (snake.get(0).x < snake.get(1).x && snake.get(0).y == snake.get(1).y) {
        	String snakeLeft = "src/snake/snakeLeft.png";
	        Image left = new Image(new File(snakeLeft).toURI().toString());
	        graphic.drawImage(left,snake.get(0).x * nodeSize, snake.get(0).y * nodeSize, nodeSize, nodeSize);
		}
        if (snake.get(0).x > snake.get(1).x && snake.get(0).y == snake.get(1).y) {
        	String snakeRight = "src/snake/snakeRight.png";
	        Image right = new Image(new File(snakeRight).toURI().toString());
	        graphic.drawImage(right,snake.get(0).x * nodeSize, snake.get(0).y * nodeSize, nodeSize, nodeSize);
		}
        
		
		/**
		 * show the shape, color, and location of snake body in the map.
		 */
		for (int i = 1; i < snake.size(); i ++) {
			String snakeBody = "src/snake/snakeBody.png";
	        Image body = new Image(new File(snakeBody).toURI().toString());
	        graphic.drawImage(body,snake.get(i).x * nodeSize, snake.get(i).y * nodeSize, nodeSize, nodeSize);
		}
		
		/**
		 * show the shape, location and color for the obstacles
		 */
		for(Node obs : obstacle) {
			String obstacle = "src/snake/obstacle.png";
	        Image obstacles = new Image(new File(obstacle).toURI().toString());
	        graphic.drawImage(obstacles,obs.x * nodeSize, obs.y * nodeSize, nodeSize, nodeSize);	
		}
		/**
		 * set the location, size and color for the score text
		 */
		graphic.setFill(Color.GOLD);
		graphic.setFont(new Font("", 20));
		graphic.fillText("Score: " + score, actualRowNum * (nodeSize - 19.5), actualColNum * (nodeSize - 19));
		
		graphic.setFill(Color.GOLD);
		graphic.setFont(new Font("", 20));
		graphic.fillText("Level: " + level, actualRowNum * (nodeSize - 15), actualColNum * (nodeSize - 19));
		
		graphic.setFill(Color.GOLD);
		graphic.setFont(new Font("", 20));
		graphic.fillText("Snake Length: " + snakeLengh, actualRowNum * (nodeSize - 10.5), actualColNum * (nodeSize - 19));
	}
		
		/**
		 * randomly appear a egg and obstacles in the map, if the location of egg and obstacles has the same location with 
		 * snake body, it will reappear a new egg or obstacles
		 */
	public static void startGame() {
		start: while (true) {
			egg1X = (int)(Math.random()*(rowNum-2)+2);
			egg1Y = (int)(Math.random()*(colNum-2)+2);
			egg2X = (int)(Math.random()*(rowNum-2)+2);
			egg2Y = (int)(Math.random()*(colNum-2)+2);
			obsX = (int)(Math.random()*(rowNum-2)+2);
			obsY = (int)(Math.random()*(colNum-2)+2);

			for (Node aCanvas : snake) {
				if (aCanvas.x == egg1X && aCanvas.y == egg1Y) {
					continue start;
				}
				if (aCanvas.x == obsX && aCanvas.y == obsY) {
					continue start;
				}
				if (aCanvas.x == egg2X && aCanvas.y == egg2Y) {
					continue start;
				}
			}
			
			break;
	}
	}

	
}
