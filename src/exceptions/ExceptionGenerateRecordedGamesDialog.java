package exceptions;

import javafx.scene.control.Alert;

/**
 *
 * @author Stef
 */
public class ExceptionGenerateRecordedGamesDialog implements ExceptionDialog  {
    
    public static void showDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Generating Recorded Game Error");
        alert.setHeaderText("Error occured while trying to decode the .rcx file");
        alert.setContentText("This might be due to an invalid .rcx file");

        alert.showAndWait();
    }
    
}
