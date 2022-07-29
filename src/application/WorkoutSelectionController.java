package application;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
    private ChoiceBox<StrengthExercise> exerciseChoices = new ChoiceBox<StrengthExercise>();

    @FXML
    void getExercises(ActionEvent event) {
    	// get the scene and store it in a variable so you can switch back to it after?
    	// We should create a new scene for the sets/reps/weighs - but for now just go back to main scene
    	Scene workoutSelection = applicationStage.getScene();
    	
    	exerciseChoices.getItems().addAll(createExerciseArrayList());
    	
    	VBox contents = new VBox();
//    	contents.setSpacing(10);
//    	contents.setAlignment(Pos.CENTER);
    	// create hashmap to store exercise types chosen and number of sets
    	//HashMap<String, ArrayList> later create this and pass into workout summary?
    	ArrayList<String> chosenExercises = new ArrayList<String>();
    	ArrayList<TextField> setsTextFields = new ArrayList<TextField>();
    	int numberOfExercises = numberOfExercisesChoiceBox.getValue();
    	int rowCounter = 0;
    	while(rowCounter < numberOfExercises) {
    		Label exerciseChoiceLabel = new Label("Exercise \t Number of Sets \t"); 
    		HBox exerciseRow = new HBox();
    		
    		// Create a choicebox and button 
//    		ChoiceBox exerciseChoiceBox = new ChoiceBox(); // how to enter choicebox options?
    		 
    		TextField numberOfSetsTextfield = new TextField(); // should only take type int
    		setsTextFields.add(numberOfSetsTextfield);
    		Button startExercise = new Button("Start Exercise");
    		
    		exerciseRow.getChildren().addAll(exerciseChoices, numberOfSetsTextfield, startExercise);
    		
    		contents.getChildren().add(exerciseRow);
    		rowCounter++;
    	}
    	
    	Button finishWorkoutButton = new Button("Finish Workout");
    	// this should go to the next scene, but just go back for now
//    	finishWorkoutButton.setOnAction(doneEvent -> );// call some function
    	contents.getChildren().add(finishWorkoutButton);
    	
    	
    	//populate an arraylist of strength exercises
//    	ArrayList<StrengthExercise> exercisesChosen = new ArrayList<StrengthExercise>();
//    	ArrayList<ChoiceBox> exercisesChosen = new ArrayList<ChoiceBox>(); 
    	// label for the title/name of boxes

    	// create a new scene
    	Scene exerciseSelectionScene = new Scene(new Label("placeholder"));
    	applicationStage.setScene(exerciseSelectionScene);
    	
    }
    
    @FXML
    ArrayList<StrengthExercise> createExerciseArrayList() {
    	// temporary solution for populating exercise choices - used in choicebox
    	// pull this out to a method or class later..
    	ArrayList<String> exercises = new ArrayList<String>();
    	exercises.add("Squat");
    	exercises.add("Bench Press");
    	exercises.add("Dead Lift");
    	exercises.add("Overhead Press");
    	exercises.add("Barbell Row");
    	exercises.add("Bicep Curl");
    	exercises.add("Tricep Push-downs");
    	exercises.add("Lateral Raises");
    	exercises.add("Pull ups");
    	exercises.add("Dips");
    	
    	ArrayList<StrengthExercise> listOfExercises = new ArrayList<StrengthExercise>();
    	for (int i = 0; i < exercises.size(); i++) {
    		// create new strength exercise and add it to the arrayList
    		listOfExercises.add(new StrengthExercise(exercises.get(i)));
    	}
    	
    	return listOfExercises;
    }

}
