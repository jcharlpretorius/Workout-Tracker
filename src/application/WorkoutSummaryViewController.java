package application;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WorkoutSummaryViewController {
	private Stage applicationStage;
	private HashMap<Integer, ArrayList<StrengthExercise>> allExercises;
	
	@FXML
	private Label workoutSummaryTitleLabel;
	
	public void setAllExercises(HashMap<Integer, ArrayList<StrengthExercise>> workout) {
		this.allExercises = workout;
	}
	
	public void setApplicationStage(Stage stage) {
    	this.applicationStage = stage;
    }
    
    public Stage getApplicationStage() {
    	return this.applicationStage;
    }
    
}
