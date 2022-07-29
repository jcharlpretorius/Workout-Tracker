package application;

public class StrengthExercise {
	// parent class for strength exercises
	private double weight;
	private int repetitions;
	private String exerciseName;
	
	public StrengthExercise(String exerciseName) {
		this.exerciseName = exerciseName;
	}
	
	public StrengthExercise(String exerciseName, int reps, double weight) {
		this.exerciseName = new String(exerciseName);
		this.weight = weight;
		this.repetitions = reps;
	}
	
	public String toString() {
		return new String(this.exerciseName);
	}
}
