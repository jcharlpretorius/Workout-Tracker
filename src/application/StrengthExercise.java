package application;


public class StrengthExercise {

	private int weight;
	private int repetitions;
	private String exerciseName;
	
	public StrengthExercise() {
	}
	
	public StrengthExercise(String exerciseName) {
		this.exerciseName = exerciseName;
	}
	
	public StrengthExercise(String exerciseName, int reps, int weight) {
		this.exerciseName = new String(exerciseName);
		this.weight = weight;
		this.repetitions = reps;
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
