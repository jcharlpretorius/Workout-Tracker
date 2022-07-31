package application;

import java.io.IOException;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
    void getExercises(ActionEvent event) {
    	Scene workoutSelection = applicationStage.getScene();
    	VBox contents = new VBox();

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
    	finishWorkoutButton.setOnAction(doneEvent -> finishWorkout());
    	
    	// go back to the workout selection window. If this breaks things it could be removed later
    	Button exitWorkout = new Button("Exit Workout");
    	exitWorkout.setOnAction(exitEvent -> exitWorkout(workoutSelection));
    	buttonBox.getChildren().addAll(exitWorkout, finishWorkoutButton);
    	contents.getChildren().add(buttonBox);
    	
    	// create and set the new scene 
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
    		int weight = Integer.parseInt(weightTextFields.get(i).getText());
    		StrengthExercise se = new StrengthExercise(exercise.getExerciseName(), reps, weight);
    		exercisesDone.add(se);
    	}
    	
    	workout.setAllExercises(exercise.getExerciseNumber(), exercisesDone);

    	// if no errors after input validation...
    	applicationStage.setScene(exerciseSelectionScene);
    }
    
    void finishWorkout() {
    	// change the scene to the workout Summary 
    	// make sure to validate input here as well. all exercises should have a valid number entered in the sets textField
    	// and the Workout class should have its allExercises HashMap populated for that exercise number
    	
    	// create workout summary scene:
    		// most values are placeholders
    	VBox summary = new VBox();
    	summary.setSpacing(10);
    	summary.setAlignment(Pos.CENTER); 
    	Label summaryTitleLabel = new Label("Workout Summary");
		summaryTitleLabel.setFont(Font.font("System Bold", FontPosture.REGULAR, 32));

    	Label congratsLabel = new Label("Congrats, you finished your workout!");
		congratsLabel.setFont(Font.font("System", FontPosture.REGULAR, 18));

    	VBox summaryContent = new VBox();
    	VBox.setMargin(summaryContent, new Insets(20, 20, 20, 20));
    	Label personRecordsLabel = new Label("*** You set 1 personal record! Squats: 245lbs ***"); // some logic elsewhere, pass in a string variable
    	Label totalWeightLiftedLabel = new Label("Total weight lifted: ");
    	ArrayList<Label> bestSetsLabels = new ArrayList<Label>();
    	
    	// for loop here, arrayList of best sets, create arrayList of labels and addAll to the VBox
    	summaryContent.getChildren().addAll(personRecordsLabel, totalWeightLiftedLabel);
    	bestSetsLabels.add(new Label("Squats: 2 x 245lbs"));
    	bestSetsLabels.add(new Label("Overhead Press: 4 x 120lbs"));
    	bestSetsLabels.add(new Label("Barbell Rows: 3 x 185lbs"));
    	for (Label bestSet : bestSetsLabels) {
        	summaryContent.getChildren().add(bestSet);
    	}
//    	 maybe add a button here too?
    	summary.getChildren().addAll(summaryTitleLabel, congratsLabel, summaryContent);
    	Scene workoutSummary = new Scene(summary);
    	applicationStage.setScene(workoutSummary);
    }
    
   
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
    
  
    public void setApplicationStage(Stage stage) {
    	this.applicationStage = stage;
    }
    
    public Stage getApplicationStage() {
    	return this.applicationStage;
    }
   
}
