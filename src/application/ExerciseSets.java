package application;

import java.util.ArrayList;

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
	
	public ExerciseSets(String exerciseName, int numberOfSets, int exerciseNumber) {
		this.exerciseName = new String(exerciseName);
		this.numberOfSets = numberOfSets;
		this.exerciseNumber = exerciseNumber; 
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
