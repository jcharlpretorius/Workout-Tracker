package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;

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
	
	/**
	 * Compares the HashMaps of personal bests with the workout's best sets and 
	 * returns an ArrayList containing the names of which exercises a personal record
	 * was broken during the workout
	 * @param personalRecordsMap a HashMap containing the personal record of the user, read from a file
	 * @return an ArrayList of Strings with the names of the exerices for which a new record was set
	 */
	public ArrayList<String> checkPersonalBests(HashMap<String, Integer> personalRecordsMap) {
		// iterate through the bestSets map and check if a mapping for that name/key
		// exists. If it doesn't exits then add it to the PRMap and to the arrayList(?) of pr's. 
		ArrayList<String> prList = new ArrayList<String>();
		// Loop through this.bestSets map
		// null pointer exception here if we look for personalRecordsMap.containsKey(Object) because exerciseName is null
		if (!bestSets.isEmpty()) {
			bestSets.forEach((exerciseName, heaviestSet) -> {
				// do something
				if (personalRecordsMap.containsKey(exerciseName)) {
					// if the user's best set weight is greater than current PR, then add it to PR list to be returned and update pr HashMap
					if (heaviestSet.getWeight() > personalRecordsMap.get(exerciseName)) {
						prList.add(exerciseName);
						personalRecordsMap.replace(exerciseName, heaviestSet.getWeight()); // should work because we passed in a reference to the map
					}
				} else {
					// personal record hasn't been recorded yet, counts as a pr
					prList.add(exerciseName);
					personalRecordsMap.put(exerciseName, heaviestSet.getWeight());
				}
			});
		}
		return prList;
	}
}
