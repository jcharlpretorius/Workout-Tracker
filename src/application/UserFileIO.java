package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class UserFileIO { // maybe name this class to something more descriptive
	private UserInfo user;
	private ArrayList<String> exerciseChoices;
	// indexes representing line numbers (starting at line 0) for the format of the text file that stores user information
	private int nameIndex = 0;
	private int numWorkoutsIndex = 1; 
	private int userHeightIndex = 2; 
	private int userWeightsIndex = 3; // CSV bodyweights, should be a method somewhere that adds this
	private int dateIndex = 4; // CSV dates, corresponding to bodyweight
	private int recordsStartIndex = 5;	
	
	
	
//	private ArrayList<String> userInfoArray;
	public UserFileIO(String fileName)  {
		// TODO Auto-generated constructor stub
		// read a file and create a UserInfo object to store the data
		try {
			parseUserInfoArray(readWorkout(fileName));
		} catch(NumberFormatException nfe) {
			// message
			nfe.printStackTrace();
		}catch (FileNotFoundException fnfe) {
			//
			fnfe.printStackTrace();
		}catch (IOException e) { 
			e.printStackTrace();
		} 
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
	//
	public static void writeWorkout(String fileName) throws IOException {
		// write the user's workout data to a file. Currently it writes over the file ever time
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PrintWriter pWriter = new PrintWriter(writer);
		// should access the same index variables as parseUserInfoArray. Maybe store them somewhere. Instance variables?
		pWriter.close();
	}
	
	// maybe move this one to the top of the class
	public void parseUserInfoArray(ArrayList<String> array) throws NumberFormatException{ // I don't think this needs to throw FileNotFoundException or IOException
		// Use the data stored in the ArrayList to set the properties of the user object
		ArrayList<String> exerciseChoicesList = user.getExerciseArrayList(); 
		ArrayList<String[]> personalRecordsList = new ArrayList<String[]>();// do you need to set the size of the String array here?
		// You can change these values depending on which line number the information is on in the text file,
		
		int recordsEndIndex = recordsStartIndex + exerciseChoicesList.size();
		
		for (int i = 0; i < array.size(); i++) {
			String line = array.get(i);
			
			if (i == nameIndex) {
				user.setUserName(line);
			} else if (i == numWorkoutsIndex) {
				user.setNumWorkoutsDone(i);
			} else if (i == userHeightIndex) {
				user.setHeight(Double.parseDouble(line));
			} else if (i == userWeightsIndex) {
				user.setBodyWeight(Double.parseDouble(line));
			} else if (i == dateIndex) {
				// how to parse string date to Date object? see simpleDateFormat. create methods to convert string to date and the other way around (for writer)
//				Date date = new DateFormat.parse(line);
//				user.setDate(Date(line));			
			}else if (i >= recordsStartIndex && i < recordsEndIndex) { // Make sure to check that your comparators are correct: 
//				System.out.println(array.get(i));
				// split line into two strings containing exercise name and pr weight and add to array
				String[] pr = array.get(i).split(",");  
				personalRecordsList.add(pr);
			} // there shouldn't be anything after this
		}
		parsePersonalRecords(personalRecordsList);
		
	}
	
	public HashMap<String, Integer> parsePersonalRecords(ArrayList<String[]> personalRecordsList) throws NumberFormatException{
		HashMap<String, Integer> personalRecords = new HashMap<String, Integer>();
		// loop through ArrayList of String arrays containing personal records for each exercise choice and add them to a HashMap
		for (String[] pr : personalRecordsList) {
			String exerciseName = pr[0];
			int weight = Integer.parseInt(pr[1]);
			personalRecords.put(exerciseName, weight);
		}
		return personalRecords;
	}
	
	
	
}
