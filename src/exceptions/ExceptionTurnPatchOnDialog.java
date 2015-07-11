package exceptions;

import javafx.scene.control.Alert;

/**
 *
 * @author Stef
 */
public class ExceptionTurnPatchOnDialog implements ExceptionDialog{
    
    public static void showDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Turn Patch On Error");
        alert.setHeaderText("Error occured while trying to turn the patch on.");
        alert.setContentText("You probably do not have the voobly patch installed yet.");

        alert.showAndWait();
    }
    
}
