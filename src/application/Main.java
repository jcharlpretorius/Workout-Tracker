package application;
	
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

/**
 * This class is the base for the javafx workout application.
 * It extends the Application class and loads the scene
 * @author JC
 *
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			VBox root = loader.load(new FileInputStream("src/application/WorkoutSelectionView.fxml"));
			
			// use FXMLLoader to get the controller and 'give' the primaryStage to the controller
			WorkoutSelectionViewController selectionController = (WorkoutSelectionViewController)loader.getController();
			selectionController.setApplicationStage(primaryStage);
			
			Scene scene = new Scene(root,320,200);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Workout Application");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The main method's function is to launch the javafx application
	 * @param args
	 */
	public static void main(String[] args) {
		// launch the workout application
		launch(args);
	}
}
