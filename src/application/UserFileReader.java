package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserFileReader { // maybe name this class to something more descriptive
	private UserInfo user;
	private ArrayList<String> exerciseChoices;
//	private ArrayList<String> userInfoArray;
	public UserFileReader() {
		// TODO Auto-generated constructor stub
		// read a file and create a UserInfo object to store it in
	}
	
	public void setUser(UserInfo user) {
		this.user = user; 
	}
	
	
	/**
	 * Read the user's information and workout history from a file and put each line into an ArrayList
	 * @param fileName A string containing the filename that the user's information is store in
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ArrayList<String>readWorkout(String fileName) throws FileNotFoundException, IOException{
		// should I make these methods static?
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		ArrayList<String> userInfoArray = new ArrayList<String>();
		String line = reader.readLine();
		
		while(line != null) {
			userInfoArray.add(line);
			line = reader.readLine();
		}
		reader.close();
		
		return userInfoArray;
	}
	public static void writeWorkout() {
		// write the user's workout data to a file
	}
	public void parseUserInfoArray(ArrayList<String> array) {
		// Use the data stored in the ArrayList to set the properties of the user object
		// store the indexes of data in a hashmap?
		ArrayList<String> exerciseChoices = user.createExerciseArrayList();
		for (int i = 0; i < array.size(); i++) {
			String line = array.get(i);
			
			if (i == 0) {
				user.setUserName(line);
			} else if (i == 1) {
				user.setNumWorkoutsDone(i);
//			} else if (i > 1 || i < arr.size())
			}	
		}
		
	}
	
	public HashMap<String, Integer> parsePersonalRecords() {
		HashMap<String, Integer> temp = new HashMap<String, Integer>();
		return temp;
	}
	
}
