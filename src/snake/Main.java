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

public class Main extends Application{
	static Dir direction = Dir.left;
	public int actualRowNum = 30;
	public int actualColNum = 30;
	public int rowNum = actualRowNum+2;
	public int colNum = actualColNum+2;
	List<Node> nodes = new ArrayList<Node>();
	public int nodeSize = 20;
	public Snake snake = new Snake();
	public Egg egg = new Egg();
	public Obstacles obs = new Obstacles();
	int score = 0;
	int listLength = 10;
	Node[] obsList = new Node[listLength];
	
	public enum Dir {
		left, right, up, down
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Frame newFrame = new Frame();
		newFrame.Initialize(); //or refresh or reappear?
		VBox root = new VBox();
		Canvas aCanvas = new Canvas(rowNum * nodeSize, colNum * nodeSize);
		GraphicsContext graphic = aCanvas.getGraphicsContext2D();
		root.getChildren().add(aCanvas);
		Snake newSnake = new Snake();
		final ComboBox level = new ComboBox();
		level.getItems().addAll(
            "Easy",
            "Medium",
            "Difficult"); 
		root.getChildren().addAll(new Label("Select level"), level);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
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
		
		snake = new Snake(new Node(rowNum/2,colNum/2,5),new Node(rowNum/2,colNum/2 - 1,3));
		egg = new Egg(new Node((int)(Math.random()*(rowNum-4)+2),(int)(Math.random()*(colNum-4)+2),2));
		primaryStage.setScene(scene);
		primaryStage.setTitle("SNAKE FRENZY");
		primaryStage.show();	
	}
	
	//background
	graphic.setFill(Color.Black);
	graphic.fillRect(0,0, rowNum * 20, colNum * 20 );
	
	public static void main(String[] arg) {
		//Frame frame = new Frame();
		//frame.run();
		launch(arg);
	}
}
