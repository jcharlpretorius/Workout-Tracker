package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class UserFileIO { // maybe name this class to something more descriptive
	private UserInfo user = new UserInfo(); // call default constructor in case file reading fails. ? i don't know what I'm doing anymore
	private ArrayList<String> exerciseChoices;
	// indexes representing line numbers (starting at line 0) for the format of the text file that stores user information
	private int nameIndex = 0;
	private int numWorkoutsIndex = 1; 
	private int userHeightIndex = 2; 
	private int userWeightsIndex = 3; // CSV bodyweights, should be a method somewhere that adds / updates this
	private int dateIndex = 4; // CSV dates, corresponding to bodyweight for BMI calculation
	private int recordsStartIndex = 5;	
	
	
	
//	private ArrayList<String> userInfoArray;
	public UserFileIO(String fileName)  {
		// TODO Auto-generated constructor stub
		// read a file and create a UserInfo object to store the data
		// could create our own exception and throw it from here.
		try {
			parseUserInfoArray(readWorkout(fileName));
		} catch(NumberFormatException nfe) {
			// message here??
			System.out.println(nfe.getMessage());
			nfe.printStackTrace();
		}catch (FileNotFoundException fnfe) {
			//
			System.out.println("Error: file " + fileName + " could not be found.");
			fnfe.printStackTrace();
		}catch (IOException e) { 
			e.printStackTrace();
		} 
	}
	
	public void setUser(UserInfo user) {
		this.user = user; 
	}
	
	public UserInfo getUser() {
		return this.user;
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
	/**
	 * Write the user's workout data to a file. Writes over the existing file 
	 * @param fileName
	 * @throws IOException
	 */
	public void writeWorkout(String fileName) throws IOException {
		// 
		int endOfFileIndex = recordsStartIndex + user.getExerciseArrayList().size();
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PrintWriter pWriter = new PrintWriter(writer);
		for (int i = 0; i < endOfFileIndex; i++) {
			//pWriter.println();
			// maybe make this a while loop, then set condition to false if reach the end of the personal records HashMap?
			String line = "";
			if (i == nameIndex) {
				pWriter.println(user.getUserName());
			} else if (i == numWorkoutsIndex) {
				pWriter.println(user.getNumWorkoutsDone());
			} else if (i == userHeightIndex) {
				pWriter.println(user.getHeight());
			} else if (i == userWeightsIndex) {
				pWriter.println(user.getBodyWeight());
				user.setBodyWeight(Double.parseDouble(line));
			} else if (i == dateIndex) {
				pWriter.println(dateToString(user.getDate())); 
			}else if (i >= recordsStartIndex) { // Make sure to check that your comparators are correct: 
//				System.out.println(array.get(i));
				// get and arrayList of string arrays and print them in a for loop
//				pWriter.println(arr[0] + "," + arr[1]); // something like this
		
			} // there shouldn't be anything after this
		}
	
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
				user.setDate(stringToDate(line));
			}else if (i >= recordsStartIndex && i < recordsEndIndex) { // Make sure to check that your comparators are correct. Can probably remove the second conditional 
//				System.out.println(array.get(i));
				// split line into two strings containing exercise name and pr weight and add to array
				String[] pr = array.get(i).split(",");  
				personalRecordsList.add(pr);
			} // there shouldn't be anything after this
		}
//		parsePersonalRecords(personalRecordsList);
		user.setPersonalRecords(parsePersonalRecords(personalRecordsList));
	}
	
	/**
	 * Populates a HashMap containing a record of the personal best lifts the user has performed
	 * @param personalRecordsList
	 * @return
	 * @throws NumberFormatException
	 */
	public HashMap<String, Integer> parsePersonalRecords(ArrayList<String[]> personalRecordsList) throws NumberFormatException{
		HashMap<String, Integer> personalRecords = new HashMap<String, Integer>();
		// loop through ArrayList of String arrays containing personal records for each exercise choice and add them to a HashMap
		for (String[] pr : personalRecordsList) {
			String exerciseName = pr[0];
			try {
				int weight = Integer.parseInt(pr[1]);
				personalRecords.put(exerciseName, weight);
			} catch (NumberFormatException nfe) {
				throw new NumberFormatException("Error: Could not parse pr weight string to an integer value.");
			}
		}
		return personalRecords;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public String dateToString(Date date) {
		// Converts a given Date object to a string. 
		//Set the date format to day-month-year
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * Converts a given string in the specified format into a Date object
	 * @param dateString a string representing a date, in the format "dd-MM-yyyy"
	 * @return a Date object
	 */
	public Date stringToDate(String dateString) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		try {
			date = simpleDateFormat.parse(dateString);
		} catch (ParseException e) {
			System.out.println("Error: date String could not be parsed by SimpleDateFormat.");
			e.printStackTrace();
		}
		return date;
	}
	// need to pass the best Sets hashmap to this class so I can compare the values to the current pr'r
	// maybe UserInfo extends UserFileIO? then userInfo will get all the methods/instance variables this class has
	// you could construct a new userInfo object by passing in the fileName/path string and use the parent class's constructor
	
	
	
}
