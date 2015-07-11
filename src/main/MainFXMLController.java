package main;

import exceptions.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import recordings.RecordingsTableFXMLController;

/**
 *
 * @author Stef
 */
public class MainFXMLController {
    
    public Button launchButton;
    
    public Button showRecordedGamesDetailsButton;
    
    public ToggleButton patchToggleButton;
    
    private List<Stage> stages = new ArrayList<>();
    
    public void setStage(Stage stage){
        stages.add(stage);
    }
    
    public void close(){
        if(patchToggleButton.isSelected()){
            patchToggleButton.setSelected(false);
            toggle();
        }
        for(Stage stage : stages){
            stage.close();
        }
    }
    
    private MainModel model;
    
    public void setModel(MainModel model){
        this.model = model;
    }

    public void launch() {
        try {
            model.launch();
        } catch (IOException ex) {
            ExceptionLaunchDialog.showDialog();
        }
    }
    
    public void toggle(){
        if(patchToggleButton.isSelected()){
            try {
                model.turnPatchOn();
            } catch (IOException ex) {
                ExceptionTurnPatchOnDialog.showDialog();
            }
        } else {
            try {
                model.turnPatchOff();
            } catch (IOException ex) {
                ExceptionTurnPatchOffDialog.showDialog();
            }
        }
    }

    public void showRecordedGamesDetails() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the RCX files.");
        List<File> files = fileChooser.showOpenMultipleDialog(new Stage());
        
        if(files != null && validateRCX(files)){
            try {
                showRecordedGamesDetailsButton.setDisable(true);
                showRecordingsTable(files);
            } catch (IOException ex) {
                ExceptionRecordingsTableLaunchDialog.showDialog();
            }
        }
    }
    
    public void enableshowRecordedGamesDetailsButton(){
        showRecordedGamesDetailsButton.setDisable(false);
    }
    
    private boolean validateRCX(List<File> files){
        Pattern pattern = Pattern.compile(".*rcx");
        for(File f : files){
            Matcher matcher = pattern.matcher(f.getName());
            if(!matcher.find()){
                return false;
            }
        }
        return true;
    }
    
    private void showRecordingsTable(List<File> files) throws IOException{
        Stage recordingsTableStage = new Stage();
        
        FXMLLoader loader = new FXMLLoader (
                getClass().getResource("/recordings/RecordingsTableFXML.fxml")
                );
        
        Scene scene = new Scene((Parent)loader.load());
        
        RecordingsTableFXMLController controller = (RecordingsTableFXMLController) loader.getController();
        controller.setCorrespond(this);
        controller.setFiles(files);
        controller.start();
        
        recordingsTableStage.setScene(scene);
        recordingsTableStage.setTitle("Recorded Games");
        recordingsTableStage.show();
        stages.add(recordingsTableStage);
    }
    
    public void about(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About " + MainModel.appName);
        alert.setHeaderText("Indivision's Recorded Game Watcher");
        alert.setContentText("Copyright © indivision - 2015\n\nPS: fruit fen");

        alert.showAndWait();
    }
    
}