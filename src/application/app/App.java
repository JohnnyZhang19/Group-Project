 package application.app;

import java.util.HashMap;

import application.FrameWork;
import application.View;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App {
	
	private Stage stage;
	private Scene scene;
	private Pane root;
	
	private final HashMap<String, View> viewMap;
	private final ObjectProperty<View> currentView;
	
	OnLaunch onLaunch;
	OnExit onExit;
	OnFinish onFinish;
	
	public App(Stage stage) {
		this.stage = stage;
		
		root = new StackPane();
		scene = new Scene(root);
		stage.setScene(scene);
		
		viewMap = new HashMap<>();
		currentView = new SimpleObjectProperty<>();
		
		initFramework();
		initApp();
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	private final void initFramework() {
		FrameWork.app = this;
	}
	private final void initApp() {
		stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, (event) -> {
			if(onExit != null && !onExit.handle()) {
				event.consume();
			}
		});
		/**
		 * obervable, oldvalue, newvalue
		 */
		currentView.addListener((o, ov, nv) -> {
			if(ov != null) {
				ov.onLeave();
				root.getChildren().remove(ov.getPane());
			}
			if(nv != null) {
				root.getChildren().add(nv.getPane());
				nv.onEnter();
			}
		});
	}

	public Stage getStage() {
		return stage;
	}

	public Scene getScene() {
		return scene;
	}
	
	public String getTitle() {
		return stage.getTitle();
	}
	
	public void setTitle(String title) {
		stage.setTitle(title);
	}
	
	public View getView(String name) {
		return viewMap.get(name);
	}
	
	public void regView(String name, View view) {
		viewMap.put(name, view);
	}
	
	public void unregView(String name) {
		viewMap.remove(name);
	}
	
	public View getCurrentView() {
		return currentView.get();
	}
	
	public ReadOnlyObjectProperty<View> currObjectProperty(){
		return currentView;
	}
	
	public void gotoView(String name) {
		View view = viewMap.get(name);
		
		if(view != null) {
			currentView.set(view);
		}
	}
	
	void launch() {
		if (onLaunch != null) {
			onLaunch.handle();
		}
		
		for(View view: viewMap.values()) {
			view.onLaunch();
		}
		
		stage.requestFocus();
		stage.show();
	}
	
	void finish() {
		if(onFinish != null) {
			onFinish.handle();
		}
		for(View view : viewMap.values()) {
			view.onFinish();
		}
	}
	
	public void exit() {
		Platform.exit();
	}
	
	static interface OnLaunch{
		void handle();
	}
	
	static interface OnFinish{
		void handle();
	}
	
	static interface OnExit{
		boolean handle();
	}

	
}

















