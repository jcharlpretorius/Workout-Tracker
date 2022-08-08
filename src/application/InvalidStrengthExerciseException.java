package application;

/**
 * Thrown to indicate that the application has attempted to enter an invalid value for the properties of a StrengthExercise object
 * @author JC
 *
 */
public class InvalidStrengthExerciseException extends Exception {
	
	public InvalidStrengthExerciseException() {
		// TODO Auto-generated constructor stub
		try {
			
		} catch (NumberFormatException nfe) {
			
		}
	}

	public InvalidStrengthExerciseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidStrengthExerciseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidStrengthExerciseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidStrengthExerciseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
