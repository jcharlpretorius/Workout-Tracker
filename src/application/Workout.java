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
	private HashMap<Integer, ExerciseSets> allExercises;
	private HashMap<String, StrengthExercise> bestSets;
	private int totalWeightLifted;
	
	
	/**
	 * Constructor for the Workout object. It initializes the map of all exercise done in the workout
	 * to a empty HashMap.
	 */
	public Workout() {
		allExercises = new HashMap<Integer, ExerciseSets>();
	}

	/**
	 * Sets the map containing information about all of the exercise sets done in the workout.
	 * It associates all the exercise sets of a specific exercise with that exercise's position 
	 * in the overall workout. For example: if the second series of exercises performed in the workout is 4 sets of 
	 * Bench Press, then the ExerciseSets object containing those 4 sets of Bench Press is the value that will be 
	 * associated with key "2". This mapping is used to ensure only 1 ExerciseSets object is stored for each position
	 * in the workout. 
	 * @param exerciseSets the exercise sets to be added to the allExercises HashMap
	 */
	public void setAllExercises(ExerciseSets exerciseSets) {
		// add ArrayList of StrengthExercises (represents sets) to the allExercises HashMap
		int exerciseNumber = exerciseSets.getExerciseNumber();
		// Makes sure you don't call .containsKey() on empty HashMap
		if (!this.allExercises.isEmpty()) {
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
	 * Sets the value for the total amount of weight lifted in the workout. This method iterates through
	 * every set of exercises done in the workout and sums the product of the number of repetition and the
	 * weight lifted for each exercise.  
	 */
	public void setTotalWeightLifted() {
		// iterate through the allExercises HashMap and add up all lifts (repetitions multiplied by weight)
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
				// Add the total of each set to the total weight lifted in the workout
				totalWeightLifted += weightLiftedPerSet;
			}
		}
		this.totalWeightLifted = totalWeightLifted;
	}
	
	/**
	 * Gets the total amount of weight lifted in the workout. The total weight lifted is the sum 
	 * of the product of the number of repetition and the weight lifted for each exercise.  
	 * @return the value of the total weight lifted in the workout
	 */
	public int getTotalWeightLifted() {
		return this.totalWeightLifted;
	}

	/**
	 * The setBestSets method finds the exercise set in which the user lifted the heaviest weight for
	 * each exercise performed. 
	 */
	public void setBestSets() {
		// Create a new HashMap to store the best sets
		HashMap<String, StrengthExercise> allBestSets = new HashMap<String, StrengthExercise>();
		
		if (!this.allExercises.isEmpty()) {
			// Iterate through the map of all exercises
			Set<Integer> exerciseNumbers = allExercises.keySet();
			for (Integer exerciseNumber : exerciseNumbers) {
				String exerciseName = allExercises.get(exerciseNumber).getExerciseName();
				
				// check if an earlier exercises is the same as the current one and already in the bests sets HashMap
				if (allBestSets.containsKey(exerciseName)) {
					StrengthExercise heaviestSet = allBestSets.get(exerciseName);
					// Call a function to find the heaviest set
					heaviestSet = getHeaviestSet(heaviestSet, exerciseNumber);
					allBestSets.replace(exerciseName, heaviestSet);
				
				} else {
					// if the exercise hasn't been done in an earlier set, set the heaviest set to the first set
					StrengthExercise heaviestSet = allExercises.get(exerciseNumber).getAllSets().get(0);
					// Call a method to find the heaviest set
					heaviestSet = getHeaviestSet(heaviestSet, exerciseNumber);
					allBestSets.put(exerciseName, heaviestSet);
				}
			}
		}
		this.bestSets = allBestSets;
	}
	
	/**
	 * Gets the best set performed in the workout. The best sets are the sets in which the user lifted
	 * the heaviest weight
	 * @return a map of the workout's best sets 
	 */
	public HashMap<String, StrengthExercise> getBestSets() {
		return this.bestSets; // could be plugged, used in the 
	}
	
	/**
	 * The getHeavietSet method compares an individual exercise set to a list of all sets for that specific exercise
	 * and find the heaviest set. It return the set with the heaviest weight, or if there are two sets tied for the 
	 * heaviest weight it will return the one with the greater number of repetitions 
	 * @param bestSet the current best set that gets compared to all other sets in the list
	 * @param index the exercise number which corresponds to the position in the workout of the list of sets to be compared 
	 * @return the heaviest set
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
	 * Compares the HashMaps of personal bests with the workout's best sets and 
	 * returns an ArrayList containing the names of which exercises a personal record
	 * was broken during the workout
	 * @param personalRecordsMap a HashMap containing the personal record of the user, read from a file
	 * @return an ArrayList of Strings with the names of the exerices for which a new record was set
	 */
	public ArrayList<String> checkPersonalBests(HashMap<String, Integer> personalRecordsMap) {
		// iterate through the bestSets map and check if a mapping for that name/key
		// exists. If it doesn't exits then add it to the PR Map and to the arrayList of pr's. 
		ArrayList<String> prList = new ArrayList<String>();
		// Loop through bestSets map and check if the weight lifted in the best set is greater than the user's personal best weight
		if (!bestSets.isEmpty()) {
			bestSets.forEach((exerciseName, heaviestSet) -> {
				if (personalRecordsMap.containsKey(exerciseName)) {
					// if the user's best set weight is greater than current PR, then add it to PR list to be returned and update pr HashMap
					if (heaviestSet.getWeight() > personalRecordsMap.get(exerciseName)) {
						prList.add(exerciseName);
						personalRecordsMap.replace(exerciseName, heaviestSet.getWeight()); // works correctly because we passed in a reference to the map
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
