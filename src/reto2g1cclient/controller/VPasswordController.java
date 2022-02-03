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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reto2g1cclient.exception.ClientServerConnectionException;
import reto2g1cclient.exception.DBServerException;
import reto2g1cclient.logic.*;
import reto2g1cclient.model.User;

/**
 *
 * @author Ordenador
 */
public class VPasswordController {
    
    private static Logger LOGGER = Logger.getLogger("package.class");
    private final Pattern pass = Pattern.compile("^.*(?=.{6,25})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+*=]).*$");
    private User user;
    private Signable sig;
    private Stage stage;
    private boolean actual, newPass, confirm;

    /*
    * Getter del usuario que ha iniciado sesi�n y est� usando la aplicaci�n que va a cambiar su contrasenia
    * @return user usuario que ha iniciado sesi�n
     */

    /**
     *
     * @return
     */

    public User getUser() {
        return user;
    }

    /*
    * Setter del usuario que ha iniciado sesi�n y est� usando la aplicaci�n que va a cambiar su contrasenia
    * @param user usuario que ha iniciado sesi�n
     */

    /**
     *
     * @param user
     */

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Metodo para asignarle el stage a la ventana
     *
     * @param primaryStage Espacio donde se va a mostrar la ventana
     */
    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
    
    @FXML
    private PasswordField txtPassActual;
    @FXML
    private PasswordField txtPassNew;
    @FXML
    private PasswordField txtPassConfirm;
    @FXML
    private Label lblErrorActual;
    @FXML
    private Label lblErrorPassword;
    @FXML
    private Label lblErrorConfirm;
    @FXML
    private Button btnContinue;
    @FXML
    private Button btnCancel;

    /**
     * Initializes the controller class.
     *
     * @param root Elemento recibido desde la ventana VSignIn que permite que la
     * ventana muestre sus elementos hijos (Cuadros de texto,Botones...)
     */
    public void initStage(Parent root) {
        sig = ViewSignableFactory.getView();
        LOGGER.info("Initializing Password Changing window... ");
        Scene scene = new Scene(root);
        stage.setTitle("VPassword");
        stage.setResizable(false);
        
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeVPassword);
        stage.setScene(scene);
        
        txtPassActual.requestFocus();
        txtPassActual.textProperty().addListener(this::txtActualVal);
        txtPassNew.textProperty().addListener(this::txtPassVal);
        txtPassConfirm.textProperty().addListener(this::txtConfirmVal);
        
        btnContinue.setOnAction(this::changePass);
        btnCancel.setOnAction(this::back);
        
        stage.show();
        LOGGER.info("Password Changing window started... ");
    }
    
    /**
     *
     * @param event
     */
    public void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Beginning VEventTableController::handleWindowShowing");
        // Visibilidad de labels
        lblErrorActual.setVisible(false);
        lblErrorConfirm.setVisible(false);
        lblErrorPassword.setVisible(false);
        // Botones
        btnContinue.setDisable(true);
    }

    /**
     * Metodo para validar que el campo de contrase�a actual una vez eliminados
     * los espacios del principio y del final no es nulo
     *
     * @param observableValue Campo a validar
     * @param oldValue Valor previo del texto
     * @param newValue Valor que se va a validar
     */
    @FXML
    public void txtActualVal(ObservableValue observableValue,
            String oldValue,
            String newValue) {
        if (!newValue.trim().equals("")) {
            actual = true;
        } else {
            actual = false;
        }
        buttonActivation();
    }

    /**
     * Metodo para validar que el campo de contrase�a nueva una vez eliminados
     * los espacios del principio y del final no es nulo
     *
     * @param observableValue Campo a validar
     * @param oldValue Valor previo del texto
     * @param newValue Valor que se va a validar
     */
    @FXML
    public void txtPassVal(ObservableValue observableValue,
            String oldValue,
            String newValue) {
        if (!newValue.trim().equals("")) {
            newPass = true;
        } else {
            newPass = false;
        }
        buttonActivation();
    }

    /**
     * Metodo para validar que el campo de contrase�a nueva confirmada una vez
     * eliminados los espacios del principio y del final no es nulo
     *
     * @param observableValue Campo a validar
     * @param oldValue Valor previo del texto
     * @param newValue Valor que se va a validar
     */
    @FXML
    public void txtConfirmVal(ObservableValue observableValue,
            String oldValue,
            String newValue) {
        if (!newValue.trim().equals("")) {
            confirm = true;
        } else {
            confirm = false;
        }
        buttonActivation();
    }
    
    private void buttonActivation() {
        if (newPass && actual && confirm) {
            btnContinue.setDisable(false);
        } else {
            btnContinue.setDisable(true);
        }
    }
    
    /**
     *
     * @param event
     */
    public void changePass(ActionEvent event) {
        if (user.getPassword().equals(txtPassActual.getText())) {
            try {
                String password = txtPassNew.getText();
                user.setPassword(password);
                sig.changePassword(user);
                user.setPassword(password);
                postChange();
            } catch (DBServerException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error al Actualizar");
                alert.setHeaderText("Error al Actualizar valores en la Base de Datos");
                alert.setContentText("No se ha podido actualizar su contrase�a en la base de datos."
                        + "\nPor favor, intentelo mas tarde. "
                        + "\nSi el error persiste, contacte con soporte tecnico");
                alert.showAndWait();
            } catch (ClientServerConnectionException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error de Conexion");
                alert.setHeaderText("Error al Conectar con la Base de Datos");
                alert.setContentText("No se ha podido con la base de datos, lo que ha imposibilitado "
                        + "\nla actualizacion de su contrase�a. Por favor, intentelo mas tarde. "
                        + "\nSi el error persiste, contacte con soporte tecnico");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error de Datos");
            alert.setHeaderText("Los datos no son válidos");
            alert.setContentText("Debe introducir valores validos:"
                    + "\nLa contraseña actual debe ser su contraseña actual"
                    + "\nLa nueva contraseña debe contener mayúsculas, minúsculas, números y mínimo un carácter especial"
                    + "\nLa contraseña confirmada debe ser igual a la nueva contraseña");
            alert.showAndWait();
            txtPassActual.setText("");
            txtPassNew.setText("");
            txtPassConfirm.setText("");
            lblErrorActual.setVisible(true);
        }
    }
    
    /**
     *
     * @return
     */
    public boolean validatePassword() {
        boolean valid = false;
        Matcher matchpass = pass.matcher(txtPassNew.getText());
        if (matchpass.matches()) {
            if (txtPassNew.getText().equals(txtPassConfirm.getText())) {
                valid = true;
            } else {
                lblErrorConfirm.setVisible(true);
                txtPassNew.setText("");
                txtPassConfirm.setText("");
            }
        } else {
            lblErrorPassword.setVisible(true);
            txtPassNew.setText("");
            txtPassConfirm.setText("");
        }
        return valid;
    }
    
    /**
     *
     * @param event
     */
    public void back(ActionEvent event) {
        LOGGER.info("Volviendo a ventana VAdmin");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Volver a Ventana Principal");
        alert.setHeaderText("�Seguro que desea volver a su Ventana Principal?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            LOGGER.info("Cerrada ventana VPassword y volviendo a VAdmin");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VAdmin.fxml"));
            try {
                Parent root = (Parent) loader.load();
                VAdminController controller = loader.getController();
                controller.setStage(this.stage);
                controller.initStage(root);
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, "Error al volver a la ventana de sesi�n del usuario");
            }
        }
    }

    /**
     * Method to advise the user when uses the UI's innate close button (button
     * X) that the application will close
     *
     * @param event the event linked to clicking on the button;
     */
    public void closeVPassword(WindowEvent event) {
        LOGGER.info("Requesting confirmation for application closing...");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            LOGGER.info("Closing the application");
        } else {
            event.consume();
            LOGGER.info("Closing aborted");
        }
    }
    
    private void postChange() {
        LOGGER.info("Cerrada ventana VPassword y volviendo a VAdmin");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Cambio de contraseña exitoso");
        alert.setContentText("Se ha cambiado su contraseña con éxito, se le redireccionará a la ventana principal");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/reto2g1cclient/view/VAdmin.fxml"));
        try {
            Parent root = (Parent) loader.load();
            VAdminController controller = loader.getController();
            controller.setUser(user);
            controller.setStage(this.stage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error al volver a la ventana de sesi�n del usuario");
        }
    }
    
}
