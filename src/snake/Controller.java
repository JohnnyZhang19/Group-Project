package application;

import java.util.ArrayList;
import java.util.LinkedList;
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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

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
       
		MenuItem lv1 = new MenuItem(" lv 1");
        MenuItem lv2 = new MenuItem(" lv 2");
        MenuItem lv3 = new MenuItem(" lv 3");

        SplitMenuButton menubutton = new SplitMenuButton();

        /**
         * When user click different level, the speed of snake will 
         * be different
         */
        lv1.setOnAction(actionEvent ->  {
            GUISnake.userChooseLevel = 0;
        menubutton.setText("selected level 1");
        });
        lv2.setOnAction(actionEvent ->  {
            GUISnake.userChooseLevel = 1;
        menubutton.setText("selected level 2");
        });
        lv3.setOnAction(actionEvent ->  {
            GUISnake.userChooseLevel = 2;
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
        Button startButton = new Button("START!!");
        startButton.setTextFill(Color.RED);
        startButton.setOnAction(actionEvent ->  {
            try{
            GUISnake s = new GUISnake();
            s.start(primaryStage);
            }
            catch (Exception e){
                    e.printStackTrace();
            }
            }
        );
        Pane border = new Pane();

        menubutton.relocate(GUISnake.rowNum * GUISnake.nodeSize/2  - 35, GUISnake.colNum * GUISnake.nodeSize/2);
        startButton.relocate(GUISnake.rowNum * GUISnake.nodeSize/2 -35 , GUISnake.colNum * GUISnake.nodeSize/2 + 50);
        startButton.setMinWidth(80);
        startButton.setMinHeight(30);


		Canvas aCanvas = new Canvas(GUISnake.rowNum * GUISnake.nodeSize, GUISnake.colNum * GUISnake.nodeSize);

        Label label = new Label("Levels: ");
        label.setLayoutX(GUISnake.rowNum * GUISnake.nodeSize/2 -80);
        label.setLayoutY(GUISnake.colNum * GUISnake.nodeSize/2 );
        label.setTextFill(Color.LIGHTBLUE);
        
        Label titleLabel = new Label("Snake Frenzy");
        titleLabel.setLayoutX(GUISnake.rowNum*GUISnake.nodeSize/2 -40);
        titleLabel.setLayoutY(GUISnake.colNum*GUISnake.nodeSize/2 -70);
        titleLabel.setTextFill(Color.RED);
	
		border.getChildren().addAll(aCanvas);
        border.getChildren().add(menubutton);
        border.getChildren().add(label);
        border.getChildren().add(titleLabel);
        border.getChildren().add(startButton);

		/**
		 * Insert an image.
		 */
        GraphicsContext graphic = aCanvas.getGraphicsContext2D();
        Image background = new Image("https://cdn.pixabay.com/photo/2015/06/19/21/24/the-road-815297_1280.jpg");
        graphic.drawImage(background,0,0,GUISnake.rowNum * GUISnake.nodeSize, GUISnake.colNum * GUISnake.nodeSize);

        Scene menuscene = new Scene(border, GUISnake.rowNum * GUISnake.nodeSize, GUISnake.colNum * GUISnake.nodeSize);
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
