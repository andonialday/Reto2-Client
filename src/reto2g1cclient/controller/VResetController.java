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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.exception.FieldsEmptyException;
import reto2g1cclient.exception.LoginNotValidException;

/**
 *
 * @author Jaime San Sebastian
 */
public class VResetController {
    
    private static final Logger LOGGER = Logger.getLogger("package.class");
    
    @FXML
    private TextField tfLogin;
    @FXML
    private Button btnContinue;
    @FXML
    private Button btnCancel;
    
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
        stage.setOnCloseRequest(this::closeVReset);

        //Show main window
        stage.show();
    }
    
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VClientTable::handleWindowShowing");
        
        //Continue button is disabled
        btnContinue.setDisable(true);
        
        //Cancel button is enabled
        btnCancel.setDisable(false);
    }
    
    /**
     * Method that controls the continue button
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleContinue (ActionEvent event) 
            throws LoginNotValidException, FieldsEmptyException {
    
    //Checks if the field is written
        if (tfLogin.getText().trim().isEmpty()){
            LOGGER.info("Some fields are empty");
            throw new FieldsEmptyException("Error, some fields are empty");
        }
    //Check if the login field has no more than 25 characters and no less than 5
        if (tfLogin.getText().trim().length() > 25 
                || tfLogin.getText().trim().length() < 5) {
            throw new LoginNotValidException("Error, invalid login");
        }
    }
    
    /**
     * Method that controls the cancel button
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        LOGGER.info("Returning to VAdmin window");

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Back to VAdmin");
        alert1.setHeaderText(null);
        alert1.setContentText("Are you sure "
                + "you want to return to the previous window?");
        alert1.showAndWait();
        Optional<ButtonType> result = alert1.showAndWait();

        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing VReset window and returning to VAdmin");
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/reto2client/view/VAdmin.fxml"));

            try {
                Parent root = (Parent) loader.load();
                VAdminController controller = loader.getController();
                controller.setStage(this.stage);
                controller.initStage(root);

            } catch (IOException ex) {
                LOGGER.info("Error closing reset password window");
            }
        }
    }
    
    /**
     * Method to advise the user when uses the UI's innate close button (button
     * X) that the application will close
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVReset(WindowEvent event) {
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
}
