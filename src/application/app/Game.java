package application.app;

import javafx.application.Application;
import javafx.stage.Stage;

public abstract class Game extends Application{
	
	private App app;
	
	public abstract void onLaunch();
	
	public void onFinish() {
		// do something in subclass
	}
	
	public boolean onExit() {
		// do something in subclass
		return true;
	}
	@Override
	public final void start(Stage primaryStage) throws Exception{
		app = new App(primaryStage);
		app.onLaunch = new App.OnLaunch() {
			
			@Override
			public void handle() {
				onLaunch();
				
			}
		};
		app.onFinish = new App.OnFinish() {
			
			@Override
			public void handle() {
				onFinish();
				
			}
		};
		app.onExit = new App.OnExit() {
			
			@Override
			public boolean handle() {
				onExit();
				return true;
			}
		};
		app.launch();
	}
	
	@Override
	public final void stop() throws Exception {
		app.finish();
	}
}
