package application;

import java.util.ArrayList;

import javafx.scene.control.TextField;

/**
 * The ExerciseSets class stores the number of sets, the name of the exercise, 
 * the number of the exercise representing its place in the workout, and 
 * a list of all of the sets done for that exercise
 * @author JC
 *
 */
public class ExerciseSets {
	private String exerciseName;
	private ArrayList<StrengthExercise> allSets;
	private int numberOfSets;
	private int exerciseNumber; 
	private int maxSets;
	
	
	public ExerciseSets(String exerciseName, String numberOfSets, int exerciseNumber) throws NumberFormatException{
		this.exerciseName = new String(exerciseName);
		this.exerciseNumber = exerciseNumber; 
		// variable for the maximum number of sets allowed for each exercises so that the window doesn't get too big
		maxSets = 20; 
		String errorMessage = "";
		try {
			this.numberOfSets = Integer.parseInt(numberOfSets);
			// check if the user entered a value outside of the allowable range
			if (this.numberOfSets < 1 || this.numberOfSets > maxSets) {
				errorMessage = "The number of sets should be between 1 and " + maxSets;
				throw new NumberFormatException(errorMessage);
			}
		} catch (NumberFormatException nfe) {
			// check if the textField was empty
			if (numberOfSets == null) {
				errorMessage = "Enter a value for the number of sets.";
			} else {
				// check is user entered an invalid numerical input for the number of sets
				for (char c : numberOfSets.toCharArray()) {
					if (!Character.isDigit(c)) {
						errorMessage = "Don't include the character: " + c + "\nNumber of sets should be a positive integer.";
					}
				}
			}
			throw new NumberFormatException(errorMessage);
		}
	}
	
	public String getExerciseName() {
		return new String(this.exerciseName);
	}
	
	public int getNumberOfSets() {
		return this.numberOfSets;
	}
	
	public int getExerciseNumber() {
		return this.exerciseNumber;
	}

	ArrayList<StrengthExercise> getAllSets() {
		return allSets; // do I want this to have privacy leak?
	}

	void setAllSets(ArrayList<StrengthExercise> allSets) {
		this.allSets = new ArrayList<StrengthExercise>(allSets); 
	}
}
