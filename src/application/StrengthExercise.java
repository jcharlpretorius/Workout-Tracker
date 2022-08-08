package application;

import java.util.ArrayList;

import javafx.scene.control.TextField;

/**
 * The StrengthExercise class is a representation of a single set in a workout.
 * It contains the name of the exercise, the number of repetitions performed, and 
 * the weight lifted in that set.
 * @author JC
 *
 */
public class StrengthExercise {

	private int weight;
	private int repetitions;
	private String exerciseName;
	private int maxReps;
	private int maxWeight;
	
	
	/**
	 * Constructs the StrengthExercise object from the exercise name, number of repetitions
	 * and weight lifted. The values for repetitions and weight are converted from strings 
	 * to integers.  
	 * @param exerciseName the name of the exercise
	 * @param reps the number of repetitions
	 * @param weight the weight moved in the exercise
	 * @throws InvalidStrengthExerciseException the String parameters for repetitions and weight 
	 * are parsed to integers and can throw this exception if an non numerical value is entered 
	 * or if the value entered is outside of the allowable range
	 */
	public StrengthExercise(String exerciseName, String reps, String weight) throws InvalidStrengthExerciseException{
		this.exerciseName = new String(exerciseName);
		maxReps = 100;
		maxWeight = 2000; // maximum allowable weight. Heavier than humanly possible
		
		// parse the repetitions input and handle invalid input
		try {
			this.repetitions = Integer.parseInt(reps);
			// check if the user entered a repetitions value outside of the allowable range
			if (this.repetitions < 1 || this.repetitions > maxReps) {
				throw new InvalidStrengthExerciseException(String.format("The number of reps should be \nbetween 1 and %d.", maxReps));
			}
			
		} catch (NumberFormatException nfe) {
			String errorMessage = "";
			// check if the reps TextField is empty
			if (reps.isEmpty()) {
				errorMessage = "Enter a reps value for each set.";
			} else {
				// check if the user has entered an invalid numerical input of the number of reps
				for (char c : reps.toCharArray()) {
					if (!Character.isDigit(c)) {
						errorMessage = "Don't include the character: " + c + "\nReps must be a positive integer.";
					}
				}
			}
			throw new InvalidStrengthExerciseException(errorMessage);
		}
		
		// parse the weight input and handle invalid input
		try {
			this.weight = Integer.parseInt(weight);
			// check if the user entered a weight value outside of the allowable range
			if (this.weight < 0 || this.weight > maxWeight) {
				throw new InvalidStrengthExerciseException(String.format("The weight value should be \nbetween 0 and %d", maxWeight));
			}
			
		} catch (NumberFormatException nfe) {
			String errorMessage = "";
			// check if the weight TextField is empty
			if (weight.isEmpty()) {
				errorMessage = "Enter a weight value for each set.";
			} else {
				// check if the user has entered an invalid numerical input of the number of reps
				for (char c : weight.toCharArray()) {
					if (!Character.isDigit(c)) {
						errorMessage = "Don't include the character: " + c + "\nWeight must be a positive integer.";
					}
				}
			}
			throw new InvalidStrengthExerciseException(errorMessage);
		}
	}
	
	/**
	 * Gets the name of the strength exercise
	 * @return a string containing the name of the name of the exercise
	 */
	public String getName() {
		return new String(this.exerciseName);
	}
	
	/**
	 * Gets the number of repetitions of the strength exercise
	 * @return the number of repetitions
	 */
	public int getReps() {
		return this.repetitions;
	}
	
	/**
	 * Gets the weight value of the strength exercise
	 * @return the weight value
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 *Returns a string representation of the strength exercise object in the form
	 *of the exercise name, the number of repetitions, and the weight value separated with 
	 *labels and all in parenthesis. Eg. For a object whose parameters are exerciseName = "Bench Press",
	 *repetitions = 10, weight = 200,  this method will return 
	 *(Bench Press: reps: 10, weight: 200)
	 */
	public String toString() {
		return "(" + this.exerciseName + ": reps: " + this.repetitions + ", weight: " + this.weight + ")";
	}
	
}
