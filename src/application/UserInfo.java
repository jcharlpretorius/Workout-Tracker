package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class UserInfo {
	private String userName;
	private Double bodyWeight;
	private Double height; 
	private int numWorkoutsDone;
	private Date date;
	HashMap<String, Integer> personalRecords;

	public UserInfo() {
		// TODO Auto-generated constructor stub
	}
	/**
    * Returns a list of exercise choices
    * @return
    */
    public ArrayList<String> createExerciseArrayList() {
    	// temporary solution for populating exercise ChoiceBoxes 
    	// pull this out to a method or class later. Or read from a file.

    	ArrayList<String> exercises = new ArrayList<String>();
    	exercises.add("Squat");
    	exercises.add("Bench Press");
    	exercises.add("Dead Lift");
    	exercises.add("Overhead Press");
    	exercises.add("Barbell Row");
    	exercises.add("Bicep Curl");
    	exercises.add("Tricep Push-downs");
    	exercises.add("Lateral Raises");
    	exercises.add("Pull ups");
    	exercises.add("Dips");
    	
    	return exercises;
    }

	String getUserName() {
		return userName;
	}

	void setUserName(String userName) {
		this.userName = userName;
	}

	Double getBodyWeight() {
		return bodyWeight;
	}

	void setBodyWeight(Double bodyWeight) {
		this.bodyWeight = bodyWeight;
	}

	Double getHeight() {
		return height;
	}

	void setHeight(Double height) {
		this.height = height;
	}
	
	int getNumWorkoutsDone() {
		return numWorkoutsDone;
	}

	void setNumWorkoutsDone(int numWorkoutsDone) {
		this.numWorkoutsDone = numWorkoutsDone;
	}

	Date getDate() {
		return date;
	}

	void setDate(Date date) {
		this.date = date;
	}

}
