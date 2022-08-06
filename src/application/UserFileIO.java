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

/**
 * A class for reading and writing a user's information and workout history to a file.
 * Uses the information in the file to set parameters of a UserInfo object
 * @author JC
 * 
 */
public class UserFileIO { 
	private UserInfo user; 
	private ExerciseChoices exerciseChoices;
	// indices representing line numbers (starting at line 0) for the format of the text file that stores user information
	private int nameIndex = 0;
	private int numWorkoutsIndex = 1; 
	private int userHeightIndex = 2; 
	private int userWeightsIndex = 3; // Later: CSV bodyweights, should be a method somewhere that adds / updates this
	private int dateIndex = 4; // Later: CSV dates, corresponding to bodyweight for BMI calculation
	private int recordsStartIndex = 5;	
	
	/**
	 * Read a file of the user's information and store the values in the user object
	 * @param fileName The name of the file to be read. eg "src/userName.txt"
	 */
	public UserFileIO(String fileName) {
		user = new UserInfo();
		exerciseChoices = new ExerciseChoices();
		try {
			parseUserInfoArray(readWorkout(fileName));
		} catch(NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
			nfe.printStackTrace();
		}catch (FileNotFoundException fnfe) {
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
	 * Read the user's information and workout history from a file and put each line into an ArrayList of String
	 * @param fileName The filename that the user's information is store in
	 * @return the lines of the read from the text file
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
	 * Write the user's workout data to a file. Writes over the existing file with information
	 * from the user object's parameters, including the user's name, height, and body weight, 
	 * the date, the number of workouts done, and the user's personal best weight lifted for each exercise they have performed
	 * @param fileName The file name which the method writes to 
	 * @throws IOException
	 */
	public void writeWorkout(String fileName) throws IOException {
		int endOfFileIndex = recordsStartIndex + exerciseChoices.getExerciseList().size(); 
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		PrintWriter pWriter = new PrintWriter(writer);
		for (int i = 0; i < endOfFileIndex; i++) {
			if (i == nameIndex) {
				pWriter.println(user.getUserName());
			} else if (i == numWorkoutsIndex) {
				pWriter.println(user.getNumWorkoutsDone());
			} else if (i == userHeightIndex) {
				pWriter.println(user.getHeight());
			} else if (i == userWeightsIndex) {
				pWriter.println(user.getBodyWeight());
			} else if (i == dateIndex) {
				pWriter.println(dateToString(user.getDate())); 
			}else if (i >= recordsStartIndex) { 
				// loop through personalRecords HashMap and write to file as comma separated values
				user.getPersonalRecords().forEach((exerciseName, weight) -> {
					pWriter.println(exerciseName + "," + weight);
				});
				break;
			}
		}

		pWriter.close();
	}
	
	/**
	 * Parses the text read from a user file into usable data.
	 * This method gets the contents of each line of a text file and sets the values parsed from
	 * the text to parameters of the user object.
	 * Values include the user's name, height, and body weight, the date, the number of workouts done, 
	 * and the user's personal best weight lifted for each exercise they have performed
	 * @param a list containing the lines text read from the text file 
	 * @throws NumberFormatException 
	 */
	public void parseUserInfoArray(ArrayList<String> array) throws NumberFormatException{ // I don't think this needs to throw FileNotFoundException or IOException
		// Use the data stored in the ArrayList to set the properties of the user object
		System.out.println(array);
		ArrayList<String> exerciseChoicesList = exerciseChoices.getExerciseList(); 
		ArrayList<String[]> personalRecordsList = new ArrayList<String[]>();// do you need to set the size of the String array here?
		try {
			int recordsEndIndex = recordsStartIndex + exerciseChoicesList.size();
			
			for (int i = 0; i < array.size(); i++) {
				String line = array.get(i);
				// Sets the user parameters with the appropriate element in the list
				if (i == nameIndex) {
					user.setUserName(line);
					
				} else if (i == numWorkoutsIndex) {
					user.setNumWorkoutsDone(Integer.parseInt(line) + 1);
					
				} else if (i == userHeightIndex) {
					user.setHeight(Double.parseDouble(line));
					
				} else if (i == userWeightsIndex) {
					user.setBodyWeight(Double.parseDouble(line));
					
				} else if (i == dateIndex) {
					user.setDate(stringToDate(line));
					
				}else if (i >= recordsStartIndex && i <= recordsEndIndex) { 
					// split line into two strings containing exercise name and pr weight and add to array
					String[] pr = array.get(i).split(",");  
					personalRecordsList.add(pr);
				} 
			}
			user.setPersonalRecords(parsePersonalRecords(personalRecordsList));
		} catch (NumberFormatException nfe) {
			throw new NumberFormatException("Error: Could not parse value from file."); // write proper error message
		}
	}
	
	/**
	 * Converts lines of text read from a file into a map containing the user's personal records for the best lifts they have performed
	 * @param personalRecordsList a list containing the user's personal records
	 * @return the user's personal records
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
	 * Converts a given Date object to a string in the form "dd-MM-yyyy"
	 * @param date the date to be converted
	 * @return The properly formatted date
	 */
	public String dateToString(Date date) {
		//Set the date format to day-month-year
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return simpleDateFormat.format(date);
	}
	
	/**
	 * Converts a String in the format "dd-MM-yyyy" into a Date object
	 * @param dateString a date in the format "dd-MM-yyyy"
	 * @return the date that was created from the input String
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
}
