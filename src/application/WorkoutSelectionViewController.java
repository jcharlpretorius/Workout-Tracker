package application;

import java.io.IOException;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.geometry.Pos;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class WorkoutSelectionViewController {
	private Stage applicationStage;
	private Workout workout = new Workout();
//	private Parent root;
//	private Scene workoutSelectionScene = applicationStage.getScene();

	//	ArrayList<StrengthExercise> allExercises = new ArrayList<StrengthExercise>();
    @FXML
    private ChoiceBox<Integer> numberOfExercisesChoiceBox;
    
    @FXML
    private ChoiceBox<StrengthExercise> exerciseChoices = new ChoiceBox<StrengthExercise>();
    
    @FXML
    private void exitWorkout(Scene workoutSelectionScene) {
    	// change scene back to work out selection scene
    	
    	applicationStage.setScene(workoutSelectionScene);
    }

    @FXML
    void getExercises(ActionEvent event) throws IOException {
    	// remove throws IOException if no longer injecting controller
    	Scene workoutSelection = applicationStage.getScene();
    	VBox contents = new VBox();
//    	contents.setSpacing(10);
//    	contents.setAlignment(Pos.CENTER); // possible alignment- make it look nice later
    	// create hashmap to store exercise types chosen and number of sets
    	//HashMap<String, ArrayList> later create this and pass into workout summary?
    	
    	ArrayList<String> chosenExercises = new ArrayList<String>();
    	ArrayList<TextField> setsTextFields = new ArrayList<TextField>();
    	
    	// tabs in label should be removed once the layout is changed to look nice
    	Label exerciseChoiceLabel = new Label("Exercise \t Number of Sets \t"); 
    	contents.getChildren().add(exerciseChoiceLabel);
    	
    	int numberOfExercises = numberOfExercisesChoiceBox.getValue();
    	
    	int rowCounter = 0;
    	while(rowCounter < numberOfExercises) {
    		HBox exerciseRow = new HBox();
    		
    		ChoiceBox<String> choiceBoxOptions = new ChoiceBox<String>();
    		choiceBoxOptions.getItems().addAll(createExerciseArrayList());
    		choiceBoxOptions.getSelectionModel().select(0); // sets default value in choiceBox

    		TextField numberOfSetsTextfield = new TextField(); // should only take type int
    		setsTextFields.add(numberOfSetsTextfield); // unused arrayList, maybe needed for summary?
    		
    		Button startExercise = new Button("Start Exercise");
    		
    		
    		
    		// need to validate the input of the textfields so that they only allow integers
    		// probably pull the input validation into a class like the "Grade" class in the gradeCalculator
    		// other option is to use integer choicebox for number of sets, but this way makes the project look better
    		
//    		int numberOfSets = Integer.parseInt(numberOfSetsTextfield.getText()); // this breaks the code when passing it as an argument, don't know why
    		int exerciseNumber = rowCounter + 1;

    		startExercise.setOnAction(startExerciseEvent -> {
    			ExerciseSets exercise = new ExerciseSets(choiceBoxOptions.getValue(), Integer.parseInt(numberOfSetsTextfield.getText()), exerciseNumber);
    			int numberOfSets = Integer.parseInt(numberOfSetsTextfield.getText());    			
    			getRepsAndWeight(applicationStage.getScene(), exercise);
    		});
    		
    		exerciseRow.getChildren().addAll(choiceBoxOptions, numberOfSetsTextfield, startExercise);
    		contents.getChildren().add(exerciseRow);
    		rowCounter++;
    	}
    	
    	HBox buttonBox = new HBox();
    	// do calculations and go to summary window
    	Button finishWorkoutButton = new Button("Finish Workout");
    	// this should go to the workout summary scene, create a new method for that
    	finishWorkoutButton.setOnAction(doneEvent -> finishWorkout());// call some function
    	
    	// go back to the workout selection window
    	Button exitWorkout = new Button("Exit Workout");
//    	exitWorkout.setOnAction(exitEvent -> exitWorkout(workoutSelection));
    	exitWorkout.setOnAction(exitEvent -> exitWorkout(workoutSelection));
    	buttonBox.getChildren().addAll(exitWorkout, finishWorkoutButton);
    	contents.getChildren().add(buttonBox);
    	
    	// create a new scene
    	Scene exerciseSelectionScene = new Scene(contents);
    	applicationStage.setScene(exerciseSelectionScene);
    	
    }
    
    void getRepsAndWeight(Scene exerciseSelectionScene, ExerciseSets exercise) {
    	// get the number of repetitions and weight lifted for each set of a particular exercise
    	// add this data into an arrayList of Strength exercises, maybe use hashmap with key being exercise name?
    	// if there is enough code duplication then maybe pull set creation into another method?   
    	// add label with the name of the exercises, ie "Squats"
    	VBox allRows = new VBox();
    	Label repsAndWeightHeaderLabel = new Label("Sets \t\t\t\t\t\t Weight (lbs)"); // maybe split these titles and add separately to a HBox?
    	allRows.getChildren().add(repsAndWeightHeaderLabel);
    	ArrayList<TextField> repsTextFields = new ArrayList<TextField>();
    	ArrayList<TextField> weightTextFields = new ArrayList<TextField>();

    	int rowCounter = 0;
    	while(rowCounter < exercise.getNumberOfSets()) {
    		HBox setsRow = new HBox();
    		setsRow.setSpacing(10.0);
    		
    		Label setLabel = new Label("Set " + (rowCounter + 1));
    		TextField reps = new TextField();
    		reps.setPromptText("Reps"); // these prompt text might be uneccessary
    		repsTextFields.add(reps);
    		Label xLabel = new Label("X");
    		// how to set font style: https://coderslegacy.com/java/javafx-font/
    		xLabel.setFont(Font.font("System Bold", FontPosture.REGULAR, 20));
    		TextField weight = new TextField();
    		weight.setPromptText("Weight (lbs)");
    		weightTextFields.add(weight);
    	
    		setsRow.getChildren().addAll(setLabel, reps, xLabel, weight);
    		
    		allRows.getChildren().add(setsRow);
    		rowCounter++;
    	}
    	
    	Button doneButton = new Button("Done");

    	doneButton.setOnAction(doneEvent -> storeSets(exerciseSelectionScene, exercise, repsTextFields, weightTextFields));
    	// need some kind of method for switching back to the exerciseSelection scene
    	allRows.getChildren().add(doneButton);
    	
    	Scene repsAndWeights = new Scene(allRows);
    	applicationStage.setScene(repsAndWeights);
    	 
    }
    
    void storeSets(Scene exerciseSelectionScene, ExerciseSets exercise, ArrayList<TextField> repsTextFields, ArrayList<TextField> weightTextFields) {
    	// method called by getRepsAndWeight to store elements in arrayList and switch back the scene
    	// change name depending on what this does.
    	// create exercise objects
    	ArrayList<StrengthExercise> exercisesDone = new ArrayList<StrengthExercise>();
    	
   
    	// create StrengthExercise objects from text field inputs and add them to the ArrayList of all exercises 
    	for (int i = 0; i < repsTextFields.size(); i++) {
    		int reps = Integer.parseInt(repsTextFields.get(i).getText());
    		double weight = Double.parseDouble(weightTextFields.get(i).getText());
    		StrengthExercise se = new StrengthExercise(exercise.getExerciseName(), reps, weight);
    		exercisesDone.add(se);
    	}
    	
    	workout.setAllExercises(exercise.getExerciseNumber(), exercisesDone);
//    	System.out.println(workout.getAllExercises().size());
//    	System.out.println(workout.getAllExercises());
//    	System.out.println(workout.getAllExercises().get(exerciseNumber));
    	
    	// now add the above ArrayList to another ArrayList that will store all of the exercises    	
    	// set the value of allExercises at this index
//    	System.out.println(workout.getAllExercises());
//    	System.out.println(workout.getAllExercises().size());
//    	workout.setAllExercises(exerciseNumber, exercisesDone);
    	
    	// if no errors after input validation...
    	applicationStage.setScene(exerciseSelectionScene);
    }
    
//    @FXML // probably don't need fxml tag here
    public ObservableList<String> createExerciseArrayList() {
    	// temporary solution for populating exercise choices - used in choicebox
    	// pull this out to a method or class later.. probably Workout
    	
    	
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
    	ObservableList<String> exercisesList = FXCollections.observableArrayList(exercises);
    	
    	return exercisesList;
   
    	
//    	ArrayList<StrengthExercise> listOfExercises = new ArrayList<StrengthExercise>();
//    	for (int i = 0; i < exercises.size(); i++) {
//    		// create new strength exercise and add it to the arrayList
//    		listOfExercises.add(new StrengthExercise(exercises.get(i)));
//    	}
////    	System.out.println(listOfExercises);
//    	return listOfExercises;
    }
    
    public void finishWorkout() {
    	// change the scene
    	System.out.println();
    }
    public void setApplicationStage(Stage stage) {
    	this.applicationStage = stage;
    }
    
    public Stage getApplicationStage() {
    	return this.applicationStage;
    }
   
}
