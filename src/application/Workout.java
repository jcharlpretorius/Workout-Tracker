package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Workout {
	private HashMap<Integer, ArrayList<StrengthExercise>> allExercises;
	private HashMap<String, StrengthExercise> bestSets;
	private int totalWeightLifted;
	
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
		System.out.println(allExercises);
	}
		
	public HashMap<Integer, ArrayList<StrengthExercise>> getAllExercises() {
		// I don't know if this should be changed to prevent privacy leaks. 
		return this.allExercises;
	}
	
	public void setTotalWeightLifted() {
		// iterate through the allExercises hashmap and add up all lifts
		int sum = 0;
		if (!allExercises.isEmpty()) {
			for (int i = 0; i < allExercises.size(); i++) {
				for (StrengthExercise ex : allExercises.get(i)) {
					sum += ex.getReps() * ex.getWeight();
				}
			}
		}
		this.totalWeightLifted = sum;
	}
	
	public int getTotalWeightLifted() {
		return this.totalWeightLifted;
	}
	
	public void setBestSets() {
		// loop through the HashMap and find the set with the heavies weight lifted
		HashMap<String, StrengthExercise> allBestSets = new HashMap<String, StrengthExercise>();
		if (!allExercises.isEmpty()) {
			for (int i = 0; i < allExercises.size(); i++) {
				String exerciseName = allExercises.get(i).get(0).getName();
				int heaviestWeight = 0;
				StrengthExercise bestSet = new StrengthExercise();
				if (!(bestSets.containsKey(exerciseName))) {
					heaviestWeight = allExercises.get(i).get(0).getWeight();
					bestSet = maxWeight(bestSet, heaviestWeight, i);
					allBestSets.put(exerciseName, bestSet);
				} else {
					heaviestWeight = allBestSets.get(exerciseName).getWeight();
					bestSet = maxWeight(bestSet, heaviestWeight, i);
					allBestSets.replace(exerciseName, bestSet);
				}
				
				for (StrengthExercise ex : allExercises.get(i)) {
					ex.getWeight();
				}
			}
		}
		this.bestSets = allBestSets;
	}
	
	public HashMap<String, StrengthExercise> getBestSets() {
		return this.bestSets;
	}
	public StrengthExercise maxWeight(StrengthExercise bestSet, int heaviestWeight, int index) {
		// loop through ArrayList of StrengthExercise objects and return the object with the largest weight value 
		for (int j = 1; j < allExercises.get(index).size(); j++) {
			StrengthExercise ex = allExercises.get(index).get(j); 
			if (ex.getWeight() > heaviestWeight) {
				heaviestWeight = ex.getWeight();
				bestSet = ex;
			}
		}
		return bestSet;
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
		} else {
			str += "empty"; // remove later, for debugging
		}
		return str;
	}
}
