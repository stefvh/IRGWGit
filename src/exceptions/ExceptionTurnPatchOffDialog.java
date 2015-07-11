package exceptions;

import javafx.scene.control.Alert;

/**
 *
 * @author Stef
 */
public class ExceptionTurnPatchOffDialog implements ExceptionDialog {
    
    public static void showDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Turn Patch Off Error");
        alert.setHeaderText("Error occured while trying to turn the patch off.");
        alert.setContentText("Make sure you did not remove the voobly patch folder beforehand.");

        alert.showAndWait();
    }
    
}
