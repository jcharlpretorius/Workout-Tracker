package application;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class WorkoutSummaryViewController {
	private Stage applicationStage;
	private ArrayList<ArrayList<StrengthExercise>> allExercises = new ArrayList<ArrayList<StrengthExercise>>(); // the ArrayList that stores all of the exercises done in the workout
//	private Scene scene;
//	private Parent root;
	
	public void initializeAllExercises(int numberOfExercises) {
		// initialize ArrayList size to the number of exercises selected
		this.allExercises = new ArrayList<ArrayList<StrengthExercise>>(numberOfExercises);
	}
 	
	public void setAllExercises() {
		// 
	}
	public void setApplicationStage(Stage stage) {
    	this.applicationStage = stage;
    }
    
    public Stage getApplicationStage() {
    	return this.applicationStage;
    }
    
}
