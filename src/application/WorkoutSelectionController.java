package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class WorkoutSelectionController {

    @FXML
    private ChoiceBox<Integer> numberOfExercisesChoiceBox;

    @FXML
    void getExercises(ActionEvent event) {
    	System.out.println("button pressed");
    	
    }

}
