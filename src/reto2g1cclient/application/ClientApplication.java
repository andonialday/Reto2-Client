/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.application;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import reto2g1cclient.controller.VSignInController;

/**
 * Client Main class to Launch Client App
 * @author Enaitz Izagirre
 */
public class ClientApplication extends Application {

    /**
     * Method to Launch Client 
     * @param args Java Arguments to launch the main
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method to Load and Launch the First Scene
     * @param primaryStage It create a Stage in which all scenes are loaded
     * @throws java.io.IOException I/O error for the Parent loader parameter
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        Logger.getLogger("Initializing client...");
        
        //get the route of the 1. View
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
            
        Parent root = (Parent) loader.load();
        
        //get the controller of the view
        VSignInController controller = ((VSignInController) loader.getController());
        controller.setStage(primaryStage);
        
        //initializate the stage
        controller.initStage(root);
    }
}