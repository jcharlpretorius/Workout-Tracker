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

	public UserInfo() {

		date = new Date(); 
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
}
