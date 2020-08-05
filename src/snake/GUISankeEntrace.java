package snake;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class GUISankeEntrace extends Application {
	
	public void start(Stage primaryStage) {
	try {
	FXMLLoader loader = new FXMLLoader();
	Scene scene = new Scene(loader.load(new FileInputStream("src/application/Button.fxml")),400,400);
	primaryStage.setTitle("Snake Frenzy");
	primaryStage.setScene(scene);
	primaryStage.show();
	}
	catch(Exception e) { 
		e.printStackTrace(); }
	}


	
	public static void main(String[] args) {
		launch(args);
	}
}
