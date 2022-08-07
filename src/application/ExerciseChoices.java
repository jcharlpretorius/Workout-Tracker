package application;

import java.util.ArrayList;

/**
 * The exerciseChoices class has all of the possible exercises that a user can choose to do
 * during their workout.
 * @author JC
 *
 */
public class ExerciseChoices {
	private ArrayList<String> exerciseList = new ArrayList<String>();

	/**
	 * The ExerciseChoices constructor adds all of the exercise choices to a list
	 */
	public ExerciseChoices() {
    	exerciseList.add("Squat");
    	exerciseList.add("Bulgarian Split Squat");
    	exerciseList.add("Bench Press");
    	exerciseList.add("Chest Fly");
    	exerciseList.add("Dead Lift");
    	exerciseList.add("Romanian Deadlift");
    	exerciseList.add("Calf Raise");
    	exerciseList.add("Back Extension");
    	exerciseList.add("Barbell Row");
    	exerciseList.add("Seated Row");
    	exerciseList.add("Lat Pulldown");
    	exerciseList.add("Chin ups");
    	exerciseList.add("Overhead Press");
    	exerciseList.add("Lateral Raise");
    	exerciseList.add("Front Raise");
    	exerciseList.add("Face Pull");
    	exerciseList.add("Bicep Curl");
    	exerciseList.add("Hammer Curl");
    	exerciseList.add("Triceps Extension");
    	exerciseList.add("Triceps Dips");
    	exerciseList.add("Skullcrusher");
	}

	/**
	 * Returns the list of all exercise choices
	 * @return the list of exercise names
	 */
	ArrayList<String> getExerciseList() {
		return new ArrayList<String>(exerciseList);
	}
}
