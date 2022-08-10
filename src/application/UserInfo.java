package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * The UserInfo class contains the basic physical information about a user and
 * the history of workouts they have completed. 
 * Contains parameters for the user's name, height, and body weight, the current date, the number of workouts done, 
 *and a list of the user's personal best weight lifted for each exercise they have performed
 * @author JC
 *
 */
public class UserInfo {
	private String userName;
	private Double bodyWeight;
	private Double height; 
	private int numWorkoutsDone;
	private Date date;
	private HashMap<String, Integer> personalRecords;

	/**
	 * Creates a new user information object and sets the date to the current date 
	 */
	public UserInfo() {
		date = new Date(); 
	}
	
	/**
	 * Gets the name of the user
	 * @return a String containing the user name
	 */
	public String getUserName() {
		return new String(userName);
	}

	/**
	 * Sets the user name 
	 * @param userName the name which is used to set the userName property
	 */
	public void setUserName(String userName) {
		this.userName = new String(userName);
	}

	/**
	 * Gets the body weight of the user
	 * @return the value of the user's bodyweight
	 */
	public Double getBodyWeight() {
		return bodyWeight;
	}

	/**
	 * Set the bodyweight of the user
	 * @param bodyWeight the weight value used to set the weight property
	 */
	public void setBodyWeight(Double bodyWeight) {
		this.bodyWeight = bodyWeight;
	}

	/**
	 * Gets the height of the user
	 * @return the value of the user's height
	 */
	public Double getHeight() {
		return height;
	}

	/**
	 * Sets the height of the user
	 * @param height the value used to set the height property
	 */
	public void setHeight(Double height) {
		this.height = height;
	}
	
	/**
	 * Gets the number of workouts done by the user
	 * @return the value of the number of workouts
	 */
	public int getNumWorkoutsDone() {
		return numWorkoutsDone;
	}

	/**
	 * Sets the number of workouts done by the user
	 * @param numWorkoutsDone the value used to set the number of workouts done property
	 */
	public void setNumWorkoutsDone(int numWorkoutsDone) {
		this.numWorkoutsDone = numWorkoutsDone;
	}

	/**
	 * Gets the date
	 * @return the Date object currently associated with the user
	 */
	public Date getDate() {
		return new Date(date.getTime());
	}
	
	/**
	 * Set the date 
	 * @param date the date to set the UserInfo object's date property
	 */
	public void setDate(Date date) {
		this.date = new Date(date.getTime());
	}
	
	/** 
	 * Gets all of the user's personal records. Personal records consist of the name of the exercise
	 * and the heaviest weight they have ever performed with that exercise  
	 * @return A map of the exercise name and the value of the weight associated with that exercise
	 */
	public HashMap<String, Integer> getPersonalRecords() {
		// pass a reference, HashMap is modified by Workout class checkPersonalBests()
		return personalRecords;
	}
	
	/**
	 * Sets the user's personal records. Personal records consist of the name of the exercise
	 * and the heaviest weight they have ever performed with that exercise    
	 * @param personalRecords A map of the exercise name and the value of the weight associated with that exercise
	 */
	public void setPersonalRecords(HashMap<String, Integer> personalRecords) {
		this.personalRecords = new HashMap<String, Integer>(personalRecords);
	}
}
