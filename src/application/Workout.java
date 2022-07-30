package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Workout {
	// use this to contain most of the variables you are passing between the methods
	// getExercises(), getRepsAndWeight(), and storeSets()
//	private ArrayList<ArrayList<StrengthExercise>> allExercises; 
	private HashMap<Integer, ArrayList<StrengthExercise>> allExercises;
	
	public Workout() {
		this.allExercises = new HashMap<Integer, ArrayList<StrengthExercise>>();
	}
//	private String exerciseName;
	//	private Scene
	
//	public void initializeAllExercises(int numberOfExercises) {
//		System.out.println(numberOfExercises);
//		this.allExercises = new ArrayList<ArrayList<StrengthExercise>>(numberOfExercises);
//		System.out.println(this.allExercises.size());
//	}
//	
//	public void setAllExercises(int exerciseNumber, ArrayList<StrengthExercise> exercisesDone) {
//		// add ArrayList of StrengthExercises (represents sets) to the nested ArrayList
//		this.allExercises.add(exerciseNumber, exercisesDone);
//	}
//	
//	public ArrayList<ArrayList<StrengthExercise>> getAllExercises() {
//		// I don't know if this should be changed to prevent privacy leak..
//		return this.allExercises;
//	}
	public void setAllExercises(int exerciseNumber, ArrayList<StrengthExercise> exercisesDone) {
		// add ArrayList of StrengthExercises (represents sets) to the nested ArrayList
		
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
