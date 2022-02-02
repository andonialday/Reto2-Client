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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Controlador del Menu del Administrador
 * @author Andoni Alday y Aitor Perez
 */
public class MenuAdminController {

    private static final Logger LOGGER = Logger.getLogger("package.class");
    
    @FXML
    private HBox hBoxMenu;
    @FXML
    private MenuBar mbAdmin;
    @FXML
    private Menu mData;
    @FXML
    private Menu mSession;
    @FXML
    private MenuItem miRental;
    @FXML
    private MenuItem miClient;
    @FXML
    private MenuItem miCommercial;
    @FXML
    private MenuItem miEquipment;
    @FXML
    private MenuItem miEvent;
    @FXML
    private MenuItem miLogout;
    @FXML
    private MenuItem miPassword;
    @FXML
    private MenuItem miExit;
    
    private Stage stage;
    
    // User for Password changing
    //private User user;

    /*
    * Getter del usuario que ha iniciado sesión y está usando la aplicación
    * @return user usuario que ha iniciado sesión
    */
    //public User getUser() {
    //    return user;
    //}
    /*
    * Setter del usuario que ha iniciado sesión y está usando la aplicación
    * @param user usuario que ha iniciado sesión
    */
    //public void setUser(User user) {
    //    user = user;
    //}

    /**
     * Getter del Stage que va a emplear el menú
     *
     * @return el stage a usar
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Setter del Stage que va a emplear el menú
     *
     * @param stage a usar por el menú
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Método de inicio del menú, asignando los métodos de cambio de ventana a
     * los submenús y agrupamientos de los mismos.
     */
    public void initStage() {
    }

    /**
     * Metodo a ejecutar cuando se pulsa en el submenu "Ver Alquileres"
     *
     * @param event evento de seleccion
     */
    @FXML
    private void goToEventEquipment(ActionEvent event) {
        LOGGER.info("Initializing EventEquipment");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ventana EventEquipment");
        alert.setHeaderText("VEventEquipment no disponible");
        alert.setContentText("La ventana VEventEquipment no se ha podido desarrollar por falta de tiempo, perdone las molestias");
        alert.showAndWait();
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
     * Metodo a ejecutar cuando se pulsa en el submenu "Ver Clientes"
     *
     * @param event evento de seleccion
     */
    @FXML
    private void goToClient(ActionEvent event) {
        LOGGER.info("Initializing Client");
        /*
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ventana EventEquipment");
        alert.setHeaderText("VEventEquipment no disponible");
        alert.setContentText("La ventana VEventEquipment no se ha podido desarrollar por falta de tiempo, perdone las molestias");
         */
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VClientTable.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VClientTableController controller = ((VClientTableController) fxmlLoader.getController());
            Stage primaryStage = new Stage();
            controller.setStage(primaryStage);
            controller.initStage(root);
            hBoxMenu.getScene().getWindow().hide();
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * Metodo a ejecutar cuando se pulsa en el submenu "Ver Comerciales"
     *
     * @param event evento de seleccion
     */
    @FXML
    private void goToCommercial(ActionEvent event) {
        LOGGER.info("Initializing Commercial");
        /*
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ventana EventEquipment");
        alert.setHeaderText("VEventEquipment no disponible");
        alert.setContentText("La ventana VEventEquipment no se ha podido desarrollar por falta de tiempo, perdone las molestias");
         */
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VCommercialTable.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VCommercialTableController controller = ((VCommercialTableController) fxmlLoader.getController());
            Stage primaryStage = new Stage();
            controller.setStage(primaryStage);
            controller.initStage(root);
            hBoxMenu.getScene().getWindow().hide();
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * Metodo a ejecutar cuando se pulsa en el submenu "Ver Equipmiento"
     *
     * @param event evento de seleccion
     */
    @FXML
    private void goToEquipment(ActionEvent event) {
        LOGGER.info("Initializing Equipment");
        /*
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ventana EventEquipment");
        alert.setHeaderText("VEventEquipment no disponible");
        alert.setContentText("La ventana VEventEquipment no se ha podido desarrollar por falta de tiempo, perdone las molestias");
         */
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VEquipmentTable.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            EquipmentController controller = ((EquipmentController) fxmlLoader.getController());
            Stage primaryStage = new Stage();
            controller.setStage(primaryStage);
            controller.initStage(root);
            hBoxMenu.getScene().getWindow().hide();
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * Metodo a ejecutar cuando se pulsa en el submenu "Ver Eventos"
     *
     * @param event evento de seleccion
     */
    @FXML
    private void goToEvent(ActionEvent event) {
        LOGGER.info("Initializing Event");
        /*
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ventana EventEquipment");
        alert.setHeaderText("VEventEquipment no disponible");
        alert.setContentText("La ventana VEventEquipment no se ha podido desarrollar por falta de tiempo, perdone las molestias");
         */
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VEventTable.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VEventTableController controller = ((VEventTableController) fxmlLoader.getController());
            Stage primaryStage = new Stage();
            controller.setEditable(true);
            controller.setStage(primaryStage);
            controller.initStage(root);
            hBoxMenu.getScene().getWindow().hide();
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * Metodo a ejecutar cuando se pulsa en el submenu "Cambiar Contraseña"
     *
     * @param event evento de seleccion
     */
    @FXML
    private void password(ActionEvent event) {
        LOGGER.info("Initializing Password Change");
        /*
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ventana Cambio Contraseña");
        alert.setHeaderText("VPassword no disponible");
        alert.setContentText("La ventana VPassword (Ventana para cambio de contraseña) " +
        + "\nno se ha podido desarrollar por falta de tiempo, perdone las molestias");
         */
        try {
            hBoxMenu.getScene().getWindow().hide();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VPassword.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VPasswordController controller = ((VPasswordController) fxmlLoader.getController());
            Stage primaryStage = new Stage();
            controller.setStage(primaryStage);
            controller.setUser(VAdminController.getUser());
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * Metodo a ejecutar cuando se pulsa en el submenu "Cerrar Sesion"
     *
     * @param event evento de seleccion
     */
    @FXML
    private void logOut(ActionEvent event) {
        LOGGER.info("Initializing LogOut");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VSignIn.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            VSignInController controller = ((VSignInController) fxmlLoader.getController());
            Stage primaryStage = new Stage();
            controller.setStage(primaryStage);
            controller.initStage(root);
            hBoxMenu.getScene().getWindow().hide();
        } catch (IOException ex) {
            LOGGER.info("Error initializing VSignUp");
        }
    }

    /**
     * Metodo a ejecutar cuando se pulsa en el submenu "Salir"
     *
     * @param event evento de seleccion
     */
    @FXML
    private void salir(ActionEvent event) {
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
