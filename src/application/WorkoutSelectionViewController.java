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
import javafx.scene.text.FontWeight;
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
    	contents.setPadding(new Insets(20, 20, 20, 20));
    	ArrayList<String> chosenExercises = new ArrayList<String>(); // unused..
    	ArrayList<TextField> setsTextFields = new ArrayList<TextField>();
    	
    	// tabs in label should be removed once the layout is changed to look nice
    	Label exerciseChoiceLabel = new Label("Exercise \t\t\t\t Number of Sets"); // use separate labels instead of tabs to adjust layout
    	contents.getChildren().add(exerciseChoiceLabel);
    	
    	int numberOfExercises = numberOfExercisesChoiceBox.getValue();
    	
    	// initializes the size of the allExercises ArrayList
    	workout.setNumberOfExercises(numberOfExercises);
    	
    	int rowCounter = 0;
    	while(rowCounter < numberOfExercises) {
    		HBox exerciseRow = new HBox();
    		exerciseRow.setPadding(new Insets(0, 0, 2, 0));
    		
    		ChoiceBox<String> choiceBoxOptions = new ChoiceBox<String>();
    		choiceBoxOptions.getItems().addAll(createExerciseArrayList());
    		choiceBoxOptions.getSelectionModel().select(0); // sets default value in choiceBox
    		
    		TextField numberOfSetsTextfield = new TextField(); // should only take type int
    		numberOfSetsTextfield.setPrefWidth(80);
    		numberOfSetsTextfield.setAlignment(Pos.CENTER);
    		setsTextFields.add(numberOfSetsTextfield); // unused arrayList
    		
    		Button startExercise = new Button("Start Exercise");
	
    		// need to validate the input of the textfields so that they only allow integers and choicebox is not empty
    		// probably pull the input validation into a class like the "Grade" class in the gradeCalculator
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
    	// Create and change the scene to the workout Summary 
    	
    	// calculate value in workout object
    	workout.setTotalWeightLifted(); 
    	workout.setBestSets();
    	// create workout summary scene:
    	VBox summary = new VBox();
    	summary.setSpacing(10);
    	summary.setAlignment(Pos.CENTER); 
    	Label summaryTitleLabel = new Label("Workout Summary");
		summaryTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

		// this label could instead be something like: "You completed your 4th workout!" - would need workout history file
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
    	
    	ArrayList<Label> bestSetsLabels = new ArrayList<Label>();
    	workout.getBestSets().forEach((exName, strengthExercise) -> {
    		bestSetsLabels.add(new Label(exName + ": " + strengthExercise.getReps() + "x" + strengthExercise.getWeight()+ "lbs"));
    	});
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
