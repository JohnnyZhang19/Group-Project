package application;
	
import static application.FrameWork.*;

import application.app.Game;
import application.view.GameView;
import application.view.HomeView;
import javafx.stage.Stage;


public class Run extends Game {

	@Override
	public void onLaunch() {
		Stage stage = app.getStage();
		
		stage.setTitle("SNAKE FRENZY");
		stage.setWidth(600);
		stage.setHeight(600);
		
		app.regView("Home", new HomeView());
		app.regView("Game", new GameView());
		app.gotoView("Home");
		
	}
	
	@Override
		public void onFinish() {
			System.out.println("finish");
		}
	
	@Override
		public boolean onExit() {
			System.out.println("exit");
			return true;
		}
	
	public static void main(String[] args) {
		launch(args);
	}

}
