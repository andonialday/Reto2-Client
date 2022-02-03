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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.model.User;

/**
 * Controlador de la ventana de VAdmin
 *
 * @author Andoni Alday y Aitor Perez
 */
public class VAdminController {

    /**
     * Initializes the controller class. We use logger to record the activity of
     * the application.
     */
    private static final Logger LOGGER = Logger.getLogger("package.class");

    private Stage stage;

    private static User usr;

    // Setters && Getter
    /**
     * Setter for the user attribute, so the user's FullName can be shown on
     * view startup
     *
     * @param user received freom VSignIn on correct Signin result
     */
    public void setUser(User user) {
        this.usr = user;
    }
    
    /**
     *
     * @return
     */
    public static User getUser() {
        return usr;
    }

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

    //Controlador del Menu
    @FXML
    private Label lblWelcome;

    /**
     * Initialize and show window
     *
     * @param root
     * @throws IOException
     */
    // añadir enlace de recuperar contraseña
    public void initStage(Parent root) throws IOException {

        LOGGER.info("Initializing Admin Main Window");

        //Create a new scene
        Scene scene = new Scene(root);

        //CSS (route & scene)
        String css = this.getClass().getResource("/reto2g1cclient/view/javaFXUIStyles.css").toExternalForm();
        scene.getStylesheets().add(css);

        //Associate the scene to the stage
        stage.setScene(scene);

        //Set the scene properties
        stage.setTitle("VAdmin");
        stage.setResizable(false);

        //Set Windows event handlers 
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeVAdmin);

        //Show main window
        stage.show();

    }

    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning AdminController::handleWindowShowing");
        lblWelcome.isVisible();
    }

    /**
     * Method to advise the user when uses the UI's innate close button (button
     * X) that the application will close
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVAdmin(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Está Cerrando el Programa");
        alert.setHeaderText("¿Seguro que desea cerrar el programa?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            LOGGER.info("Closing the application");
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }
}
