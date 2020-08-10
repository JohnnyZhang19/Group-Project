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

public class helpView extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Rules of Snake Frenzy");
		Group root = new Group();
		Scene scene = new Scene(root, 600, 500);
		primaryStage.setScene(scene);

		Text roles = new Text(30, 60, "This is a test");
		roles.setWrappingWidth(550);
		roles.setFont(new Font(20));
		roles.setFill(Color.RED);
		roles.setText("	Welcome to Snake Frenzy!!! I really hope you can have a fantastic experience in this game. "
				+ "You need to choose a level you want to play at first and then click the \"START\" button to"
				+ " stat the game. You should use the direction key to control the direction of the snake. "
				+ "When the snake eat a gift the length will incease one node and the score will plus one, and"
				+ " the speed of the snake will also rise a little.\n	Try your best do not touch bombs, otherwise, you will"
				+ " lose mark and decrease 2 nodes of the length of the snake. When "
				+ "the length decline to 0, game over!! When snake knock the stone and the margine of the scene,"
				+ " game over!! When you manipulate the snake to move in the opposite direction, game over!! Try to survive longer!");
		
		Canvas aCanvas = new Canvas(600,500); 
		GraphicsContext graphic = aCanvas.getGraphicsContext2D();
	    Image background = new Image("https://cdn.pixabay.com/photo/2015/06/19/21/24/the-road-815297_1280.jpg");
	    graphic.drawImage(background,0,0,600,500);
	    
	    Button returnButton = new Button("Return");
        returnButton.setTextFill(Color.CORNFLOWERBLUE);
        returnButton.setMinSize(120, 30);
        returnButton.relocate(GameView.rowNum * GameView.nodeSize/2 - 55 , GameView.colNum * GameView.nodeSize/2 + 120);
        returnButton.setTextFill(Color.RED);
        returnButton.setOnAction(actionEvent ->  {
            try{
            Controller returnToStart = new Controller();
            returnToStart.start(primaryStage);
            }
            catch (Exception e){
                    e.printStackTrace();
            }
            }
        );
        
	    root.getChildren().add(aCanvas);
	    root.getChildren().add(roles);
	    root.getChildren().add(returnButton);
		primaryStage.show();
	}
}



	
	
