package application;

import java.util.ArrayList;

public class ExerciseChoices {
	private ArrayList<String> exerciseList = new ArrayList<String>();

	public ExerciseChoices() {
    	exerciseList.add("Squat");
    	exerciseList.add("Bench Press");
    	exerciseList.add("Dead Lift");
    	exerciseList.add("Overhead Press");
    	exerciseList.add("Barbell Row");
    	exerciseList.add("Bicep Curl");
    	exerciseList.add("Tricep Push-downs");
    	exerciseList.add("Lateral Raises");
    	exerciseList.add("Pull ups");
    	exerciseList.add("Dips");
	}

	ArrayList<String> getExerciseList() {
		return new ArrayList<String>(exerciseList);
	}

	void setExerciseList(ArrayList<String> exerciseList) {
		this.exerciseList = exerciseList;
	}
	
}
