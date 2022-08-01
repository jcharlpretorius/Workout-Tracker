package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Workout {
	private ArrayList<ArrayList<StrengthExercise>> allExercises;
	private HashMap<String, StrengthExercise> bestSets;
	private int totalWeightLifted;
	
	public Workout() {
	}

	public void setAllExercises(int exerciseNumber, ArrayList<StrengthExercise> exercisesDone) {
		// add ArrayList of StrengthExercises (represents sets) to the allExercises ArrayList
		int index = exerciseNumber - 1;
		if (!this.getAllExercises().isEmpty()) {
			if (this.getAllExercises().size() > index) {
				// remove set if already added to prevent adding another set when re-entering the set window
				this.allExercises.remove(index);
				this.allExercises.add(index, exercisesDone);
			}
			this.allExercises.add(index, exercisesDone);
		} else {
			this.allExercises.add(exercisesDone);

		}
	}
		
	public ArrayList<ArrayList<StrengthExercise>> getAllExercises() {
		return this.allExercises;
	}
	
	public void setTotalWeightLifted() {
		// iterate through the allExercises ArrayList and add up all lifts (repetitions multiplied by weight)
		int totalWeightLifted = 0;
		if (!allExercises.isEmpty()) {
			for (ArrayList<StrengthExercise> set : allExercises) {
				int weightLiftedPerSet = 0;
				for (StrengthExercise ex : set) {
					weightLiftedPerSet += ex.getReps() * ex.getWeight();
				}
				totalWeightLifted += weightLiftedPerSet;
			}
		}
		this.totalWeightLifted = totalWeightLifted;
	}
	
	public int getTotalWeightLifted() {
		return this.totalWeightLifted;
	}
	
	public void setBestSets() {
		HashMap<String, StrengthExercise> allBestSets = new HashMap<String, StrengthExercise>();
		if (!this.allExercises.isEmpty()) {
			for (int i = 0; i < allExercises.size(); i++) {
				String exerciseName = allExercises.get(i).get(0).getName();
					if (allBestSets.containsKey(exerciseName)) {
						StrengthExercise heaviestSet = allBestSets.get(exerciseName);
						heaviestSet = getHeaviestSet(heaviestSet, i);
						allBestSets.replace(exerciseName, heaviestSet);
					} else {
						StrengthExercise heaviestSet = allExercises.get(i).get(0);
						heaviestSet = getHeaviestSet(heaviestSet, i);
						allBestSets.put(exerciseName, heaviestSet);
					}
			}
		}
		this.bestSets = allBestSets;
	}
	
	public HashMap<String, StrengthExercise> getBestSets() {
		return this.bestSets;
	}
	public StrengthExercise getHeaviestSet(StrengthExercise bestSet, int index) {
		int heaviestWeight = bestSet.getWeight();
		// loop through ArrayList of StrengthExercise objects and return the exercise with the largest weight value 
		for (int j = 1; j < allExercises.get(index).size(); j++) {
			StrengthExercise ex = allExercises.get(index).get(j); 
			if (ex.getWeight() > heaviestWeight) {
				heaviestWeight = ex.getWeight();
				bestSet = ex;
				
				// if weight is equal take the exercise with the higher number of repetitions
			} else if ((ex.getWeight() == heaviestWeight) && (ex.getReps() > bestSet.getReps())) {
				bestSet = ex;
			}
		}
		return bestSet;
	}
	
	public String toString() {
		String output = "";
		if (!this.allExercises.isEmpty()) {
			for (int i = 0; i < this.allExercises.size(); i++) {
				String str = "";
				for (int j = 0; j < this.allExercises.get(i).size(); j++) {
					str += "Exercise number: " + i + ", " + this.allExercises.get(i).get(j).toString() + "\n";
				}
				output += str;
			}
		} 
		return output;
	}
	
	public void setNumberOfExercises(int numExercises) {
		// Uses the number of exercises to initialize the allExercises ArrayList size
		if (this.allExercises == null) {
			this.allExercises = new ArrayList<ArrayList<StrengthExercise>>(numExercises);
		}
	}
}
