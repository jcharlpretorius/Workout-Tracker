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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/**
 * The javafx controller class for the workout application.
 * Manages the graphical user interface and some basic user funtionality 
 * @author JC
 *
 */
public class WorkoutSelectionViewController {
	private Stage applicationStage;
	private Workout workout = new Workout();
	private String userFileName = "src/James.txt"; // maybe have a way of selecting/entering a user file?
	private UserFileIO userFile = new UserFileIO(userFileName); // pass in String fileName
	private UserInfo user = userFile.getUser(); 
	private ExerciseChoices exerciseChoices = new ExerciseChoices();

    @FXML
    private ChoiceBox<Integer> numberOfExercisesChoiceBox;

    
    @FXML
   /**
    * Creates a scene based on the number of exercises selected in the workout selection scene
    * to get the types of exercises and number of sets the user wants to perform
    * @param event on action of "Select Exercises" button press in the workout selection scene
    */
    void getExercises(ActionEvent event) {
    	Scene workoutSelection = applicationStage.getScene();
    	
    	VBox allContents = new VBox();
    	allContents.setPadding(new Insets(20, 20, 20, 20));

    	Label exerciseChoiceLabel = new Label("Exercise \t\t\t\t Number of Sets"); 
    	allContents.getChildren().add(exerciseChoiceLabel);
    	
    	// Add a label to display any error messages
    	Label setsErrorLabel = new Label("");
    	setsErrorLabel.setPrefHeight(35);
    	
    	// call helper function to build rows for choosing the exercises and numbers of sets
    	exerciseChoiceContent(allContents, setsErrorLabel);

    	HBox buttonBox = new HBox();
    	
    	// Add a Button to finish the workout and switch to workout summary window
    	Button finishWorkoutButton = new Button("Finish Workout");
    	finishWorkoutButton.setOnAction(doneEvent -> finishWorkout());
    	
    	// Add a Button to switch back to the workout selection window. 
    	Button exitWorkout = new Button("Exit Workout");
    	exitWorkout.setOnAction(exitEvent -> exitWorkout(workoutSelection));
    	buttonBox.getChildren().addAll(exitWorkout, finishWorkoutButton);
    	
    
    	allContents.getChildren().addAll(buttonBox, setsErrorLabel);
    	
    	// create and set the new scene 
    	Scene exerciseSelectionScene = new Scene(allContents);
    	applicationStage.setScene(exerciseSelectionScene);
    }
    
    
    /**
     * A helper method for the getExercises method. This method adds a number of rows depending 
     * on the number of exercises selected on the workout selection scene. Each row
     * contains a ChoiceBox for the type of exercise, a TextField to enter the number of
     * sets the user wants to do, and a button to start the exercise 
     * @param contents the VBox container that holds the entire contents of the scene
     */
    private void exerciseChoiceContent(VBox allContents, Label setsErrorLabel) {
    	// A loop for creating elements of the scene for getting the exercise choices and number of sets desired
    	int numberOfExercises = numberOfExercisesChoiceBox.getValue();
    	int rowCounter = 0;
    	while(rowCounter < numberOfExercises) {
    		HBox exerciseRow = new HBox();
    		exerciseRow.setPadding(new Insets(0, 0, 2, 0));
    		
    		// Create ChoiceBoxes containing a list of exercise choices
    		ChoiceBox<String> choiceBoxOptions = new ChoiceBox<String>();
    		// Create an observable list from the ArrayList of exercise names to display as choices
    		ObservableList<String> exChoices = FXCollections.observableArrayList(exerciseChoices.getExerciseList());
    		choiceBoxOptions.getItems().addAll(exChoices);
    		choiceBoxOptions.getSelectionModel().select(0); // sets default value in choiceBox
    		
    		// creates the text field for the user to input the number of sets
    		TextField numberOfSetsTextfield = new TextField(); 
    		numberOfSetsTextfield.setPromptText("# of sets");
    		numberOfSetsTextfield.setPrefWidth(80);
    		numberOfSetsTextfield.setAlignment(Pos.CENTER);
    		
    		Button startExercise = new Button("Start Exercise");
    		int exerciseNumber = rowCounter + 1;
    		
    		// Button press to switch to the scene to get the user's input for reps and weight
    		startExercise.setOnAction(startExerciseEvent -> {
    			// create new ExerciseSets object to store the set information
    			try {
        			ExerciseSets exercise = new ExerciseSets(choiceBoxOptions.getValue(), numberOfSetsTextfield.getText(), exerciseNumber);
        			getRepsAndWeight(applicationStage.getScene(), exercise);
        			// clear error message
        	    	setsErrorLabel.setText("");

    			} catch (NumberFormatException nfe) {
    				setsErrorLabel.setText(nfe.getMessage());
    				setsErrorLabel.setTextFill(Color.RED);
    			}
    		});
    		 
    		exerciseRow.getChildren().addAll(choiceBoxOptions, numberOfSetsTextfield, startExercise);
    		allContents.getChildren().add(exerciseRow);
    		rowCounter++;
    	}
    }
    
    
    /**
     * Exit the workout and switch the scene back to the workout selection scene
     * and clear any saved workout data
     * @param workoutSelectionScene a scene that holds the previous scene 
     * (the workout selection scene) so that the scene can be switched back to that scene
     */
    private void exitWorkout(Scene workoutSelectionScene) {
    	applicationStage.setScene(workoutSelectionScene);
    	workout = new Workout();
    }
    
    
    /**
     * Creates and changes to a Scene to get the user's input for the number of repetitions
     * and weight lifted for each set based on the name of exercise chosen and number of sets the
     * user enters 
     * 
     * @param exerciseSelectionScene the Scene that will be switched back to, passed as an argument to storeSets()
     * @param exercise An ExerciseSet object that holds the values of the number of sets and the exercise name, passed 
     * as an argument to storeSets()
     */
    void getRepsAndWeight(Scene exerciseSelectionScene, ExerciseSets exercise) {
    	VBox allRows = new VBox();
//    	allRows.setPadding(new Insets(20, 20, 20, 20));
//    	Inset labelMargins = new Insets(20, 20, 20, 20)); // if insets are reused the could be made variables
    	// values refer to: (top, right, bottom, left)
    	allRows.setPadding(new Insets(20, 20, 20, 20));
    	
    	// Create label to display the exercise name at the top
    	Label exerciseNameLabel = new Label(exercise.getExerciseName());
    	VBox.setMargin(exerciseNameLabel, new Insets(0, 0, 20, 0));
    	exerciseNameLabel.setFont(Font.font("System Bold", FontPosture.REGULAR, 24));
    	
    	// Create labels to go above the text fields
    	Label repsAndWeightHeaderLabel = new Label("\t     Reps \t\t   Weight (lbs)"); // maybe split these titles and add separately to a HBox?
    	allRows.getChildren().addAll(exerciseNameLabel, repsAndWeightHeaderLabel);
    	
    	// Create an error label
    	Label repsAndWeightErrorLabel = new Label("");
    	repsAndWeightErrorLabel.setTextFill(Color.RED);
    	repsAndWeightErrorLabel.setPrefHeight(35);
//    	VBox.setMargin(repsAndWeightErrorLabel, new Insets(0, 0, 0, 20));
    	
    	//
    	ArrayList<TextField> repsTextFields = new ArrayList<TextField>();
    	ArrayList<TextField> weightTextFields = new ArrayList<TextField>();
    	repsAndWeightContent(allRows, exercise, repsTextFields, weightTextFields);

    	// Done button calls storeSets function to store the values in the text fields
    	Button doneButton = new Button("Done");
    	doneButton.setOnAction(doneEvent -> storeSets(exerciseSelectionScene, exercise, repsTextFields, weightTextFields, repsAndWeightErrorLabel));
    	allRows.getChildren().addAll(doneButton, repsAndWeightErrorLabel);
    	
    	// Finally, change to the scene created above
    	Scene repsAndWeights = new Scene(allRows);
    	applicationStage.setScene(repsAndWeights);
    }
    
    
    /**
     * A helper method for the getRepsAndWeight method. This method adds a number of rows depending
     * on the number of sets the user entered on the exercise selection scene. Each row contains two
     * TextFields, one for the number of repetitions performed and the other for the weight lifted.
     * @param allRows the VBox container that holds the entire contents of the repetitions & weights scene
     * @param exercise the exercise set object, used in this method for its value of the number of sets 
     * @param repsTextFields a list of TextFields that holds the user input for the number of repetitions
     * @param weightTextFields a list of TextFields that holds the user input for the weight lifted in lbs
     */
    private void repsAndWeightContent(VBox allRows, ExerciseSets exercise, ArrayList<TextField> repsTextFields, ArrayList<TextField> weightTextFields) {
    	
    	int rowCounter = 0;
    	while(rowCounter < exercise.getNumberOfSets()) {
    		HBox setsRow = new HBox();
    		setsRow.setSpacing(10.0);
//    		HBox.setMargin(setsRow, new Insets(10, 10, 5, 10)); // I don't think this does anything
    		
    		Label setLabel = new Label("Set " + (rowCounter + 1)); 
    		
    		// Create text field for the number of repetitions
    		TextField reps = new TextField();
    		reps.setPrefWidth(40);
    		reps.setPromptText("Reps");
    		reps.setAlignment(Pos.CENTER);
    		repsTextFields.add(reps);
    		
    		// Add a "X" label in between text fields
    		Label xLabel = new Label("X");
    		// how to set font style: https://coderslegacy.com/java/javafx-font/
    		xLabel.setFont(Font.font("System Bold", FontPosture.REGULAR, 20));
    		
    		// Create text field for the weight lifted
    		TextField weight = new TextField();
    		weight.setPrefWidth(90);
    		weight.setPromptText("Weight (lbs)");
    		weight.setAlignment(Pos.CENTER);
    		weightTextFields.add(weight);
    	
    		setsRow.getChildren().addAll(setLabel, reps, xLabel, weight);
    		
    		allRows.getChildren().add(setsRow);
    		rowCounter++;
    	}
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
    void storeSets(Scene exerciseSelectionScene, ExerciseSets exercise, ArrayList<TextField> repsTextFields, ArrayList<TextField> weightTextFields, Label errorLabel) {
    	// method called by getRepsAndWeight to store elements in arrayList and switch back the scene

    	// create StrengthExercise objects from ArrayLists of TextField inputs and add them to an ArrayList in the ExerciseSets object
    	ArrayList<StrengthExercise> exercisesDone = new ArrayList<StrengthExercise>();
		boolean noErrors = true;
		// loop through each set
    	for (int i = 0; i < repsTextFields.size(); i++) {
    		String reps = repsTextFields.get(i).getText();
    		String weight = weightTextFields.get(i).getText();
    		// try to make a new StrengthExercise object with the TextField values
    		try {
    			StrengthExercise se = new StrengthExercise(exercise.getExerciseName(), reps, weight);
        		exercisesDone.add(se);
        		// clear error message 
//        		errorLabel.setText("");	
//        		System.out.println("Set " + i + " added to list");
    		} catch (NumberFormatException nfe) {
    			// Set error message
        		errorLabel.setText(nfe.getMessage());
        		noErrors = false;
//        		System.out.println("Set " + i + " not added to list: " + nfe.getMessage());
    		} catch (InvalidRepetitionsException ire) {
    			// do something
    			errorLabel.setText(ire.getMessage());
        		noErrors = false;
    		} catch (InvalidWeightException iwe) {
    			// do something
    			errorLabel.setText(iwe.getMessage());
        		noErrors = false;
    		}
    	}
    	if (noErrors) {
    		System.out.println(exercisesDone);
    		// Store the ArrayList of StrengthExercises in the ExerciseSets object and add that to the workout  
        	exercise.setAllSets(exercisesDone);
        	workout.setAllExercises(exercise);

        	// Change the scene back to the exercise selection scene
        	applicationStage.setScene(exerciseSelectionScene);
        }
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
    	ArrayList<String> newPRs = workout.checkPersonalBests(user.getPersonalRecords()); 
    	
    	// create workout summary scene:
    	VBox allSummary = new VBox();
    	allSummary.setSpacing(10);
    	allSummary.setAlignment(Pos.CENTER); 
    	Label summaryTitleLabel = new Label("Workout Summary");
		summaryTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

		// Label to display the number of workouts done
		int workoutNumber = user.getNumWorkoutsDone(); 
    	Label congratsLabel = new Label("Congratulations " + user.getUserName() + ",\n" + "you finished your " + workoutNumber + getOrdinalSuffix(workoutNumber) + " workout!"); 
		congratsLabel.setFont(Font.font("System", FontPosture.REGULAR, 16));
		VBox.setMargin(congratsLabel, new Insets(0, 10, 0, 10));
		
		// Call helper function to populate the summary content with total weight lifted, number of PRs, and the best sets
		VBox summaryContent = new VBox();
		workoutSummaryContent(summaryContent, newPRs);
    	allSummary.getChildren().addAll(summaryTitleLabel, congratsLabel, summaryContent);
    	
    	// Switch to the workout summary scene
    	Scene workoutSummary = new Scene(allSummary);
    	applicationStage.setScene(workoutSummary);
    	
    	// Print Statements for testing, remove before submitting final version
    	System.out.println("Name: " + user.getUserName());
    	System.out.println("Num workouts Done: " + user.getNumWorkoutsDone());
    	System.out.println("User height: " + user.getHeight());
    	System.out.println("User BodyWeight: " + user.getBodyWeight());
    	System.out.println("Personal Records: " + user.getPersonalRecords());

    	
    	//write user information to file
    	try {
			userFile.writeWorkout(userFileName);
		} catch (IOException e) {
			System.out.println("Error: Failed to write to file");
			e.printStackTrace();
		}
    }
    
    /**
     * A helper method for finishWorkout(). This method populates a container with labels to display
     * important information about the workout, including total weight lifted, the best sets performed,
     * and the personal records broken (if any).
     * @param summaryContent the container that is being populated by this method
     * @param newPRs a list containing the personal records broken during the workout
     */
    void workoutSummaryContent(VBox summaryContent, ArrayList<String> newPRs) {
    	VBox.setMargin(summaryContent, new Insets(20, 20, 20, 20));
    	
    	// Create labels for the personal bests, if any have been broken
    	Label personRecordsLabel = new Label(""); 
    	if (!newPRs.isEmpty()) {
    		String prText = "You set " + newPRs.size() + " new personal record";
    		if (newPRs.size() == 1) {
    			prText += "!";
    		} else {
    			// Handle grammar for plural case, append an "s"
    			prText += "s!";
    		}
    		personRecordsLabel.setText(prText);
    	}
    	
    	// Create labels for total weight lifted and a header for the best sets
    	Label totalWeightLiftedLabel = new Label("Total weight lifted: " + workout.getTotalWeightLifted() + "lbs");
    	Label bestSetsHeaderLabel = new Label("Best sets: ");
    	bestSetsHeaderLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
    	
    	summaryContent.getChildren().addAll(personRecordsLabel,totalWeightLiftedLabel, bestSetsHeaderLabel);
    	
    	// Create labels for displaying best sets from a list
    	ArrayList<Label> bestSetsLabels = new ArrayList<Label>();
    	workout.getBestSets().forEach((exName, strengthExercise) -> {
    		String bs = exName + ": " + strengthExercise.getReps() + "x" + strengthExercise.getWeight()+ "lbs";
    		// Concatenate new PR indicator to string 
    		if (!newPRs.isEmpty()) {
    			if (newPRs.contains(exName)) {
    				bs += "   **New PR**";
    			};
    		}
    		bestSetsLabels.add(new Label(bs));

    	});
    	// Add best sets labels to the VBox
    	for (Label bestSet : bestSetsLabels) {
        	summaryContent.getChildren().add(bestSet);
    	}
    }
    
    /**
     * Gets the ordinal suffix for the input number. 
     * This method gives the correct suffix for all numbers < 11111
     * Citation: Code for this method modified from StackOverflow user Salman A:
     * https://stackoverflow.com/questions/13627308/add-st-nd-rd-and-th-ordinal-suffix-to-a-number/#13627586 
     * 
     * @param num an integer to get the suffix for
     * @return a 2 character string, either "st", "nd", "rd", or "th"
     */
    public String getOrdinalSuffix(int number) {
    	int lastDigit = number % 10;
    	int secondLastDigit = number % 100;
    	if (lastDigit == 1 && secondLastDigit != 11) {
    		return "st";
    	}
    	if (lastDigit == 2 && secondLastDigit != 12) {
    		return "nd";
    	}
    	if (lastDigit == 3 && secondLastDigit != 13) {
    		return "rd";
    	}
    	// return "th" for all other numbers
    	return "th";
	}
    
    public void setApplicationStage(Stage stage) {
    	this.applicationStage = stage;
    }
}
