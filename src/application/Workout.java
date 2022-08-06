package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;


/**
 * The Workout class is a representation of a strength workout.
 * It contains information about all of the exercise sets including their number of 
 * repetitions and weights lifted. Workout contains methods to calculate values 
 * about the workout as a whole, such as the total amount of weight lifted and what 
 * the best sets the user performed are
 * @author JC
 *
 */
public class Workout {
//	private ArrayList<ArrayList<StrengthExercise>> allExercises;
//	private ArrayList<ExerciseSets> allExercises;
	private HashMap<Integer, ExerciseSets> allExercises;
	private HashMap<String, StrengthExercise> bestSets;
	private int totalWeightLifted;
	
	public Workout() {
		allExercises = new HashMap<Integer, ExerciseSets>();
	}

	/**
	 * 
	 * @param exerciseSets
	 */
	public void setAllExercises(ExerciseSets exerciseSets) {
		// add ArrayList of StrengthExercises (represents sets) to the allExercises HashMap
		int exerciseNumber = exerciseSets.getExerciseNumber();
		// Makes sure you don't call .containsKey() on empty HashMap
		if (!this.getAllExercises().isEmpty()) {
			if (allExercises.containsKey(exerciseNumber)) { 
				// replace set if already added to prevent adding another set when re-entering the set window
				allExercises.replace(exerciseNumber, exerciseSets);
			} else {
				allExercises.put(exerciseNumber, exerciseSets);
			}
		} else {
			allExercises.put(exerciseNumber, exerciseSets);
		}
	}
		
	/**
	 * 
	 * @return
	 */
	public HashMap<Integer, ExerciseSets> getAllExercises() { // Do I even use this method? does it need privacy plug?
		return this.allExercises;
	}
	
	public void setTotalWeightLifted() {
		// iterate through the allExercises ArrayList and add up all lifts (repetitions multiplied by weight)
		int totalWeightLifted = 0;
		if (!allExercises.isEmpty()) {
			// loop through the allExercises HashMap
			Set<Integer> exerciseNumbers = allExercises.keySet();
			for (Integer exerciseNumber : exerciseNumbers) {
				int weightLiftedPerSet = 0;
				ArrayList<StrengthExercise> set = allExercises.get(exerciseNumber).getAllSets();
				// loop through the ArrayList of Strength exercises 
				for (StrengthExercise exercise : set) {
					// multiply the reps and the weight and add to the total
					weightLiftedPerSet += exercise.getReps() * exercise.getWeight();
				}
				totalWeightLifted += weightLiftedPerSet;
			}
		}
		this.totalWeightLifted = totalWeightLifted;
	}
	
	public int getTotalWeightLifted() {
		return this.totalWeightLifted;
	}

	/**
	 * 
	 */
	public void setBestSets() {
		HashMap<String, StrengthExercise> allBestSets = new HashMap<String, StrengthExercise>();
		if (!this.allExercises.isEmpty()) {
			Set<Integer> exerciseNumbers = allExercises.keySet();
			for (Integer exerciseNumber : exerciseNumbers) {
				String exerciseName = allExercises.get(exerciseNumber).getExerciseName();
				// check if an earlier exercises is the same as the current one and already in the bests sets HashMap
				if (allBestSets.containsKey(exerciseName)) {
					StrengthExercise heaviestSet = allBestSets.get(exerciseName);
					heaviestSet = getHeaviestSet(heaviestSet, exerciseNumber);
					allBestSets.replace(exerciseName, heaviestSet);
				} else {

					// if the exercise hasn't been done in an earlier set, set the heaviest set to the first set
					StrengthExercise heaviestSet = allExercises.get(exerciseNumber).getAllSets().get(0);
					heaviestSet = getHeaviestSet(heaviestSet, exerciseNumber);
					allBestSets.put(exerciseName, heaviestSet);
				}
			}
		}
		this.bestSets = allBestSets;
//		// print statements for debugging:
//		Set<Integer> exerciseNumbers = allExercises.keySet();
//		for (Integer exerciseNumber : exerciseNumbers) {
//			ArrayList<StrengthExercise> list = getAllExercises().get(exerciseNumber).getAllSets();
//			for (StrengthExercise ex : list) {
//				System.out.println(ex);
//			}
//		}

	}
	
	public HashMap<String, StrengthExercise> getBestSets() {
		return this.bestSets; // could be plugged, used in the 
	}
	
	/**
	 * 
	 * @param bestSet
	 * @param index
	 * @return
	 */
	public StrengthExercise getHeaviestSet(StrengthExercise bestSet, int index) {
		int heaviestWeight = bestSet.getWeight();
		// loop through ArrayList of StrengthExercise objects and return the exercise with the largest weight value 
		for (int j = 1; j < allExercises.get(index).getAllSets().size(); j++) {
			StrengthExercise ex = allExercises.get(index).getAllSets().get(j); 
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
	
	/**
	 * 
	 */
	public String toString() {
		String output = "";
		if (!this.allExercises.isEmpty()) {
			for (int i = 0; i < this.allExercises.size(); i++) {
				String str = "";
				for (int j = 0; j < this.allExercises.get(i).getAllSets().size(); j++) {
					str += "Exercise number: " + i + ", " + this.allExercises.get(i).getAllSets().get(j).toString() + "\n";
				}
				output += str;
			}
		} 
		return output;
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
		// Loop through bestSets map and check if the weight lifted in the best set is greater than the user's personal best weight
		if (!bestSets.isEmpty()) {
			bestSets.forEach((exerciseName, heaviestSet) -> {
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
