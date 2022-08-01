package application;

import java.io.IOException;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class WorkoutSelectionViewController {
	private Stage applicationStage;
	private Workout workout = new Workout();

    @FXML
    private ChoiceBox<Integer> numberOfExercisesChoiceBox;
    
    @FXML
    private ChoiceBox<StrengthExercise> exerciseChoices = new ChoiceBox<StrengthExercise>();
    
    @FXML
    /**
     * Switch the scene back to the workout selection scene
     * @param workoutSelectionScene a scene that holds the previous scene 
     * (the workout selection scene) so that the scene can be switched back to that scene
     */
    private void exitWorkout(Scene workoutSelectionScene) {
    	// change scene back to work out selection scene
    	applicationStage.setScene(workoutSelectionScene);
    }

    @FXML
   /**
    * Creates a scene based on the number of exercises selected in the workout selection scene
    * to get the types of exercises and number of sets the user wants to perform
    * @param event on action of "Select Exercises" button press in the workout selection scene
    */
    void getExercises(ActionEvent event) {
    	Scene workoutSelection = applicationStage.getScene();
    	VBox contents = new VBox();
    	contents.setPadding(new Insets(20, 20, 20, 20));
//    	ArrayList<String> chosenExercises = new ArrayList<String>(); // unused ArrayList
    	ArrayList<TextField> setsTextFields = new ArrayList<TextField>();
    	
    	// Change this to use separate labels instead of tabs to adjust layout
    	Label exerciseChoiceLabel = new Label("Exercise \t\t\t\t Number of Sets"); 
    	contents.getChildren().add(exerciseChoiceLabel);
    	
    	int numberOfExercises = numberOfExercisesChoiceBox.getValue();
    	
    	// initializes the size of the allExercises ArrayList
    	workout.setNumberOfExercises(numberOfExercises);
    	
    	// A loop for creating elements of the scene for getting the exercise choices and number of sets desired
    	int rowCounter = 0;
    	while(rowCounter < numberOfExercises) {
    		HBox exerciseRow = new HBox();
    		exerciseRow.setPadding(new Insets(0, 0, 2, 0));
    		
    		// Create ChoiceBoxes containing a list of exercise choices
    		ChoiceBox<String> choiceBoxOptions = new ChoiceBox<String>();
    		choiceBoxOptions.getItems().addAll(createExerciseArrayList());
    		choiceBoxOptions.getSelectionModel().select(0); // sets default value in choiceBox
    		
    		TextField numberOfSetsTextfield = new TextField(); // should only take type int
    		numberOfSetsTextfield.setPrefWidth(80);
    		numberOfSetsTextfield.setAlignment(Pos.CENTER);
    		setsTextFields.add(numberOfSetsTextfield); 
    		
    		Button startExercise = new Button("Start Exercise");
	
    		// need to validate the input of the textfields so that they only allow integers and choicebox is not empty
    		// probably pull the input validation into another class or method 
    		int exerciseNumber = rowCounter + 1;
    		startExercise.setOnAction(startExerciseEvent -> {
    			int numberOfSets = Integer.parseInt(numberOfSetsTextfield.getText());    			
    			ExerciseSets exercise = new ExerciseSets(choiceBoxOptions.getValue(), numberOfSets, exerciseNumber);
    			getRepsAndWeight(applicationStage.getScene(), exercise);
    		});
    		
    		exerciseRow.getChildren().addAll(choiceBoxOptions, numberOfSetsTextfield, startExercise);
    		contents.getChildren().add(exerciseRow);
    		rowCounter++;
    	}
    	
    	HBox buttonBox = new HBox();
    	
    	// Button calls finishWorkout function to do calculations and switch to workout summary window
    	Button finishWorkoutButton = new Button("Finish Workout");
    	finishWorkoutButton.setOnAction(doneEvent -> finishWorkout());
    	
    	// Button to switch back to the workout selection window. 
    	Button exitWorkout = new Button("Exit Workout");
    	exitWorkout.setOnAction(exitEvent -> exitWorkout(workoutSelection));
    	buttonBox.getChildren().addAll(exitWorkout, finishWorkoutButton);
    	contents.getChildren().add(buttonBox);
    	
    	// create and set the new scene 
    	Scene exerciseSelectionScene = new Scene(contents);
    	applicationStage.setScene(exerciseSelectionScene);
    	
    }
    
    /**
     * Creates and switches to a Scene to get the user's input for the number of repetitions
     * and weight lifted for each set based on the type of exercise and number of sets the
     * user enters 
     * 
     * @param exerciseSelectionScene the Scene that will be switched back to, passed as an argument to storeSets()
     * @param exercise An ExerciseSet object that holds the values of the number of sets and the exercise name, passed 
     * as an argument to storeSets()
     */
    void getRepsAndWeight(Scene exerciseSelectionScene, ExerciseSets exercise) {
    	VBox allRows = new VBox();
//    	Inset labelMargins = new Insets(20, 20, 20, 20)); // if insets are reused the could be made variables
    	// values refer to: (top, right, bottom, left)
    	allRows.setPadding(new Insets(20, 20, 20, 20));
    	Label exerciseNameLabel = new Label(exercise.getExerciseName());
    	VBox.setMargin(exerciseNameLabel, new Insets(0, 0, 20, 0));
    	exerciseNameLabel.setFont(Font.font("System Bold", FontPosture.REGULAR, 24));
    	Label repsAndWeightHeaderLabel = new Label("\t\tSets \t\t Weight (lbs)"); // maybe split these titles and add separately to a HBox?
    	allRows.getChildren().addAll(exerciseNameLabel, repsAndWeightHeaderLabel);
    	ArrayList<TextField> repsTextFields = new ArrayList<TextField>();
    	ArrayList<TextField> weightTextFields = new ArrayList<TextField>();

    	int rowCounter = 0;
    	while(rowCounter < exercise.getNumberOfSets()) {
    		HBox setsRow = new HBox();
    		setsRow.setSpacing(10.0);
    		HBox.setMargin(setsRow, new Insets(10, 10, 5, 10));
    		
    		Label setLabel = new Label("Set " + (rowCounter + 1)); // how to prevent row from shifting to the right when set# is > 1 digit?
    		TextField reps = new TextField();
    		reps.setPrefWidth(40);
    		reps.setPromptText("Reps");
    		reps.setAlignment(Pos.CENTER);
    		repsTextFields.add(reps);
    		Label xLabel = new Label("X");
    		// how to set font style: https://coderslegacy.com/java/javafx-font/
    		xLabel.setFont(Font.font("System Bold", FontPosture.REGULAR, 20));
    		TextField weight = new TextField();
    		weight.setPrefWidth(90);
    		weight.setPromptText("Weight (lbs)");
    		weight.setAlignment(Pos.CENTER);
    		weightTextFields.add(weight);
    	
    		setsRow.getChildren().addAll(setLabel, reps, xLabel, weight);
    		
    		allRows.getChildren().add(setsRow);
    		rowCounter++;
    	}
    	
    	Button doneButton = new Button("Done");

    	doneButton.setOnAction(doneEvent -> storeSets(exerciseSelectionScene, exercise, repsTextFields, weightTextFields));
    	allRows.getChildren().add(doneButton);
    	
    	Scene repsAndWeights = new Scene(allRows);
    	applicationStage.setScene(repsAndWeights);
    }
    
    /**
     * Stores the information about the repetitions and weights lifted for each set of the 
     * exercise, then adds that to the data for the entire workout.
     * Then it switches back to the exercise selection scene
     * 
     * @param exerciseSelectionScene the Scene that will be switched back to
     * @param exercise An ExerciseSet object that holds the values of the number of sets and the exercise name
     * @param repsTextFields An ArrayList of TextFields that contains user input integers for the number of repetitions
     * @param weightTextFields An ArrayList of TextFields that contains user input integers for the weight lifted in lbs
     */
    void storeSets(Scene exerciseSelectionScene, ExerciseSets exercise, ArrayList<TextField> repsTextFields, ArrayList<TextField> weightTextFields) {
    	// method called by getRepsAndWeight to store elements in arrayList and switch back the scene
   
    	ArrayList<StrengthExercise> exercisesDone = new ArrayList<StrengthExercise>();
    
    	// create StrengthExercise objects from ArrayLists of TextField inputs and add them to the ArrayList of all exercises 
    	// need to implement input validation on the text fields 
    	for (int i = 0; i < repsTextFields.size(); i++) {
    		int reps = Integer.parseInt(repsTextFields.get(i).getText());
    		int weight = Integer.parseInt(weightTextFields.get(i).getText());
    		StrengthExercise se = new StrengthExercise(exercise.getExerciseName(), reps, weight);
    		exercisesDone.add(se);
    	}
    	
    	// Add the Arraylist of exercises to ArrayList for all the exercises done in the workout
    	workout.setAllExercises(exercise.getExerciseNumber(), exercisesDone);
    	applicationStage.setScene(exerciseSelectionScene);
    }
    
    /**
     * Creates and switch to a scene for the workout summary and displays important information about workout
     * including the total weight lifted, the number of personal records beaten (if any) and 
     * the bests sets done for each exercise
     */
    void finishWorkout() {
    	
    	// calculate values in workout object to be displayed
    	workout.setTotalWeightLifted(); 
    	workout.setBestSets();
    	
    	// create workout summary scene:
    	VBox summary = new VBox();
    	summary.setSpacing(10);
    	summary.setAlignment(Pos.CENTER); 
    	Label summaryTitleLabel = new Label("Workout Summary");
		summaryTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

		// this label could instead be something like: "You completed your nth workout!" - would need workout history file
    	Label congratsLabel = new Label("Congrats, you finished your workout!");
		congratsLabel.setFont(Font.font("System", FontPosture.REGULAR, 16));
		VBox.setMargin(congratsLabel, new Insets(0, 10, 0, 10));

    	VBox summaryContent = new VBox();
    	VBox.setMargin(summaryContent, new Insets(20, 20, 20, 20));
    	Label personRecordsLabel = new Label("***<Placeholder> You set 1 personal record! Squats: 245lbs ***"); // some logic elsewhere, pass in a string variable
    	Label totalWeightLiftedLabel = new Label("Total weight lifted: " + workout.getTotalWeightLifted() + "lbs");
    	Label bestSetsHeaderLabel = new Label("Best sets: ");
    	bestSetsHeaderLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
    	summaryContent.getChildren().addAll(totalWeightLiftedLabel, personRecordsLabel, bestSetsHeaderLabel);
    	
    	// Create labels for best sets
    	ArrayList<Label> bestSetsLabels = new ArrayList<Label>();
    	workout.getBestSets().forEach((exName, strengthExercise) -> {
    		bestSetsLabels.add(new Label(exName + ": " + strengthExercise.getReps() + "x" + strengthExercise.getWeight()+ "lbs"));
    	});
    	for (Label bestSet : bestSetsLabels) {
        	summaryContent.getChildren().add(bestSet);
    	}
  
    	summary.getChildren().addAll(summaryTitleLabel, congratsLabel, summaryContent);
    	
    	// Switch to the workout summary scene
    	Scene workoutSummary = new Scene(summary);
    	applicationStage.setScene(workoutSummary);
    }
    
   /**
    * Returns a list of exercise choices
    * @return
    */
    public ObservableList<String> createExerciseArrayList() {
    	// temporary solution for populating exercise ChoiceBoxes 
    	// pull this out to a method or class later. Or read from a file.

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
    }
    
  
    public void setApplicationStage(Stage stage) {
    	this.applicationStage = stage;
    }
}
