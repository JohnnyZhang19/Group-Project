package snake;

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.*;

/**
 * This class is to show the first scene and connect
 * the second scene together.
 * @author Yuzhe Zhou UCID: 30102199
 *
 */
public class Controller extends Application{
	
	/**
	  * To set some button and label in the first scene.
	  */
	@Override
	public void start(Stage primaryStage) throws Exception {
       
		/**
         *Create a  pane at first. 
         */
		Pane border = new Pane();
		
		MenuItem lv1 = new MenuItem(" lv 1");
        MenuItem lv2 = new MenuItem(" lv 2");
        MenuItem lv3 = new MenuItem(" lv 3");

        SplitMenuButton menubutton = new SplitMenuButton();
        
        /**
         * Add BGM in game.
         */
        String musicplay = "src/snake/BGM.mp3";
		Media bgm = new Media(new File(musicplay).toURI().toString());  
	    MediaPlayer mediaPlayer = new MediaPlayer(bgm);  
	    mediaPlayer.setAutoPlay(true); 
        
	    /**
         * When user click different level, the speed of snake will 
         * be different
         */
        lv1.setOnAction(actionEvent ->  {
            GameView.userChooseLevel = 0;
            GameView.speed = 5;
        menubutton.setText("selected level 1");
        });
        lv2.setOnAction(actionEvent ->  {
            GameView.userChooseLevel = 1;
            GameView.speed = 10;
        menubutton.setText("selected level 2");
        });
        lv3.setOnAction(actionEvent ->  {
            GameView.userChooseLevel = 2;
            GameView.speed = 15;
        menubutton.setText("selected level 3");
        });
        
        menubutton.setText("select level");
        menubutton.getItems().addAll(lv1, lv2, lv3);
        menubutton.setLayoutX(15*6);
        menubutton.setLayoutY(13*6);

        /**
         * Create start button, when click this button 
         * interface will switch to the second scene.
         */
        Button startButton = new Button("S T A R T !!");
        startButton.setTextFill(Color.RED);
        startButton.setOnAction(actionEvent ->  {
        	
        	/**
             * Call the start method in GameView class to switch the 
             * scene and try and catch the unexpected Exception 
             * when we call start method
             */
        	try{
            GameView gameView = new GameView();
            gameView.start(primaryStage);
            }
            catch (Exception e){
                    e.printStackTrace();
            }
            }
        );
        
        /**
         * Create a help button, the scene will switch to a Rule scene 
         * when click the help button.
         */
        Button helpButton = new Button("HELP");
        helpButton.setTextFill(Color.RED);
        helpButton.setOnAction(actionEvent ->  {
            try{
            helpView roles = new helpView();
            roles.start(primaryStage);
            }
            catch (Exception e){
                    e.printStackTrace();
            }
            }
        );
        
        /**
         * Set the location, height, and width of the menu button start button and help button. 
         */
        menubutton.relocate(GameView.rowNum * GameView.nodeSize/2  - 60, GameView.colNum * GameView.nodeSize/2);
        menubutton.setMinWidth(120);
        menubutton.setMinHeight(30);
        startButton.relocate(GameView.rowNum * GameView.nodeSize/2 -60 , GameView.colNum * GameView.nodeSize/2 + 70);
        startButton.setMinWidth(120);
        startButton.setMinHeight(30);
        helpButton.relocate(GameView.rowNum * GameView.nodeSize/2 - 20 , GameView.colNum * GameView.nodeSize/2 + 150);

        /**
		 * Insert an image.
		 */
		Canvas aCanvas = new Canvas(GameView.rowNum * GameView.nodeSize, GameView.colNum * GameView.nodeSize);
        GraphicsContext graphic = aCanvas.getGraphicsContext2D();
        Image background = new Image("https://cdn.pixabay.com/photo/2015/06/19/21/24/the-road-815297_1280.jpg");
        graphic.drawImage(background,0,0,GameView.rowNum * GameView.nodeSize, GameView.colNum * GameView.nodeSize);
       
        /**
         * Set a label shows "Levels: " before the menu button.
         */
        Label label = new Label("Levels: ");
        label.setFont(new Font(label.getFont().getName(), Font.getDefault().getSize() + 10));
        label.relocate(GameView.rowNum * GameView.nodeSize/2 -145, GameView.colNum * GameView.nodeSize/2 - 2);
        label.setTextFill(Color.LIGHTBLUE);
        
        /**
         * Set a label shows "Click here before you play ->" before the help button.
         */
        Label mentionLabel = new Label("Click here before you play->");
        mentionLabel.setFont(new Font(label.getFont().getName(), Font.getDefault().getSize() + 10));
        mentionLabel.relocate(GameView.rowNum * GameView.nodeSize/2 -280, GameView.colNum * GameView.nodeSize/2 + 150);
        mentionLabel.setTextFill(Color.RED);
        mentionLabel.setFont(new Font(19));
        
        /**
         * Set a label shows "Snake Frenzy" 
         */
        Label titleLabel = new Label("Snake Frenzy");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.getDefault().getSize() + 30));
        /**
         * set the effect of the title.
         */
        Reflection reflect = new Reflection();
        reflect.setFraction(1.0);
        titleLabel.setEffect(reflect);
        titleLabel.relocate(GameView.rowNum*GameView.nodeSize/2 -210, GameView.colNum*GameView.nodeSize/2 -170);
        titleLabel.setTextFill(Color.RED);
        titleLabel.setFont(new Font(70));
        
        /**
         * Add all the buttons and labels above the in the pane.
         */
		border.getChildren().add(aCanvas);
        border.getChildren().add(menubutton);
        border.getChildren().add(label);
        border.getChildren().add(titleLabel);
        border.getChildren().add(startButton);
        border.getChildren().add(helpButton);
        border.getChildren().add(mentionLabel);

        /**
         * Show the pane in this scene.
         */
        Scene menuscene = new Scene(border, GameView.rowNum * GameView.nodeSize, GameView.colNum * GameView.nodeSize);
        primaryStage.setScene(menuscene);
		primaryStage.setTitle("SNAKE FRENZY");
		primaryStage.show();	
	}
	
	/**
	 * Launch this application.
	 * @param arg
	 */
	public static void main(String[] arg) {
		launch(arg);
	}
	

}
