package application;

import java.io.IOException;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
	private Parent root;
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
    	
    	
    	// creating an instance of our WorkoutSummaryController:
    	// from https://www.youtube.com/watch?v=wxhGKR3PQpo
//    	FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkoutSummaryView.fxml"));
//    	root = loader.load();
//    	FXMLLoader loader = new FXMLLoader();
//    	loader.setLocation(getClass().getResource("WorkoutSummaryView.fxml"));
//    	Parent root = loader.load();
//    	WorkoutSummaryViewController summaryController = loader.getController();
//    	summaryController.initializeAllExercises(numberOfExercises);
    	
//    	workout.initializeAllExercises(numberOfExercises);
//    	System.out.println(numberOfExercises);
//    	System.out.println(workout.getAllExercises());
//    	System.out.println(workout.getAllExercises().size());
    	
//    	HashMap<Integer, ArrayList> exercisesMap = new HashMap<Integer, ArrayList>(); 
    	int rowCounter = 0;
    	while(rowCounter < numberOfExercises) {
    		HBox exerciseRow = new HBox();
    		ChoiceBox<String> choiceBoxOptions = new ChoiceBox<String>();
    		
    		// This relies on the StrengthExercise toString() method to return only the name of the exercises
    		// find a better way of getting the names of all exercises
    		choiceBoxOptions.getItems().addAll(createExerciseArrayList());
    		
    		TextField numberOfSetsTextfield = new TextField(); // should only take type int
    		setsTextFields.add(numberOfSetsTextfield); // unused arrayList, maybe needed for summary?
    		
    		Button startExercise = new Button("Start Exercise");
    		
    		
    		
    		// need to validate the input of the textfields so that they only allow integers
    		// probably pull the input validation into a class like the "Grade" class in the gradeCalculator
    		// other option is to use integer choicebox for number of sets, but this way makes the project look better
    		
//    		int numberOfSets = Integer.parseInt(numberOfSetsTextfield.getText()); // this breaks the code when passing it as an argument, don't know why
    		int exerciseNumber = rowCounter + 1;
    		String exerciseName = choiceBoxOptions.toString(); /// this is wrong
//    		exercisesMap.put(exerciseNumber, );
    		startExercise.setOnAction(startExerciseEvent -> getRepsAndWeight(applicationStage.getScene(), exerciseName, Integer.parseInt(numberOfSetsTextfield.getText()), exerciseNumber));
    		
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
    
    void getRepsAndWeight(Scene exerciseSelectionScene, String exerciseName, int numberOfSets, int exerciseNumber) {
    	// get the number of repetitions and weight lifted for each set of a particular exercise
    	// add this data into an arrayList of Strength exercises, maybe use hashmap with key being exercise name?
    	// if there is enough code duplication then maybe pull set creation into another method?   
    	
    	VBox allRows = new VBox();
    	Label repsAndWeightHeaderLabel = new Label("Sets \t\t\t\t\t\t Weight (lbs)"); // maybe split these titles and add separately to a HBox?
    	allRows.getChildren().add(repsAndWeightHeaderLabel);
    	ArrayList<TextField> repsTextFields = new ArrayList<TextField>();
    	ArrayList<TextField> weightTextFields = new ArrayList<TextField>();

    	int rowCounter = 0;
    	while(rowCounter < numberOfSets) {
    		HBox setsRow = new HBox();
    		setsRow.setSpacing(10.0);
    		
    		Label setLabel = new Label("Set " + (rowCounter + 1));
    		TextField reps = new TextField();
    		reps.setPromptText("Reps"); // these prompt text might be uneccessary
    		repsTextFields.add(reps);
    		Label xLabel = new Label("X");
    		// how to set font: https://coderslegacy.com/java/javafx-font/
    		xLabel.setFont(Font.font("System Bold", FontPosture.REGULAR, 20));
    		TextField weight = new TextField();
    		weight.setPromptText("Weight (lbs)");
    		weightTextFields.add(weight);
    	
    		setsRow.getChildren().addAll(setLabel, reps, xLabel, weight);
    		
    		allRows.getChildren().add(setsRow);
    		rowCounter++;
    	}
    	
    	Button doneButton = new Button("Done");

    	doneButton.setOnAction(doneEvent -> storeSets(exerciseSelectionScene, exerciseName, repsTextFields, weightTextFields, exerciseNumber));
    	// need some kind of method for switching back to the exerciseSelection scene
    	allRows.getChildren().add(doneButton);
    	
    	Scene repsAndWeights = new Scene(allRows);
    	applicationStage.setScene(repsAndWeights);
    	 
    }
    
    void storeSets(Scene exerciseSelectionScene, String exerciseName, ArrayList<TextField> repsTextFields, ArrayList<TextField> weightTextFields, int exerciseNumber) {
    	// method called by getRepsAndWeight to store elements in arrayList and switch back the scene
    	// change name depending on what this does.
    	// create exercise objects
    	ArrayList<StrengthExercise> exercisesDone = new ArrayList<StrengthExercise>();
    	
   
    	// create StrengthExercise objects from text field inputs and add them to the ArrayList of all exercises 
    	for (int i = 0; i < repsTextFields.size(); i++) {
    		int reps = Integer.parseInt(repsTextFields.get(i).getText());
    		System.out.println(reps);
    		double weight = Double.parseDouble(weightTextFields.get(i).getText());
    		System.out.println(weight);

    		StrengthExercise exercise = new StrengthExercise(exerciseName, reps, weight);
    		System.out.println(exercise.toString(true));
    		exercisesDone.add(exercise);
    	}
    	
    	workout.setAllExercises(exerciseNumber, exercisesDone);
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
    
    @FXML
    ArrayList<String> createExerciseArrayList() {
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
    	
    	return exercises;
    	
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
