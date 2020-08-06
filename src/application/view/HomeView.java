package application.view;

import application.View;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import static application.FrameWork.*;

public class HomeView extends View{
	private Button playBtn;
	private Button exitBtn;
	
	@Override
	public void onLaunch() {
		playBtn = new Button("Start Play");
		playBtn.setOnAction((event) -> {
			app.gotoView("Game");
		});
		
		exitBtn = new Button("Exit Game");
		exitBtn.setOnAction((even) ->{
			app.exit();
		});
		
		VBox box = new VBox(playBtn, exitBtn);
		box.setAlignment(Pos.CENTER);
		box.setSpacing(20);
		
		getChildren().add(box);
		
		
	}
	
	
}
