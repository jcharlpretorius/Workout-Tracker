package application;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkoutSelectionController {
	// variable for the controller to access the stage
	Stage applicationStage;

    @FXML
    private ChoiceBox<Integer> numberOfExercisesChoiceBox;

    @FXML
    void getExercises(ActionEvent event) {
    	// get the scene and store it in a variable so you can switch back to it after?
    	// We should create a new scene for the sets/reps/weighs - but for now just go back to main scene
    	Scene workoutSelection = applicationStage.getScene();
    	
    	VBox cont = new VBox();
    	int numberOfExercises = numberOfExercisesChoiceBox.getValue();
    	int rowCounter = 0;
    	//populate an arraylist of strength exercises
    	ArrayList<StrengthExercise> exercisesChosen = new ArrayList<StrengthExercise>();
    	// label for the title/name of boxes
    	Label exerciseChoiceLabel = new Label("Exercise \t Number of Sets \t"); 
    	HBox exerciseRow = new HBox();
    	
    	
    	
    	
    	
    	// create a new scene
    	Scene exerciseSelectionScene = new Scene(new Label("placeholder"));
    	applicationStage.setScene(exerciseSelectionScene);
    	
    }

}
