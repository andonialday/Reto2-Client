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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Andoni Alday y Aitor Perez
 */
public class MenuAdminController {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    private MenuBar mbAdmin;
    private Menu mData;
    private Menu mSession;
    private MenuItem miRental;
    private MenuItem miClient;
    private MenuItem miCommercial;
    private MenuItem miEquipment;
    private MenuItem miEvent;
    private MenuItem miLogout;
    private MenuItem miExit;

    public void initStage() {
        // Setting Menu
        miRental.setOnAction(this::goToEventEquipment);
        miClient.setOnAction(this::goToClient);
        miCommercial.setOnAction(this::goToCommercial);
        miEquipment.setOnAction(this::goToEquipment);
        miEvent.setOnAction(this::goToEvent);
        miLogout.setOnAction(this::logOut);
        miExit.setOnAction(this::exitProgram);
        mData.getItems().addAll(miRental, miClient, miCommercial, miEquipment, miEvent);
        mSession.getItems().addAll(miLogout, miExit);
        mbAdmin.getMenus().addAll(mData, mSession);
    }

    /**
     * This method is executed when the user clicks the hyperlink SignUp. The
     * Sign Up window will open.
     *
     * @param event
     */
    @FXML
    private void goToEventEquipment(ActionEvent event) {
        LOGGER.info("Initializing EventEquipment");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ventana EventEquipment");
        alert.setHeaderText("VEventEquipment no disponible");
        alert.setContentText("La ventana VEventEquipment no se ha podido desarrollar por falta de tiempo, perdone las molestias");
        /*
        try {            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VEventEquipment.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VEventEquipmentController controller = ((VEventEquipmentController) fxmlLoader.getController());
            Stage primaryStage = this.stage;
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
            */
    }

    /**
     * This method is executed when the user clicks the hyperlink SignUp. The
     * Sign Up window will open.
     *
     * @param event
     */
    @FXML
    private void goToClient(ActionEvent event) {
        LOGGER.info("Initializing SignUp");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VSignInController controller = ((VSignInController) fxmlLoader.getController());
            Stage primaryStage = this.stage;
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * This method is executed when the user clicks the hyperlink SignUp. The
     * Sign Up window will open.
     *
     * @param event
     */
    @FXML
    private void goToCommercial(ActionEvent event) {
        LOGGER.info("Initializing SignUp");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VSignInController controller = ((VSignInController) fxmlLoader.getController());
            Stage primaryStage = this.stage;
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * This method is executed when the user clicks the hyperlink SignUp. The
     * Sign Up window will open.
     *
     * @param event
     */
    @FXML
    private void goToEquipment(ActionEvent event) {
        LOGGER.info("Initializing SignUp");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VSignInController controller = ((VSignInController) fxmlLoader.getController());
            Stage primaryStage = this.stage;
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * This method is executed when the user clicks the hyperlink SignUp. The
     * Sign Up window will open.
     *
     * @param event
     */
    @FXML
    private void goToEvent(ActionEvent event) {
        LOGGER.info("Initializing SignUp");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VSignInController controller = ((VSignInController) fxmlLoader.getController());
            Stage primaryStage = this.stage;
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * This method is executed when the user clicks the hyperlink SignUp. The
     * Sign Up window will open.
     *
     * @param event
     */
    @FXML
    private void logOut(ActionEvent event) {
        LOGGER.info("Initializing SignUp");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VSignInController controller = ((VSignInController) fxmlLoader.getController());
            Stage primaryStage = this.stage;
            controller.setStage(primaryStage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    public void exitProgram(WindowEvent event) {
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
