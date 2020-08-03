package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class Controller extends Frame{

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label score;

	@FXML
	private ChoiceBox<String> level;

	@FXML
	private Button start;
	
	private int userChooseLevel = 0;

	/**
	 * To start the game.
	 * @param event click the button.
	 */
	@FXML
	void toStart(ActionEvent event) {
		run();
	}
	
	/**
	 * Set the option in the level choiceBox and let user choose.
	 */
	public void getLevel() {
		String[] lev = {"Easy", "Medium", "Difficult"};
		level.setItems(FXCollections.observableArrayList(lev));
		level.getSelectionModel().selectedIndexProperty().addListener(
				new ChangeListener<Number>() {

					@Override
					public void changed(ObservableValue ob, Number old, Number newValue) {
						userChooseLevel = newValue.intValue();
						if(userChooseLevel == 0) {
							setListLength(10);
							}
						if(userChooseLevel == 1) {
							setListLength(15);
						}
						if(userChooseLevel == 2) {
							setListLength(20);
						}
					}
				
				});
	}
	
	/**
	 * Set the score which get from Frame in this score.
	 */
	public void score() {
		score.setText("" + getScore());
	}
	
	/**
	 * initialize the GUI.
	 */
	@FXML
	void initialize() {
		assert score != null : "fx:id=\"score\" was not injected: check your FXML file 'Button.fxml'.";
		assert level != null : "fx:id=\"level\" was not injected: check your FXML file 'Button.fxml'.";
		assert start != null : "fx:id=\"start\" was not injected: check your FXML file 'Button.fxml'.";

	}
}


