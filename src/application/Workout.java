package application;

import java.util.ArrayList;
import java.util.HashMap;
// stores workout data
public class Workout {
	private HashMap<Integer, ArrayList<StrengthExercise>> allExercises;
	
	public Workout() {
		this.allExercises = new HashMap<Integer, ArrayList<StrengthExercise>>();
	}


	public void setAllExercises(int exerciseNumber, ArrayList<StrengthExercise> exercisesDone) {
		// add ArrayList of StrengthExercises (represents sets) to the HashMap
		
		if (!this.getAllExercises().isEmpty()) {
			// remove sets if already added -> prevents adding another set when re-entering the set window
			if (this.allExercises.containsKey(exerciseNumber)) {
				this.allExercises.remove(exerciseNumber);
			}
			this.allExercises.put(exerciseNumber, exercisesDone);
		} else {
			this.allExercises.put(exerciseNumber, exercisesDone);
		}
	}
		
	
	public HashMap<Integer, ArrayList<StrengthExercise>> getAllExercises() {
		// I don't know if this should be changed to prevent privacy leak..
		return this.allExercises;
	}
	
	public String toString() {
		// make sure allExercises is not null
		String str = "";
		if (!this.allExercises.isEmpty()) {
//			for(int i = 0; i < this.allExercises.size(); i++)
			this.allExercises.forEach((exerciseNumber, strengthExercisesArrayList) -> {
				// why can't StrengthExercise be seen as a valid type?
				String output = "";
				for (int i = 0; i < strengthExercisesArrayList.size(); i++) {
					output += "Exercises number: " + exerciseNumber + ", " + strengthExercisesArrayList.get(i).toString()
							+ "\n";
				}
				
			});
		}
		return str;
	}
}
