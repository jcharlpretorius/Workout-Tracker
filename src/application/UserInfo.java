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
	private HashMap<String, Integer> personalRecords;
	private ArrayList<String> exercisesList;

	public UserInfo() {
		setExerciseArrayList();
		date = new Date(); // Change this. How to get the current system time? - probably user simple date format
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
	HashMap<String, Integer> getPersonalRecords() {
		return personalRecords;
	}
	void setPersonalRecords(HashMap<String, Integer> personalRecords) {
		this.personalRecords = personalRecords;
	}

	/**
	 * Setter method for the exercisesList ArrayList 
	 * Contains a list of possible exercise choices
	 */
    public void setExerciseArrayList() {
    	ArrayList<String> exercisesList = new ArrayList<String>();
    	exercisesList.add("Squat");
    	exercisesList.add("Bench Press");
    	exercisesList.add("Dead Lift");
    	exercisesList.add("Overhead Press");
    	exercisesList.add("Barbell Row");
    	exercisesList.add("Bicep Curl");
    	exercisesList.add("Tricep Push-downs");
    	exercisesList.add("Lateral Raises");
    	exercisesList.add("Pull ups");
    	exercisesList.add("Dips");
    	
    	this.exercisesList = exercisesList;
    }
    public ArrayList<String> getExerciseArrayList() {
    	return this.exercisesList;
    }

}
