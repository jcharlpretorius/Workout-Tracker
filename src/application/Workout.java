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
		// add ArrayList of StrengthExercises (represents sets) to the HashMap
		// // // // This is where I need to make big changes if not using hashmap
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
//		if (!this.getAllExercises().isEmpty()) {
//			// remove sets if already added -> prevents adding another set when re-entering the set window
//			if (this.allExercises.containsKey(exerciseNumber)) {
//				this.allExercises.remove(exerciseNumber);
//			}
//			this.allExercises.put(exerciseNumber, exercisesDone);
//		} else {
//			this.allExercises.put(exerciseNumber, exercisesDone);
//		}
//		System.out.println(allExercises);
	}
		
	public ArrayList<ArrayList<StrengthExercise>> getAllExercises() {
		// I don't know if this should be changed to prevent privacy leaks. 
		return this.allExercises;
	}
	
	public void setTotalWeightLifted() {
		// iterate through the allExercises ArrayList and add up all lifts (repetitions multiplied by weight)
//		System.out.println(allExercises);
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
		// loop through the HashMap and find the set with the heavies weight lifted
		HashMap<String, StrengthExercise> allBestSets = new HashMap<String, StrengthExercise>();
		if (!this.allExercises.isEmpty()) {
			// If bestSets is null, add the first set of the first exercise
//			if (this.bestSets == null) {
//				this.bestSets.put(allExercises.get(0).get(0).getName(), allExercises.get(0).get(0));
//			} 
			for (int i = 0; i < allExercises.size(); i++) {
				String exerciseName = allExercises.get(i).get(0).getName();
//				StrengthExercise bestSet = new StrengthExercise();
				// set best set to the first set of the exercise at index i
				
					if (allBestSets.containsKey(exerciseName)) {
						StrengthExercise heaviestSet = allBestSets.get(exerciseName);
						heaviestSet = getHeaviestSet(heaviestSet, i);
						allBestSets.replace(exerciseName, heaviestSet);
					} else {
						StrengthExercise heaviestSet = allExercises.get(i).get(0);
						heaviestSet = getHeaviestSet(heaviestSet, i);
						allBestSets.put(exerciseName, heaviestSet);
					}
				
//					if (!(allBestSets.containsKey(exerciseName))) {
//						// set the heaviest weight to the weight of the first set
//						heaviestWeight = allExercises.get(i).get(0).getWeight();
//						bestSet = getHeaviestSet(bestSet, heaviestWeight, i);
//						allBestSets.put(exerciseName, bestSet);
//						System.out.println("put set in bestSets, doesn't contain key");
//					} else {
//						heaviestWeight = allBestSets.get(exerciseName).getWeight();
//						bestSet = getHeaviestSet(bestSet, heaviestWeight, i);
//						allBestSets.replace(exerciseName, bestSet);
//						System.out.println("put set in bestSets, contains key");
//
//					}

				// what is this used for?
//				for (StrengthExercise ex : allExercises.get(i)) {
//					ex.getWeight();
//				}
			}
		}
		this.bestSets = allBestSets;
	}
	
	public HashMap<String, StrengthExercise> getBestSets() {
		return this.bestSets;
	}
	public StrengthExercise getHeaviestSet(StrengthExercise bestSet, int index) {
		// loop through ArrayList of StrengthExercise objects and return the object with the largest weight value 
		int heaviestWeight = bestSet.getWeight();
		for (int j = 1; j < allExercises.get(index).size(); j++) {
			StrengthExercise ex = allExercises.get(index).get(j); 
			if (ex.getWeight() > heaviestWeight) {
				heaviestWeight = ex.getWeight();
				bestSet = ex;
			} else if ((ex.getWeight() == heaviestWeight) && (ex.getReps() > bestSet.getReps())) {
				// if weight is equal take the set with more repetitions
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
		// initializes ArrayList size
		if (this.allExercises == null) {
			this.allExercises = new ArrayList<ArrayList<StrengthExercise>>(numExercises);
		}
	}
}
