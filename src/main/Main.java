package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Stef
 */
public class Main extends Application {
    
    MainFXMLController controller;
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader (
                getClass().getResource("MainFXML.fxml")
                );
        
        Scene scene = new Scene((Parent)loader.load());
        
        controller = (MainFXMLController) loader.getController();
        controller.setStage(stage);
        controller.setModel(new MainModel());
        
        stage.setScene(scene);
        stage.setTitle(MainModel.appName);
        stage.show();
    }
    
    @Override
    public void stop(){
        controller.close();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
