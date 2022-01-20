/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto1client.controller;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto1libraries.object.User;


/**
 * FXML VCommercialController class Controller for the VFinal JavaFX scene
 * @author Enaitz Izagirre
 */
public class VCommercialController {

    
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
    
    
    private String msg;
    
     public VCommercialController() {
        msg = "Bienvenido ";
    }
    
    
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnApect;
    @FXML
    private Label lblLogin;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblType;

  
    
    private User usr;
    private static final Logger LOGGER = Logger.getLogger("package.class");

   

    /**
     * Window launcher method for the VCOmmercial JavaFX view with its elements
     *
     * @param root received from the VSignIn, which allows the window to handle
     * its children elements
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing Post SignIn process ...");
        // Inicialización de la ventana
        Scene scene = new Scene(root);
        
        //Associate the scene to the stage
        stage.setScene(scene);
        
        stage.setTitle("VCommercial");
        stage.setResizable(false);
        
  //      stage.setOnCloseRequest(this::closeVFinalX);
        stage.setScene(scene);
     
       // btnApect.setOnAction(this::signIn);
      //  btnCancel.setOnAction(this::exit);
        
        stage.show();
        LOGGER.info("VCommerce Window Showing");
    }

    /**
     * Method of the Close Button (btClose) that closes the scene and the
     * program completelly
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVFinal(ActionEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alertc = new Alert(AlertType.CONFIRMATION);
        alertc.setTitle("Está Cerrando el Programa");
        alertc.setHeaderText("¿Seguro que desea cerrar el programa?");
        Optional<ButtonType> result = alertc.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing the application");
            Platform.exit();
        } else {
            LOGGER.info("Application closing cancelled");
        }
    }

    /**
     * Method to create a confirmation popup when user uses the UI's innate
     * close button (button X)
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVFinalX(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alertx = new Alert(AlertType.CONFIRMATION);
        alertx.setTitle("Está Cerrando el Programa");
        alertx.setHeaderText("¿Seguro que desea cerrar el programa?");
        Optional<ButtonType> result = alertx.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Closing the application");
            Platform.exit();
        } else {
            LOGGER.info("Application closing cancelled");
        }
    }

    /**
     * Method of the LogOut Hyperlink (hlLogOut) that nds thecurrent users
     * session on the application and returns to the SignIn window
     *
     * @param event the event linked to clicking on the button;
     * @throws java.io.IOException
     */
    public void logOut(ActionEvent event) throws IOException {
        LOGGER.info("Requesting confirmation for Signing Out...");
        Alert alertl = new Alert(AlertType.CONFIRMATION);
        alertl.setTitle("Cerrando Sesión");
        alertl.setHeaderText("¿Seguro que desea cerrar sesión?");
        Optional<ButtonType> result = alertl.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Signing Out");
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto1client/view/VCommercial.fxml"));
            Parent root = (Parent) loader.load();
      //      VSignInController controller = loader.getController();
     //       controller.setStage(this.stage);
     //       controller.initStage(root);
        } else {
            LOGGER.info("Signing Out cancelled");
        }
    }

    // Setters
    /**
     * Setter for the user attribute, so the user's FullName can be shown on
     * view startup
     *
     * @param user received freom VSignIn on correct Signin result
     
    public void setUser(User user) {
        this.usr = user;
    }
    * */

    /**
     * Setter for the Stage attribute , in which the view will be loaded
     *
     * @param primaryStage received from VSignIn, thestageon which all the views
     * will appear
     */
 //   public void setStage(Stage primaryStage) {
  //      this.stage = primaryStage;

   

}
