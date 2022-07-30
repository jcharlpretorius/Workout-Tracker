package application;

public class ExerciseSets {
	private String exerciseName;
	private int numberOfSets;
	private int exerciseNumber;
	//may rename this class. 
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
}