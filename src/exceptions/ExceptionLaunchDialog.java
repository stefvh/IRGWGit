package exceptions;

import javafx.scene.control.Alert;

/**
 *
 * @author Stef
 */
public class ExceptionLaunchDialog implements ExceptionDialog{
    
    public static void showDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Launch Error");
        alert.setHeaderText("Error occured while trying to launch AoT.");
        alert.setContentText("AoT is probably already running...\n"
                + "If not, make sure you have the aomxnocd.exe file in the correct folder!");

        alert.showAndWait();
    }
    
}
