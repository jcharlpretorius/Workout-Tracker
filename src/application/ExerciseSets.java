package application;

import java.util.ArrayList;

import javafx.scene.control.TextField;

/**
 * The ExerciseSets class stores the number of sets, the name of the exercise, 
 * the number of the exercise representing its place in the workout, and 
 * a list of all of the sets done for that exercise. It also holds a variable
 * for the maximum number of sets allowed.
 * @author JC
 *
 */
public class ExerciseSets {
	private String exerciseName;
	private ArrayList<StrengthExercise> allSets;
	private int numberOfSets;
	private int exerciseNumber; 
	private int maxSets;
	
	/**
	 * Constructs a newly created ExerciseSets object from an exercise name, an exercise number
	 * and the number of sets
	 * @param exerciseName the name of the exercise the ExerciseSets contains
	 * @param numberOfSets the total number of sets
	 * @param exerciseNumber the number corresponding to the exercise's position in a workout
	 * @throws NumberFormatException parsing a string to an integer for the number of sets can cause an exception
	 */
	public ExerciseSets(String exerciseName, String numberOfSets, int exerciseNumber) throws NumberFormatException{
		this.exerciseName = new String(exerciseName);
		this.exerciseNumber = exerciseNumber; 
		// variable for the maximum number of sets allowed for each exercises so that the window doesn't get too big
		maxSets = 20; 
		String errorMessage = "";
		
		// Try to parse the String to an integer and use it to initialize the number of sets
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
	
	/**
	 * Gets the name of the exercise
	 * @return the string containing the exercise name 
	 */
	public String getExerciseName() {
		return new String(this.exerciseName);
	}
	
	/**
	 * Gets the number of sets 
	 * @return the number of sets
	 */
	public int getNumberOfSets() {
		return this.numberOfSets;
	}
	
	/**
	 * Gets the exercise number, which represents its position in the workout
	 * @return the exercise number
	 */
	public int getExerciseNumber() {
		return this.exerciseNumber;
	}

	/**
	 * Get the list of all sets in the exercise
	 * @return the list containing StrengthExerices objects for each set
	 */
	ArrayList<StrengthExercise> getAllSets() {
		return new ArrayList<StrengthExercise>(allSets); 
	}
	
	/**
	 * Sets the list of all exercise sets
	 * @param allSets the list of StrengthExercise objects to set allSets to
	 */
	void setAllSets(ArrayList<StrengthExercise> allSets) {
		this.allSets = new ArrayList<StrengthExercise>(allSets); 
	}
}
