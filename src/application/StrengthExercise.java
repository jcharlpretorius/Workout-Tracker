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
	
	
	public StrengthExercise(String exerciseName, String reps, String weight) throws NumberFormatException, InvalidRepetitionsException, InvalidWeightException{
		this.exerciseName = new String(exerciseName);
		maxReps = 100;
		maxWeight = 1500; // maximum allowable weight. Heavier than world record deadlift
		
		// parse the repetitions input and handle invalid input
		try {
			this.repetitions = Integer.parseInt(reps);
			// check if the user entered a repetitions value outside of the allowable range
			if (this.repetitions < 1 || this.repetitions > maxReps) {
				throw new InvalidRepetitionsException(String.format("The number of reps should be \nbetween 1 and %d.", maxReps));
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
			throw new InvalidRepetitionsException(errorMessage);
		}
		
		// parse the weight input and handle invalid input
		try {
			this.weight = Integer.parseInt(weight);
			// check if the user entered a weight value outside of the allowable range
			if (this.weight < 1 || this.weight > maxWeight) {
				throw new InvalidWeightException(String.format("The weight value should be \nbetween 1 and %d", maxWeight));
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
			throw new InvalidWeightException(errorMessage);
		}
	}
	
	public String getName() {
		return new String(this.exerciseName);
	}
	
	public int getReps() {
		return this.repetitions;
	}
	
	public int getWeight() {
		return this.weight;
	}
	public String toString() {
		return "(" + this.exerciseName + ": reps: " + this.repetitions + ", weight: " + this.weight + ")";
	}
	
}
