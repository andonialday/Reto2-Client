/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.model.Client;

/**
 *
 * @author 2dam
 */
public class VEventTableController {
    
    private static final Logger LOGGER = Logger.getLogger("package.class");
    
    private Stage stage;
    
    /**
     * Sets the stage
     *
     * @param stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Gets the stage
     *
     * @return stage to get
     */
    public Stage getStage() {
        return stage;
    }
    
    /**
     * Initialize and show window
     *
     * @param root
     * @throws IOException
     */
    public void initStage(Parent root) throws IOException {

        LOGGER.info("Initializing VReset stage.");

        //Create a new scene
        Scene scene = new Scene(root);

        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("Reset Password");
        stage.setResizable(false);

        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeVEventTableController);

        //Show main window
        stage.show();
    }
    
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VEventTableController::handleWindowShowing");
        
    }
    
    /**
     * Method to advise the user when uses the UI's innate close button (button
     * X) that the application will close
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVEventTableController(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Program is closing");
        alert.setHeaderText("Are you sure you want to close the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing the application");
            Platform.exit();
        } else {
            event.consume();
            LOGGER.info("Application closing cancelled");
        }
    }

    void setClient(Client clientEvents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void setEditable(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
