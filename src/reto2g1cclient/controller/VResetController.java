/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reto2g1cclient.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.CredentialErrorException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.Signable;
import reto2g1cclient.logic.ViewSignableFactory;

/**
 *
 * @author Jaime San Sebastian
 */
public class VResetController {
    
    private static final Logger LOGGER = Logger.getLogger("package.class");
    
    @FXML
    private TextField tfLogin;
    @FXML
    private Label lblLogin;
    @FXML
    private Button btnContinue;
    @FXML
    private Button btnCancel;
    
    private Stage stage;
    
    private Signable sig;

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
     */
    public void initStage(Parent root) {
        
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
        //Listener for Login TextField Input
        tfLogin.textProperty().addListener(this::loginInput);

        //Initialization of the Signable Interface
        sig = ViewSignableFactory.getView();
        //Show main window
        stage.show();
    }
    
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VClientTable::handleWindowShowing");
        //Login label is visible
        lblLogin.setVisible(true);
        //TextField Login is editable
        tfLogin.setEditable(true);
        //Continue button is disabled
        btnContinue.setDisable(true);
        //Cancel button is enabled
        btnCancel.setDisable(false);
    }
    
    private void loginInput(ObservableValue observableValue, String oldValue, String newValue) {
        if (newValue.trim().equals("")) {
            btnContinue.setDisable(true);
        } else {
            btnContinue.setDisable(false);
        }
    }

    /**
     * Method that controls the continue button
     *
     * @param event the event linked to clicking on the button;
     */
    @FXML
    private void handleContinue(ActionEvent event) {
        try {
            sig.resetPassword(tfLogin.getText());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Contraseña cambiada correctamente");
            alert.setContentText("Se ha enviado un correo electronico con la nueva contraseña");
        } catch (ClientServerConnectionException ex) {
            Logger.getLogger(VResetController.class.getName()).log(Level.SEVERE, null, ex);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error de Conexión");
            altWarningLog.setHeaderText("Error al contactar con el servidor de base de datos");
            altWarningLog.setContentText("Ha sucedido un error al intentar contactar con la base de "
                    + "\ndatos para solicitar la restauración de la contraseña. "
                    + "\nPor favor, inténtelo más tarde. Si el error persiste, comuníquese con soporte técnico.");
            altWarningLog.showAndWait();
        } catch (DBServerException ex) {
            Logger.getLogger(VResetController.class.getName()).log(Level.SEVERE, null, ex);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error de Procesamiento");
            altWarningLog.setHeaderText("Error al restaurar la contraseña");
            altWarningLog.setContentText("Ha sucedido un error al intentar restaurar su contraseña "
                    + "\ny notificarle de la misma por correo electrónico"
                    + "\nPor favor, inténtelo más tarde. Si el error persiste, comuníquese con soporte técnico.");
            altWarningLog.showAndWait();
        } catch (CredentialErrorException e) {
            Logger.getLogger(VResetController.class.getName()).log(Level.SEVERE, null, e);
            Alert altWarningLog = new Alert(AlertType.WARNING);
            altWarningLog.setTitle("Error de Credenciales");
            altWarningLog.setHeaderText("Error al restaurar la contraseña");
            altWarningLog.setContentText("Ha sucedido un error al intentar restaurar su contraseña "
                    + "\ny notificarle de la misma por correo electrónico"
                    + "\nPor favor, inténtelo más tarde. Si el error persiste, comuníquese con soporte técnico.");
            altWarningLog.showAndWait();
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
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                LOGGER.info("Closing VReset window and returning to VAdmin");
                FXMLLoader loader = new FXMLLoader(getClass()
                        .getResource("/reto2cclient/view/VSignIn.fxml"));
                Parent root = (Parent) loader.load();
                VSignInController controller = (VSignInController) loader.getController();
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
