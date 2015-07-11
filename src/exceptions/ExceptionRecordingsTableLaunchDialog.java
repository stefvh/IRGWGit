package exceptions;

import javafx.scene.control.Alert;

/**
 *
 * @author Stef
 */
public class ExceptionRecordingsTableLaunchDialog implements ExceptionDialog{
    
    public static void showDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Recodings Table Launch Error");
        alert.setHeaderText("Error occured while trying to generate your recording games");
        alert.setContentText("Make sure you have selected .rcx files and have updated your Java!");

        alert.showAndWait();
    }
}
