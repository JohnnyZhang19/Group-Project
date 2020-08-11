package snake;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class is the scene for the rules of the game.
 * @author Yuzhe Zhou UCID: 30102199
 */
public class helpView extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/**
		 * Create a group to put all the text.
		 */
		Group root = new Group();

		/**
		 * Create a text and set all the properties of the text.
		 */
		Text roles = new Text(30, 40, "This is a test");
		roles.setWrappingWidth(550);
		roles.setFont(new Font(20));
		roles.setFill(Color.WHITE);
		roles.setText("	Welcome to Snake Frenzy!!! I really hope you can have a fantastic experience in this game. "
				+ "You need to choose a level you want to play at first and then click the \"START\" button to"
				+ " stat the game. You should use the direction keys to control the direction of the snake. "
				+ "When the snake eat a coin the length will incease one node and the score will plus one, and"
				+ " the speed of the snake will also rise a little. The blank key can pause the game and press it" 
				+ "once againe to start. \n	Try your best do not touch bombs, otherwise, you will"
				+ " decrease 2 nodes of the length of the snake. When "
				+ "the length lease than 2, game over!! When snake knock the stone and the margine of the scene,"
				+ " game over!! When you manipulate the snake to move in the opposite direction, game over!! Try to survive longer!");
		
		/**
		 * Create a canvas to put the image.
		 */
		Canvas aCanvas = new Canvas(600,500); 
		GraphicsContext graphic = aCanvas.getGraphicsContext2D();
	    Image background = new Image("https://cdn.pixabay.com/photo/2020/07/25/14/23/cliff-5436923_1280.jpg");
	    graphic.drawImage(background,0,0,600,500);
	    
	    /**
	     * Add a return button to return to the start scene.
	     */
	    Button returnButton = new Button("Return");
        returnButton.setTextFill(Color.CORNFLOWERBLUE);
        returnButton.setMinSize(120, 30);
        returnButton.relocate(GameView.rowNum * GameView.nodeSize/2 - 55 , GameView.colNum * GameView.nodeSize/2 + 120);
        returnButton.setTextFill(Color.RED);
        returnButton.setOnAction(actionEvent ->  {
            
        	/**
             * Call the start method in Controller to switch the 
             * scene and try and catch the unexpected Exception 
             * when we call start method
             */
        	try{
            Controller returnToStart = new Controller();
            returnToStart.start(primaryStage);
            }
            catch (Exception e){
                    e.printStackTrace();
            }
            }
        );
        
	    /**
	     * Add all the objects above in the root group.
	     */
        root.getChildren().add(aCanvas);
	    root.getChildren().add(roles);
	    root.getChildren().add(returnButton);
	   
	    /**
	     * Create a scene, put the root group in here and show it.
	     */
	    Scene scene = new Scene(root, 600, 500);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Rules of Snake Frenzy");
	    primaryStage.show();
	}
}



	
	
