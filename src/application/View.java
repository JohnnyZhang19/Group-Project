package application;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class View {
	private final Pane pane;
	
	public View() {
		pane = new StackPane();
	}
	
	public Pane getPane() {
		return pane;
	}
	
	public ObservableList<Node> getChildren(){
		return pane.getChildren();
	}
	
	public abstract void onLaunch();
	
	public void onEnter() {
		
	}
	
	public void onLeave() {
		
	}
	
	public void onFinish() {
		// do something in cubclass
	}
}
