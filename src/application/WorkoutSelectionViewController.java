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
    		// Create an observable list from the ArrayList of exercise names to display as choices
    		ObservableList<String> exChoices = FXCollections.observableArrayList(exerciseChoices.getExerciseList());
    		choiceBoxOptions.getItems().addAll(exChoices);
    		choiceBoxOptions.getSelectionModel().select(0); // sets default value in choiceBox
    		
    		// creates the text field for the user to input the number of sets
    		TextField numberOfSetsTextfield = new TextField(); 
    		numberOfSetsTextfield.setPrefWidth(80);
    		numberOfSetsTextfield.setAlignment(Pos.CENTER);
    		setsTextFields.add(numberOfSetsTextfield); 
    		
    		Button startExercise = new Button("Start Exercise");
    		int exerciseNumber = rowCounter + 1;
    		
    		// Button press to switch to the scene to get the user's input for reps and weight
    		startExercise.setOnAction(startExerciseEvent -> {
    			int numberOfSets = Integer.parseInt(numberOfSetsTextfield.getText());    
    			// create new ExerciseSets object to store the set information
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
     * Exit the workout and switch the scene back to the workout selection scene
     * @param workoutSelectionScene a scene that holds the previous scene 
     * (the workout selection scene) so that the scene can be switched back to that scene
     */
    private void exitWorkout(Scene workoutSelectionScene) {
    	// change scene back to work out selection scene
    	applicationStage.setScene(workoutSelectionScene);
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
//    	Inset labelMargins = new Insets(20, 20, 20, 20)); // if insets are reused the could be made variables
    	// values refer to: (top, right, bottom, left)
    	allRows.setPadding(new Insets(20, 20, 20, 20));
    	
    	// Create label to display the exercise name at the top
    	Label exerciseNameLabel = new Label(exercise.getExerciseName());
    	VBox.setMargin(exerciseNameLabel, new Insets(0, 0, 20, 0));
    	exerciseNameLabel.setFont(Font.font("System Bold", FontPosture.REGULAR, 24));
    	
    	// Create labels to go above the text fields
    	Label repsAndWeightHeaderLabel = new Label("\t\tReps \t\t Weight (lbs)"); // maybe split these titles and add separately to a HBox?
    	allRows.getChildren().addAll(exerciseNameLabel, repsAndWeightHeaderLabel);
    	
    	ArrayList<TextField> repsTextFields = new ArrayList<TextField>();
    	ArrayList<TextField> weightTextFields = new ArrayList<TextField>();

    	int rowCounter = 0;
    	while(rowCounter < exercise.getNumberOfSets()) {
    		HBox setsRow = new HBox();
    		setsRow.setSpacing(10.0);
    		HBox.setMargin(setsRow, new Insets(10, 10, 5, 10));
    		
    		Label setLabel = new Label("Set " + (rowCounter + 1)); // how to prevent row from shifting to the right when set# is > 1 digit?
    		
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
    	
    	Button doneButton = new Button("Done");
    	
    	// Done button calls storeSets function to store the values in the text fields
    	doneButton.setOnAction(doneEvent -> storeSets(exerciseSelectionScene, exercise, repsTextFields, weightTextFields));
    	allRows.getChildren().add(doneButton);
    	
    	// Finally, change to the scene created above
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
   
    	// create StrengthExercise objects from ArrayLists of TextField inputs and add them to the ArrayList of all exercises 
    	ArrayList<StrengthExercise> exercisesDone = new ArrayList<StrengthExercise>();
    	for (int i = 0; i < repsTextFields.size(); i++) {
    		int reps = Integer.parseInt(repsTextFields.get(i).getText());
    		int weight = Integer.parseInt(weightTextFields.get(i).getText());
    		StrengthExercise se = new StrengthExercise(exercise.getExerciseName(), reps, weight);
    		exercisesDone.add(se);
    	}
    	
    	// Add the Arraylist of exercises to ArrayList for all the exercises done in the workout
    	workout.setAllExercises(exercise.getExerciseNumber(), exercisesDone);
    	
    	// Change the scene back to the exercise selection scene
    	applicationStage.setScene(exerciseSelectionScene);
    }
    
    double calculatedBMI = 0;
    boolean isMetric = true;
    Label calculatedBMILabel = new Label();
	
    void calculateBMI(TextField weightTextField, TextField heightTextField, boolean isMetric) {
    	
    	double weight = Double.parseDouble(weightTextField.getText());
    	double height = Double.parseDouble(heightTextField.getText());
    	
    	if (isMetric) {
    		calculatedBMI = weight/(height*height);
    		System.out.println(weight);
    		System.out.println(height);
    		System.out.println(calculatedBMI);
    	}else {
    		calculatedBMI = (weight*703)/(height*height);
    		System.out.println(calculatedBMI);
    	}
    	
    	calculatedBMILabel.setText("Your BMI is: " + String.valueOf(calculatedBMI));
    	
    }
    
    /**
     * Gets to BMI calculator scene
     * @param event: 'Calculate BMI button' switches scene to the BMI calculator.
     */
    @FXML
    void getBMI(ActionEvent event) {
    	//main scene (workout selection aka the first scene when main is run)
    	Scene workoutSelection = applicationStage.getScene();
    	
    	System.out.println("Scene Change to BMI CALCULATOR");
    	
    	//creating new scene containers
    	VBox bmiContainer = new VBox();
    	HBox rowContainer = new HBox();
    	
    	
    	//creating new scene controls/widgets
    	Label weightLabel = new Label("Enter your body weight");
    	TextField weightTextField = new TextField();
    	Label heightlabel = new Label("Enter your height");
    	TextField heightTextField = new TextField();
    	
    	// change unit button (CHANGE SCENE TO A NEW SCENE WHERE THE CALCULATIONS WILL BE DONE IN IMPERIAL)
    	
    	
    	ToggleButton changeUnitButton = new ToggleButton("Change unit to imperial.");
  
    	//changeUnitButton.setSelected(true);
    	
    	changeUnitButton.setOnAction(changeUnitEvent -> {
    		
    		if (changeUnitButton.isSelected()) {
        		changeUnitButton.setText("Change unit to metric.");
        		isMetric = false;
        	}else {
        		changeUnitButton.setText("Change unit to imperial.");
        		isMetric = true;
        	}
    		
    	});
    	
    	
    	calculatedBMILabel.setText("Your BMI is: ");
    	//calculate bmi button (SHOULD CALCULATE THE BMI AND SHOW IT ON SCREEN)
    	Button calcualteBMIButton = new Button("Calculate BMI!");
    	calcualteBMIButton.setOnAction(doneEvent -> calculateBMI(weightTextField,heightTextField,isMetric));
    	
    	rowContainer.getChildren().addAll(weightLabel,weightTextField,heightlabel,heightTextField,calcualteBMIButton);
    	
    	
    	
    	Button exitBMIScreen = new Button("Exit to main menu.");
    	exitBMIScreen.setOnAction(exitEvent -> exitWorkout(workoutSelection));
    	
    	
    	bmiContainer.getChildren().addAll(rowContainer,changeUnitButton,exitBMIScreen,calculatedBMILabel);	
    	
    	
    	Scene getBMI = new Scene(bmiContainer);
    	applicationStage.setScene(getBMI);
    	
    	
    	
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
    	VBox summary = new VBox();
    	summary.setSpacing(10);
    	summary.setAlignment(Pos.CENTER); 
    	Label summaryTitleLabel = new Label("Workout Summary");
		summaryTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

		// Label to display the number of workouts done
		int workoutNumber = user.getNumWorkoutsDone(); 
    	Label congratsLabel = new Label("Congratulations " + user.getUserName() + ",\n" + "you finished your " + workoutNumber + getOrdinalSuffix(workoutNumber) + " workout!"); 
		congratsLabel.setFont(Font.font("System", FontPosture.REGULAR, 16));
		VBox.setMargin(congratsLabel, new Insets(0, 10, 0, 10));
		
    	VBox summaryContent = new VBox();
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
  
    	summary.getChildren().addAll(summaryTitleLabel, congratsLabel, summaryContent);
    	
    	// Switch to the workout summary scene
    	Scene workoutSummary = new Scene(summary);
    	applicationStage.setScene(workoutSummary);
    	
    	// Print Statements for testing, remove before submitting final version
    	System.out.println("Name: " + user.getUserName());
    	System.out.println("Num workouts Done: " + user.getNumWorkoutsDone());
    	System.out.println("User height: " + user.getHeight());
    	System.out.println("User BodyWeight: " + user.getBodyWeight());
    	System.out.println("Personal Records: " + user.getPersonalRecords());

    	
//    	 write user information to file
    	try {
			userFile.writeWorkout(userFileName);
		} catch (IOException e) {
			System.out.println("Error: Failed to write to file");
			e.printStackTrace();
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
    public static String getOrdinalSuffix(int num) {
    	int i = num % 10;
    	int j = num % 100;
    	if (i == 1 && j != 11 && j != 111 && j != 1111) {
    		return "st";
    	}
    	if (i == 2 && j != 12 && j != 112 && j != 1112) {
    		return "nd";
    	}
    	if (i == 3 && j != 13 && j != 113 && j != 1113) {
    		return "rd";
    	}
    	return "th";
	}
    
    public void setApplicationStage(Stage stage) {
    	this.applicationStage = stage;
    }
}
